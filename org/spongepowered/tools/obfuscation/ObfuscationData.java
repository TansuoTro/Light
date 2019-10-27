/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObfuscationData<T>
/*     */   extends Object
/*     */   implements Iterable<ObfuscationType>
/*     */ {
/*     */   private final Map<ObfuscationType, T> data;
/*     */   private final T defaultValue;
/*     */   
/*  62 */   public ObfuscationData() { this(null); }
/*     */   
/*     */   public ObfuscationData(T defaultValue) {
/*     */     this.data = new HashMap();
/*  66 */     this.defaultValue = defaultValue;
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
/*     */   @Deprecated
/*  81 */   public void add(ObfuscationType type, T value) { put(type, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   public void put(ObfuscationType type, T value) { this.data.put(type, value); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public boolean isEmpty() { return this.data.isEmpty(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T get(ObfuscationType type) {
/* 109 */     T value = (T)this.data.get(type);
/* 110 */     return (value != null) ? value : this.defaultValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public Iterator<ObfuscationType> iterator() { return this.data.keySet().iterator(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public String toString() { return String.format("ObfuscationData[%s,DEFAULT=%s]", new Object[] { listValues(), this.defaultValue }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 135 */   public String values() { return "[" + listValues() + "]"; }
/*     */ 
/*     */   
/*     */   private String listValues() {
/* 139 */     StringBuilder sb = new StringBuilder();
/* 140 */     boolean delim = false;
/* 141 */     for (ObfuscationType type : this.data.keySet()) {
/* 142 */       if (delim) {
/* 143 */         sb.append(',');
/*     */       }
/* 145 */       sb.append(type.getKey()).append('=').append(this.data.get(type));
/* 146 */       delim = true;
/*     */     } 
/* 148 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\ObfuscationData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */