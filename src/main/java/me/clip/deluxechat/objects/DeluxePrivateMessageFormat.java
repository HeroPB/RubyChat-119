/*    */ package me.clip.deluxechat.objects;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeluxePrivateMessageFormat
/*    */ {
/*    */   private String format;
/*    */   private List<String> tooltip;
/*    */   private String clickAction;
/*    */   private String chatColor;
/*    */   
/*    */   public DeluxePrivateMessageFormat(String format, List<String> tooltip, String clickAction, String chatColor) {
/* 20 */     setChatColor(chatColor);
/* 21 */     setFormat(format);
/* 22 */     setTooltip(tooltip);
/* 23 */     setClickAction(clickAction);
/*    */   }
/*    */   
/*    */   public DeluxePrivateMessageFormat(String format) {
/* 27 */     setFormat(format);
/*    */   }
/*    */   
/*    */   public static DeluxePrivateMessageFormat newInstance(DeluxePrivateMessageFormat format) {
/* 31 */     DeluxePrivateMessageFormat copy = new DeluxePrivateMessageFormat(format.getFormat());
/* 32 */     copy.setTooltip(format.getTooltip());
/* 33 */     copy.setClickAction(format.getClickAction());
/* 34 */     copy.setChatColor(format.getChatColor());
/* 35 */     return copy;
/*    */   }
/*    */   
/*    */   public String getFormat() {
/* 39 */     return this.format;
/*    */   }
/*    */   
/*    */   public void setFormat(String format) {
/* 43 */     this.format = format;
/*    */   }
/*    */   
/*    */   public List<String> getTooltip() {
/* 47 */     return this.tooltip;
/*    */   }
/*    */   
/*    */   public void setTooltip(List<String> tooltip) {
/* 51 */     this.tooltip = tooltip;
/*    */   }
/*    */   
/*    */   public String getClickAction() {
/* 55 */     return this.clickAction;
/*    */   }
/*    */   
/*    */   public void setClickAction(String clickAction) {
/* 59 */     this.clickAction = clickAction;
/*    */   }
/*    */   
/*    */   public String getChatColor() {
/* 63 */     return this.chatColor;
/*    */   }
/*    */   
/*    */   public void setChatColor(String chatColor) {
/* 67 */     this.chatColor = chatColor;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\objects\DeluxePrivateMessageFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */