/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.spongepowered.asm.lib.tree.AnnotationNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodNode;
/*     */ import org.spongepowered.asm.mixin.injection.throwables.InjectionValidationException;
/*     */ import org.spongepowered.asm.util.Annotations;
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
/*     */ public class InjectorGroupInfo
/*     */ {
/*     */   private final String name;
/*     */   private final List<InjectionInfo> members;
/*     */   private final boolean isDefault;
/*     */   private int minCallbackCount;
/*     */   private int maxCallbackCount;
/*     */   
/*     */   public static final class Map
/*     */     extends HashMap<String, InjectorGroupInfo>
/*     */   {
/*     */     private static final long serialVersionUID = 1L;
/*  52 */     private static final InjectorGroupInfo NO_GROUP = new InjectorGroupInfo("NONE", true);
/*     */ 
/*     */ 
/*     */     
/*  56 */     public InjectorGroupInfo get(Object key) { return forName(key.toString()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectorGroupInfo forName(String name) {
/*  67 */       InjectorGroupInfo value = (InjectorGroupInfo)super.get(name);
/*  68 */       if (value == null) {
/*  69 */         value = new InjectorGroupInfo(name);
/*  70 */         put(name, value);
/*     */       } 
/*  72 */       return value;
/*     */     }
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
/*  84 */     public InjectorGroupInfo parseGroup(MethodNode method, String defaultGroup) { return parseGroup(Annotations.getInvisible(method, org.spongepowered.asm.mixin.injection.Group.class), defaultGroup); }
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
/*     */     public InjectorGroupInfo parseGroup(AnnotationNode annotation, String defaultGroup) {
/*  96 */       if (annotation == null) {
/*  97 */         return NO_GROUP;
/*     */       }
/*     */       
/* 100 */       String name = (String)Annotations.getValue(annotation, "name");
/* 101 */       if (name == null || name.isEmpty()) {
/* 102 */         name = defaultGroup;
/*     */       }
/* 104 */       InjectorGroupInfo groupInfo = forName(name);
/*     */       
/* 106 */       Integer min = (Integer)Annotations.getValue(annotation, "min");
/* 107 */       if (min != null && min.intValue() != -1) {
/* 108 */         groupInfo.setMinRequired(min.intValue());
/*     */       }
/*     */       
/* 111 */       Integer max = (Integer)Annotations.getValue(annotation, "max");
/* 112 */       if (max != null && max.intValue() != -1) {
/* 113 */         groupInfo.setMaxAllowed(max.intValue());
/*     */       }
/*     */       
/* 116 */       return groupInfo;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void validateAll() {
/* 125 */       for (InjectorGroupInfo group : values()) {
/* 126 */         group.validate();
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
/* 158 */   public InjectorGroupInfo(String name) { this(name, false); } InjectorGroupInfo(String name, boolean flag) {
/*     */     this.members = new ArrayList();
/*     */     this.minCallbackCount = -1;
/*     */     this.maxCallbackCount = Integer.MAX_VALUE;
/* 162 */     this.name = name;
/* 163 */     this.isDefault = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 168 */   public String toString() { return String.format("@Group(name=%s, min=%d, max=%d)", new Object[] { getName(), Integer.valueOf(getMinRequired()), Integer.valueOf(getMaxAllowed()) }); }
/*     */ 
/*     */ 
/*     */   
/* 172 */   public boolean isDefault() { return this.isDefault; }
/*     */ 
/*     */ 
/*     */   
/* 176 */   public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */   
/* 180 */   public int getMinRequired() { return Math.max(this.minCallbackCount, 1); }
/*     */ 
/*     */ 
/*     */   
/* 184 */   public int getMaxAllowed() { return Math.min(this.maxCallbackCount, 2147483647); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 193 */   public Collection<InjectionInfo> getMembers() { return Collections.unmodifiableCollection(this.members); }
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
/*     */   public void setMinRequired(int min) {
/* 205 */     if (min < 1) {
/* 206 */       throw new IllegalArgumentException("Cannot set zero or negative value for injector group min count. Attempted to set min=" + min + " on " + this);
/*     */     }
/*     */     
/* 209 */     if (this.minCallbackCount > 0 && this.minCallbackCount != min) {
/* 210 */       LogManager.getLogger("mixin").warn("Conflicting min value '{}' on @Group({}), previously specified {}", new Object[] { Integer.valueOf(min), this.name, 
/* 211 */             Integer.valueOf(this.minCallbackCount) });
/*     */     }
/* 213 */     this.minCallbackCount = Math.max(this.minCallbackCount, min);
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
/*     */   public void setMaxAllowed(int max) {
/* 225 */     if (max < 1) {
/* 226 */       throw new IllegalArgumentException("Cannot set zero or negative value for injector group max count. Attempted to set max=" + max + " on " + this);
/*     */     }
/*     */     
/* 229 */     if (this.maxCallbackCount < Integer.MAX_VALUE && this.maxCallbackCount != max) {
/* 230 */       LogManager.getLogger("mixin").warn("Conflicting max value '{}' on @Group({}), previously specified {}", new Object[] { Integer.valueOf(max), this.name, 
/* 231 */             Integer.valueOf(this.maxCallbackCount) });
/*     */     }
/* 233 */     this.maxCallbackCount = Math.min(this.maxCallbackCount, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorGroupInfo add(InjectionInfo member) {
/* 243 */     this.members.add(member);
/* 244 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectorGroupInfo validate() throws InjectionValidationException {
/* 254 */     if (this.members.size() == 0)
/*     */     {
/* 256 */       return this;
/*     */     }
/*     */     
/* 259 */     int total = 0;
/* 260 */     for (InjectionInfo member : this.members) {
/* 261 */       total += member.getInjectedCallbackCount();
/*     */     }
/*     */     
/* 264 */     int min = getMinRequired();
/* 265 */     int max = getMaxAllowed();
/* 266 */     if (total < min)
/* 267 */       throw new InjectionValidationException(this, String.format("expected %d invocation(s) but only %d succeeded", new Object[] { Integer.valueOf(min), Integer.valueOf(total) })); 
/* 268 */     if (total > max) {
/* 269 */       throw new InjectionValidationException(this, String.format("maximum of %d invocation(s) allowed but %d succeeded", new Object[] { Integer.valueOf(max), Integer.valueOf(total) }));
/*     */     }
/*     */     
/* 272 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\InjectorGroupInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */