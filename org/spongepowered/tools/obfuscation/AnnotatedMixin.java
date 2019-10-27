/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.ElementKind;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.DeclaredType;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ import org.spongepowered.tools.obfuscation.struct.InjectorRemap;
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
/*     */ class AnnotatedMixin
/*     */ {
/*     */   private final AnnotationHandle annotation;
/*     */   private final Messager messager;
/*     */   private final ITypeHandleProvider typeProvider;
/*     */   private final IObfuscationManager obf;
/*     */   private final IMappingConsumer mappings;
/*     */   private final TypeElement mixin;
/*     */   private final List<ExecutableElement> methods;
/*     */   private final TypeHandle handle;
/*     */   private final List<TypeHandle> targets;
/*     */   private final TypeHandle primaryTarget;
/*     */   private final String classRef;
/*     */   private final boolean remap;
/*     */   private final boolean virtual;
/*     */   private final AnnotatedMixinElementHandlerOverwrite overwrites;
/*     */   private final AnnotatedMixinElementHandlerShadow shadows;
/*     */   private final AnnotatedMixinElementHandlerInjector injectors;
/*     */   private final AnnotatedMixinElementHandlerAccessor accessors;
/*     */   private final AnnotatedMixinElementHandlerSoftImplements softImplements;
/*     */   private boolean validated;
/*     */   
/*     */   public AnnotatedMixin(IMixinAnnotationProcessor ap, TypeElement type) {
/* 106 */     this.targets = new ArrayList();
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
/* 159 */     this.validated = false;
/*     */ 
/*     */     
/* 162 */     this.typeProvider = ap.getTypeProvider();
/* 163 */     this.obf = ap.getObfuscationManager();
/* 164 */     this.mappings = this.obf.createMappingConsumer();
/* 165 */     this.messager = ap;
/* 166 */     this.mixin = type;
/* 167 */     this.handle = new TypeHandle(type);
/* 168 */     this.methods = new ArrayList(this.handle.getEnclosedElements(new ElementKind[] { ElementKind.METHOD }));
/* 169 */     this.virtual = this.handle.getAnnotation(org.spongepowered.asm.mixin.Pseudo.class).exists();
/* 170 */     this.annotation = this.handle.getAnnotation(org.spongepowered.asm.mixin.Mixin.class);
/* 171 */     this.classRef = TypeUtils.getInternalName(type);
/* 172 */     this.primaryTarget = initTargets();
/* 173 */     this.remap = (this.annotation.getBoolean("remap", true) && this.targets.size() > 0);
/*     */     
/* 175 */     this.overwrites = new AnnotatedMixinElementHandlerOverwrite(ap, this);
/* 176 */     this.shadows = new AnnotatedMixinElementHandlerShadow(ap, this);
/* 177 */     this.injectors = new AnnotatedMixinElementHandlerInjector(ap, this);
/* 178 */     this.accessors = new AnnotatedMixinElementHandlerAccessor(ap, this);
/* 179 */     this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(ap, this);
/*     */   }
/*     */   
/*     */   AnnotatedMixin runValidators(IMixinValidator.ValidationPass pass, Collection<IMixinValidator> validators) {
/* 183 */     for (IMixinValidator validator : validators) {
/* 184 */       if (!validator.validate(pass, this.mixin, this.annotation, this.targets)) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 189 */     if (pass == IMixinValidator.ValidationPass.FINAL && !this.validated) {
/* 190 */       this.validated = true;
/* 191 */       runFinalValidation();
/*     */     } 
/*     */     
/* 194 */     return this;
/*     */   }
/*     */   
/*     */   private TypeHandle initTargets() {
/* 198 */     TypeHandle primaryTarget = null;
/*     */ 
/*     */     
/*     */     try {
/* 202 */       for (TypeMirror target : this.annotation.getList()) {
/* 203 */         TypeHandle type = new TypeHandle((DeclaredType)target);
/* 204 */         if (this.targets.contains(type)) {
/*     */           continue;
/*     */         }
/* 207 */         addTarget(type);
/* 208 */         if (primaryTarget == null) {
/* 209 */           primaryTarget = type;
/*     */         }
/*     */       } 
/* 212 */     } catch (Exception ex) {
/* 213 */       printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 218 */       for (String privateTarget : this.annotation.getList("targets")) {
/* 219 */         TypeHandle type = this.typeProvider.getTypeHandle(privateTarget);
/* 220 */         if (this.targets.contains(type)) {
/*     */           continue;
/*     */         }
/* 223 */         if (this.virtual)
/* 224 */         { type = this.typeProvider.getSimulatedHandle(privateTarget, this.mixin.asType()); }
/* 225 */         else { if (type == null) {
/* 226 */             printMessage(Diagnostic.Kind.ERROR, "Mixin target " + privateTarget + " could not be found", this);
/* 227 */             return null;
/* 228 */           }  if (type.isPublic()) {
/* 229 */             printMessage(Diagnostic.Kind.WARNING, "Mixin target " + privateTarget + " is public and must be specified in value", this);
/* 230 */             return null;
/*     */           }  }
/* 232 */          addSoftTarget(type, privateTarget);
/* 233 */         if (primaryTarget == null) {
/* 234 */           primaryTarget = type;
/*     */         }
/*     */       } 
/* 237 */     } catch (Exception ex) {
/* 238 */       printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
/*     */     } 
/*     */     
/* 241 */     if (primaryTarget == null) {
/* 242 */       printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
/*     */     }
/*     */     
/* 245 */     return primaryTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   private void printMessage(Diagnostic.Kind kind, CharSequence msg, AnnotatedMixin mixin) { this.messager.printMessage(kind, msg, this.mixin, this.annotation.asMirror()); }
/*     */ 
/*     */   
/*     */   private void addSoftTarget(TypeHandle type, String reference) {
/* 256 */     ObfuscationData<String> obfClassData = this.obf.getDataProvider().getObfClass(type);
/* 257 */     if (!obfClassData.isEmpty()) {
/* 258 */       this.obf.getReferenceManager().addClassMapping(this.classRef, reference, obfClassData);
/*     */     }
/*     */     
/* 261 */     addTarget(type);
/*     */   }
/*     */ 
/*     */   
/* 265 */   private void addTarget(TypeHandle type) { this.targets.add(type); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 270 */   public String toString() { return this.mixin.getSimpleName().toString(); }
/*     */ 
/*     */ 
/*     */   
/* 274 */   public AnnotationHandle getAnnotation() { return this.annotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 281 */   public TypeElement getMixin() { return this.mixin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 288 */   public TypeHandle getHandle() { return this.handle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 295 */   public String getClassRef() { return this.classRef; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 302 */   public boolean isInterface() { return (this.mixin.getKind() == ElementKind.INTERFACE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 310 */   public TypeHandle getPrimaryTarget() { return this.primaryTarget; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 317 */   public List<TypeHandle> getTargets() { return this.targets; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 324 */   public boolean isMultiTarget() { return (this.targets.size() > 1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 331 */   public boolean remap() { return this.remap; }
/*     */ 
/*     */ 
/*     */   
/* 335 */   public IMappingConsumer getMappings() { return this.mappings; }
/*     */ 
/*     */   
/*     */   private void runFinalValidation() {
/* 339 */     for (ExecutableElement method : this.methods) {
/* 340 */       this.overwrites.registerMerge(method);
/*     */     }
/*     */   }
/*     */   
/*     */   public void registerOverwrite(ExecutableElement method, AnnotationHandle overwrite, boolean shouldRemap) {
/* 345 */     this.methods.remove(method);
/* 346 */     this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(method, overwrite, shouldRemap));
/*     */   }
/*     */ 
/*     */   
/* 350 */   public void registerShadow(VariableElement field, AnnotationHandle shadow, boolean shouldRemap) { this.shadows.getClass(); this.shadows.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowField(this.shadows, field, shadow, shouldRemap)); }
/*     */ 
/*     */   
/*     */   public void registerShadow(ExecutableElement method, AnnotationHandle shadow, boolean shouldRemap) {
/* 354 */     this.methods.remove(method);
/* 355 */     this.shadows.getClass(); this.shadows.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowMethod(this.shadows, method, shadow, shouldRemap));
/*     */   }
/*     */   
/*     */   public void registerInjector(ExecutableElement method, AnnotationHandle inject, InjectorRemap remap) {
/* 359 */     this.methods.remove(method);
/* 360 */     this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(method, inject, remap));
/*     */     
/* 362 */     List<AnnotationHandle> ats = inject.getAnnotationList("at");
/* 363 */     for (AnnotationHandle at : ats) {
/* 364 */       registerInjectionPoint(method, inject, at, remap, "@At(%s)");
/*     */     }
/*     */     
/* 367 */     List<AnnotationHandle> slices = inject.getAnnotationList("slice");
/* 368 */     for (AnnotationHandle slice : slices) {
/* 369 */       String id = (String)slice.getValue("id", "");
/*     */       
/* 371 */       AnnotationHandle from = slice.getAnnotation("from");
/* 372 */       if (from != null) {
/* 373 */         registerInjectionPoint(method, inject, from, remap, "@Slice[" + id + "](from=@At(%s))");
/*     */       }
/*     */       
/* 376 */       AnnotationHandle to = slice.getAnnotation("to");
/* 377 */       if (to != null) {
/* 378 */         registerInjectionPoint(method, inject, to, remap, "@Slice[" + id + "](to=@At(%s))");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 384 */   public void registerInjectionPoint(ExecutableElement element, AnnotationHandle inject, AnnotationHandle at, InjectorRemap remap, String format) { this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(element, inject, at, remap), format); }
/*     */ 
/*     */   
/*     */   public void registerAccessor(ExecutableElement element, AnnotationHandle accessor, boolean shouldRemap) {
/* 388 */     this.methods.remove(element);
/* 389 */     this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(element, accessor, shouldRemap));
/*     */   }
/*     */   
/*     */   public void registerInvoker(ExecutableElement element, AnnotationHandle invoker, boolean shouldRemap) {
/* 393 */     this.methods.remove(element);
/* 394 */     this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(element, invoker, shouldRemap));
/*     */   }
/*     */ 
/*     */   
/* 398 */   public void registerSoftImplements(AnnotationHandle implementsAnnotation) { this.softImplements.process(implementsAnnotation); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */