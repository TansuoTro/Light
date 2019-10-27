/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
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
/*     */ 
/*     */ 
/*     */ public final class MethodSlices
/*     */ {
/*     */   private final InjectionInfo info;
/*     */   private final Map<String, MethodSlice> slices;
/*     */   
/*     */   private MethodSlices(InjectionInfo info) {
/*  50 */     this.slices = new HashMap(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     this.info = info;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void add(MethodSlice slice) {
/*  67 */     String id = this.info.getSliceId(slice.getId());
/*  68 */     if (this.slices.containsKey(id)) {
/*  69 */       throw new InvalidSliceException(this.info, slice + " has a duplicate id, '" + id + "' was already defined");
/*     */     }
/*  71 */     this.slices.put(id, slice);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public MethodSlice get(String id) { return (MethodSlice)this.slices.get(id); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public String toString() { return String.format("MethodSlices%s", new Object[] { this.slices.keySet() }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlices parse(InjectionInfo info) {
/* 100 */     MethodSlices slices = new MethodSlices(info);
/*     */     
/* 102 */     AnnotationNode annotation = info.getAnnotation();
/* 103 */     if (annotation != null) {
/* 104 */       for (AnnotationNode node : Annotations.getValue(annotation, "slice", true)) {
/* 105 */         MethodSlice slice = MethodSlice.parse(info, node);
/* 106 */         slices.add(slice);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return slices;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\code\MethodSlices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */