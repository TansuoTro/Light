/*    */ package org.spongepowered.asm.mixin;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.List;
/*    */ import net.minecraft.launchwrapper.ITweaker;
/*    */ import net.minecraft.launchwrapper.LaunchClassLoader;
/*    */ import org.spongepowered.asm.launch.MixinBootstrap;
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
/*    */ public class EnvironmentStateTweaker
/*    */   implements ITweaker
/*    */ {
/*    */   public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {}
/*    */   
/* 48 */   public void injectIntoClassLoader(LaunchClassLoader classLoader) { MixinBootstrap.getPlatform().inject(); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String getLaunchTarget() { return ""; }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getLaunchArguments() {
/* 58 */     MixinEnvironment.gotoPhase(MixinEnvironment.Phase.DEFAULT);
/* 59 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\EnvironmentStateTweaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */