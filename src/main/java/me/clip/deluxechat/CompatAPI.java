/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CompatAPI
/*    */ {
/* 10 */   public static final Pattern HEX_PATTERN = Pattern.compile("(#[A-Fa-f0-9]{6})");
/*    */   
/*    */   boolean isLocal(String paramString);
/*    */   
/*    */   String convertToJson(String paramString);
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\CompatAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */