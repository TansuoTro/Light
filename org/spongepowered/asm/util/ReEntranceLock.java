/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReEntranceLock
/*     */ {
/*     */   private final int maxDepth;
/*     */   private int depth;
/*     */   private boolean semaphore;
/*     */   
/*     */   public ReEntranceLock(int maxDepth) {
/*  40 */     this.depth = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  45 */     this.semaphore = false;
/*     */ 
/*     */     
/*  48 */     this.maxDepth = maxDepth;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public int getMaxDepth() { return this.maxDepth; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   public int getDepth() { return this.depth; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock push() {
/*  72 */     this.depth++;
/*  73 */     checkAndSet();
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock pop() {
/*  83 */     if (this.depth == 0) {
/*  84 */       throw new IllegalStateException("ReEntranceLock pop() with zero depth");
/*     */     }
/*     */     
/*  87 */     this.depth--;
/*  88 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   public boolean check() { return (this.depth > this.maxDepth); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   public boolean checkAndSet() { return this.semaphore |= check(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock set() {
/* 115 */     this.semaphore = true;
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public boolean isSet() { return this.semaphore; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReEntranceLock clear() {
/* 132 */     this.semaphore = false;
/* 133 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\as\\util\ReEntranceLock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */