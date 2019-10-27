/*     */ package org.spongepowered.asm.lib.tree.analysis;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.Opcodes;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.IincInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.LabelNode;
/*     */ import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
/*     */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Analyzer<V extends Value>
/*     */   extends Object
/*     */   implements Opcodes
/*     */ {
/*     */   private final Interpreter<V> interpreter;
/*     */   private int n;
/*     */   private InsnList insns;
/*     */   private List<TryCatchBlockNode>[] handlers;
/*     */   private Frame<V>[] frames;
/*     */   private Subroutine[] subroutines;
/*     */   private boolean[] queued;
/*     */   private int[] queue;
/*     */   private int top;
/*     */   
/*  87 */   public Analyzer(Interpreter<V> interpreter) { this.interpreter = interpreter; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Frame<V>[] analyze(String owner, MethodNode m) throws AnalyzerException {
/* 108 */     if ((m.access & 0x500) != 0) {
/* 109 */       this.frames = (Frame[])new Frame[0];
/* 110 */       return this.frames;
/*     */     } 
/* 112 */     this.n = m.instructions.size();
/* 113 */     this.insns = m.instructions;
/* 114 */     this.handlers = (List[])new List[this.n];
/* 115 */     this.frames = (Frame[])new Frame[this.n];
/* 116 */     this.subroutines = new Subroutine[this.n];
/* 117 */     this.queued = new boolean[this.n];
/* 118 */     this.queue = new int[this.n];
/* 119 */     this.top = 0;
/*     */ 
/*     */     
/* 122 */     for (int i = 0; i < m.tryCatchBlocks.size(); i++) {
/* 123 */       TryCatchBlockNode tcb = (TryCatchBlockNode)m.tryCatchBlocks.get(i);
/* 124 */       int begin = this.insns.indexOf(tcb.start);
/* 125 */       int end = this.insns.indexOf(tcb.end);
/* 126 */       for (int j = begin; j < end; j++) {
/* 127 */         List<TryCatchBlockNode> insnHandlers = this.handlers[j];
/* 128 */         if (insnHandlers == null) {
/* 129 */           insnHandlers = new ArrayList<TryCatchBlockNode>();
/* 130 */           this.handlers[j] = insnHandlers;
/*     */         } 
/* 132 */         insnHandlers.add(tcb);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 137 */     Subroutine main = new Subroutine(null, m.maxLocals, null);
/* 138 */     List<AbstractInsnNode> subroutineCalls = new ArrayList<AbstractInsnNode>();
/* 139 */     Map<LabelNode, Subroutine> subroutineHeads = new HashMap<LabelNode, Subroutine>();
/* 140 */     findSubroutine(0, main, subroutineCalls);
/* 141 */     while (!subroutineCalls.isEmpty()) {
/* 142 */       JumpInsnNode jsr = (JumpInsnNode)subroutineCalls.remove(0);
/* 143 */       Subroutine sub = (Subroutine)subroutineHeads.get(jsr.label);
/* 144 */       if (sub == null) {
/* 145 */         sub = new Subroutine(jsr.label, m.maxLocals, jsr);
/* 146 */         subroutineHeads.put(jsr.label, sub);
/* 147 */         findSubroutine(this.insns.indexOf(jsr.label), sub, subroutineCalls); continue;
/*     */       } 
/* 149 */       sub.callers.add(jsr);
/*     */     } 
/*     */     
/* 152 */     for (int i = 0; i < this.n; i++) {
/* 153 */       if (this.subroutines[i] != null && (this.subroutines[i]).start == null) {
/* 154 */         this.subroutines[i] = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 159 */     Frame<V> current = newFrame(m.maxLocals, m.maxStack);
/* 160 */     Frame<V> handler = newFrame(m.maxLocals, m.maxStack);
/* 161 */     current.setReturn(this.interpreter.newValue(Type.getReturnType(m.desc)));
/* 162 */     Type[] args = Type.getArgumentTypes(m.desc);
/* 163 */     int local = 0;
/* 164 */     if ((m.access & 0x8) == 0) {
/* 165 */       Type ctype = Type.getObjectType(owner);
/* 166 */       current.setLocal(local++, this.interpreter.newValue(ctype));
/*     */     } 
/* 168 */     for (int i = 0; i < args.length; i++) {
/* 169 */       current.setLocal(local++, this.interpreter.newValue(args[i]));
/* 170 */       if (args[i].getSize() == 2) {
/* 171 */         current.setLocal(local++, this.interpreter.newValue(null));
/*     */       }
/*     */     } 
/* 174 */     while (local < m.maxLocals) {
/* 175 */       current.setLocal(local++, this.interpreter.newValue(null));
/*     */     }
/* 177 */     merge(0, current, null);
/*     */     
/* 179 */     init(owner, m);
/*     */ 
/*     */     
/* 182 */     while (this.top > 0) {
/* 183 */       int insn = this.queue[--this.top];
/* 184 */       Frame<V> f = this.frames[insn];
/* 185 */       Subroutine subroutine = this.subroutines[insn];
/* 186 */       this.queued[insn] = false;
/*     */       
/* 188 */       AbstractInsnNode insnNode = null;
/*     */       try {
/* 190 */         insnNode = m.instructions.get(insn);
/* 191 */         int insnOpcode = insnNode.getOpcode();
/* 192 */         int insnType = insnNode.getType();
/*     */         
/* 194 */         if (insnType == 8 || insnType == 15 || insnType == 14) {
/*     */ 
/*     */           
/* 197 */           merge(insn + 1, f, subroutine);
/* 198 */           newControlFlowEdge(insn, insn + 1);
/*     */         } else {
/* 200 */           current.init(f).execute(insnNode, this.interpreter);
/* 201 */           subroutine = (subroutine == null) ? null : subroutine.copy();
/*     */           
/* 203 */           if (insnNode instanceof JumpInsnNode) {
/* 204 */             JumpInsnNode j = (JumpInsnNode)insnNode;
/* 205 */             if (insnOpcode != 167 && insnOpcode != 168) {
/* 206 */               merge(insn + 1, current, subroutine);
/* 207 */               newControlFlowEdge(insn, insn + 1);
/*     */             } 
/* 209 */             int jump = this.insns.indexOf(j.label);
/* 210 */             if (insnOpcode == 168) {
/* 211 */               merge(jump, current, new Subroutine(j.label, m.maxLocals, j));
/*     */             } else {
/*     */               
/* 214 */               merge(jump, current, subroutine);
/*     */             } 
/* 216 */             newControlFlowEdge(insn, jump);
/* 217 */           } else if (insnNode instanceof LookupSwitchInsnNode) {
/* 218 */             LookupSwitchInsnNode lsi = (LookupSwitchInsnNode)insnNode;
/* 219 */             int jump = this.insns.indexOf(lsi.dflt);
/* 220 */             merge(jump, current, subroutine);
/* 221 */             newControlFlowEdge(insn, jump);
/* 222 */             for (int j = 0; j < lsi.labels.size(); j++) {
/* 223 */               LabelNode label = (LabelNode)lsi.labels.get(j);
/* 224 */               jump = this.insns.indexOf(label);
/* 225 */               merge(jump, current, subroutine);
/* 226 */               newControlFlowEdge(insn, jump);
/*     */             } 
/* 228 */           } else if (insnNode instanceof TableSwitchInsnNode) {
/* 229 */             TableSwitchInsnNode tsi = (TableSwitchInsnNode)insnNode;
/* 230 */             int jump = this.insns.indexOf(tsi.dflt);
/* 231 */             merge(jump, current, subroutine);
/* 232 */             newControlFlowEdge(insn, jump);
/* 233 */             for (int j = 0; j < tsi.labels.size(); j++) {
/* 234 */               LabelNode label = (LabelNode)tsi.labels.get(j);
/* 235 */               jump = this.insns.indexOf(label);
/* 236 */               merge(jump, current, subroutine);
/* 237 */               newControlFlowEdge(insn, jump);
/*     */             } 
/* 239 */           } else if (insnOpcode == 169) {
/* 240 */             if (subroutine == null) {
/* 241 */               throw new AnalyzerException(insnNode, "RET instruction outside of a sub routine");
/*     */             }
/*     */             
/* 244 */             for (int i = 0; i < subroutine.callers.size(); i++) {
/* 245 */               JumpInsnNode caller = (JumpInsnNode)subroutine.callers.get(i);
/* 246 */               int call = this.insns.indexOf(caller);
/* 247 */               if (this.frames[call] != null) {
/* 248 */                 merge(call + 1, this.frames[call], current, this.subroutines[call], subroutine.access);
/*     */                 
/* 250 */                 newControlFlowEdge(insn, call + 1);
/*     */               } 
/*     */             } 
/* 253 */           } else if (insnOpcode != 191 && (insnOpcode < 172 || insnOpcode > 177)) {
/*     */             
/* 255 */             if (subroutine != null) {
/* 256 */               if (insnNode instanceof VarInsnNode) {
/* 257 */                 int var = ((VarInsnNode)insnNode).var;
/* 258 */                 subroutine.access[var] = true;
/* 259 */                 if (insnOpcode == 22 || insnOpcode == 24 || insnOpcode == 55 || insnOpcode == 57)
/*     */                 {
/*     */                   
/* 262 */                   subroutine.access[var + 1] = true;
/*     */                 }
/* 264 */               } else if (insnNode instanceof IincInsnNode) {
/* 265 */                 int var = ((IincInsnNode)insnNode).var;
/* 266 */                 subroutine.access[var] = true;
/*     */               } 
/*     */             }
/* 269 */             merge(insn + 1, current, subroutine);
/* 270 */             newControlFlowEdge(insn, insn + 1);
/*     */           } 
/*     */         } 
/*     */         
/* 274 */         List<TryCatchBlockNode> insnHandlers = this.handlers[insn];
/* 275 */         if (insnHandlers != null) {
/* 276 */           for (int i = 0; i < insnHandlers.size(); i++) {
/* 277 */             Type type; TryCatchBlockNode tcb = (TryCatchBlockNode)insnHandlers.get(i);
/*     */             
/* 279 */             if (tcb.type == null) {
/* 280 */               type = Type.getObjectType("java/lang/Throwable");
/*     */             } else {
/* 282 */               type = Type.getObjectType(tcb.type);
/*     */             } 
/* 284 */             int jump = this.insns.indexOf(tcb.handler);
/* 285 */             if (newControlFlowExceptionEdge(insn, tcb)) {
/* 286 */               handler.init(f);
/* 287 */               handler.clearStack();
/* 288 */               handler.push(this.interpreter.newValue(type));
/* 289 */               merge(jump, handler, subroutine);
/*     */             } 
/*     */           } 
/*     */         }
/* 293 */       } catch (AnalyzerException e) {
/* 294 */         throw new AnalyzerException(e.node, "Error at instruction " + insn + ": " + e
/* 295 */             .getMessage(), e);
/* 296 */       } catch (Exception e) {
/* 297 */         throw new AnalyzerException(insnNode, "Error at instruction " + insn + ": " + e
/* 298 */             .getMessage(), e);
/*     */       } 
/*     */     } 
/*     */     
/* 302 */     return this.frames;
/*     */   }
/*     */ 
/*     */   
/*     */   private void findSubroutine(int insn, Subroutine sub, List<AbstractInsnNode> calls) throws AnalyzerException {
/*     */     while (true) {
/* 308 */       if (insn < 0 || insn >= this.n) {
/* 309 */         throw new AnalyzerException(null, "Execution can fall off end of the code");
/*     */       }
/*     */       
/* 312 */       if (this.subroutines[insn] != null) {
/*     */         return;
/*     */       }
/* 315 */       this.subroutines[insn] = sub.copy();
/* 316 */       AbstractInsnNode node = this.insns.get(insn);
/*     */ 
/*     */       
/* 319 */       if (node instanceof JumpInsnNode) {
/* 320 */         if (node.getOpcode() == 168) {
/*     */           
/* 322 */           calls.add(node);
/*     */         } else {
/* 324 */           JumpInsnNode jnode = (JumpInsnNode)node;
/* 325 */           findSubroutine(this.insns.indexOf(jnode.label), sub, calls);
/*     */         } 
/* 327 */       } else if (node instanceof TableSwitchInsnNode) {
/* 328 */         TableSwitchInsnNode tsnode = (TableSwitchInsnNode)node;
/* 329 */         findSubroutine(this.insns.indexOf(tsnode.dflt), sub, calls);
/* 330 */         for (int i = tsnode.labels.size() - 1; i >= 0; i--) {
/* 331 */           LabelNode l = (LabelNode)tsnode.labels.get(i);
/* 332 */           findSubroutine(this.insns.indexOf(l), sub, calls);
/*     */         } 
/* 334 */       } else if (node instanceof LookupSwitchInsnNode) {
/* 335 */         LookupSwitchInsnNode lsnode = (LookupSwitchInsnNode)node;
/* 336 */         findSubroutine(this.insns.indexOf(lsnode.dflt), sub, calls);
/* 337 */         for (int i = lsnode.labels.size() - 1; i >= 0; i--) {
/* 338 */           LabelNode l = (LabelNode)lsnode.labels.get(i);
/* 339 */           findSubroutine(this.insns.indexOf(l), sub, calls);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 344 */       List<TryCatchBlockNode> insnHandlers = this.handlers[insn];
/* 345 */       if (insnHandlers != null) {
/* 346 */         for (int i = 0; i < insnHandlers.size(); i++) {
/* 347 */           TryCatchBlockNode tcb = (TryCatchBlockNode)insnHandlers.get(i);
/* 348 */           findSubroutine(this.insns.indexOf(tcb.handler), sub, calls);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 353 */       switch (node.getOpcode()) {
/*     */         case 167:
/*     */         case 169:
/*     */         case 170:
/*     */         case 171:
/*     */         case 172:
/*     */         case 173:
/*     */         case 174:
/*     */         case 175:
/*     */         case 176:
/*     */         case 177:
/*     */         case 191:
/*     */           return;
/*     */       } 
/* 367 */       insn++;
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
/* 383 */   public Frame<V>[] getFrames() { return this.frames; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 395 */   public List<TryCatchBlockNode> getHandlers(int insn) { return this.handlers[insn]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init(String owner, MethodNode m) throws AnalyzerException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 423 */   protected Frame<V> newFrame(int nLocals, int nStack) { return new Frame(nLocals, nStack); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 434 */   protected Frame<V> newFrame(Frame<? extends V> src) { return new Frame(src); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void newControlFlowEdge(int insn, int successor) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 468 */   protected boolean newControlFlowExceptionEdge(int insn, int successor) { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 492 */   protected boolean newControlFlowExceptionEdge(int insn, TryCatchBlockNode tcb) { return newControlFlowExceptionEdge(insn, this.insns.indexOf(tcb.handler)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void merge(int insn, Frame<V> frame, Subroutine subroutine) throws AnalyzerException {
/*     */     boolean bool;
/* 499 */     Frame<V> oldFrame = this.frames[insn];
/* 500 */     Subroutine oldSubroutine = this.subroutines[insn];
/*     */ 
/*     */     
/* 503 */     if (oldFrame == null) {
/* 504 */       this.frames[insn] = newFrame(frame);
/* 505 */       bool = true;
/*     */     } else {
/* 507 */       bool = oldFrame.merge(frame, this.interpreter);
/*     */     } 
/*     */     
/* 510 */     if (oldSubroutine == null) {
/* 511 */       if (subroutine != null) {
/* 512 */         this.subroutines[insn] = subroutine.copy();
/* 513 */         bool = true;
/*     */       }
/*     */     
/* 516 */     } else if (subroutine != null) {
/* 517 */       bool |= oldSubroutine.merge(subroutine);
/*     */     } 
/*     */     
/* 520 */     if (bool && !this.queued[insn]) {
/* 521 */       this.queued[insn] = true;
/* 522 */       this.queue[this.top++] = insn;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void merge(int insn, Frame<V> beforeJSR, Frame<V> afterRET, Subroutine subroutineBeforeJSR, boolean[] access) throws AnalyzerException {
/*     */     boolean changes;
/* 529 */     Frame<V> oldFrame = this.frames[insn];
/* 530 */     Subroutine oldSubroutine = this.subroutines[insn];
/*     */ 
/*     */     
/* 533 */     afterRET.merge(beforeJSR, access);
/*     */     
/* 535 */     if (oldFrame == null) {
/* 536 */       this.frames[insn] = newFrame(afterRET);
/* 537 */       changes = true;
/*     */     } else {
/* 539 */       changes = oldFrame.merge(afterRET, this.interpreter);
/*     */     } 
/*     */     
/* 542 */     if (oldSubroutine != null && subroutineBeforeJSR != null) {
/* 543 */       changes |= oldSubroutine.merge(subroutineBeforeJSR);
/*     */     }
/* 545 */     if (changes && !this.queued[insn]) {
/* 546 */       this.queued[insn] = true;
/* 547 */       this.queue[this.top++] = insn;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\tree\analysis\Analyzer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */