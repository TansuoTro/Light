/*     */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*     */ 
/*     */ import me.jellysquid.mods.phosphor.mod.world.lighting.LightingHooks;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.Slice;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({Chunk.class})
/*     */ public abstract class MixinChunk$Vanilla
/*     */ {
/*     */   private static final String SET_BLOCK_STATE_VANILLA = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;";
/*     */   @Shadow
/*     */   @Final
/*     */   private World field_76637_e;
/*     */   private static final int WIZARD_MAGIC = 694698818;
/*     */   
/*     */   @Redirect(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "NEW", args = {"class=net/minecraft/world/chunk/storage/ExtendedBlockStorage"}), expect = 0)
/*  40 */   private ExtendedBlockStorage setBlockStateCreateSectionVanilla(int y, boolean storeSkylight) { return initSection(y, storeSkylight); }
/*     */ 
/*     */   
/*     */   private ExtendedBlockStorage initSection(int y, boolean storeSkylight) {
/*  44 */     ExtendedBlockStorage storage = new ExtendedBlockStorage(y, storeSkylight);
/*     */     
/*  46 */     LightingHooks.initSkylightForSection(this.field_76637_e, (Chunk)this, storage);
/*     */     
/*  48 */     return storage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "STORE", ordinal = 1), index = 13, name = {"flag"}, slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/chunk/Chunk;storageArrays:[Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;set(IIILnet/minecraft/block/state/IBlockState;)V")), allow = 1)
/*  79 */   private boolean setBlockStateInjectGenerateSkylightMapVanilla(boolean generateSkylight) { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "LOAD", ordinal = 0), index = 11, name = {"k1"}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;relightBlock(III)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;propagateSkylightOcclusion(II)V")), allow = 1)
/* 111 */   private int setBlockStatePreventPropagateSkylightOcclusion1(int generateSkylight) { return 694698818; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyVariable(method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "LOAD", ordinal = 1), index = 14, name = {"j1"}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;relightBlock(III)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;propagateSkylightOcclusion(II)V")), allow = 1)
/* 143 */   private int setBlockStatePreventPropagateSkylightOcclusion2(int generateSkylight) { return 694698818; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinChunk$Vanilla.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */