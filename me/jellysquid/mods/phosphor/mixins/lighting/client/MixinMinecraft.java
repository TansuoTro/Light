/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.client;
/*    */ 
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.profiler.Profiler;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.client.Minecraft.class})
/*    */ public abstract class MixinMinecraft
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   public Profiler field_71424_I;
/*    */   @Shadow
/*    */   public WorldClient field_71441_e;
/*    */   
/*    */   @Inject(method = {"runTick"}, at = {@At(value = "CONSTANT", args = {"stringValue=levelRenderer"}, shift = At.Shift.BY, by = -3)})
/*    */   private void onRunTick(CallbackInfo ci) {
/* 30 */     this.field_71424_I.func_76318_c("lighting");
/*    */     
/* 32 */     ((ILightingEngineProvider)this.field_71441_e).getLightingEngine().processLightUpdates();
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\client\MixinMinecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */