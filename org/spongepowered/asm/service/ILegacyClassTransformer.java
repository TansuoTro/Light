package org.spongepowered.asm.service;

public interface ILegacyClassTransformer extends ITransformer {
  String getName();
  
  boolean isDelegationExcluded();
  
  byte[] transformClassBytes(String paramString1, String paramString2, byte[] paramArrayOfByte);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\service\ILegacyClassTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */