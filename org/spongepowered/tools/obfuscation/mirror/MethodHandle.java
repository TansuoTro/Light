/*    */ package org.spongepowered.tools.obfuscation.mirror;
/*    */ 
/*    */ import com.google.common.base.Strings;
/*    */ import javax.lang.model.element.ExecutableElement;
/*    */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.mirror.mapping.ResolvableMappingMethod;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodHandle
/*    */   extends MemberHandle<MappingMethod>
/*    */ {
/*    */   private final ExecutableElement element;
/*    */   private final TypeHandle ownerHandle;
/*    */   
/* 50 */   public MethodHandle(TypeHandle owner, ExecutableElement element) { this(owner, element, TypeUtils.getName(element), TypeUtils.getDescriptor(element)); }
/*    */ 
/*    */ 
/*    */   
/* 54 */   public MethodHandle(TypeHandle owner, String name, String desc) { this(owner, null, name, desc); }
/*    */ 
/*    */   
/*    */   private MethodHandle(TypeHandle owner, ExecutableElement element, String name, String desc) {
/* 58 */     super((owner != null) ? owner.getName() : null, name, desc);
/* 59 */     this.element = element;
/* 60 */     this.ownerHandle = owner;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public boolean isImaginary() { return (this.element == null); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 74 */   public ExecutableElement getElement() { return this.element; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 79 */   public Visibility getVisibility() { return TypeUtils.getVisibility(this.element); }
/*    */ 
/*    */ 
/*    */   
/*    */   public MappingMethod asMapping(boolean includeOwner) {
/* 84 */     if (includeOwner) {
/* 85 */       if (this.ownerHandle != null) {
/* 86 */         return new ResolvableMappingMethod(this.ownerHandle, getName(), getDesc());
/*    */       }
/* 88 */       return new MappingMethod(getOwner(), getName(), getDesc());
/*    */     } 
/* 90 */     return new MappingMethod(null, getName(), getDesc());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     String owner = (getOwner() != null) ? ("L" + getOwner() + ";") : "";
/* 96 */     String name = Strings.nullToEmpty(getName());
/* 97 */     String desc = Strings.nullToEmpty(getDesc());
/* 98 */     return String.format("%s%s%s", new Object[] { owner, name, desc });
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mirror\MethodHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */