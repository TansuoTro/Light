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
/*    */ public class IntInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int operand;
/*    */   
/*    */   public IntInsnNode(int opcode, int operand) {
/* 58 */     super(opcode);
/* 59 */     this.operand = operand;
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
/* 70 */   public void setOpcode(int opcode) { this.opcode = opcode; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public int getType() { return 1; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 80 */     mv.visitIntInsn(this.opcode, this.operand);
/* 81 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return (new IntInsnNode(this.opcode, this.operand)).cloneAnnotations(this); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\IntInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */