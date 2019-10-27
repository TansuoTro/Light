/*    */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*    */ 
/*    */ import org.spongepowered.asm.lib.util.CheckClassAdapter;
/*    */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*    */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*    */ import org.spongepowered.asm.transformers.MixinClassWriter;
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
/*    */ public class ExtensionCheckClass
/*    */   implements IExtension
/*    */ {
/*    */   public static class ValidationFailedException
/*    */     extends MixinException
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/* 50 */     public ValidationFailedException(String message, Throwable cause) { super(message, cause); }
/*    */ 
/*    */ 
/*    */     
/* 54 */     public ValidationFailedException(String message) { super(message); }
/*    */ 
/*    */ 
/*    */     
/* 58 */     public ValidationFailedException(Throwable cause) { super(cause); }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public boolean checkActive(MixinEnvironment environment) { return environment.getOption(MixinEnvironment.Option.DEBUG_VERIFY); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void preApply(ITargetClassContext context) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postApply(ITargetClassContext context) {
/*    */     try {
/* 87 */       context.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
/* 88 */     } catch (RuntimeException ex) {
/* 89 */       throw new ValidationFailedException(ex.getMessage(), ex);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {}
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionCheckClass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */