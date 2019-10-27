/*      */ package org.spongepowered.asm.mixin.transformer;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.apache.logging.log4j.Level;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.ClassNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.FieldNode;
/*      */ import org.spongepowered.asm.lib.tree.FrameNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*      */ import org.spongepowered.asm.service.MixinService;
/*      */ import org.spongepowered.asm.util.Annotations;
/*      */ import org.spongepowered.asm.util.ClassSignature;
/*      */ import org.spongepowered.asm.util.perf.Profiler;
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
/*      */ public final class ClassInfo
/*      */ {
/*      */   public static final int INCLUDE_PRIVATE = 2;
/*      */   public static final int INCLUDE_STATIC = 8;
/*      */   public static final int INCLUDE_ALL = 10;
/*      */   
/*      */   public enum SearchType
/*      */   {
/*   85 */     ALL_CLASSES,
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   90 */     SUPER_CLASSES_ONLY;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Traversal
/*      */   {
/*  114 */     NONE(null, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     ALL(null, true, ClassInfo.SearchType.ALL_CLASSES),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  124 */     IMMEDIATE(NONE, true, ClassInfo.SearchType.SUPER_CLASSES_ONLY),
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  130 */     SUPER(ALL, false, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
/*      */     
/*      */     private final Traversal next;
/*      */     
/*      */     private final boolean traverse;
/*      */     
/*      */     private final ClassInfo.SearchType searchType;
/*      */     
/*      */     Traversal(Traversal next, boolean traverse, ClassInfo.SearchType searchType) {
/*  139 */       this.next = (next != null) ? next : this;
/*  140 */       this.traverse = traverse;
/*  141 */       this.searchType = searchType;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  148 */     public Traversal next() { return this.next; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  155 */     public boolean canTraverse() { return this.traverse; }
/*      */ 
/*      */ 
/*      */     
/*  159 */     public ClassInfo.SearchType getSearchType() { return this.searchType; }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class FrameData
/*      */   {
/*  169 */     private static final String[] FRAMETYPES = { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
/*      */ 
/*      */ 
/*      */     
/*      */     public final int index;
/*      */ 
/*      */ 
/*      */     
/*      */     public final int type;
/*      */ 
/*      */ 
/*      */     
/*      */     public final int locals;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FrameData(int index, int type, int locals) {
/*  187 */       this.index = index;
/*  188 */       this.type = type;
/*  189 */       this.locals = locals;
/*      */     }
/*      */     
/*      */     FrameData(int index, FrameNode frameNode) {
/*  193 */       this.index = index;
/*  194 */       this.type = frameNode.type;
/*  195 */       this.locals = (frameNode.local != null) ? frameNode.local.size() : 0;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  203 */     public String toString() { return String.format("FrameData[index=%d, type=%s, locals=%d]", new Object[] { Integer.valueOf(this.index), FRAMETYPES[this.type + 1], Integer.valueOf(this.locals) }); } }
/*      */   
/*      */   static abstract class Member { private final Type type;
/*      */     private final String memberName;
/*      */     private final String memberDesc;
/*      */     private final boolean isInjected;
/*      */     private final int modifiers;
/*      */     private String currentName;
/*      */     private String currentDesc;
/*      */     private boolean decoratedFinal;
/*      */     private boolean decoratedMutable;
/*      */     private boolean unique;
/*      */     
/*  216 */     enum Type { METHOD,
/*  217 */       FIELD; }
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
/*      */     protected Member(Member member) {
/*  274 */       this(member.type, member.memberName, member.memberDesc, member.modifiers, member.isInjected);
/*  275 */       this.currentName = member.currentName;
/*  276 */       this.currentDesc = member.currentDesc;
/*  277 */       this.unique = member.unique;
/*      */     }
/*      */ 
/*      */     
/*  281 */     protected Member(Type type, String name, String desc, int access) { this(type, name, desc, access, false); }
/*      */ 
/*      */     
/*      */     protected Member(Type type, String name, String desc, int access, boolean injected) {
/*  285 */       this.type = type;
/*  286 */       this.memberName = name;
/*  287 */       this.memberDesc = desc;
/*  288 */       this.isInjected = injected;
/*  289 */       this.currentName = name;
/*  290 */       this.currentDesc = desc;
/*  291 */       this.modifiers = access;
/*      */     }
/*      */ 
/*      */     
/*  295 */     public String getOriginalName() { return this.memberName; }
/*      */ 
/*      */ 
/*      */     
/*  299 */     public String getName() { return this.currentName; }
/*      */ 
/*      */ 
/*      */     
/*  303 */     public String getOriginalDesc() { return this.memberDesc; }
/*      */ 
/*      */ 
/*      */     
/*  307 */     public String getDesc() { return this.currentDesc; }
/*      */ 
/*      */ 
/*      */     
/*  311 */     public boolean isInjected() { return this.isInjected; }
/*      */ 
/*      */ 
/*      */     
/*  315 */     public boolean isRenamed() { return !this.currentName.equals(this.memberName); }
/*      */ 
/*      */ 
/*      */     
/*  319 */     public boolean isRemapped() { return !this.currentDesc.equals(this.memberDesc); }
/*      */ 
/*      */ 
/*      */     
/*  323 */     public boolean isPrivate() { return ((this.modifiers & 0x2) != 0); }
/*      */ 
/*      */ 
/*      */     
/*  327 */     public boolean isStatic() { return ((this.modifiers & 0x8) != 0); }
/*      */ 
/*      */ 
/*      */     
/*  331 */     public boolean isAbstract() { return ((this.modifiers & 0x400) != 0); }
/*      */ 
/*      */ 
/*      */     
/*  335 */     public boolean isFinal() { return ((this.modifiers & 0x10) != 0); }
/*      */ 
/*      */ 
/*      */     
/*  339 */     public boolean isSynthetic() { return ((this.modifiers & 0x1000) != 0); }
/*      */ 
/*      */ 
/*      */     
/*  343 */     public boolean isUnique() { return this.unique; }
/*      */ 
/*      */ 
/*      */     
/*  347 */     public void setUnique(boolean unique) { this.unique = unique; }
/*      */ 
/*      */ 
/*      */     
/*  351 */     public boolean isDecoratedFinal() { return this.decoratedFinal; }
/*      */ 
/*      */ 
/*      */     
/*  355 */     public boolean isDecoratedMutable() { return this.decoratedMutable; }
/*      */ 
/*      */     
/*      */     public void setDecoratedFinal(boolean decoratedFinal, boolean decoratedMutable) {
/*  359 */       this.decoratedFinal = decoratedFinal;
/*  360 */       this.decoratedMutable = decoratedMutable;
/*      */     }
/*      */ 
/*      */     
/*  364 */     public boolean matchesFlags(int flags) { return (((this.modifiers ^ 0xFFFFFFFF | flags & 0x2) & 0x2) != 0 && ((this.modifiers ^ 0xFFFFFFFF | flags & 0x8) & 0x8) != 0); }
/*      */ 
/*      */ 
/*      */     
/*      */     public abstract ClassInfo getOwner();
/*      */ 
/*      */ 
/*      */     
/*  372 */     public ClassInfo getImplementor() { return getOwner(); }
/*      */ 
/*      */ 
/*      */     
/*  376 */     public int getAccess() { return this.modifiers; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String renameTo(String name) {
/*  384 */       this.currentName = name;
/*  385 */       return name;
/*      */     }
/*      */     
/*      */     public String remapTo(String desc) {
/*  389 */       this.currentDesc = desc;
/*  390 */       return desc;
/*      */     }
/*      */     
/*      */     public boolean equals(String name, String desc) {
/*  394 */       return ((this.memberName.equals(name) || this.currentName.equals(name)) && (this.memberDesc
/*  395 */         .equals(desc) || this.currentDesc.equals(desc)));
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  400 */       if (!(obj instanceof Member)) {
/*  401 */         return false;
/*      */       }
/*      */       
/*  404 */       Member other = (Member)obj;
/*  405 */       return ((other.memberName.equals(this.memberName) || other.currentName.equals(this.currentName)) && (other.memberDesc
/*  406 */         .equals(this.memberDesc) || other.currentDesc.equals(this.currentDesc)));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  411 */     public int hashCode() { return toString().hashCode(); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  416 */     public String toString() { return String.format(getDisplayFormat(), new Object[] { this.memberName, this.memberDesc }); }
/*      */ 
/*      */ 
/*      */     
/*  420 */     protected String getDisplayFormat() { return "%s%s"; } }
/*      */ 
/*      */   
/*      */   enum Type
/*      */   {
/*      */     METHOD, FIELD;
/*      */   }
/*      */   
/*      */   public class Method
/*      */     extends Member
/*      */   {
/*      */     private final List<ClassInfo.FrameData> frames;
/*      */     private boolean isAccessor;
/*      */     
/*      */     public Method(ClassInfo.Member member) {
/*  435 */       super(member);
/*  436 */       this.frames = (member instanceof Method) ? ((Method)member).frames : null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Method(MethodNode method) {
/*  441 */       this(method, false);
/*  442 */       setUnique((Annotations.getVisible(method, org.spongepowered.asm.mixin.Unique.class) != null));
/*  443 */       this.isAccessor = (Annotations.getSingleVisible(method, new Class[] { org.spongepowered.asm.mixin.gen.Accessor.class, org.spongepowered.asm.mixin.gen.Invoker.class }) != null);
/*      */     }
/*      */ 
/*      */     
/*      */     public Method(MethodNode method, boolean injected) {
/*  448 */       super(ClassInfo.Member.Type.METHOD, method.name, method.desc, method.access, injected);
/*  449 */       this.frames = gatherFrames(method);
/*  450 */       setUnique((Annotations.getVisible(method, org.spongepowered.asm.mixin.Unique.class) != null));
/*  451 */       this.isAccessor = (Annotations.getSingleVisible(method, new Class[] { org.spongepowered.asm.mixin.gen.Accessor.class, org.spongepowered.asm.mixin.gen.Invoker.class }) != null);
/*      */     }
/*      */     
/*      */     public Method(String name, String desc) {
/*  455 */       super(ClassInfo.Member.Type.METHOD, name, desc, 1, false);
/*  456 */       this.frames = null;
/*      */     }
/*      */     
/*      */     public Method(String name, String desc, int access) {
/*  460 */       super(ClassInfo.Member.Type.METHOD, name, desc, access, false);
/*  461 */       this.frames = null;
/*      */     }
/*      */     
/*      */     public Method(String name, String desc, int access, boolean injected) {
/*  465 */       super(ClassInfo.Member.Type.METHOD, name, desc, access, injected);
/*  466 */       this.frames = null;
/*      */     }
/*      */     
/*      */     private List<ClassInfo.FrameData> gatherFrames(MethodNode method) {
/*  470 */       List<ClassInfo.FrameData> frames = new ArrayList<ClassInfo.FrameData>();
/*  471 */       for (Iterator<AbstractInsnNode> iter = method.instructions.iterator(); iter.hasNext(); ) {
/*  472 */         AbstractInsnNode insn = (AbstractInsnNode)iter.next();
/*  473 */         if (insn instanceof FrameNode) {
/*  474 */           frames.add(new ClassInfo.FrameData(method.instructions.indexOf(insn), (FrameNode)insn));
/*      */         }
/*      */       } 
/*  477 */       return frames;
/*      */     }
/*      */ 
/*      */     
/*  481 */     public List<ClassInfo.FrameData> getFrames() { return this.frames; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  486 */     public ClassInfo getOwner() { return ClassInfo.this; }
/*      */ 
/*      */ 
/*      */     
/*  490 */     public boolean isAccessor() { return this.isAccessor; }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  495 */       if (!(obj instanceof Method)) {
/*  496 */         return false;
/*      */       }
/*      */       
/*  499 */       return super.equals(obj);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public class InterfaceMethod
/*      */     extends Method
/*      */   {
/*      */     private final ClassInfo owner;
/*      */ 
/*      */ 
/*      */     
/*      */     public InterfaceMethod(ClassInfo.Member member) {
/*  513 */       super(ClassInfo.this, member);
/*  514 */       this.owner = member.getOwner();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  519 */     public ClassInfo getOwner() { return this.owner; }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  524 */     public ClassInfo getImplementor() { return ClassInfo.this; }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class Field
/*      */     extends Member
/*      */   {
/*  535 */     public Field(ClassInfo.Member member) { super(member); }
/*      */ 
/*      */ 
/*      */     
/*  539 */     public Field(FieldNode field) { this(field, false); }
/*      */ 
/*      */     
/*      */     public Field(FieldNode field, boolean injected) {
/*  543 */       super(ClassInfo.Member.Type.FIELD, field.name, field.desc, field.access, injected);
/*      */       
/*  545 */       setUnique((Annotations.getVisible(field, org.spongepowered.asm.mixin.Unique.class) != null));
/*      */       
/*  547 */       if (Annotations.getVisible(field, org.spongepowered.asm.mixin.Shadow.class) != null) {
/*  548 */         boolean decoratedFinal = (Annotations.getVisible(field, org.spongepowered.asm.mixin.Final.class) != null);
/*  549 */         boolean decoratedMutable = (Annotations.getVisible(field, org.spongepowered.asm.mixin.Mutable.class) != null);
/*  550 */         setDecoratedFinal(decoratedFinal, decoratedMutable);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  555 */     public Field(String name, String desc, int access) { super(ClassInfo.Member.Type.FIELD, name, desc, access, false); }
/*      */ 
/*      */ 
/*      */     
/*  559 */     public Field(String name, String desc, int access, boolean injected) { super(ClassInfo.Member.Type.FIELD, name, desc, access, injected); }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  564 */     public ClassInfo getOwner() { return ClassInfo.this; }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/*  569 */       if (!(obj instanceof Field)) {
/*  570 */         return false;
/*      */       }
/*      */       
/*  573 */       return super.equals(obj);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  578 */     protected String getDisplayFormat() { return "%s:%s"; }
/*      */   }
/*      */ 
/*      */   
/*  582 */   private static final Logger logger = LogManager.getLogger("mixin");
/*      */   
/*  584 */   private static final Profiler profiler = MixinEnvironment.getProfiler();
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String JAVA_LANG_OBJECT = "java/lang/Object";
/*      */ 
/*      */ 
/*      */   
/*  592 */   private static final Map<String, ClassInfo> cache = new HashMap();
/*      */   
/*  594 */   private static final ClassInfo OBJECT = new ClassInfo(); private final String name;
/*      */   
/*      */   static  {
/*  597 */     cache.put("java/lang/Object", OBJECT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String superName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final String outerName;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isProbablyStatic;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<String> interfaces;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<Method> methods;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final Set<Field> fields;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  639 */   private final Set<MixinInfo> mixins = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  645 */   private final Map<ClassInfo, ClassInfo> correspondingTypes = new HashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MixinInfo mixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final MethodMapper methodMapper;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isMixin;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final boolean isInterface;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int access;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo superClass;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo outerClass;
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassSignature signature;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo() {
/*  688 */     this.name = "java/lang/Object";
/*  689 */     this.superName = null;
/*  690 */     this.outerName = null;
/*  691 */     this.isProbablyStatic = true;
/*  692 */     this.methods = ImmutableSet.of(new Method("getClass", "()Ljava/lang/Class;"), new Method("hashCode", "()I"), new Method("equals", "(Ljava/lang/Object;)Z"), new Method("clone", "()Ljava/lang/Object;"), new Method("toString", "()Ljava/lang/String;"), new Method("notify", "()V"), new Method[] { new Method("notifyAll", "()V"), new Method("wait", "(J)V"), new Method("wait", "(JI)V"), new Method("wait", "()V"), new Method("finalize", "()V") });
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
/*  705 */     this.fields = Collections.emptySet();
/*  706 */     this.isInterface = false;
/*  707 */     this.interfaces = Collections.emptySet();
/*  708 */     this.access = 1;
/*  709 */     this.isMixin = false;
/*  710 */     this.mixin = null;
/*  711 */     this.methodMapper = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo(ClassNode classNode) {
/*  720 */     timer = profiler.begin(1, "class.meta");
/*      */     try {
/*  722 */       this.name = classNode.name;
/*  723 */       this.superName = (classNode.superName != null) ? classNode.superName : "java/lang/Object";
/*  724 */       this.methods = new HashSet();
/*  725 */       this.fields = new HashSet();
/*  726 */       this.isInterface = ((classNode.access & 0x200) != 0);
/*  727 */       this.interfaces = new HashSet();
/*  728 */       this.access = classNode.access;
/*  729 */       this.isMixin = classNode instanceof MixinInfo.MixinClassNode;
/*  730 */       this.mixin = this.isMixin ? ((MixinInfo.MixinClassNode)classNode).getMixin() : null;
/*      */       
/*  732 */       this.interfaces.addAll(classNode.interfaces);
/*      */       
/*  734 */       for (MethodNode method : classNode.methods) {
/*  735 */         addMethod(method, this.isMixin);
/*      */       }
/*      */       
/*  738 */       boolean isProbablyStatic = true;
/*  739 */       String outerName = classNode.outerClass;
/*  740 */       for (FieldNode field : classNode.fields) {
/*  741 */         if ((field.access & 0x1000) != 0 && 
/*  742 */           field.name.startsWith("this$")) {
/*  743 */           isProbablyStatic = false;
/*  744 */           if (outerName == null) {
/*  745 */             outerName = field.desc;
/*  746 */             if (outerName != null && outerName.startsWith("L")) {
/*  747 */               outerName = outerName.substring(1, outerName.length() - 1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/*  753 */         this.fields.add(new Field(field, this.isMixin));
/*      */       } 
/*      */       
/*  756 */       this.isProbablyStatic = isProbablyStatic;
/*  757 */       this.outerName = outerName;
/*  758 */       this.methodMapper = new MethodMapper(MixinEnvironment.getCurrentEnvironment(), this);
/*  759 */       this.signature = ClassSignature.ofLazy(classNode);
/*      */     } finally {
/*  761 */       timer.end();
/*      */     } 
/*      */   }
/*      */   
/*      */   void addInterface(String iface) {
/*  766 */     this.interfaces.add(iface);
/*  767 */     getSignature().addInterface(iface);
/*      */   }
/*      */ 
/*      */   
/*  771 */   void addMethod(MethodNode method) { addMethod(method, true); }
/*      */ 
/*      */   
/*      */   private void addMethod(MethodNode method, boolean injected) {
/*  775 */     if (!method.name.startsWith("<")) {
/*  776 */       this.methods.add(new Method(method, injected));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void addMixin(MixinInfo mixin) {
/*  784 */     if (this.isMixin) {
/*  785 */       throw new IllegalArgumentException("Cannot add target " + this.name + " for " + mixin.getClassName() + " because the target is a mixin");
/*      */     }
/*  787 */     this.mixins.add(mixin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  794 */   public Set<MixinInfo> getMixins() { return Collections.unmodifiableSet(this.mixins); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  801 */   public boolean isMixin() { return this.isMixin; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  808 */   public boolean isPublic() { return ((this.access & true) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  815 */   public boolean isAbstract() { return ((this.access & 0x400) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  822 */   public boolean isSynthetic() { return ((this.access & 0x1000) != 0); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  829 */   public boolean isProbablyStatic() { return this.isProbablyStatic; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  836 */   public boolean isInner() { return (this.outerName != null); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  843 */   public boolean isInterface() { return this.isInterface; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  850 */   public Set<String> getInterfaces() { return Collections.unmodifiableSet(this.interfaces); }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  855 */   public String toString() { return this.name; }
/*      */ 
/*      */ 
/*      */   
/*  859 */   public MethodMapper getMethodMapper() { return this.methodMapper; }
/*      */ 
/*      */ 
/*      */   
/*  863 */   public int getAccess() { return this.access; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  870 */   public String getName() { return this.name; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  877 */   public String getClassName() { return this.name.replace('/', '.'); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  884 */   public String getSuperName() { return this.superName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getSuperClass() {
/*  892 */     if (this.superClass == null && this.superName != null) {
/*  893 */       this.superClass = forName(this.superName);
/*      */     }
/*      */     
/*  896 */     return this.superClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  903 */   public String getOuterName() { return this.outerName; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ClassInfo getOuterClass() {
/*  911 */     if (this.outerClass == null && this.outerName != null) {
/*  912 */       this.outerClass = forName(this.outerName);
/*      */     }
/*      */     
/*  915 */     return this.outerClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  924 */   public ClassSignature getSignature() { return this.signature.wake(); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   List<ClassInfo> getTargets() {
/*  931 */     if (this.mixin != null) {
/*  932 */       List<ClassInfo> targets = new ArrayList<ClassInfo>();
/*  933 */       targets.add(this);
/*  934 */       targets.addAll(this.mixin.getTargets());
/*  935 */       return targets;
/*      */     } 
/*      */     
/*  938 */     return ImmutableList.of(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  947 */   public Set<Method> getMethods() { return Collections.unmodifiableSet(this.methods); }
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
/*      */   public Set<Method> getInterfaceMethods(boolean includeMixins) {
/*  961 */     Set<Method> methods = new HashSet<Method>();
/*      */     
/*  963 */     ClassInfo supClass = addMethodsRecursive(methods, includeMixins);
/*  964 */     if (!this.isInterface) {
/*  965 */       while (supClass != null && supClass != OBJECT) {
/*  966 */         supClass = supClass.addMethodsRecursive(methods, includeMixins);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*  971 */     for (Iterator<Method> it = methods.iterator(); it.hasNext();) {
/*  972 */       if (!((Method)it.next()).isAbstract()) {
/*  973 */         it.remove();
/*      */       }
/*      */     } 
/*      */     
/*  977 */     return Collections.unmodifiableSet(methods);
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
/*      */   private ClassInfo addMethodsRecursive(Set<Method> methods, boolean includeMixins) {
/*  990 */     if (this.isInterface) {
/*  991 */       for (Method method : this.methods) {
/*      */         
/*  993 */         if (!method.isAbstract())
/*      */         {
/*  995 */           methods.remove(method);
/*      */         }
/*  997 */         methods.add(method);
/*      */       } 
/*  999 */     } else if (!this.isMixin && includeMixins) {
/* 1000 */       for (MixinInfo mixin : this.mixins) {
/* 1001 */         mixin.getClassInfo().addMethodsRecursive(methods, includeMixins);
/*      */       }
/*      */     } 
/*      */     
/* 1005 */     for (String iface : this.interfaces) {
/* 1006 */       forName(iface).addMethodsRecursive(methods, includeMixins);
/*      */     }
/*      */     
/* 1009 */     return getSuperClass();
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
/* 1020 */   public boolean hasSuperClass(String superClass) { return hasSuperClass(superClass, Traversal.NONE); }
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
/*      */   public boolean hasSuperClass(String superClass, Traversal traversal) {
/* 1032 */     if ("java/lang/Object".equals(superClass)) {
/* 1033 */       return true;
/*      */     }
/*      */     
/* 1036 */     return (findSuperClass(superClass, traversal) != null);
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
/* 1047 */   public boolean hasSuperClass(ClassInfo superClass) { return hasSuperClass(superClass, Traversal.NONE, false); }
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
/* 1059 */   public boolean hasSuperClass(ClassInfo superClass, Traversal traversal) { return hasSuperClass(superClass, traversal, false); }
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
/*      */   public boolean hasSuperClass(ClassInfo superClass, Traversal traversal, boolean includeInterfaces) {
/* 1072 */     if (OBJECT == superClass) {
/* 1073 */       return true;
/*      */     }
/*      */     
/* 1076 */     return (findSuperClass(superClass.name, traversal, includeInterfaces) != null);
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
/* 1087 */   public ClassInfo findSuperClass(String superClass) { return findSuperClass(superClass, Traversal.NONE); }
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
/* 1099 */   public ClassInfo findSuperClass(String superClass, Traversal traversal) { return findSuperClass(superClass, traversal, false, new HashSet()); }
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
/*      */   public ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces) {
/* 1112 */     if (OBJECT.name.equals(superClass)) {
/* 1113 */       return null;
/*      */     }
/*      */     
/* 1116 */     return findSuperClass(superClass, traversal, includeInterfaces, new HashSet());
/*      */   }
/*      */   
/*      */   private ClassInfo findSuperClass(String superClass, Traversal traversal, boolean includeInterfaces, Set<String> traversed) {
/* 1120 */     ClassInfo superClassInfo = getSuperClass();
/* 1121 */     if (superClassInfo != null) {
/* 1122 */       for (ClassInfo superTarget : superClassInfo.getTargets()) {
/* 1123 */         if (superClass.equals(superTarget.getName())) {
/* 1124 */           return superClassInfo;
/*      */         }
/*      */         
/* 1127 */         ClassInfo found = superTarget.findSuperClass(superClass, traversal.next(), includeInterfaces, traversed);
/* 1128 */         if (found != null) {
/* 1129 */           return found;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1134 */     if (includeInterfaces) {
/* 1135 */       ClassInfo iface = findInterface(superClass);
/* 1136 */       if (iface != null) {
/* 1137 */         return iface;
/*      */       }
/*      */     } 
/*      */     
/* 1141 */     if (traversal.canTraverse()) {
/* 1142 */       for (MixinInfo mixin : this.mixins) {
/* 1143 */         String mixinClassName = mixin.getClassName();
/* 1144 */         if (traversed.contains(mixinClassName)) {
/*      */           continue;
/*      */         }
/* 1147 */         traversed.add(mixinClassName);
/* 1148 */         ClassInfo mixinClass = mixin.getClassInfo();
/* 1149 */         if (superClass.equals(mixinClass.getName())) {
/* 1150 */           return mixinClass;
/*      */         }
/* 1152 */         ClassInfo targetSuper = mixinClass.findSuperClass(superClass, Traversal.ALL, includeInterfaces, traversed);
/* 1153 */         if (targetSuper != null) {
/* 1154 */           return targetSuper;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1159 */     return null;
/*      */   }
/*      */   
/*      */   private ClassInfo findInterface(String superClass) {
/* 1163 */     for (String ifaceName : getInterfaces()) {
/* 1164 */       ClassInfo iface = forName(ifaceName);
/* 1165 */       if (superClass.equals(ifaceName)) {
/* 1166 */         return iface;
/*      */       }
/* 1168 */       ClassInfo superIface = iface.findInterface(superClass);
/* 1169 */       if (superIface != null) {
/* 1170 */         return superIface;
/*      */       }
/*      */     } 
/* 1173 */     return null;
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
/*      */   ClassInfo findCorrespondingType(ClassInfo mixin) {
/* 1187 */     if (mixin == null || !mixin.isMixin || this.isMixin) {
/* 1188 */       return null;
/*      */     }
/*      */     
/* 1191 */     ClassInfo correspondingType = (ClassInfo)this.correspondingTypes.get(mixin);
/* 1192 */     if (correspondingType == null) {
/* 1193 */       correspondingType = findSuperTypeForMixin(mixin);
/* 1194 */       this.correspondingTypes.put(mixin, correspondingType);
/*      */     } 
/* 1196 */     return correspondingType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ClassInfo findSuperTypeForMixin(ClassInfo mixin) {
/* 1204 */     ClassInfo superClass = this;
/*      */     
/* 1206 */     while (superClass != null && superClass != OBJECT) {
/* 1207 */       for (MixinInfo minion : superClass.mixins) {
/* 1208 */         if (minion.getClassInfo().equals(mixin)) {
/* 1209 */           return superClass;
/*      */         }
/*      */       } 
/*      */       
/* 1213 */       superClass = superClass.getSuperClass();
/*      */     } 
/*      */     
/* 1216 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasMixinInHierarchy() {
/* 1227 */     if (!this.isMixin) {
/* 1228 */       return false;
/*      */     }
/*      */     
/* 1231 */     ClassInfo supClass = getSuperClass();
/*      */     
/* 1233 */     while (supClass != null && supClass != OBJECT) {
/* 1234 */       if (supClass.isMixin) {
/* 1235 */         return true;
/*      */       }
/* 1237 */       supClass = supClass.getSuperClass();
/*      */     } 
/*      */     
/* 1240 */     return false;
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
/*      */   public boolean hasMixinTargetInHierarchy() {
/* 1252 */     if (this.isMixin) {
/* 1253 */       return false;
/*      */     }
/*      */     
/* 1256 */     ClassInfo supClass = getSuperClass();
/*      */     
/* 1258 */     while (supClass != null && supClass != OBJECT) {
/* 1259 */       if (supClass.mixins.size() > 0) {
/* 1260 */         return true;
/*      */       }
/* 1262 */       supClass = supClass.getSuperClass();
/*      */     } 
/*      */     
/* 1265 */     return false;
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
/* 1276 */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType) { return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE); }
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
/* 1288 */   public Method findMethodInHierarchy(MethodNode method, SearchType searchType, int flags) { return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags); }
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
/* 1299 */   public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType) { return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE); }
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
/* 1311 */   public Method findMethodInHierarchy(MethodInsnNode method, SearchType searchType, int flags) { return findMethodInHierarchy(method.name, method.desc, searchType, Traversal.NONE, flags); }
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
/* 1323 */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType) { return findMethodInHierarchy(name, desc, searchType, Traversal.NONE); }
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
/* 1336 */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) { return findMethodInHierarchy(name, desc, searchType, traversal, 0); }
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
/* 1350 */   public Method findMethodInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) { return (Method)findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.METHOD); }
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
/* 1361 */   public Field findFieldInHierarchy(FieldNode field, SearchType searchType) { return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE); }
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
/* 1373 */   public Field findFieldInHierarchy(FieldNode field, SearchType searchType, int flags) { return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags); }
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
/* 1384 */   public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType) { return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE); }
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
/* 1396 */   public Field findFieldInHierarchy(FieldInsnNode field, SearchType searchType, int flags) { return findFieldInHierarchy(field.name, field.desc, searchType, Traversal.NONE, flags); }
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
/* 1408 */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType) { return findFieldInHierarchy(name, desc, searchType, Traversal.NONE); }
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
/* 1421 */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal) { return findFieldInHierarchy(name, desc, searchType, traversal, 0); }
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
/* 1435 */   public Field findFieldInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags) { return (Field)findInHierarchy(name, desc, searchType, traversal, flags, Member.Type.FIELD); }
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
/*      */   private <M extends Member> M findInHierarchy(String name, String desc, SearchType searchType, Traversal traversal, int flags, Member.Type type) {
/* 1452 */     if (searchType == SearchType.ALL_CLASSES) {
/* 1453 */       M member = (M)findMember(name, desc, flags, type);
/* 1454 */       if (member != null) {
/* 1455 */         return member;
/*      */       }
/*      */       
/* 1458 */       if (traversal.canTraverse()) {
/* 1459 */         for (MixinInfo mixin : this.mixins) {
/* 1460 */           M mixinMember = (M)mixin.getClassInfo().findMember(name, desc, flags, type);
/* 1461 */           if (mixinMember != null) {
/* 1462 */             return (M)cloneMember(mixinMember);
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1468 */     ClassInfo superClassInfo = getSuperClass();
/* 1469 */     if (superClassInfo != null) {
/* 1470 */       for (ClassInfo superTarget : superClassInfo.getTargets()) {
/* 1471 */         M member = (M)superTarget.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & 0xFFFFFFFD, type);
/*      */         
/* 1473 */         if (member != null) {
/* 1474 */           return member;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1479 */     if (type == Member.Type.METHOD && (this.isInterface || MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces())) {
/* 1480 */       for (String implemented : this.interfaces) {
/* 1481 */         ClassInfo iface = forName(implemented);
/* 1482 */         if (iface == null) {
/* 1483 */           logger.debug("Failed to resolve declared interface {} on {}", new Object[] { implemented, this.name });
/*      */           
/*      */           continue;
/*      */         } 
/* 1487 */         M member = (M)iface.findInHierarchy(name, desc, SearchType.ALL_CLASSES, traversal.next(), flags & 0xFFFFFFFD, type);
/* 1488 */         if (member != null) {
/* 1489 */           return this.isInterface ? member : new InterfaceMethod(member);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1494 */     return null;
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
/*      */   private <M extends Member> M cloneMember(M member) {
/* 1508 */     if (member instanceof Method) {
/* 1509 */       return (M)new Method(member);
/*      */     }
/*      */     
/* 1512 */     return (M)new Field(member);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1522 */   public Method findMethod(MethodNode method) { return findMethod(method.name, method.desc, method.access); }
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
/* 1533 */   public Method findMethod(MethodNode method, int flags) { return findMethod(method.name, method.desc, flags); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1543 */   public Method findMethod(MethodInsnNode method) { return findMethod(method.name, method.desc, 0); }
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
/* 1554 */   public Method findMethod(MethodInsnNode method, int flags) { return findMethod(method.name, method.desc, flags); }
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
/* 1566 */   public Method findMethod(String name, String desc, int flags) { return (Method)findMember(name, desc, flags, Member.Type.METHOD); }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1576 */   public Field findField(FieldNode field) { return findField(field.name, field.desc, field.access); }
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
/* 1587 */   public Field findField(FieldInsnNode field, int flags) { return findField(field.name, field.desc, flags); }
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
/* 1599 */   public Field findField(String name, String desc, int flags) { return (Field)findMember(name, desc, flags, Member.Type.FIELD); }
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
/*      */   private <M extends Member> M findMember(String name, String desc, int flags, Member.Type memberType) {
/* 1613 */     Set<M> members = (memberType == Member.Type.METHOD) ? this.methods : this.fields;
/*      */     
/* 1615 */     for (Iterator iterator = members.iterator(); iterator.hasNext(); ) { M member = (M)(Member)iterator.next();
/* 1616 */       if (member.equals(name, desc) && member.matchesFlags(flags)) {
/* 1617 */         return member;
/*      */       } }
/*      */ 
/*      */     
/* 1621 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object other) {
/* 1629 */     if (!(other instanceof ClassInfo)) {
/* 1630 */       return false;
/*      */     }
/* 1632 */     return ((ClassInfo)other).name.equals(this.name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1640 */   public int hashCode() { return this.name.hashCode(); }
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
/*      */   static ClassInfo fromClassNode(ClassNode classNode) {
/* 1653 */     ClassInfo info = (ClassInfo)cache.get(classNode.name);
/* 1654 */     if (info == null) {
/* 1655 */       info = new ClassInfo(classNode);
/* 1656 */       cache.put(classNode.name, info);
/*      */     } 
/*      */     
/* 1659 */     return info;
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
/*      */   public static ClassInfo forName(String className) {
/* 1671 */     className = className.replace('.', '/');
/*      */     
/* 1673 */     ClassInfo info = (ClassInfo)cache.get(className);
/* 1674 */     if (info == null) {
/*      */       try {
/* 1676 */         ClassNode classNode = MixinService.getService().getBytecodeProvider().getClassNode(className);
/* 1677 */         info = new ClassInfo(classNode);
/* 1678 */       } catch (Exception ex) {
/* 1679 */         logger.catching(Level.TRACE, ex);
/* 1680 */         logger.warn("Error loading class: {} ({}: {})", new Object[] { className, ex.getClass().getName(), ex.getMessage() });
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1685 */       cache.put(className, info);
/* 1686 */       logger.trace("Added class metadata for {} to metadata cache", new Object[] { className });
/*      */     } 
/*      */     
/* 1689 */     return info;
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
/*      */   public static ClassInfo forType(Type type) {
/* 1701 */     if (type.getSort() == 9)
/* 1702 */       return forType(type.getElementType()); 
/* 1703 */     if (type.getSort() < 9) {
/* 1704 */       return null;
/*      */     }
/* 1706 */     return forName(type.getClassName().replace('.', '/'));
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
/*      */   public static ClassInfo getCommonSuperClass(String type1, String type2) {
/* 1718 */     if (type1 == null || type2 == null) {
/* 1719 */       return OBJECT;
/*      */     }
/* 1721 */     return getCommonSuperClass(forName(type1), forName(type2));
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
/*      */   public static ClassInfo getCommonSuperClass(Type type1, Type type2) {
/* 1733 */     if (type1 == null || type2 == null || type1
/* 1734 */       .getSort() != 10 || type2.getSort() != 10) {
/* 1735 */       return OBJECT;
/*      */     }
/* 1737 */     return getCommonSuperClass(forType(type1), forType(type2));
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
/* 1749 */   private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2) { return getCommonSuperClass(type1, type2, false); }
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
/*      */   public static ClassInfo getCommonSuperClassOrInterface(String type1, String type2) {
/* 1761 */     if (type1 == null || type2 == null) {
/* 1762 */       return OBJECT;
/*      */     }
/* 1764 */     return getCommonSuperClassOrInterface(forName(type1), forName(type2));
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
/*      */   public static ClassInfo getCommonSuperClassOrInterface(Type type1, Type type2) {
/* 1776 */     if (type1 == null || type2 == null || type1
/* 1777 */       .getSort() != 10 || type2.getSort() != 10) {
/* 1778 */       return OBJECT;
/*      */     }
/* 1780 */     return getCommonSuperClassOrInterface(forType(type1), forType(type2));
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
/* 1792 */   public static ClassInfo getCommonSuperClassOrInterface(ClassInfo type1, ClassInfo type2) { return getCommonSuperClass(type1, type2, true); }
/*      */ 
/*      */   
/*      */   private static ClassInfo getCommonSuperClass(ClassInfo type1, ClassInfo type2, boolean includeInterfaces) {
/* 1796 */     if (type1.hasSuperClass(type2, Traversal.NONE, includeInterfaces))
/* 1797 */       return type2; 
/* 1798 */     if (type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces))
/* 1799 */       return type1; 
/* 1800 */     if (type1.isInterface() || type2.isInterface()) {
/* 1801 */       return OBJECT;
/*      */     }
/*      */     
/*      */     do {
/* 1805 */       type1 = type1.getSuperClass();
/* 1806 */       if (type1 == null) {
/* 1807 */         return OBJECT;
/*      */       }
/* 1809 */     } while (!type2.hasSuperClass(type1, Traversal.NONE, includeInterfaces));
/*      */     
/* 1811 */     return type1;
/*      */   }
/*      */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\ClassInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */