/*    */ package org.spongepowered.tools.obfuscation.mapping;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import java.util.LinkedHashSet;
/*    */ import org.spongepowered.asm.obfuscation.mapping.IMapping;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
/*    */ import org.spongepowered.tools.obfuscation.ObfuscationType;
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
/*    */ public interface IMappingConsumer
/*    */ {
/*    */   void clear();
/*    */   
/*    */   void addFieldMapping(ObfuscationType paramObfuscationType, MappingField paramMappingField1, MappingField paramMappingField2);
/*    */   
/*    */   void addMethodMapping(ObfuscationType paramObfuscationType, MappingMethod paramMappingMethod1, MappingMethod paramMappingMethod2);
/*    */   
/*    */   MappingSet<MappingField> getFieldMappings(ObfuscationType paramObfuscationType);
/*    */   
/*    */   MappingSet<MappingMethod> getMethodMappings(ObfuscationType paramObfuscationType);
/*    */   
/*    */   public static class MappingSet<TMapping extends IMapping<TMapping>>
/*    */     extends LinkedHashSet<MappingSet.Pair<TMapping>>
/*    */   {
/*    */     private static final long serialVersionUID = 1L;
/*    */     
/*    */     public static class Pair<TMapping extends IMapping<TMapping>>
/*    */       extends Object
/*    */     {
/*    */       public final TMapping from;
/*    */       public final TMapping to;
/*    */       
/*    */       public Pair(TMapping from, TMapping to) {
/* 67 */         this.from = from;
/* 68 */         this.to = to;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(Object obj) {
/* 73 */         if (!(obj instanceof Pair)) {
/* 74 */           return false;
/*    */         }
/*    */ 
/*    */         
/* 78 */         Pair<TMapping> other = (Pair)obj;
/* 79 */         return (Objects.equal(this.from, other.from) && Objects.equal(this.to, other.to));
/*    */       }
/*    */ 
/*    */ 
/*    */       
/* 84 */       public int hashCode() { return Objects.hashCode(new Object[] { this.from, this.to }); }
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 89 */       public String toString() { return String.format("%s -> %s", new Object[] { this.from, this.to }); }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mapping\IMappingConsumer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */