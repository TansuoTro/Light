/*    */ package org.spongepowered.asm.mixin.injection.struct;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.throwables.MixinException;
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
/*    */ public class InvalidMemberDescriptorException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */   
/* 37 */   public InvalidMemberDescriptorException(String message) { super(message); }
/*    */ 
/*    */ 
/*    */   
/* 41 */   public InvalidMemberDescriptorException(Throwable cause) { super(cause); }
/*    */ 
/*    */ 
/*    */   
/* 45 */   public InvalidMemberDescriptorException(String message, Throwable cause) { super(message, cause); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\struct\InvalidMemberDescriptorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */