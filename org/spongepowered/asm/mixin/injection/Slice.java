package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Slice {
  String id() default "";
  
  At from() default @At("HEAD");
  
  At to() default @At("TAIL");
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\Slice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */