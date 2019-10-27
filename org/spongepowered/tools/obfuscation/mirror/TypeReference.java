/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import javax.annotation.processing.ProcessingEnvironment;
/*     */ import javax.lang.model.element.TypeElement;
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
/*     */ public class TypeReference
/*     */   extends Object
/*     */   implements Serializable, Comparable<TypeReference>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final String name;
/*     */   private TypeHandle handle;
/*     */   
/*     */   public TypeReference(TypeHandle handle) {
/*  55 */     this.name = handle.getName();
/*  56 */     this.handle = handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public TypeReference(String name) { this.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public String getClassName() { return this.name.replace('/', '.'); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TypeHandle getHandle(ProcessingEnvironment processingEnv) {
/*  90 */     if (this.handle == null) {
/*  91 */       TypeElement element = processingEnv.getElementUtils().getTypeElement(getClassName());
/*     */       try {
/*  93 */         this.handle = new TypeHandle(element);
/*  94 */       } catch (Exception ex) {
/*     */         
/*  96 */         ex.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/* 100 */     return this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 105 */   public String toString() { return String.format("TypeReference[%s]", new Object[] { this.name }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   public int compareTo(TypeReference other) { return (other == null) ? -1 : this.name.compareTo(other.name); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   public boolean equals(Object other) { return (other instanceof TypeReference && compareTo((TypeReference)other) == 0); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 120 */   public int hashCode() { return this.name.hashCode(); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mirror\TypeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */