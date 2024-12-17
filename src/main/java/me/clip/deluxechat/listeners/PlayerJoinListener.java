/*    */ package me.clip.deluxechat.listeners;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.clip.deluxechat.DeluxeChat;
/*    */ import me.clip.deluxechat.DeluxeUtil;
/*    */ import me.clip.deluxechat.fanciful.FancyMessage;
/*    */ import me.clip.deluxechat.updater.SpigotUpdater;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class PlayerJoinListener
/*    */   implements Listener
/*    */ {
/*    */   DeluxeChat plugin;
/*    */   
/*    */   public PlayerJoinListener(DeluxeChat i) {
/* 23 */     this.plugin = i;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent e) {
/* 28 */     DeluxeChat.disableSocialSpy(e.getPlayer());
/* 29 */     DeluxeChat.removeFromPM(e.getPlayer().getName());
/* 30 */     this.plugin.enablePm(e.getPlayer());
/* 31 */     this.plugin.setGlobal(e.getPlayer().getUniqueId().toString());
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent e) {
/* 37 */     Player p = e.getPlayer();
/*    */     
/* 39 */     if (p.hasPermission("deluxechat.socialspy.onjoin")) {
/* 40 */       DeluxeChat.enableSocialSpy(p);
/*    */     }
/*    */     
/* 43 */     if (DeluxeChat.useBungee())
/*    */     {
/* 45 */       if (DeluxeChat.globalOnJoin()) {
/* 46 */         this.plugin.setGlobal(p.getUniqueId().toString());
/*    */       } else {
/* 48 */         this.plugin.setLocal(p.getUniqueId().toString());
/*    */       } 
/*    */     }
/*    */     
/* 52 */     if (!p.isOp()) {
/*    */       return;
/*    */     }
/*    */     
/* 56 */     if (this.plugin.getUpdater() == null) {
/*    */       return;
/*    */     }
/*    */     
/* 60 */     if (SpigotUpdater.updateAvailable()) {
/*    */       
/* 62 */       DeluxeUtil.sms(p, "&8&m-----------------------------------------------------");
/*    */       
/* 64 */       List<String> tip = new ArrayList<>();
/* 65 */       tip.add(ChatColor.translateAlternateColorCodes('&', "&aUpdate released: &f" + SpigotUpdater.getHighest()));
/* 66 */       tip.add(ChatColor.translateAlternateColorCodes('&', "&cYou are running: &f" + this.plugin.getDescription().getVersion()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 72 */       FancyMessage send = (new FancyMessage("An update for DeluxeChat has been found! ")).color(ChatColor.GREEN).tooltip(tip).link("http://www.spigotmc.org/resources/deluxechat.1277/").then("Click to download!").color(ChatColor.WHITE).link("http://www.spigotmc.org/resources/deluxechat.1277/");
/*    */       
/* 74 */       this.plugin.getChat().sendFancyMessage((CommandSender)p, send);
/*    */       
/* 76 */       DeluxeUtil.sms(p, "&8&m-----------------------------------------------------");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\listeners\PlayerJoinListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */