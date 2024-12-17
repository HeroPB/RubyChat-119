/*     */ package me.clip.deluxechat.bungee;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteArrayDataOutput;
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Collection;
/*     */ import java.util.NoSuchElementException;
/*     */ import me.clip.deluxechat.messaging.PrivateMessageType;
/*     */ import net.md_5.bungee.UserConnection;
import net.md_5.bungee.api.config.ServerInfo;
/*     */ import net.md_5.bungee.api.connection.ProxiedPlayer;
/*     */ import net.md_5.bungee.api.event.PluginMessageEvent;
/*     */ import net.md_5.bungee.api.plugin.Listener;
/*     */ import net.md_5.bungee.api.plugin.Plugin;
/*     */ import net.md_5.bungee.event.EventHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeluxeChatBungee
/*     */   extends Plugin
/*     */   implements Listener
/*     */ {
/*     */   public void onEnable() {
/*  31 */     getProxy().registerChannel(PMChan.CHAT.getChannelName());
/*  32 */     getProxy().registerChannel(PMChan.PM.getChannelName());
/*  33 */     getProxy().registerChannel(PMChan.SPY.getChannelName());
/*  34 */     getProxy().getPluginManager().registerListener(this, this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  40 */     getProxy().unregisterChannel(PMChan.CHAT.getChannelName());
/*  41 */     getProxy().unregisterChannel(PMChan.PM.getChannelName());
/*  42 */     getProxy().unregisterChannel(PMChan.SPY.getChannelName());
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void receievePluginMessage(PluginMessageEvent e) {
/*  48 */     if (e.getTag().equalsIgnoreCase(PMChan.CHAT.getChannelName())) {
/*     */       
/*  50 */       if (e.getSender() instanceof UserConnection) {
/*  51 */         e.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*  55 */       InetSocketAddress senderServer = e.getSender().getAddress();
/*     */       
/*  57 */       for (ServerInfo server : getProxy().getServers().values()) {
/*     */         
/*  59 */         if (!server.getAddress().equals(senderServer) && server.getPlayers().size() > 0) {
/*  60 */           server.sendData(PMChan.CHAT.getChannelName(), e.getData());
/*     */         }
/*     */       } 
/*  63 */     } else if (e.getTag().equalsIgnoreCase(PMChan.PM.getChannelName())) {
/*     */       
/*  65 */       if (e.getSender() instanceof UserConnection) {
/*  66 */         e.setCancelled(true);
/*     */         
/*     */         return;
/*     */       } 
/*  70 */       ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
/*     */       
/*  72 */       String t = in.readUTF();
/*     */       
/*  74 */       PrivateMessageType type = PrivateMessageType.fromName(t);
/*     */       
/*  76 */       if (type == null) {
/*     */         return;
/*     */       }
/*     */       
/*  80 */       if (type != PrivateMessageType.MESSAGE_SEND) {
/*     */         return;
/*     */       }
/*     */       
/*  84 */       String sender = in.readUTF();
/*     */       
/*  86 */       String recipient = in.readUTF();
/*     */       
/*  88 */       String format = in.readUTF();
/*     */       
/*  90 */       String jsonMessage = in.readUTF();
/*     */       
/*  92 */       String rawMessage = in.readUTF();
/*     */       
/*  94 */       ProxiedPlayer send = getProxy().getPlayer(sender);
/*     */       
/*  96 */       if (send == null) {
/*     */         return;
/*     */       }
/*     */       
/* 100 */       Collection<ProxiedPlayer> matches = null;
/*     */ 
/*     */       
/*     */       try {
/* 104 */         matches = getProxy().matchPlayer(recipient);
/*     */       }
/* 106 */       catch (NoSuchElementException ex) {
/*     */         
/* 108 */         ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
/* 109 */         byteArrayDataOutput.writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
/* 110 */         byteArrayDataOutput.writeUTF(sender);
/* 111 */         byteArrayDataOutput.writeUTF(recipient);
/* 112 */         byteArrayDataOutput.writeUTF(format);
/* 113 */         byteArrayDataOutput.writeUTF(jsonMessage);
/* 114 */         byteArrayDataOutput.writeUTF(rawMessage);
/* 115 */         send.getServer().sendData(PMChan.PM.getChannelName(), byteArrayDataOutput.toByteArray());
/*     */         
/*     */         return;
/*     */       } 
/* 119 */       if (matches == null || matches.isEmpty()) {
/* 120 */         ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
/* 121 */         byteArrayDataOutput.writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
/* 122 */         byteArrayDataOutput.writeUTF(sender);
/* 123 */         byteArrayDataOutput.writeUTF(recipient);
/* 124 */         byteArrayDataOutput.writeUTF(format);
/* 125 */         byteArrayDataOutput.writeUTF(jsonMessage);
/* 126 */         byteArrayDataOutput.writeUTF(rawMessage);
/* 127 */         send.getServer().sendData(PMChan.PM.getChannelName(), byteArrayDataOutput.toByteArray());
/*     */         
/*     */         return;
/*     */       } 
/* 131 */       ProxiedPlayer recip = (ProxiedPlayer)Iterables.get(matches, 0);
/*     */       
/* 133 */       if (recip == null) {
/* 134 */         ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();
/* 135 */         byteArrayDataOutput.writeUTF(PrivateMessageType.MESSAGE_SENT_FAIL.getType());
/* 136 */         byteArrayDataOutput.writeUTF(sender);
/* 137 */         byteArrayDataOutput.writeUTF(recipient);
/* 138 */         byteArrayDataOutput.writeUTF(format);
/* 139 */         byteArrayDataOutput.writeUTF(jsonMessage);
/* 140 */         byteArrayDataOutput.writeUTF(rawMessage);
/* 141 */         send.getServer().sendData(PMChan.PM.getChannelName(), byteArrayDataOutput.toByteArray());
/*     */         
/*     */         return;
/*     */       } 
/* 145 */       ByteArrayDataOutput out = ByteStreams.newDataOutput();
/* 146 */       out.writeUTF(PrivateMessageType.MESSAGE_TO_RECIPIENT.getType());
/* 147 */       out.writeUTF(sender);
/* 148 */       out.writeUTF(recip.getName());
/* 149 */       out.writeUTF(format);
/* 150 */       out.writeUTF(jsonMessage);
/* 151 */       out.writeUTF(rawMessage);
/* 152 */       recip.getServer().sendData(PMChan.PM.getChannelName(), out.toByteArray());
/*     */       
/* 154 */       ByteArrayDataOutput senderOut = ByteStreams.newDataOutput();
/* 155 */       senderOut.writeUTF(PrivateMessageType.MESSAGE_SENT_SUCCESS.getType());
/* 156 */       senderOut.writeUTF(sender);
/* 157 */       senderOut.writeUTF(recip.getName());
/* 158 */       senderOut.writeUTF(format);
/* 159 */       senderOut.writeUTF(jsonMessage);
/* 160 */       senderOut.writeUTF(rawMessage);
/* 161 */       send.getServer().sendData(PMChan.PM.getChannelName(), senderOut.toByteArray());
/*     */       
/* 163 */       ByteArrayDataOutput socialspy = ByteStreams.newDataOutput();
/* 164 */       socialspy.writeUTF(sender);
/* 165 */       socialspy.writeUTF(recip.getName());
/* 166 */       socialspy.writeUTF(rawMessage);
/*     */       
/* 168 */       byte[] data = socialspy.toByteArray();
/*     */       
/* 170 */       InetSocketAddress senderServer = e.getSender().getAddress();
/*     */       
/* 172 */       for (ServerInfo server : getProxy().getServers().values()) {
/* 173 */         if (!server.getAddress().equals(senderServer) && server.getPlayers().size() > 0)
/* 174 */           server.sendData(PMChan.SPY.getChannelName(), data); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\bungee\DeluxeChatBungee.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */