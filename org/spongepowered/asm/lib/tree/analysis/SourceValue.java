/*    */ package org.spongepowered.asm.lib.tree.analysis;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SourceValue
/*    */   implements Value
/*    */ {
/*    */   public final int size;
/*    */   public final Set<AbstractInsnNode> insns;
/*    */   
/* 67 */   public SourceValue(int size) { this(size, SmallSet.emptySet()); }
/*    */ 
/*    */   
/*    */   public SourceValue(int size, AbstractInsnNode insn) {
/* 71 */     this.size = size;
/* 72 */     this.insns = new SmallSet(insn, null);
/*    */   }
/*    */   
/*    */   public SourceValue(int size, Set<AbstractInsnNode> insns) {
/* 76 */     this.size = size;
/* 77 */     this.insns = insns;
/*    */   }
/*    */ 
/*    */   
/* 81 */   public int getSize() { return this.size; }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object value) {
/* 86 */     if (!(value instanceof SourceValue)) {
/* 87 */       return false;
/*    */     }
/* 89 */     SourceValue v = (SourceValue)value;
/* 90 */     return (this.size == v.size && this.insns.equals(v.insns));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 95 */   public int hashCode() { return this.insns.hashCode(); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\analysis\SourceValue.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */