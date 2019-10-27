/*     */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*     */ 
/*     */ import me.jellysquid.mods.phosphor.mod.world.lighting.LightingHooks;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import org.spongepowered.asm.mixin.Dynamic;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin(value = {Chunk.class}, priority = 10055)
/*     */ public abstract class MixinChunk$Sponge
/*     */ {
/*     */   private static final String SET_BLOCK_STATE_SPONGE = "bridge$setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lorg/spongepowered/api/world/BlockChangeFlag;)Lnet/minecraft/block/state/IBlockState;";
/*     */   @Shadow
/*     */   @Final
/*     */   private World field_76637_e;
/*     */   private static final int WIZARD_MAGIC = 694698818;
/*     */   
/*     */   @Redirect(method = {"bridge$setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lorg/spongepowered/api/world/BlockChangeFlag;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "NEW", args = {"class=net/minecraft/world/chunk/storage/ExtendedBlockStorage"}), expect = 0)
/*     */   @Dynamic
/*  46 */   private ExtendedBlockStorage setBlockStateCreateSectionSponge(int y, boolean storeSkylight) { return initSection(y, storeSkylight); }
/*     */ 
/*     */   
/*     */   private ExtendedBlockStorage initSection(int y, boolean storeSkylight) {
/*  50 */     ExtendedBlockStorage storage = new ExtendedBlockStorage(y, storeSkylight);
/*     */     
/*  52 */     LightingHooks.initSkylightForSection(this.field_76637_e, (Chunk)this, storage);
/*     */     
/*  54 */     return storage;
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
/*     */   @ModifyVariable(method = {"bridge$setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lorg/spongepowered/api/world/BlockChangeFlag;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "LOAD", ordinal = 0), index = 14, name = {"requiresNewLightCalculations"}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;get(III)Lnet/minecraft/block/state/IBlockState;"), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;generateSkylightMap()V")), allow = 1)
/*     */   @Dynamic
/*  85 */   private boolean setBlockStateInjectGenerateSkylightMapVanilla(boolean generateSkylight) { return false; }
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
/*     */   @ModifyVariable(method = {"bridge$setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lorg/spongepowered/api/world/BlockChangeFlag;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "LOAD", ordinal = 1), index = 13, name = {"newBlockLightOpacity"}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;relightBlock(III)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;propagateSkylightOcclusion(II)V")), allow = 1)
/*     */   @Dynamic
/* 118 */   private int setBlockStatePreventPropagateSkylightOcclusion1(int generateSkylight) { return 694698818; }
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
/*     */   @ModifyVariable(method = {"bridge$setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/block/state/IBlockState;Lorg/spongepowered/api/world/BlockChangeFlag;)Lnet/minecraft/block/state/IBlockState;"}, at = @At(value = "LOAD", ordinal = 0), index = 24, name = {"postNewBlockLightOpacity"}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;relightBlock(III)V", ordinal = 1), to = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;propagateSkylightOcclusion(II)V")), allow = 1)
/*     */   @Dynamic
/* 151 */   private int setBlockStatePreventPropagateSkylightOcclusion2(int generateSkylight) { return 694698818; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinChunk$Sponge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */