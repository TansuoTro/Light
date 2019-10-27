/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*    */ public class RedirectInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 39 */   public RedirectInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) { super(mixin, method, annotation); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   protected Injector parseInjector(AnnotationNode injectAnnotation) { return new RedirectInjector(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 49 */   protected String getDescription() { return "Redirector"; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\RedirectInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */