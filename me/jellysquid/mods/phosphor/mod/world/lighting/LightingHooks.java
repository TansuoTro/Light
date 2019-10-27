/*     */ package me.jellysquid.mods.phosphor.mod.world.lighting;
/*     */ 
/*     */ import me.jellysquid.mods.phosphor.api.IChunkLighting;
/*     */ import me.jellysquid.mods.phosphor.api.IChunkLightingData;
/*     */ import me.jellysquid.mods.phosphor.api.ILightingEngine;
/*     */ import me.jellysquid.mods.phosphor.api.ILightingEngineProvider;
/*     */ import me.jellysquid.mods.phosphor.mod.PhosphorMod;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ public class LightingHooks
/*     */ {
/*  21 */   private static final EnumSkyBlock[] ENUM_SKY_BLOCK_VALUES = EnumSkyBlock.values();
/*     */   
/*  23 */   private static final EnumFacing.AxisDirection[] ENUM_AXIS_DIRECTION_VALUES = EnumFacing.AxisDirection.values();
/*     */   private static final int FLAG_COUNT = 32;
/*     */   public static final String neighborLightChecksKey = "NeighborLightChecks";
/*     */   
/*     */   public static void relightSkylightColumn(World world, Chunk chunk, int x, int z, int height1, int height2) {
/*  28 */     int yMin = Math.min(height1, height2);
/*  29 */     int yMax = Math.max(height1, height2) - 1;
/*     */     
/*  31 */     ExtendedBlockStorage[] sections = chunk.func_76587_i();
/*     */     
/*  33 */     int xBase = (chunk.field_76635_g << 4) + x;
/*  34 */     int zBase = (chunk.field_76647_h << 4) + z;
/*     */     
/*  36 */     scheduleRelightChecksForColumn(world, EnumSkyBlock.SKY, xBase, zBase, yMin, yMax);
/*     */     
/*  38 */     if (sections[yMin >> 4] == Chunk.field_186036_a && yMin > 0) {
/*  39 */       world.func_180500_c(EnumSkyBlock.SKY, new BlockPos(xBase, yMin - 1, zBase));
/*     */     }
/*     */     
/*  42 */     short emptySections = 0;
/*     */     
/*  44 */     for (int sec = yMax >> 4; sec >= yMin >> 4; sec--) {
/*  45 */       if (sections[sec] == Chunk.field_186036_a) {
/*  46 */         emptySections = (short)(emptySections | 1 << sec);
/*     */       }
/*     */     } 
/*     */     
/*  50 */     if (emptySections != 0) {
/*  51 */       for (EnumFacing dir : EnumFacing.field_176754_o) {
/*  52 */         int xOffset = dir.func_82601_c();
/*  53 */         int zOffset = dir.func_82599_e();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  58 */         boolean neighborColumnExists = (((x + xOffset | z + zOffset) & 0x10) == 0 || world.func_72863_F().func_186026_b(chunk.field_76635_g + xOffset, chunk.field_76647_h + zOffset) != null);
/*     */         
/*  60 */         if (neighborColumnExists) {
/*  61 */           for (int sec = yMax >> 4; sec >= yMin >> 4; sec--) {
/*  62 */             if ((emptySections & 1 << sec) != 0) {
/*  63 */               scheduleRelightChecksForColumn(world, EnumSkyBlock.SKY, xBase + xOffset, zBase + zOffset, sec << 4, (sec << 4) + 15);
/*     */             }
/*     */           } 
/*     */         } else {
/*  67 */           flagChunkBoundaryForUpdate(chunk, emptySections, EnumSkyBlock.SKY, dir, getAxisDirection(dir, x, z), EnumBoundaryFacing.OUT);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void scheduleRelightChecksForArea(World world, EnumSkyBlock lightType, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
/*  75 */     for (int x = xMin; x <= xMax; x++) {
/*  76 */       for (int z = zMin; z <= zMax; z++) {
/*  77 */         scheduleRelightChecksForColumn(world, lightType, x, z, yMin, yMax);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void scheduleRelightChecksForColumn(World world, EnumSkyBlock lightType, int x, int z, int yMin, int yMax) {
/*  83 */     BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*     */     
/*  85 */     for (int y = yMin; y <= yMax; y++)
/*  86 */       world.func_180500_c(lightType, pos.func_181079_c(x, y, z)); 
/*     */   }
/*     */   
/*     */   public enum EnumBoundaryFacing
/*     */   {
/*  91 */     IN, OUT;
/*     */ 
/*     */     
/*  94 */     public EnumBoundaryFacing getOpposite() { return (this == IN) ? OUT : IN; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static void flagSecBoundaryForUpdate(Chunk chunk, BlockPos pos, EnumSkyBlock lightType, EnumFacing dir, EnumBoundaryFacing boundaryFacing) { flagChunkBoundaryForUpdate(chunk, (short)(1 << pos.func_177956_o() >> 4), lightType, dir, getAxisDirection(dir, pos.func_177958_n(), pos.func_177952_p()), boundaryFacing); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void flagChunkBoundaryForUpdate(Chunk chunk, short sectionMask, EnumSkyBlock lightType, EnumFacing dir, EnumFacing.AxisDirection axisDirection, EnumBoundaryFacing boundaryFacing) {
/* 105 */     initNeighborLightChecks(chunk);
/* 106 */     ((IChunkLightingData)chunk).getNeighborLightChecks()[getFlagIndex(lightType, dir, axisDirection, boundaryFacing)] = (short)(((IChunkLightingData)chunk).getNeighborLightChecks()[getFlagIndex(lightType, dir, axisDirection, boundaryFacing)] | sectionMask);
/* 107 */     chunk.func_76630_e();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 112 */   public static int getFlagIndex(EnumSkyBlock lightType, int xOffset, int zOffset, EnumFacing.AxisDirection axisDirection, EnumBoundaryFacing boundaryFacing) { return ((lightType == EnumSkyBlock.BLOCK) ? 0 : 16) | xOffset + 1 << 2 | zOffset + 1 << 1 | axisDirection.func_179524_a() + 1 | boundaryFacing
/* 113 */       .ordinal(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public static int getFlagIndex(EnumSkyBlock lightType, EnumFacing dir, EnumFacing.AxisDirection axisDirection, EnumBoundaryFacing boundaryFacing) { return getFlagIndex(lightType, dir.func_82601_c(), dir.func_82599_e(), axisDirection, boundaryFacing); }
/*     */ 
/*     */ 
/*     */   
/* 122 */   private static EnumFacing.AxisDirection getAxisDirection(EnumFacing dir, int x, int z) { return ((((dir.func_176740_k() == EnumFacing.Axis.X) ? z : x) & 0xF) < 8) ? EnumFacing.AxisDirection.NEGATIVE : EnumFacing.AxisDirection.POSITIVE; }
/*     */ 
/*     */   
/*     */   public static void scheduleRelightChecksForChunkBoundaries(World world, Chunk chunk) {
/* 126 */     for (EnumFacing dir : EnumFacing.field_176754_o) {
/* 127 */       int xOffset = dir.func_82601_c();
/* 128 */       int zOffset = dir.func_82599_e();
/*     */       
/* 130 */       Chunk nChunk = world.func_72863_F().func_186026_b(chunk.field_76635_g + xOffset, chunk.field_76647_h + zOffset);
/*     */       
/* 132 */       if (nChunk != null)
/*     */       {
/*     */ 
/*     */         
/* 136 */         for (EnumSkyBlock lightType : ENUM_SKY_BLOCK_VALUES) {
/* 137 */           for (EnumFacing.AxisDirection axisDir : ENUM_AXIS_DIRECTION_VALUES) {
/*     */             
/* 139 */             mergeFlags(lightType, chunk, nChunk, dir, axisDir);
/* 140 */             mergeFlags(lightType, nChunk, chunk, dir.func_176734_d(), axisDir);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 145 */             scheduleRelightChecksForBoundary(world, chunk, nChunk, null, lightType, xOffset, zOffset, axisDir);
/* 146 */             scheduleRelightChecksForBoundary(world, nChunk, chunk, null, lightType, -xOffset, -zOffset, axisDir);
/*     */             
/* 148 */             scheduleRelightChecksForBoundary(world, nChunk, null, chunk, lightType, (zOffset != 0) ? axisDir.func_179524_a() : 0, (xOffset != 0) ? axisDir
/* 149 */                 .func_179524_a() : 0, (dir.func_176743_c() == EnumFacing.AxisDirection.POSITIVE) ? EnumFacing.AxisDirection.NEGATIVE : EnumFacing.AxisDirection.POSITIVE);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mergeFlags(EnumSkyBlock lightType, Chunk inChunk, Chunk outChunk, EnumFacing dir, EnumFacing.AxisDirection axisDir) {
/* 159 */     IChunkLightingData outChunkLightingData = (IChunkLightingData)outChunk;
/*     */     
/* 161 */     if (outChunkLightingData.getNeighborLightChecks() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     IChunkLightingData inChunkLightingData = (IChunkLightingData)inChunk;
/*     */     
/* 167 */     initNeighborLightChecks(inChunk);
/*     */     
/* 169 */     int inIndex = getFlagIndex(lightType, dir, axisDir, EnumBoundaryFacing.IN);
/* 170 */     int outIndex = getFlagIndex(lightType, dir.func_176734_d(), axisDir, EnumBoundaryFacing.OUT);
/*     */     
/* 172 */     inChunkLightingData.getNeighborLightChecks()[inIndex] = (short)(inChunkLightingData.getNeighborLightChecks()[inIndex] | outChunkLightingData.getNeighborLightChecks()[outIndex]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void scheduleRelightChecksForBoundary(World world, Chunk chunk, Chunk nChunk, Chunk sChunk, EnumSkyBlock lightType, int xOffset, int zOffset, EnumFacing.AxisDirection axisDir) {
/* 178 */     IChunkLightingData chunkLightingData = (IChunkLightingData)chunk;
/*     */     
/* 180 */     if (chunkLightingData.getNeighborLightChecks() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 184 */     int flagIndex = getFlagIndex(lightType, xOffset, zOffset, axisDir, EnumBoundaryFacing.IN);
/*     */     
/* 186 */     int flags = chunkLightingData.getNeighborLightChecks()[flagIndex];
/*     */     
/* 188 */     if (flags == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     if (nChunk == null) {
/* 193 */       nChunk = world.func_72863_F().func_186026_b(chunk.field_76635_g + xOffset, chunk.field_76647_h + zOffset);
/*     */       
/* 195 */       if (nChunk == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if (sChunk == null) {
/*     */       
/* 202 */       sChunk = world.func_72863_F().func_186026_b(chunk.field_76635_g + ((zOffset != 0) ? axisDir.func_179524_a() : 0), chunk.field_76647_h + ((xOffset != 0) ? axisDir.func_179524_a() : 0));
/*     */       
/* 204 */       if (sChunk == null) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 209 */     int reverseIndex = getFlagIndex(lightType, -xOffset, -zOffset, axisDir, EnumBoundaryFacing.OUT);
/*     */     
/* 211 */     chunkLightingData.getNeighborLightChecks()[flagIndex] = 0;
/*     */     
/* 213 */     IChunkLightingData nChunkLightingData = (IChunkLightingData)nChunk;
/*     */     
/* 215 */     if (nChunkLightingData.getNeighborLightChecks() != null) {
/* 216 */       nChunkLightingData.getNeighborLightChecks()[reverseIndex] = 0;
/*     */     }
/*     */     
/* 219 */     chunk.func_76630_e();
/* 220 */     nChunk.func_76630_e();
/*     */ 
/*     */ 
/*     */     
/* 224 */     int xMin = chunk.field_76635_g << 4;
/* 225 */     int zMin = chunk.field_76647_h << 4;
/*     */ 
/*     */     
/* 228 */     if ((xOffset | zOffset) > 0) {
/* 229 */       xMin += 15 * xOffset;
/* 230 */       zMin += 15 * zOffset;
/*     */     } 
/*     */ 
/*     */     
/* 234 */     if (axisDir == EnumFacing.AxisDirection.POSITIVE) {
/* 235 */       xMin += 8 * (zOffset & true);
/* 236 */       zMin += 8 * (xOffset & true);
/*     */     } 
/*     */ 
/*     */     
/* 240 */     int xMax = xMin + 7 * (zOffset & true);
/* 241 */     int zMax = zMin + 7 * (xOffset & true);
/*     */     
/* 243 */     for (int y = 0; y < 16; y++) {
/* 244 */       if ((flags & 1 << y) != 0) {
/* 245 */         scheduleRelightChecksForArea(world, lightType, xMin, y << 4, zMin, xMax, (y << 4) + 15, zMax);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void initNeighborLightChecks(Chunk chunk) {
/* 251 */     IChunkLightingData lightingData = (IChunkLightingData)chunk;
/*     */     
/* 253 */     if (lightingData.getNeighborLightChecks() == null) {
/* 254 */       lightingData.setNeighborLightChecks(new short[32]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeNeighborLightChecksToNBT(Chunk chunk, NBTTagCompound nbt) {
/* 261 */     short[] neighborLightChecks = ((IChunkLightingData)chunk).getNeighborLightChecks();
/*     */     
/* 263 */     if (neighborLightChecks == null) {
/*     */       return;
/*     */     }
/*     */     
/* 267 */     boolean empty = true;
/*     */     
/* 269 */     NBTTagList list = new NBTTagList();
/*     */     
/* 271 */     for (short flags : neighborLightChecks) {
/* 272 */       list.func_74742_a(new NBTTagShort(flags));
/*     */       
/* 274 */       if (flags != 0) {
/* 275 */         empty = false;
/*     */       }
/*     */     } 
/*     */     
/* 279 */     if (!empty) {
/* 280 */       nbt.func_74782_a("NeighborLightChecks", list);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void readNeighborLightChecksFromNBT(Chunk chunk, NBTTagCompound nbt) {
/* 285 */     if (nbt.func_150297_b("NeighborLightChecks", 9)) {
/* 286 */       NBTTagList list = nbt.func_150295_c("NeighborLightChecks", 2);
/*     */       
/* 288 */       if (list.func_74745_c() == 32) {
/* 289 */         initNeighborLightChecks(chunk);
/*     */         
/* 291 */         short[] neighborLightChecks = ((IChunkLightingData)chunk).getNeighborLightChecks();
/*     */         
/* 293 */         for (int i = 0; i < 32; i++) {
/* 294 */           neighborLightChecks[i] = ((NBTTagShort)list.func_179238_g(i)).func_150289_e();
/*     */         }
/*     */       } else {
/* 297 */         PhosphorMod.LOGGER.warn("Chunk field {} had invalid length, ignoring it (chunk coordinates: {} {})", "NeighborLightChecks", Integer.valueOf(chunk.field_76635_g), Integer.valueOf(chunk.field_76647_h));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void initChunkLighting(Chunk chunk, World world) {
/* 303 */     int xBase = chunk.field_76635_g << 4;
/* 304 */     int zBase = chunk.field_76647_h << 4;
/*     */     
/* 306 */     BlockPos.PooledMutableBlockPos pos = BlockPos.PooledMutableBlockPos.func_185339_c(xBase, 0, zBase);
/*     */     
/* 308 */     if (world.func_175706_a(pos.func_177982_a(-16, 0, -16), pos.func_177982_a(31, 255, 31), false)) {
/* 309 */       ExtendedBlockStorage[] extendedBlockStorage = chunk.func_76587_i();
/*     */       
/* 311 */       for (int j = 0; j < extendedBlockStorage.length; j++) {
/* 312 */         ExtendedBlockStorage storage = extendedBlockStorage[j];
/*     */         
/* 314 */         if (storage != Chunk.field_186036_a) {
/*     */ 
/*     */ 
/*     */           
/* 318 */           int yBase = j * 16;
/*     */           
/* 320 */           for (int y = 0; y < 16; y++) {
/* 321 */             for (int z = 0; z < 16; z++) {
/* 322 */               for (int x = 0; x < 16; x++) {
/* 323 */                 int key = storage.field_177488_d.field_186021_b.func_188142_a(y << 8 | z << 4 | x);
/*     */                 
/* 325 */                 if (key != 0) {
/* 326 */                   IBlockState state = storage.field_177488_d.field_186022_c.func_186039_a(key);
/*     */                   
/* 328 */                   if (state != null) {
/* 329 */                     int light = state.getLightValue(world, pos);
/*     */                     
/* 331 */                     if (light > 0) {
/* 332 */                       pos.func_181079_c(xBase + x, yBase + y, zBase + z);
/*     */                       
/* 334 */                       world.func_180500_c(EnumSkyBlock.BLOCK, pos);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 343 */       if (world.field_73011_w.func_191066_m()) {
/* 344 */         ((IChunkLightingData)chunk).setSkylightUpdatedPublic();
/*     */       }
/*     */       
/* 347 */       ((IChunkLightingData)chunk).setLightInitialized(true);
/*     */     } 
/*     */     
/* 350 */     pos.func_185344_t();
/*     */   }
/*     */   
/*     */   public static void checkChunkLighting(Chunk chunk, World world) {
/* 354 */     if (!((IChunkLightingData)chunk).isLightInitialized()) {
/* 355 */       initChunkLighting(chunk, world);
/*     */     }
/*     */     
/* 358 */     for (int x = -1; x <= 1; x++) {
/* 359 */       for (int z = -1; z <= 1; z++) {
/* 360 */         if (x != 0 || z != 0) {
/* 361 */           Chunk nChunk = world.func_72863_F().func_186026_b(chunk.field_76635_g + x, chunk.field_76647_h + z);
/*     */           
/* 363 */           if (nChunk == null || !((IChunkLightingData)nChunk).isLightInitialized()) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 370 */     chunk.func_177421_e(true);
/*     */   }
/*     */   
/*     */   public static void initSkylightForSection(World world, Chunk chunk, ExtendedBlockStorage section) {
/* 374 */     if (world.field_73011_w.func_191066_m()) {
/* 375 */       for (int x = 0; x < 16; x++) {
/* 376 */         for (int z = 0; z < 16; z++) {
/* 377 */           if (chunk.func_76611_b(x, z) <= section.func_76662_d()) {
/* 378 */             for (int y = 0; y < 16; y++) {
/* 379 */               section.func_76657_c(x, y, z, EnumSkyBlock.SKY.field_77198_c);
/*     */             }
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 388 */   private static short[] getNeighborLightChecks(Chunk chunk) { return ((IChunkLightingData)chunk).getNeighborLightChecks(); }
/*     */ 
/*     */ 
/*     */   
/* 392 */   private static void setNeighborLightChecks(Chunk chunk, short[] table) { ((IChunkLightingData)chunk).setNeighborLightChecks(table); }
/*     */ 
/*     */ 
/*     */   
/* 396 */   public static int getCachedLightFor(Chunk chunk, EnumSkyBlock type, BlockPos pos) { return ((IChunkLighting)chunk).getCachedLightFor(type, pos); }
/*     */ 
/*     */ 
/*     */   
/* 400 */   public static ILightingEngine getLightingEngine(World world) { return ((ILightingEngineProvider)world).getLightingEngine(); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\world\lighting\LightingHooks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */