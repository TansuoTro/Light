/*    */ package org.spongepowered.asm.transformers;
/*    */ 
/*    */ import org.spongepowered.asm.lib.ClassReader;
/*    */ import org.spongepowered.asm.lib.ClassWriter;
/*    */ import org.spongepowered.asm.mixin.transformer.ClassInfo;
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
/*    */ public class MixinClassWriter
/*    */   extends ClassWriter
/*    */ {
/* 38 */   public MixinClassWriter(int flags) { super(flags); }
/*    */ 
/*    */ 
/*    */   
/* 42 */   public MixinClassWriter(ClassReader classReader, int flags) { super(classReader, flags); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 51 */   protected String getCommonSuperClass(String type1, String type2) { return ClassInfo.getCommonSuperClass(type1, type2).getName(); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\transformers\MixinClassWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */