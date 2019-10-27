/*    */ package me.jellysquid.mods.phosphor.mod.world.lighting;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.chunk.Chunk;
/*    */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*    */ 
/*    */ public class LightingEngineHelpers {
/* 10 */   private static final IBlockState DEFAULT_BLOCK_STATE = Blocks.field_150350_a.func_176223_P();
/*    */ 
/*    */ 
/*    */   
/* 14 */   static IBlockState posToState(BlockPos pos, Chunk chunk) { return posToState(pos, chunk.func_76587_i()[pos.func_177956_o() >> 4]); }
/*    */ 
/*    */   
/*    */   static IBlockState posToState(BlockPos pos, ExtendedBlockStorage section) {
/* 18 */     int x = pos.func_177958_n();
/* 19 */     int y = pos.func_177956_o();
/* 20 */     int z = pos.func_177952_p();
/*    */     
/* 22 */     if (section != Chunk.field_186036_a) {
/*    */       
/* 24 */       int i = section.field_177488_d.field_186021_b.func_188142_a((y & 0xF) << 8 | (z & 0xF) << 4 | x & 0xF);
/*    */       
/* 26 */       if (i != 0) {
/* 27 */         IBlockState state = section.field_177488_d.field_186022_c.func_186039_a(i);
/*    */         
/* 29 */         if (state != null) {
/* 30 */           return state;
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 35 */     return DEFAULT_BLOCK_STATE;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\world\lighting\LightingEngineHelpers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */