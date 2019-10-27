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
/*    */ public class CancellationException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public CancellationException() {}
/*    */   
/* 39 */   public CancellationException(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public CancellationException(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public CancellationException(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\callback\CancellationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */