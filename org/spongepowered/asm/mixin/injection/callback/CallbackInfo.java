/*     */ package org.spongepowered.asm.mixin.injection.callback;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Type;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CallbackInfo
/*     */   implements Cancellable
/*     */ {
/*     */   private final String name;
/*     */   private final boolean cancellable;
/*     */   private boolean cancelled;
/*     */   
/*     */   public CallbackInfo(String name, boolean cancellable) {
/*  62 */     this.name = name;
/*  63 */     this.cancellable = cancellable;
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
/*  74 */   public String getId() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public String toString() { return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", new Object[] { getClass().getSimpleName(), this.name, Boolean.valueOf(this.cancellable) }); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   public final boolean isCancellable() { return this.cancellable; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public final boolean isCancelled() { return this.cancelled; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() throws CancellationException {
/* 100 */     if (!this.cancellable) {
/* 101 */       throw new CancellationException(String.format("The call %s is not cancellable.", new Object[] { this.name }));
/*     */     }
/*     */     
/* 104 */     this.cancelled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 110 */   static String getCallInfoClassName() { return CallbackInfo.class.getName(); }
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
/* 122 */   public static String getCallInfoClassName(Type returnType) { return (returnType.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/'); }
/*     */ 
/*     */   
/*     */   static String getConstructorDescriptor(Type returnType) {
/* 126 */     if (returnType.equals(Type.VOID_TYPE)) {
/* 127 */       return getConstructorDescriptor();
/*     */     }
/*     */     
/* 130 */     if (returnType.getSort() == 10 || returnType.getSort() == 9) {
/* 131 */       return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", "Ljava/lang/Object;" });
/*     */     }
/*     */     
/* 134 */     return String.format("(%sZ%s)V", new Object[] { "Ljava/lang/String;", returnType.getDescriptor() });
/*     */   }
/*     */ 
/*     */   
/* 138 */   static String getConstructorDescriptor() { return String.format("(%sZ)V", new Object[] { "Ljava/lang/String;" }); }
/*     */ 
/*     */ 
/*     */   
/* 142 */   static String getIsCancelledMethodName() { return "isCancelled"; }
/*     */ 
/*     */ 
/*     */   
/* 146 */   static String getIsCancelledMethodSig() { return "()Z"; }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\callback\CallbackInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */