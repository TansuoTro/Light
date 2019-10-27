/*    */ package org.spongepowered.asm.mixin;
/*    */ 
/*    */ import java.lang.annotation.ElementType;
/*    */ import java.lang.annotation.Retention;
/*    */ import java.lang.annotation.RetentionPolicy;
/*    */ import java.lang.annotation.Target;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Target({ElementType.TYPE})
/*    */ @Retention(RetentionPolicy.CLASS)
/*    */ public @interface Interface
/*    */ {
/*    */   Class<?> iface();
/*    */   
/*    */   String prefix();
/*    */   
/*    */   boolean unique() default false;
/*    */   
/*    */   Remap remap() default Remap.ALL;
/*    */   
/*    */   public enum Remap
/*    */   {
/* 49 */     ALL,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 57 */     FORCE(true),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     ONLY_PREFIXED,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 70 */     NONE;
/*    */ 
/*    */ 
/*    */     
/*    */     private final boolean forceRemap;
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 79 */     Remap(boolean forceRemap) { this.forceRemap = forceRemap; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 86 */     public boolean forceRemap() { return this.forceRemap; }
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\Interface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */