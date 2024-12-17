/*    */ package me.clip.deluxechat.hooks;
/*    */ 
/*    */ import com.earth2me.essentials.Essentials;
/*    */ import com.earth2me.essentials.IUser;
/*    */ import com.earth2me.essentials.User;
/*    */ import me.clip.deluxechat.DeluxeChat;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class EssentialsHook
/*    */   implements PluginHook
/*    */ {
/*    */   DeluxeChat plugin;
/* 15 */   private static Essentials essentials = null;
/*    */   
/*    */   public EssentialsHook(DeluxeChat i) {
/* 18 */     this.plugin = i;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isIgnored(Player ignoring, Player ignored) {
/* 23 */     if (essentials == null) {
/* 24 */       return false;
/*    */     }
/*    */     
/* 27 */     User one = essentials.getUser(ignoring);
/*    */     
/* 29 */     if (one == null) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     User two = essentials.getUser(ignored);
/*    */     
/* 35 */     if (two == null) {
/* 36 */       return false;
/*    */     }
/*    */     
/* 39 */     return one.isIgnoredPlayer((IUser)two);
/*    */   }
/*    */   
/*    */   public boolean isVanished(Player sender, Player recipient) {
/* 43 */     if (essentials == null) {
/* 44 */       return false;
/*    */     }
/* 46 */     User r = essentials.getUser(recipient);
/*    */     
/* 48 */     if (r == null) {
/* 49 */       return false;
/*    */     }
/* 51 */     User s = essentials.getUser(sender);
/*    */     
/* 53 */     if (s == null) {
/* 54 */       return false;
/*    */     }
/*    */     
/* 57 */     return r.isHidden(sender);
/*    */   }
/*    */   
/*    */   public boolean isMuted(Player p) {
/* 61 */     if (essentials == null) {
/* 62 */       return false;
/*    */     }
/* 64 */     User u = essentials.getUser(p);
/*    */     
/* 66 */     if (u == null) {
/* 67 */       return false;
/*    */     }
/*    */     
/* 70 */     return u.isMuted();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hook() {
/* 75 */     essentials = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
/* 76 */     return (essentials != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\hooks\EssentialsHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */