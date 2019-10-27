/*     */ package org.spongepowered.asm.mixin.transformer.ext.extensions;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IDecompiler;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtension;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
/*     */ import org.spongepowered.asm.util.Constants;
/*     */ import org.spongepowered.asm.util.perf.Profiler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtensionClassExporter
/*     */   implements IExtension
/*     */ {
/*     */   private static final String DECOMPILER_CLASS = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler";
/*     */   private static final String EXPORT_CLASS_DIR = "class";
/*     */   private static final String EXPORT_JAVA_DIR = "java";
/*  56 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */   private final File classExportDir;
/*     */   private final IDecompiler decompiler;
/*     */   
/*     */   public ExtensionClassExporter(MixinEnvironment env) {
/*  61 */     this.classExportDir = new File(Constants.DEBUG_OUTPUT_DIR, "class");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.decompiler = initDecompiler(env, new File(Constants.DEBUG_OUTPUT_DIR, "java"));
/*     */     
/*     */     try {
/*  72 */       FileUtils.deleteDirectory(this.classExportDir);
/*  73 */     } catch (IOException ex) {
/*  74 */       logger.warn("Error cleaning class output directory: {}", new Object[] { ex.getMessage() });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*  79 */   public boolean isDecompilerActive() { return (this.decompiler != null); }
/*     */ 
/*     */   
/*     */   private IDecompiler initDecompiler(MixinEnvironment env, File outputPath) {
/*  83 */     if (!env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE)) {
/*  84 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  88 */       boolean as = env.getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_THREADED);
/*  89 */       logger.info("Attempting to load Fernflower decompiler{}", new Object[] { as ? " (Threaded mode)" : "" });
/*  90 */       String className = "org.spongepowered.asm.mixin.transformer.debug.RuntimeDecompiler" + (as ? "Async" : "");
/*     */       
/*  92 */       Class<? extends IDecompiler> clazz = Class.forName(className);
/*  93 */       Constructor<? extends IDecompiler> ctor = clazz.getDeclaredConstructor(new Class[] { File.class });
/*  94 */       IDecompiler decompiler = (IDecompiler)ctor.newInstance(new Object[] { outputPath });
/*  95 */       logger.info("Fernflower decompiler was successfully initialised, exported classes will be decompiled{}", new Object[] { as ? " in a separate thread" : "" });
/*     */       
/*  97 */       return decompiler;
/*  98 */     } catch (Throwable th) {
/*  99 */       logger.info("Fernflower could not be loaded, exported classes will not be decompiled. {}: {}", new Object[] { th
/* 100 */             .getClass().getSimpleName(), th.getMessage() });
/*     */       
/* 102 */       return null;
/*     */     } 
/*     */   }
/*     */   private String prepareFilter(String filter) {
/* 106 */     filter = "^\\Q" + filter.replace("**", "").replace("*", "").replace("?", "") + "\\E$";
/* 107 */     return filter.replace("", "\\E.*\\Q").replace("", "\\E[^\\.]+\\Q").replace("", "\\E.\\Q").replace("\\Q\\E", "");
/*     */   }
/*     */ 
/*     */   
/* 111 */   private boolean applyFilter(String filter, String subject) { return Pattern.compile(prepareFilter(filter), 2).matcher(subject).matches(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public boolean checkActive(MixinEnvironment environment) { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
/* 130 */     if (force || env.getOption(MixinEnvironment.Option.DEBUG_EXPORT)) {
/* 131 */       String filter = env.getOptionValue(MixinEnvironment.Option.DEBUG_EXPORT_FILTER);
/* 132 */       if (force || filter == null || applyFilter(filter, name)) {
/* 133 */         Profiler.Section exportTimer = MixinEnvironment.getProfiler().begin("debug.export");
/* 134 */         File outputFile = dumpClass(name.replace('.', '/'), bytes);
/* 135 */         if (this.decompiler != null) {
/* 136 */           this.decompiler.decompile(outputFile);
/*     */         }
/* 138 */         exportTimer.end();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File dumpClass(String fileName, byte[] bytes) {
/* 151 */     File outputFile = new File(this.classExportDir, fileName + ".class");
/*     */     try {
/* 153 */       FileUtils.writeByteArrayToFile(outputFile, bytes);
/* 154 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 157 */     return outputFile;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\ext\extensions\ExtensionClassExporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */