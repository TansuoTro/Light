/*    */ package org.spongepowered.asm.lib.commons;
/*    */ 
/*    */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*    */ import org.spongepowered.asm.lib.FieldVisitor;
/*    */ import org.spongepowered.asm.lib.TypePath;
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
/*    */ public class FieldRemapper
/*    */   extends FieldVisitor
/*    */ {
/*    */   private final Remapper remapper;
/*    */   
/* 48 */   public FieldRemapper(FieldVisitor fv, Remapper remapper) { this(327680, fv, remapper); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected FieldRemapper(int api, FieldVisitor fv, Remapper remapper) {
/* 53 */     super(api, fv);
/* 54 */     this.remapper = remapper;
/*    */   }
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 59 */     AnnotationVisitor av = this.fv.visitAnnotation(this.remapper.mapDesc(desc), visible);
/*    */     
/* 61 */     return (av == null) ? null : new AnnotationRemapper(av, this.remapper);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 67 */     AnnotationVisitor av = super.visitTypeAnnotation(typeRef, typePath, this.remapper
/* 68 */         .mapDesc(desc), visible);
/* 69 */     return (av == null) ? null : new AnnotationRemapper(av, this.remapper);
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\commons\FieldRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */