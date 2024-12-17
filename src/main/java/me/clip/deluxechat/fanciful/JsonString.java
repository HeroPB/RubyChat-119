/*    */ package me.clip.deluxechat.fanciful;
/*    */ 
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*    */ import org.jetbrains.annotations.Unmodifiable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Unmodifiable
/*    */ final class JsonString
/*    */   implements JsonRepresentedObject, ConfigurationSerializable
/*    */ {
/*    */   private String _value;
/*    */   
/*    */   public JsonString(String value) {
/* 43 */     this._value = value;
/*    */   }
/*    */   
/*    */   public void writeJson(JsonWriter writer) throws IOException {
/* 47 */     writer.value(getValue());
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 51 */     return this._value;
/*    */   }
/*    */   
/*    */   public Map<String, Object> serialize() {
/* 55 */     HashMap<String, Object> theSingleValue = new HashMap<>();
/* 56 */     theSingleValue.put("stringValue", this._value);
/* 57 */     return theSingleValue;
/*    */   }
/*    */   
/*    */   public static JsonString deserialize(Map<String, Object> map) {
/* 61 */     return new JsonString(map.get("stringValue").toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return this._value;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\JsonString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */