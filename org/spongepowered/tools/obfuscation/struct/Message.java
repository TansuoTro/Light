/*     */ package org.spongepowered.tools.obfuscation.struct;
/*     */ 
/*     */ import javax.annotation.processing.Messager;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.tools.Diagnostic;
/*     */ import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
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
/*     */ public class Message
/*     */ {
/*     */   private Diagnostic.Kind kind;
/*     */   private CharSequence msg;
/*     */   private final Element element;
/*     */   private final AnnotationMirror annotation;
/*     */   private final AnnotationValue value;
/*     */   
/*  48 */   public Message(Diagnostic.Kind kind, CharSequence msg) { this(kind, msg, null, (AnnotationMirror)null, null); }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element) { this(kind, msg, element, (AnnotationMirror)null, null); }
/*     */ 
/*     */ 
/*     */   
/*  56 */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation) { this(kind, msg, element, annotation.asMirror(), null); }
/*     */ 
/*     */ 
/*     */   
/*  60 */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation) { this(kind, msg, element, annotation, null); }
/*     */ 
/*     */ 
/*     */   
/*  64 */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationHandle annotation, AnnotationValue value) { this(kind, msg, element, annotation.asMirror(), value); }
/*     */ 
/*     */   
/*     */   public Message(Diagnostic.Kind kind, CharSequence msg, Element element, AnnotationMirror annotation, AnnotationValue value) {
/*  68 */     this.kind = kind;
/*  69 */     this.msg = msg;
/*  70 */     this.element = element;
/*  71 */     this.annotation = annotation;
/*  72 */     this.value = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message sendTo(Messager messager) {
/*  82 */     if (this.value != null) {
/*  83 */       messager.printMessage(this.kind, this.msg, this.element, this.annotation, this.value);
/*  84 */     } else if (this.annotation != null) {
/*  85 */       messager.printMessage(this.kind, this.msg, this.element, this.annotation);
/*  86 */     } else if (this.element != null) {
/*  87 */       messager.printMessage(this.kind, this.msg, this.element);
/*     */     } else {
/*  89 */       messager.printMessage(this.kind, this.msg);
/*     */     } 
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  98 */   public Diagnostic.Kind getKind() { return this.kind; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message setKind(Diagnostic.Kind kind) {
/* 108 */     this.kind = kind;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 118 */   public CharSequence getMsg() { return this.msg; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message setMsg(CharSequence msg) {
/* 128 */     this.msg = msg;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   public Element getElement() { return this.element; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 143 */   public AnnotationMirror getAnnotation() { return this.annotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   public AnnotationValue getValue() { return this.value; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\struct\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */