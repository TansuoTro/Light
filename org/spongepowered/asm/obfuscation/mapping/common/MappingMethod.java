/*     */ package org.spongepowered.asm.obfuscation.mapping.common;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MappingMethod
/*     */   extends Object
/*     */   implements IMapping<MappingMethod>
/*     */ {
/*     */   private final String owner;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   
/*  42 */   public MappingMethod(String fullyQualifiedName, String desc) { this(getOwnerFromName(fullyQualifiedName), getBaseName(fullyQualifiedName), desc); }
/*     */ 
/*     */   
/*     */   public MappingMethod(String owner, String simpleName, String desc) {
/*  46 */     this.owner = owner;
/*  47 */     this.name = simpleName;
/*  48 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  53 */   public IMapping.Type getType() { return IMapping.Type.METHOD; }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  58 */     if (this.name == null) {
/*  59 */       return null;
/*     */     }
/*  61 */     return ((this.owner != null) ? (this.owner + "/") : "") + this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  66 */   public String getSimpleName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   public String getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   public String getDesc() { return this.desc; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public MappingMethod getSuper() { return null; }
/*     */ 
/*     */ 
/*     */   
/*  85 */   public boolean isConstructor() { return "<init>".equals(this.name); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public MappingMethod move(String newOwner) { return new MappingMethod(newOwner, getSimpleName(), getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public MappingMethod remap(String newName) { return new MappingMethod(getOwner(), newName, getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public MappingMethod transform(String newDesc) { return new MappingMethod(getOwner(), getSimpleName(), newDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public MappingMethod copy() { return new MappingMethod(getOwner(), getSimpleName(), getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod addPrefix(String prefix) {
/* 116 */     String simpleName = getSimpleName();
/* 117 */     if (simpleName == null || simpleName.startsWith(prefix)) {
/* 118 */       return this;
/*     */     }
/* 120 */     return new MappingMethod(getOwner(), prefix + simpleName, getDesc());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 125 */   public int hashCode() { return Objects.hashCode(new Object[] { getName(), getDesc() }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 130 */     if (this == obj) {
/* 131 */       return true;
/*     */     }
/* 133 */     if (obj instanceof MappingMethod) {
/* 134 */       return (Objects.equal(this.name, ((MappingMethod)obj).name) && Objects.equal(this.desc, ((MappingMethod)obj).desc));
/*     */     }
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 141 */   public String serialise() { return toString(); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     String desc = getDesc();
/* 147 */     return String.format("%s%s%s", new Object[] { getName(), (desc != null) ? " " : "", (desc != null) ? desc : "" });
/*     */   }
/*     */   
/*     */   private static String getBaseName(String name) {
/* 151 */     if (name == null) {
/* 152 */       return null;
/*     */     }
/* 154 */     int pos = name.lastIndexOf('/');
/* 155 */     return (pos > -1) ? name.substring(pos + 1) : name;
/*     */   }
/*     */   
/*     */   private static String getOwnerFromName(String name) {
/* 159 */     if (name == null) {
/* 160 */       return null;
/*     */     }
/* 162 */     int pos = name.lastIndexOf('/');
/* 163 */     return (pos > -1) ? name.substring(0, pos) : null;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\obfuscation\mapping\common\MappingMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */