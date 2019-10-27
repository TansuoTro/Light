/*    */ package org.spongepowered.asm.lib.tree;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.spongepowered.asm.lib.Label;
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
/*    */ public class LabelNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   private Label label;
/*    */   
/* 45 */   public LabelNode() { super(-1); }
/*    */ 
/*    */   
/*    */   public LabelNode(Label label) {
/* 49 */     super(-1);
/* 50 */     this.label = label;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 55 */   public int getType() { return 8; }
/*    */ 
/*    */   
/*    */   public Label getLabel() {
/* 59 */     if (this.label == null) {
/* 60 */       this.label = new Label();
/*    */     }
/* 62 */     return this.label;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 67 */   public void accept(MethodVisitor cv) { cv.visitLabel(getLabel()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return (AbstractInsnNode)labels.get(this); }
/*    */ 
/*    */ 
/*    */   
/* 76 */   public void resetLabel() { this.label = null; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\LabelNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */