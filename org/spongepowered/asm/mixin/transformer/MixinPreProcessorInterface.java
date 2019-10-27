/*    */ package org.spongepowered.asm.mixin.transformer;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.FieldNode;
/*    */ import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
/*    */ import org.spongepowered.asm.util.Bytecode;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MixinPreProcessorInterface
/*    */   extends MixinPreProcessorStandard
/*    */ {
/* 49 */   MixinPreProcessorInterface(MixinInfo mixin, MixinInfo.MixinClassNode classNode) { super(mixin, classNode); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethod, ClassInfo.Method method) {
/* 60 */     if (!Bytecode.hasFlag(mixinMethod, 1) && !Bytecode.hasFlag(mixinMethod, 4096)) {
/* 61 */       throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
/*    */     }
/*    */ 
/*    */     
/* 65 */     super.prepareMethod(mixinMethod, method);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean validateField(MixinTargetContext context, FieldNode field, AnnotationNode shadow) {
/* 77 */     if (!Bytecode.hasFlag(field, 8)) {
/* 78 */       throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + field.name + " in " + this.mixin);
/*    */     }
/*    */ 
/*    */     
/* 82 */     return super.validateField(context, field, shadow);
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\MixinPreProcessorInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */