/*     */ package org.spongepowered.asm.mixin.transformer.ext;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTransformer;
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
/*     */ public final class Extensions
/*     */ {
/*     */   private final MixinTransformer transformer;
/*     */   private final List<IExtension> extensions;
/*     */   private final Map<Class<? extends IExtension>, IExtension> extensionMap;
/*     */   private final List<IClassGenerator> generators;
/*     */   private final List<IClassGenerator> generatorsView;
/*     */   private final Map<Class<? extends IClassGenerator>, IClassGenerator> generatorMap;
/*     */   private List<IExtension> activeExtensions;
/*     */   
/*     */   public Extensions(MixinTransformer transformer) {
/*  52 */     this.extensions = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.extensionMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.generators = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     this.generatorsView = Collections.unmodifiableList(this.generators);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     this.generatorMap = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.activeExtensions = Collections.emptyList();
/*     */ 
/*     */     
/*  84 */     this.transformer = transformer;
/*     */   }
/*     */ 
/*     */   
/*  88 */   public MixinTransformer getTransformer() { return this.transformer; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IExtension extension) {
/*  97 */     this.extensions.add(extension);
/*  98 */     this.extensionMap.put(extension.getClass(), extension);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public List<IExtension> getExtensions() { return Collections.unmodifiableList(this.extensions); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public List<IExtension> getActiveExtensions() { return this.activeExtensions; }
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
/* 124 */   public <T extends IExtension> T getExtension(Class<? extends IExtension> extensionClass) { return (T)(IExtension)lookup(extensionClass, this.extensionMap, this.extensions); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void select(MixinEnvironment environment) {
/* 133 */     ImmutableList.Builder<IExtension> activeExtensions = ImmutableList.builder();
/*     */     
/* 135 */     for (IExtension extension : this.extensions) {
/* 136 */       if (extension.checkActive(environment)) {
/* 137 */         activeExtensions.add(extension);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     this.activeExtensions = activeExtensions.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preApply(ITargetClassContext context) {
/* 150 */     for (IExtension extension : this.activeExtensions) {
/* 151 */       extension.preApply(context);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postApply(ITargetClassContext context) {
/* 161 */     for (IExtension extension : this.activeExtensions) {
/* 162 */       extension.postApply(context);
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
/*     */ 
/*     */   
/*     */   public void export(MixinEnvironment env, String name, boolean force, byte[] bytes) {
/* 176 */     for (IExtension extension : this.activeExtensions) {
/* 177 */       extension.export(env, name, force, bytes);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(IClassGenerator generator) {
/* 187 */     this.generators.add(generator);
/* 188 */     this.generatorMap.put(generator.getClass(), generator);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public List<IClassGenerator> getGenerators() { return this.generatorsView; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 205 */   public <T extends IClassGenerator> T getGenerator(Class<? extends IClassGenerator> generatorClass) { return (T)(IClassGenerator)lookup(generatorClass, this.generatorMap, this.generators); }
/*     */ 
/*     */   
/*     */   private static <T> T lookup(Class<? extends T> extensionClass, Map<Class<? extends T>, T> map, List<T> list) {
/* 209 */     T extension = (T)map.get(extensionClass);
/* 210 */     if (extension == null) {
/* 211 */       for (T classGenerator : list) {
/* 212 */         if (extensionClass.isAssignableFrom(classGenerator.getClass())) {
/* 213 */           extension = classGenerator;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 218 */       if (extension == null) {
/* 219 */         throw new IllegalArgumentException("Extension for <" + extensionClass.getName() + "> could not be found");
/*     */       }
/*     */       
/* 222 */       map.put(extensionClass, extension);
/*     */     } 
/*     */     
/* 225 */     return extension;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\ext\Extensions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */