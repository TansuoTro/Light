/*    */ package org.spongepowered.asm.obfuscation.mapping.mcp;
/*    */ 
/*    */ import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
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
/*    */ public class MappingFieldSrg
/*    */   extends MappingField
/*    */ {
/*    */   private final String srg;
/*    */   
/*    */   public MappingFieldSrg(String srg) {
/* 37 */     super(getOwnerFromSrg(srg), getNameFromSrg(srg), null);
/* 38 */     this.srg = srg;
/*    */   }
/*    */   
/*    */   public MappingFieldSrg(MappingField field) {
/* 42 */     super(field.getOwner(), field.getName(), null);
/* 43 */     this.srg = field.getOwner() + "/" + field.getName();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 48 */   public String serialise() { return this.srg; }
/*    */ 
/*    */   
/*    */   private static String getNameFromSrg(String srg) {
/* 52 */     if (srg == null) {
/* 53 */       return null;
/*    */     }
/* 55 */     int pos = srg.lastIndexOf('/');
/* 56 */     return (pos > -1) ? srg.substring(pos + 1) : srg;
/*    */   }
/*    */   
/*    */   private static String getOwnerFromSrg(String srg) {
/* 60 */     if (srg == null) {
/* 61 */       return null;
/*    */     }
/* 63 */     int pos = srg.lastIndexOf('/');
/* 64 */     return (pos > -1) ? srg.substring(0, pos) : null;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\obfuscation\mapping\mcp\MappingFieldSrg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */