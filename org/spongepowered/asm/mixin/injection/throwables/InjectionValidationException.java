/*    */ package org.spongepowered.asm.mixin.injection.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InjectionValidationException
/*    */   extends Exception
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   private final InjectorGroupInfo group;
/*    */   
/*    */   public InjectionValidationException(InjectorGroupInfo group, String message) {
/* 39 */     super(message);
/* 40 */     this.group = group;
/*    */   }
/*    */ 
/*    */   
/* 44 */   public InjectorGroupInfo getGroup() { return this.group; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\throwables\InjectionValidationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */