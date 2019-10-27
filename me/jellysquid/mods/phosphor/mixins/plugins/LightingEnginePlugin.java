/*     */ package me.jellysquid.mods.phosphor.mixins.plugins;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import me.jellysquid.mods.phosphor.mod.PhosphorConfig;
/*     */ import net.minecraft.launchwrapper.Launch;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ 
/*     */ public class LightingEnginePlugin
/*     */   implements IMixinConfigPlugin {
/*  16 */   private static final Logger logger = LogManager.getLogger("Phosphor Plugin");
/*     */   
/*     */   public static boolean ENABLE_ILLEGAL_THREAD_ACCESS_WARNINGS = false;
/*     */   
/*     */   private PhosphorConfig config;
/*     */   
/*     */   private boolean spongePresent;
/*     */ 
/*     */   
/*     */   public void onLoad(String mixinPackage) {
/*  26 */     logger.debug("Loading configuration");
/*     */     
/*  28 */     this.config = PhosphorConfig.loadConfig();
/*     */     
/*  30 */     if (!this.config.enablePhosphor) {
/*  31 */       logger.warn("Phosphor has been disabled through mod configuration! No patches will be applied...");
/*     */     }
/*     */     
/*  34 */     ENABLE_ILLEGAL_THREAD_ACCESS_WARNINGS = this.config.enableIllegalThreadAccessWarnings;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  40 */       Class.forName("org.spongepowered.mod.SpongeCoremod");
/*     */       
/*  42 */       this.spongePresent = true;
/*  43 */     } catch (Exception e) {
/*  44 */       this.spongePresent = false;
/*     */     } 
/*     */     
/*  47 */     if (this.spongePresent) {
/*  48 */       logger.info("Sponge has been detected on the classpath! Enabling Sponge specific patches...");
/*  49 */       logger.warn("We cannot currently detect if you are using Sponge's async lighting patch. If you have not already done so, please disable it in your configuration file for SpongeForge or you will run into issues.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRefMapperConfig() {
/*  56 */     if (Launch.blackboard.get("fml.deobfuscatedEnvironment") == Boolean.TRUE) {
/*  57 */       return null;
/*     */     }
/*     */     
/*  60 */     return "mixins.phosphor.refmap.json";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
/*  65 */     if (!this.config.enablePhosphor) {
/*  66 */       return false;
/*     */     }
/*     */     
/*  69 */     if (this.spongePresent) {
/*     */       
/*  71 */       if (mixinClassName.endsWith("$Vanilla")) {
/*  72 */         logger.debug("Disabled mixin '{}' because we are in a SpongeForge environment", mixinClassName);
/*     */         
/*  74 */         return false;
/*     */       }
/*     */     
/*     */     }
/*  78 */     else if (mixinClassName.endsWith("$Sponge")) {
/*  79 */       logger.debug("Disabled patch '{}' because we are in a standard Vanilla/Forge environment", mixinClassName);
/*     */       
/*  81 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (targetClassName.startsWith("net.minecraft.client") && MixinEnvironment.getCurrentEnvironment().getSide() != MixinEnvironment.Side.CLIENT) {
/*  87 */       logger.debug("Disabled patch '{}' because it targets an client-side class unavailable in the current environment", mixinClassName);
/*     */       
/*  89 */       return false;
/*     */     } 
/*     */     
/*  92 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
/*     */ 
/*     */ 
/*     */   
/* 102 */   public List<String> getMixins() { return null; }
/*     */   
/*     */   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */   
/*     */   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mixins\plugins\LightingEnginePlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */