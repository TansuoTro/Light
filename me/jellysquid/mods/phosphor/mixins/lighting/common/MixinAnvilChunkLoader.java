/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*    */ 
/*    */ import me.jellysquid.mods.phosphor.api.IChunkLightingData;
/*    */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*    */ import me.jellysquid.mods.phosphor.mod.world.lighting.LightingHooks;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.world.chunk.storage.AnvilChunkLoader.class})
/*    */ public abstract class MixinAnvilChunkLoader
/*    */ {
/*    */   @Inject(method = {"saveChunk"}, at = {@At("HEAD")})
/* 25 */   private void onConstructed(World world, Chunk chunkIn, CallbackInfo callbackInfo) { ((ILightingEngineProvider)world).getLightingEngine().processLightUpdates(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"readChunkFromNBT"}, at = {@At("RETURN")})
/*    */   private void onReadChunkFromNBT(World world, NBTTagCompound compound, CallbackInfoReturnable<Chunk> cir) {
/* 35 */     Chunk chunk = (Chunk)cir.getReturnValue();
/*    */     
/* 37 */     LightingHooks.readNeighborLightChecksFromNBT(chunk, compound);
/*    */     
/* 39 */     ((IChunkLightingData)chunk).setLightInitialized(compound.func_74767_n("LightPopulated"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"writeChunkToNBT"}, at = {@At("RETURN")})
/*    */   private void onWriteChunkToNBT(Chunk chunk, World world, NBTTagCompound compound, CallbackInfo ci) {
/* 49 */     LightingHooks.writeNeighborLightChecksToNBT(chunk, compound);
/*    */     
/* 51 */     compound.func_74757_a("LightPopulated", ((IChunkLightingData)chunk).isLightInitialized());
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinAnvilChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */