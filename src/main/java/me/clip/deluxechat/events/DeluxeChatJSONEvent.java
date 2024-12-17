/*    */ package me.clip.deluxechat.events;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class DeluxeChatJSONEvent
/*    */   extends Event implements Cancellable {
/* 10 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final Player player;
/*    */   
/*    */   private String jsonFormat;
/*    */   
/*    */   private String jsonChatMessage;
/*    */   
/*    */   private String rawChatMessage;
/*    */   
/*    */   private boolean cancelled;
/*    */   
/*    */   public DeluxeChatJSONEvent(boolean async, Player player, String jsonFormat, String jsonChatMessage, String rawChatMessage) {
/* 23 */     super(async);
/* 24 */     this.player = player;
/* 25 */     this.jsonChatMessage = jsonChatMessage;
/* 26 */     this.rawChatMessage = rawChatMessage;
/* 27 */     this.jsonFormat = jsonFormat;
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
/*    */   public void setJSONFormat(String updatedJSONFormat) {
/* 39 */     this.jsonFormat = updatedJSONFormat;
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
/*    */   public String getJSONChatMessage() {
/* 52 */     return this.jsonChatMessage;
/*    */   }
/*    */   
/*    */   public void setJSONChatMessage(String updatedJSONChatMessage) {
/* 56 */     this.jsonChatMessage = updatedJSONChatMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 61 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 66 */     this.cancelled = cancelled;
/*    */   }
/*    */   
/*    */   public String getRawChatMessage() {
/* 70 */     return this.rawChatMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\events\DeluxeChatJSONEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */