/*     */ package me.clip.deluxechat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import me.clip.deluxechat.objects.DeluxeFormat;
/*     */ import me.clip.deluxechat.objects.DeluxePrivateMessageFormat;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeluxeConfig
/*     */ {
/*     */   DeluxeChat plugin;
/*     */   
/*     */   public DeluxeConfig(DeluxeChat i) {
/*  24 */     this.plugin = i;
/*     */   }
/*     */   
/*     */   protected void loadConfigFile() {
/*  28 */     FileConfiguration c = this.plugin.getConfig();
/*  29 */     c.options().header("DeluxeChat version " + this.plugin.getDescription().getVersion() + " config file\nCreated by extended_clip\nList as many format configurations you in the formats: config section.\nEach format configuration requires a specific layout which can be seen below.\nFormat priority must be specified per format and each priority must be unique.\nhigher value = lower priority\nex: guest-100, owner-1\n  YOU MUST KEEP A 'default' TEMPLATE! \n  \nSocial Spy only allows %player% and %recipient% as placeholders\n\nYou must download the placeholder expansion through PlaceholderAPI for which\nplaceholders you want to use. The example below uses Player placeholders, so\nwe need to download the player expansion: /papi ecloud download Player, /papi reload\n\n\nexample format template:\nformats: \n  default:\n    priority: 2147483647\n    channel: ''\n    prefix: '&8[&7Guest&8] '\n    name_color: '&7'\n    name: '%player_name%'\n    suffix: '&7> '\n    chat_color: '&f'\n    channel_tooltip:\n    - '&7%player_name% &bis a Guest'\n    prefix_tooltip:\n    - '&7%player_name% &bis a Guest'\n    name_tooltip:\n    - ''\n    suffix_tooltip:\n    - ''\n    channel_click_command: '\n    prefix_click_command: '/ranks\n    name_click_command: '/msg %player_name% \n    suffix_click_command: '\n  Member:\n    priority: 100\n    channel: ''\n    prefix: '&8[&aMember&8] '\n    name_color: '&7'\n    name: '%player_name%'\n    suffix: '&7> '\n    chat_color: '&f'\n    channel_tooltip:\n    - '&7%player_name% &bis a Member'\n    prefix_tooltip:\n    - '&7%player_name% &bis a Member'\n    name_tooltip:\n    - ''\n    suffix_tooltip:\n    - ''\n    channel_click_command: '\n    prefix_click_command: '/ranks\n    name_click_command: '/msg %player_name% \n    suffix_click_command: '");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     c.addDefault("check_updates", Boolean.valueOf(true));
/*  91 */     c.addDefault("bungeecord.enabled", Boolean.valueOf(false));
/*  92 */     c.addDefault("bungeecord.server_name", "Server");
/*  93 */     c.addDefault("bungeecord.server_prefix", "&8[&cServer&8]");
/*  94 */     c.addDefault("bungeecord.join_global", Boolean.valueOf(true));
/*  95 */     c.addDefault("bungeecord.use_server_whitelist", Boolean.valueOf(true));
/*  96 */     c.addDefault("bungeecord.server_whitelist", Arrays.asList(new String[] { "Server2", "Server3" }));
/*  97 */     c.addDefault("relation_placeholders_enabled", Boolean.valueOf(true));
/*  98 */     c.addDefault("timestamp_format", "MM/dd/yy HH:mm:ss");
/*  99 */     c.addDefault("boolean.true", "&atrue");
/* 100 */     c.addDefault("boolean.false", "&cfalse");
/* 101 */     c.addDefault("ops_use_group_format", Boolean.valueOf(false));
/* 102 */     if (c.contains("hooks")) {
/* 103 */       c.set("hooks", null);
/*     */     }
/* 105 */     if (c.contains("bungeecord.servername")) {
/* 106 */       c.set("bungeecord.servername", null);
/*     */     }
/* 108 */     c.addDefault("chat_filter.enabled", Boolean.valueOf(false));
/* 109 */     c.addDefault("chat_filter.ignore_case", Boolean.valueOf(true));
/* 110 */     c.addDefault("chat_filter.list", Arrays.asList(new String[] { ".; ", "fuck;fuck" }));
/* 111 */     if (c.contains("chat_url.tooltip")) {
/* 112 */       c.set("chat_url.tooltip", null);
/*     */     }
/* 114 */     c.addDefault("private_message.enabled", Boolean.valueOf(true));
/* 115 */     c.addDefault("private_message.bungeecord", Boolean.valueOf(false));
/* 116 */     c.addDefault("private_message_formats.to_sender.format", "&7you &e-> &7%recipient_player_name% &7:");
/* 117 */     c.addDefault("private_message_formats.to_sender.tooltip", Arrays.asList(new String[] { "%player_name%" }));
/* 118 */     c.addDefault("private_message_formats.to_sender.click_command", "/r ");
/* 119 */     c.addDefault("private_message_formats.to_sender.chat_color", "&f");
/* 120 */     c.addDefault("private_message_formats.to_recipient.format", "&7%player_name% &e-> &7you &7:");
/* 121 */     c.addDefault("private_message_formats.to_recipient.tooltip", Arrays.asList(new String[] { "%player_name%" }));
/* 122 */     c.addDefault("private_message_formats.to_recipient.click_command", "/r ");
/* 123 */     c.addDefault("private_message_formats.to_recipient.chat_color", "&f");
/* 124 */     c.addDefault("private_message_formats.social_spy", "&8[&cspy&8] &f%player% &e-> &f%recipient%&7:");
/*     */     
/* 126 */     c.addDefault("formats.default.priority", Integer.valueOf(2147483647));
/* 127 */     c.addDefault("formats.default.channel", "");
/* 128 */     c.addDefault("formats.default.prefix", "&7[%vault_group%&7] ");
/* 129 */     c.addDefault("formats.default.name_color", "&b");
/* 130 */     c.addDefault("formats.default.name", "%player_name%");
/* 131 */     c.addDefault("formats.default.suffix", "&7> ");
/* 132 */     c.addDefault("formats.default.chat_color", "&f");
/* 133 */     c.addDefault("formats.default.channel_tooltip", Arrays.asList(new String[] { "" }));
/* 134 */     c.addDefault("formats.default.prefix_tooltip", Arrays.asList(new String[] { "%player_name%", "&bRank: %vault_group%" }));
/* 135 */     c.addDefault("formats.default.name_tooltip", Arrays.asList(new String[] { "" }));
/* 136 */     c.addDefault("formats.default.suffix_tooltip", Arrays.asList(new String[] { "" }));
/* 137 */     c.addDefault("formats.default.channel_click_command", "/ranks");
/* 138 */     c.addDefault("formats.default.prefix_click_command", "/ranks");
/* 139 */     c.addDefault("formats.default.name_click_command", "/msg %player_name% ");
/* 140 */     c.addDefault("formats.default.suffix_click_command", "");
/*     */     
/* 142 */     c.options().copyDefaults(true);
/* 143 */     this.plugin.saveConfig();
/*     */   }
/*     */   
/*     */   protected void loadPMFormats() {
/* 147 */     FileConfiguration c = this.plugin.getConfig();
/*     */ 
/*     */ 
/*     */     
/* 151 */     DeluxePrivateMessageFormat sender = new DeluxePrivateMessageFormat(c.getString("private_message_formats.to_sender.format"), c.getStringList("private_message_formats.to_sender.tooltip"), c.getString("private_message_formats.to_sender.click_command"), c.getString("private_message_formats.to_sender.chat_color"));
/*     */ 
/*     */ 
/*     */     
/* 155 */     DeluxePrivateMessageFormat recip = new DeluxePrivateMessageFormat(c.getString("private_message_formats.to_recipient.format"), c.getStringList("private_message_formats.to_recipient.tooltip"), c.getString("private_message_formats.to_recipient.click_command"), c.getString("private_message_formats.to_recipient.chat_color"));
/*     */     
/* 157 */     DeluxeChat.toSenderPmFormat = sender;
/* 158 */     DeluxeChat.toRecipientPmFormat = recip;
/*     */   }
/*     */   
/*     */   protected List<String> serverWhitelist() {
/* 162 */     return this.plugin.getConfig().getStringList("bungeecord.server_whitelist");
/*     */   }
/*     */   
/*     */   protected boolean useServerWhitelist() {
/* 166 */     return this.plugin.getConfig().getBoolean("bungeecord.use_server_whitelist");
/*     */   }
/*     */   
/*     */   protected boolean blacklistIgnoreCase() {
/* 170 */     return this.plugin.getConfig().getBoolean("chat_filter.ignore_case");
/*     */   }
/*     */   
/*     */   protected boolean bungeePMEnabled() {
/* 174 */     return this.plugin.getConfig().getBoolean("private_message.bungeecord");
/*     */   }
/*     */   
/*     */   protected String socialSpyFormat() {
/* 178 */     return this.plugin.getConfig().getString("private_message_formats.social_spy");
/*     */   }
/*     */   
/*     */   protected String booleanTrue() {
/* 182 */     return this.plugin.getConfig().getString("boolean.true");
/*     */   }
/*     */   
/*     */   protected String booleanFalse() {
/* 186 */     return this.plugin.getConfig().getString("boolean.false");
/*     */   }
/*     */   
/*     */   protected String timestampFormat() {
/* 190 */     return this.plugin.getConfig().getString("timestamp_format");
/*     */   }
/*     */   
/*     */   protected boolean useRelationPlaceholders() {
/* 194 */     return this.plugin.getConfig().getBoolean("relation_placeholders_enabled");
/*     */   }
/*     */   
/*     */   protected boolean joinGlobal() {
/* 198 */     return this.plugin.getConfig().getBoolean("bungeecord.join_global");
/*     */   }
/*     */   
/*     */   protected boolean useBlacklist() {
/* 202 */     return this.plugin.getConfig().getBoolean("chat_filter.enabled");
/*     */   }
/*     */   
/*     */   protected boolean opsUseGroupFormat() {
/* 206 */     return this.plugin.getConfig().getBoolean("ops_use_group_format");
/*     */   }
/*     */   
/*     */   protected String serverName() {
/* 210 */     return this.plugin.getConfig().getString("bungeecord.server_name");
/*     */   }
/*     */   
/*     */   protected String serverPrefix() {
/* 214 */     return this.plugin.getConfig().getString("bungeecord.server_prefix");
/*     */   }
/*     */ 
/*     */   
/*     */   protected int loadBlacklist() {
/* 219 */     DeluxeChat.blacklist = new HashMap<>();
/*     */     
/* 221 */     List<String> cfgList = this.plugin.getConfig().getStringList("chat_filter.list");
/*     */     
/* 223 */     if (cfgList == null || cfgList.isEmpty()) {
/* 224 */       return 0;
/*     */     }
/*     */     
/* 227 */     for (String s : cfgList) {
/* 228 */       if (!s.contains(";")) {
/* 229 */         DeluxeChat.blacklist.put(s, s);
/*     */         continue;
/*     */       } 
/* 232 */       String[] p = s.split(";");
/* 233 */       String blword = p[0];
/* 234 */       String replace = p[1];
/* 235 */       DeluxeChat.blacklist.put(blword, replace);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 240 */     return DeluxeChat.blacklist.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int loadFormats() {
/* 245 */     if (DeluxeChat.formats != null && !DeluxeChat.formats.isEmpty()) {
/* 246 */       for (DeluxeFormat format : DeluxeChat.formats.values()) {
/* 247 */         format.unload();
/*     */       }
/*     */     }
/*     */     
/* 251 */     DeluxeChat.formats.clear();
/*     */     
/* 253 */     FileConfiguration c = this.plugin.getConfig();
/*     */     
/* 255 */     if (c.contains("formats") && c.isConfigurationSection("formats")) {
/*     */ 
/*     */       
/* 258 */       Set<String> identifiers = c.getConfigurationSection("formats").getKeys(false);
/*     */       
/* 260 */       boolean has = true;
/*     */       
/* 262 */       for (String i : identifiers) {
/*     */         
/* 264 */         if (!c.contains("formats." + i + ".channel")) {
/* 265 */           c.set("formats." + i + ".channel", "");
/* 266 */           has = false;
/*     */         } 
/* 268 */         if (!c.contains("formats." + i + ".channel_tooltip")) {
/* 269 */           c.set("formats." + i + ".channel_tooltip", new ArrayList());
/* 270 */           has = false;
/*     */         } 
/* 272 */         if (!c.contains("formats." + i + ".channel_click_command")) {
/* 273 */           c.set("formats." + i + ".channel_click_command", "[URL]http://www.google.com");
/* 274 */           has = false;
/*     */         } 
/*     */         
/* 277 */         if (!has) {
/* 278 */           this.plugin.saveConfig();
/* 279 */           this.plugin.reloadConfig();
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 284 */         DeluxeFormat f = new DeluxeFormat(i, c.getInt("formats." + i + ".priority"));
/*     */ 
/*     */         
/* 287 */         f.setChannel(ChatColor.translateAlternateColorCodes('&', c
/* 288 */               .getString("formats." + i + ".channel")));
/* 289 */         f.setPrefix(ChatColor.translateAlternateColorCodes('&', c
/* 290 */               .getString("formats." + i + ".prefix")));
/* 291 */         f.setNameColor(ChatColor.translateAlternateColorCodes('&', c
/* 292 */               .getString("formats." + i + ".name_color")));
/* 293 */         f.setName(ChatColor.translateAlternateColorCodes('&', c
/* 294 */               .getString("formats." + i + ".name")));
/* 295 */         f.setSuffix(ChatColor.translateAlternateColorCodes('&', c
/* 296 */               .getString("formats." + i + ".suffix")));
/* 297 */         f.setChatColor(ChatColor.translateAlternateColorCodes('&', c
/* 298 */               .getString("formats." + i + ".chat_color")));
/*     */         
/* 300 */         if (c.getStringList("formats." + i + ".channel_tooltip") == null || c
/* 301 */           .getStringList("formats." + i + ".channel_tooltip")
/* 302 */           .isEmpty()) {
/* 303 */           f.setShowChannelTooltip(false);
/*     */         } else {
/* 305 */           f.setShowChannelTooltip(true);
/* 306 */           f.setChannelTooltip(c.getStringList("formats." + i + ".channel_tooltip"));
/*     */         } 
/*     */ 
/*     */         
/* 310 */         if (c.getStringList("formats." + i + ".prefix_tooltip") == null || c
/* 311 */           .getStringList("formats." + i + ".prefix_tooltip")
/* 312 */           .isEmpty()) {
/* 313 */           f.setShowPreTooltip(false);
/*     */         } else {
/* 315 */           f.setShowPreTooltip(true);
/* 316 */           f.setPrefixTooltip(c.getStringList("formats." + i + ".prefix_tooltip"));
/*     */         } 
/*     */ 
/*     */         
/* 320 */         if (c.getStringList("formats." + i + ".name_tooltip") == null || c
/* 321 */           .getStringList("formats." + i + ".name_tooltip")
/* 322 */           .isEmpty()) {
/* 323 */           f.setShowNameTooltip(false);
/*     */         } else {
/* 325 */           f.setShowNameTooltip(true);
/* 326 */           f.setNameTooltip(c.getStringList("formats." + i + ".name_tooltip"));
/*     */         } 
/*     */ 
/*     */         
/* 330 */         if (c.getStringList("formats." + i + ".suffix_tooltip") == null || c
/* 331 */           .getStringList("formats." + i + ".suffix_tooltip")
/* 332 */           .isEmpty()) {
/* 333 */           f.setShowSuffixTooltip(false);
/*     */         } else {
/* 335 */           f.setShowSuffixTooltip(true);
/* 336 */           f.setSuffixTooltip(c.getStringList("formats." + i + ".suffix_tooltip"));
/*     */         } 
/*     */ 
/*     */         
/* 340 */         if (c.getString("formats." + i + ".channel_click_command") == null || c
/* 341 */           .getString("formats." + i + ".channel_click_command")
/*     */           
/* 343 */           .isEmpty()) {
/* 344 */           f.setUseChannelClick(false);
/*     */         } else {
/* 346 */           f.setUseChannelClick(true);
/* 347 */           f.setChannelClickCommand(c.getString("formats." + i + ".channel_click_command"));
/*     */         } 
/*     */ 
/*     */         
/* 351 */         if (c.getString("formats." + i + ".prefix_click_command") == null || c
/* 352 */           .getString("formats." + i + ".prefix_click_command")
/* 353 */           .isEmpty()) {
/* 354 */           f.setUsePreClick(false);
/*     */         } else {
/* 356 */           f.setUsePreClick(true);
/* 357 */           f.setPreClickCmd(c.getString("formats." + i + ".prefix_click_command"));
/*     */         } 
/*     */ 
/*     */         
/* 361 */         if (c.getString("formats." + i + ".name_click_command") == null || c
/* 362 */           .getString("formats." + i + ".name_click_command")
/* 363 */           .isEmpty()) {
/* 364 */           f.setUseNameClick(false);
/*     */         } else {
/* 366 */           f.setUseNameClick(true);
/* 367 */           f.setNameClickCmd(c.getString("formats." + i + ".name_click_command"));
/*     */         } 
/*     */ 
/*     */         
/* 371 */         if (c.getString("formats." + i + ".suffix_click_command") == null || c
/* 372 */           .getString("formats." + i + ".suffix_click_command")
/* 373 */           .isEmpty()) {
/* 374 */           f.setUseSuffixClick(false);
/*     */         } else {
/* 376 */           f.setUseSuffixClick(true);
/* 377 */           f.setSuffixClickCmd(c.getString("formats." + i + ".suffix_click_command"));
/*     */         } 
/*     */ 
/*     */         
/* 381 */         f.load();
/*     */       } 
/*     */     } 
/*     */     
/* 385 */     return DeluxeChat.getFormats().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void rc() {
/* 392 */     this.plugin.reloadConfig();
/* 393 */     this.plugin.saveConfig();
/*     */     
/* 395 */     DeluxeChat.useBlacklist = useBlacklist();
/* 396 */     DeluxeChat.blacklistIgnoreCase = blacklistIgnoreCase();
/* 397 */     DeluxeChat.serverPrefix = ChatColor.translateAlternateColorCodes('&', serverPrefix());
/* 398 */     DeluxeChat.serverName = serverName();
/* 399 */     DeluxeChat.joinGlobal = joinGlobal();
/* 400 */     DeluxeChat.useServerWhitelist = useServerWhitelist();
/* 401 */     DeluxeChat.serverWhitelist = serverWhitelist();
/* 402 */     DeluxeChat.useRelationPlaceholders = useRelationPlaceholders();
/* 403 */     DeluxeChat.booleanTrue = booleanTrue();
/* 404 */     DeluxeChat.booleanFalse = booleanFalse();
/* 405 */     loadPMFormats();
/* 406 */     DeluxeChat.socialSpyFormat = socialSpyFormat();
/* 407 */     if (this.plugin.vault != null && this.plugin.vault.useVaultPerms()) {
/* 408 */       DeluxeChat.opsUseGroupFormat = opsUseGroupFormat();
/*     */     } else {
/* 410 */       DeluxeChat.opsUseGroupFormat = false;
/*     */     } 
/*     */     
/* 413 */     this.plugin.getLog().info(loadFormats() + " formats loaded!");
/*     */     
/* 415 */     if (DeluxeChat.useBlacklist)
/* 416 */       this.plugin.getLog().info(loadBlacklist() + " entries added to the chat_filter!"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\DeluxeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */