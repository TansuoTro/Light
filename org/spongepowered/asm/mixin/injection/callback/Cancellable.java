package org.spongepowered.asm.mixin.injection.callback;

public interface Cancellable {
  boolean isCancellable();
  
  boolean isCancelled();
  
  void cancel() throws CancellationException;
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\injection\callback\Cancellable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */