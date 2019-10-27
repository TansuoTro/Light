/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
/*     */ import org.spongepowered.asm.util.Annotations;
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
/*     */ public class InjectorTarget
/*     */ {
/*     */   private final ISliceContext context;
/*     */   private final Map<String, ReadOnlyInsnList> cache;
/*     */   private final Target target;
/*     */   private final String mergedBy;
/*     */   private final int mergedPriority;
/*     */   
/*     */   public InjectorTarget(ISliceContext context, Target target) {
/*  53 */     this.cache = new HashMap();
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
/*  71 */     this.context = context;
/*  72 */     this.target = target;
/*     */     
/*  74 */     AnnotationNode merged = Annotations.getVisible(target.method, org.spongepowered.asm.mixin.transformer.meta.MixinMerged.class);
/*  75 */     this.mergedBy = (String)Annotations.getValue(merged, "mixin");
/*  76 */     this.mergedPriority = ((Integer)Annotations.getValue(merged, "priority", Integer.valueOf(1000))).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  81 */   public String toString() { return this.target.toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   public Target getTarget() { return this.target; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public MethodNode getMethod() { return this.target.method; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 102 */   public boolean isMerged() { return (this.mergedBy != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public String getMergedBy() { return this.mergedBy; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public int getMergedPriority() { return this.mergedPriority; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsnList getSlice(String id) {
/* 128 */     ReadOnlyInsnList slice = (ReadOnlyInsnList)this.cache.get(id);
/* 129 */     if (slice == null) {
/* 130 */       MethodSlice sliceInfo = this.context.getSlice(id);
/* 131 */       if (sliceInfo != null) {
/* 132 */         slice = sliceInfo.getSlice(this.target.method);
/*     */       } else {
/*     */         
/* 135 */         slice = new ReadOnlyInsnList(this.target.method.instructions);
/*     */       } 
/* 137 */       this.cache.put(id, slice);
/*     */     } 
/*     */     
/* 140 */     return slice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public InsnList getSlice(InjectionPoint injectionPoint) { return getSlice(injectionPoint.getSlice()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 157 */     for (ReadOnlyInsnList insns : this.cache.values()) {
/* 158 */       insns.dispose();
/*     */     }
/*     */     
/* 161 */     this.cache.clear();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\code\InjectorTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */