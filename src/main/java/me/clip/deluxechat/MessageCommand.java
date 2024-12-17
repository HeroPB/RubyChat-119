/*     */ package me.clip.deluxechat;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import me.clip.deluxechat.events.PrivateMessageEvent;
/*     */ import me.clip.deluxechat.fanciful.FancyMessage;
/*     */ import me.clip.deluxechat.messaging.PrivateMessageType;
/*     */ import me.clip.deluxechat.placeholders.PlaceholderHandler;
/*     */ import me.clip.placeholderapi.PlaceholderAPI;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MessageCommand
/*     */   implements CommandExecutor
/*     */ {
/*     */   DeluxeChat plugin;
/*     */   
/*     */   public MessageCommand(DeluxeChat instance) {
/*  26 */     this.plugin = instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/*  34 */     if (!(s instanceof Player)) {
/*  35 */       s.sendMessage("This command is not supported in console yet!");
/*  36 */       return true;
/*     */     } 
/*     */     
/*  39 */     Player sender = (Player)s;
/*     */     
/*  41 */     if (!sender.hasPermission("deluxechat.pm")) {
/*  42 */       DeluxeUtil.sms(sender, Lang.NO_PERMISSION.getConfigValue(new String[] { "deluxechat.pm" }));
/*     */ 
/*     */ 
/*     */       
/*  46 */       return true;
/*     */     } 
/*     */     
/*  49 */     if (args.length < 2) {
/*  50 */       DeluxeUtil.sms(sender, Lang.MSG_INCORRECT_USAGE.getConfigValue(null));
/*  51 */       return true;
/*     */     } 
/*     */     
/*  54 */     if (this.plugin.isMuted(sender)) {
/*  55 */       return true;
/*     */     }
/*     */     
/*  58 */     if (this.plugin.hasPmDisabled(sender)) {
/*  59 */       DeluxeUtil.sms(sender, Lang.PM_TOGGLED_SENDER.getConfigValue(null));
/*  60 */       return true;
/*     */     } 
/*     */     
/*  63 */     String message = StringUtils.join(Arrays.copyOfRange((Object[])args, 1, args.length), " ");
/*     */     
/*  65 */     message = DeluxeUtil.checkColor(sender, message, true);
/*     */     
/*  67 */     if (message.isEmpty()) {
/*  68 */       return true;
/*     */     }
/*     */     
/*  71 */     if (DeluxeChat.bungeePMEnabled()) {
/*     */       
/*  73 */       Player player = Bukkit.getPlayer(args[0]);
/*     */       
/*  75 */       if (player != null) {
/*     */         
/*  77 */         if (player.getName().equals(sender.getName())) {
/*  78 */           DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
/*  79 */           return true;
/*     */         } 
/*     */         
/*  82 */         if (this.plugin.premiumVanish != null && 
/*  83 */           this.plugin.premiumVanish.isVanished(player) && !sender.hasPermission("deluxechat.vanish.bypass")) {
/*  84 */           DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { args[0] }));
/*  85 */           return true;
/*     */         } 
/*     */ 
/*     */
/*     */ 
/*     */         
/*  98 */         if (this.plugin.essentials != null) {
/*     */           
/* 100 */           if (this.plugin.essentials.isVanished(sender, player) && !sender.hasPermission("deluxechat.vanish.bypass")) {
/* 101 */             DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE
/* 102 */                 .getConfigValue(new String[] { args[0] }));
/* 103 */             return true;
/*     */           } 
/*     */           
/* 106 */           if (this.plugin.essentials.isIgnored(player, sender) && !sender.hasPermission("deluxechat.ignore.bypass")) {
/* 107 */             DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IGNORING_SENDER
/*     */                 
/* 109 */                 .getConfigValue(new String[] {
/* 110 */                     player.getName() }));
/* 111 */             return true;
/*     */           } 
/*     */         } 
/*     */         
/* 115 */         if (this.plugin.hasPmDisabled(player) && 
/* 116 */           !sender.hasPermission("deluxechat.pm.ignoretoggle")) {
/* 117 */           DeluxeUtil.sms(sender, Lang.PM_TOGGLED_RECIPIENT.getConfigValue(new String[] { player
/* 118 */                   .getName() }));
/*     */           
/* 120 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 124 */         PrivateMessageEvent privateMessageEvent = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, player, message, false);
/*     */         
/* 126 */         Bukkit.getPluginManager().callEvent((Event)privateMessageEvent);
/*     */         
/* 128 */         if (privateMessageEvent.isCancelled()) {
/* 129 */           return true;
/*     */         }
/*     */         
/* 132 */         message = privateMessageEvent.getMessage();
/*     */         
/* 134 */         DeluxeChat.setInPm(sender.getName(), player.getName());
/* 135 */         DeluxeChat.setInPm(player.getName(), sender.getName());
/*     */         
/* 137 */         FancyMessage toSender = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToSenderPMFormat());
/*     */         
/* 139 */         if (toSender != null) {
/*     */           
/* 141 */           String toSenderJSON = toSender.toJSONString();
/*     */           
/* 143 */           toSenderJSON = PlaceholderAPI.setPlaceholders(sender, toSenderJSON);
/*     */           
/* 145 */           toSenderJSON = toSenderJSON.replace("%recipient%", player.getName());
/*     */           
/* 147 */           String str1 = this.plugin.getChat().convertPm(sender, toSender.getLastColor() + toSender.getChatColor() + message);
/*     */           
/* 149 */           this.plugin.getChat().sendPrivateMessage(sender, ChatColor.translateAlternateColorCodes('&', toSenderJSON), str1);
/*     */         } 
/*     */         
/* 152 */         FancyMessage toRecipient = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToRecipientPMFormat());
/*     */         
/* 154 */         if (toRecipient != null) {
/*     */           
/* 156 */           String toRecipJSON = toRecipient.toJSONString();
/*     */           
/* 158 */           toRecipJSON = PlaceholderAPI.setPlaceholders(sender, toRecipJSON);
/*     */           
/* 160 */           toRecipJSON = toRecipJSON.replace("%recipient%", player.getName());
/*     */           
/* 162 */           String str1 = this.plugin.getChat().convertPm(sender, toRecipient.getLastColor() + toRecipient.getChatColor() + message);
/*     */           
/* 164 */           this.plugin.getChat().sendPrivateMessage(player, ChatColor.translateAlternateColorCodes('&', toRecipJSON), str1);
/*     */         } 
/*     */         
/* 167 */         String sFormat = DeluxeChat.getSocialSpyFormat();
/*     */         
/* 169 */         if (sFormat == null || sFormat.isEmpty()) {
/* 170 */           return true;
/*     */         }
/*     */         
/* 173 */         sFormat = PlaceholderHandler.setPlaceholders(sender, sFormat);
/*     */         
/* 175 */         sFormat = PlaceholderHandler.setRecipPlaceholders(player, sFormat);
/*     */         
/* 177 */         sFormat = sFormat.replace("%player%", sender.getName()).replace("%recipient%", player.getName());
/*     */         
/* 179 */         for (Player online : Bukkit.getOnlinePlayers()) {
/*     */           
/* 181 */           if (DeluxeChat.inSocialSpy(online)) {
/*     */             
/* 183 */             if (online.getName().equals(sender.getName()) || online.getName().equals(player.getName())) {
/*     */               continue;
/*     */             }
/* 186 */             DeluxeUtil.sms(online, sFormat + message);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 193 */         return true;
/*     */       } 
/*     */       
/* 196 */       FancyMessage format = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToRecipientPMFormat());
/*     */       
/* 198 */       if (format == null) {
/* 199 */         return true;
/*     */       }
/*     */       
/* 202 */       PrivateMessageEvent pme = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, null, message, true);
/*     */       
/* 204 */       Bukkit.getPluginManager().callEvent((Event)pme);
/*     */       
/* 206 */       if (pme.isCancelled()) {
/* 207 */         return true;
/*     */       }
/*     */       
/* 210 */       message = pme.getMessage();
/*     */       
/* 212 */       String toRecipFormat = format.toJSONString();
/*     */       
/* 214 */       toRecipFormat = PlaceholderAPI.setPlaceholders(sender, toRecipFormat);
/*     */       
/* 216 */       String msg = this.plugin.getChat().convertPm(sender, format.getLastColor() + format.getChatColor() + message);
/*     */       
/* 218 */       DeluxeChat.forwardPm(sender, PrivateMessageType.MESSAGE_SEND, args[0], ChatColor.translateAlternateColorCodes('&', toRecipFormat), msg, message);
/*     */ 
/*     */       
/* 221 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 226 */     Player target = Bukkit.getPlayer(args[0]);
/*     */     
/* 228 */     if (target != null) {
/*     */       
/* 230 */       if (target.getName().equals(sender.getName())) {
/* 231 */         DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
/* 232 */         return true;
/*     */       } 
/*     */
/*     */ 
/*     */       
/* 244 */       if (this.plugin.premiumVanish != null && 
/* 245 */         this.plugin.premiumVanish.isVanished(target) && !sender.hasPermission("deluxechat.vanish.bypass")) {
/* 246 */         DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { args[0] }));
/* 247 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 251 */       if (this.plugin.essentials != null) {
/*     */         
/* 253 */         if (this.plugin.essentials.isVanished(sender, target) && !sender.hasPermission("deluxechat.vanish.bypass")) {
/* 254 */           DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { args[0] }));
/*     */ 
/*     */           
/* 257 */           return true;
/*     */         } 
/*     */         
/* 260 */         if (this.plugin.essentials.isIgnored(target, sender) && !sender.hasPermission("deluxechat.ignore.bypass")) {
/* 261 */           DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IGNORING_SENDER.getConfigValue(new String[] { target
/* 262 */                   .getName() }));
/*     */           
/* 264 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 268 */       if (this.plugin.hasPmDisabled(target) && 
/* 269 */         !sender.hasPermission("deluxechat.pm.ignoretoggle")) {
/* 270 */         DeluxeUtil.sms(sender, Lang.PM_TOGGLED_RECIPIENT.getConfigValue(new String[] { target
/* 271 */                 .getName() }));
/*     */         
/* 273 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 277 */       PrivateMessageEvent pme = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, target, message, false);
/*     */       
/* 279 */       Bukkit.getPluginManager().callEvent((Event)pme);
/*     */       
/* 281 */       if (pme.isCancelled()) {
/* 282 */         return true;
/*     */       }
/*     */       
/* 285 */       message = pme.getMessage();
/*     */       
/* 287 */       DeluxeChat.setInPm(sender.getName(), target.getName());
/* 288 */       DeluxeChat.setInPm(target.getName(), sender.getName());
/*     */       
/* 290 */       FancyMessage toSender = this.plugin.getPrivateMessageFormat(DeluxeChat.getToSenderPMFormat());
/*     */       
/* 292 */       if (toSender != null) {
/*     */         
/* 294 */         String toSenderJSON = toSender.toJSONString();
/*     */         
/* 296 */         toSenderJSON = PlaceholderHandler.setRecipPlaceholders(target, toSenderJSON);
/*     */         
/* 298 */         toSenderJSON = PlaceholderAPI.setPlaceholders(sender, toSenderJSON);
/*     */         
/* 300 */         String msg = this.plugin.getChat().convertPm(sender, toSender.getLastColor() + toSender.getChatColor() + message);
/*     */         
/* 302 */         this.plugin.getChat().sendPrivateMessage(sender, ChatColor.translateAlternateColorCodes('&', toSenderJSON), msg);
/*     */       } 
/*     */       
/* 305 */       FancyMessage toRecipient = this.plugin.getPrivateMessageFormat(DeluxeChat.getToRecipientPMFormat());
/*     */       
/* 307 */       if (toRecipient != null) {
/*     */         
/* 309 */         String toRecipJSON = toRecipient.toJSONString();
/*     */         
/* 311 */         toRecipJSON = PlaceholderHandler.setRecipPlaceholders(target, toRecipJSON);
/*     */         
/* 313 */         toRecipJSON = PlaceholderAPI.setPlaceholders(sender, toRecipJSON);
/*     */         
/* 315 */         String msg = this.plugin.getChat().convertPm(sender, toRecipient.getLastColor() + toRecipient.getChatColor() + message);
/*     */         
/* 317 */         this.plugin.getChat().sendPrivateMessage(target, ChatColor.translateAlternateColorCodes('&', toRecipJSON), msg);
/*     */       } 
/*     */       
/* 320 */       String sFormat = DeluxeChat.getSocialSpyFormat();
/*     */       
/* 322 */       if (sFormat == null || sFormat.isEmpty()) {
/* 323 */         return true;
/*     */       }
/*     */       
/* 326 */       sFormat = PlaceholderHandler.setPlaceholders(sender, sFormat);
/*     */       
/* 328 */       sFormat = PlaceholderHandler.setRecipPlaceholders(target, sFormat);
/*     */       
/* 330 */       sFormat = sFormat.replace("%player%", sender.getName()).replace("%recipient%", target.getName());
/*     */       
/* 332 */       for (Player online : Bukkit.getOnlinePlayers()) {
/*     */         
/* 334 */         if (DeluxeChat.inSocialSpy(online)) {
/*     */           
/* 336 */           if (online.getName().equals(sender.getName()) || online.getName().equals(target.getName())) {
/*     */             continue;
/*     */           }
/* 339 */           DeluxeUtil.sms(online, sFormat + message);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 344 */       DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { args[0] }));
/*     */     } 
/*     */ 
/*     */     
/* 348 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\MessageCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */