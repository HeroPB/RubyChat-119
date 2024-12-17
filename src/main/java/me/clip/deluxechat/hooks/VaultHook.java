/*     */ package me.clip.deluxechat.hooks;
/*     */ 
/*     */ import me.clip.deluxechat.DeluxeChat;
/*     */ import net.milkbowl.vault.chat.Chat;
/*     */ import net.milkbowl.vault.economy.Economy;
/*     */ import net.milkbowl.vault.permission.Permission;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
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
/*     */ public class VaultHook
/*     */   implements PluginHook
/*     */ {
/*     */   private DeluxeChat plugin;
/*  38 */   private Permission perms = null;
/*     */   
/*  40 */   private Economy econ = null;
/*     */   
/*  42 */   private Chat chat = null;
/*     */   
/*     */   public VaultHook(DeluxeChat instance) {
/*  45 */     this.plugin = instance;
/*     */   }
/*     */   
/*     */   public boolean hook() {
/*  49 */     setupEconomy();
/*  50 */     setupPerms();
/*  51 */     setupChat();
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setupEconomy() {
/*  57 */     if (this.plugin.getConfig().getBoolean("hooks.vault_eco")) {
/*     */ 
/*     */       
/*  60 */       RegisteredServiceProvider<Economy> rsp = this.plugin.getServer().getServicesManager().getRegistration(Economy.class);
/*     */       
/*  62 */       if (rsp == null) {
/*  63 */         return false;
/*     */       }
/*     */       
/*  66 */       this.econ = (Economy)rsp.getProvider();
/*     */       
/*  68 */       return (this.econ != null);
/*     */     } 
/*     */     
/*  71 */     return false;
/*     */   }
/*     */   
/*     */   private boolean setupChat() {
/*  75 */     if (this.plugin.getConfig().getBoolean("hooks.vault_perms")) {
/*     */       
/*  77 */       RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
/*     */       
/*  79 */       if (chatProvider != null && chatProvider.getPlugin() != null) {
/*  80 */         this.chat = (Chat)chatProvider.getProvider();
/*     */       }
/*     */       
/*  83 */       return (this.chat != null);
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setupPerms() {
/*  90 */     if (this.plugin.getConfig().getBoolean("hooks.vault_perms")) {
/*     */       
/*  92 */       RegisteredServiceProvider<Permission> permsProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
/*     */       
/*  94 */       if (permsProvider != null && permsProvider.getPlugin() != null) {
/*  95 */         this.perms = (Permission)permsProvider.getProvider();
/*     */       }
/*     */       
/*  98 */       return (this.perms != null);
/*     */     } 
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   public boolean useVaultChat() {
/* 104 */     return (this.chat != null);
/*     */   }
/*     */   
/*     */   public boolean useVaultEcon() {
/* 108 */     return (this.econ != null);
/*     */   }
/*     */   
/*     */   public boolean useVaultPerms() {
/* 112 */     return (this.perms != null);
/*     */   }
/*     */   
/*     */   public String getVaultVersion() {
/* 116 */     return Bukkit.getServer().getPluginManager().getPlugin("Vault")
/* 117 */       .getDescription().getVersion();
/*     */   }
/*     */   
/*     */   public String[] getGroups(Player p) {
/* 121 */     if (this.perms.getPlayerGroups(p) != null) {
/* 122 */       return this.perms.getPlayerGroups(p);
/*     */     }
/* 124 */     return new String[] { "" };
/*     */   }
/*     */   
/*     */   public String getMainGroup(Player p) {
/* 128 */     if (this.perms.getPrimaryGroup(p) != null) {
/* 129 */       return String.valueOf(this.perms.getPrimaryGroup(p));
/*     */     }
/* 131 */     return "";
/*     */   }
/*     */   
/*     */   public boolean opHasPermission(Player p, String perm) {
/* 135 */     if (this.perms.getPrimaryGroup(p) != null)
/*     */     {
/* 137 */       return this.perms.groupHas(p.getWorld(), this.perms.getPrimaryGroup(p), perm);
/*     */     }
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hasPerm(Player p, String perm) {
/* 143 */     if (this.perms != null) {
/* 144 */       return this.perms.has(p, perm);
/*     */     }
/* 146 */     return p.hasPermission(perm);
/*     */   }
/*     */   
/*     */   public String[] getServerGroups() {
/* 150 */     if (this.perms.getGroups() != null) {
/* 151 */       return this.perms.getGroups();
/*     */     }
/* 153 */     return new String[] { "" };
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\hooks\VaultHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */