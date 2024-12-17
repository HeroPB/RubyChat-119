/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PmToggleCommand
/*    */   implements CommandExecutor {
/*    */   DeluxeChat plugin;
/*    */   
/*    */   public PmToggleCommand(DeluxeChat i) {
/* 13 */     this.plugin = i;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/* 18 */     if (!(s instanceof Player)) {
/* 19 */       return true;
/*    */     }
/*    */     
/* 22 */     Player p = (Player)s;
/*    */     
/* 24 */     if (!p.hasPermission("deluxechat.pm.toggle")) {
/* 25 */       DeluxeUtil.sms(s, Lang.NO_PERMISSION.getConfigValue(new String[] { "deluxechat.pm.toggle" }));
/*    */ 
/*    */       
/* 28 */       return true;
/*    */     } 
/*    */     
/* 31 */     if (this.plugin.hasPmDisabled(p)) {
/* 32 */       this.plugin.enablePm(p);
/* 33 */       DeluxeUtil.sms(s, Lang.PM_TOGGLE_ON.getConfigValue(null));
/*    */     } else {
/*    */       
/* 36 */       this.plugin.disablePm(p);
/* 37 */       DeluxeUtil.sms(s, Lang.PM_TOGGLE_OFF.getConfigValue(null));
/*    */     } 
/*    */     
/* 40 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\PmToggleCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */