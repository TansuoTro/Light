/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.Label;
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
/*     */ public class LookupSwitchInsnNode
/*     */   extends AbstractInsnNode
/*     */ {
/*     */   public LabelNode dflt;
/*     */   public List<Integer> keys;
/*     */   public List<LabelNode> labels;
/*     */   
/*     */   public LookupSwitchInsnNode(LabelNode dflt, int[] keys, LabelNode[] labels) {
/*  77 */     super(171);
/*  78 */     this.dflt = dflt;
/*  79 */     this.keys = new ArrayList((keys == null) ? 0 : keys.length);
/*  80 */     this.labels = new ArrayList((labels == null) ? 0 : labels.length);
/*     */     
/*  82 */     if (keys != null) {
/*  83 */       for (int i = 0; i < keys.length; i++) {
/*  84 */         this.keys.add(Integer.valueOf(keys[i]));
/*     */       }
/*     */     }
/*  87 */     if (labels != null) {
/*  88 */       this.labels.addAll(Arrays.asList(labels));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  94 */   public int getType() { return 12; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor mv) {
/*  99 */     int[] keys = new int[this.keys.size()];
/* 100 */     for (int i = 0; i < keys.length; i++) {
/* 101 */       keys[i] = ((Integer)this.keys.get(i)).intValue();
/*     */     }
/* 103 */     Label[] labels = new Label[this.labels.size()];
/* 104 */     for (int i = 0; i < labels.length; i++) {
/* 105 */       labels[i] = ((LabelNode)this.labels.get(i)).getLabel();
/*     */     }
/* 107 */     mv.visitLookupSwitchInsn(this.dflt.getLabel(), keys, labels);
/* 108 */     acceptAnnotations(mv);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode clone(Map<LabelNode, LabelNode> labels) {
/* 114 */     LookupSwitchInsnNode clone = new LookupSwitchInsnNode(clone(this.dflt, labels), null, clone(this.labels, labels));
/* 115 */     clone.keys.addAll(this.keys);
/* 116 */     return clone.cloneAnnotations(this);
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\LookupSwitchInsnNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */