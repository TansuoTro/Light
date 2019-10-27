/*     */ package org.spongepowered.asm.util;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VersionNumber
/*     */   extends Object
/*     */   implements Comparable<VersionNumber>, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*  44 */   public static final VersionNumber NONE = new VersionNumber();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   private static final Pattern PATTERN = Pattern.compile("^(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5})(?:\\.(\\d{1,5}))?)?)?(-[a-zA-Z0-9_\\-]+)?$");
/*     */ 
/*     */ 
/*     */   
/*     */   private final long value;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String suffix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private VersionNumber() {
/*  64 */     this.value = 0L;
/*  65 */     this.suffix = "";
/*     */   }
/*     */ 
/*     */   
/*  69 */   private VersionNumber(short[] parts) { this(parts, null); }
/*     */ 
/*     */   
/*     */   private VersionNumber(short[] parts, String suffix) {
/*  73 */     this.value = pack(parts);
/*  74 */     this.suffix = (suffix != null) ? suffix : "";
/*     */   }
/*     */ 
/*     */   
/*  78 */   private VersionNumber(short major, short minor, short revision, short build) { this(major, minor, revision, build, null); }
/*     */ 
/*     */   
/*     */   private VersionNumber(short major, short minor, short revision, short build, String suffix) {
/*  82 */     this.value = pack(new short[] { major, minor, revision, build });
/*  83 */     this.suffix = (suffix != null) ? suffix : "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  91 */     short[] parts = unpack(this.value);
/*     */     
/*  93 */     return String.format("%d.%d%3$s%4$s%5$s", new Object[] {
/*  94 */           Short.valueOf(parts[0]), 
/*  95 */           Short.valueOf(parts[1]), ((this.value & 0x7FFFFFFFL) > 0L) ? 
/*  96 */           String.format(".%d", new Object[] { Short.valueOf(parts[2]) }) : "", ((this.value & 0x7FFFL) > 0L) ? 
/*  97 */           String.format(".%d", new Object[] { Short.valueOf(parts[3]) }) : "", this.suffix
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(VersionNumber other) {
/* 106 */     if (other == null) {
/* 107 */       return 1;
/*     */     }
/* 109 */     long delta = this.value - other.value;
/* 110 */     return (delta > 0L) ? 1 : ((delta < 0L) ? -1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/* 118 */     if (!(other instanceof VersionNumber)) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     return (((VersionNumber)other).value == this.value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   public int hashCode() { return (int)(this.value >> 32) ^ (int)(this.value & 0xFFFFFFFFL); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 140 */   private static long pack(short... shorts) { return shorts[0] << 48 | shorts[1] << 32 | (shorts[2] << 16) | shorts[3]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 150 */   private static short[] unpack(long along) { return new short[] { (short)(int)(along >> 48), (short)(int)(along >> 32 & 0x7FFFL), (short)(int)(along >> 16 & 0x7FFFL), (short)(int)(along & 0x7FFFL) }; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 165 */   public static VersionNumber parse(String version) { return parse(version, NONE); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 177 */   public static VersionNumber parse(String version, String defaultVersion) { return parse(version, parse(defaultVersion)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static VersionNumber parse(String version, VersionNumber defaultVersion) {
/* 189 */     if (version == null) {
/* 190 */       return defaultVersion;
/*     */     }
/*     */     
/* 193 */     Matcher versionNumberPatternMatcher = PATTERN.matcher(version);
/* 194 */     if (!versionNumberPatternMatcher.matches()) {
/* 195 */       return defaultVersion;
/*     */     }
/*     */     
/* 198 */     short[] parts = new short[4];
/* 199 */     for (int pos = 0; pos < 4; pos++) {
/* 200 */       String part = versionNumberPatternMatcher.group(pos + 1);
/* 201 */       if (part != null) {
/* 202 */         int value = Integer.parseInt(part);
/* 203 */         if (value > 32767) {
/* 204 */           throw new IllegalArgumentException("Version parts cannot exceed 32767, found " + value);
/*     */         }
/* 206 */         parts[pos] = (short)value;
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     return new VersionNumber(parts, versionNumberPatternMatcher.group(5));
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\as\\util\VersionNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */