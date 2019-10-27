/*    */ package me.jellysquid.mods.phosphor.mod;
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.annotations.SerializedName;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
/*    */ import java.io.Writer;
/*    */ 
/*    */ public class PhosphorConfig {
/* 11 */   private static final Gson gson = createGson();
/*    */   
/*    */   private static PhosphorConfig INSTANCE;
/*    */   
/*    */   @SerializedName("enable_illegal_thread_access_warnings")
/*    */   public boolean enableIllegalThreadAccessWarnings = true;
/*    */   
/*    */   @SerializedName("enable_phosphor")
/*    */   public boolean enablePhosphor = true;
/*    */   
/*    */   @SerializedName("show_patreon_message")
/*    */   public boolean showPatreonMessage = true;
/*    */ 
/*    */   
/* 25 */   public static PhosphorConfig instance() { return INSTANCE; }
/*    */   
/*    */   public static PhosphorConfig loadConfig() {
/*    */     PhosphorConfig config;
/* 29 */     if (INSTANCE != null) {
/* 30 */       return INSTANCE;
/*    */     }
/*    */     
/* 33 */     file = getConfigFile();
/*    */ 
/*    */ 
/*    */     
/* 37 */     if (!file.exists()) {
/* 38 */       config = new PhosphorConfig();
/* 39 */       config.saveConfig();
/*    */     } else {
/* 41 */       try (Reader reader = new FileReader(file)) {
/* 42 */         config = (PhosphorConfig)gson.fromJson(reader, PhosphorConfig.class);
/* 43 */       } catch (IOException e) {
/* 44 */         throw new RuntimeException("Failed to deserialize config from disk", e);
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     INSTANCE = config;
/*    */     
/* 50 */     return config;
/*    */   }
/*    */   
/*    */   public void saveConfig() {
/* 54 */     File dir = getConfigDirectory();
/*    */     
/* 56 */     if (!dir.exists()) {
/* 57 */       if (!dir.mkdirs()) {
/* 58 */         throw new RuntimeException("Could not create configuration directory at '" + dir.getAbsolutePath() + "'");
/*    */       }
/* 60 */     } else if (!dir.isDirectory()) {
/* 61 */       throw new RuntimeException("Configuration directory at '" + dir.getAbsolutePath() + "' is not a directory");
/*    */     } 
/*    */     
/* 64 */     try (Writer writer = new FileWriter(getConfigFile())) {
/* 65 */       gson.toJson(this, writer);
/* 66 */     } catch (IOException e) {
/* 67 */       throw new RuntimeException("Failed to serialize config to disk", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/* 72 */   private static File getConfigDirectory() { return new File("config"); }
/*    */ 
/*    */ 
/*    */   
/* 76 */   private static File getConfigFile() { return new File(getConfigDirectory(), "phosphor.json"); }
/*    */ 
/*    */ 
/*    */   
/* 80 */   private static Gson createGson() { return (new GsonBuilder()).setPrettyPrinting().create(); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\PhosphorConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */