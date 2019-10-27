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
/*    */ public class LineNumberNode
/*    */   extends AbstractInsnNode
/*    */ {
/*    */   public int line;
/*    */   public LabelNode start;
/*    */   
/*    */   public LineNumberNode(int line, LabelNode start) {
/* 65 */     super(-1);
/* 66 */     this.line = line;
/* 67 */     this.start = start;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public int getType() { return 15; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public void accept(MethodVisitor mv) { mv.visitLineNumber(this.line, this.start.getLabel()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return new LineNumberNode(this.line, clone(this.start, labels)); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\LineNumberNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */