/*     */ package me.clip.deluxechat.listeners;
/*     */ 
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import me.clip.deluxechat.DeluxeChat;
/*     */ import me.clip.deluxechat.DeluxeUtil;
/*     */ import me.clip.deluxechat.Lang;
/*     */ import me.clip.deluxechat.events.ChatToPlayerEvent;
/*     */ import me.clip.deluxechat.events.DeluxeChatEvent;
/*     */ import me.clip.deluxechat.events.DeluxeChatJSONEvent;
/*     */ import me.clip.deluxechat.fanciful.FancyMessage;
/*     */ import me.clip.deluxechat.objects.DeluxeFormat;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncPlayerChatListener
/*     */   implements Listener
/*     */ {
/*     */   private DeluxeChat plugin;
/*  31 */   private final Cache<UUID, String> msgCache = CacheBuilder.newBuilder()
/*  32 */     .maximumSize(50L)
/*  33 */     .expireAfterWrite(500L, TimeUnit.MILLISECONDS)
/*  34 */     .build();
/*     */   
/*     */   public AsyncPlayerChatListener(DeluxeChat i) {
/*  37 */     this.plugin = i;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
/*     */   public void onChhat(AsyncPlayerChatEvent e) {
/*  43 */     Player p = e.getPlayer();
/*     */     
/*  45 */     if (this.plugin.isMuted(p)) {
/*  46 */       e.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*  50 */     String chatMessage = e.getMessage();
/*     */     
/*  52 */     if (DeluxeUtil.containsInvalidChars(chatMessage) && 
/*  53 */       !p.hasPermission("deluxechat.utf")) {
/*  54 */       DeluxeUtil.sms(p, Lang.CHAT_ILLEGAL_CHARACTERS.getConfigValue(null));
/*  55 */       e.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*  59 */     if (!p.hasPermission("deluxechat.filter.bypass")) {
/*  60 */       chatMessage = DeluxeUtil.removeBlacklisted(chatMessage);
/*     */     }
/*     */     
/*  63 */     chatMessage = DeluxeUtil.checkColor(p, chatMessage, false);
/*     */     
/*  65 */     e.setMessage(chatMessage);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
/*     */   public void onChat(AsyncPlayerChatEvent e) {
/*  71 */     if (DeluxeChat.getFormats() == null || DeluxeChat.getFormats().isEmpty()) {
/*     */       return;
/*     */     }
/*  74 */     Player p = e.getPlayer();
/*     */     
/*  76 */     String chatMessage = e.getMessage();
/*     */     
/*  78 */     chatMessage = ChatColor.translateAlternateColorCodes('&', chatMessage);
/*     */     
/*  80 */     if (this.msgCache.asMap().containsKey(p.getUniqueId()) && ((String)this.msgCache.asMap().get(p.getUniqueId())).equals(chatMessage)) {
/*  81 */       e.setCancelled(true);
/*     */       
/*     */       return;
/*     */     } 
/*  85 */     DeluxeFormat playerFormat = DeluxeFormat.newInstance(this.plugin.getPlayerFormat(p));
/*     */     
/*  87 */     DeluxeChatEvent deluxeEvent = new DeluxeChatEvent(true, p, e.getRecipients(), playerFormat, chatMessage);
/*     */     
/*  89 */     if (e.isAsynchronous()) {
/*  90 */       Bukkit.getPluginManager().callEvent((Event)deluxeEvent);
/*     */       
/*  92 */       if (deluxeEvent.isCancelled()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  98 */     playerFormat = deluxeEvent.getDeluxeFormat();
/*     */     
/* 100 */     FancyMessage fm = this.plugin.getFancyChatFormat(p, playerFormat);
/*     */     
/* 102 */     if (fm == null) {
/* 103 */       this.plugin.getLog().severe("There was an error getting the chat format for player" + p.getName());
/*     */       
/*     */       return;
/*     */     } 
/* 107 */     Set<Player> deluxeRecipients = deluxeEvent.getRecipients();
/*     */     
/* 109 */     if (deluxeRecipients == null) {
/* 110 */       fm = null;
/*     */       
/*     */       return;
/*     */     } 
/* 114 */     String msg = fm.getLastColor() + fm.getChatColor() + deluxeEvent.getChatMessage();
/*     */     
/* 116 */     boolean relation = DeluxeChat.useRelationPlaceholders();
/*     */     
/* 118 */     String jsonMessage = this.plugin.getChat().convertMsg(p, msg);
/*     */     
/* 120 */     this.msgCache.put(p.getUniqueId(), chatMessage);
/*     */     
/* 122 */     if (relation) {
/*     */       
/* 124 */       DeluxeChatJSONEvent jsonEvent = new DeluxeChatJSONEvent(!Bukkit.isPrimaryThread(), p, fm.toJSONString(), jsonMessage, msg);
/* 125 */       if (e.isAsynchronous()) {
/* 126 */         Bukkit.getPluginManager().callEvent((Event)jsonEvent);
/*     */       }
/*     */       
/* 129 */       if (jsonEvent.getJSONFormat() == null || jsonEvent
/* 130 */         .getJSONChatMessage() == null || jsonEvent
/* 131 */         .getJSONFormat().isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 135 */       String format = jsonEvent.getJSONFormat().replace("%server%", "");
/* 136 */       format = this.plugin.getChat().setHexColors(format);
/*     */       
/* 138 */       for (Player recipient : deluxeRecipients)
/*     */       {
/* 140 */         this.plugin.getChat().sendDirectChat(p, format, jsonEvent.getJSONChatMessage(), p, recipient);
/*     */       }
/*     */     } else {
/*     */       
/* 144 */       String format = fm.toJSONString().replace("%server%", "");
/* 145 */       format = this.plugin.getChat().setHexColors(format);
/* 146 */       this.plugin.getChat().sendDeluxeChat(p, format, jsonMessage, deluxeRecipients);
/*     */     } 
/*     */     
/* 149 */     deluxeRecipients.clear();
/* 150 */     e.getRecipients().clear();
/*     */     
/* 152 */     if (DeluxeChat.useBungee())
/*     */     {
/* 154 */       if (!DeluxeChat.isLocal(p.getUniqueId().toString()) && p.hasPermission("deluxechat.bungee.chat")) {
/*     */         
/* 156 */         boolean override = p.hasPermission("deluxechat.bungee.override");
/*     */         
/* 158 */         if (relation) {
/*     */           
/* 160 */           ChatToPlayerEvent event = new ChatToPlayerEvent(!Bukkit.isPrimaryThread(), p, p, fm.toJSONString(), jsonMessage, true);
/* 161 */           if (e.isAsynchronous()) {
/* 162 */             Bukkit.getPluginManager().callEvent((Event)event);
/*     */           }
/*     */           
/* 165 */           if (event.getJSONFormat() == null || event
/* 166 */             .getChatMessage() == null || event
/* 167 */             .getJSONFormat().isEmpty() || event
/* 168 */             .getChatMessage().isEmpty()) {
/*     */             return;
/*     */           }
/*     */           
/* 172 */           String format = event.getJSONFormat().replace("%server%", DeluxeChat.getServerPrefix());
/*     */           
/* 174 */           DeluxeChat.forwardString(p, format, event.getChatMessage(), override, DeluxeChat.getServerName());
/*     */         } else {
/*     */           
/* 177 */           String format = fm.toJSONString().replace("%server%", DeluxeChat.getServerPrefix());
/*     */           
/* 179 */           DeluxeChat.forwardString(p, format, jsonMessage, override, DeluxeChat.getServerName());
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\listeners\AsyncPlayerChatListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */