/*    */ package org.spongepowered.tools.obfuscation.validation;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javax.lang.model.element.ElementKind;
/*    */ import javax.lang.model.element.Modifier;
/*    */ import javax.lang.model.element.TypeElement;
/*    */ import org.spongepowered.tools.obfuscation.MixinValidator;
/*    */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*    */ import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
/*    */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
/*    */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
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
/*    */ public class ParentValidator
/*    */   extends MixinValidator
/*    */ {
/* 49 */   public ParentValidator(IMixinAnnotationProcessor ap) { super(ap, IMixinValidator.ValidationPass.EARLY); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean validate(TypeElement mixin, AnnotationHandle annotation, Collection<TypeHandle> targets) {
/* 60 */     if (mixin.getEnclosingElement().getKind() != ElementKind.PACKAGE && !mixin.getModifiers().contains(Modifier.STATIC)) {
/* 61 */       error("Inner class mixin must be declared static", mixin);
/*    */     }
/*    */     
/* 64 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\validation\ParentValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */