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
/*    */ public class AccessorGeneratorFieldGetter
/*    */   extends AccessorGeneratorField
/*    */ {
/* 39 */   public AccessorGeneratorFieldGetter(AccessorInfo info) { super(info); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MethodNode generate() {
/* 47 */     MethodNode method = createMethod(this.targetType.getSize(), this.targetType.getSize());
/* 48 */     if (this.isInstanceField) {
/* 49 */       method.instructions.add(new VarInsnNode(25, 0));
/*    */     }
/* 51 */     int opcode = this.isInstanceField ? 180 : 178;
/* 52 */     method.instructions.add(new FieldInsnNode(opcode, (this.info.getClassNode()).name, this.targetField.name, this.targetField.desc));
/* 53 */     method.instructions.add(new InsnNode(this.targetType.getOpcode(172)));
/* 54 */     return method;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\gen\AccessorGeneratorFieldGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */