/*    */ package me.jellysquid.mods.phosphor.core;
/*    */ 
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
/*    */ import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
/*    */ 
/*    */ 
/*    */ @MCVersion("1.12.2")
/*    */ public class PhosphorFMLLoadingPlugin
/*    */   implements IFMLLoadingPlugin
/*    */ {
/* 13 */   public String[] getASMTransformerClass() { return new String[0]; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 18 */   public String getModContainerClass() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/* 24 */   public String getSetupClass() { return PhosphorFMLSetupHook.class.getName(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   public String getAccessTransformerClass() { return null; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\core\PhosphorFMLLoadingPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */