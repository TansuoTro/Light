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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/* 64 */   public InsnNode(int opcode) { super(opcode); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 69 */   public int getType() { return 0; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 80 */     mv.visitInsn(this.opcode);
/* 81 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 86 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return (new InsnNode(this.opcode)).cloneAnnotations(this); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\InsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */