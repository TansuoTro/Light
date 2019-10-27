/*    */ package org.spongepowered.asm.mixin.injection.throwables;
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
/*    */ public class InjectionError
/*    */   extends Error
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/*    */   public InjectionError() {}
/*    */   
/* 38 */   public InjectionError(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public InjectionError(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 46 */   public InjectionError(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\throwables\InjectionError.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */