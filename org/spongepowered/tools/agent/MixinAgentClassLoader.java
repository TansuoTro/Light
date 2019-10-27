/*     */ package org.spongepowered.tools.agent;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spongepowered.asm.lib.ClassWriter;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
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
/*     */ class MixinAgentClassLoader
/*     */   extends ClassLoader
/*     */ {
/*  45 */   private static final Logger logger = LogManager.getLogger("mixin.agent");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private Map<Class<?>, byte[]> mixins = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private Map<String, byte[]> targets = new HashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addMixinClass(String name) {
/*  64 */     logger.debug("Mixin class {} added to class loader", new Object[] { name });
/*     */     try {
/*  66 */       byte[] bytes = materialise(name);
/*  67 */       Class<?> clazz = defineClass(name, bytes, 0, bytes.length);
/*     */ 
/*     */       
/*  70 */       clazz.newInstance();
/*  71 */       this.mixins.put(clazz, bytes);
/*  72 */     } catch (Throwable e) {
/*  73 */       logger.catching(e);
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
/*  84 */   void addTargetClass(String name, byte[] bytecode) { this.targets.put(name, bytecode); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   byte[] getFakeMixinBytecode(Class<?> clazz) { return (byte[])this.mixins.get(clazz); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 104 */   byte[] getOriginalTargetBytecode(String name) { return (byte[])this.targets.get(name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private byte[] materialise(String name) {
/* 114 */     ClassWriter cw = new ClassWriter(3);
/* 115 */     cw.visit(MixinEnvironment.getCompatibilityLevel().classVersion(), 1, name.replace('.', '/'), null, 
/* 116 */         Type.getInternalName(Object.class), null);
/*     */ 
/*     */     
/* 119 */     MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", null, null);
/* 120 */     mv.visitCode();
/* 121 */     mv.visitVarInsn(25, 0);
/* 122 */     mv.visitMethodInsn(183, Type.getInternalName(Object.class), "<init>", "()V", false);
/* 123 */     mv.visitInsn(177);
/* 124 */     mv.visitMaxs(1, 1);
/* 125 */     mv.visitEnd();
/*     */     
/* 127 */     cw.visitEnd();
/* 128 */     return cw.toByteArray();
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\agent\MixinAgentClassLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */