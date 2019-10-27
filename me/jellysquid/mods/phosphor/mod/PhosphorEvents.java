/*    */ package me.jellysquid.mods.phosphor.mod;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.text.Style;
/*    */ import net.minecraft.util.text.TextComponentString;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ import net.minecraft.util.text.event.ClickEvent;
/*    */ import net.minecraftforge.event.entity.EntityJoinWorldEvent;
/*    */ import net.minecraftforge.fml.common.CertificateHelper;
/*    */ import net.minecraftforge.fml.common.Loader;
/*    */ import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
/*    */ import net.minecraftforge.fml.common.ModContainer;
/*    */ import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
/*    */ import net.minecraftforge.fml.relauncher.Side;
/*    */ 
/*    */ @EventBusSubscriber({Side.CLIENT})
/*    */ public class PhosphorEvents {
/*    */   @SubscribeEvent
/*    */   public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
/* 20 */     if (!(event.getWorld()).field_72995_K) {
/*    */       return;
/*    */     }
/*    */     
/* 24 */     PhosphorConfig config = PhosphorConfig.instance();
/*    */     
/* 26 */     if (!config.enablePhosphor || !config.showPatreonMessage) {
/*    */       return;
/*    */     }
/*    */     
/* 30 */     if (!(event.getEntity() instanceof EntityPlayer)) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     EntityPlayer player = (EntityPlayer)event.getEntity();
/*    */     
/* 36 */     if (isAetherInstalled()) {
/* 37 */       player.func_145747_a((new TextComponentString(TextFormatting.YELLOW + "The Aether II includes the Phosphor mod! ❤ " + TextFormatting.GOLD + "You can help fund the development of Phosphor with a pledge to the author's Patreon. " + TextFormatting.YELLOW + TextFormatting.UNDERLINE + "Click this link to make a pledge!"))
/*    */           
/* 39 */           .func_150255_a((new Style()).func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/jellysquid"))));
/*    */     } else {
/* 41 */       player.func_145747_a((new TextComponentString(TextFormatting.YELLOW + "Thanks for installing Phosphor! ❤ " + TextFormatting.GOLD + "You can help fund the development of future optimization mods like this one through pledging to my Patreon. " + TextFormatting.YELLOW + TextFormatting.UNDERLINE + "Click this link to make a pledge!"))
/*    */           
/* 43 */           .func_150255_a((new Style()).func_150241_a(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.patreon.com/jellysquid"))));
/*    */     } 
/*    */     
/* 46 */     config.showPatreonMessage = false;
/* 47 */     config.saveConfig();
/*    */   }
/*    */   
/*    */   private static boolean isAetherInstalled() {
/* 51 */     if (!Loader.isModLoaded("aether")) {
/* 52 */       return false;
/*    */     }
/*    */     
/* 55 */     mod = (ModContainer)Loader.instance().getIndexedModList().get("aether");
/*    */     
/* 57 */     if (mod == null) {
/* 58 */       return false;
/*    */     }
/*    */     
/* 61 */     String fingerprint = CertificateHelper.getFingerprint(mod.getSigningCertificate());
/*    */     
/* 63 */     return fingerprint.equals("db341c083b1b8ce9160a769b569ef6737b3f4cdf");
/*    */   }
/*    */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\PhosphorEvents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */