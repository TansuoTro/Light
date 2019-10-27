/*     */ package org.spongepowered.tools.obfuscation.mirror.mapping;
/*     */ 
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ResolvableMappingMethod
/*     */   extends MappingMethod
/*     */ {
/*     */   private final TypeHandle ownerHandle;
/*     */   
/*     */   public ResolvableMappingMethod(TypeHandle owner, String name, String desc) {
/*  41 */     super(owner.getName(), name, desc);
/*  42 */     this.ownerHandle = owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MappingMethod getSuper() {
/*  51 */     if (this.ownerHandle == null) {
/*  52 */       return super.getSuper();
/*     */     }
/*     */     
/*  55 */     String name = getSimpleName();
/*  56 */     String desc = getDesc();
/*  57 */     String signature = TypeUtils.getJavaSignature(desc);
/*     */     
/*  59 */     TypeHandle superClass = this.ownerHandle.getSuperclass();
/*  60 */     if (superClass != null)
/*     */     {
/*  62 */       if (superClass.findMethod(name, signature) != null) {
/*  63 */         return superClass.getMappingMethod(name, desc);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*  68 */     for (TypeHandle iface : this.ownerHandle.getInterfaces()) {
/*  69 */       if (iface.findMethod(name, signature) != null) {
/*  70 */         return iface.getMappingMethod(name, desc);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if (superClass != null) {
/*  76 */       return superClass.getMappingMethod(name, desc).getSuper();
/*     */     }
/*     */     
/*  79 */     return super.getSuper();
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
/*  90 */   public MappingMethod move(TypeHandle newOwner) { return new ResolvableMappingMethod(newOwner, getSimpleName(), getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public MappingMethod remap(String newName) { return new ResolvableMappingMethod(this.ownerHandle, newName, getDesc()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public MappingMethod transform(String newDesc) { return new ResolvableMappingMethod(this.ownerHandle, getSimpleName(), newDesc); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public MappingMethod copy() { return new ResolvableMappingMethod(this.ownerHandle, getSimpleName(), getDesc()); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mirror\mapping\ResolvableMappingMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */