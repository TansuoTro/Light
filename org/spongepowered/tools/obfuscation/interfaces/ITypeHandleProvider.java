package org.spongepowered.tools.obfuscation.interfaces;

import javax.lang.model.type.TypeMirror;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

public interface ITypeHandleProvider {
  TypeHandle getTypeHandle(String paramString);
  
  TypeHandle getSimulatedHandle(String paramString, TypeMirror paramTypeMirror);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\interfaces\ITypeHandleProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */