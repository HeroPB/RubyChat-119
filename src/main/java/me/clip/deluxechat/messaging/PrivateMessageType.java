/*    */ package me.clip.deluxechat.messaging;
/*    */ 
/*    */ public enum PrivateMessageType
/*    */ {
/*  5 */   MESSAGE_SEND("MESSAGE_SEND"),
/*  6 */   MESSAGE_SENT_SUCCESS("MESSAGE_SENT_SUCCESS"),
/*  7 */   MESSAGE_SENT_FAIL("MESSAGE_SENT_FAIL"),
/*  8 */   MESSAGE_TO_RECIPIENT("MESSAGE_TO_RECIPIENT");
/*    */   
/*    */   private String name;
/*    */   
/*    */   PrivateMessageType(String name) {
/* 13 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getType() {
/* 17 */     return this.name;
/*    */   }
/*    */   
/*    */   public static PrivateMessageType fromName(String name) {
/* 21 */     for (PrivateMessageType type : values()) {
/* 22 */       if (type.getType().equalsIgnoreCase(name)) {
/* 23 */         return type;
/*    */       }
/*    */     } 
/* 26 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\messaging\PrivateMessageType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */