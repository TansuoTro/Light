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
/*    */ public class MultiANewArrayInsnNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public String desc;
/*    */   public int dims;
/*    */   
/*    */   public MultiANewArrayInsnNode(String desc, int dims) {
/* 63 */     super(197);
/* 64 */     this.desc = desc;
/* 65 */     this.dims = dims;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 70 */   public int getType() { return 13; }
/*    */ 
/*    */ 
/*    */   
/*    */   public void accept(MethodVisitor mv) {
/* 75 */     mv.visitMultiANewArrayInsn(this.desc, this.dims);
/* 76 */     acceptAnnotations(mv);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 81 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return (new MultiANewArrayInsnNode(this.desc, this.dims)).cloneAnnotations(this); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\MultiANewArrayInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */