/*     */ package me.clip.deluxechat;
/*     */ 
/*     */ import me.clip.deluxechat.fanciful.FancyMessage;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeluxeUtil
/*     */ {
/*     */   public static void sms(CommandSender s, String msg) {
/*  16 */     s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
/*     */   }
/*     */   
/*     */   public static void sms(Player p, String msg) {
/*  20 */     p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
/*     */   }
/*     */   
/*     */   public static boolean containsInvalidChars(String text) {
/*  24 */     for (char c : text.toCharArray()) {
/*  25 */       int ci = c;
/*  26 */       if ((ci > 128 && ci < 167) || ci > 167) {
/*  27 */         return true;
/*     */       }
/*     */     } 
/*  30 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFormat(ChatColor c) {
/*  35 */     return c.isFormat();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String convertToJson(String text) {
/*  40 */     if (text == null) {
/*  41 */       text = "";
/*     */     }
/*     */     
/*  44 */     if (!text.contains("§")) {
/*  45 */       return (new FancyMessage(text)).toJSONString();
/*     */     }
/*     */     
/*  48 */     FancyMessage fm = null;
/*     */     
/*  50 */     boolean first = true;
/*     */     
/*  52 */     String[] split = text.split("§");
/*     */     
/*  54 */     ChatColor prevColor = null;
/*     */     
/*  56 */     ChatColor prevFormat = null;
/*     */     
/*  58 */     for (String part : split) {
/*     */       
/*  60 */       if (!part.isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/*  64 */         ChatColor color = ChatColor.getByChar(part.charAt(0));
/*     */         
/*  66 */         String textAfter = part.substring(1);
/*     */         
/*  68 */         if (textAfter.isEmpty() && 
/*  69 */           color != null && !color.equals(ChatColor.RESET)) {
/*  70 */           if (isFormat(color)) {
/*  71 */             prevFormat = color;
/*     */           } else {
/*  73 */             prevColor = color;
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*  79 */         else if (color == null) {
/*     */           
/*  81 */           if (first) {
/*  82 */             first = false;
/*  83 */             fm = new FancyMessage(textAfter);
/*     */           } else {
/*     */             
/*  86 */             fm.then(textAfter);
/*     */           } 
/*     */           
/*  89 */           if (prevColor != null) {
/*  90 */             fm.color(prevColor);
/*  91 */             prevColor = null;
/*     */           } 
/*     */           
/*  94 */           if (prevFormat != null) {
/*  95 */             fm.style(new ChatColor[] { prevFormat });
/*  96 */             prevFormat = null;
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/* 102 */         else if (first) {
/*     */           
/* 104 */           first = false;
/*     */           
/* 106 */           fm = new FancyMessage(textAfter);
/*     */           
/* 108 */           if (prevColor != null) {
/* 109 */             fm.color(prevColor);
/* 110 */             prevColor = null;
/*     */           } 
/*     */           
/* 113 */           if (prevFormat != null) {
/* 114 */             fm.style(new ChatColor[] { prevFormat });
/* 115 */             prevFormat = null;
/*     */           } 
/*     */           
/* 118 */           if (!color.equals(ChatColor.RESET)) {
/* 119 */             if (isFormat(color)) {
/* 120 */               fm.style(new ChatColor[] { color });
/*     */             } else {
/* 122 */               fm.color(color);
/*     */             }
/*     */           
/*     */           }
/*     */         } else {
/*     */           
/* 128 */           fm.then(textAfter);
/*     */           
/* 130 */           if (prevColor != null) {
/* 131 */             fm.color(prevColor);
/* 132 */             prevColor = null;
/*     */           } 
/*     */           
/* 135 */           if (prevFormat != null) {
/* 136 */             fm.style(new ChatColor[] { prevFormat });
/* 137 */             prevFormat = null;
/*     */           } 
/*     */           
/* 140 */           if (!color.equals(ChatColor.RESET)) {
/* 141 */             if (isFormat(color)) {
/* 142 */               fm.style(new ChatColor[] { color });
/*     */             } else {
/* 144 */               fm.color(color);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (fm == null) {
/* 154 */       return (new FancyMessage(text)).toJSONString();
/*     */     }
/* 156 */     return fm.toJSONString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getLastColor(String text) {
/* 162 */     text = ChatColor.translateAlternateColorCodes('&', text);
/*     */     
/* 164 */     String r = "";
/*     */     
/* 166 */     if (text == null || text.isEmpty())
/*     */     {
/* 168 */       return r;
/*     */     }
/*     */     
/* 171 */     if (DeluxeChat.getInstance().getChat().getVersion().equals("v1_16_R1")) {
/* 172 */       String lastHex = DeluxeChat.getInstance().getChat().getLastHex(text);
/* 173 */       if (lastHex != null) {
/* 174 */         return lastHex;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     if (text.length() >= 2 && text.charAt(text.length() - 2) == '§') {
/*     */       
/* 180 */       char a = text.charAt(text.length() - 1);
/*     */       
/* 182 */       ChatColor c1 = ChatColor.getByChar(a);
/*     */       
/* 184 */       if (c1 == null)
/*     */       {
/* 186 */         return r;
/*     */       }
/*     */       
/* 189 */       r = r + c1;
/*     */       
/* 191 */       if (text.length() >= 4 && text.charAt(text.length() - 4) == '§') {
/*     */         
/* 193 */         char b = text.charAt(text.length() - 3);
/*     */         
/* 195 */         ChatColor c2 = ChatColor.getByChar(b);
/*     */         
/* 197 */         if (c2 == null)
/*     */         {
/* 199 */           return r;
/*     */         }
/*     */         
/* 202 */         r = c2 + r;
/*     */         
/* 204 */         if (text.length() >= 6 && text.charAt(text.length() - 6) == '§') {
/*     */           
/* 206 */           char c = text.charAt(text.length() - 5);
/*     */           
/* 208 */           ChatColor c3 = ChatColor.getByChar(c);
/*     */           
/* 210 */           if (c3 == null)
/*     */           {
/* 212 */             return r;
/*     */           }
/*     */           
/* 215 */           r = c3 + r;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 221 */     return r;
/*     */   }
/*     */   
/*     */   public static String setMsgColor(String msg, String color) {
/* 225 */     if (msg.contains(" ")) {
/* 226 */       msg = ChatColor.translateAlternateColorCodes('&', msg);
/* 227 */       String[] parts = msg.split(" ");
/* 228 */       String temp = "";
/* 229 */       String lastColor = color;
/* 230 */       for (String w : parts) {
/* 231 */         if (w.length() >= 2 && w.charAt(0) == '§') {
/* 232 */           lastColor = w.charAt(0) + "" + w.charAt(1);
/* 233 */           if (w.length() >= 4 && w.charAt(2) == '§') {
/* 234 */             lastColor = lastColor + w.charAt(2) + "" + w.charAt(3);
/*     */           }
/* 236 */           if (w.length() >= 6 && w.charAt(4) == '§') {
/* 237 */             lastColor = lastColor + w.charAt(4) + "" + w.charAt(5);
/*     */           }
/*     */         } 
/*     */         
/* 241 */         temp = temp + lastColor + w + " ";
/*     */       } 
/* 243 */       msg = temp;
/*     */     } else {
/*     */       
/* 246 */       return color + msg;
/*     */     } 
/* 248 */     return msg;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String checkColor(Player p, String text, boolean pm) {
/* 253 */     text = text.replaceAll("[§]", "&");
/*     */     
/* 255 */     if (pm) {
/*     */       
/* 257 */       if (!p.hasPermission("deluxechat.pm.color")) {
/* 258 */         String c = "(&+)([0-9a-fA-F])";
/* 259 */         text = text.replaceAll(c, "");
/*     */       } 
/*     */       
/* 262 */       if (!p.hasPermission("deluxechat.pm.formatting")) {
/* 263 */         String f = "(&+)([k-orK-OR])";
/* 264 */         text = text.replaceAll(f, "");
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 269 */       if (!p.hasPermission("deluxechat.color")) {
/* 270 */         String c = "(&+)([0-9a-fA-F])";
/* 271 */         text = text.replaceAll(c, "");
/*     */       } 
/*     */       
/* 274 */       if (!p.hasPermission("deluxechat.formatting")) {
/* 275 */         String f = "(&+)([k-orK-OR])";
/* 276 */         text = text.replaceAll(f, "");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 281 */     return text;
/*     */   }
/*     */   
/*     */   public static String removeBlacklisted(String s) {
/* 285 */     if (!DeluxeChat.useBlacklist || DeluxeChat.blacklist == null || DeluxeChat.blacklist
/*     */       
/* 287 */       .isEmpty()) {
/* 288 */       return s;
/*     */     }
/*     */     
/* 291 */     if (!DeluxeChat.blacklistIgnoreCase) {
/* 292 */       for (String remove : DeluxeChat.blacklist.keySet())
/*     */       {
/* 294 */         String replace = DeluxeChat.blacklist.get(remove);
/*     */         
/* 296 */         if (replace.equalsIgnoreCase(remove)) {
/* 297 */           s = s.replace(remove, "");
/*     */           continue;
/*     */         } 
/* 300 */         s = s.replace(remove, replace);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 306 */       for (String remove : DeluxeChat.blacklist.keySet()) {
/* 307 */         if (s.indexOf(" ") == -1) {
/* 308 */           if (s.equalsIgnoreCase(remove)) {
/* 309 */             String replace = DeluxeChat.blacklist.get(remove);
/*     */             
/* 311 */             if (replace.equalsIgnoreCase(remove)) {
/* 312 */               s = s.replace(s, "");
/*     */               break;
/*     */             } 
/* 315 */             s = s.replace(s, replace);
/*     */             break;
/*     */           } 
/*     */           continue;
/*     */         } 
/* 320 */         for (String word : s.split(" ")) {
/* 321 */           if (word.equalsIgnoreCase(remove)) {
/* 322 */             String replace = DeluxeChat.blacklist.get(remove);
/*     */             
/* 324 */             if (replace.equalsIgnoreCase(remove)) {
/* 325 */               s = s.replace(word, "");
/*     */             } else {
/*     */               
/* 328 */               s = s.replace(word, replace);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 336 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\DeluxeUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */