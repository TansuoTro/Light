/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*     */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*     */ import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
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
/*     */ class Mappings
/*     */   implements IMappingConsumer
/*     */ {
/*     */   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingField>> fieldMappings;
/*     */   private final Map<ObfuscationType, IMappingConsumer.MappingSet<MappingMethod>> methodMappings;
/*     */   private UniqueMappings unique;
/*     */   
/*     */   public static class MappingConflictException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*     */     private final IMapping<?> oldMapping;
/*     */     private final IMapping<?> newMapping;
/*     */     
/*     */     public MappingConflictException(IMapping<?> oldMapping, IMapping<?> newMapping) {
/*  51 */       this.oldMapping = oldMapping;
/*  52 */       this.newMapping = newMapping;
/*     */     }
/*     */ 
/*     */     
/*  56 */     public IMapping<?> getOld() { return this.oldMapping; }
/*     */ 
/*     */ 
/*     */     
/*  60 */     public IMapping<?> getNew() { return this.newMapping; }
/*     */   }
/*     */ 
/*     */   
/*     */   static class UniqueMappings
/*     */     implements IMappingConsumer
/*     */   {
/*     */     private final IMappingConsumer mappings;
/*     */     
/*     */     private final Map<ObfuscationType, Map<MappingField, MappingField>> fields;
/*     */     private final Map<ObfuscationType, Map<MappingMethod, MappingMethod>> methods;
/*     */     
/*     */     public UniqueMappings(IMappingConsumer mappings) {
/*  73 */       this.fields = new HashMap();
/*     */       
/*  75 */       this.methods = new HashMap();
/*     */ 
/*     */ 
/*     */       
/*  79 */       this.mappings = mappings;
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {
/*  84 */       clearMaps();
/*  85 */       this.mappings.clear();
/*     */     }
/*     */     
/*     */     protected void clearMaps() {
/*  89 */       this.fields.clear();
/*  90 */       this.methods.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
/*  95 */       if (!checkForExistingMapping(type, from, to, this.fields)) {
/*  96 */         this.mappings.addFieldMapping(type, from, to);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
/* 102 */       if (!checkForExistingMapping(type, from, to, this.methods)) {
/* 103 */         this.mappings.addMethodMapping(type, from, to);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private <TMapping extends IMapping<TMapping>> boolean checkForExistingMapping(ObfuscationType type, TMapping from, TMapping to, Map<ObfuscationType, Map<TMapping, TMapping>> mappings) throws Mappings.MappingConflictException {
/* 109 */       Map<TMapping, TMapping> existingMappings = (Map)mappings.get(type);
/* 110 */       if (existingMappings == null) {
/* 111 */         existingMappings = new HashMap<TMapping, TMapping>();
/* 112 */         mappings.put(type, existingMappings);
/*     */       } 
/* 114 */       TMapping existing = (TMapping)(IMapping)existingMappings.get(from);
/* 115 */       if (existing != null) {
/* 116 */         if (existing.equals(to)) {
/* 117 */           return true;
/*     */         }
/* 119 */         throw new Mappings.MappingConflictException(existing, to);
/*     */       } 
/* 121 */       existingMappings.put(from, to);
/* 122 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 127 */     public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) { return this.mappings.getFieldMappings(type); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) { return this.mappings.getMethodMappings(type); }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Mappings() {
/* 140 */     this.fieldMappings = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     this.methodMappings = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 156 */     for (ObfuscationType obfType : ObfuscationType.types()) {
/* 157 */       this.fieldMappings.put(obfType, new IMappingConsumer.MappingSet());
/* 158 */       this.methodMappings.put(obfType, new IMappingConsumer.MappingSet());
/*     */     } 
/*     */   }
/*     */   
/*     */   public IMappingConsumer asUnique() {
/* 163 */     if (this.unique == null) {
/* 164 */       this.unique = new UniqueMappings(this);
/*     */     }
/* 166 */     return this.unique;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMappingConsumer.MappingSet<MappingField> getFieldMappings(ObfuscationType type) {
/* 174 */     IMappingConsumer.MappingSet<MappingField> mappings = (IMappingConsumer.MappingSet)this.fieldMappings.get(type);
/* 175 */     return (mappings != null) ? mappings : new IMappingConsumer.MappingSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMappingConsumer.MappingSet<MappingMethod> getMethodMappings(ObfuscationType type) {
/* 183 */     IMappingConsumer.MappingSet<MappingMethod> mappings = (IMappingConsumer.MappingSet)this.methodMappings.get(type);
/* 184 */     return (mappings != null) ? mappings : new IMappingConsumer.MappingSet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 192 */     this.fieldMappings.clear();
/* 193 */     this.methodMappings.clear();
/* 194 */     if (this.unique != null) {
/* 195 */       this.unique.clearMaps();
/*     */     }
/* 197 */     init();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFieldMapping(ObfuscationType type, MappingField from, MappingField to) {
/* 202 */     IMappingConsumer.MappingSet<MappingField> mappings = (IMappingConsumer.MappingSet)this.fieldMappings.get(type);
/* 203 */     if (mappings == null) {
/* 204 */       mappings = new IMappingConsumer.MappingSet<MappingField>();
/* 205 */       this.fieldMappings.put(type, mappings);
/*     */     } 
/* 207 */     mappings.add(new IMappingConsumer.MappingSet.Pair(from, to));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addMethodMapping(ObfuscationType type, MappingMethod from, MappingMethod to) {
/* 212 */     IMappingConsumer.MappingSet<MappingMethod> mappings = (IMappingConsumer.MappingSet)this.methodMappings.get(type);
/* 213 */     if (mappings == null) {
/* 214 */       mappings = new IMappingConsumer.MappingSet<MappingMethod>();
/* 215 */       this.methodMappings.put(type, mappings);
/*     */     } 
/* 217 */     mappings.add(new IMappingConsumer.MappingSet.Pair(from, to));
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\Mappings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */