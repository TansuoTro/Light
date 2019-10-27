/*     */ package org.spongepowered.asm.mixin.injection.invoke.util;
/*     */ 
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Analyzer;
/*     */ import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
/*     */ import org.spongepowered.asm.lib.tree.analysis.BasicInterpreter;
/*     */ import org.spongepowered.asm.lib.tree.analysis.BasicValue;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Frame;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Interpreter;
/*     */ import org.spongepowered.asm.lib.tree.analysis.Value;
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
/*     */ public class InsnFinder
/*     */ {
/*     */   static class AnalysisResultException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private AbstractInsnNode result;
/*     */     
/*  53 */     public AnalysisResultException(AbstractInsnNode popNode) { this.result = popNode; }
/*     */ 
/*     */ 
/*     */     
/*  57 */     public AbstractInsnNode getResult() { return this.result; }
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
/*     */   enum AnalyzerState
/*     */   {
/*  70 */     SEARCH,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     ANALYSE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     COMPLETE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class PopAnalyzer
/*     */     extends Analyzer<BasicValue>
/*     */   {
/*     */     protected final AbstractInsnNode node;
/*     */ 
/*     */ 
/*     */     
/*     */     class PopFrame
/*     */       extends Frame<BasicValue>
/*     */     {
/*     */       private AbstractInsnNode current;
/*     */ 
/*     */       
/*  99 */       private InsnFinder.AnalyzerState state = InsnFinder.AnalyzerState.SEARCH;
/* 100 */       private int depth = 0;
/*     */ 
/*     */       
/* 103 */       public PopFrame(int locals, int stack) { super(locals, stack); }
/*     */ 
/*     */ 
/*     */       
/*     */       public void execute(AbstractInsnNode insn, Interpreter<BasicValue> interpreter) throws AnalyzerException {
/* 108 */         this.current = insn;
/* 109 */         super.execute(insn, interpreter);
/*     */       }
/*     */ 
/*     */       
/*     */       public void push(BasicValue value) throws IndexOutOfBoundsException {
/* 114 */         if (this.current == InsnFinder.PopAnalyzer.this.node && this.state == InsnFinder.AnalyzerState.SEARCH) {
/* 115 */           this.state = InsnFinder.AnalyzerState.ANALYSE;
/* 116 */           this.depth++;
/* 117 */         } else if (this.state == InsnFinder.AnalyzerState.ANALYSE) {
/* 118 */           this.depth++;
/*     */         } 
/* 120 */         super.push(value);
/*     */       }
/*     */ 
/*     */       
/*     */       public BasicValue pop() throws IndexOutOfBoundsException {
/* 125 */         if (this.state == InsnFinder.AnalyzerState.ANALYSE && 
/* 126 */           --this.depth == 0) {
/* 127 */           this.state = InsnFinder.AnalyzerState.COMPLETE;
/* 128 */           throw new InsnFinder.AnalysisResultException(this.current);
/*     */         } 
/*     */         
/* 131 */         return (BasicValue)super.pop();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public PopAnalyzer(AbstractInsnNode node) {
/* 139 */       super(new BasicInterpreter());
/* 140 */       this.node = node;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     protected Frame<BasicValue> newFrame(int locals, int stack) { return new PopFrame(locals, stack); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 152 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractInsnNode findPopInsn(Target target, AbstractInsnNode node) {
/*     */     try {
/* 164 */       (new PopAnalyzer(node)).analyze(target.classNode.name, target.method);
/* 165 */     } catch (AnalyzerException ex) {
/* 166 */       if (ex.getCause() instanceof AnalysisResultException) {
/* 167 */         return ((AnalysisResultException)ex.getCause()).getResult();
/*     */       }
/* 169 */       logger.catching(ex);
/*     */     } 
/* 171 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\invok\\util\InsnFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */