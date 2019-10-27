/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
/*    */ import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
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
/*    */ public class ModifyVariableInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 41 */   public ModifyVariableInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) { super(mixin, method, annotation); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   protected Injector parseInjector(AnnotationNode injectAnnotation) { return new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(injectAnnotation)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   protected String getDescription() { return "Variable modifier method"; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyVariableInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */