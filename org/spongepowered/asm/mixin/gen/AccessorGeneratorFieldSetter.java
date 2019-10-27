/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnNode;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.lib.tree.VarInsnNode;
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
/*    */ public class AccessorGeneratorFieldSetter
/*    */   extends AccessorGeneratorField
/*    */ {
/* 39 */   public AccessorGeneratorFieldSetter(AccessorInfo info) { super(info); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 47 */     int stackSpace = this.isInstanceField ? 1 : 0;
/* 48 */     int maxLocals = stackSpace + this.targetType.getSize();
/* 49 */     int maxStack = stackSpace + this.targetType.getSize();
/* 50 */     MethodNode method = createMethod(maxLocals, maxStack);
/* 51 */     if (this.isInstanceField) {
/* 52 */       method.instructions.add(new VarInsnNode(25, 0));
/*    */     }
/* 54 */     method.instructions.add(new VarInsnNode(this.targetType.getOpcode(21), stackSpace));
/* 55 */     int opcode = this.isInstanceField ? 181 : 179;
/* 56 */     method.instructions.add(new FieldInsnNode(opcode, (this.info.getClassNode()).name, this.targetField.name, this.targetField.desc));
/* 57 */     method.instructions.add(new InsnNode(177));
/* 58 */     return method;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorFieldSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */