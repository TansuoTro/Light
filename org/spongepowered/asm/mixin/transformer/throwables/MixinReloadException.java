/*    */ package org.spongepowered.asm.mixin.transformer.throwables;
/*    */ 
/*    */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
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
/*    */ 
/*    */ public class MixinReloadException
/*    */   extends MixinException
/*    */ {
/*    */   private static final long serialVersionUID = 2L;
/*    */   private final IMixinInfo mixinInfo;
/*    */   
/*    */   public MixinReloadException(IMixinInfo mixinInfo, String message) {
/* 41 */     super(message);
/* 42 */     this.mixinInfo = mixinInfo;
/*    */   }
/*    */ 
/*    */   
/* 46 */   public IMixinInfo getMixinInfo() { return this.mixinInfo; }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\throwables\MixinReloadException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */