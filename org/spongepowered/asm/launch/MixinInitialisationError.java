/*    */ package org.spongepowered.asm.launch;
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
/*    */ public class MixinInitialisationError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public MixinInitialisationError() {}
/*    */   
/* 38 */   public MixinInitialisationError(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public MixinInitialisationError(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public MixinInitialisationError(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\launch\MixinInitialisationError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */