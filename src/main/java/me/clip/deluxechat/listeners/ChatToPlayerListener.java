/*    */ package me.clip.deluxechat.listeners;
/*    */ 
/*    */ import me.clip.deluxechat.events.ChatToPlayerEvent;
/*    */ import me.clip.placeholderapi.PlaceholderAPI;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatToPlayerListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
/*    */   public void onChhat(ChatToPlayerEvent e) {
/* 18 */     e.setJSONFormat(PlaceholderAPI.setRelationalPlaceholders(e.getPlayer(), e.getRecipient(), e.getJSONFormat()));
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\listeners\ChatToPlayerListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */