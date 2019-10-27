/*    */ package me.jellysquid.mods.phosphor.mixins.lighting.common;
/*    */ 
/*    */ import net.minecraft.world.chunk.NibbleArray;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Overwrite;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({net.minecraft.world.chunk.storage.ExtendedBlockStorage.class})
/*    */ public class MixinExtendedBlockStorage
/*    */ {
/*    */   @Shadow
/*    */   private NibbleArray field_76685_h;
/*    */   @Shadow
/*    */   private int field_76682_b;
/*    */   @Shadow
/*    */   private NibbleArray field_76679_g;
/* 20 */   private int lightRefCount = -1;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public void func_76657_c(int x, int y, int z, int value) {
/* 28 */     this.field_76685_h.func_76581_a(x, y, z, value);
/* 29 */     this.lightRefCount = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public void func_76677_d(int x, int y, int z, int value) {
/* 38 */     this.field_76679_g.func_76581_a(x, y, z, value);
/* 39 */     this.lightRefCount = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public void func_76659_c(NibbleArray array) {
/* 48 */     this.field_76679_g = array;
/* 49 */     this.lightRefCount = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public void func_76666_d(NibbleArray array) {
/* 58 */     this.field_76685_h = array;
/* 59 */     this.lightRefCount = -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Overwrite
/*    */   public boolean func_76663_a() {
/* 69 */     if (this.field_76682_b != 0) {
/* 70 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 74 */     if (this.lightRefCount == -1) {
/* 75 */       if (checkLightArrayEqual(this.field_76685_h, (byte)-1) && 
/* 76 */         checkLightArrayEqual(this.field_76679_g, (byte)0)) {
/* 77 */         this.lightRefCount = 0;
/*    */       } else {
/* 79 */         this.lightRefCount = 1;
/*    */       } 
/*    */     }
/*    */     
/* 83 */     return (this.lightRefCount == 0);
/*    */   }
/*    */   
/*    */   private boolean checkLightArrayEqual(NibbleArray storage, byte val) {
/* 87 */     if (storage == null) {
/* 88 */       return true;
/*    */     }
/*    */     
/* 91 */     byte[] arr = storage.func_177481_a();
/*    */     
/* 93 */     for (byte b : arr) {
/* 94 */       if (b != val) {
/* 95 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\lighting\common\MixinExtendedBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */