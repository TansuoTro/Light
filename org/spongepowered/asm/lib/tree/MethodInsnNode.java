/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public String owner;
/*     */   public String name;
/*     */   public String desc;
/*     */   public boolean itf;
/*     */   
/*     */   @Deprecated
/*  85 */   public MethodInsnNode(int opcode, String owner, String name, String desc) { this(opcode, owner, name, desc, (opcode == 185)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodInsnNode(int opcode, String owner, String name, String desc, boolean itf) {
/* 108 */     super(opcode);
/* 109 */     this.owner = owner;
/* 110 */     this.name = name;
/* 111 */     this.desc = desc;
/* 112 */     this.itf = itf;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 123 */   public void setOpcode(int opcode) { this.opcode = opcode; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 128 */   public int getType() { return 5; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor mv) {
/* 133 */     mv.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
/* 134 */     acceptAnnotations(mv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 139 */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) { return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\MethodInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */