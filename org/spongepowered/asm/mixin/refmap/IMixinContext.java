package org.spongepowered.asm.mixin.refmap;

import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;

public interface IMixinContext {
  IMixinInfo getMixin();
  
  Extensions getExtensions();
  
  String getClassName();
  
  String getClassRef();
  
  String getTargetClassRef();
  
  IReferenceMapper getReferenceMapper();
  
  boolean getOption(MixinEnvironment.Option paramOption);
  
  int getPriority();
  
  Target getTargetMethod(MethodNode paramMethodNode);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\refmap\IMixinContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */