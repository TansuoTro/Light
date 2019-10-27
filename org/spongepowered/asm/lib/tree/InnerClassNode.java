/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import org.spongepowered.asm.lib.ClassVisitor;
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
/*    */ public class InnerClassNode
/*    */ {
/*    */   public String name;
/*    */   public String outerName;
/*    */   public String innerName;
/*    */   public int access;
/*    */   
/*    */   public InnerClassNode(String name, String outerName, String innerName, int access) {
/* 86 */     this.name = name;
/* 87 */     this.outerName = outerName;
/* 88 */     this.innerName = innerName;
/* 89 */     this.access = access;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 99 */   public void accept(ClassVisitor cv) { cv.visitInnerClass(this.name, this.outerName, this.innerName, this.access); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\InnerClassNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */