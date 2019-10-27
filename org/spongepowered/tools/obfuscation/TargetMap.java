/*     */ package org.spongepowered.tools.obfuscation;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
/*     */ import org.spongepowered.tools.obfuscation.mirror.TypeReference;
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
/*     */ public final class TargetMap
/*     */   extends HashMap<TypeReference, Set<TypeReference>>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   private final String sessionId;
/*     */   
/*  65 */   private TargetMap() { this(String.valueOf(System.currentTimeMillis())); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private TargetMap(String sessionId) { this.sessionId = sessionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   public String getSessionId() { return this.sessionId; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public void registerTargets(AnnotatedMixin mixin) { registerTargets(mixin.getTargets(), mixin.getHandle()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTargets(List<TypeHandle> targets, TypeHandle mixin) {
/* 100 */     for (TypeHandle target : targets) {
/* 101 */       addMixin(target, mixin);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   public void addMixin(TypeHandle target, TypeHandle mixin) { addMixin(target.getReference(), mixin.getReference()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public void addMixin(String target, String mixin) { addMixin(new TypeReference(target), new TypeReference(mixin)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addMixin(TypeReference target, TypeReference mixin) {
/* 132 */     Set<TypeReference> mixins = getMixinsFor(target);
/* 133 */     mixins.add(mixin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public Collection<TypeReference> getMixinsTargeting(TypeElement target) { return getMixinsTargeting(new TypeHandle(target)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 153 */   public Collection<TypeReference> getMixinsTargeting(TypeHandle target) { return getMixinsTargeting(target.getReference()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 163 */   public Collection<TypeReference> getMixinsTargeting(TypeReference target) { return Collections.unmodifiableCollection(getMixinsFor(target)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Set<TypeReference> getMixinsFor(TypeReference target) {
/* 173 */     Set<TypeReference> mixins = (Set)get(target);
/* 174 */     if (mixins == null) {
/* 175 */       mixins = new HashSet<TypeReference>();
/* 176 */       put(target, mixins);
/*     */     } 
/* 178 */     return mixins;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readImports(File file) throws IOException {
/* 188 */     if (!file.isFile()) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     for (String line : Files.readLines(file, Charset.defaultCharset())) {
/* 193 */       String[] parts = line.split("\t");
/* 194 */       if (parts.length == 2) {
/* 195 */         addMixin(parts[1], parts[0]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(boolean temp) {
/* 206 */     oos = null;
/* 207 */     FileOutputStream fout = null;
/*     */     try {
/* 209 */       sessionFile = getSessionFile(this.sessionId);
/* 210 */       if (temp) {
/* 211 */         sessionFile.deleteOnExit();
/*     */       }
/* 213 */       fout = new FileOutputStream(sessionFile, true);
/* 214 */       oos = new ObjectOutputStream(fout);
/* 215 */       oos.writeObject(this);
/* 216 */     } catch (Exception ex) {
/* 217 */       ex.printStackTrace();
/*     */     } finally {
/* 219 */       if (oos != null) {
/*     */         try {
/* 221 */           oos.close();
/* 222 */         } catch (IOException ex) {
/* 223 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TargetMap read(File sessionFile) {
/* 236 */     objectinputstream = null;
/* 237 */     FileInputStream streamIn = null;
/*     */     try {
/* 239 */       streamIn = new FileInputStream(sessionFile);
/* 240 */       objectinputstream = new ObjectInputStream(streamIn);
/* 241 */       return (TargetMap)objectinputstream.readObject();
/* 242 */     } catch (Exception e) {
/* 243 */       e.printStackTrace();
/*     */     } finally {
/* 245 */       if (objectinputstream != null) {
/*     */         try {
/* 247 */           objectinputstream.close();
/* 248 */         } catch (IOException ex) {
/* 249 */           ex.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/* 253 */     return null;
/*     */   }
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
/*     */   public static TargetMap create(String sessionId) {
/* 266 */     if (sessionId != null) {
/* 267 */       File sessionFile = getSessionFile(sessionId);
/* 268 */       if (sessionFile.exists()) {
/* 269 */         TargetMap map = read(sessionFile);
/* 270 */         if (map != null) {
/* 271 */           return map;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 276 */     return new TargetMap();
/*     */   }
/*     */   
/*     */   private static File getSessionFile(String sessionId) {
/* 280 */     File tempDir = new File(System.getProperty("java.io.tmpdir"));
/* 281 */     return new File(tempDir, String.format("mixin-targetdb-%s.tmp", new Object[] { sessionId }));
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\TargetMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */