/*    */ package org.spongepowered.asm.service.mojang;
/*    */ 
/*    */ import net.minecraft.launchwrapper.IClassTransformer;
/*    */ import org.spongepowered.asm.service.ILegacyClassTransformer;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LegacyTransformerHandle
/*    */   implements ILegacyClassTransformer
/*    */ {
/*    */   private final IClassTransformer transformer;
/*    */   
/* 45 */   LegacyTransformerHandle(IClassTransformer transformer) { this.transformer = transformer; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   public String getName() { return this.transformer.getClass().getName(); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 62 */   public boolean isDelegationExcluded() { return (this.transformer.getClass().getAnnotation(javax.annotation.Resource.class) != null); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 71 */   public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) { return this.transformer.transform(name, transformedName, basicClass); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\service\mojang\LegacyTransformerHandle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */