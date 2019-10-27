/*     */ package org.spongepowered.asm.obfuscation.mapping.common;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Strings;
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
/*     */ 
/*     */ public class MappingField
/*     */   extends Object
/*     */   implements IMapping<MappingField>
/*     */ {
/*     */   private final String owner;
/*     */   private final String name;
/*     */   private final String desc;
/*     */   
/*  44 */   public MappingField(String owner, String name) { this(owner, name, null); }
/*     */ 
/*     */   
/*     */   public MappingField(String owner, String name, String desc) {
/*  48 */     this.owner = owner;
/*  49 */     this.name = name;
/*  50 */     this.desc = desc;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public IMapping.Type getType() { return IMapping.Type.FIELD; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public final String getSimpleName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public final String getOwner() { return this.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public final String getDesc() { return this.desc; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public MappingField getSuper() { return null; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public MappingField move(String newOwner) { return new MappingField(newOwner, getName(), getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public MappingField remap(String newName) { return new MappingField(getOwner(), newName, getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public MappingField transform(String newDesc) { return new MappingField(getOwner(), getName(), newDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public MappingField copy() { return new MappingField(getOwner(), getName(), getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public int hashCode() { return Objects.hashCode(new Object[] { toString() }); }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 110 */     if (this == obj) {
/* 111 */       return true;
/*     */     }
/* 113 */     if (obj instanceof MappingField) {
/* 114 */       return Objects.equal(toString(), ((MappingField)obj).toString());
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 121 */   public String serialise() { return toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String toString() { return String.format("L%s;%s:%s", new Object[] { getOwner(), getName(), Strings.nullToEmpty(getDesc()) }); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\obfuscation\mapping\common\MappingField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */