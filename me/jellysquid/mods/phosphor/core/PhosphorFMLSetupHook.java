/*    */ package me.jellysquid.mods.phosphor.core;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraftforge.fml.relauncher.IFMLCallHook;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
/*    */ import org.spongepowered.asm.mixin.Mixins;
/*    */ 
/*    */ public class PhosphorFMLSetupHook
/*    */   implements IFMLCallHook {
/* 12 */   private static final Logger logger = LogManager.getLogger("Phosphor Forge Core");
/*    */ 
/*    */ 
/*    */   
/*    */   public void injectData(Map<String, Object> data) {}
/*    */ 
/*    */   
/*    */   public Void call() {
/* 20 */     logger.debug("Success! Phosphor has been called into from Forge... initializing Mixin environment and configurations");
/*    */     
/* 22 */     MixinBootstrap.init();
/*    */     
/* 24 */     Mixins.addConfiguration("mixins.phosphor.json");
/*    */     
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\core\PhosphorFMLSetupHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */