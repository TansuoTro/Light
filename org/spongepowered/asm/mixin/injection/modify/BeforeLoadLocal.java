/*     */ package org.spongepowered.asm.mixin.injection.modify;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.ListIterator;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint.AtCode;
/*     */ import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
/*     */ import org.spongepowered.asm.mixin.injection.struct.Target;
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
/*     */ @AtCode("LOAD")
/*     */ public class BeforeLoadLocal
/*     */   extends ModifyVariableInjector.ContextualInjectionPoint
/*     */ {
/*     */   private final Type returnType;
/*     */   private final LocalVariableDiscriminator discriminator;
/*     */   private final int opcode;
/*     */   private final int ordinal;
/*     */   private boolean opcodeAfter;
/*     */   
/*     */   static class SearchState
/*     */   {
/*     */     private final boolean print;
/*     */     private final int targetOrdinal;
/*     */     private int ordinal;
/*     */     private boolean pendingCheck;
/*     */     private boolean found;
/*     */     private VarInsnNode varNode;
/*     */     
/*     */     SearchState(int targetOrdinal, boolean print) {
/*  96 */       this.ordinal = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       this.pendingCheck = false;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       this.found = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 115 */       this.targetOrdinal = targetOrdinal;
/* 116 */       this.print = print;
/*     */     }
/*     */ 
/*     */     
/* 120 */     boolean success() { return this.found; }
/*     */ 
/*     */ 
/*     */     
/* 124 */     boolean isPendingCheck() { return this.pendingCheck; }
/*     */ 
/*     */ 
/*     */     
/* 128 */     void setPendingCheck() { this.pendingCheck = true; }
/*     */ 
/*     */ 
/*     */     
/* 132 */     void register(VarInsnNode node) { this.varNode = node; }
/*     */ 
/*     */     
/*     */     void check(Collection<AbstractInsnNode> nodes, AbstractInsnNode insn, int local) {
/* 136 */       this.pendingCheck = false;
/* 137 */       if (local != this.varNode.var && (local > -2 || !this.print)) {
/*     */         return;
/*     */       }
/*     */       
/* 141 */       if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
/* 142 */         nodes.add(insn);
/* 143 */         this.found = true;
/*     */       } 
/*     */       
/* 146 */       this.ordinal++;
/* 147 */       this.varNode = null;
/*     */     }
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
/* 180 */   protected BeforeLoadLocal(InjectionPointData data) { this(data, 21, false); }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BeforeLoadLocal(InjectionPointData data, int opcode, boolean opcodeAfter) {
/* 185 */     super(data.getContext());
/* 186 */     this.returnType = data.getMethodReturnType();
/* 187 */     this.discriminator = data.getLocalVariableDiscriminator();
/* 188 */     this.opcode = data.getOpcode(this.returnType.getOpcode(opcode));
/* 189 */     this.ordinal = data.getOrdinal();
/* 190 */     this.opcodeAfter = opcodeAfter;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean find(Target target, Collection<AbstractInsnNode> nodes) {
/* 195 */     SearchState state = new SearchState(this.ordinal, this.discriminator.printLVT());
/*     */     
/* 197 */     ListIterator<AbstractInsnNode> iter = target.method.instructions.iterator();
/* 198 */     while (iter.hasNext()) {
/* 199 */       AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/* 200 */       if (state.isPendingCheck()) {
/* 201 */         int local = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, insn);
/* 202 */         state.check(nodes, insn, local); continue;
/* 203 */       }  if (insn instanceof VarInsnNode && insn.getOpcode() == this.opcode && (this.ordinal == -1 || !state.success())) {
/* 204 */         state.register((VarInsnNode)insn);
/* 205 */         if (this.opcodeAfter) {
/* 206 */           state.setPendingCheck(); continue;
/*     */         } 
/* 208 */         int local = this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, insn);
/* 209 */         state.check(nodes, insn, local);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 214 */     return state.success();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\modify\BeforeLoadLocal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */