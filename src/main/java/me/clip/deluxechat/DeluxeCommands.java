/*     */ package me.clip.deluxechat;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import me.clip.deluxechat.events.DeluxeChatJSONEvent;
/*     */ import me.clip.deluxechat.fanciful.FancyMessage;
/*     */ import me.clip.deluxechat.objects.DeluxeFormat;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeluxeCommands
/*     */   implements CommandExecutor
/*     */ {
/*     */   DeluxeChat plugin;
/*     */   
/*     */   public DeluxeCommands(DeluxeChat i) {
/*  31 */     this.plugin = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
/*  39 */     if (!(s instanceof Player)) {
/*  40 */       if (args.length == 0) {
/*     */         
/*  42 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  43 */         DeluxeUtil.sms(s, "DeluxeChat &7Version: &f" + this.plugin.getDescription().getVersion());
/*  44 */         DeluxeUtil.sms(s, "&7Created by: &cextended_clip");
/*  45 */         DeluxeUtil.sms(s, "&c/dchat reload &7- &fReload DeluxeChat");
/*  46 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  47 */         return true;
/*     */       } 
/*  49 */       if (args.length > 0) {
/*     */         
/*  51 */         if (args[0].equalsIgnoreCase("reload")) {
/*     */           
/*  53 */           this.plugin.c.rc();
/*     */           
/*  55 */           DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  56 */           DeluxeUtil.sms(s, "&fDeluxeChat &asuccessfully reloaded!");
/*  57 */           DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  58 */           return true;
/*     */         } 
/*     */ 
/*     */         
/*  62 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  63 */         DeluxeUtil.sms(s, "&cIncorrect command usage! Use &7/dchat");
/*  64 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  65 */         return true;
/*     */       } 
/*     */       
/*  68 */       return true;
/*     */     } 
/*     */     
/*  71 */     final Player p = (Player)s;
/*     */     
/*  73 */     if (!p.hasPermission("deluxechat.admin")) {
/*  74 */       DeluxeUtil.sms(s, "&cYou don't have permission to do that!");
/*  75 */       return true;
/*     */     } 
/*     */     
/*  78 */     if (args.length == 0) {
/*  79 */       DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*  80 */       List<String> info = new ArrayList<>();
/*  81 */       info.add(ChatColor.translateAlternateColorCodes('&', "&7Version: &f" + this.plugin.getDescription().getVersion()));
/*  82 */       info.add(ChatColor.translateAlternateColorCodes('&', "&7Created by: &cbxtended_clip"));
/*     */ 
/*     */ 
/*     */       
/*  86 */       FancyMessage main = (new FancyMessage("DeluxeChat")).color(ChatColor.AQUA).tooltip(info).then(" help menu").color(ChatColor.WHITE);
/*     */       
/*  88 */       this.plugin.getChat().sendFancyMessage(s, main);
/*     */       
/*  90 */       if (DeluxeChat.useBungee()) {
/*  91 */         List<String> list1 = new ArrayList<>();
/*  92 */         list1.add(ChatColor.translateAlternateColorCodes('&', "&bClick to toggle bungee / server chat"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  98 */         FancyMessage fancyMessage = (new FancyMessage("/gtoggle")).color(ChatColor.AQUA).tooltip(list1).suggest("/gtoggle").then(" - toggle bungee / server chat").color(ChatColor.WHITE);
/*     */         
/* 100 */         this.plugin.getChat().sendFancyMessage(s, fancyMessage);
/*     */       } 
/*     */       
/* 103 */       List<String> t = new ArrayList<>();
/* 104 */       t.add(ChatColor.translateAlternateColorCodes('&', "&bClick to toggle bungee / server chat"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       FancyMessage gtoggle = (new FancyMessage("/msgtoggle")).color(ChatColor.AQUA).tooltip(t).suggest("/msgtoggle").then(" - toggle receiving private messages on/off").color(ChatColor.WHITE);
/*     */       
/* 112 */       this.plugin.getChat().sendFancyMessage(s, gtoggle);
/*     */       
/* 114 */       List<String> reload = new ArrayList<>();
/* 115 */       reload.add(ChatColor.translateAlternateColorCodes('&', "&bClick to reload"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       FancyMessage rel = (new FancyMessage("/dchat reload")).color(ChatColor.AQUA).tooltip(reload).suggest("/dchat reload").then(" - Reload the config.yml ").color(ChatColor.WHITE);
/*     */       
/* 123 */       this.plugin.getChat().sendFancyMessage(s, rel);
/*     */       
/* 125 */       List<String> test = new ArrayList<>();
/* 126 */       test.add(ChatColor.translateAlternateColorCodes('&', "&bClick to test a format"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 132 */       FancyMessage testFormat = (new FancyMessage("/dchat test")).color(ChatColor.AQUA).tooltip(test).suggest("/dchat test <format> <message>").then(" - test a loaded format").color(ChatColor.WHITE);
/*     */       
/* 134 */       this.plugin.getChat().sendFancyMessage(s, testFormat);
/*     */       
/* 136 */       List<String> li = new ArrayList<>();
/* 137 */       li.add(ChatColor.translateAlternateColorCodes('&', "&bClick to list loaded format names"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       FancyMessage list = (new FancyMessage("/dchat list")).color(ChatColor.AQUA).tooltip(li).suggest("/dchat list").then(" - list loaded format names").color(ChatColor.WHITE);
/* 144 */       this.plugin.getChat().sendFancyMessage(s, list);
/*     */       
/* 146 */       DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/*     */       
/* 148 */       return true;
/*     */     } 
/* 150 */     if (args.length > 0) {
/* 151 */       if (args[0].equalsIgnoreCase("reload")) {
/*     */         
/* 153 */         this.plugin.c.rc();
/*     */         
/* 155 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/* 156 */         List<String> list = new ArrayList<>();
/* 157 */         list.add(ChatColor.translateAlternateColorCodes('&', "&f" + DeluxeChat.getFormats().size() + " &bformats successfully loaded"));
/*     */ 
/*     */ 
/*     */         
/* 161 */         FancyMessage rl = (new FancyMessage("Configuration")).color(ChatColor.WHITE).tooltip(list).then(" successfully reloaded!").color(ChatColor.GREEN);
/*     */         
/* 163 */         this.plugin.getChat().sendFancyMessage(s, rl);
/*     */         
/* 165 */         DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/* 166 */         return true;
/* 167 */       }  if (args[0].equalsIgnoreCase("list")) {
/*     */         
/* 169 */         Map<Integer, DeluxeFormat> formats = DeluxeChat.getFormats();
/*     */         
/* 171 */         if (formats == null || formats.isEmpty()) {
/* 172 */           DeluxeUtil.sms(s, "&cThere are no formats loaded!");
/* 173 */           return true;
/*     */         } 
/*     */         
/* 176 */         List<String> names = new ArrayList<>();
/*     */         
/* 178 */         Iterator<DeluxeFormat> iterator = formats.values().iterator();
/*     */         
/* 180 */         while (iterator.hasNext()) {
/* 181 */           String name = ((DeluxeFormat)iterator.next()).getIdentifier();
/* 182 */           names.add(name);
/*     */         } 
/*     */         
/* 185 */         DeluxeUtil.sms(s, "&7Loaded formats &e(&f" + names.size() + "&e)&7: " + names.toString());
/* 186 */         return true;
/*     */       } 
/* 188 */       if (args[0].equalsIgnoreCase("test")) {
/*     */         
/* 190 */         if (args.length < 3) {
/* 191 */           DeluxeUtil.sms(s, "&cIncorrect usage! &7/dchat test <formatname> <message>");
/* 192 */           return true;
/*     */         } 
/*     */         
/* 195 */         String f = args[1];
/*     */         
/* 197 */         DeluxeFormat format = this.plugin.getTestFormat(f);
/*     */         
/* 199 */         if (format == null) {
/* 200 */           DeluxeUtil.sms(s, "&cThe format &f" + f + " &cis not a loaded format!");
/* 201 */           return true;
/*     */         } 
/*     */         
/* 204 */         final FancyMessage fm = this.plugin.getFancyChatFormat(p, format);
/*     */         
/* 206 */         if (fm == null) {
/* 207 */           DeluxeUtil.sms(s, "&cThere was an error getting the chat format: &f" + f);
/* 208 */           return true;
/*     */         } 
/*     */         
/* 211 */         String msg = fm.getLastColor() + fm.getChatColor() + StringUtils.join(Arrays.copyOfRange((Object[])args, 2, args.length), " ");
/*     */         
/* 213 */         msg = ChatColor.translateAlternateColorCodes('&', msg);
/*     */         
/* 215 */         boolean relation = DeluxeChat.useRelationPlaceholders();
/*     */         
/* 217 */         final String jsonMessage = this.plugin.getChat().convertMsg(p, msg);
/*     */         
/* 219 */         final String m = msg;
/*     */         
/* 221 */         if (relation) {
/*     */           
/* 223 */           Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 227 */                   DeluxeChatJSONEvent jsonEvent = new DeluxeChatJSONEvent(!Bukkit.isPrimaryThread(), p, fm.toJSONString(), jsonMessage, m);
/*     */                   
/* 229 */                   Bukkit.getPluginManager().callEvent((Event)jsonEvent);
/*     */                   
/* 231 */                   if (jsonEvent.getJSONFormat() == null || jsonEvent
/* 232 */                     .getJSONChatMessage() == null || jsonEvent
/* 233 */                     .getJSONFormat().isEmpty()) {
/*     */                     return;
/*     */                   }
/*     */                   
/* 237 */                   DeluxeCommands.this.plugin.getChat().sendDirectChat(p, jsonEvent.getJSONFormat(), jsonEvent.getJSONChatMessage(), p, p);
/*     */                 }
/*     */               });
/*     */           
/* 241 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 245 */         Set<Player> recip = new HashSet<>();
/* 246 */         recip.add(p);
/*     */         
/* 248 */         this.plugin.getChat().sendDeluxeChat(p, fm.toJSONString(), jsonMessage, recip);
/*     */ 
/*     */         
/* 251 */         return true;
/*     */       } 
/*     */       
/* 254 */       DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/* 255 */       List<String> info = new ArrayList<>();
/* 256 */       info.add(ChatColor.translateAlternateColorCodes('&', "&cIncorrect command!"));
/* 257 */       info.add(ChatColor.translateAlternateColorCodes('&', "&fUse &b/dchat"));
/*     */ 
/*     */ 
/*     */       
/* 261 */       FancyMessage incorrect = (new FancyMessage("Incorrect usage!")).color(ChatColor.RED).tooltip(info).suggest("/dchat");
/*     */       
/* 263 */       this.plugin.getChat().sendFancyMessage(s, incorrect);
/*     */       
/* 265 */       DeluxeUtil.sms(s, "&8&m-----------------------------------------------------");
/* 266 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\DeluxeCommands.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */