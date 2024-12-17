/*    */ package me.clip.deluxechat.events;
/*    */ 
/*    */ import java.util.Set;
/*    */ import me.clip.deluxechat.objects.DeluxeFormat;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Cancellable;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class DeluxeChatEvent
/*    */   extends Event
/*    */   implements Cancellable
/*    */ {
/* 14 */   private static final HandlerList handlers = new HandlerList();
/*    */   
/*    */   private final Player player;
/*    */   
/*    */   private Set<Player> recipients;
/*    */   
/*    */   private DeluxeFormat rawFormat;
/*    */   
/*    */   private String chatMessage;
/*    */   
/*    */   private boolean cancelled;
/*    */ 
/*    */   
/*    */   public DeluxeChatEvent(boolean async, Player player, Set<Player> recip, DeluxeFormat rawPlayerFormat, String chatMessage) {
/* 28 */     super(async);
/* 29 */     this.player = player;
/* 30 */     this.recipients = recip;
/* 31 */     this.chatMessage = chatMessage;
/* 32 */     this.rawFormat = rawPlayerFormat;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 36 */     return this.player;
/*    */   }
/*    */   
/*    */   public DeluxeFormat getDeluxeFormat() {
/* 40 */     return this.rawFormat;
/*    */   }
/*    */   
/*    */   public void setDeluxeFormat(DeluxeFormat updatedFormat) {
/* 44 */     this.rawFormat = updatedFormat;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 49 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 53 */     return handlers;
/*    */   }
/*    */   
/*    */   public Set<Player> getRecipients() {
/* 57 */     return this.recipients;
/*    */   }
/*    */   
/*    */   public void setRecipients(Set<Player> recipients) {
/* 61 */     this.recipients = recipients;
/*    */   }
/*    */   
/*    */   public String getChatMessage() {
/* 65 */     return this.chatMessage;
/*    */   }
/*    */   
/*    */   public void setChatMessage(String chatMessage) {
/* 69 */     this.chatMessage = chatMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 74 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 79 */     this.cancelled = cancelled;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\events\DeluxeChatEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */