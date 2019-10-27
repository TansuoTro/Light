package org.spongepowered.asm.mixin.transformer.ext;

import org.spongepowered.asm.mixin.MixinEnvironment;

public interface IExtension {
  boolean checkActive(MixinEnvironment paramMixinEnvironment);
  
  void preApply(ITargetClassContext paramITargetClassContext);
  
  void postApply(ITargetClassContext paramITargetClassContext);
  
  void export(MixinEnvironment paramMixinEnvironment, String paramString, boolean paramBoolean, byte[] paramArrayOfByte);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\transformer\ext\IExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */