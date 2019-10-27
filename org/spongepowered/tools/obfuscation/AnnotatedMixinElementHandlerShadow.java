/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ class AnnotatedMixinElementHandlerShadow
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static abstract class AnnotatedElementShadow<E extends Element, M extends IMapping<M>>
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<E>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final AnnotatedMixinElementHandler.ShadowElementName name;
/*     */     private final IMapping.Type type;
/*     */     
/*     */     protected AnnotatedElementShadow(E element, AnnotationHandle annotation, boolean shouldRemap, IMapping.Type type) {
/*  62 */       super(element, annotation);
/*  63 */       this.shouldRemap = shouldRemap;
/*  64 */       this.name = new AnnotatedMixinElementHandler.ShadowElementName(element, annotation);
/*  65 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*  69 */     public boolean shouldRemap() { return this.shouldRemap; }
/*     */ 
/*     */ 
/*     */     
/*  73 */     public AnnotatedMixinElementHandler.ShadowElementName getName() { return this.name; }
/*     */ 
/*     */ 
/*     */     
/*  77 */     public IMapping.Type getElementType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     public String toString() { return getElementType().name().toLowerCase(); }
/*     */ 
/*     */ 
/*     */     
/*  86 */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping<?> name) { return setObfuscatedName(name.getSimpleName()); }
/*     */ 
/*     */ 
/*     */     
/*  90 */     public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String name) { return getName().setObfuscatedName(name); }
/*     */ 
/*     */ 
/*     */     
/*  94 */     public ObfuscationData<M> getObfuscationData(IObfuscationDataProvider provider, TypeHandle owner) { return provider.getObfEntry(getMapping(owner, getName().toString(), getDesc())); }
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract M getMapping(TypeHandle param1TypeHandle, String param1String1, String param1String2);
/*     */ 
/*     */ 
/*     */     
/*     */     public abstract void addMapping(ObfuscationType param1ObfuscationType, IMapping<?> param1IMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowField
/*     */     extends AnnotatedElementShadow<VariableElement, MappingField>
/*     */   {
/* 109 */     public AnnotatedElementShadowField(VariableElement element, AnnotationHandle annotation, boolean shouldRemap) { super(element, annotation, shouldRemap, IMapping.Type.FIELD); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     public MappingField getMapping(TypeHandle owner, String name, String desc) { return new MappingField(owner.getName(), name, desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     public void addMapping(ObfuscationType type, IMapping<?> remapped) { AnnotatedMixinElementHandlerShadow.this.addFieldMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc()); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class AnnotatedElementShadowMethod
/*     */     extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
/*     */   {
/* 130 */     public AnnotatedElementShadowMethod(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) { super(element, annotation, shouldRemap, IMapping.Type.METHOD); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     public MappingMethod getMapping(TypeHandle owner, String name, String desc) { return owner.getMappingMethod(name, desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     public void addMapping(ObfuscationType type, IMapping<?> remapped) { AnnotatedMixinElementHandlerShadow.this.addMethodMapping(type, setObfuscatedName(remapped), getDesc(), remapped.getDesc()); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) { super(ap, mixin); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerShadow(AnnotatedElementShadow<?, ?> elem) {
/* 153 */     validateTarget(elem.getElement(), elem.getAnnotation(), elem.getName(), "@Shadow");
/*     */     
/* 155 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 159 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 160 */       registerShadowForTarget(elem, target);
/*     */     }
/*     */   }
/*     */   
/*     */   private void registerShadowForTarget(AnnotatedElementShadow<?, ?> elem, TypeHandle target) {
/* 165 */     ObfuscationData<? extends IMapping<?>> obfData = elem.getObfuscationData(this.obf.getDataProvider(), target);
/*     */     
/* 167 */     if (obfData.isEmpty()) {
/* 168 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 169 */       if (target.isSimulated()) {
/* 170 */         elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } else {
/* 172 */         elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Shadow " + elem);
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     for (ObfuscationType type : obfData) {
/*     */       try {
/* 179 */         elem.addMapping(type, (IMapping)obfData.get(type));
/* 180 */       } catch (MappingConflictException ex) {
/* 181 */         elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + elem + ": " + ex.getNew().getSimpleName() + " for target " + target + " conflicts with existing mapping " + ex
/* 182 */             .getOld().getSimpleName());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerShadow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */