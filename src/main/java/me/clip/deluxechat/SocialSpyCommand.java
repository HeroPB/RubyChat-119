/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class SocialSpyCommand
/*    */   implements CommandExecutor
/*    */ {
/*    */   DeluxeChat plugin;
/*    */   
/*    */   public SocialSpyCommand(DeluxeChat instance) {
/* 15 */     this.plugin = instance;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/* 21 */     if (!(s instanceof Player)) {
/* 22 */       s.sendMessage("This command is not supported in console yet!");
/* 23 */       return true;
/*    */     } 
/*    */     
/* 26 */     Player p = (Player)s;
/*    */     
/* 28 */     if (DeluxeChat.inSocialSpy(p)) {
/*    */       
/* 30 */       DeluxeChat.disableSocialSpy(p);
/* 31 */       DeluxeUtil.sms(p, Lang.SOCIALSPY_TOGGLE_OFF.getConfigValue(null));
/*    */     }
/*    */     else {
/*    */       
/* 35 */       if (!p.hasPermission("deluxechat.socialspy")) {
/* 36 */         DeluxeUtil.sms(p, Lang.NO_PERMISSION.getConfigValue(new String[] { "deluxechat.socialspy" }));
/*    */ 
/*    */ 
/*    */         
/* 40 */         return true;
/*    */       } 
/*    */       
/* 43 */       DeluxeChat.enableSocialSpy(p);
/* 44 */       DeluxeUtil.sms(p, Lang.SOCIALSPY_TOGGLE_ON.getConfigValue(null));
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\SocialSpyCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */