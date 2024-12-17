/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ 
/*    */ public class ConfigWrapper
/*    */ {
/*    */   private final JavaPlugin plugin;
/*    */   private FileConfiguration config;
/*    */   private File configFile;
/*    */   private final String folderName;
/*    */   private final String fileName;
/*    */   
/*    */   public ConfigWrapper(JavaPlugin instance, String folderName, String fileName) {
/* 20 */     this.plugin = instance;
/* 21 */     this.folderName = folderName;
/* 22 */     this.fileName = fileName;
/*    */   }
/*    */   
/*    */   public void createNewFile(String message, String header) {
/* 26 */     reloadConfig();
/* 27 */     saveConfig();
/* 28 */     loadConfig(header);
/*    */     
/* 30 */     if (message != null) {
/* 31 */       this.plugin.getLogger().info(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public FileConfiguration getConfig() {
/* 36 */     if (this.config == null) {
/* 37 */       reloadConfig();
/*    */     }
/* 39 */     return this.config;
/*    */   }
/*    */   
/*    */   public void loadConfig(String header) {
/* 43 */     this.config.options().header(header);
/* 44 */     this.config.options().copyDefaults(true);
/* 45 */     saveConfig();
/*    */   }
/*    */   
/*    */   public void reloadConfig() {
/* 49 */     if (this.configFile == null) {
/* 50 */       this.configFile = new File(this.plugin.getDataFolder() + this.folderName, this.fileName);
/*    */     }
/* 52 */     this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
/*    */   }
/*    */   
/*    */   public void saveConfig() {
/* 56 */     if (this.config == null || this.configFile == null) {
/*    */       return;
/*    */     }
/*    */     try {
/* 60 */       getConfig().save(this.configFile);
/* 61 */     } catch (IOException ex) {
/* 62 */       this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, ex);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\ConfigWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */