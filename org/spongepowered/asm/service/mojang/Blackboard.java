/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import net.minecraft.launchwrapper.Launch;
/*    */ import org.spongepowered.asm.service.IGlobalPropertyService;
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
/*    */ 
/*    */ 
/*    */ public class Blackboard
/*    */   implements IGlobalPropertyService
/*    */ {
/* 46 */   public final <T> T getProperty(String key) { return (T)Launch.blackboard.get(key); }
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
/* 57 */   public final void setProperty(String key, Object value) { Launch.blackboard.put(key, value); }
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
/*    */   public final <T> T getProperty(String key, T defaultValue) {
/* 72 */     Object value = Launch.blackboard.get(key);
/* 73 */     return (T)((value != null) ? value : defaultValue);
/*    */   }
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
/*    */   public final String getPropertyString(String key, String defaultValue) {
/* 87 */     Object value = Launch.blackboard.get(key);
/* 88 */     return (value != null) ? value.toString() : defaultValue;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\service\mojang\Blackboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */