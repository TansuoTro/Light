/*      */ package org.spongepowered.asm.util;
/*      */ 
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.annotation.Annotation;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.ClassReader;
/*      */ import org.spongepowered.asm.lib.ClassWriter;
/*      */ import org.spongepowered.asm.lib.MethodVisitor;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.FrameNode;
/*      */ import org.spongepowered.asm.lib.tree.InsnList;
/*      */ import org.spongepowered.asm.lib.tree.IntInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.JumpInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LabelNode;
/*      */ import org.spongepowered.asm.lib.tree.LdcInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.LineNumberNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.lib.tree.TypeInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.VarInsnNode;
/*      */ import org.spongepowered.asm.lib.util.CheckClassAdapter;
/*      */ import org.spongepowered.asm.lib.util.TraceClassVisitor;
/*      */ import org.spongepowered.asm.util.throwables.SyntheticBridgeException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Bytecode
/*      */ {
/*      */   public enum Visibility
/*      */   {
/*   76 */     PRIVATE(2),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   81 */     PROTECTED(4),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   86 */     PACKAGE(0),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   91 */     PUBLIC(1);
/*      */     
/*      */     static final int MASK = 7;
/*      */     
/*      */     final int access;
/*      */ 
/*      */     
/*   98 */     Visibility(int access) { this.access = access; }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   public static final int[] CONSTANTS_INT = { 2, 3, 4, 5, 6, 7, 8 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   public static final int[] CONSTANTS_FLOAT = { 11, 12, 13 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  120 */   public static final int[] CONSTANTS_DOUBLE = { 14, 15 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  127 */   public static final int[] CONSTANTS_LONG = { 9, 10 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int[] CONSTANTS_ALL = { 
/*  134 */       1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Object[] CONSTANTS_VALUES = { 
/*  146 */       null, 
/*      */       
/*  148 */       Integer.valueOf(-1), 
/*  149 */       Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), 
/*  150 */       Long.valueOf(0L), Long.valueOf(1L), 
/*  151 */       Float.valueOf(0.0F), Float.valueOf(1.0F), Float.valueOf(2.0F), 
/*  152 */       Double.valueOf(0.0D), Double.valueOf(1.0D) };
/*      */   
/*      */   private static final String[] CONSTANTS_TYPES = { 
/*  155 */       null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String[] BOXING_TYPES = { 
/*  169 */       null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String[] UNBOXING_METHODS = { 
/*  187 */       null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  205 */   private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS = { org.spongepowered.asm.mixin.Overwrite.class, org.spongepowered.asm.mixin.Intrinsic.class, org.spongepowered.asm.mixin.Final.class, org.spongepowered.asm.mixin.Debug.class };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  212 */   private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
/*      */   
/*  214 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodNode findMethod(ClassNode classNode, String name, String desc) {
/*  229 */     for (MethodNode method : classNode.methods) {
/*  230 */       if (method.name.equals(name) && method.desc.equals(desc)) {
/*  231 */         return method;
/*      */       }
/*      */     } 
/*  234 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AbstractInsnNode findInsn(MethodNode method, int opcode) {
/*  245 */     Iterator<AbstractInsnNode> findReturnIter = method.instructions.iterator();
/*  246 */     while (findReturnIter.hasNext()) {
/*  247 */       AbstractInsnNode insn = (AbstractInsnNode)findReturnIter.next();
/*  248 */       if (insn.getOpcode() == opcode) {
/*  249 */         return insn;
/*      */       }
/*      */     } 
/*  252 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MethodInsnNode findSuperInit(MethodNode method, String superName) {
/*  265 */     if (!"<init>".equals(method.name)) {
/*  266 */       return null;
/*      */     }
/*      */     
/*  269 */     int news = 0;
/*  270 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  271 */       AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/*  272 */       if (insn instanceof TypeInsnNode && insn.getOpcode() == 187) {
/*  273 */         news++; continue;
/*  274 */       }  if (insn instanceof MethodInsnNode && insn.getOpcode() == 183) {
/*  275 */         MethodInsnNode methodNode = (MethodInsnNode)insn;
/*  276 */         if ("<init>".equals(methodNode.name)) {
/*  277 */           if (news > 0) {
/*  278 */             news--; continue;
/*  279 */           }  if (methodNode.owner.equals(superName)) {
/*  280 */             return methodNode;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*  285 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  296 */   public static void textify(ClassNode classNode, OutputStream out) { classNode.accept(new TraceClassVisitor(new PrintWriter(out))); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void textify(MethodNode methodNode, OutputStream out) {
/*  307 */     TraceClassVisitor trace = new TraceClassVisitor(new PrintWriter(out));
/*  308 */     MethodVisitor mv = trace.visitMethod(methodNode.access, methodNode.name, methodNode.desc, methodNode.signature, (String[])methodNode.exceptions
/*  309 */         .toArray(new String[0]));
/*  310 */     methodNode.accept(mv);
/*  311 */     trace.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpClass(ClassNode classNode) {
/*  320 */     ClassWriter cw = new ClassWriter(3);
/*  321 */     classNode.accept(cw);
/*  322 */     dumpClass(cw.toByteArray());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void dumpClass(byte[] bytes) {
/*  331 */     ClassReader cr = new ClassReader(bytes);
/*  332 */     CheckClassAdapter.verify(cr, true, new PrintWriter(System.out));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printMethodWithOpcodeIndices(MethodNode method) {
/*  341 */     System.err.printf("%s%s\n", new Object[] { method.name, method.desc });
/*  342 */     int i = 0;
/*  343 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext();) {
/*  344 */       System.err.printf("[%4d] %s\n", new Object[] { Integer.valueOf(i++), describeNode((AbstractInsnNode)iter.next()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printMethod(MethodNode method) {
/*  354 */     System.err.printf("%s%s\n", new Object[] { method.name, method.desc });
/*  355 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  356 */       System.err.print("  ");
/*  357 */       printNode((AbstractInsnNode)iter.next());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  367 */   public static void printNode(AbstractInsnNode node) { System.err.printf("%s\n", new Object[] { describeNode(node) }); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String describeNode(AbstractInsnNode node) {
/*  377 */     if (node == null) {
/*  378 */       return String.format("   %-14s ", new Object[] { "null" });
/*      */     }
/*      */     
/*  381 */     if (node instanceof LabelNode) {
/*  382 */       return String.format("[%s]", new Object[] { ((LabelNode)node).getLabel() });
/*      */     }
/*      */     
/*  385 */     String out = String.format("   %-14s ", new Object[] { node.getClass().getSimpleName().replace("Node", "") });
/*  386 */     if (node instanceof JumpInsnNode) {
/*  387 */       out = out + String.format("[%s] [%s]", new Object[] { getOpcodeName(node), ((JumpInsnNode)node).label.getLabel() });
/*  388 */     } else if (node instanceof VarInsnNode) {
/*  389 */       out = out + String.format("[%s] %d", new Object[] { getOpcodeName(node), Integer.valueOf(((VarInsnNode)node).var) });
/*  390 */     } else if (node instanceof MethodInsnNode) {
/*  391 */       MethodInsnNode mth = (MethodInsnNode)node;
/*  392 */       out = out + String.format("[%s] %s %s %s", new Object[] { getOpcodeName(node), mth.owner, mth.name, mth.desc });
/*  393 */     } else if (node instanceof FieldInsnNode) {
/*  394 */       FieldInsnNode fld = (FieldInsnNode)node;
/*  395 */       out = out + String.format("[%s] %s %s %s", new Object[] { getOpcodeName(node), fld.owner, fld.name, fld.desc });
/*  396 */     } else if (node instanceof LineNumberNode) {
/*  397 */       LineNumberNode ln = (LineNumberNode)node;
/*  398 */       out = out + String.format("LINE=[%d] LABEL=[%s]", new Object[] { Integer.valueOf(ln.line), ln.start.getLabel() });
/*  399 */     } else if (node instanceof LdcInsnNode) {
/*  400 */       out = out + ((LdcInsnNode)node).cst;
/*  401 */     } else if (node instanceof IntInsnNode) {
/*  402 */       out = out + ((IntInsnNode)node).operand;
/*  403 */     } else if (node instanceof FrameNode) {
/*  404 */       out = out + String.format("[%s] ", new Object[] { getOpcodeName(((FrameNode)node).type, "H_INVOKEINTERFACE", -1) });
/*      */     } else {
/*  406 */       out = out + String.format("[%s] ", new Object[] { getOpcodeName(node) });
/*      */     } 
/*  408 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  420 */   public static String getOpcodeName(AbstractInsnNode node) { return (node != null) ? getOpcodeName(node.getOpcode()) : ""; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  432 */   public static String getOpcodeName(int opcode) { return getOpcodeName(opcode, "UNINITIALIZED_THIS", 1); }
/*      */ 
/*      */   
/*      */   private static String getOpcodeName(int opcode, String start, int min) {
/*  436 */     if (opcode >= min) {
/*  437 */       boolean found = false;
/*      */       
/*      */       try {
/*  440 */         for (Field f : org.spongepowered.asm.lib.Opcodes.class.getDeclaredFields()) {
/*  441 */           if (found || f.getName().equals(start)) {
/*      */ 
/*      */             
/*  444 */             found = true;
/*  445 */             if (f.getType() == int.class && f.getInt(null) == opcode)
/*  446 */               return f.getName(); 
/*      */           } 
/*      */         } 
/*  449 */       } catch (Exception exception) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  454 */     return (opcode >= 0) ? String.valueOf(opcode) : "UNKNOWN";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean methodHasLineNumbers(MethodNode method) {
/*  464 */     for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext();) {
/*  465 */       if (iter.next() instanceof LineNumberNode) {
/*  466 */         return true;
/*      */       }
/*      */     } 
/*  469 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  479 */   public static boolean methodIsStatic(MethodNode method) { return ((method.access & 0x8) == 8); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  489 */   public static boolean fieldIsStatic(FieldNode field) { return ((field.access & 0x8) == 8); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  503 */   public static int getFirstNonArgLocalIndex(MethodNode method) { return getFirstNonArgLocalIndex(Type.getArgumentTypes(method.desc), ((method.access & 0x8) == 0)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  519 */   public static int getFirstNonArgLocalIndex(Type[] args, boolean includeThis) { return getArgsSize(args) + (includeThis ? 1 : 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getArgsSize(Type[] args) {
/*  530 */     int size = 0;
/*      */     
/*  532 */     for (Type type : args) {
/*  533 */       size += type.getSize();
/*      */     }
/*      */     
/*  536 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  548 */   public static void loadArgs(Type[] args, InsnList insns, int pos) { loadArgs(args, insns, pos, -1); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  561 */   public static void loadArgs(Type[] args, InsnList insns, int start, int end) { loadArgs(args, insns, start, end, null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadArgs(Type[] args, InsnList insns, int start, int end, Type[] casts) {
/*  575 */     int pos = start, index = 0;
/*      */     
/*  577 */     for (Type type : args) {
/*  578 */       insns.add(new VarInsnNode(type.getOpcode(21), pos));
/*  579 */       if (casts != null && index < casts.length && casts[index] != null) {
/*  580 */         insns.add(new TypeInsnNode(192, casts[index].getInternalName()));
/*      */       }
/*  582 */       pos += type.getSize();
/*  583 */       if (end >= start && pos >= end) {
/*      */         return;
/*      */       }
/*  586 */       index++;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<LabelNode, LabelNode> cloneLabels(InsnList source) {
/*  599 */     Map<LabelNode, LabelNode> labels = new HashMap<LabelNode, LabelNode>();
/*      */     
/*  601 */     for (Iterator<AbstractInsnNode> iter = source.iterator(); iter.hasNext(); ) {
/*  602 */       AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/*  603 */       if (insn instanceof LabelNode) {
/*  604 */         labels.put((LabelNode)insn, new LabelNode(((LabelNode)insn).getLabel()));
/*      */       }
/*      */     } 
/*      */     
/*  608 */     return labels;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String generateDescriptor(Object returnType, Object... args) {
/*  621 */     StringBuilder sb = (new StringBuilder()).append('(');
/*      */     
/*  623 */     for (Object arg : args) {
/*  624 */       sb.append(toDescriptor(arg));
/*      */     }
/*      */     
/*  627 */     return sb.append(')').append((returnType != null) ? toDescriptor(returnType) : "V").toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toDescriptor(Object arg) {
/*  637 */     if (arg instanceof String)
/*  638 */       return (String)arg; 
/*  639 */     if (arg instanceof Type)
/*  640 */       return arg.toString(); 
/*  641 */     if (arg instanceof Class) {
/*  642 */       return Type.getDescriptor((Class)arg);
/*      */     }
/*  644 */     return (arg == null) ? "" : arg.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  655 */   public static String getDescriptor(Type[] args) { return "(" + Joiner.on("").join(args) + ")"; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  666 */   public static String getDescriptor(Type[] args, Type returnType) { return getDescriptor(args) + returnType.toString(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String changeDescriptorReturnType(String desc, String returnType) {
/*  677 */     if (desc == null)
/*  678 */       return null; 
/*  679 */     if (returnType == null) {
/*  680 */       return desc;
/*      */     }
/*  682 */     return desc.substring(0, desc.lastIndexOf(')') + 1) + returnType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  693 */   public static String getSimpleName(Class<? extends Annotation> annotationType) { return annotationType.getSimpleName(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  704 */   public static String getSimpleName(AnnotationNode annotation) { return getSimpleName(annotation.desc); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(String desc) {
/*  714 */     int pos = Math.max(desc.lastIndexOf('/'), 0);
/*  715 */     return desc.substring(pos + 1).replace(";", "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isConstant(AbstractInsnNode insn) {
/*  726 */     if (insn == null) {
/*  727 */       return false;
/*      */     }
/*  729 */     return Ints.contains(CONSTANTS_ALL, insn.getOpcode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object getConstant(AbstractInsnNode insn) {
/*  741 */     if (insn == null)
/*  742 */       return null; 
/*  743 */     if (insn instanceof LdcInsnNode)
/*  744 */       return ((LdcInsnNode)insn).cst; 
/*  745 */     if (insn instanceof IntInsnNode) {
/*  746 */       int value = ((IntInsnNode)insn).operand;
/*  747 */       if (insn.getOpcode() == 16 || insn.getOpcode() == 17) {
/*  748 */         return Integer.valueOf(value);
/*      */       }
/*  750 */       throw new IllegalArgumentException("IntInsnNode with invalid opcode " + insn.getOpcode() + " in getConstant");
/*      */     } 
/*      */     
/*  753 */     int index = Ints.indexOf(CONSTANTS_ALL, insn.getOpcode());
/*  754 */     return (index < 0) ? null : CONSTANTS_VALUES[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Type getConstantType(AbstractInsnNode insn) {
/*  765 */     if (insn == null)
/*  766 */       return null; 
/*  767 */     if (insn instanceof LdcInsnNode) {
/*  768 */       Object cst = ((LdcInsnNode)insn).cst;
/*  769 */       if (cst instanceof Integer)
/*  770 */         return Type.getType("I"); 
/*  771 */       if (cst instanceof Float)
/*  772 */         return Type.getType("F"); 
/*  773 */       if (cst instanceof Long)
/*  774 */         return Type.getType("J"); 
/*  775 */       if (cst instanceof Double)
/*  776 */         return Type.getType("D"); 
/*  777 */       if (cst instanceof String)
/*  778 */         return Type.getType("Ljava/lang/String;"); 
/*  779 */       if (cst instanceof Type) {
/*  780 */         return Type.getType("Ljava/lang/Class;");
/*      */       }
/*  782 */       throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + cst.getClass() + " in getConstant");
/*      */     } 
/*      */     
/*  785 */     int index = Ints.indexOf(CONSTANTS_ALL, insn.getOpcode());
/*  786 */     return (index < 0) ? null : Type.getType(CONSTANTS_TYPES[index]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  797 */   public static boolean hasFlag(ClassNode classNode, int flag) { return ((classNode.access & flag) == flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  808 */   public static boolean hasFlag(MethodNode method, int flag) { return ((method.access & flag) == flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  819 */   public static boolean hasFlag(FieldNode field, int flag) { return ((field.access & flag) == flag); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  832 */   public static boolean compareFlags(MethodNode m1, MethodNode m2, int flag) { return (hasFlag(m1, flag) == hasFlag(m2, flag)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  845 */   public static boolean compareFlags(FieldNode f1, FieldNode f2, int flag) { return (hasFlag(f1, flag) == hasFlag(f2, flag)); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  863 */   public static Visibility getVisibility(MethodNode method) { return getVisibility(method.access & 0x7); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  881 */   public static Visibility getVisibility(FieldNode field) { return getVisibility(field.access & 0x7); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Visibility getVisibility(int flags) {
/*  899 */     if ((flags & 0x4) != 0)
/*  900 */       return Visibility.PROTECTED; 
/*  901 */     if ((flags & 0x2) != 0)
/*  902 */       return Visibility.PRIVATE; 
/*  903 */     if ((flags & true) != 0) {
/*  904 */       return Visibility.PUBLIC;
/*      */     }
/*  906 */     return Visibility.PACKAGE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  917 */   public static void setVisibility(MethodNode method, Visibility visibility) { method.access = setVisibility(method.access, visibility.access); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  928 */   public static void setVisibility(FieldNode field, Visibility visibility) { field.access = setVisibility(field.access, visibility.access); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  939 */   public static void setVisibility(MethodNode method, int access) { method.access = setVisibility(method.access, access); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  950 */   public static void setVisibility(FieldNode field, int access) { field.access = setVisibility(field.access, access); }
/*      */ 
/*      */ 
/*      */   
/*  954 */   private static int setVisibility(int oldAccess, int newAccess) { return oldAccess & 0xFFFFFFF8 | newAccess & 0x7; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getMaxLineNumber(ClassNode classNode, int min, int pad) {
/*  966 */     int max = 0;
/*  967 */     for (MethodNode method : classNode.methods) {
/*  968 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  969 */         AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/*  970 */         if (insn instanceof LineNumberNode) {
/*  971 */           max = Math.max(max, ((LineNumberNode)insn).line);
/*      */         }
/*      */       } 
/*      */     } 
/*  975 */     return Math.max(min, max + pad);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  986 */   public static String getBoxingType(Type type) { return (type == null) ? null : BOXING_TYPES[type.getSort()]; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  999 */   public static String getUnboxingMethod(Type type) { return (type == null) ? null : UNBOXING_METHODS[type.getSort()]; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeAnnotations(ClassNode from, ClassNode to) {
/* 1014 */     to.visibleAnnotations = mergeAnnotations(from.visibleAnnotations, to.visibleAnnotations, "class", from.name);
/* 1015 */     to.invisibleAnnotations = mergeAnnotations(from.invisibleAnnotations, to.invisibleAnnotations, "class", from.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeAnnotations(MethodNode from, MethodNode to) {
/* 1030 */     to.visibleAnnotations = mergeAnnotations(from.visibleAnnotations, to.visibleAnnotations, "method", from.name);
/* 1031 */     to.invisibleAnnotations = mergeAnnotations(from.invisibleAnnotations, to.invisibleAnnotations, "method", from.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void mergeAnnotations(FieldNode from, FieldNode to) {
/* 1046 */     to.visibleAnnotations = mergeAnnotations(from.visibleAnnotations, to.visibleAnnotations, "field", from.name);
/* 1047 */     to.invisibleAnnotations = mergeAnnotations(from.invisibleAnnotations, to.invisibleAnnotations, "field", from.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static List<AnnotationNode> mergeAnnotations(List<AnnotationNode> from, List<AnnotationNode> to, String type, String name) {
/*      */     try {
/* 1062 */       if (from == null) {
/* 1063 */         return to;
/*      */       }
/*      */       
/* 1066 */       if (to == null) {
/* 1067 */         to = new ArrayList<AnnotationNode>();
/*      */       }
/*      */       
/* 1070 */       for (AnnotationNode annotation : from) {
/* 1071 */         if (!isMergeableAnnotation(annotation)) {
/*      */           continue;
/*      */         }
/*      */         
/* 1075 */         for (Iterator<AnnotationNode> iter = to.iterator(); iter.hasNext();) {
/* 1076 */           if (((AnnotationNode)iter.next()).desc.equals(annotation.desc)) {
/* 1077 */             iter.remove();
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1082 */         to.add(annotation);
/*      */       } 
/* 1084 */     } catch (Exception ex) {
/* 1085 */       logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[] { type, name });
/*      */     } 
/*      */     
/* 1088 */     return to;
/*      */   }
/*      */   
/*      */   private static boolean isMergeableAnnotation(AnnotationNode annotation) {
/* 1092 */     if (annotation.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF)) {
/* 1093 */       return mergeableAnnotationPattern.matcher(annotation.desc).matches();
/*      */     }
/* 1095 */     return true;
/*      */   }
/*      */   
/*      */   private static Pattern getMergeableAnnotationPattern() {
/* 1099 */     sb = new StringBuilder("^L(");
/* 1100 */     for (int i = 0; i < MERGEABLE_MIXIN_ANNOTATIONS.length; i++) {
/* 1101 */       if (i > 0) {
/* 1102 */         sb.append('|');
/*      */       }
/* 1104 */       sb.append(MERGEABLE_MIXIN_ANNOTATIONS[i].getName().replace('.', '/'));
/*      */     } 
/* 1106 */     return Pattern.compile(sb.append(");$").toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void compareBridgeMethods(MethodNode a, MethodNode b) {
/* 1117 */     ListIterator<AbstractInsnNode> ia = a.instructions.iterator();
/* 1118 */     ListIterator<AbstractInsnNode> ib = b.instructions.iterator();
/*      */     
/* 1120 */     int index = 0;
/* 1121 */     for (; ia.hasNext() && ib.hasNext(); index++) {
/* 1122 */       AbstractInsnNode na = (AbstractInsnNode)ia.next();
/* 1123 */       AbstractInsnNode nb = (AbstractInsnNode)ib.next();
/* 1124 */       if (!(na instanceof LabelNode))
/*      */       {
/*      */ 
/*      */         
/* 1128 */         if (na instanceof MethodInsnNode) {
/* 1129 */           MethodInsnNode ma = (MethodInsnNode)na;
/* 1130 */           MethodInsnNode mb = (MethodInsnNode)nb;
/* 1131 */           if (!ma.name.equals(mb.name))
/* 1132 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, a.name, a.desc, index, na, nb); 
/* 1133 */           if (!ma.desc.equals(mb.desc))
/* 1134 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, a.name, a.desc, index, na, nb); 
/*      */         } else {
/* 1136 */           if (na.getOpcode() != nb.getOpcode())
/* 1137 */             throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, a.name, a.desc, index, na, nb); 
/* 1138 */           if (na instanceof VarInsnNode) {
/* 1139 */             VarInsnNode va = (VarInsnNode)na;
/* 1140 */             VarInsnNode vb = (VarInsnNode)nb;
/* 1141 */             if (va.var != vb.var) {
/* 1142 */               throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, a.name, a.desc, index, na, nb);
/*      */             }
/* 1144 */           } else if (na instanceof TypeInsnNode) {
/* 1145 */             TypeInsnNode ta = (TypeInsnNode)na;
/* 1146 */             TypeInsnNode tb = (TypeInsnNode)nb;
/* 1147 */             if (ta.getOpcode() == 192 && !ta.desc.equals(tb.desc))
/* 1148 */               throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, a.name, a.desc, index, na, nb); 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1153 */     if (ia.hasNext() || ib.hasNext())
/* 1154 */       throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, a.name, a.desc, index, null, null); 
/*      */   }
/*      */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\as\\util\Bytecode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */