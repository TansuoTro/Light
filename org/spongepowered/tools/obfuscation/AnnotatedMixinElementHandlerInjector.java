/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InvalidMemberDescriptorException;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*     */ class AnnotatedMixinElementHandlerInjector
/*     */   extends AnnotatedMixinElementHandler
/*     */ {
/*     */   static class AnnotatedElementInjector
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final InjectorRemap state;
/*     */     
/*     */     public AnnotatedElementInjector(ExecutableElement element, AnnotationHandle annotation, InjectorRemap shouldRemap) {
/*  64 */       super(element, annotation);
/*  65 */       this.state = shouldRemap;
/*     */     }
/*     */ 
/*     */     
/*  69 */     public boolean shouldRemap() { return this.state.shouldRemap(); }
/*     */ 
/*     */     
/*     */     public boolean hasCoerceArgument() {
/*  73 */       if (!this.annotation.toString().equals("@Inject")) {
/*  74 */         return false;
/*     */       }
/*     */       
/*  77 */       Iterator iterator = ((ExecutableElement)this.element).getParameters().iterator(); if (iterator.hasNext()) { VariableElement param = (VariableElement)iterator.next();
/*  78 */         return AnnotationHandle.of(param, org.spongepowered.asm.mixin.injection.Coerce.class).exists(); }
/*     */ 
/*     */       
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  85 */     public void addMessage(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) { this.state.addMessage(kind, msg, element, annotation); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     public String toString() { return getAnnotation().toString(); }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class AnnotatedElementInjectionPoint
/*     */     extends AnnotatedMixinElementHandler.AnnotatedElement<ExecutableElement>
/*     */   {
/*     */     private final AnnotationHandle at;
/*     */ 
/*     */     
/*     */     private Map<String, String> args;
/*     */     
/*     */     private final InjectorRemap state;
/*     */ 
/*     */     
/*     */     public AnnotatedElementInjectionPoint(ExecutableElement element, AnnotationHandle inject, AnnotationHandle at, InjectorRemap state) {
/* 107 */       super(element, inject);
/* 108 */       this.at = at;
/* 109 */       this.state = state;
/*     */     }
/*     */ 
/*     */     
/* 113 */     public boolean shouldRemap() { return this.at.getBoolean("remap", this.state.shouldRemap()); }
/*     */ 
/*     */ 
/*     */     
/* 117 */     public AnnotationHandle getAt() { return this.at; }
/*     */ 
/*     */     
/*     */     public String getAtArg(String key) {
/* 121 */       if (this.args == null) {
/* 122 */         this.args = new HashMap();
/* 123 */         for (String arg : this.at.getList("args")) {
/* 124 */           if (arg == null) {
/*     */             continue;
/*     */           }
/* 127 */           int eqPos = arg.indexOf('=');
/* 128 */           if (eqPos > -1) {
/* 129 */             this.args.put(arg.substring(0, eqPos), arg.substring(eqPos + 1)); continue;
/*     */           } 
/* 131 */           this.args.put(arg, "");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 136 */       return (String)this.args.get(key);
/*     */     }
/*     */ 
/*     */     
/* 140 */     public void notifyRemapped() { this.state.notifyRemapped(); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   AnnotatedMixinElementHandlerInjector(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) { super(ap, mixin); }
/*     */ 
/*     */   
/*     */   public void registerInjector(AnnotatedElementInjector elem) {
/* 150 */     if (this.mixin.isInterface()) {
/* 151 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
/*     */     }
/*     */     
/* 154 */     for (String reference : elem.getAnnotation().getList("method")) {
/* 155 */       MemberInfo targetMember = MemberInfo.parse(reference);
/* 156 */       if (targetMember.name == null) {
/*     */         continue;
/*     */       }
/*     */       
/*     */       try {
/* 161 */         targetMember.validate();
/* 162 */       } catch (InvalidMemberDescriptorException ex) {
/* 163 */         elem.printMessage(this.ap, Diagnostic.Kind.ERROR, ex.getMessage());
/*     */       } 
/*     */       
/* 166 */       if (targetMember.desc != null) {
/* 167 */         validateReferencedTarget((ExecutableElement)elem.getElement(), elem.getAnnotation(), targetMember, elem.toString());
/*     */       }
/*     */       
/* 170 */       if (!elem.shouldRemap()) {
/*     */         continue;
/*     */       }
/*     */       
/* 174 */       for (TypeHandle target : this.mixin.getTargets()) {
/* 175 */         if (!registerInjector(elem, reference, targetMember, target)) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean registerInjector(AnnotatedElementInjector elem, String reference, MemberInfo targetMember, TypeHandle target) {
/* 183 */     String desc = target.findDescriptor(targetMember);
/* 184 */     if (desc == null) {
/* 185 */       Diagnostic.Kind error = this.mixin.isMultiTarget() ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
/* 186 */       if (target.isSimulated()) {
/* 187 */         elem.printMessage(this.ap, Diagnostic.Kind.NOTE, elem + " target '" + reference + "' in @Pseudo mixin will not be obfuscated");
/* 188 */       } else if (target.isImaginary()) {
/* 189 */         elem.printMessage(this.ap, error, elem + " target requires method signature because enclosing type information for " + target + " is unavailable");
/*     */       }
/* 191 */       else if (!targetMember.isInitialiser()) {
/* 192 */         elem.printMessage(this.ap, error, "Unable to determine signature for " + elem + " target method");
/*     */       } 
/* 194 */       return true;
/*     */     } 
/*     */     
/* 197 */     String targetName = elem + " target " + targetMember.name;
/* 198 */     MappingMethod targetMethod = target.getMappingMethod(targetMember.name, desc);
/* 199 */     ObfuscationData<MappingMethod> obfData = this.obf.getDataProvider().getObfMethod(targetMethod);
/* 200 */     if (obfData.isEmpty()) {
/* 201 */       if (target.isSimulated())
/* 202 */       { obfData = this.obf.getDataProvider().getRemappedMethod(targetMethod); }
/* 203 */       else { if (targetMember.isClassInitialiser()) {
/* 204 */           return true;
/*     */         }
/* 206 */         Diagnostic.Kind error = targetMember.isConstructor() ? Diagnostic.Kind.WARNING : Diagnostic.Kind.ERROR;
/* 207 */         elem.addMessage(error, "No obfuscation mapping for " + targetName, elem.getElement(), elem.getAnnotation());
/* 208 */         return false; }
/*     */     
/*     */     }
/*     */     
/* 212 */     IReferenceManager refMap = this.obf.getReferenceManager();
/*     */     
/*     */     try {
/* 215 */       if ((targetMember.owner == null && this.mixin.isMultiTarget()) || target.isSimulated()) {
/* 216 */         obfData = AnnotatedMixinElementHandler.stripOwnerData(obfData);
/*     */       }
/* 218 */       refMap.addMethodMapping(this.classRef, reference, obfData);
/* 219 */     } catch (ReferenceConflictException ex) {
/* 220 */       String conflictType = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
/*     */       
/* 222 */       if (elem.hasCoerceArgument() && targetMember.owner == null && targetMember.desc == null) {
/* 223 */         MemberInfo oldMember = MemberInfo.parse(ex.getOld());
/* 224 */         MemberInfo newMember = MemberInfo.parse(ex.getNew());
/* 225 */         if (oldMember.name.equals(newMember.name)) {
/* 226 */           obfData = AnnotatedMixinElementHandler.stripDescriptors(obfData);
/* 227 */           refMap.setAllowConflicts(true);
/* 228 */           refMap.addMethodMapping(this.classRef, reference, obfData);
/* 229 */           refMap.setAllowConflicts(false);
/*     */ 
/*     */           
/* 232 */           elem.printMessage(this.ap, Diagnostic.Kind.WARNING, "Coerced " + conflictType + " reference has conflicting descriptors for " + targetName + ": Storing bare references " + obfData
/* 233 */               .values() + " in refMap");
/* 234 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 238 */       elem.printMessage(this.ap, Diagnostic.Kind.ERROR, conflictType + " reference conflict for " + targetName + ": " + reference + " -> " + ex
/* 239 */           .getNew() + " previously defined as " + ex.getOld());
/*     */     } 
/*     */     
/* 242 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInjectionPoint(AnnotatedElementInjectionPoint elem, String format) {
/* 250 */     if (this.mixin.isInterface()) {
/* 251 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", elem.getElement());
/*     */     }
/*     */     
/* 254 */     if (!elem.shouldRemap()) {
/*     */       return;
/*     */     }
/*     */     
/* 258 */     String type = InjectionPointData.parseType((String)elem.getAt().getValue("value"));
/* 259 */     String target = (String)elem.getAt().getValue("target");
/*     */     
/* 261 */     if ("NEW".equals(type)) {
/* 262 */       remapNewTarget(String.format(format, new Object[] { type + ".<target>" }), target, elem);
/* 263 */       remapNewTarget(String.format(format, new Object[] { type + ".args[class]" }), elem.getAtArg("class"), elem);
/*     */     } else {
/* 265 */       remapReference(String.format(format, new Object[] { type + ".<target>" }), target, elem);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void remapNewTarget(String subject, String reference, AnnotatedElementInjectionPoint elem) {
/* 270 */     if (reference == null) {
/*     */       return;
/*     */     }
/*     */     
/* 274 */     MemberInfo member = MemberInfo.parse(reference);
/* 275 */     String target = member.toCtorType();
/*     */     
/* 277 */     if (target != null) {
/* 278 */       String desc = member.toCtorDesc();
/* 279 */       MappingMethod m = new MappingMethod(target, ".", (desc != null) ? desc : "()V");
/* 280 */       ObfuscationData<MappingMethod> remapped = this.obf.getDataProvider().getRemappedMethod(m);
/* 281 */       if (remapped.isEmpty()) {
/* 282 */         this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + subject + " '" + target + "'", elem.getElement(), elem
/* 283 */             .getAnnotation().asMirror());
/*     */         
/*     */         return;
/*     */       } 
/* 287 */       ObfuscationData<String> mappings = new ObfuscationData<String>();
/* 288 */       for (ObfuscationType type : remapped) {
/* 289 */         MappingMethod mapping = (MappingMethod)remapped.get(type);
/* 290 */         if (desc == null) {
/* 291 */           mappings.put(type, mapping.getOwner()); continue;
/*     */         } 
/* 293 */         mappings.put(type, mapping.getDesc().replace(")V", ")L" + mapping.getOwner() + ";"));
/*     */       } 
/*     */ 
/*     */       
/* 297 */       this.obf.getReferenceManager().addClassMapping(this.classRef, reference, mappings);
/*     */     } 
/*     */     
/* 300 */     elem.notifyRemapped();
/*     */   }
/*     */   
/*     */   protected final void remapReference(String subject, String reference, AnnotatedElementInjectionPoint elem) {
/* 304 */     if (reference == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 309 */     AnnotationMirror errorsOn = ((this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT) ? elem.getAt() : elem.getAnnotation()).asMirror();
/*     */     
/* 311 */     MemberInfo targetMember = MemberInfo.parse(reference);
/* 312 */     if (!targetMember.isFullyQualified()) {
/* 313 */       String missing = (targetMember.owner == null) ? ((targetMember.desc == null) ? "owner and signature" : "owner") : "signature";
/* 314 */       this.ap.printMessage(Diagnostic.Kind.ERROR, subject + " is not fully qualified, missing " + missing, elem.getElement(), errorsOn);
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 319 */       targetMember.validate();
/* 320 */     } catch (InvalidMemberDescriptorException ex) {
/* 321 */       this.ap.printMessage(Diagnostic.Kind.ERROR, ex.getMessage(), elem.getElement(), errorsOn);
/*     */     } 
/*     */     
/*     */     try {
/* 325 */       if (targetMember.isField()) {
/* 326 */         ObfuscationData<MappingField> obfFieldData = this.obf.getDataProvider().getObfFieldRecursive(targetMember);
/* 327 */         if (obfFieldData.isEmpty()) {
/* 328 */           this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn);
/*     */           
/*     */           return;
/*     */         } 
/* 332 */         this.obf.getReferenceManager().addFieldMapping(this.classRef, reference, targetMember, obfFieldData);
/*     */       } else {
/* 334 */         ObfuscationData<MappingMethod> obfMethodData = this.obf.getDataProvider().getObfMethodRecursive(targetMember);
/* 335 */         if (obfMethodData.isEmpty() && (
/* 336 */           targetMember.owner == null || !targetMember.owner.startsWith("java/lang/"))) {
/* 337 */           this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + subject + " '" + reference + "'", elem.getElement(), errorsOn);
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 342 */         this.obf.getReferenceManager().addMethodMapping(this.classRef, reference, targetMember, obfMethodData);
/*     */       } 
/* 344 */     } catch (ReferenceConflictException ex) {
/*     */ 
/*     */       
/* 347 */       this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + subject + ": " + reference + " -> " + ex
/* 348 */           .getNew() + " previously defined as " + ex.getOld(), elem.getElement(), errorsOn);
/*     */       
/*     */       return;
/*     */     } 
/* 352 */     elem.notifyRemapped();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\AnnotatedMixinElementHandlerInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */