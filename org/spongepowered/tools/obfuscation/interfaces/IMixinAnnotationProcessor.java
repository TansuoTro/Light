/*    */ package org.spongepowered.tools.obfuscation.interfaces;
/*    */ 
/*    */ import javax.annotation.processing.Messager;
/*    */ import javax.annotation.processing.ProcessingEnvironment;
/*    */ import org.spongepowered.asm.util.ITokenProvider;
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
/*    */ public interface IMixinAnnotationProcessor
/*    */   extends Messager, IOptionProvider
/*    */ {
/*    */   CompilerEnvironment getCompilerEnvironment();
/*    */   
/*    */   ProcessingEnvironment getProcessingEnvironment();
/*    */   
/*    */   IObfuscationManager getObfuscationManager();
/*    */   
/*    */   ITokenProvider getTokenProvider();
/*    */   
/*    */   ITypeHandleProvider getTypeProvider();
/*    */   
/*    */   IJavadocProvider getJavadocProvider();
/*    */   
/*    */   public enum CompilerEnvironment
/*    */   {
/* 46 */     JAVAC,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     JDT;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\interfaces\IMixinAnnotationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */