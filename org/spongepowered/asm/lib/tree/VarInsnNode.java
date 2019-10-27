/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.spongepowered.asm.lib.MethodVisitor;
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
/*    */ public class VarInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int var;
/*    */   
/*    */   public VarInsnNode(int opcode, int var) {
/* 63 */     super(opcode);
/* 64 */     this.var = var;
/*    */   }
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
/* 76 */   public void setOpcode(int opcode) { this.opcode = opcode; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 81 */   public int getType() { return 2; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 86 */     mv.visitVarInsn(this.opcode, this.var);
/* 87 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 92 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return (new VarInsnNode(this.opcode, this.var)).cloneAnnotations(this); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\VarInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */