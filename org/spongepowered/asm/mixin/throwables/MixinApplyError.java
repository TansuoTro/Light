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
/*    */ public class MixinApplyError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 35 */   public MixinApplyError(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public MixinApplyError(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public MixinApplyError(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\throwables\MixinApplyError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */