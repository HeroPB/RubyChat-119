/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public enum Lang
/*    */ {
/*  8 */   NO_PERMISSION("no_permission", "&cYou don't have permission to do that!"),
/*  9 */   MSG_INCORRECT_USAGE("msg_incorrect_usage", "&cIncorrect usage! &7/msg <player> <message>"),
/* 10 */   MSG_RECIPIENT_NOT_ONLINE("msg_recipient_not_online", "&c{0} &cis not online!"),
/* 11 */   MSG_RECIPIENT_IGNORING_SENDER("msg_recipient_ignoring_sender", "&c{0} &cdoes not wish to speak to you!"),
/* 12 */   MSG_RECIPIENT_IS_SENDER("msg_recipient_is_sender", "&cYou can't message yourself!"),
/* 13 */   REPLY_INCORRECT_USAGE("reply_incorrect_usage", "&cIncorrect usage! &7/r <message>"),
/* 14 */   REPLY_NO_RECIPIENT("reply_no_recipient", "&cYou don't have a recipient to reply to!"),
/* 15 */   SOCIALSPY_TOGGLE_ON("socialspy_toggle_on", "&aSocialspy toggled on!"),
/* 16 */   SOCIALSPY_TOGGLE_OFF("socialspy_toggle_off", "&7Socialspy toggled off!"),
/* 17 */   BUNGEE_GLOBAL_TOGGLE_ON("bungee_global_toggle_on", "&aChat set to global"),
/* 18 */   BUNGEE_GLOBAL_TOGGLE_OFF("bungee_global_toggle_off", "&aChat set to local"),
/* 19 */   PM_TOGGLE_ON("pm_toggle_on", "&aPrivate messaging toggled on."),
/* 20 */   PM_TOGGLE_OFF("pm_toggle_off", "&7Private messaging toggled off."),
/* 21 */   PM_TOGGLED_SENDER("pm_toggled_sender", "&7You can not send pm's with private messages turned off."),
/* 22 */   PM_TOGGLED_RECIPIENT("pm_toggled_recipient", "{0} &7has private messages turned off."),
/* 23 */   URL_INCORRECT_USAGE("url_incorrect_usage", "Hover for url command usage info!"),
/* 24 */   URL_INCORRECT_USAGE_TOOLTIP_1("url_incorrect_usage_tooltip_1", "&7/url <link>"),
/* 25 */   URL_INCORRECT_USAGE_TOOLTIP_2("url_incorrect_usage_tooltip_2", "&7/url <link> <message>"),
/* 26 */   CHAT_ILLEGAL_CHARACTERS("chat_illegal_characters", "You can't use special characters in chat!");
/*    */   
/*    */   private String path;
/*    */   
/*    */   private String def;
/*    */   
/*    */   private static FileConfiguration LANG;
/*    */   
/*    */   Lang(String path, String start) {
/* 35 */     this.path = path;
/* 36 */     this.def = start;
/*    */   }
/*    */   
/*    */   public static void setFile(FileConfiguration config) {
/* 40 */     LANG = config;
/*    */   }
/*    */   
/*    */   public String getDefault() {
/* 44 */     return this.def;
/*    */   }
/*    */   
/*    */   public String getPath() {
/* 48 */     return this.path;
/*    */   }
/*    */   
/*    */   public String getConfigValue(String[] args) {
/* 52 */     String value = ChatColor.translateAlternateColorCodes('&', LANG
/* 53 */         .getString(this.path, this.def));
/*    */     
/* 55 */     if (args == null) {
/* 56 */       return value;
/*    */     }
/* 58 */     if (args.length == 0) {
/* 59 */       return value;
/*    */     }
/* 61 */     for (int i = 0; i < args.length; i++) {
/* 62 */       value = value.replace("{" + i + "}", args[i]);
/*    */     }
/*    */ 
/*    */     
/* 66 */     return value;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\Lang.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */