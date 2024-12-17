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
/*     */ public class ReplyCommand
/*     */   implements CommandExecutor
/*     */ {
/*     */   DeluxeChat plugin;
/*     */   
/*     */   public ReplyCommand(DeluxeChat instance) {
/*  24 */     this.plugin = instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/*  31 */     if (!(s instanceof Player))
/*     */     {
/*  33 */       return true;
/*     */     }
/*     */     
/*  36 */     Player sender = (Player)s;
/*     */     
/*  38 */     if (!sender.hasPermission("deluxechat.pm")) {
/*  39 */       DeluxeUtil.sms(sender, Lang.NO_PERMISSION.getConfigValue(new String[] { "deluxechat.pm" }));
/*     */ 
/*     */       
/*  42 */       return true;
/*     */     } 
/*     */     
/*  45 */     if (this.plugin.hasPmDisabled(sender)) {
/*  46 */       DeluxeUtil.sms(sender, Lang.PM_TOGGLED_SENDER.getConfigValue(null));
/*  47 */       return true;
/*     */     } 
/*     */     
/*  50 */     if (DeluxeChat.getPmRecipient(sender.getName()) == null) {
/*  51 */       DeluxeUtil.sms(sender, Lang.REPLY_NO_RECIPIENT.getConfigValue(null));
/*  52 */       return true;
/*     */     } 
/*     */     
/*  55 */     if (args.length < 1) {
/*  56 */       DeluxeUtil.sms(sender, Lang.REPLY_INCORRECT_USAGE.getConfigValue(null));
/*     */       
/*  58 */       return true;
/*     */     } 
/*     */     
/*  61 */     if (this.plugin.isMuted(sender)) {
/*  62 */       return true;
/*     */     }
/*     */     
/*  65 */     String recipient = DeluxeChat.getPmRecipient(sender.getName());
/*     */     
/*  67 */     String message = StringUtils.join(Arrays.copyOfRange((Object[])args, 0, args.length), " ");
/*     */     
/*  69 */     message = DeluxeUtil.checkColor(sender, message, true);
/*     */     
/*  71 */     if (message.isEmpty()) {
/*  72 */       return true;
/*     */     }
/*     */     
/*  75 */     if (DeluxeChat.bungeePMEnabled()) {
/*     */       
/*  77 */       Player player = Bukkit.getPlayer(recipient);
/*     */       
/*  79 */       if (player != null) {
/*     */         
/*  81 */         if (this.plugin.essentials != null)
/*     */         {
/*  83 */           if (this.plugin.essentials.isIgnored(player, sender) && !sender.hasPermission("deluxechat.ignore.bypass")) {
/*     */             
/*  85 */             DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IGNORING_SENDER
/*     */                 
/*  87 */                 .getConfigValue(new String[] { recipient }));
/*  88 */             DeluxeChat.removeFromPM(sender.getName());
/*  89 */             return true;
/*     */           } 
/*     */         }
/*     */         
/*  93 */         if (this.plugin.hasPmDisabled(player) && 
/*  94 */           !sender.hasPermission("deluxechat.pm.ignoretoggle")) {
/*  95 */           DeluxeUtil.sms(sender, Lang.PM_TOGGLED_RECIPIENT.getConfigValue(new String[] { player
/*  96 */                   .getName() }));
/*     */           
/*  98 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 102 */         PrivateMessageEvent privateMessageEvent = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, player, message, false);
/*     */         
/* 104 */         Bukkit.getPluginManager().callEvent((Event)privateMessageEvent);
/*     */         
/* 106 */         if (privateMessageEvent.isCancelled()) {
/* 107 */           return true;
/*     */         }
/*     */         
/* 110 */         message = privateMessageEvent.getMessage();
/*     */         
/* 112 */         DeluxeChat.setInPm(sender.getName(), player.getName());
/* 113 */         DeluxeChat.setInPm(player.getName(), sender.getName());
/*     */         
/* 115 */         FancyMessage toSender = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToSenderPMFormat());
/*     */         
/* 117 */         if (toSender != null) {
/*     */           
/* 119 */           String toSenderJSON = toSender.toJSONString();
/*     */           
/* 121 */           toSenderJSON = PlaceholderAPI.setPlaceholders(sender, toSenderJSON);
/*     */           
/* 123 */           toSenderJSON = toSenderJSON.replace("%recipient%", player.getName());
/*     */           
/* 125 */           String str1 = this.plugin.getChat().convertPm(sender, toSender.getLastColor() + toSender.getChatColor() + message);
/*     */           
/* 127 */           this.plugin.getChat().sendPrivateMessage(sender, ChatColor.translateAlternateColorCodes('&', toSenderJSON), str1);
/*     */         } 
/*     */         
/* 130 */         FancyMessage toRecipient = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToRecipientPMFormat());
/*     */         
/* 132 */         if (toRecipient != null) {
/*     */           
/* 134 */           String toRecipJSON = toRecipient.toJSONString();
/*     */           
/* 136 */           toRecipJSON = PlaceholderAPI.setPlaceholders(sender, toRecipJSON);
/*     */           
/* 138 */           toRecipJSON = toRecipJSON.replace("%recipient%", player.getName());
/*     */           
/* 140 */           String str1 = this.plugin.getChat().convertPm(sender, toRecipient.getLastColor() + toRecipient.getChatColor() + message);
/*     */           
/* 142 */           this.plugin.getChat().sendPrivateMessage(player, ChatColor.translateAlternateColorCodes('&', toRecipJSON), str1);
/*     */         } 
/*     */         
/* 145 */         String sFormat = DeluxeChat.getSocialSpyFormat();
/*     */         
/* 147 */         if (sFormat == null || sFormat.isEmpty()) {
/* 148 */           return true;
/*     */         }
/*     */         
/* 151 */         sFormat = PlaceholderHandler.setPlaceholders(sender, sFormat);
/*     */         
/* 153 */         sFormat = PlaceholderHandler.setRecipPlaceholders(player, sFormat);
/*     */         
/* 155 */         sFormat = sFormat.replace("%player%", sender.getName()).replace("%recipient%", player.getName());
/*     */         
/* 157 */         for (Player online : Bukkit.getOnlinePlayers()) {
/*     */           
/* 159 */           if (DeluxeChat.inSocialSpy(online)) {
/*     */             
/* 161 */             if (online.getName().equals(sender.getName()) || online.getName().equals(player.getName())) {
/*     */               continue;
/*     */             }
/* 164 */             DeluxeUtil.sms(online, sFormat + message);
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 171 */         return true;
/*     */       } 
/*     */       
/* 174 */       FancyMessage format = this.plugin.getBungeePrivateMessageFormat(sender, DeluxeChat.getToRecipientPMFormat());
/*     */       
/* 176 */       if (format == null) {
/* 177 */         return true;
/*     */       }
/*     */       
/* 180 */       PrivateMessageEvent pme = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, null, message, true);
/*     */       
/* 182 */       Bukkit.getPluginManager().callEvent((Event)pme);
/*     */       
/* 184 */       if (pme.isCancelled()) {
/* 185 */         return true;
/*     */       }
/*     */       
/* 188 */       message = pme.getMessage();
/*     */       
/* 190 */       String toRecipFormat = format.toJSONString().replace("%player%", sender.getName());
/*     */       
/* 192 */       toRecipFormat = PlaceholderAPI.setPlaceholders(sender, toRecipFormat);
/*     */       
/* 194 */       String msg = this.plugin.getChat().convertPm(sender, format.getLastColor() + format.getChatColor() + message);
/*     */       
/* 196 */       DeluxeChat.forwardPm(sender, PrivateMessageType.MESSAGE_SEND, recipient, ChatColor.translateAlternateColorCodes('&', toRecipFormat), msg, message);
/*     */ 
/*     */       
/* 199 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 204 */     Player target = Bukkit.getPlayer(recipient);
/*     */     
/* 206 */     if (target != null) {
/*     */       
/* 208 */       if (target.getName().equals(sender.getName())) {
/* 209 */         DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IS_SENDER.getConfigValue(null));
/* 210 */         return true;
/*     */       } 
/*     */       
/* 213 */       if (this.plugin.essentials != null)
/*     */       {
/* 215 */         if (this.plugin.essentials.isIgnored(target, sender) && !sender.hasPermission("deluxechat.ignore.bypass")) {
/* 216 */           DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_IGNORING_SENDER.getConfigValue(new String[] { target
/* 217 */                   .getName() }));
/*     */           
/* 219 */           DeluxeChat.removeFromPM(sender.getName());
/* 220 */           return true;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 225 */       if (this.plugin.hasPmDisabled(target) && 
/* 226 */         !sender.hasPermission("deluxechat.pm.ignoretoggle")) {
/* 227 */         DeluxeUtil.sms(sender, Lang.PM_TOGGLED_RECIPIENT.getConfigValue(new String[] { target
/* 228 */                 .getName() }));
/*     */         
/* 230 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 234 */       PrivateMessageEvent pme = new PrivateMessageEvent(!Bukkit.isPrimaryThread(), sender, target, message, false);
/*     */       
/* 236 */       Bukkit.getPluginManager().callEvent((Event)pme);
/*     */       
/* 238 */       if (pme.isCancelled()) {
/* 239 */         return true;
/*     */       }
/*     */       
/* 242 */       message = pme.getMessage();
/*     */ 
/*     */       
/* 245 */       DeluxeChat.setInPm(sender.getName(), target.getName());
/* 246 */       DeluxeChat.setInPm(target.getName(), sender.getName());
/*     */       
/* 248 */       FancyMessage toSender = this.plugin.getPrivateMessageFormat(DeluxeChat.getToSenderPMFormat());
/*     */       
/* 250 */       if (toSender != null) {
/*     */         
/* 252 */         String toSenderJSON = toSender.toJSONString();
/*     */         
/* 254 */         toSenderJSON = PlaceholderHandler.setRecipPlaceholders(target, toSenderJSON);
/*     */         
/* 256 */         toSenderJSON = PlaceholderAPI.setPlaceholders(sender, toSenderJSON);
/*     */ 
/*     */         
/* 259 */         String msg = this.plugin.getChat().convertPm(sender, toSender.getLastColor() + toSender.getChatColor() + message);
/*     */         
/* 261 */         this.plugin.getChat().sendPrivateMessage(sender, ChatColor.translateAlternateColorCodes('&', toSenderJSON), msg);
/*     */       } 
/*     */       
/* 264 */       FancyMessage toRecipient = this.plugin.getPrivateMessageFormat(DeluxeChat.getToRecipientPMFormat());
/*     */       
/* 266 */       if (toRecipient != null) {
/*     */         
/* 268 */         String toRecipJSON = toRecipient.toJSONString();
/*     */         
/* 270 */         toRecipJSON = PlaceholderHandler.setRecipPlaceholders(target, toRecipJSON);
/*     */         
/* 272 */         toRecipJSON = PlaceholderAPI.setPlaceholders(sender, toRecipJSON);
/*     */         
/* 274 */         String msg = this.plugin.getChat().convertPm(sender, toRecipient.getLastColor() + toRecipient.getChatColor() + message);
/*     */         
/* 276 */         this.plugin.getChat().sendPrivateMessage(target, ChatColor.translateAlternateColorCodes('&', toRecipJSON), msg);
/*     */       } 
/*     */       
/* 279 */       String sFormat = DeluxeChat.getSocialSpyFormat();
/*     */       
/* 281 */       if (sFormat == null || sFormat.isEmpty()) {
/* 282 */         return true;
/*     */       }
/*     */       
/* 285 */       sFormat = PlaceholderHandler.setPlaceholders(sender, sFormat);
/*     */       
/* 287 */       sFormat = PlaceholderHandler.setRecipPlaceholders(target, sFormat);
/*     */       
/* 289 */       sFormat = sFormat.replace("%player%", sender.getName()).replace("%recipient%", target.getName());
/*     */       
/* 291 */       for (Player online : Bukkit.getOnlinePlayers()) {
/*     */         
/* 293 */         if (DeluxeChat.inSocialSpy(online)) {
/*     */           
/* 295 */           if (online.getName().equals(sender.getName()) || online.getName().equals(target.getName())) {
/*     */             continue;
/*     */           }
/* 298 */           DeluxeUtil.sms(online, sFormat + message);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 303 */       DeluxeUtil.sms(sender, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { recipient }));
/*     */ 
/*     */       
/* 306 */       DeluxeChat.removeFromPM(sender.getName());
/*     */     } 
/* 308 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\ReplyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */