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
/*     */ public class CallbackInfoReturnable<R>
/*     */   extends CallbackInfo
/*     */ {
/*     */   private R returnValue;
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable) {
/*  42 */     super(name, cancellable);
/*  43 */     this.returnValue = null;
/*     */   }
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, R returnValue) {
/*  47 */     super(name, cancellable);
/*  48 */     this.returnValue = returnValue;
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, byte returnValue) {
/*  53 */     super(name, cancellable);
/*  54 */     this.returnValue = Byte.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, char returnValue) {
/*  59 */     super(name, cancellable);
/*  60 */     this.returnValue = Character.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, double returnValue) {
/*  65 */     super(name, cancellable);
/*  66 */     this.returnValue = Double.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, float returnValue) {
/*  71 */     super(name, cancellable);
/*  72 */     this.returnValue = Float.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, int returnValue) {
/*  77 */     super(name, cancellable);
/*  78 */     this.returnValue = Integer.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, long returnValue) {
/*  83 */     super(name, cancellable);
/*  84 */     this.returnValue = Long.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, short returnValue) {
/*  89 */     super(name, cancellable);
/*  90 */     this.returnValue = Short.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */   
/*     */   public CallbackInfoReturnable(String name, boolean cancellable, boolean returnValue) {
/*  95 */     super(name, cancellable);
/*  96 */     this.returnValue = Boolean.valueOf(returnValue);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReturnValue(R returnValue) throws CancellationException {
/* 106 */     cancel();
/*     */     
/* 108 */     this.returnValue = returnValue;
/*     */   }
/*     */ 
/*     */   
/* 112 */   public R getReturnValue() { return (R)this.returnValue; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   public byte getReturnValueB() { return (this.returnValue == null) ? 0 : ((Byte)this.returnValue).byteValue(); }
/* 118 */   public char getReturnValueC() { return (this.returnValue == null) ? Character.MIN_VALUE : ((Character)this.returnValue).charValue(); }
/* 119 */   public double getReturnValueD() { return (this.returnValue == null) ? 0.0D : ((Double)this.returnValue).doubleValue(); }
/* 120 */   public float getReturnValueF() { return (this.returnValue == null) ? 0.0F : ((Float)this.returnValue).floatValue(); }
/* 121 */   public int getReturnValueI() { return (this.returnValue == null) ? 0 : ((Integer)this.returnValue).intValue(); }
/* 122 */   public long getReturnValueJ() { return (this.returnValue == null) ? 0L : ((Long)this.returnValue).longValue(); }
/* 123 */   public short getReturnValueS() { return (this.returnValue == null) ? 0 : ((Short)this.returnValue).shortValue(); }
/* 124 */   public boolean getReturnValueZ() { return (this.returnValue == null) ? false : ((Boolean)this.returnValue).booleanValue(); }
/*     */ 
/*     */   
/*     */   static String getReturnAccessor(Type returnType) {
/* 128 */     if (returnType.getSort() == 10 || returnType.getSort() == 9) {
/* 129 */       return "getReturnValue";
/*     */     }
/*     */     
/* 132 */     return String.format("getReturnValue%s", new Object[] { returnType.getDescriptor() });
/*     */   }
/*     */   
/*     */   static String getReturnDescriptor(Type returnType) {
/* 136 */     if (returnType.getSort() == 10 || returnType.getSort() == 9) {
/* 137 */       return String.format("()%s", new Object[] { "Ljava/lang/Object;" });
/*     */     }
/*     */     
/* 140 */     return String.format("()%s", new Object[] { returnType.getDescriptor() });
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\callback\CallbackInfoReturnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */