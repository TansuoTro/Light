/*     */ package me.jellysquid.mods.phosphor.mod.world.lighting;
/*     */ 
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import me.jellysquid.mods.phosphor.api.IChunkLighting;
/*     */ import me.jellysquid.mods.phosphor.api.ILightingEngine;
/*     */ import me.jellysquid.mods.phosphor.mixins.plugins.LightingEnginePlugin;
/*     */ import me.jellysquid.mods.phosphor.mod.PhosphorMod;
/*     */ import me.jellysquid.mods.phosphor.mod.collections.PooledLongQueue;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import net.minecraftforge.fml.relauncher.Side;
/*     */ import net.minecraftforge.fml.relauncher.SideOnly;
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
/*     */ public class LightingEngine
/*     */   implements ILightingEngine
/*     */ {
/*     */   private static final int MAX_SCHEDULED_COUNT = 4194304;
/*     */   private static final int MAX_LIGHT = 15;
/*     */   private final Thread ownedThread;
/*     */   private final World world;
/*     */   private final Profiler profiler;
/*     */   private final PooledLongQueue[] queuedLightUpdates;
/*     */   private final PooledLongQueue[] queuedDarkenings;
/*     */   private final PooledLongQueue[] queuedBrightenings;
/*     */   private final PooledLongQueue initialBrightenings;
/*     */   private final PooledLongQueue initialDarkenings;
/*     */   private boolean updating;
/*     */   private static final int lX = 26;
/*     */   private static final int lY = 8;
/*     */   private static final int lZ = 26;
/*     */   private static final int lL = 4;
/*     */   private static final int sZ = 0;
/*     */   private static final int sX = 26;
/*     */   private static final int sY = 52;
/*     */   private static final int sL = 60;
/*     */   private static final long mX = 67108863L;
/*     */   private static final long mY = 255L;
/*     */   private static final long mZ = 67108863L;
/*     */   private static final long mL = 15L;
/*     */   private static final long mPos = 1152921504606846975L;
/*     */   private static final long yCheck = 1152921504606846976L;
/*  76 */   private static final long[] neighborShifts = new long[6];
/*     */   private static final long mChunk = 4503598620737520L;
/*     */   private final BlockPos.MutableBlockPos curPos;
/*  79 */   private Chunk curChunk; private long curChunkIdentifier; private long curData; private boolean isNeighborDataValid; private final NeighborInfo[] neighborInfos; private PooledLongQueue.LongQueueIterator queueIt; private final ReentrantLock lock; private static int ITEMS_PROCESSED; private static int CHUNKS_FETCHED; public LightingEngine(World world) { this.ownedThread = Thread.currentThread(); this.queuedLightUpdates = new PooledLongQueue[EnumSkyBlock.values().length]; this.queuedDarkenings = new PooledLongQueue[16]; this.queuedBrightenings = new PooledLongQueue[16]; this.updating = false; this.curPos = new BlockPos.MutableBlockPos(); this.isNeighborDataValid = false; this.neighborInfos = new NeighborInfo[6]; this.lock = new ReentrantLock(); this.world = world; this.profiler = world.field_72984_F; PooledLongQueue.Pool pool = new PooledLongQueue.Pool(); this.initialBrightenings = new PooledLongQueue(pool); this.initialDarkenings = new PooledLongQueue(pool); for (int i = 0; i < EnumSkyBlock.values().length; i++) this.queuedLightUpdates[i] = new PooledLongQueue(pool);  for (int i = 0; i < this.queuedDarkenings.length; i++) this.queuedDarkenings[i] = new PooledLongQueue(pool);  for (int i = 0; i < this.queuedBrightenings.length; i++) this.queuedBrightenings[i] = new PooledLongQueue(pool);  for (int i = 0; i < this.neighborInfos.length; i++) this.neighborInfos[i] = new NeighborInfo(null);  } public void scheduleLightUpdate(EnumSkyBlock lightType, BlockPos pos) { acquireLock(); try { scheduleLightUpdate(lightType, encodeWorldCoord(pos)); } finally { releaseLock(); }  } private void scheduleLightUpdate(EnumSkyBlock lightType, long pos) { PooledLongQueue queue = this.queuedLightUpdates[lightType.ordinal()]; queue.add(pos); if (queue.size() >= 4194304) processLightUpdatesForType(lightType);  } public void processLightUpdates() { processLightUpdatesForType(EnumSkyBlock.SKY); processLightUpdatesForType(EnumSkyBlock.BLOCK); } public void processLightUpdatesForType(EnumSkyBlock lightType) { if (this.world.field_72995_K && !isCallingFromMainThread()) return;  PooledLongQueue queue = this.queuedLightUpdates[lightType.ordinal()]; if (queue.isEmpty()) return;  acquireLock(); try { processLightUpdatesForTypeInner(lightType, queue); } finally { releaseLock(); }  } static  { for (i = 0; i < 6; i++) {
/*  80 */       Vec3i offset = EnumFacing.field_82609_l[i].func_176730_m();
/*  81 */       neighborShifts[i] = offset.func_177956_o() << 52 | offset.func_177958_n() << 26 | offset.func_177952_p() << false;
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 542 */     ITEMS_PROCESSED = 0; CHUNKS_FETCHED = 0; }
/*     */   @SideOnly(Side.CLIENT) private boolean isCallingFromMainThread() { return Minecraft.func_71410_x().func_152345_ab(); }
/*     */   private void acquireLock() { if (!this.lock.tryLock()) { if (LightingEnginePlugin.ENABLE_ILLEGAL_THREAD_ACCESS_WARNINGS) { Thread current = Thread.currentThread(); if (current != this.ownedThread) { IllegalAccessException e = new IllegalAccessException(String.format("World is owned by '%s' (ID: %s), but was accessed from thread '%s' (ID: %s)", new Object[] { this.ownedThread.getName(), Long.valueOf(this.ownedThread.getId()), current.getName(), Long.valueOf(current.getId()) })); PhosphorMod.LOGGER.warn("Something (likely another mod) has attempted to modify the world's state from the wrong thread!\nThis is *bad practice* and can cause severe issues in your game. Phosphor has done as best as it can to mitigate this violation, but it may negatively impact performance or introduce stalls.\nIn a future release, this violation may result in a hard crash instead of the current soft warning. You should report this issue to our issue tracker with the following stacktrace information.\n(If you are aware you have misbehaving mods and cannot resolve this issue, you can safely disable this warning by setting `enable_illegal_thread_access_warnings` to `false` in Phosphor's configuration file for the time being.)", e); }  }  this.lock.lock(); }  }
/*     */   private void releaseLock() { this.lock.unlock(); } private void processLightUpdatesForTypeInner(EnumSkyBlock lightType, PooledLongQueue queue) { if (this.updating) throw new IllegalStateException("Already processing updates!");  this.updating = true; this.curChunkIdentifier = -1L; this.profiler.func_76320_a("lighting"); this.profiler.func_76320_a("checking"); this.queueIt = queue.iterator(); while (nextItem()) { if (this.curChunk == null) continue;  int oldLight = getCursorCachedLight(lightType); int newLight = calculateNewLightFromCursor(lightType); if (oldLight < newLight) { this.initialBrightenings.add(newLight << 60 | this.curData); continue; }  if (oldLight > newLight) this.initialDarkenings.add(this.curData);  }  this.queueIt = this.initialBrightenings.iterator(); while (nextItem()) { int newLight = (int)(this.curData >> 60 & 0xFL); if (newLight > getCursorCachedLight(lightType))
/*     */         enqueueBrightening(this.curPos, this.curData & 0xFFFFFFFFFFFFFFFL, newLight, this.curChunk, lightType);  }  this.queueIt = this.initialDarkenings.iterator(); while (nextItem()) { int oldLight = getCursorCachedLight(lightType); if (oldLight != 0)
/*     */         enqueueDarkening(this.curPos, this.curData, oldLight, this.curChunk, lightType);  }  this.profiler.func_76319_b(); for (int curLight = 15; curLight >= 0; curLight--) { this.profiler.func_76320_a("darkening"); this.queueIt = this.queuedDarkenings[curLight].iterator(); while (nextItem()) { int opacity; if (getCursorCachedLight(lightType) >= curLight)
/*     */           continue;  IBlockState state = LightingEngineHelpers.posToState(this.curPos, this.curChunk); int luminosity = getCursorLuminosity(state, lightType); if (luminosity >= 14) { opacity = 1; } else { opacity = getPosOpacity(this.curPos, state); }  if (calculateNewLightFromCursor(luminosity, opacity, lightType) < curLight) { int newLight = luminosity; fetchNeighborDataFromCursor(lightType); for (NeighborInfo info : this.neighborInfos) { Chunk nChunk = info.chunk; if (nChunk != null) { int nLight = info.light; if (nLight != 0) { BlockPos.MutableBlockPos nPos = info.pos; if (curLight - getPosOpacity(nPos, LightingEngineHelpers.posToState(nPos, info.section)) >= nLight) { enqueueDarkening(nPos, info.key, nLight, nChunk, lightType); } else { newLight = Math.max(newLight, nLight - opacity); }  }  }  }  enqueueBrighteningFromCursor(newLight, lightType); continue; }  enqueueBrighteningFromCursor(curLight, lightType); }  this.profiler.func_76318_c("brightening"); this.queueIt = this.queuedBrightenings[curLight].iterator(); while (nextItem()) { int oldLight = getCursorCachedLight(lightType); if (oldLight == curLight) { this.world.func_175679_n(this.curPos); if (curLight > 1)
/*     */             spreadLightFromCursor(curLight, lightType);  }  }  this.profiler.func_76319_b(); }  this.profiler.func_76319_b(); this.updating = false; } private void fetchNeighborDataFromCursor(EnumSkyBlock lightType) { if (this.isNeighborDataValid)
/* 550 */       return;  this.isNeighborDataValid = true; for (int i = 0; i < this.neighborInfos.length; i++) { NeighborInfo info = this.neighborInfos[i]; long nLongPos = info.key = this.curData + neighborShifts[i]; if ((nLongPos & 0x1000000000000000L) != 0L) { info.chunk = null; info.section = null; } else { Chunk nChunk; BlockPos.MutableBlockPos nPos = decodeWorldCoord(info.pos, nLongPos); if ((nLongPos & 0xFFFFFC3FFFFF0L) == this.curChunkIdentifier) { nChunk = info.chunk = this.curChunk; } else { nChunk = info.chunk = getChunk(nPos); }  if (nChunk != null) { ExtendedBlockStorage nSection = nChunk.func_76587_i()[nPos.func_177956_o() >> 4]; info.light = getCachedLightFor(nChunk, nSection, nPos, lightType); info.section = nSection; }  }  }  } private boolean nextItem() { if (!this.queueIt.hasNext()) {
/* 551 */       this.queueIt.finish();
/* 552 */       this.queueIt = null;
/*     */       
/* 554 */       return false;
/*     */     } 
/*     */     
/* 557 */     this.curData = this.queueIt.next();
/* 558 */     this.isNeighborDataValid = false;
/*     */     
/* 560 */     decodeWorldCoord(this.curPos, this.curData);
/*     */     
/* 562 */     long chunkIdentifier = this.curData & 0xFFFFFC3FFFFF0L;
/*     */     
/* 564 */     if (this.curChunkIdentifier != chunkIdentifier) {
/* 565 */       this.curChunk = getChunk(this.curPos);
/* 566 */       this.curChunkIdentifier = chunkIdentifier;
/* 567 */       CHUNKS_FETCHED++;
/*     */     } 
/*     */     
/* 570 */     ITEMS_PROCESSED++;
/*     */     
/* 572 */     return true; } private static int getCachedLightFor(Chunk chunk, ExtendedBlockStorage storage, BlockPos pos, EnumSkyBlock type) { int i = pos.func_177958_n() & 0xF; int j = pos.func_177956_o(); int k = pos.func_177952_p() & 0xF; if (storage == Chunk.field_186036_a) { if (type == EnumSkyBlock.SKY && chunk.func_177444_d(pos)) return type.field_77198_c;  return 0; }  if (type == EnumSkyBlock.SKY) { if (!(chunk.func_177412_p()).field_73011_w.func_191066_m()) return 0;  return storage.func_76670_c(i, j & 0xF, k); }  if (type == EnumSkyBlock.BLOCK) return storage.func_76674_d(i, j & 0xF, k);  return type.field_77198_c; } private int calculateNewLightFromCursor(EnumSkyBlock lightType) { int opacity; IBlockState state = LightingEngineHelpers.posToState(this.curPos, this.curChunk); int luminosity = getCursorLuminosity(state, lightType); if (luminosity >= 14) { opacity = 1; } else { opacity = getPosOpacity(this.curPos, state); }  return calculateNewLightFromCursor(luminosity, opacity, lightType); } private int calculateNewLightFromCursor(int luminosity, int opacity, EnumSkyBlock lightType) { if (luminosity >= 15 - opacity) return luminosity;  int newLight = luminosity; fetchNeighborDataFromCursor(lightType); for (NeighborInfo info : this.neighborInfos) { if (info.chunk != null) { int nLight = info.light; newLight = Math.max(nLight - opacity, newLight); }  }  return newLight; } private void spreadLightFromCursor(int curLight, EnumSkyBlock lightType) { fetchNeighborDataFromCursor(lightType); for (NeighborInfo info : this.neighborInfos) { Chunk nChunk = info.chunk; if (nChunk != null) { int newLight = curLight - getPosOpacity(info.pos, LightingEngineHelpers.posToState(info.pos, info.section)); if (newLight > info.light) enqueueBrightening(info.pos, info.key, newLight, nChunk, lightType);  }  }  } private void enqueueBrighteningFromCursor(int newLight, EnumSkyBlock lightType) { enqueueBrightening(this.curPos, this.curData, newLight, this.curChunk, lightType); } private void enqueueBrightening(BlockPos pos, long longPos, int newLight, Chunk chunk, EnumSkyBlock lightType) { this.queuedBrightenings[newLight].add(longPos); chunk.func_177431_a(lightType, pos, newLight); } private void enqueueDarkening(BlockPos pos, long longPos, int oldLight, Chunk chunk, EnumSkyBlock lightType) { this.queuedDarkenings[oldLight].add(longPos); chunk.func_177431_a(lightType, pos, 0); }
/*     */   private static BlockPos.MutableBlockPos decodeWorldCoord(BlockPos.MutableBlockPos pos, long longPos) { int posX = (int)(longPos >> 26 & 0x3FFFFFFL) - 33554432; int posY = (int)(longPos >> 52 & 0xFFL); int posZ = (int)(longPos >> false & 0x3FFFFFFL) - 33554432; return pos.func_181079_c(posX, posY, posZ); }
/*     */   private static long encodeWorldCoord(BlockPos pos) { return encodeWorldCoord(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p()); }
/*     */   private static long encodeWorldCoord(long x, long y, long z) { return y << 52 | x + 33554432L << 26 | z + 33554432L << false; }
/* 576 */   private int getCursorCachedLight(EnumSkyBlock lightType) { return ((IChunkLighting)this.curChunk).getCachedLightFor(lightType, this.curPos); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getCursorLuminosity(IBlockState state, EnumSkyBlock lightType) {
/* 583 */     if (lightType == EnumSkyBlock.SKY) {
/* 584 */       if (this.curChunk.func_177444_d(this.curPos)) {
/* 585 */         return EnumSkyBlock.SKY.field_77198_c;
/*     */       }
/* 587 */       return 0;
/*     */     } 
/*     */ 
/*     */     
/* 591 */     return MathHelper.func_76125_a(state.getLightValue(this.world, this.curPos), 0, 15);
/*     */   }
/*     */ 
/*     */   
/* 595 */   private int getPosOpacity(BlockPos pos, IBlockState state) { return MathHelper.func_76125_a(state.getLightOpacity(this.world, pos), 1, 15); }
/*     */ 
/*     */ 
/*     */   
/* 599 */   private Chunk getChunk(BlockPos pos) { return this.world.func_72863_F().func_186026_b(pos.func_177958_n() >> 4, pos.func_177952_p() >> 4); }
/*     */   
/*     */   private static class NeighborInfo
/*     */   {
/*     */     Chunk chunk;
/*     */     ExtendedBlockStorage section;
/*     */     int light;
/*     */     long key;
/*     */     
/*     */     private NeighborInfo() {}
/*     */     
/* 610 */     final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\world\lighting\LightingEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */