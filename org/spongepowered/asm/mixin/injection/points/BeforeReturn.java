/*     */ package org.spongepowered.asm.mixin.injection.points;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
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
/*     */ @AtCode("RETURN")
/*     */ public class BeforeReturn
/*     */   extends InjectionPoint
/*     */ {
/*     */   private final int ordinal;
/*     */   
/*     */   public BeforeReturn(InjectionPointData data) {
/*  77 */     super(data);
/*     */     
/*  79 */     this.ordinal = data.getOrdinal();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public boolean checkPriority(int targetPriority, int ownerPriority) { return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/*  89 */     boolean found = false;
/*     */ 
/*     */     
/*  92 */     int returnOpcode = Type.getReturnType(desc).getOpcode(172);
/*  93 */     int ordinal = 0;
/*     */     
/*  95 */     ListIterator<AbstractInsnNode> iter = insns.iterator();
/*  96 */     while (iter.hasNext()) {
/*  97 */       AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/*     */       
/*  99 */       if (insn instanceof org.spongepowered.asm.lib.tree.InsnNode && insn.getOpcode() == returnOpcode) {
/* 100 */         if (this.ordinal == -1 || this.ordinal == ordinal) {
/* 101 */           nodes.add(insn);
/* 102 */           found = true;
/*     */         } 
/*     */         
/* 105 */         ordinal++;
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return found;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\points\BeforeReturn.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */