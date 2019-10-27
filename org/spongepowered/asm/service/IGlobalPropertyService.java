package org.spongepowered.asm.service;

public interface IGlobalPropertyService {
  <T> T getProperty(String paramString);
  
  void setProperty(String paramString, Object paramObject);
  
  <T> T getProperty(String paramString, T paramT);
  
  String getPropertyString(String paramString1, String paramString2);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\service\IGlobalPropertyService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */