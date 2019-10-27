/*     */ package org.spongepowered.asm.mixin.struct;
/*     */ 
/*     */ import org.spongepowered.asm.lib.Handle;
/*     */ import org.spongepowered.asm.lib.tree.FieldInsnNode;
/*     */ import org.spongepowered.asm.lib.tree.MethodInsnNode;
/*     */ import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
/*     */ import org.spongepowered.asm.util.Bytecode;
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
/*     */ public abstract class MemberRef
/*     */ {
/*     */   public static final class Method
/*     */     extends MemberRef
/*     */   {
/*     */     private static final int OPCODES = 191;
/*     */     public final MethodInsnNode insn;
/*     */     
/*  59 */     public Method(MethodInsnNode insn) { this.insn = insn; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     public boolean isField() { return false; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     public int getOpcode() { return this.insn.getOpcode(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOpcode(int opcode) {
/*  74 */       if ((opcode & 0xBF) == 0) {
/*  75 */         throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(opcode));
/*     */       }
/*     */       
/*  78 */       this.insn.setOpcode(opcode);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  83 */     public String getOwner() { return this.insn.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     public void setOwner(String owner) { this.insn.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     public String getName() { return this.insn.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     public void setName(String name) { this.insn.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     public String getDesc() { return this.insn.desc; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     public void setDesc(String desc) { this.insn.desc = desc; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Field
/*     */     extends MemberRef
/*     */   {
/*     */     private static final int OPCODES = 183;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final FieldInsnNode insn;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 130 */     public Field(FieldInsnNode insn) { this.insn = insn; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     public boolean isField() { return true; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     public int getOpcode() { return this.insn.getOpcode(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOpcode(int opcode) {
/* 145 */       if ((opcode & 0xB7) == 0) {
/* 146 */         throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(opcode));
/*     */       }
/*     */       
/* 149 */       this.insn.setOpcode(opcode);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 154 */     public String getOwner() { return this.insn.owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 159 */     public void setOwner(String owner) { this.insn.owner = owner; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     public String getName() { return this.insn.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     public void setName(String name) { this.insn.name = name; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     public String getDesc() { return this.insn.desc; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     public void setDesc(String desc) { this.insn.desc = desc; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Handle
/*     */     extends MemberRef
/*     */   {
/*     */     private Handle handle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     public Handle(Handle handle) { this.handle = handle; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 207 */     public Handle getMethodHandle() { return this.handle; }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isField() {
/* 212 */       switch (this.handle.getTag()) {
/*     */         case 5:
/*     */         case 6:
/*     */         case 7:
/*     */         case 8:
/*     */         case 9:
/* 218 */           return false;
/*     */         case 1:
/*     */         case 2:
/*     */         case 3:
/*     */         case 4:
/* 223 */           return true;
/*     */       } 
/* 225 */       throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int getOpcode() {
/* 231 */       int opcode = MemberRef.opcodeFromTag(this.handle.getTag());
/* 232 */       if (opcode == 0) {
/* 233 */         throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
/*     */       }
/* 235 */       return opcode;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setOpcode(int opcode) {
/* 240 */       int tag = MemberRef.tagFromOpcode(opcode);
/* 241 */       if (tag == 0) {
/* 242 */         throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(opcode) + " for method handle " + this.handle + ".");
/*     */       }
/* 244 */       boolean itf = (tag == 9);
/* 245 */       this.handle = new Handle(tag, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), itf);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 250 */     public String getOwner() { return this.handle.getOwner(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setOwner(String owner) {
/* 255 */       boolean itf = (this.handle.getTag() == 9);
/* 256 */       this.handle = new Handle(this.handle.getTag(), owner, this.handle.getName(), this.handle.getDesc(), itf);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 261 */     public String getName() { return this.handle.getName(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setName(String name) {
/* 266 */       boolean itf = (this.handle.getTag() == 9);
/* 267 */       this.handle = new Handle(this.handle.getTag(), this.handle.getOwner(), name, this.handle.getDesc(), itf);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 272 */     public String getDesc() { return this.handle.getDesc(); }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDesc(String desc) {
/* 277 */       boolean itf = (this.handle.getTag() == 9);
/* 278 */       this.handle = new Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), desc, itf);
/*     */     }
/*     */   }
/*     */   
/* 282 */   private static final int[] H_OPCODES = { 0, 180, 178, 181, 179, 182, 184, 183, 183, 185 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isField();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int getOpcode();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setOpcode(int paramInt);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getOwner();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setOwner(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setName(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getDesc();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void setDesc(String paramString);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 360 */     String name = Bytecode.getOpcodeName(getOpcode());
/* 361 */     return String.format("%s for %s.%s%s%s", new Object[] { name, getOwner(), getName(), isField() ? ":" : "", getDesc() });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 366 */     if (!(obj instanceof MemberRef)) {
/* 367 */       return false;
/*     */     }
/*     */     
/* 370 */     MemberRef other = (MemberRef)obj;
/* 371 */     return (getOpcode() == other.getOpcode() && 
/* 372 */       getOwner().equals(other.getOwner()) && 
/* 373 */       getName().equals(other.getName()) && 
/* 374 */       getDesc().equals(other.getDesc()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 379 */   public int hashCode() { return toString().hashCode(); }
/*     */ 
/*     */ 
/*     */   
/* 383 */   static int opcodeFromTag(int tag) { return (tag >= 0 && tag < H_OPCODES.length) ? H_OPCODES[tag] : 0; }
/*     */ 
/*     */   
/*     */   static int tagFromOpcode(int opcode) {
/* 387 */     for (int tag = 1; tag < H_OPCODES.length; tag++) {
/* 388 */       if (H_OPCODES[tag] == opcode) {
/* 389 */         return tag;
/*     */       }
/*     */     } 
/* 392 */     return 0;
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\struct\MemberRef.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */