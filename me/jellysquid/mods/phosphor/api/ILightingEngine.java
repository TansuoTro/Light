package me.jellysquid.mods.phosphor.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;

public interface ILightingEngine {
  void scheduleLightUpdate(EnumSkyBlock paramEnumSkyBlock, BlockPos paramBlockPos);
  
  void processLightUpdates();
  
  void processLightUpdatesForType(EnumSkyBlock paramEnumSkyBlock);
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\api\ILightingEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */