/*    */ package me.clip.deluxechat.placeholders;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PlaceholderLayout
/*    */ {
/*  8 */   private static Map<String, PlaceholderLayout> layouts = new HashMap<>();
/*    */   
/*    */   private String identifier;
/*    */   
/*    */   private String hasValue;
/*    */   
/*    */   private String noValue;
/*    */   
/*    */   public PlaceholderLayout(String identifier, String hasValue, String noValue) {
/* 17 */     this.identifier = identifier;
/* 18 */     this.hasValue = hasValue;
/* 19 */     this.noValue = noValue;
/*    */   }
/*    */   
/*    */   public String getIdentifier() {
/* 23 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public String getHasValue() {
/* 27 */     return this.hasValue;
/*    */   }
/*    */   
/*    */   public String getNoValue() {
/* 31 */     return this.noValue;
/*    */   }
/*    */   
/*    */   public void load() {
/* 35 */     if (layouts == null) {
/* 36 */       layouts = new HashMap<>();
/*    */     }
/*    */     
/* 39 */     layouts.put(this.identifier, this);
/*    */   }
/*    */   
/*    */   public static void load(PlaceholderLayout layout) {
/* 43 */     if (layouts == null) {
/* 44 */       layouts = new HashMap<>();
/*    */     }
/*    */     
/* 47 */     layouts.put(layout.getIdentifier(), layout);
/*    */   }
/*    */   
/*    */   public boolean unload() {
/* 51 */     if (layouts == null || layouts.isEmpty()) {
/* 52 */       return false;
/*    */     }
/* 54 */     return (layouts.remove(this.identifier) != null);
/*    */   }
/*    */   
/*    */   public static boolean unload(PlaceholderLayout layout) {
/* 58 */     if (layouts == null || layouts.isEmpty()) {
/* 59 */       return false;
/*    */     }
/* 61 */     return (layouts.remove(layout.getIdentifier()) != null);
/*    */   }
/*    */   
/*    */   public static void unloadAll() {
/* 65 */     layouts = null;
/*    */   }
/*    */   
/*    */   public static PlaceholderLayout getLayout(String identifier) {
/* 69 */     if (layouts == null || layouts.isEmpty()) {
/* 70 */       return null;
/*    */     }
/* 72 */     if (layouts.containsKey(identifier) && layouts.get(identifier) != null) {
/* 73 */       return layouts.get(identifier);
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\placeholders\PlaceholderLayout.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */