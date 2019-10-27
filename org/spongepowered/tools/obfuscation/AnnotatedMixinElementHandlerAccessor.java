/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.lang.model.type.TypeKind;
/*     */ import javax.lang.model.type.TypeMirror;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.gen.AccessorInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.Extensions;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
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
/*     */ public class AnnotatedMixinElementHandlerAccessor
/*     */   extends AnnotatedMixinElementHandler
/*     */   implements IMixinContext
/*     */ {
/*     */   static class AnnotatedElementAccessor
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final boolean shouldRemap;
/*     */     private final TypeMirror returnType;
/*     */     private String targetName;
/*     */     
/*     */     public AnnotatedElementAccessor(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) {
/*  71 */       super(element, annotation);
/*  72 */       this.shouldRemap = shouldRemap;
/*  73 */       this.returnType = ((ExecutableElement)getElement()).getReturnType();
/*     */     }
/*     */ 
/*     */     
/*  77 */     public boolean shouldRemap() { return this.shouldRemap; }
/*     */ 
/*     */ 
/*     */     
/*  81 */     public String getAnnotationValue() { return (String)getAnnotation().getValue(); }
/*     */ 
/*     */     
/*     */     public TypeMirror getTargetType() {
/*  85 */       switch (AnnotatedMixinElementHandlerAccessor.null.$SwitchMap$org$spongepowered$asm$mixin$gen$AccessorInfo$AccessorType[getAccessorType().ordinal()]) {
/*     */         case 1:
/*  87 */           return this.returnType;
/*     */         case 2:
/*  89 */           return ((VariableElement)((ExecutableElement)getElement()).getParameters().get(0)).asType();
/*     */       } 
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  96 */     public String getTargetTypeName() { return TypeUtils.getTypeName(getTargetType()); }
/*     */ 
/*     */ 
/*     */     
/* 100 */     public String getAccessorDesc() { return TypeUtils.getInternalName(getTargetType()); }
/*     */ 
/*     */ 
/*     */     
/* 104 */     public MemberInfo getContext() { return new MemberInfo(getTargetName(), null, getAccessorDesc()); }
/*     */ 
/*     */ 
/*     */     
/* 108 */     public AccessorInfo.AccessorType getAccessorType() { return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER; }
/*     */ 
/*     */ 
/*     */     
/* 112 */     public void setTargetName(String targetName) { this.targetName = targetName; }
/*     */ 
/*     */ 
/*     */     
/* 116 */     public String getTargetName() { return this.targetName; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     public String toString() { return (this.targetName != null) ? this.targetName : "<invalid>"; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInvoker
/*     */     extends AnnotatedElementAccessor
/*     */   {
/* 131 */     public AnnotatedElementInvoker(ExecutableElement element, AnnotationHandle annotation, boolean shouldRemap) { super(element, annotation, shouldRemap); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     public String getAccessorDesc() { return TypeUtils.getDescriptor((ExecutableElement)getElement()); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     public AccessorInfo.AccessorType getAccessorType() { return AccessorInfo.AccessorType.METHOD_PROXY; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     public String getTargetTypeName() { return TypeUtils.getJavaSignature(getElement()); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   public AnnotatedMixinElementHandlerAccessor(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) { super(ap, mixin); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 157 */   public ReferenceMapper getReferenceMapper() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public String getClassName() { return this.mixin.getClassRef().replace('/', '.'); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public String getClassRef() { return this.mixin.getClassRef(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 172 */   public String getTargetClassRef() { throw new UnsupportedOperationException("Target class not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public IMixinInfo getMixin() { throw new UnsupportedOperationException("MixinInfo not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   public Extensions getExtensions() { throw new UnsupportedOperationException("Mixin Extensions not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 187 */   public boolean getOption(MixinEnvironment.Option option) { throw new UnsupportedOperationException("Options not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 192 */   public int getPriority() { throw new UnsupportedOperationException("Priority not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public Target getTargetMethod(MethodNode into) { throw new UnsupportedOperationException("Target not available at compile time"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerAccessor(AnnotatedElementAccessor elem) {
/* 206 */     if (elem.getAccessorType() == null) {
/* 207 */       elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
/*     */       
/*     */       return;
/*     */     } 
/* 211 */     String targetName = getAccessorTargetName(elem);
/* 212 */     if (targetName == null) {
/* 213 */       elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
/*     */       return;
/*     */     } 
/* 216 */     elem.setTargetName(targetName);
/*     */     
/* 218 */     for (TypeHandle target : this.mixin.getTargets()) {
/* 219 */       if (elem.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
/* 220 */         registerInvokerForTarget((AnnotatedElementInvoker)elem, target); continue;
/*     */       } 
/* 222 */       registerAccessorForTarget(elem, target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAccessorForTarget(AnnotatedElementAccessor elem, TypeHandle target) {
/* 228 */     FieldHandle targetField = target.findField(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 229 */     if (targetField == null) {
/* 230 */       if (!target.isImaginary()) {
/* 231 */         elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 235 */       targetField = new FieldHandle(target.getName(), elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 238 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 242 */     ObfuscationData<MappingField> obfData = this.obf.getDataProvider().getObfField(targetField.asMapping(false).move(target.getName()));
/* 243 */     if (obfData.isEmpty()) {
/* 244 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 245 */       elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 249 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 252 */       this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 253 */     } catch (ReferenceConflictException ex) {
/* 254 */       elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 255 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void registerInvokerForTarget(AnnotatedElementInvoker elem, TypeHandle target) {
/* 260 */     MethodHandle targetMethod = target.findMethod(elem.getTargetName(), elem.getTargetTypeName(), false);
/* 261 */     if (targetMethod == null) {
/* 262 */       if (!target.isImaginary()) {
/* 263 */         elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + elem + " in target " + target);
/*     */         
/*     */         return;
/*     */       } 
/* 267 */       targetMethod = new MethodHandle(target, elem.getTargetName(), elem.getDesc());
/*     */     } 
/*     */     
/* 270 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod.asMapping(false).move(target.getName()));
/* 275 */     if (obfData.isEmpty()) {
/* 276 */       String info = this.mixin.isMultiTarget() ? (" in target " + target) : "";
/* 277 */       elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + info + " for @Accessor target " + elem);
/*     */       
/*     */       return;
/*     */     } 
/* 281 */     obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */     
/*     */     try {
/* 284 */       this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), elem.getTargetName(), elem.getContext(), obfData);
/* 285 */     } catch (ReferenceConflictException ex) {
/* 286 */       elem.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + elem + ": " + ex.getNew() + " for target " + target + " conflicts with existing mapping " + ex
/* 287 */           .getOld());
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getAccessorTargetName(AnnotatedElementAccessor elem) {
/* 292 */     String value = elem.getAnnotationValue();
/* 293 */     if (Strings.isNullOrEmpty(value)) {
/* 294 */       return inflectAccessorTarget(elem);
/*     */     }
/* 296 */     return value;
/*     */   }
/*     */ 
/*     */   
/* 300 */   private String inflectAccessorTarget(AnnotatedElementAccessor elem) { return AccessorInfo.inflectTarget(elem.getSimpleName(), elem.getAccessorType(), "", this, false); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */