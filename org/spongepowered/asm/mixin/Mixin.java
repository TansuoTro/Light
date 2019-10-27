package org.spongepowered.asm.mixin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface Mixin {
  Class<?>[] value() default {};
  
  String[] targets() default {};
  
  int priority() default 1000;
  
  boolean remap() default true;
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\Mixin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */