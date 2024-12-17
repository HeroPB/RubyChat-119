package me.clip.deluxechat.compatibility;

import java.util.Set;
import me.clip.deluxechat.fanciful.FancyMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface CompatibilityManager {
  String setHexColors(String paramString);
  
  String getLastHex(String paramString);
  
  String getVersion();
  
  void sendDirectChat(Player paramPlayer1, String paramString1, String paramString2, Player paramPlayer2, Player paramPlayer3);
  
  void sendDeluxeChat(Player paramPlayer, String paramString1, String paramString2, Set<Player> paramSet);
  
  void sendFancyMessage(CommandSender paramCommandSender, FancyMessage paramFancyMessage);
  
  void sendBungeeChat(String paramString1, String paramString2, boolean paramBoolean);
  
  void sendPrivateMessage(Player paramPlayer, String paramString1, String paramString2);
  
  String convertMsg(Player paramPlayer, String paramString);
  
  String convertPm(Player paramPlayer, String paramString);
}


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\compatibility\CompatibilityManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */