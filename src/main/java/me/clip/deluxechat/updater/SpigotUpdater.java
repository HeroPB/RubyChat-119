/*    */ package me.clip.deluxechat.updater;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.net.ssl.HttpsURLConnection;
/*    */ import me.clip.deluxechat.DeluxeChat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpigotUpdater
/*    */ {
/*    */   private DeluxeChat plugin;
/* 18 */   final int resource = 1277;
/* 19 */   private static String latestVersion = "";
/*    */   private static boolean updateAvailable = false;
/*    */   
/*    */   public SpigotUpdater(DeluxeChat i) {
/* 23 */     this.plugin = i;
/*    */   }
/*    */ 
/*    */   
/*    */   private String getSpigotVersion() {
/*    */     try {
/* 29 */       HttpsURLConnection con = (HttpsURLConnection)(new URL("https://api.spigotmc.org/legacy/update.php?resource=1277")).openConnection();
/* 30 */       con.setDoOutput(true);
/* 31 */       con.setRequestMethod("GET");
/*    */       
/* 33 */       String version = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
/* 34 */       if (version.length() <= 7) {
/* 35 */         return version;
/*    */       }
/* 37 */     } catch (Exception ex) {
/* 38 */       System.out.println("----------------------------");
/* 39 */       System.out.println("     DeluxeChat Updater");
/* 40 */       System.out.println(" ");
/* 41 */       System.out.println("Could not connect to spigotmc.org");
/* 42 */       System.out.println("to check for updates! ");
/* 43 */       System.out.println(" ");
/* 44 */       System.out.println("----------------------------");
/*    */     } 
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   private boolean checkHigher(String currentVersion, String newVersion) {
/* 50 */     String current = toReadable(currentVersion);
/* 51 */     String newVers = toReadable(newVersion);
/* 52 */     return (current.compareTo(newVers) < 0);
/*    */   }
/*    */   
/*    */   public boolean checkUpdates() {
/* 56 */     if (getHighest() != "") {
/* 57 */       return true;
/*    */     }
/* 59 */     String version = getSpigotVersion();
/* 60 */     if (version != null && 
/* 61 */       checkHigher(this.plugin.getDescription().getVersion(), version)) {
/* 62 */       latestVersion = version;
/* 63 */       updateAvailable = true;
/* 64 */       return true;
/*    */     } 
/*    */     
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public static boolean updateAvailable() {
/* 71 */     return updateAvailable;
/*    */   }
/*    */   
/*    */   public static String getHighest() {
/* 75 */     return latestVersion;
/*    */   }
/*    */   
/*    */   private String toReadable(String version) {
/* 79 */     String[] split = Pattern.compile(".", 16).split(version
/* 80 */         .replace("v", ""));
/* 81 */     version = "";
/* 82 */     for (String s : split) {
/* 83 */       version = version + String.format("%4s", new Object[] { s });
/* 84 */     }  return version;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxecha\\updater\SpigotUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */