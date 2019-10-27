/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.code.ISliceContext;
/*    */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
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
/*    */ public class InvalidSliceException
/*    */   extends InvalidInjectionException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 39 */   public InvalidSliceException(IMixinContext context, String message) { super(context, message); }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public InvalidSliceException(ISliceContext owner, String message) { super(owner.getContext(), message); }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public InvalidSliceException(IMixinContext context, Throwable cause) { super(context, cause); }
/*    */ 
/*    */ 
/*    */   
/* 51 */   public InvalidSliceException(ISliceContext owner, Throwable cause) { super(owner.getContext(), cause); }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public InvalidSliceException(IMixinContext context, String message, Throwable cause) { super(context, message, cause); }
/*    */ 
/*    */ 
/*    */   
/* 59 */   public InvalidSliceException(ISliceContext owner, String message, Throwable cause) { super(owner.getContext(), message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidSliceException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */