/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
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
/*    */ public class InvalidInterfaceMixinException
/*    */   extends InvalidMixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   
/* 38 */   public InvalidInterfaceMixinException(IMixinInfo mixin, String message) { super(mixin, message); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public InvalidInterfaceMixinException(IMixinContext context, String message) { super(context, message); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public InvalidInterfaceMixinException(IMixinInfo mixin, Throwable cause) { super(mixin, cause); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public InvalidInterfaceMixinException(IMixinContext context, Throwable cause) { super(context, cause); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public InvalidInterfaceMixinException(IMixinInfo mixin, String message, Throwable cause) { super(mixin, message, cause); }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public InvalidInterfaceMixinException(IMixinContext context, String message, Throwable cause) { super(context, message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\throwables\InvalidInterfaceMixinException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */