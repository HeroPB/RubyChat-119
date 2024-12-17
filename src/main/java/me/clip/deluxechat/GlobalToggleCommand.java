/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class GlobalToggleCommand
/*    */   implements CommandExecutor {
/*    */   DeluxeChat plugin;
/*    */   
/*    */   public GlobalToggleCommand(DeluxeChat i) {
/* 13 */     this.plugin = i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/* 19 */     if (!(s instanceof Player)) {
/* 20 */       return true;
/*    */     }
/*    */     
/* 23 */     if (!DeluxeChat.useBungee()) {
/* 24 */       return true;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 29 */     Player p = (Player)s;
/*    */     
/* 31 */     if (!p.hasPermission("deluxechat.bungee.toggle")) {
/* 32 */       DeluxeUtil.sms(s, Lang.NO_PERMISSION.getConfigValue(new String[] { "deluxechat.bungee.toggle" }));
/*    */ 
/*    */       
/* 35 */       return true;
/*    */     } 
/*    */     
/* 38 */     String uuid = p.getUniqueId().toString();
/*    */     
/* 40 */     if (DeluxeChat.isLocal(uuid)) {
/* 41 */       this.plugin.setGlobal(uuid);
/* 42 */       DeluxeUtil.sms(s, Lang.BUNGEE_GLOBAL_TOGGLE_ON.getConfigValue(null));
/*    */     } else {
/*    */       
/* 45 */       this.plugin.setLocal(uuid);
/* 46 */       DeluxeUtil.sms(s, Lang.BUNGEE_GLOBAL_TOGGLE_OFF.getConfigValue(null));
/*    */     } 
/*    */     
/* 49 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\GlobalToggleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */