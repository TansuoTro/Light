/*    */ package org.spongepowered.tools.obfuscation.mcp;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import org.spongepowered.tools.obfuscation.service.IObfuscationService;
/*    */ import org.spongepowered.tools.obfuscation.service.ObfuscationTypeDescriptor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ObfuscationServiceMCP
/*    */   implements IObfuscationService
/*    */ {
/*    */   public static final String SEARGE = "searge";
/*    */   public static final String NOTCH = "notch";
/*    */   public static final String REOBF_SRG_FILE = "reobfSrgFile";
/*    */   public static final String REOBF_EXTRA_SRG_FILES = "reobfSrgFiles";
/*    */   public static final String REOBF_NOTCH_FILE = "reobfNotchSrgFile";
/*    */   public static final String REOBF_EXTRA_NOTCH_FILES = "reobfNotchSrgFiles";
/*    */   public static final String OUT_SRG_SRG_FILE = "outSrgFile";
/*    */   public static final String OUT_NOTCH_SRG_FILE = "outNotchSrgFile";
/*    */   public static final String OUT_REFMAP_FILE = "outRefMapFile";
/*    */   
/* 54 */   public Set<String> getSupportedOptions() { return ImmutableSet.of("reobfSrgFile", "reobfSrgFiles", "reobfNotchSrgFile", "reobfNotchSrgFiles", "outSrgFile", "outNotchSrgFile", new String[] { "outRefMapFile" }); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public Collection<ObfuscationTypeDescriptor> getObfuscationTypes() { return ImmutableList.of(new ObfuscationTypeDescriptor("searge", "reobfSrgFile", "reobfSrgFiles", "outSrgFile", ObfuscationEnvironmentMCP.class), new ObfuscationTypeDescriptor("notch", "reobfNotchSrgFile", "reobfNotchSrgFiles", "outNotchSrgFile", ObfuscationEnvironmentMCP.class)); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\tools\obfuscation\mcp\ObfuscationServiceMCP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */