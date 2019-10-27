/*    */ package org.spongepowered.asm.obfuscation.mapping;
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
/*    */ public interface IMapping<TMapping>
/*    */ {
/*    */   Type getType();
/*    */   
/*    */   TMapping move(String paramString);
/*    */   
/*    */   TMapping remap(String paramString);
/*    */   
/*    */   TMapping transform(String paramString);
/*    */   
/*    */   TMapping copy();
/*    */   
/*    */   String getName();
/*    */   
/*    */   String getSimpleName();
/*    */   
/*    */   String getOwner();
/*    */   
/*    */   String getDesc();
/*    */   
/*    */   TMapping getSuper();
/*    */   
/*    */   String serialise();
/*    */   
/*    */   public enum Type
/*    */   {
/* 38 */     FIELD,
/* 39 */     METHOD,
/* 40 */     CLASS,
/* 41 */     PACKAGE;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\obfuscation\mapping\IMapping.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */