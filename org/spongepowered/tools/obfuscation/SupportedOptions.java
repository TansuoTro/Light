/*    */ package org.spongepowered.tools.obfuscation;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import org.spongepowered.tools.obfuscation.service.ObfuscationServices;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class SupportedOptions
/*    */ {
/*    */   public static final String TOKENS = "tokens";
/*    */   public static final String OUT_REFMAP_FILE = "outRefMapFile";
/*    */   public static final String DISABLE_TARGET_VALIDATOR = "disableTargetValidator";
/*    */   public static final String DISABLE_TARGET_EXPORT = "disableTargetExport";
/*    */   public static final String DISABLE_OVERWRITE_CHECKER = "disableOverwriteChecker";
/*    */   public static final String OVERWRITE_ERROR_LEVEL = "overwriteErrorLevel";
/*    */   public static final String DEFAULT_OBFUSCATION_ENV = "defaultObfuscationEnv";
/*    */   public static final String DEPENDENCY_TARGETS_FILE = "dependencyTargetsFile";
/*    */   
/*    */   public static Set<String> getAllOptions() {
/* 55 */     options = ImmutableSet.builder();
/* 56 */     options.add(new String[] { "tokens", "outRefMapFile", "disableTargetValidator", "disableTargetExport", "disableOverwriteChecker", "overwriteErrorLevel", "defaultObfuscationEnv", "dependencyTargetsFile" });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 66 */     options.addAll(
/* 67 */         ObfuscationServices.getInstance().getSupportedOptions());
/*    */     
/* 69 */     return options.build();
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\SupportedOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */