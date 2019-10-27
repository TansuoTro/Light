/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.service.ObfuscationServices;
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
/*     */ public class ObfuscationManager
/*     */   implements IObfuscationManager
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   private final IObfuscationDataProvider obfs;
/*     */   private final IReferenceManager refs;
/*     */   private final List<IMappingConsumer> consumers;
/*     */   private boolean initDone;
/*     */   
/*     */   public ObfuscationManager(IMixinAnnotationProcessor ap) {
/*  50 */     this.environments = new ArrayList();
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
/*  65 */     this.consumers = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     this.ap = ap;
/*  71 */     this.obfs = new ObfuscationDataProvider(ap, this.environments);
/*  72 */     this.refs = new ReferenceManager(ap, this.environments);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  77 */     if (this.initDone) {
/*     */       return;
/*     */     }
/*  80 */     this.initDone = true;
/*  81 */     ObfuscationServices.getInstance().initProviders(this.ap);
/*  82 */     for (ObfuscationType obfType : ObfuscationType.types()) {
/*  83 */       if (obfType.isSupported()) {
/*  84 */         this.environments.add(obfType.createEnvironment());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  91 */   public IObfuscationDataProvider getDataProvider() { return this.obfs; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public IReferenceManager getReferenceManager() { return this.refs; }
/*     */ 
/*     */ 
/*     */   
/*     */   public IMappingConsumer createMappingConsumer() {
/* 101 */     Mappings mappings = new Mappings();
/* 102 */     this.consumers.add(mappings);
/* 103 */     return mappings;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public List<ObfuscationEnvironment> getEnvironments() { return this.environments; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeMappings() {
/* 116 */     for (ObfuscationEnvironment env : this.environments) {
/* 117 */       env.writeMappings(this.consumers);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public void writeReferences() { this.refs.write(); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\ObfuscationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */