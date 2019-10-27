/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldHandle
/*     */   extends MemberHandle<MappingField>
/*     */ {
/*     */   private final VariableElement element;
/*     */   private final boolean rawType;
/*     */   
/*  51 */   public FieldHandle(TypeElement owner, VariableElement element) { this(TypeUtils.getInternalName(owner), element); }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public FieldHandle(String owner, VariableElement element) { this(owner, element, false); }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public FieldHandle(TypeElement owner, VariableElement element, boolean rawType) { this(TypeUtils.getInternalName(owner), element, rawType); }
/*     */ 
/*     */ 
/*     */   
/*  63 */   public FieldHandle(String owner, VariableElement element, boolean rawType) { this(owner, element, rawType, TypeUtils.getName(element), TypeUtils.getInternalName(element)); }
/*     */ 
/*     */ 
/*     */   
/*  67 */   public FieldHandle(String owner, String name, String desc) { this(owner, null, false, name, desc); }
/*     */ 
/*     */   
/*     */   private FieldHandle(String owner, VariableElement element, boolean rawType, String name, String desc) {
/*  71 */     super(owner, name, desc);
/*  72 */     this.element = element;
/*  73 */     this.rawType = rawType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public boolean isImaginary() { return (this.element == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public VariableElement getElement() { return this.element; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public Visibility getVisibility() { return TypeUtils.getVisibility(this.element); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public boolean isRawType() { return this.rawType; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public MappingField asMapping(boolean includeOwner) { return new MappingField(includeOwner ? getOwner() : null, getName(), getDesc()); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 110 */     String owner = (getOwner() != null) ? ("L" + getOwner() + ";") : "";
/* 111 */     String name = Strings.nullToEmpty(getName());
/* 112 */     String desc = Strings.nullToEmpty(getDesc());
/* 113 */     return String.format("%s%s:%s", new Object[] { owner, name, desc });
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mirror\FieldHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */