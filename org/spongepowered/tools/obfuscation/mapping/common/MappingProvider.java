/*    */ package org.spongepowered.tools.obfuscation.mapping.common;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import javax.annotation.processing.Filer;
/*    */ import javax.annotation.processing.Messager;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
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
/*    */ public abstract class MappingProvider
/*    */   implements IMappingProvider
/*    */ {
/*    */   protected final Messager messager;
/*    */   protected final Filer filer;
/*    */   protected final BiMap<String, String> packageMap;
/*    */   protected final BiMap<String, String> classMap;
/*    */   protected final BiMap<MappingField, MappingField> fieldMap;
/*    */   protected final BiMap<MappingMethod, MappingMethod> methodMap;
/*    */   
/*    */   public MappingProvider(Messager messager, Filer filer) {
/* 45 */     this.packageMap = HashBiMap.create();
/* 46 */     this.classMap = HashBiMap.create();
/* 47 */     this.fieldMap = HashBiMap.create();
/* 48 */     this.methodMap = HashBiMap.create();
/*    */ 
/*    */     
/* 51 */     this.messager = messager;
/* 52 */     this.filer = filer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 57 */     this.packageMap.clear();
/* 58 */     this.classMap.clear();
/* 59 */     this.fieldMap.clear();
/* 60 */     this.methodMap.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 65 */   public boolean isEmpty() { return (this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public MappingMethod getMethodMapping(MappingMethod method) { return (MappingMethod)this.methodMap.get(method); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 75 */   public MappingField getFieldMapping(MappingField field) { return (MappingField)this.fieldMap.get(field); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 80 */   public String getClassMapping(String className) { return (String)this.classMap.get(className); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 85 */   public String getPackageMapping(String packageName) { return (String)this.packageMap.get(packageName); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mapping\common\MappingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */