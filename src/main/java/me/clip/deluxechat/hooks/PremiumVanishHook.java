/*    */ package me.clip.deluxechat.hooks;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.metadata.MetadataValue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PremiumVanishHook
/*    */   implements PluginHook
/*    */ {
/*    */   public boolean hook() {
/* 13 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHooked() {
/* 18 */     return true;
/*    */   }
/*    */   
/*    */   public boolean isVanished(Player player) {
/* 22 */     for (MetadataValue meta : player.getMetadata("vanished")) {
/* 23 */       if (meta.asBoolean()) {
/* 24 */         return true;
/*    */       }
/*    */     } 
/* 27 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\hooks\PremiumVanishHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */