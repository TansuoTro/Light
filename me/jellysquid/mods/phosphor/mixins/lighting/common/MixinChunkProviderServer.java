/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*    */ 
/*    */ import java.util.Set;
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*    */ import net.minecraft.world.WorldServer;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.world.gen.ChunkProviderServer.class})
/*    */ public abstract class MixinChunkProviderServer
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   public WorldServer field_73251_h;
/*    */   @Shadow
/*    */   @Final
/*    */   private Set<Long> field_73248_b;
/*    */   
/*    */   @Inject(method = {"saveChunks"}, at = {@At("HEAD")})
/* 32 */   private void onSaveChunks(boolean all, CallbackInfoReturnable<Boolean> cir) { ((ILightingEngineProvider)this.field_73251_h).getLightingEngine().processLightUpdates(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"tick"}, at = {@At("HEAD")})
/*    */   private void onTick(CallbackInfoReturnable<Boolean> cir) {
/* 43 */     if (!this.field_73251_h.field_73058_d && 
/* 44 */       !this.field_73248_b.isEmpty())
/* 45 */       ((ILightingEngineProvider)this.field_73251_h).getLightingEngine().processLightUpdates(); 
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinChunkProviderServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */