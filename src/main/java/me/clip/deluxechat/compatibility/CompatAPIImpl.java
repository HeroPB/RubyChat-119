/*    */ package me.clip.deluxechat.compatibility;
/*    */ 
/*    */ import me.clip.deluxechat.CompatAPI;
/*    */ import me.clip.deluxechat.DeluxeChat;
/*    */ import me.clip.deluxechat.DeluxeUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class CompatAPIImpl
/*    */   implements CompatAPI
/*    */ {
/*    */   public boolean isLocal(String uuid) {
/* 14 */     return DeluxeChat.isLocal(uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public String convertToJson(String text) {
/* 19 */     return DeluxeUtil.convertToJson(text);
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\compatibility\CompatAPIImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */