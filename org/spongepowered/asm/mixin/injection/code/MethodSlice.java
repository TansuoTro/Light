/*     */ package org.spongepowered.asm.mixin.injection.code;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.InsnList;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.Slice;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
/*     */ import org.spongepowered.asm.util.Annotations;
/*     */ import org.spongepowered.asm.util.Bytecode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class MethodSlice
/*     */ {
/*     */   static final class InsnListSlice
/*     */     extends ReadOnlyInsnList
/*     */   {
/*     */     private final int start;
/*     */     private final int end;
/*     */     
/*     */     static class SliceIterator
/*     */       extends Object
/*     */       implements ListIterator<AbstractInsnNode>
/*     */     {
/*     */       private final ListIterator<AbstractInsnNode> iter;
/*     */       private int start;
/*     */       private int end;
/*     */       private int index;
/*     */       
/*     */       public SliceIterator(ListIterator<AbstractInsnNode> iter, int start, int end, int index) {
/*  89 */         this.iter = iter;
/*  90 */         this.start = start;
/*  91 */         this.end = end;
/*  92 */         this.index = index;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       public boolean hasNext() { return (this.index <= this.end && this.iter.hasNext()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public AbstractInsnNode next() {
/* 108 */         if (this.index > this.end) {
/* 109 */           throw new NoSuchElementException();
/*     */         }
/* 111 */         this.index++;
/* 112 */         return (AbstractInsnNode)this.iter.next();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       public boolean hasPrevious() { return (this.index > this.start); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public AbstractInsnNode previous() {
/* 128 */         if (this.index <= this.start) {
/* 129 */           throw new NoSuchElementException();
/*     */         }
/* 131 */         this.index--;
/* 132 */         return (AbstractInsnNode)this.iter.previous();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       public int nextIndex() { return this.index - this.start; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       public int previousIndex() { return this.index - this.start - 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       public void remove() { throw new UnsupportedOperationException("Cannot remove insn from slice"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 164 */       public void set(AbstractInsnNode e) { throw new UnsupportedOperationException("Cannot set insn using slice"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 172 */       public void add(AbstractInsnNode e) { throw new UnsupportedOperationException("Cannot add insn using slice"); }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected InsnListSlice(InsnList inner, int start, int end) {
/* 182 */       super(inner);
/*     */ 
/*     */       
/* 185 */       this.start = start;
/* 186 */       this.end = end;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     public ListIterator<AbstractInsnNode> iterator() { return iterator(0); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 205 */     public ListIterator<AbstractInsnNode> iterator(int index) { return new SliceIterator(super.iterator(this.start + index), this.start, this.end, this.start + index); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AbstractInsnNode[] toArray() {
/* 214 */       AbstractInsnNode[] all = super.toArray();
/* 215 */       AbstractInsnNode[] subset = new AbstractInsnNode[size()];
/* 216 */       System.arraycopy(all, this.start, subset, 0, subset.length);
/* 217 */       return subset;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 226 */     public int size() { return this.end - this.start + 1; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     public AbstractInsnNode getFirst() { return super.get(this.start); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 244 */     public AbstractInsnNode getLast() { return super.get(this.end); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     public AbstractInsnNode get(int index) { return super.get(this.start + index); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(AbstractInsnNode insn) {
/* 262 */       for (AbstractInsnNode node : toArray()) {
/* 263 */         if (node == insn) {
/* 264 */           return true;
/*     */         }
/*     */       } 
/* 267 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int indexOf(AbstractInsnNode insn) {
/* 280 */       int index = super.indexOf(insn);
/* 281 */       return (index >= this.start && index <= this.end) ? (index - this.start) : -1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     public int realIndexOf(AbstractInsnNode insn) { return super.indexOf(insn); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 299 */   private static final Logger logger = LogManager.getLogger("mixin");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ISliceContext owner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final InjectionPoint from;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final InjectionPoint to;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MethodSlice(ISliceContext owner, String id, InjectionPoint from, InjectionPoint to) {
/* 338 */     if (from == null && to == null) {
/* 339 */       throw new InvalidSliceException(owner, String.format("%s is redundant. No 'from' or 'to' value specified", new Object[] { this }));
/*     */     }
/*     */     
/* 342 */     this.owner = owner;
/* 343 */     this.id = Strings.nullToEmpty(id);
/* 344 */     this.from = from;
/* 345 */     this.to = to;
/* 346 */     this.name = getSliceName(id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 353 */   public String getId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ReadOnlyInsnList getSlice(MethodNode method) {
/* 363 */     int max = method.instructions.size() - 1;
/* 364 */     int start = find(method, this.from, 0, 0, this.name + "(from)");
/* 365 */     int end = find(method, this.to, max, start, this.name + "(to)");
/*     */     
/* 367 */     if (start > end) {
/* 368 */       throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", new Object[] { describe(), Integer.valueOf(start), Integer.valueOf(end) }));
/*     */     }
/*     */     
/* 371 */     if (start < 0 || end < 0 || start > max || end > max) {
/* 372 */       throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + start + " end=" + end + " lim=" + max);
/*     */     }
/*     */     
/* 375 */     if (start == 0 && end == max) {
/* 376 */       return new ReadOnlyInsnList(method.instructions);
/*     */     }
/*     */     
/* 379 */     return new InsnListSlice(method.instructions, start, end);
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
/*     */   private int find(MethodNode method, InjectionPoint injectionPoint, int defaultValue, int failValue, String description) {
/* 396 */     if (injectionPoint == null) {
/* 397 */       return defaultValue;
/*     */     }
/*     */     
/* 400 */     Deque<AbstractInsnNode> nodes = new LinkedList<AbstractInsnNode>();
/* 401 */     ReadOnlyInsnList insns = new ReadOnlyInsnList(method.instructions);
/* 402 */     boolean result = injectionPoint.find(method.desc, insns, nodes);
/* 403 */     InjectionPoint.Selector select = injectionPoint.getSelector();
/* 404 */     if (nodes.size() != 1 && select == InjectionPoint.Selector.ONE) {
/* 405 */       throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", new Object[] { describe(description), Integer.valueOf(nodes.size()) }));
/*     */     }
/*     */     
/* 408 */     if (!result) {
/* 409 */       if (this.owner.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
/* 410 */         logger.warn("{} did not match any instructions", new Object[] { describe(description) });
/*     */       }
/* 412 */       return failValue;
/*     */     } 
/*     */     
/* 415 */     return method.instructions.indexOf((select == InjectionPoint.Selector.FIRST) ? (AbstractInsnNode)nodes.getFirst() : (AbstractInsnNode)nodes.getLast());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 423 */   public String toString() { return describe(); }
/*     */ 
/*     */ 
/*     */   
/* 427 */   private String describe() { return describe(this.name); }
/*     */ 
/*     */ 
/*     */   
/* 431 */   private String describe(String description) { return describeSlice(description, this.owner); }
/*     */ 
/*     */   
/*     */   private static String describeSlice(String description, ISliceContext owner) {
/* 435 */     String annotation = Bytecode.getSimpleName(owner.getAnnotation());
/* 436 */     MethodNode method = owner.getMethod();
/* 437 */     return String.format("%s->%s(%s)::%s%s", new Object[] { owner.getContext(), annotation, description, method.name, method.desc });
/*     */   }
/*     */ 
/*     */   
/* 441 */   private static String getSliceName(String id) { return String.format("@Slice[%s]", new Object[] { Strings.nullToEmpty(id) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlice parse(ISliceContext owner, Slice slice) {
/* 452 */     String id = slice.id();
/*     */     
/* 454 */     At from = slice.from();
/* 455 */     At to = slice.to();
/*     */     
/* 457 */     InjectionPoint fromPoint = (from != null) ? InjectionPoint.parse(owner, from) : null;
/* 458 */     InjectionPoint toPoint = (to != null) ? InjectionPoint.parse(owner, to) : null;
/*     */     
/* 460 */     return new MethodSlice(owner, id, fromPoint, toPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodSlice parse(ISliceContext info, AnnotationNode node) {
/* 471 */     String id = (String)Annotations.getValue(node, "id");
/*     */     
/* 473 */     AnnotationNode from = (AnnotationNode)Annotations.getValue(node, "from");
/* 474 */     AnnotationNode to = (AnnotationNode)Annotations.getValue(node, "to");
/*     */     
/* 476 */     InjectionPoint fromPoint = (from != null) ? InjectionPoint.parse(info, from) : null;
/* 477 */     InjectionPoint toPoint = (to != null) ? InjectionPoint.parse(info, to) : null;
/*     */     
/* 479 */     return new MethodSlice(info, id, fromPoint, toPoint);
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\code\MethodSlice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */