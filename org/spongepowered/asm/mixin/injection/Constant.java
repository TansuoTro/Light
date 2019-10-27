/*     */ package org.spongepowered.asm.mixin.injection;
/*     */ 
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Retention(RetentionPolicy.RUNTIME)
/*     */ public @interface Constant
/*     */ {
/*     */   boolean nullValue() default false;
/*     */   
/*     */   int intValue() default 0;
/*     */   
/*     */   float floatValue() default 0.0F;
/*     */   
/*     */   long longValue() default 0L;
/*     */   
/*     */   double doubleValue() default 0.0D;
/*     */   
/*     */   String stringValue() default "";
/*     */   
/*     */   Class<?> classValue() default Object.class;
/*     */   
/*     */   int ordinal() default -1;
/*     */   
/*     */   String slice() default "";
/*     */   
/*     */   Condition[] expandZeroConditions() default {};
/*     */   
/*     */   boolean log() default false;
/*     */   
/*     */   public enum Condition
/*     */   {
/*  62 */     LESS_THAN_ZERO(new int[] { 155, 156
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/*  69 */     LESS_THAN_OR_EQUAL_TO_ZERO(new int[] { 158, 157
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }),
/*  77 */     GREATER_THAN_OR_EQUAL_TO_ZERO(LESS_THAN_ZERO),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     GREATER_THAN_ZERO(LESS_THAN_OR_EQUAL_TO_ZERO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int[] opcodes;
/*     */ 
/*     */ 
/*     */     
/*     */     private final Condition equivalence;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Condition(Condition equivalence, int... opcodes) {
/* 100 */       this.equivalence = (equivalence != null) ? equivalence : this;
/* 101 */       this.opcodes = opcodes;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     public Condition getEquivalentCondition() { return this.equivalence; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     public int[] getOpcodes() { return this.opcodes; }
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\Constant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */