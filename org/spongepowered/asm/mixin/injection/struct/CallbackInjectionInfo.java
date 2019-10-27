/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInjector;
/*    */ import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
/*    */ import org.spongepowered.asm.util.Annotations;
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
/*    */ public class CallbackInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 44 */   protected CallbackInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) { super(mixin, method, annotation); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Injector parseInjector(AnnotationNode injectAnnotation) {
/* 49 */     boolean cancellable = ((Boolean)Annotations.getValue(injectAnnotation, "cancellable", Boolean.FALSE)).booleanValue();
/* 50 */     LocalCapture locals = (LocalCapture)Annotations.getValue(injectAnnotation, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
/* 51 */     String identifier = (String)Annotations.getValue(injectAnnotation, "id", "");
/*    */     
/* 53 */     return new CallbackInjector(this, cancellable, locals, identifier);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public String getSliceId(String id) { return Strings.nullToEmpty(id); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\CallbackInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */