/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*    */ 
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngine;
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*    */ import me.jellysquid.mods.phosphor.mod.world.lighting.LightingEngine;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.EnumSkyBlock;
/*    */ import net.minecraft.world.World;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({World.class})
/*    */ public abstract class MixinWorld
/*    */   implements ILightingEngineProvider
/*    */ {
/*    */   private LightingEngine lightingEngine;
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At("RETURN")})
/* 24 */   private void onConstructed(CallbackInfo ci) { this.lightingEngine = new LightingEngine((World)this); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"checkLightFor"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void checkLightFor(EnumSkyBlock type, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
/* 33 */     this.lightingEngine.scheduleLightUpdate(type, pos);
/*    */     
/* 35 */     cir.setReturnValue(Boolean.valueOf(true));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 40 */   public LightingEngine getLightingEngine() { return this.lightingEngine; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */