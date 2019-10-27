/*    */ package org.spongepowered.asm.mixin.throwables;
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
/*    */ public class ClassAlreadyLoadedException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 39 */   public ClassAlreadyLoadedException(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public ClassAlreadyLoadedException(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public ClassAlreadyLoadedException(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\throwables\ClassAlreadyLoadedException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */