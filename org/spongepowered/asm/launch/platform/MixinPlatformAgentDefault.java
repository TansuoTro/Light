/*    */ package org.spongepowered.asm.launch.platform;
/*    */ 
/*    */ import java.net.URI;
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
/*    */ public class MixinPlatformAgentDefault
/*    */   extends MixinPlatformAgentAbstract
/*    */ {
/* 42 */   public MixinPlatformAgentDefault(MixinPlatformManager manager, URI uri) { super(manager, uri); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void prepare() {
/* 48 */     String compatibilityLevel = this.attributes.get("MixinCompatibilityLevel");
/* 49 */     if (compatibilityLevel != null) {
/* 50 */       this.manager.setCompatibilityLevel(compatibilityLevel);
/*    */     }
/*    */     
/* 53 */     String mixinConfigs = this.attributes.get("MixinConfigs");
/* 54 */     if (mixinConfigs != null) {
/* 55 */       for (String config : mixinConfigs.split(",")) {
/* 56 */         this.manager.addConfig(config.trim());
/*    */       }
/*    */     }
/*    */     
/* 60 */     String tokenProviders = this.attributes.get("MixinTokenProviders");
/* 61 */     if (tokenProviders != null) {
/* 62 */       for (String provider : tokenProviders.split(",")) {
/* 63 */         this.manager.addTokenProvider(provider.trim());
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initPrimaryContainer() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void inject() {}
/*    */ 
/*    */ 
/*    */   
/* 78 */   public String getLaunchTarget() { return this.attributes.get("Main-Class"); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\launch\platform\MixinPlatformAgentDefault.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */