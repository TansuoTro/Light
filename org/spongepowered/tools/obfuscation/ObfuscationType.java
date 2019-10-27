/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
/*     */ import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;
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
/*     */ 
/*     */ public final class ObfuscationType
/*     */ {
/*  48 */   private static final Map<String, ObfuscationType> types = new LinkedHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String key;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ObfuscationTypeDescriptor descriptor;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IMixinAnnotationProcessor ap;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IOptionProvider options;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ObfuscationType(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/*  71 */     this.key = descriptor.getKey();
/*  72 */     this.descriptor = descriptor;
/*  73 */     this.ap = ap;
/*  74 */     this.options = ap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ObfuscationEnvironment createEnvironment() {
/*     */     try {
/*  82 */       Class<? extends ObfuscationEnvironment> cls = this.descriptor.getEnvironmentType();
/*  83 */       Constructor<? extends ObfuscationEnvironment> ctor = cls.getDeclaredConstructor(new Class[] { ObfuscationType.class });
/*  84 */       ctor.setAccessible(true);
/*  85 */       return (ObfuscationEnvironment)ctor.newInstance(new Object[] { this });
/*  86 */     } catch (Exception ex) {
/*  87 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  93 */   public String toString() { return this.key; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public String getKey() { return this.key; }
/*     */ 
/*     */ 
/*     */   
/* 102 */   public ObfuscationTypeDescriptor getConfig() { return this.descriptor; }
/*     */ 
/*     */ 
/*     */   
/* 106 */   public IMixinAnnotationProcessor getAnnotationProcessor() { return this.ap; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDefault() {
/* 113 */     String defaultEnv = this.options.getOption("defaultObfuscationEnv");
/* 114 */     return ((defaultEnv == null && this.key.equals("searge")) || (defaultEnv != null && this.key
/* 115 */       .equals(defaultEnv.toLowerCase())));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public boolean isSupported() { return (getInputFileNames().size() > 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getInputFileNames() {
/* 129 */     ImmutableList.Builder<String> builder = ImmutableList.builder();
/*     */     
/* 131 */     String inputFile = this.options.getOption(this.descriptor.getInputFileOption());
/* 132 */     if (inputFile != null) {
/* 133 */       builder.add(inputFile);
/*     */     }
/*     */     
/* 136 */     String extraInputFiles = this.options.getOption(this.descriptor.getExtraInputFilesOption());
/* 137 */     if (extraInputFiles != null) {
/* 138 */       for (String extraInputFile : extraInputFiles.split(";")) {
/* 139 */         builder.add(extraInputFile.trim());
/*     */       }
/*     */     }
/*     */     
/* 143 */     return builder.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public String getOutputFileName() { return this.options.getOption(this.descriptor.getOutputFileOption()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public static Iterable<ObfuscationType> types() { return types.values(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType create(ObfuscationTypeDescriptor descriptor, IMixinAnnotationProcessor ap) {
/* 168 */     String key = descriptor.getKey();
/* 169 */     if (types.containsKey(key)) {
/* 170 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was already registered");
/*     */     }
/* 172 */     ObfuscationType type = new ObfuscationType(descriptor, ap);
/* 173 */     types.put(key, type);
/* 174 */     return type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ObfuscationType get(String key) {
/* 185 */     ObfuscationType type = (ObfuscationType)types.get(key);
/* 186 */     if (type == null) {
/* 187 */       throw new IllegalArgumentException("Obfuscation type with key " + key + " was not registered");
/*     */     }
/* 189 */     return type;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\ObfuscationType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */