/*    */ package org.spongepowered.asm.lib.commons;
/*    */ 
/*    */ import org.spongepowered.asm.lib.AnnotationVisitor;
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
/*    */ public class AnnotationRemapper
/*    */   extends AnnotationVisitor
/*    */ {
/*    */   protected final Remapper remapper;
/*    */   
/* 47 */   public AnnotationRemapper(AnnotationVisitor av, Remapper remapper) { this(327680, av, remapper); }
/*    */ 
/*    */ 
/*    */   
/*    */   protected AnnotationRemapper(int api, AnnotationVisitor av, Remapper remapper) {
/* 52 */     super(api, av);
/* 53 */     this.remapper = remapper;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void visit(String name, Object value) { this.av.visit(name, this.remapper.mapValue(value)); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 63 */   public void visitEnum(String name, String desc, String value) { this.av.visitEnum(name, this.remapper.mapDesc(desc), value); }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitAnnotation(String name, String desc) {
/* 68 */     AnnotationVisitor v = this.av.visitAnnotation(name, this.remapper.mapDesc(desc));
/* 69 */     return (v == null) ? null : ((v == this.av) ? this : new AnnotationRemapper(v, this.remapper));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AnnotationVisitor visitArray(String name) {
/* 75 */     AnnotationVisitor v = this.av.visitArray(name);
/* 76 */     return (v == null) ? null : ((v == this.av) ? this : new AnnotationRemapper(v, this.remapper));
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\commons\AnnotationRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */