package org.spongepowered.asm.mixin.refmap;

public interface IReferenceMapper {
  boolean isDefault();
  
  String getResourceName();
  
  String getStatus();
  
  String getContext();
  
  void setContext(String paramString);
  
  String remap(String paramString1, String paramString2);
  
  String remapWithContext(String paramString1, String paramString2, String paramString3);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\refmap\IReferenceMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */