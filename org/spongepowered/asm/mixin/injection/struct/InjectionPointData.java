/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Strings;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.InjectionPoint;
/*     */ import org.spongepowered.asm.mixin.injection.modify.LocalVariableDiscriminator;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionPointException;
/*     */ import org.spongepowered.asm.mixin.refmap.IMixinContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InjectionPointData
/*     */ {
/*  55 */   private static final Pattern AT_PATTERN = createPattern(); private final Map<String, String> args; private final IMixinContext context; private final MethodNode method; private final AnnotationNode parent;
/*     */   private final String at;
/*     */   private final String type;
/*     */   
/*     */   public InjectionPointData(IMixinContext context, MethodNode method, AnnotationNode parent, String at, List<String> args, String target, String slice, int ordinal, int opcode, String id) {
/*  60 */     this.args = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     this.context = context;
/* 120 */     this.method = method;
/* 121 */     this.parent = parent;
/* 122 */     this.at = at;
/* 123 */     this.target = target;
/* 124 */     this.slice = Strings.nullToEmpty(slice);
/* 125 */     this.ordinal = Math.max(-1, ordinal);
/* 126 */     this.opcode = opcode;
/* 127 */     this.id = id;
/*     */     
/* 129 */     parseArgs(args);
/*     */     
/* 131 */     this.args.put("target", target);
/* 132 */     this.args.put("ordinal", String.valueOf(ordinal));
/* 133 */     this.args.put("opcode", String.valueOf(opcode));
/*     */     
/* 135 */     Matcher matcher = AT_PATTERN.matcher(at);
/* 136 */     this.type = parseType(matcher, at);
/* 137 */     this.selector = parseSelector(matcher);
/*     */   }
/*     */   private final InjectionPoint.Selector selector; private final String target; private final String slice; private final int ordinal; private final int opcode; private final String id;
/*     */   private void parseArgs(List<String> args) {
/* 141 */     if (args == null) {
/*     */       return;
/*     */     }
/* 144 */     for (String arg : args) {
/* 145 */       if (arg != null) {
/* 146 */         int eqPos = arg.indexOf('=');
/* 147 */         if (eqPos > -1) {
/* 148 */           this.args.put(arg.substring(0, eqPos), arg.substring(eqPos + 1)); continue;
/*     */         } 
/* 150 */         this.args.put(arg, "");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 160 */   public String getAt() { return this.at; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 167 */   public String getType() { return this.type; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 174 */   public InjectionPoint.Selector getSelector() { return this.selector; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 181 */   public IMixinContext getContext() { return this.context; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 188 */   public MethodNode getMethod() { return this.method; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   public Type getMethodReturnType() { return Type.getReturnType(this.method.desc); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 202 */   public AnnotationNode getParent() { return this.parent; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 209 */   public String getSlice() { return this.slice; }
/*     */ 
/*     */ 
/*     */   
/* 213 */   public LocalVariableDiscriminator getLocalVariableDiscriminator() { return LocalVariableDiscriminator.parse(this.parent); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String get(String key, String defaultValue) {
/* 225 */     String value = (String)this.args.get(key);
/* 226 */     return (value != null) ? value : defaultValue;
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
/* 238 */   public int get(String key, int defaultValue) { return parseInt(get(key, String.valueOf(defaultValue)), defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 250 */   public boolean get(String key, boolean defaultValue) { return parseBoolean(get(key, String.valueOf(defaultValue)), defaultValue); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo get(String key) {
/*     */     try {
/* 262 */       return MemberInfo.parseAndValidate(get(key, ""), this.context);
/* 263 */     } catch (InvalidMemberDescriptorException ex) {
/* 264 */       throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\").%s descriptor \"%s\" on %s", new Object[] { this.at, key, this.target, 
/* 265 */             InjectionInfo.describeInjector(this.context, this.parent, this.method) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MemberInfo getTarget() {
/*     */     try {
/* 274 */       return MemberInfo.parseAndValidate(this.target, this.context);
/* 275 */     } catch (InvalidMemberDescriptorException ex) {
/* 276 */       throw new InvalidInjectionPointException(this.context, "Failed parsing @At(\"%s\") descriptor \"%s\" on %s", new Object[] { this.at, this.target, 
/* 277 */             InjectionInfo.describeInjector(this.context, this.parent, this.method) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   public int getOrdinal() { return this.ordinal; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 292 */   public int getOpcode() { return this.opcode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 303 */   public int getOpcode(int defaultOpcode) { return (this.opcode > 0) ? this.opcode : defaultOpcode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpcode(int defaultOpcode, int... validOpcodes) {
/* 316 */     for (int validOpcode : validOpcodes) {
/* 317 */       if (this.opcode == validOpcode) {
/* 318 */         return this.opcode;
/*     */       }
/*     */     } 
/* 321 */     return defaultOpcode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 328 */   public String getId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 333 */   public String toString() { return this.type; }
/*     */ 
/*     */ 
/*     */   
/* 337 */   private static Pattern createPattern() { return Pattern.compile(String.format("^([^:]+):?(%s)?$", new Object[] { Joiner.on('|').join(InjectionPoint.Selector.values()) })); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String parseType(String at) {
/* 347 */     Matcher matcher = AT_PATTERN.matcher(at);
/* 348 */     return parseType(matcher, at);
/*     */   }
/*     */ 
/*     */   
/* 352 */   private static String parseType(Matcher matcher, String at) { return matcher.matches() ? matcher.group(1) : at; }
/*     */ 
/*     */ 
/*     */   
/* 356 */   private static InjectionPoint.Selector parseSelector(Matcher matcher) { return (matcher.matches() && matcher.group(2) != null) ? InjectionPoint.Selector.valueOf(matcher.group(2)) : InjectionPoint.Selector.DEFAULT; }
/*     */ 
/*     */   
/*     */   private static int parseInt(String string, int defaultValue) {
/*     */     try {
/* 361 */       return Integer.parseInt(string);
/* 362 */     } catch (Exception ex) {
/* 363 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean parseBoolean(String string, boolean defaultValue) {
/*     */     try {
/* 369 */       return Boolean.parseBoolean(string);
/* 370 */     } catch (Exception ex) {
/* 371 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\InjectionPointData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */