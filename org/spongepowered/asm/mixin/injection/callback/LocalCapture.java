/*    */ package org.spongepowered.asm.mixin.injection.callback;
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
/*    */ public static enum LocalCapture
/*    */ {
/* 44 */   NO_CAPTURE(false, false),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   PRINT(false, true),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 56 */   CAPTURE_FAILSOFT,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   CAPTURE_FAILHARD,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   CAPTURE_FAILEXCEPTION;
/*    */ 
/*    */   
/*    */   private final boolean captureLocals;
/*    */ 
/*    */   
/*    */   private final boolean printLocals;
/*    */ 
/*    */ 
/*    */   
/*    */   LocalCapture(boolean captureLocals, boolean printLocals) {
/* 80 */     this.captureLocals = captureLocals;
/* 81 */     this.printLocals = printLocals;
/*    */   }
/*    */ 
/*    */   
/* 85 */   boolean isCaptureLocals() { return this.captureLocals; }
/*    */ 
/*    */ 
/*    */   
/* 89 */   boolean isPrintLocals() { return this.printLocals; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\callback\LocalCapture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */