/*     */ package me.clip.deluxechat.placeholders;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import me.clip.deluxechat.DeluxeChat;
/*     */ import me.clip.placeholderapi.PlaceholderAPI;
/*     */ import me.clip.placeholderapi.PlaceholderAPIPlugin;
/*     */ import me.clip.placeholderapi.expansion.PlaceholderExpansion;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public class PlaceholderHandler
/*     */ {
/*  22 */   private static final Pattern RECIPIENT_PLACEHOLDER_PATTERN = Pattern.compile("[%](recipient_)([a-zA-Z0-9_.-@-]+)[%]");
/*     */   public PlaceholderHandler(DeluxeChat i) {}
/*     */   
/*     */   @Deprecated
/*     */   public static void unregisterInternalPlaceholderHooks() {}
/*     */   
/*     */   @Deprecated
/*     */   public static void unregisterInternalRecipientPlaceholderHooks() {}
/*     */   
/*     */   @Deprecated
/*     */   public static Set<String> getExternalPlaceholderPlugins() {
/*  33 */     return null;
/*     */   }
/*     */   @Deprecated
/*     */   public static Set<String> getExternalRecipientPlaceholderPlugins() {
/*  37 */     return null;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerPlaceholderHook(Plugin plugin, DeluxePlaceholderHook placeholderHook) {
/*  41 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerPlaceholderHook(String plugin, DeluxePlaceholderHook placeholderHook) {
/*  45 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerPlaceholderHook(Plugin plugin, DeluxePlaceholderHook placeholderHook, boolean internal) {
/*  49 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerPlaceholderHook(String plugin, DeluxePlaceholderHook placeholderHook, boolean internal) {
/*  53 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerRecipientPlaceholderHook(Plugin plugin, DeluxeRecipientPlaceholderHook placeholderHook) {
/*  57 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerRecipientPlaceholderHook(String plugin, DeluxeRecipientPlaceholderHook placeholderHook) {
/*  61 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerRecipientPlaceholderHook(Plugin plugin, DeluxeRecipientPlaceholderHook placeholderHook, boolean internal) {
/*  65 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean registerRecipientPlaceholderHook(String plugin, DeluxeRecipientPlaceholderHook placeholderHook, boolean internal) {
/*  69 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean unregisterPlaceholderHook(Plugin plugin) {
/*  73 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean unregisterPlaceholderHook(String plugin) {
/*  77 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean unregisterRecipientPlaceholderHook(Plugin plugin) {
/*  81 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static boolean unregisterRecipientPlaceholderHook(String plugin) {
/*  85 */     return false;
/*     */   }
/*     */   @Deprecated
/*     */   public static Set<String> getRegisteredPlaceholderPlugins() {
/*  89 */     return null;
/*     */   }
/*     */   @Deprecated
/*     */   public static Set<String> getRegisteredRecipientPlaceholderPlugins() {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Map<String, DeluxePlaceholderHook> getPlaceholders() {
/*  99 */     return null;
/*     */   } @Deprecated
/*     */   public static void unregisterAllPlaceholderHooks() {} @Deprecated
/*     */   public static Map<String, DeluxeRecipientPlaceholderHook> getRecipientPlaceholders() {
/* 103 */     return null;
/*     */   }
/*     */   
/*     */   public static List<String> setPlaceholders(Player p, List<String> text) {
/* 107 */     return PlaceholderAPI.setPlaceholders(p, text);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String setPlaceholders(Player player, String text) {
/* 112 */     return PlaceholderAPI.setPlaceholders(player, text);
/*     */   }
/*     */   
/*     */   public static List<String> setRecipientPlaceholders(Player recipient, List<String> text) {
/* 116 */     if (text == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     List<String> temp = new ArrayList<>();
/* 120 */     for (String line : text) {
/* 121 */       temp.add(ChatColor.translateAlternateColorCodes('&', setRecipPlaceholders(recipient, line)));
/*     */     }
/* 123 */     return temp;
/*     */   }
/*     */   
/*     */   public static String setRecipPlaceholders(Player recipient, String text) {
/* 127 */     Matcher m = RECIPIENT_PLACEHOLDER_PATTERN.matcher(text);
/* 128 */     while (m.find()) {
/*     */       
/* 130 */       String format = m.group(2);
/* 131 */       int index = format.indexOf("_");
/*     */       
/* 133 */       if (index <= 0 || index >= format.length()) {
/*     */         continue;
/*     */       }
/*     */       
/* 137 */       String pl = format.substring(0, index).toLowerCase();
/* 138 */       String identifier = format.substring(index + 1);
/*     */       
/* 140 */       Optional<PlaceholderExpansion> ex = PlaceholderAPIPlugin.getInstance().getLocalExpansionManager().findExpansionByIdentifier(pl);
/*     */       
/* 142 */       if (ex.isPresent()) {
/*     */         
/* 144 */         String value = ((PlaceholderExpansion)ex.get()).onRequest((OfflinePlayer)recipient, identifier);
/*     */         
/* 146 */         if (value != null) {
/* 147 */           text = text.replaceAll("%recipient_" + format + "%", Matcher.quoteReplacement(value));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     return ChatColor.translateAlternateColorCodes('&', text);
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\placeholders\PlaceholderHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */