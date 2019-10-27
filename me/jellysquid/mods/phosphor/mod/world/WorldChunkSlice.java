/*    */ package me.jellysquid.mods.phosphor.mod.world;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldChunkSlice
/*    */ {
/*    */   private static final int DIAMETER = 5;
/* 14 */   private final Chunk[] chunks = new Chunk[25]; private final int x;
/*    */   public WorldChunkSlice(World world, int x, int z) {
/* 16 */     int radius = 2;
/*    */     
/* 18 */     for (int xDiff = -radius; xDiff <= radius; xDiff++) {
/* 19 */       for (int zDiff = -radius; zDiff <= radius; zDiff++) {
/* 20 */         this.chunks[(xDiff + radius) * 5 + zDiff + radius] = world.func_72863_F().func_186026_b(x + xDiff, z + zDiff);
/*    */       }
/*    */     } 
/*    */     
/* 24 */     this.x = x - radius;
/* 25 */     this.z = z - radius;
/*    */   }
/*    */   private final int z;
/*    */   
/* 29 */   public Chunk getChunk(int x, int z) { return this.chunks[x * 5 + z]; }
/*    */ 
/*    */ 
/*    */   
/* 33 */   public Chunk getChunkFromWorldCoords(int x, int z) { return getChunk((x >> 4) - this.x, (z >> 4) - this.z); }
/*    */ 
/*    */ 
/*    */   
/* 37 */   public boolean isLoaded(int x, int z, int radius) { return isLoaded(x - radius, z - radius, x + radius, z + radius); }
/*    */ 
/*    */   
/*    */   public boolean isLoaded(int xStart, int zStart, int xEnd, int zEnd) {
/* 41 */     xStart = (xStart >> 4) - this.x;
/* 42 */     zStart = (zStart >> 4) - this.z;
/* 43 */     xEnd = (xEnd >> 4) - this.x;
/* 44 */     zEnd = (zEnd >> 4) - this.z;
/*    */     
/* 46 */     for (int i = xStart; i <= xEnd; i++) {
/* 47 */       for (int j = zStart; j <= zEnd; j++) {
/* 48 */         if (getChunk(i, j) == null) {
/* 49 */           return false;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\world\WorldChunkSlice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */