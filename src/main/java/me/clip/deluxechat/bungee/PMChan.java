/*    */ package me.clip.deluxechat.bungee;
/*    */ 
/*    */ public enum PMChan
/*    */ {
/*  5 */   CHAT("dchat:chat"),
/*  6 */   PM("dchat:pm"),
/*  7 */   SPY("dchat:spy");
/*    */   
/*    */   private String channel;
/*    */   
/*    */   PMChan(String channel) {
/* 12 */     this.channel = channel;
/*    */   }
/*    */   
/*    */   public String getChannelName() {
/* 16 */     return this.channel;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\bungee\PMChan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */