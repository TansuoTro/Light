/*     */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*     */ 
/*     */ import me.jellysquid.mods.phosphor.api.IChunkLighting;
/*     */ import me.jellysquid.mods.phosphor.api.IChunkLightingData;
/*     */ import me.jellysquid.mods.phosphor.api.ILightingEngine;
/*     */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*     */ import me.jellysquid.mods.phosphor.mod.world.WorldChunkSlice;
/*     */ import me.jellysquid.mods.phosphor.mod.world.lighting.LightingHooks;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Overwrite;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin({Chunk.class})
/*     */ public abstract class MixinChunk
/*     */   implements IChunkLighting, IChunkLightingData, ILightingEngineProvider {
/*  30 */   private static final EnumFacing[] HORIZONTAL = EnumFacing.Plane.HORIZONTAL.func_179516_a();
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private ExtendedBlockStorage[] field_76652_q;
/*     */   
/*     */   @Shadow
/*     */   private boolean field_76643_l;
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private int[] field_76634_f;
/*     */   
/*     */   @Shadow
/*     */   private int field_82912_p;
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private int[] field_76638_b;
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private World field_76637_e;
/*     */   
/*     */   @Shadow
/*     */   private boolean field_76646_k;
/*     */   
/*     */   @Final
/*     */   @Shadow
/*     */   private boolean[] field_76639_c;
/*     */   
/*     */   @Final
/*     */   @Shadow
/*     */   public int field_76635_g;
/*     */   
/*     */   @Final
/*     */   @Shadow
/*     */   public int field_76647_h;
/*     */   
/*     */   @Shadow
/*     */   private boolean field_76650_s;
/*     */   
/*     */   private short[] neighborLightChecks;
/*     */   
/*     */   private boolean isLightInitialized;
/*     */   
/*     */   private ILightingEngine lightingEngine;
/*     */   
/*     */   @Shadow
/*     */   public abstract TileEntity func_177424_a(BlockPos paramBlockPos, Chunk.EnumCreateEntityType paramEnumCreateEntityType);
/*     */   
/*     */   @Shadow
/*     */   public abstract IBlockState func_177435_g(BlockPos paramBlockPos);
/*     */   
/*     */   @Shadow
/*     */   protected abstract int func_150808_b(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean func_177444_d(BlockPos paramBlockPos);
/*     */   
/*     */   @Inject(method = {"<init>"}, at = {@At("RETURN")})
/*  91 */   private void onConstructed(CallbackInfo ci) { this.lightingEngine = ((ILightingEngineProvider)this.field_76637_e).getLightingEngine(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"getLightSubtracted"}, at = {@At("HEAD")})
/* 101 */   private void onGetLightSubtracted(BlockPos pos, int amount, CallbackInfoReturnable<Integer> cir) { this.lightingEngine.processLightUpdates(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"onLoad"}, at = {@At("RETURN")})
/* 111 */   private void onLoad(CallbackInfo ci) { LightingHooks.scheduleRelightChecksForChunkBoundaries(this.field_76637_e, (Chunk)this); }
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
/*     */   @Redirect(method = {"setLightFor"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;generateSkylightMap()V"), expect = 0)
/* 130 */   private void setLightForRedirectGenerateSkylightMap(Chunk chunk, EnumSkyBlock type, BlockPos pos, int value) { LightingHooks.initSkylightForSection(this.field_76637_e, (Chunk)this, this.field_76652_q[pos.func_177956_o() >> 4]); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   private void func_76615_h(int x, int y, int z) {
/* 139 */     int i = this.field_76634_f[z << 4 | x] & 0xFF;
/* 140 */     int j = i;
/*     */     
/* 142 */     if (y > i) {
/* 143 */       j = y;
/*     */     }
/*     */     
/* 146 */     while (j > 0 && func_150808_b(x, j - 1, z) == 0) {
/* 147 */       j--;
/*     */     }
/*     */     
/* 150 */     if (j != i) {
/* 151 */       this.field_76634_f[z << 4 | x] = j;
/*     */       
/* 153 */       if (this.field_76637_e.field_73011_w.func_191066_m()) {
/* 154 */         LightingHooks.relightSkylightColumn(this.field_76637_e, (Chunk)this, x, z, i, j);
/*     */       }
/*     */       
/* 157 */       int l1 = this.field_76634_f[z << 4 | x];
/*     */       
/* 159 */       if (l1 < this.field_82912_p) {
/* 160 */         this.field_82912_p = l1;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   public int func_177413_a(EnumSkyBlock type, BlockPos pos) {
/* 173 */     this.lightingEngine.processLightUpdatesForType(type);
/*     */     
/* 175 */     return getCachedLightFor(type, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   public void func_150809_p() {
/* 185 */     this.field_76646_k = true;
/*     */     
/* 187 */     LightingHooks.checkChunkLighting((Chunk)this, this.field_76637_e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Overwrite
/*     */   private void func_150803_c(boolean onlyOne) {
/* 197 */     this.field_76637_e.field_72984_F.func_76320_a("recheckGaps");
/*     */     
/* 199 */     WorldChunkSlice slice = new WorldChunkSlice(this.field_76637_e, this.field_76635_g, this.field_76647_h);
/*     */     
/* 201 */     if (this.field_76637_e.func_175697_a(new BlockPos(this.field_76635_g * 16 + 8, 0, this.field_76647_h * 16 + 8), 16)) {
/* 202 */       for (int x = 0; x < 16; x++) {
/* 203 */         for (int z = 0; z < 16; z++) {
/* 204 */           if (recheckGapsForColumn(slice, x, z) && 
/* 205 */             onlyOne) {
/* 206 */             this.field_76637_e.field_72984_F.func_76319_b();
/*     */ 
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 214 */       this.field_76650_s = false;
/*     */     } 
/*     */     
/* 217 */     this.field_76637_e.field_72984_F.func_76319_b();
/*     */   }
/*     */   
/*     */   private boolean recheckGapsForColumn(WorldChunkSlice slice, int x, int z) {
/* 221 */     int i = x + z * 16;
/*     */     
/* 223 */     if (this.field_76639_c[i]) {
/* 224 */       this.field_76639_c[i] = false;
/*     */       
/* 226 */       int height = func_76611_b(x, z);
/*     */       
/* 228 */       int x1 = this.field_76635_g * 16 + x;
/* 229 */       int z1 = this.field_76647_h * 16 + z;
/*     */       
/* 231 */       int max = recheckGapsGetLowestHeight(slice, x1, z1);
/*     */       
/* 233 */       recheckGapsSkylightNeighborHeight(slice, x1, z1, height, max);
/*     */       
/* 235 */       return true;
/*     */     } 
/*     */     
/* 238 */     return false;
/*     */   }
/*     */   
/*     */   private int recheckGapsGetLowestHeight(WorldChunkSlice slice, int x, int z) {
/* 242 */     int max = Integer.MAX_VALUE;
/*     */     
/* 244 */     for (EnumFacing facing : HORIZONTAL) {
/* 245 */       int j = x + facing.func_82601_c();
/* 246 */       int k = z + facing.func_82599_e();
/*     */       
/* 248 */       max = Math.min(max, slice.getChunkFromWorldCoords(j, k).func_177442_v());
/*     */     } 
/*     */     
/* 251 */     return max;
/*     */   }
/*     */   
/*     */   private void recheckGapsSkylightNeighborHeight(WorldChunkSlice slice, int x, int z, int height, int max) {
/* 255 */     checkSkylightNeighborHeight(slice, x, z, max);
/*     */     
/* 257 */     for (EnumFacing facing : HORIZONTAL) {
/* 258 */       int j = x + facing.func_82601_c();
/* 259 */       int k = z + facing.func_82599_e();
/*     */       
/* 261 */       checkSkylightNeighborHeight(slice, j, k, height);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkSkylightNeighborHeight(WorldChunkSlice slice, int x, int z, int maxValue) {
/* 266 */     int i = slice.getChunkFromWorldCoords(x, z).func_76611_b(x & 0xF, z & 0xF);
/*     */     
/* 268 */     if (i > maxValue) {
/* 269 */       updateSkylightNeighborHeight(slice, x, z, maxValue, i + 1);
/* 270 */     } else if (i < maxValue) {
/* 271 */       updateSkylightNeighborHeight(slice, x, z, i, maxValue + 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSkylightNeighborHeight(WorldChunkSlice slice, int x, int z, int startY, int endY) {
/* 276 */     if (endY > startY) {
/* 277 */       if (!slice.isLoaded(x, z, 16)) {
/*     */         return;
/*     */       }
/*     */       
/* 281 */       for (int i = startY; i < endY; i++) {
/* 282 */         this.field_76637_e.func_180500_c(EnumSkyBlock.SKY, new BlockPos(x, i, z));
/*     */       }
/*     */       
/* 285 */       this.field_76643_l = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   public abstract int func_76611_b(int paramInt1, int paramInt2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public short[] getNeighborLightChecks() { return this.neighborLightChecks; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 307 */   public void setNeighborLightChecks(short[] data) { this.neighborLightChecks = data; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 312 */   public ILightingEngine getLightingEngine() { return this.lightingEngine; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public boolean isLightInitialized() { return this.isLightInitialized; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 322 */   public void setLightInitialized(boolean lightInitialized) { this.isLightInitialized = lightInitialized; }
/*     */ 
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   protected abstract void func_177441_y();
/*     */ 
/*     */   
/* 330 */   public void setSkylightUpdatedPublic() { func_177441_y(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCachedLightFor(EnumSkyBlock type, BlockPos pos) {
/* 335 */     int i = pos.func_177958_n() & 0xF;
/* 336 */     int j = pos.func_177956_o();
/* 337 */     int k = pos.func_177952_p() & 0xF;
/*     */     
/* 339 */     ExtendedBlockStorage extendedblockstorage = this.field_76652_q[j >> 4];
/*     */     
/* 341 */     if (extendedblockstorage == Chunk.field_186036_a) {
/* 342 */       if (func_177444_d(pos)) {
/* 343 */         return type.field_77198_c;
/*     */       }
/* 345 */       return 0;
/*     */     } 
/* 347 */     if (type == EnumSkyBlock.SKY) {
/* 348 */       if (!this.field_76637_e.field_73011_w.func_191066_m()) {
/* 349 */         return 0;
/*     */       }
/* 351 */       return extendedblockstorage.func_76670_c(i, j & 0xF, k);
/*     */     } 
/*     */     
/* 354 */     if (type == EnumSkyBlock.BLOCK) {
/* 355 */       return extendedblockstorage.func_76674_d(i, j & 0xF, k);
/*     */     }
/* 357 */     return type.field_77198_c;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */