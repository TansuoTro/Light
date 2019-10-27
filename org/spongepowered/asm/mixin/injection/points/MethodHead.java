/*    */ package org.spongepowered.asm.mixin.injection.points;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*    */ import org.spongepowered.asm.lib.tree.InsnList;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*    */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @AtCode("HEAD")
/*    */ public class MethodHead
/*    */   extends InjectionPoint
/*    */ {
/* 50 */   public MethodHead(InjectionPointData data) { super(data); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public boolean checkPriority(int targetPriority, int ownerPriority) { return true; }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean find(String desc, InsnList insns, Collection<AbstractInsnNode> nodes) {
/* 60 */     nodes.add(insns.getFirst());
/* 61 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\points\MethodHead.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */