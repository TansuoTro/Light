/*    */ package org.spongepowered.asm.mixin.extensibility;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
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
/*    */ 
/*    */ public interface IMixinErrorHandler
/*    */ {
/*    */   ErrorAction onPrepareError(IMixinConfig paramIMixinConfig, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
/*    */   
/*    */   ErrorAction onApplyError(String paramString, Throwable paramThrowable, IMixinInfo paramIMixinInfo, ErrorAction paramErrorAction);
/*    */   
/*    */   public enum ErrorAction
/*    */   {
/* 46 */     NONE(Level.INFO),
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 51 */     WARN(Level.WARN),
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     ERROR(Level.FATAL);
/*    */ 
/*    */ 
/*    */     
/*    */     public final Level logLevel;
/*    */ 
/*    */ 
/*    */     
/* 66 */     ErrorAction(Level logLevel) { this.logLevel = logLevel; }
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\extensibility\IMixinErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */