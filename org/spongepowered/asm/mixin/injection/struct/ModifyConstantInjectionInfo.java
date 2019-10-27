/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.code.Injector;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.ModifyConstantInjector;
/*    */ import org.spongepowered.asm.mixin.injection.points.BeforeConstant;
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
/*    */ 
/*    */ public class ModifyConstantInjectionInfo
/*    */   extends InjectionInfo
/*    */ {
/* 46 */   private static final String CONSTANT_ANNOTATION_CLASS = org.spongepowered.asm.mixin.injection.Constant.class.getName().replace('.', '/');
/*    */ 
/*    */   
/* 49 */   public ModifyConstantInjectionInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) { super(mixin, method, annotation, "constant"); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected List<AnnotationNode> readInjectionPoints(String type) {
/* 54 */     ImmutableList immutableList = super.readInjectionPoints(type);
/* 55 */     if (immutableList.isEmpty()) {
/* 56 */       AnnotationNode c = new AnnotationNode(CONSTANT_ANNOTATION_CLASS);
/* 57 */       c.visit("log", Boolean.TRUE);
/* 58 */       immutableList = ImmutableList.of(c);
/*    */     } 
/* 60 */     return immutableList;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void parseInjectionPoints(List<AnnotationNode> ats) {
/* 65 */     Type returnType = Type.getReturnType(this.method.desc);
/*    */     
/* 67 */     for (AnnotationNode at : ats) {
/* 68 */       this.injectionPoints.add(new BeforeConstant(getContext(), at, returnType.getDescriptor()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 74 */   protected Injector parseInjector(AnnotationNode injectAnnotation) { return new ModifyConstantInjector(this); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   protected String getDescription() { return "Constant modifier method"; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public String getSliceId(String id) { return Strings.nullToEmpty(id); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\ModifyConstantInjectionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */