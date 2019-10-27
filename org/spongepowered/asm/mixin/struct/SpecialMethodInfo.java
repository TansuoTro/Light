/*     */ package org.spongepowered.asm.mixin.struct;
/*     */ 
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpecialMethodInfo
/*     */   implements IInjectionPointContext
/*     */ {
/*     */   protected final AnnotationNode annotation;
/*     */   protected final ClassNode classNode;
/*     */   protected final MethodNode method;
/*     */   protected final MixinTargetContext mixin;
/*     */   
/*     */   public SpecialMethodInfo(MixinTargetContext mixin, MethodNode method, AnnotationNode annotation) {
/*  60 */     this.mixin = mixin;
/*  61 */     this.method = method;
/*  62 */     this.annotation = annotation;
/*  63 */     this.classNode = mixin.getTargetClassNode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   public final IMixinContext getContext() { return this.mixin; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  83 */   public final AnnotationNode getAnnotation() { return this.annotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public final ClassNode getClassNode() { return this.classNode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public final MethodNode getMethod() { return this.method; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\struct\SpecialMethodInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */