/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*    */ 
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import org.spongepowered.asm.mixin.Mixin;
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
/*    */ @Mixin({net.minecraft.network.play.server.SPacketChunkData.class})
/*    */ public abstract class MixinSPacketChunkData
/*    */ {
/*    */   @Inject(method = {"calculateChunkSize"}, at = {@At("HEAD")})
/* 21 */   private void onCalculateChunkSize(Chunk chunkIn, boolean hasSkyLight, int changedSectionFilter, CallbackInfoReturnable<Integer> cir) { ((ILightingEngineProvider)chunkIn).getLightingEngine().processLightUpdates(); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinSPacketChunkData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */