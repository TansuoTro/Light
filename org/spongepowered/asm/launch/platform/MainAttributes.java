/*     */ package org.spongepowered.asm.launch.platform;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class MainAttributes
/*     */ {
/*  42 */   private static final Map<URI, MainAttributes> instances = new HashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Attributes attributes;
/*     */ 
/*     */ 
/*     */   
/*  50 */   private MainAttributes() { this.attributes = new Attributes(); }
/*     */ 
/*     */ 
/*     */   
/*  54 */   private MainAttributes(File jar) { this.attributes = getAttributes(jar); }
/*     */ 
/*     */   
/*     */   public final String get(String name) {
/*  58 */     if (this.attributes != null) {
/*  59 */       return this.attributes.getValue(name);
/*     */     }
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   private static Attributes getAttributes(File jar) {
/*  65 */     if (jar == null) {
/*  66 */       return null;
/*     */     }
/*     */     
/*  69 */     jarFile = null;
/*     */     
/*  71 */     try { jarFile = new JarFile(jar);
/*  72 */       manifest = jarFile.getManifest();
/*  73 */       if (manifest != null) {
/*  74 */         return manifest.getMainAttributes();
/*     */       } }
/*  76 */     catch (IOException iOException)
/*     */     
/*     */     { 
/*     */       try {
/*  80 */         if (jarFile != null) {
/*  81 */           jarFile.close();
/*     */         }
/*  83 */       } catch (IOException iOException) {} } finally { try { if (jarFile != null) jarFile.close();  } catch (IOException iOException) {} }
/*     */ 
/*     */ 
/*     */     
/*  87 */     return new Attributes();
/*     */   }
/*     */ 
/*     */   
/*  91 */   public static MainAttributes of(File jar) { return of(jar.toURI()); }
/*     */ 
/*     */   
/*     */   public static MainAttributes of(URI uri) {
/*  95 */     MainAttributes attributes = (MainAttributes)instances.get(uri);
/*  96 */     if (attributes == null) {
/*  97 */       attributes = new MainAttributes(new File(uri));
/*  98 */       instances.put(uri, attributes);
/*     */     } 
/* 100 */     return attributes;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\launch\platform\MainAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */