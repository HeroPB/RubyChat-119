/*    */ package me.clip.deluxechat.events;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class ChatToPlayerEvent
/*    */   extends Event {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final Player player;
/*    */   
/*    */   private Player recipient;
/*    */   
/*    */   private String jsonFormat;
/*    */   
/*    */   private String chatMessage;
/*    */   
/*    */   private boolean bungeeMessage;
/*    */   
/*    */   public ChatToPlayerEvent(boolean async, Player player, Player recipient, String jsonFormat, String chatMessage, boolean isBungeeMessage) {
/* 22 */     super(async);
/* 23 */     this.player = player;
/* 24 */     this.recipient = recipient;
/* 25 */     this.chatMessage = chatMessage;
/* 26 */     this.jsonFormat = jsonFormat;
/* 27 */     this.bungeeMessage = isBungeeMessage;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 31 */     return this.player;
/*    */   }
/*    */   
/*    */   public String getJSONFormat() {
/* 35 */     return this.jsonFormat;
/*    */   }
/*    */   
/*    */   public void setJSONFormat(String jsonFormat) {
/* 39 */     this.jsonFormat = jsonFormat;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 44 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 48 */     return handlers;
/*    */   }
/*    */   
/*    */   public Player getRecipient() {
/* 52 */     return this.recipient;
/*    */   }
/*    */   
/*    */   public String getChatMessage() {
/* 56 */     return this.chatMessage;
/*    */   }
/*    */   
/*    */   public void setChatMessage(String chatMessage) {
/* 60 */     this.chatMessage = chatMessage;
/*    */   }
/*    */   
/*    */   public boolean isBungeeMessage() {
/* 64 */     return this.bungeeMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\events\ChatToPlayerEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */