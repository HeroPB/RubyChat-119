/*    */ package me.clip.deluxechat;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import me.clip.deluxechat.objects.DeluxeFormat;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.TabCompleter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DeluxeCommandsTabCompleter
/*    */   implements TabCompleter
/*    */ {
/*    */   public DeluxeCommandsTabCompleter(DeluxeChat instance) {}
/*    */   
/*    */   public List<String> onTabComplete(CommandSender s, Command c, String label, String[] args) {
/* 22 */     if (!s.hasPermission("deluxechat.admin")) {
/* 23 */       return null;
/*    */     }
/*    */     
/* 26 */     if (args.length == 0) {
/* 27 */       return null;
/*    */     }
/*    */     
/* 30 */     if (args.length == 1) {
/* 31 */       return Arrays.asList(new String[] { "list", "test", "reload" });
/*    */     }
/*    */     
/* 34 */     if (args.length == 2 && 
/* 35 */       args[0].equals("test")) {
/* 36 */       return (List<String>)DeluxeChat.getFormats().values().stream().map(f -> f.getIdentifier()).collect(Collectors.toList());
/*    */     }
/*    */ 
/*    */     
/* 40 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\DeluxeCommandsTabCompleter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */