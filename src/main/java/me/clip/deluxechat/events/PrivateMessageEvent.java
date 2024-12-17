/*    */ package me.clip.deluxechat.events;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrivateMessageEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final Player sender;
/*    */   
/*    */   private Player recipient;
/*    */   
/*    */   private String message;
/*    */   
/*    */   private boolean bungeeMessage;
/*    */   
/*    */   private boolean cancelled;
/*    */   
/*    */   public PrivateMessageEvent(boolean async, Player sender, Player recipient, String message, boolean isBungeeMessage) {
/* 27 */     super(async);
/* 28 */     this.sender = sender;
/* 29 */     this.recipient = recipient;
/* 30 */     this.message = message;
/* 31 */     this.bungeeMessage = isBungeeMessage;
/*    */   }
/*    */   
/*    */   public Player getSender() {
/* 35 */     return this.sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */   
/*    */   public Player getRecipient() {
/* 48 */     return this.recipient;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 52 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 56 */     this.message = message;
/*    */   }
/*    */   
/*    */   public boolean isBungeeMessage() {
/* 60 */     return this.bungeeMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 65 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 70 */     this.cancelled = cancelled;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\events\PrivateMessageEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */