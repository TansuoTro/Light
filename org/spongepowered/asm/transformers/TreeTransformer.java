/*    */ package org.spongepowered.asm.transformers;
/*    */ 
/*    */ import org.spongepowered.asm.lib.ClassReader;
/*    */ import org.spongepowered.asm.lib.ClassWriter;
/*    */ import org.spongepowered.asm.lib.tree.ClassNode;
/*    */ import org.spongepowered.asm.service.ILegacyClassTransformer;
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
/*    */ public abstract class TreeTransformer
/*    */   implements ILegacyClassTransformer
/*    */ {
/*    */   private ClassReader classReader;
/*    */   private ClassNode classNode;
/*    */   
/* 45 */   protected final ClassNode readClass(byte[] basicClass) { return readClass(basicClass, true); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final ClassNode readClass(byte[] basicClass, boolean cacheReader) {
/* 55 */     ClassReader classReader = new ClassReader(basicClass);
/* 56 */     if (cacheReader) {
/* 57 */       this.classReader = classReader;
/*    */     }
/*    */     
/* 60 */     ClassNode classNode = new ClassNode();
/* 61 */     classReader.accept(classNode, 8);
/* 62 */     return classNode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected final byte[] writeClass(ClassNode classNode) {
/* 71 */     if (this.classReader != null && this.classNode == classNode) {
/* 72 */       this.classNode = null;
/* 73 */       ClassWriter writer = new MixinClassWriter(this.classReader, 3);
/* 74 */       this.classReader = null;
/* 75 */       classNode.accept(writer);
/* 76 */       return writer.toByteArray();
/*    */     } 
/*    */     
/* 79 */     this.classNode = null;
/*    */     
/* 81 */     ClassWriter writer = new MixinClassWriter(3);
/* 82 */     classNode.accept(writer);
/* 83 */     return writer.toByteArray();
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\transformers\TreeTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */