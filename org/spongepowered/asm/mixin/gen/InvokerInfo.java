/*    */ package org.spongepowered.asm.mixin.gen;
/*    */ 
/*    */ import org.spongepowered.asm.lib.Type;
/*    */ import org.spongepowered.asm.lib.tree.MethodNode;
/*    */ import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
/*    */ import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
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
/*    */ public class InvokerInfo
/*    */   extends AccessorInfo
/*    */ {
/* 38 */   public InvokerInfo(MixinTargetContext mixin, MethodNode method) { super(mixin, method, Invoker.class); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 43 */   protected AccessorInfo.AccessorType initType() { return AccessorInfo.AccessorType.METHOD_PROXY; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 48 */   protected Type initTargetFieldType() { return null; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 53 */   protected MemberInfo initTarget() { return new MemberInfo(getTargetName(), null, this.method.desc); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 58 */   public void locate() { this.targetMethod = findTargetMethod(); }
/*    */ 
/*    */ 
/*    */   
/* 62 */   private MethodNode findTargetMethod() { return (MethodNode)findTarget(this.classNode.methods); }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\asm\mixin\gen\InvokerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */