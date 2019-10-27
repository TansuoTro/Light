/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
/*     */ import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObfuscationDataProvider
/*     */   implements IObfuscationDataProvider
/*     */ {
/*     */   private final IMixinAnnotationProcessor ap;
/*     */   private final List<ObfuscationEnvironment> environments;
/*     */   
/*     */   public ObfuscationDataProvider(IMixinAnnotationProcessor ap, List<ObfuscationEnvironment> environments) {
/*  55 */     this.ap = ap;
/*  56 */     this.environments = environments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntryRecursive(MemberInfo targetMember) {
/*  66 */     MemberInfo currentTarget = targetMember;
/*  67 */     ObfuscationData<String> obfTargetNames = getObfClass(currentTarget.owner);
/*  68 */     ObfuscationData<T> obfData = getObfEntry(currentTarget);
/*     */     try {
/*  70 */       while (obfData.isEmpty()) {
/*  71 */         TypeHandle targetType = this.ap.getTypeProvider().getTypeHandle(currentTarget.owner);
/*  72 */         if (targetType == null) {
/*  73 */           return obfData;
/*     */         }
/*     */         
/*  76 */         TypeHandle superClass = targetType.getSuperclass();
/*  77 */         obfData = getObfEntryUsing(currentTarget, superClass);
/*  78 */         if (!obfData.isEmpty()) {
/*  79 */           return applyParents(obfTargetNames, obfData);
/*     */         }
/*     */         
/*  82 */         for (TypeHandle iface : targetType.getInterfaces()) {
/*  83 */           obfData = getObfEntryUsing(currentTarget, iface);
/*  84 */           if (!obfData.isEmpty()) {
/*  85 */             return applyParents(obfTargetNames, obfData);
/*     */           }
/*     */         } 
/*     */         
/*  89 */         if (superClass == null) {
/*     */           break;
/*     */         }
/*     */         
/*  93 */         currentTarget = currentTarget.move(superClass.getName());
/*     */       } 
/*  95 */     } catch (Exception ex) {
/*  96 */       ex.printStackTrace();
/*  97 */       return getObfEntry(targetMember);
/*     */     } 
/*  99 */     return obfData;
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
/* 113 */   private <T> ObfuscationData<T> getObfEntryUsing(MemberInfo targetMember, TypeHandle targetClass) { return (targetClass == null) ? new ObfuscationData() : getObfEntry(targetMember.move(targetClass.getName())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(MemberInfo targetMember) {
/* 124 */     if (targetMember.isField()) {
/* 125 */       return getObfField(targetMember);
/*     */     }
/* 127 */     return getObfMethod(targetMember.asMethodMapping());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ObfuscationData<T> getObfEntry(IMapping<T> mapping) {
/* 133 */     if (mapping != null) {
/* 134 */       if (mapping.getType() == IMapping.Type.FIELD)
/* 135 */         return getObfField((MappingField)mapping); 
/* 136 */       if (mapping.getType() == IMapping.Type.METHOD) {
/* 137 */         return getObfMethod((MappingMethod)mapping);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     return new ObfuscationData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 151 */   public ObfuscationData<MappingMethod> getObfMethodRecursive(MemberInfo targetMember) { return getObfEntryRecursive(targetMember); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 161 */   public ObfuscationData<MappingMethod> getObfMethod(MemberInfo method) { return getRemappedMethod(method, method.isConstructor()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 166 */   public ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method) { return getRemappedMethod(method, true); }
/*     */ 
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(MemberInfo method, boolean remapDescriptor) {
/* 170 */     ObfuscationData<MappingMethod> data = new ObfuscationData<MappingMethod>();
/*     */     
/* 172 */     for (ObfuscationEnvironment env : this.environments) {
/* 173 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 174 */       if (obfMethod != null) {
/* 175 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 179 */     if (!data.isEmpty() || !remapDescriptor) {
/* 180 */       return data;
/*     */     }
/*     */     
/* 183 */     return remapDescriptor(data, method);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public ObfuscationData<MappingMethod> getObfMethod(MappingMethod method) { return getRemappedMethod(method, method.isConstructor()); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 198 */   public ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method) { return getRemappedMethod(method, true); }
/*     */ 
/*     */   
/*     */   private ObfuscationData<MappingMethod> getRemappedMethod(MappingMethod method, boolean remapDescriptor) {
/* 202 */     ObfuscationData<MappingMethod> data = new ObfuscationData<MappingMethod>();
/*     */     
/* 204 */     for (ObfuscationEnvironment env : this.environments) {
/* 205 */       MappingMethod obfMethod = env.getObfMethod(method);
/* 206 */       if (obfMethod != null) {
/* 207 */         data.put(env.getType(), obfMethod);
/*     */       }
/*     */     } 
/*     */     
/* 211 */     if (!data.isEmpty() || !remapDescriptor) {
/* 212 */       return data;
/*     */     }
/*     */     
/* 215 */     return remapDescriptor(data, new MemberInfo(method));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingMethod> remapDescriptor(ObfuscationData<MappingMethod> data, MemberInfo method) {
/* 226 */     for (ObfuscationEnvironment env : this.environments) {
/* 227 */       MemberInfo obfMethod = env.remapDescriptor(method);
/* 228 */       if (obfMethod != null) {
/* 229 */         data.put(env.getType(), obfMethod.asMethodMapping());
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 243 */   public ObfuscationData<MappingField> getObfFieldRecursive(MemberInfo targetMember) { return getObfEntryRecursive(targetMember); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public ObfuscationData<MappingField> getObfField(MemberInfo field) { return getObfField(field.asFieldMapping()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<MappingField> getObfField(MappingField field) {
/* 261 */     ObfuscationData<MappingField> data = new ObfuscationData<MappingField>();
/*     */     
/* 263 */     for (ObfuscationEnvironment env : this.environments) {
/* 264 */       MappingField obfField = env.getObfField(field);
/* 265 */       if (obfField != null) {
/* 266 */         if (obfField.getDesc() == null && field.getDesc() != null) {
/* 267 */           obfField = obfField.transform(env.remapDescriptor(field.getDesc()));
/*     */         }
/* 269 */         data.put(env.getType(), obfField);
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 282 */   public ObfuscationData<String> getObfClass(TypeHandle type) { return getObfClass(type.getName()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObfuscationData<String> getObfClass(String className) {
/* 291 */     ObfuscationData<String> data = new ObfuscationData<String>(className);
/*     */     
/* 293 */     for (ObfuscationEnvironment env : this.environments) {
/* 294 */       String obfClass = env.getObfClass(className);
/* 295 */       if (obfClass != null) {
/* 296 */         data.put(env.getType(), obfClass);
/*     */       }
/*     */     } 
/*     */     
/* 300 */     return data;
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
/*     */   private static <T> ObfuscationData<T> applyParents(ObfuscationData<String> parents, ObfuscationData<T> members) {
/* 312 */     for (ObfuscationType type : members) {
/* 313 */       String obfClass = (String)parents.get(type);
/* 314 */       T obfMember = (T)members.get(type);
/* 315 */       members.put(type, MemberInfo.fromMapping((IMapping)obfMember).move(obfClass).asMapping());
/*     */     } 
/* 317 */     return members;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\ObfuscationDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */