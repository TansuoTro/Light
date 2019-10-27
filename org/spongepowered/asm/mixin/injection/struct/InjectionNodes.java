/*     */ package org.spongepowered.asm.mixin.injection.struct;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.spongepowered.asm.lib.tree.AbstractInsnNode;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ 
/*     */ 
/*     */ public class InjectionNodes
/*     */   extends ArrayList<InjectionNodes.InjectionNode>
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */   
/*     */   public static class InjectionNode
/*     */     extends Object
/*     */     implements Comparable<InjectionNode>
/*     */   {
/*  58 */     private static int nextId = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final AbstractInsnNode originalTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private AbstractInsnNode currentTarget;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Map<String, Object> decorations;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode(AbstractInsnNode node) {
/*  88 */       this.currentTarget = this.originalTarget = node;
/*  89 */       this.id = nextId++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     public int getId() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     public AbstractInsnNode getOriginalTarget() { return this.originalTarget; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     public AbstractInsnNode getCurrentTarget() { return this.currentTarget; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode replace(AbstractInsnNode target) {
/* 120 */       this.currentTarget = target;
/* 121 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InjectionNode remove() {
/* 128 */       this.currentTarget = null;
/* 129 */       return this;
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
/* 141 */     public boolean matches(AbstractInsnNode node) { return (this.originalTarget == node || this.currentTarget == node); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     public boolean isReplaced() { return (this.originalTarget != this.currentTarget); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     public boolean isRemoved() { return (this.currentTarget == null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public <V> InjectionNode decorate(String key, V value) {
/* 166 */       if (this.decorations == null) {
/* 167 */         this.decorations = new HashMap();
/*     */       }
/* 169 */       this.decorations.put(key, value);
/* 170 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 180 */     public boolean hasDecoration(String key) { return (this.decorations != null && this.decorations.get(key) != null); }
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
/* 192 */     public <V> V getDecoration(String key) { return (V)((this.decorations == null) ? null : this.decorations.get(key)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     public int compareTo(InjectionNode other) { return (other == null) ? Integer.MAX_VALUE : (hashCode() - other.hashCode()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 208 */     public String toString() { return String.format("InjectionNode[%s]", new Object[] { Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " ") }); }
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
/*     */   public InjectionNode add(AbstractInsnNode node) {
/* 220 */     InjectionNode injectionNode = get(node);
/* 221 */     if (injectionNode == null) {
/* 222 */       injectionNode = new InjectionNode(node);
/* 223 */       add(injectionNode);
/*     */     } 
/* 225 */     return injectionNode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InjectionNode get(AbstractInsnNode node) {
/* 236 */     for (InjectionNode injectionNode : this) {
/* 237 */       if (injectionNode.matches(node)) {
/* 238 */         return injectionNode;
/*     */       }
/*     */     } 
/* 241 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 251 */   public boolean contains(AbstractInsnNode node) { return (get(node) != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replace(AbstractInsnNode oldNode, AbstractInsnNode newNode) {
/* 262 */     InjectionNode injectionNode = get(oldNode);
/* 263 */     if (injectionNode != null) {
/* 264 */       injectionNode.replace(newNode);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(AbstractInsnNode node) {
/* 275 */     InjectionNode injectionNode = get(node);
/* 276 */     if (injectionNode != null)
/* 277 */       injectionNode.remove(); 
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\InjectionNodes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */