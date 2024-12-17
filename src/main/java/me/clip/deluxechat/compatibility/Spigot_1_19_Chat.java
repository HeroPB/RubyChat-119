/*     */
package me.clip.deluxechat.compatibility;
/*     */

import com.github.retrooper.packetevents.protocol.chat.message.ChatMessage_v1_19_3;
import me.clip.deluxechat.CompatAPI;
import me.clip.deluxechat.events.ChatToPlayerEvent;
import me.clip.deluxechat.events.DeluxeChatJSONEvent;
import me.clip.deluxechat.fanciful.FancyMessage;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.network.chat.ChatMessageType;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;

/*     */
public class Spigot_1_19_Chat implements CompatibilityManager {
    /*     */
    public Spigot_1_19_Chat(CompatAPI compat) {
        /*  27 */
        this.compat = compat;
        /*     */
    }

    /*     */   private final CompatAPI compat;

    /*     */
    /*     */
    public String getVersion() {
        /*  32 */
        return "v1_20_4_R1";
        /*     */
    }

    /*     */
    /*     */
    public String setHexColors(String message) {
        /*  36 */
        Matcher m = CompatAPI.HEX_PATTERN.matcher(message);
        /*  37 */
        while (m.find()) {
            /*  38 */
            String hex = m.group(1);
            /*  39 */
            ChatColor clr = ChatColor.of(hex);
            /*  40 */
            message = message.replace(hex, clr + "");
            /*     */
        }
        /*  42 */
        return message;
        /*     */
    }

    /*     */
    /*     */
    public String getLastHex(String message) {
        /*  46 */
        Matcher m = CompatAPI.HEX_PATTERN.matcher(message);
        /*  47 */
        String hex = null;
        /*  48 */
        while (m.find()) {
            /*  49 */
            hex = m.group(1);
            /*     */
        }
        /*  51 */
        return hex;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public String convertMsg(Player p, String message) {
        /*  58 */
        message = ChatColor.translateAlternateColorCodes('&', message);
        /*     */
        /*  60 */
        if (p.hasPermission("deluxechat.hex")) {
            /*  61 */
            message = setHexColors(message);
            /*     */
        }
        /*     */
        /*  64 */
        if (p.hasPermission("deluxechat.url")) {
            /*  65 */
            BaseComponent[] c = TextComponent.fromLegacyText(message);
            /*  66 */
            return ComponentSerializer.toString(c);
            /*     */
        }
        /*     */
        /*  69 */
        return this.compat.convertToJson(message);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public String convertPm(Player p, String message) {
        /*  75 */
        message = ChatColor.translateAlternateColorCodes('&', message);
        /*     */
        /*  77 */
        if (p.hasPermission("deluxechat.pm.hex")) {
            /*  78 */
            message = setHexColors(message);
            /*     */
        }
        /*     */
        /*  81 */
        if (p.hasPermission("deluxechat.pm.url")) {
            /*  82 */
            BaseComponent[] c = TextComponent.fromLegacyText(message);
            /*  83 */
            return ComponentSerializer.toString(c);
            /*     */
        }
        /*     */
        /*  86 */
        return this.compat.convertToJson(message);
        /*     */
    }

    /*     */
    /*     */
    public void sendPrivateMessage(Player p, String jsonFormat, String message) {
        IChatMutableComponent iChatMutableComponent = IChatBaseComponent.ChatSerializer.a(jsonFormat);
        IChatBaseComponent messageComponent = IChatBaseComponent.ChatSerializer.a(message);
        iChatMutableComponent = iChatMutableComponent.b(messageComponent);

        // Creazione del pacchetto per il sistema di chat
        ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(iChatMutableComponent, false);

        // Invio del pacchetto al giocatore
        ((CraftPlayer) p).getHandle().c.a(chatPacket, null);
    }
    public void sendDirectChat(Player p, String jsonFormat, String chatMessage, Player sender, Player recipient) {
        /* 100 */
        ChatToPlayerEvent event = new ChatToPlayerEvent(!Bukkit.isPrimaryThread(), sender, recipient, jsonFormat, chatMessage, false);
        /*     */
        /* 102 */
        Bukkit.getPluginManager().callEvent(event);
        /*     */
        /* 104 */
        if (event.getJSONFormat() == null || event
/* 105 */.getChatMessage() == null || event
/* 106 */.getJSONFormat().isEmpty() || event
/* 107 */.getChatMessage().isEmpty()) {
            return;
        }
        /*     */
        /* 111 */
        IChatMutableComponent iChatMutableComponent = IChatBaseComponent.ChatSerializer.a(jsonFormat);
        IChatBaseComponent messageComponent = IChatBaseComponent.ChatSerializer.a(event.getChatMessage());

// Copia i siblings e aggiungi il nuovo componente
        List<IChatBaseComponent> siblings = new ArrayList<>(iChatMutableComponent.c());
        siblings.add(messageComponent);

// Crea un nuovo IChatMutableComponent con i nuovi siblings
        IChatMutableComponent updatedComponent = IChatMutableComponent.a(iChatMutableComponent.b());
        for (IChatBaseComponent sibling : siblings) {
            updatedComponent.b(sibling);
        }

        // Creazione del pacchetto per il sistema di chat
        ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(updatedComponent, false);

        // Invio del pacchetto al giocatore
        ((CraftPlayer) recipient).getHandle().c.a(chatPacket, null);
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public void sendDeluxeChat(Player p, String jsonFormat, String chatMessage, Set<Player> recipients) {
        /* 120 */
        if (recipients == null) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /*     */
        /* 125 */
        DeluxeChatJSONEvent jsonEvent = new DeluxeChatJSONEvent(!Bukkit.isPrimaryThread(), p, jsonFormat, chatMessage, chatMessage);
        /*     */
        /* 127 */
        Bukkit.getPluginManager().callEvent((Event) jsonEvent);
        /*     */
        /* 129 */
        if (jsonEvent.getJSONFormat() == null || jsonEvent
/* 130 */.getJSONChatMessage() == null || jsonEvent
/* 131 */.getJSONFormat().isEmpty()) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /* 135 */
        IChatMutableComponent iChatMutableComponent = IChatBaseComponent.ChatSerializer.a(jsonFormat);
        IChatBaseComponent messageComponent = IChatBaseComponent.ChatSerializer.a(chatMessage);
        iChatMutableComponent = iChatMutableComponent.b(messageComponent);

        // Creazione del pacchetto per il sistema di chat

        /* 137 */
        for (Player recip : recipients) {
            /* 138 */
            ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(iChatMutableComponent, false);

            // Invio del pacchetto al giocatore
            ((CraftPlayer) recip).getHandle().c.a(chatPacket, null);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public void sendFancyMessage(CommandSender sender, FancyMessage fm) {
        /* 146 */
        if (!(sender instanceof Player)) {
            /* 147 */
            sender.sendMessage(fm.toOldMessageFormat());
            /*     */
            /*     */
            return;
            /*     */
        }
        /* 151 */
        Player player = (Player) sender;
        /*     */
        /*     */
        /*     */
        try {
            /* 155 */
            IChatMutableComponent iChatMutableComponent = IChatBaseComponent.ChatSerializer.a(setHexColors(fm.toJSONString()));
            /* 156 */
            ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(iChatMutableComponent, false);

            // Invio del pacchetto al giocatore
            ((CraftPlayer) player).getHandle().c.a(chatPacket, null);
            /*     */
        }
        /* 159 */ catch (IllegalArgumentException e) {
            /* 160 */
            Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public void sendBungeeChat(String format, String msg, boolean override) {
        /* 167 */
        IChatMutableComponent iChatMutableComponent = IChatBaseComponent.ChatSerializer.a(setHexColors(format));
        IChatBaseComponent messageComponent = IChatBaseComponent.ChatSerializer.a(msg);
        iChatMutableComponent = iChatMutableComponent.b(messageComponent);
        /* 169 */
        if (override) {
            /*     */
            /* 171 */
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                /* 172 */
                ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(iChatMutableComponent, false);

                // Invio del pacchetto al giocatore
                ((CraftPlayer) online).getHandle().c.a(chatPacket, null);
                /*     */
            }
            /*     */
            /*     */
        } else {
            /*     */
            /* 178 */
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                /*     */
                /* 180 */
                if (!this.compat.isLocal(online.getUniqueId().toString())) {
                    /* 181 */
                    ClientboundSystemChatPacket chatPacket = new ClientboundSystemChatPacket(iChatMutableComponent, false);

                    // Invio del pacchetto al giocatore
                    ((CraftPlayer) online).getHandle().c.a(chatPacket, null);
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }
    /*     */
}
