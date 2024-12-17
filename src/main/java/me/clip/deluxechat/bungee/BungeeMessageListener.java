/*     */ package me.clip.deluxechat.bungee;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import me.clip.deluxechat.DeluxeChat;
/*     */ import me.clip.deluxechat.DeluxeUtil;
/*     */ import me.clip.deluxechat.Lang;
/*     */ import me.clip.deluxechat.fanciful.FancyMessage;
/*     */ import me.clip.deluxechat.messaging.PrivateMessageType;
/*     */ import me.clip.deluxechat.placeholders.PlaceholderHandler;
/*     */ import me.clip.placeholderapi.PlaceholderAPI;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.messaging.PluginMessageListener;
/*     */ 
/*     */ 
/*     */ public class BungeeMessageListener
/*     */   implements PluginMessageListener
/*     */ {
/*     */   DeluxeChat plugin;
/*     */   
/*     */   public BungeeMessageListener(DeluxeChat i) {
/*  24 */     this.plugin = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
/*  31 */     if (channel.equals(PMChan.CHAT.getChannelName())) {
/*     */       
/*  33 */       ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
/*     */       
/*  35 */       String format = in.readUTF();
/*     */       
/*  37 */       String message = in.readUTF();
/*     */       
/*  39 */       boolean override = in.readBoolean();
/*     */       
/*  41 */       String server = in.readUTF();
/*     */       
/*  43 */       if (DeluxeChat.useServerWhitelist() && !DeluxeChat.getServerWhitelist().contains(server)) {
/*     */         return;
/*     */       }
/*     */       
/*  47 */       this.plugin.getChat().sendBungeeChat(format, message, override);
/*     */     } else {
/*  49 */       if (channel.equals(PMChan.PM.getChannelName())) {
/*     */         
/*  51 */         ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
/*     */         
/*  53 */         String t = in.readUTF();
/*     */         
/*  55 */         PrivateMessageType type = PrivateMessageType.fromName(t);
/*     */         
/*  57 */         if (type == null) {
/*     */           return;
/*     */         }
/*     */         
/*  61 */         String sender = in.readUTF();
/*     */         
/*  63 */         String recipient = in.readUTF();
/*     */         
/*  65 */         String format = in.readUTF();
/*     */         
/*  67 */         String jsonMessage = in.readUTF();
/*     */         
/*  69 */         String rawMessage = in.readUTF();
/*     */         
/*  71 */         if (type == PrivateMessageType.MESSAGE_SENT_FAIL) {
/*     */           
/*  73 */           Player send = Bukkit.getPlayer(sender);
/*     */           
/*  75 */           if (send == null)
/*     */             return; 
/*  77 */           DeluxeUtil.sms(send, Lang.MSG_RECIPIENT_NOT_ONLINE.getConfigValue(new String[] { recipient }));
/*     */ 
/*     */ 
/*     */           
/*  81 */           DeluxeChat.removeFromPM(send.getName());
/*     */         }
/*  83 */         else if (type == PrivateMessageType.MESSAGE_TO_RECIPIENT) {
/*     */           
/*  85 */           Player recip = Bukkit.getPlayer(recipient);
/*     */           
/*  87 */           if (recip == null)
/*     */             return; 
/*  89 */           if (this.plugin.hasPmDisabled(recip)) {
/*     */             return;
/*     */           }
/*     */           
/*  93 */           format = PlaceholderHandler.setRecipPlaceholders(recip, format);
/*     */           
/*  95 */           format = format.replace("%recipient%", recip.getName()).replace("%player%", sender);
/*     */           
/*  97 */           this.plugin.getChat().sendPrivateMessage(recip, format, jsonMessage);
/*     */           
/*  99 */           DeluxeChat.setInPm(recip.getName(), sender);
/*     */         }
/* 101 */         else if (type == PrivateMessageType.MESSAGE_SENT_SUCCESS) {
/*     */           
/* 103 */           Player send = Bukkit.getPlayer(sender);
/*     */           
/* 105 */           if (send == null)
/*     */             return; 
/* 107 */           FancyMessage toSender = this.plugin.getBungeePrivateMessageFormat(send, DeluxeChat.getToSenderPMFormat());
/*     */           
/* 109 */           String toSenderJSON = toSender.toJSONString();
/*     */           
/* 111 */           toSenderJSON = PlaceholderAPI.setPlaceholders(send, toSenderJSON);
/*     */           
/* 113 */           toSenderJSON = toSenderJSON.replace("%recipient%", recipient).replace("%player%", sender);
/*     */           
/* 115 */           String json = this.plugin.getChat().convertPm(send, toSender.getLastColor() + toSender.getChatColor() + rawMessage);
/*     */           
/* 117 */           this.plugin.getChat().sendPrivateMessage(send, ChatColor.translateAlternateColorCodes('&', toSenderJSON), json);
/*     */           
/* 119 */           DeluxeChat.setInPm(send.getName(), recipient);
/*     */         } 
/*     */         return;
/*     */       } 
/* 123 */       if (channel.equals(PMChan.SPY.getChannelName())) {
/*     */         
/* 125 */         String format = DeluxeChat.getSocialSpyFormat();
/*     */         
/* 127 */         if (format == null || format.isEmpty()) {
/*     */           return;
/*     */         }
/*     */         
/* 131 */         ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
/*     */         
/* 133 */         String sender = in.readUTF();
/* 134 */         String recipient = in.readUTF();
/* 135 */         String message = in.readUTF();
/*     */         
/* 137 */         format = format.replace("%player%", sender).replace("%recipient%", recipient);
/*     */         
/* 139 */         for (Player online : Bukkit.getOnlinePlayers()) {
/*     */           
/* 141 */           if (DeluxeChat.inSocialSpy(online)) {
/*     */             
/* 143 */             if (online.getName().equals(sender) || online.getName().equals(recipient)) {
/*     */               continue;
/*     */             }
/* 146 */             DeluxeUtil.sms(online, format + message);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\bungee\BungeeMessageListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */