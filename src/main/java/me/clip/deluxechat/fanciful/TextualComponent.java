/*     */ package me.clip.deluxechat.fanciful;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
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
/*     */ public abstract class TextualComponent
/*     */   implements Cloneable
/*     */ {
/*     */   static {
/*  42 */     ConfigurationSerialization.registerClass(ArbitraryTextTypeComponent.class);
/*  43 */     ConfigurationSerialization.registerClass(ComplexTextTypeComponent.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  48 */     return getReadableString();
/*     */   }
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
/*     */   static TextualComponent deserialize(Map<String, Object> map) {
/*  77 */     if (map.containsKey("key") && map.size() == 2 && map.containsKey("value"))
/*     */     {
/*  79 */       return ArbitraryTextTypeComponent.deserialize(map); } 
/*  80 */     if (map.size() >= 2 && map.containsKey("key") && !map.containsKey("value"))
/*     */     {
/*  82 */       return ComplexTextTypeComponent.deserialize(map);
/*     */     }
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   static boolean isTextKey(String key) {
/*  89 */     return (key.equals("translate") || key.equals("text") || key.equals("score") || key.equals("selector"));
/*     */   }
/*     */   
/*     */   private static final class ArbitraryTextTypeComponent
/*     */     extends TextualComponent
/*     */     implements ConfigurationSerializable {
/*     */     private String _key;
/*     */     private String _value;
/*     */     
/*     */     public ArbitraryTextTypeComponent(String key, String value) {
/*  99 */       setKey(key);
/* 100 */       setValue(value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 105 */       return this._key;
/*     */     }
/*     */     
/*     */     public void setKey(String key) {
/* 109 */       Preconditions.checkArgument((key != null && !key.isEmpty()), "The key must be specified.");
/* 110 */       this._key = key;
/*     */     }
/*     */     
/*     */     public String getValue() {
/* 114 */       return this._value;
/*     */     }
/*     */     
/*     */     public void setValue(String value) {
/* 118 */       Preconditions.checkArgument((value != null), "The value must be specified.");
/* 119 */       this._value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TextualComponent clone() throws CloneNotSupportedException {
/* 128 */       return new ArbitraryTextTypeComponent(getKey(), getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeJson(JsonWriter writer) throws IOException {
/* 133 */       writer.name(getKey()).value(getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, Object> serialize() {
/* 138 */       return new HashMap<String, Object>()
/*     */         {
/*     */         
/*     */         };
/*     */     }
/*     */     
/*     */     public static ArbitraryTextTypeComponent deserialize(Map<String, Object> map) {
/* 145 */       return new ArbitraryTextTypeComponent(map.get("key").toString(), map.get("value").toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public String getReadableString() {
/* 150 */       return getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class ComplexTextTypeComponent
/*     */     extends TextualComponent
/*     */     implements ConfigurationSerializable {
/*     */     private String _key;
/*     */     private Map<String, String> _value;
/*     */     
/*     */     public ComplexTextTypeComponent(String key, Map<String, String> values) {
/* 161 */       setKey(key);
/* 162 */       setValue(values);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 167 */       return this._key;
/*     */     }
/*     */     
/*     */     public void setKey(String key) {
/* 171 */       Preconditions.checkArgument((key != null && !key.isEmpty()), "The key must be specified.");
/* 172 */       this._key = key;
/*     */     }
/*     */     
/*     */     public Map<String, String> getValue() {
/* 176 */       return this._value;
/*     */     }
/*     */     
/*     */     public void setValue(Map<String, String> value) {
/* 180 */       Preconditions.checkArgument((value != null), "The value must be specified.");
/* 181 */       this._value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public TextualComponent clone() throws CloneNotSupportedException {
/* 190 */       return new ComplexTextTypeComponent(getKey(), getValue());
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeJson(JsonWriter writer) throws IOException {
/* 195 */       writer.name(getKey());
/* 196 */       writer.beginObject();
/* 197 */       for (Map.Entry<String, String> jsonPair : this._value.entrySet()) {
/* 198 */         writer.name(jsonPair.getKey()).value(jsonPair.getValue());
/*     */       }
/* 200 */       writer.endObject();
/*     */     }
/*     */ 
/*     */     
/*     */     public Map<String, Object> serialize() {
/* 205 */       return new HashMap<String, Object>()
/*     */         {
/*     */         
/*     */         };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public static ComplexTextTypeComponent deserialize(Map<String, Object> map) {
/* 214 */       String key = null;
/* 215 */       Map<String, String> value = new HashMap<>();
/* 216 */       for (Map.Entry<String, Object> valEntry : map.entrySet()) {
/* 217 */         if (((String)valEntry.getKey()).equals("key")) {
/* 218 */           key = (String)valEntry.getValue(); continue;
/* 219 */         }  if (((String)valEntry.getKey()).startsWith("value.")) {
/* 220 */           value.put(((String)valEntry.getKey()).substring(6), valEntry.getValue().toString());
/*     */         }
/*     */       } 
/* 223 */       return new ComplexTextTypeComponent(key, value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getReadableString() {
/* 228 */       return getKey();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TextualComponent rawText(String textValue) {
/* 239 */     return new ArbitraryTextTypeComponent("text", textValue);
/*     */   }
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
/*     */   public static TextualComponent localizedText(String translateKey) {
/* 253 */     return new ArbitraryTextTypeComponent("translate", translateKey);
/*     */   }
/*     */   
/*     */   private static void throwUnsupportedSnapshot() {
/* 257 */     throw new UnsupportedOperationException("This feature is only supported in snapshot releases.");
/*     */   }
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
///*     */   public static TextualComponent objectiveScore(String scoreboardObjective) {
///* 270 */     return objectiveScore("*", scoreboardObjective);
///*     */   }
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
///*     */   public static TextualComponent objectiveScore(String playerName, String scoreboardObjective) {
///* 285 */     throwUnsupportedSnapshot();
///*     */
///* 287 */     return new ComplexTextTypeComponent("score", (Map<String, String>)ImmutableMap.builder()
///* 288 */         .put("name", playerName)
///* 289 */         .put("objective", scoreboardObjective)
///* 290 */         .build());
///*     */   }
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
/*     */   public static TextualComponent selector(String selector) {
/* 303 */     throwUnsupportedSnapshot();
/*     */     
/* 305 */     return new ArbitraryTextTypeComponent("selector", selector);
/*     */   }
/*     */   
/*     */   public abstract String getKey();
/*     */   
/*     */   public abstract String getReadableString();
/*     */   
/*     */   public abstract TextualComponent clone() throws CloneNotSupportedException;
/*     */   
/*     */   public abstract void writeJson(JsonWriter paramJsonWriter) throws IOException;
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\TextualComponent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */