/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
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
/*    */ public class InvalidInjectionPointException
/*    */   extends InvalidInjectionException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   
/* 38 */   public InvalidInjectionPointException(IMixinContext context, String format, Object... args) { super(context, String.format(format, args)); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public InvalidInjectionPointException(InjectionInfo info, String format, Object... args) { super(info, String.format(format, args)); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public InvalidInjectionPointException(IMixinContext context, Throwable cause, String format, Object... args) { super(context, String.format(format, args), cause); }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public InvalidInjectionPointException(InjectionInfo info, Throwable cause, String format, Object... args) { super(info, String.format(format, args), cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\throwables\InvalidInjectionPointException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */