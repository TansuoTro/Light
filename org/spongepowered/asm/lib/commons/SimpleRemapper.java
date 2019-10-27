/*    */ package org.spongepowered.asm.lib.commons;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.Map;
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
/*    */ public class SimpleRemapper
/*    */   extends Remapper
/*    */ {
/*    */   private final Map<String, String> mapping;
/*    */   
/* 46 */   public SimpleRemapper(Map<String, String> mapping) { this.mapping = mapping; }
/*    */ 
/*    */ 
/*    */   
/* 50 */   public SimpleRemapper(String oldName, String newName) { this.mapping = Collections.singletonMap(oldName, newName); }
/*    */ 
/*    */ 
/*    */   
/*    */   public String mapMethodName(String owner, String name, String desc) {
/* 55 */     String s = map(owner + '.' + name + desc);
/* 56 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapInvokeDynamicMethodName(String name, String desc) {
/* 61 */     String s = map('.' + name + desc);
/* 62 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */   
/*    */   public String mapFieldName(String owner, String name, String desc) {
/* 67 */     String s = map(owner + '.' + name);
/* 68 */     return (s == null) ? name : s;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 73 */   public String map(String key) { return (String)this.mapping.get(key); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\lib\commons\SimpleRemapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.0.7
 */