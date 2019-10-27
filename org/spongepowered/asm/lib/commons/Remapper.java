/*     */ package org.spongepowered.asm.lib.commons;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Handle;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.signature.SignatureReader;
/*     */ import org.spongepowered.asm.lib.signature.SignatureVisitor;
/*     */ import org.spongepowered.asm.lib.signature.SignatureWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Remapper
/*     */ {
/*     */   public String mapDesc(String desc) {
/*     */     int i;
/*     */     String newType, s;
/*  54 */     Type t = Type.getType(desc);
/*  55 */     switch (t.getSort()) {
/*     */       case 9:
/*  57 */         s = mapDesc(t.getElementType().getDescriptor());
/*  58 */         for (i = 0; i < t.getDimensions(); i++) {
/*  59 */           s = '[' + s;
/*     */         }
/*  61 */         return s;
/*     */       case 10:
/*  63 */         newType = map(t.getInternalName());
/*  64 */         if (newType != null)
/*  65 */           return 'L' + newType + ';'; 
/*     */         break;
/*     */     } 
/*  68 */     return desc; } private Type mapType(Type t) {
/*     */     int i;
/*     */     String s;
/*     */     String s;
/*  72 */     switch (t.getSort()) {
/*     */       case 9:
/*  74 */         s = mapDesc(t.getElementType().getDescriptor());
/*  75 */         for (i = 0; i < t.getDimensions(); i++) {
/*  76 */           s = '[' + s;
/*     */         }
/*  78 */         return Type.getType(s);
/*     */       case 10:
/*  80 */         s = map(t.getInternalName());
/*  81 */         return (s != null) ? Type.getObjectType(s) : t;
/*     */       case 11:
/*  83 */         return Type.getMethodType(mapMethodDesc(t.getDescriptor()));
/*     */     } 
/*  85 */     return t;
/*     */   }
/*     */   
/*     */   public String mapType(String type) {
/*  89 */     if (type == null) {
/*  90 */       return null;
/*     */     }
/*  92 */     return mapType(Type.getObjectType(type)).getInternalName();
/*     */   }
/*     */   
/*     */   public String[] mapTypes(String[] types) {
/*  96 */     String[] newTypes = null;
/*  97 */     boolean needMapping = false;
/*  98 */     for (int i = 0; i < types.length; i++) {
/*  99 */       String type = types[i];
/* 100 */       String newType = map(type);
/* 101 */       if (newType != null && newTypes == null) {
/* 102 */         newTypes = new String[types.length];
/* 103 */         if (i > 0) {
/* 104 */           System.arraycopy(types, 0, newTypes, 0, i);
/*     */         }
/* 106 */         needMapping = true;
/*     */       } 
/* 108 */       if (needMapping) {
/* 109 */         newTypes[i] = (newType == null) ? type : newType;
/*     */       }
/*     */     } 
/* 112 */     return needMapping ? newTypes : types;
/*     */   }
/*     */   
/*     */   public String mapMethodDesc(String desc) {
/* 116 */     if ("()V".equals(desc)) {
/* 117 */       return desc;
/*     */     }
/*     */     
/* 120 */     Type[] args = Type.getArgumentTypes(desc);
/* 121 */     StringBuilder sb = new StringBuilder("(");
/* 122 */     for (int i = 0; i < args.length; i++) {
/* 123 */       sb.append(mapDesc(args[i].getDescriptor()));
/*     */     }
/* 125 */     Type returnType = Type.getReturnType(desc);
/* 126 */     if (returnType == Type.VOID_TYPE) {
/* 127 */       sb.append(")V");
/* 128 */       return sb.toString();
/*     */     } 
/* 130 */     sb.append(')').append(mapDesc(returnType.getDescriptor()));
/* 131 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public Object mapValue(Object value) {
/* 135 */     if (value instanceof Type) {
/* 136 */       return mapType((Type)value);
/*     */     }
/* 138 */     if (value instanceof Handle) {
/* 139 */       Handle h = (Handle)value;
/* 140 */       return new Handle(h.getTag(), mapType(h.getOwner()), mapMethodName(h
/* 141 */             .getOwner(), h.getName(), h.getDesc()), 
/* 142 */           mapMethodDesc(h.getDesc()), h.isInterface());
/*     */     } 
/* 144 */     return value;
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
/*     */   public String mapSignature(String signature, boolean typeSignature) {
/* 157 */     if (signature == null) {
/* 158 */       return null;
/*     */     }
/* 160 */     SignatureReader r = new SignatureReader(signature);
/* 161 */     SignatureWriter w = new SignatureWriter();
/* 162 */     SignatureVisitor a = createSignatureRemapper(w);
/* 163 */     if (typeSignature) {
/* 164 */       r.acceptType(a);
/*     */     } else {
/* 166 */       r.accept(a);
/*     */     } 
/* 168 */     return w.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/* 177 */   protected SignatureVisitor createRemappingSignatureAdapter(SignatureVisitor v) { return new SignatureRemapper(v, this); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   protected SignatureVisitor createSignatureRemapper(SignatureVisitor v) { return createRemappingSignatureAdapter(v); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 197 */   public String mapMethodName(String owner, String name, String desc) { return name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   public String mapInvokeDynamicMethodName(String name, String desc) { return name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 225 */   public String mapFieldName(String owner, String name, String desc) { return name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public String map(String typeName) { return typeName; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\commons\Remapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */