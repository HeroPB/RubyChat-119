/*     */ package me.clip.deluxechat.fanciful;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableBiMap;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
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
/*     */ final class MessagePart
/*     */   implements JsonRepresentedObject, ConfigurationSerializable, Cloneable
/*     */ {
/*  45 */   ChatColor color = ChatColor.WHITE;
/*  46 */   ArrayList<ChatColor> styles = new ArrayList<>();
/*  47 */   String clickActionName = null; String clickActionData = null; String hoverActionName = null;
/*     */   
/*  49 */   JsonRepresentedObject hoverActionData = null;
/*  50 */   TextualComponent text = null; static final BiMap<ChatColor, String> stylesToNames;
/*     */   
/*     */   MessagePart(TextualComponent text) {
/*  53 */     this.text = text;
/*     */   }
/*     */   
/*     */   MessagePart() {
/*  57 */     this.text = null;
/*     */   }
/*     */   
/*     */   boolean hasText() {
/*  61 */     return (this.text != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public MessagePart clone() throws CloneNotSupportedException {
/*  67 */     MessagePart obj = (MessagePart)super.clone();
/*  68 */     obj.styles = (ArrayList<ChatColor>)this.styles.clone();
/*  69 */     if (this.hoverActionData instanceof JsonString) {
/*  70 */       obj.hoverActionData = new JsonString(((JsonString)this.hoverActionData).getValue());
/*  71 */     } else if (this.hoverActionData instanceof FancyMessage) {
/*  72 */       obj.hoverActionData = ((FancyMessage)this.hoverActionData).clone();
/*     */     } 
/*  74 */     return obj;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  81 */     ImmutableBiMap.Builder<ChatColor, String> builder = ImmutableBiMap.builder();
/*  82 */     for (ChatColor style : ChatColor.values()) {
/*  83 */       if (style.isFormat()) {
/*     */         String styleName;
/*     */ 
/*     */ 
/*     */         
/*  88 */         switch (style) {
/*     */           case MAGIC:
/*  90 */             styleName = "obfuscated"; break;
/*     */           case UNDERLINE:
/*  92 */             styleName = "underlined"; break;
/*     */           default:
/*  94 */             styleName = style.name().toLowerCase();
/*     */             break;
/*     */         } 
/*  97 */         builder.put(style, styleName);
/*     */       } 
/*  99 */     }  stylesToNames = (BiMap<ChatColor, String>)builder.build();
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
/* 156 */     ConfigurationSerialization.registerClass(MessagePart.class);
/*     */   }
/*     */   
/*     */   public void writeJson(JsonWriter json) {
/*     */     try {
/*     */       json.beginObject();
/*     */       this.text.writeJson(json);
/*     */       json.name("color").value(this.color.name().toLowerCase());
/*     */       for (ChatColor style : this.styles)
/*     */         json.name((String)stylesToNames.get(style)).value(true); 
/*     */       if (this.clickActionName != null && this.clickActionData != null)
/*     */         json.name("clickEvent").beginObject().name("action").value(this.clickActionName).name("value").value(this.clickActionData).endObject(); 
/*     */       if (this.hoverActionName != null && this.hoverActionData != null) {
/*     */         json.name("hoverEvent").beginObject().name("action").value(this.hoverActionName).name("value");
/*     */         this.hoverActionData.writeJson(json);
/*     */         json.endObject();
/*     */       } 
/*     */       json.endObject();
/*     */     } catch (IOException e) {
/*     */       Bukkit.getLogger().log(Level.WARNING, "A problem occured during writing of JSON string", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/*     */     HashMap<String, Object> map = new HashMap<>();
/*     */     map.put("text", this.text);
/*     */     map.put("styles", this.styles);
/*     */     map.put("color", Character.valueOf(this.color.getChar()));
/*     */     map.put("hoverActionName", this.hoverActionName);
/*     */     map.put("hoverActionData", this.hoverActionData);
/*     */     map.put("clickActionName", this.clickActionName);
/*     */     map.put("clickActionData", this.clickActionData);
/*     */     return map;
/*     */   }
/*     */   
/*     */   public static MessagePart deserialize(Map<String, Object> serialized) {
/*     */     MessagePart part = new MessagePart((TextualComponent)serialized.get("text"));
/*     */     part.styles = (ArrayList<ChatColor>)serialized.get("styles");
/*     */     part.color = ChatColor.getByChar(serialized.get("color").toString());
/*     */     part.hoverActionName = serialized.get("hoverActionName").toString();
/*     */     part.hoverActionData = (JsonRepresentedObject)serialized.get("hoverActionData");
/*     */     part.clickActionName = serialized.get("clickActionName").toString();
/*     */     part.clickActionData = serialized.get("clickActionData").toString();
/*     */     return part;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\MessagePart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */