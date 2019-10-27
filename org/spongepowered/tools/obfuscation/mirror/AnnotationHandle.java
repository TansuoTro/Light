/*     */ package org.spongepowered.tools.obfuscation.mirror;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.lang.model.element.AnnotationMirror;
/*     */ import javax.lang.model.element.AnnotationValue;
/*     */ import javax.lang.model.element.Element;
/*     */ import javax.lang.model.element.ExecutableElement;
/*     */ import javax.lang.model.element.TypeElement;
/*     */ import javax.lang.model.element.VariableElement;
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
/*     */ public final class AnnotationHandle
/*     */ {
/*  47 */   public static final AnnotationHandle MISSING = new AnnotationHandle(null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final AnnotationMirror annotation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private AnnotationHandle(AnnotationMirror annotation) { this.annotation = annotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public AnnotationMirror asMirror() { return this.annotation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   public boolean exists() { return (this.annotation != null); }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  84 */     if (this.annotation == null) {
/*  85 */       return "@{UnknownAnnotation}";
/*     */     }
/*  87 */     return "@" + this.annotation.getAnnotationType().asElement().getSimpleName();
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
/*     */   
/*     */   public <T> T getValue(String key, T defaultValue) {
/* 101 */     if (this.annotation == null) {
/* 102 */       return defaultValue;
/*     */     }
/*     */     
/* 105 */     AnnotationValue value = getAnnotationValue(key);
/* 106 */     if (defaultValue instanceof Enum && value != null) {
/* 107 */       VariableElement varValue = (VariableElement)value.getValue();
/* 108 */       if (varValue == null) {
/* 109 */         return defaultValue;
/*     */       }
/* 111 */       return (T)Enum.valueOf(defaultValue.getClass(), varValue.getSimpleName().toString());
/*     */     } 
/*     */     
/* 114 */     return (T)((value != null) ? value.getValue() : defaultValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 124 */   public <T> T getValue() { return (T)getValue("value", null); }
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
/* 136 */   public <T> T getValue(String key) { return (T)getValue(key, null); }
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
/* 148 */   public boolean getBoolean(String key, boolean defaultValue) { return ((Boolean)getValue(key, Boolean.valueOf(defaultValue))).booleanValue(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationHandle getAnnotation(String key) {
/* 158 */     Object value = getValue(key);
/* 159 */     if (value instanceof AnnotationMirror)
/* 160 */       return of((AnnotationMirror)value); 
/* 161 */     if (value instanceof AnnotationValue) {
/* 162 */       Object mirror = ((AnnotationValue)value).getValue();
/* 163 */       if (mirror instanceof AnnotationMirror) {
/* 164 */         return of((AnnotationMirror)mirror);
/*     */       }
/*     */     } 
/* 167 */     return null;
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
/* 178 */   public <T> List<T> getList() { return getList("value"); }
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
/*     */   public <T> List<T> getList(String key) {
/* 191 */     List<AnnotationValue> list = (List)getValue(key, Collections.emptyList());
/* 192 */     return unwrapAnnotationValueList(list);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AnnotationHandle> getAnnotationList(String key) {
/* 202 */     Object val = getValue(key, null);
/* 203 */     if (val == null) {
/* 204 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     
/* 208 */     if (val instanceof AnnotationMirror) {
/* 209 */       return ImmutableList.of(of((AnnotationMirror)val));
/*     */     }
/*     */     
/* 212 */     List<AnnotationValue> list = (List)val;
/* 213 */     List<AnnotationHandle> annotations = new ArrayList<AnnotationHandle>(list.size());
/* 214 */     for (AnnotationValue value : list) {
/* 215 */       annotations.add(new AnnotationHandle((AnnotationMirror)value.getValue()));
/*     */     }
/* 217 */     return Collections.unmodifiableList(annotations);
/*     */   }
/*     */   
/*     */   protected AnnotationValue getAnnotationValue(String key) {
/* 221 */     for (ExecutableElement elem : this.annotation.getElementValues().keySet()) {
/* 222 */       if (elem.getSimpleName().contentEquals(key)) {
/* 223 */         return (AnnotationValue)this.annotation.getElementValues().get(elem);
/*     */       }
/*     */     } 
/*     */     
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static <T> List<T> unwrapAnnotationValueList(List<AnnotationValue> list) {
/* 232 */     if (list == null) {
/* 233 */       return Collections.emptyList();
/*     */     }
/*     */     
/* 236 */     List<T> unfolded = new ArrayList<T>(list.size());
/* 237 */     for (AnnotationValue value : list) {
/* 238 */       unfolded.add(value.getValue());
/*     */     }
/*     */     
/* 241 */     return unfolded;
/*     */   }
/*     */   
/*     */   protected static AnnotationMirror getAnnotation(Element elem, Class<? extends Annotation> annotationClass) {
/* 245 */     if (elem == null) {
/* 246 */       return null;
/*     */     }
/*     */     
/* 249 */     List<? extends AnnotationMirror> annotations = elem.getAnnotationMirrors();
/*     */     
/* 251 */     if (annotations == null) {
/* 252 */       return null;
/*     */     }
/*     */     
/* 255 */     for (AnnotationMirror annotation : annotations) {
/* 256 */       Element element = annotation.getAnnotationType().asElement();
/* 257 */       if (!(element instanceof TypeElement)) {
/*     */         continue;
/*     */       }
/* 260 */       TypeElement annotationElement = (TypeElement)element;
/* 261 */       if (annotationElement.getQualifiedName().contentEquals(annotationClass.getName())) {
/* 262 */         return annotation;
/*     */       }
/*     */     } 
/*     */     
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 276 */   public static AnnotationHandle of(AnnotationMirror annotation) { return new AnnotationHandle(annotation); }
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
/* 289 */   public static AnnotationHandle of(Element elem, Class<? extends Annotation> annotationClass) { return new AnnotationHandle(getAnnotation(elem, annotationClass)); }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mirror\AnnotationHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */