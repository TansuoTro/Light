package me.jellysquid.mods.phosphor.api;

public interface IChunkLightingData {
  short[] getNeighborLightChecks();
  
  void setNeighborLightChecks(short[] paramArrayOfShort);
  
  boolean isLightInitialized();
  
  void setLightInitialized(boolean paramBoolean);
  
  void setSkylightUpdatedPublic();
}


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\api\IChunkLightingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */