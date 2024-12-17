/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabCompleter;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageTabCompleter
/*    */   implements TabCompleter
/*    */ {
/*    */   private DeluxeChat plugin;
/*    */   
/*    */   public MessageTabCompleter(DeluxeChat instance) {
/* 19 */     this.plugin = instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> onTabComplete(CommandSender s, Command c, String label, String[] args) {
/* 25 */     if (args.length > 0) {
/* 26 */       List<Player> matches = Bukkit.matchPlayer(args[args.length - 1]);
/* 27 */       List<String> players = new ArrayList<>();
/* 28 */       if (!(s instanceof Player)) {
/* 29 */         matches.forEach(m -> players.add(m.getName()));
/* 30 */         return players;
/*    */       } 
/*    */       
/* 33 */       Player snd = (Player)s;
/*    */       
/* 35 */       boolean vbp = snd.hasPermission("deluxechat.vanish.bypass");
/* 36 */       boolean ibp = snd.hasPermission("deluxechat.ignore.bypass");
/* 37 */       boolean ignoretoggle = snd.hasPermission("deluxechat.pm.ignoretoggle");
/*    */       
/* 39 */       for (Player m : matches) {
/*    */         
/* 41 */         if (!ignoretoggle && 
/* 42 */           this.plugin.hasPmDisabled(m)) {
/*    */           continue;
/*    */         }
/*    */ 
/*    */         
/* 47 */         if (!vbp) {
/* 51 */           if (this.plugin.essentials != null && this.plugin.essentials.isVanished(snd, m)) {
/*    */             continue;
/*    */           }
/* 54 */           if (this.plugin.premiumVanish != null && this.plugin.premiumVanish.isVanished(m)) {
/*    */             continue;
/*    */           }
/*    */         } 
/* 58 */         if (!ibp && 
/* 59 */           this.plugin.essentials != null && this.plugin.essentials.isIgnored(m, snd)) {
/*    */           continue;
/*    */         }
/*    */         
/* 63 */         players.add(m.getName());
/*    */       } 
/* 65 */       return players;
/*    */     } 
/* 67 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\MessageTabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */