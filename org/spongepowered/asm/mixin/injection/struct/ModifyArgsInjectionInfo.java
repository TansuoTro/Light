/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
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
/*    */ 
/*    */ public class ModifyArgsInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 40 */   public ModifyArgsInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) { super(mixin, method, annotation); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   protected Injector parseInjector(AnnotationNode injectAnnotation) { return new ModifyArgsInjector(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   protected String getDescription() { return "Multi-argument modifier method"; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyArgsInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */