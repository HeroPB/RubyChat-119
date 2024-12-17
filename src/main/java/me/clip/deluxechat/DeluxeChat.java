/*     */
package me.clip.deluxechat;
/*  */

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.clip.deluxechat.bungee.BungeeMessageListener;
import me.clip.deluxechat.bungee.PMChan;
import me.clip.deluxechat.compatibility.*;
import me.clip.deluxechat.fanciful.FancyMessage;
import me.clip.deluxechat.hooks.*;
import me.clip.deluxechat.listeners.AsyncPlayerChatListener;
import me.clip.deluxechat.listeners.ChatToPlayerListener;
import me.clip.deluxechat.listeners.PlayerJoinListener;
import me.clip.deluxechat.messaging.PrivateMessageType;
import me.clip.deluxechat.objects.DeluxeFormat;
import me.clip.deluxechat.objects.DeluxePrivateMessageFormat;
import me.clip.deluxechat.updater.SpigotUpdater;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

/*     */
/*     */ public class DeluxeChat extends JavaPlugin {
    /*  46 */   private Logger log = getLogger();
    /*     */
    /*  48 */   protected static final TreeMap<Integer, DeluxeFormat> formats = new TreeMap<>();
    /*     */   protected static List<String> localPlayers;
    /*  50 */   protected static Map<String, String> blacklist = new HashMap<>();
    /*     */   protected static boolean blacklistIgnoreCase;
    /*     */   protected static boolean useBlacklist;
    /*     */   protected static boolean opsUseGroupFormat;
    /*     */   protected static boolean joinGlobal;
    /*     */   protected static String serverName;
    /*     */   protected static String serverPrefix;
    /*     */   protected static List<String> serverWhitelist;
    /*     */   protected static boolean useServerWhitelist;
    /*     */   protected static String booleanTrue;
    /*     */   protected static String booleanFalse;
    /*  61 */   protected static Map<String, String> inPrivateChat = new HashMap<>();
    /*  62 */   protected static List<String> socialSpy = new ArrayList<>();
    /*     */
    /*     */   protected static DeluxePrivateMessageFormat toSenderPmFormat;
    /*     */
    /*     */   protected static DeluxePrivateMessageFormat toRecipientPmFormat;
    /*     */   protected static String socialSpyFormat;
    /*     */   protected static boolean useRelationPlaceholders;
    /*     */   protected static SimpleDateFormat timestampFormat;
    /*     */   protected EssentialsHook essentials;
    /*     */   protected VaultHook vault;
    /*     */   protected PremiumVanishHook premiumVanish;
    /*  76 */   protected DeluxeConfig c = new DeluxeConfig(this);
    /*     */
    /*  78 */   protected DeluxeCommands commands = new DeluxeCommands(this);
    /*     */
    /*  80 */   private SpigotUpdater updater = null;
    /*     */
    /*     */   private CompatibilityManager chat;
    /*     */
    /*     */   private static BungeeMessageListener bungee;
    /*     */
    /*     */   private static boolean bungeePM;
    /*     */
    /*     */   private static DeluxeChat instance;
    /*     */
    /*  90 */   private ConfigWrapper messages = new ConfigWrapper(this, "", "messages.yml");
    /*     */
    /*  92 */   protected final List<String> pmToggle = new ArrayList<>();

    /*     */
    /*     */
    /*     */
    /*     */
    public void onEnable() {
        /*  97 */
        if (setupCompatibility()) {
            /*     */
            /*  99 */
            if (!hookPlaceholderAPI()) {
                /* 100 */
                System.out.println("----------------------------");
                /* 101 */
                System.out.println("     DeluxeChat ERROR");
                /* 102 */
                System.out.println(" ");
                /* 103 */
                System.out.println("As of DeluxeChat 1.12.0, PlaceholderAPI is now required to be installed on your server.");
                /* 104 */
                System.out.println("PlaceholderAPI now handles all parsing of placeholders within DeluxeChat.");
                /* 105 */
                System.out.println(" ");
                /* 106 */
                System.out.println("Download PlaceholderAPI at https://www.spigotmc.org/resources/placeholderapi.6245/");
                /* 107 */
                System.out.println("----------------------------");
                /* 108 */
                throw new RuntimeException("Failed to detect PlaceholderAPI!");
                /*     */
            }
            /* 110 */
            instance = this;
            /* 111 */
            this.c.loadConfigFile();
            /* 112 */
            getLog().info(this.c.loadFormats() + " formats loaded!");
            /* 113 */
            this.messages.createNewFile("Loading DeluxeChat messages.yml", "DeluxeChat language file\nYou can edit all the messages here!\nYou must restart for changes to take affect when editing this file!");
            /* 114 */
            loadMessages();
            /* 115 */
            blacklistIgnoreCase = this.c.blacklistIgnoreCase();
            /* 116 */
            useBlacklist = this.c.useBlacklist();
            /* 117 */
            joinGlobal = this.c.joinGlobal();
            /* 118 */
            booleanTrue = this.c.booleanTrue();
            /* 119 */
            booleanFalse = this.c.booleanFalse();
            /* 120 */
            useRelationPlaceholders = this.c.useRelationPlaceholders();
            /* 121 */
            socialSpyFormat = this.c.socialSpyFormat();
            /* 122 */
            this.c.loadPMFormats();
            /*     */
            try {
                /* 124 */
                timestampFormat = new SimpleDateFormat(this.c.timestampFormat());
                /* 125 */
            } catch (Exception e) {
                /* 126 */
                timestampFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                /*     */
            }
            /* 128 */
            if (useBlacklist) {
                /* 129 */
                getLog().info(this.c.loadBlacklist() + " entries added to the chat_filter!");
                /*     */
            }
            /* 131 */
            if (Bukkit.getPluginManager().isPluginEnabled("Towny") && Bukkit.getPluginManager().isPluginEnabled("TownyChat")) {
                /* 132 */
                /* 133 *//* 134 */
                getLog().info("TownyChat integration is enabled!");
                /*     */
            } else {
                /* 136 */
                Bukkit.getServer().getPluginManager().registerEvents((Listener) new AsyncPlayerChatListener(this), (Plugin) this);
                /*     */
            }
            /*     */
            /* 139 */
            if (useRelationPlaceholders) {
                /* 140 */
                Bukkit.getServer().getPluginManager().registerEvents((Listener) new ChatToPlayerListener(), (Plugin) this);
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /* 145 */
            if (Bukkit.getPluginManager().getPlugin("Essentials") != null) {
                /* 146 */
                this.essentials = new EssentialsHook(this);
                /* 147 */
                if (!this.essentials.hook()) {
                    /* 148 */
                    this.essentials = null;
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 152 */
            if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
                /* 153 */
                this.log.info("Detected Vault");
                /* 154 */
                this.vault = new VaultHook(this);
                /* 155 */
                if (!this.vault.hook()) {
                    /* 156 */
                    this.vault = null;
                    /*     */
                } else {
                    /* 158 */
                    this.log.info("Hooked into Vault");
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 180 */
            if (Bukkit.getPluginManager().getPlugin("SuperVanish") != null ||
                    /* 181 */         Bukkit.getPluginManager().getPlugin("PremiumVanish") != null) {
                /* 182 */
                this.premiumVanish = new PremiumVanishHook();
                /* 183 */
                this.log.info("Detected SuperVanish / PremiumVanish");
                /*     */
            }
            /*     */
            /* 186 */
            Bukkit.getServer().getPluginManager().registerEvents((Listener) new PlayerJoinListener(this), (Plugin) this);
            /*     */
            /* 188 */
            serverPrefix = ChatColor.translateAlternateColorCodes('&', this.c.serverPrefix());
            /* 189 */
            serverName = this.c.serverName();
            /*     */
            /* 191 */
            if (getConfig().getBoolean("bungeecord.enabled")) {
                /* 192 */
                bungeePM = this.c.bungeePMEnabled();
                /* 193 */
                joinGlobal = this.c.joinGlobal();
                /* 194 */
                localPlayers = new ArrayList<>();
                /* 195 */
                useServerWhitelist = this.c.useServerWhitelist();
                /* 196 */
                serverWhitelist = this.c.serverWhitelist();
                /* 197 */
                getLog().info("Bungee integration has been enabled!");
                /* 198 */
                getLog().info("Remember DeluxeChat.jar must be running on your bungee proxy server also for integration to work properly!!");
                /* 199 */
                bungee = new BungeeMessageListener(this);
                /* 200 */
                getCommand("gtoggle").setExecutor(new GlobalToggleCommand(this));
                /* 201 */
                getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, PMChan.CHAT.getChannelName());
                /* 202 */
                getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, PMChan.CHAT.getChannelName(), (PluginMessageListener) bungee);
                /* 203 */
                getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, PMChan.PM.getChannelName());
                /* 204 */
                getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, PMChan.PM.getChannelName(), (PluginMessageListener) bungee);
                /* 205 */
                getServer().getMessenger().registerOutgoingPluginChannel((Plugin) this, PMChan.SPY.getChannelName());
                /* 206 */
                getServer().getMessenger().registerIncomingPluginChannel((Plugin) this, PMChan.SPY.getChannelName(), (PluginMessageListener) bungee);
                /*     */
            } else {
                /* 208 */
                bungeePM = false;
                /*     */
            }
            /*     */
            /* 211 */
            getCommand("dchat").setExecutor(this.commands);
            /* 212 */
            getCommand("dchat").setTabCompleter(new DeluxeCommandsTabCompleter(this));
            /*     */
            /* 214 */
            if (getConfig().getBoolean("private_message.enabled", false)) {
                /* 215 */
                getCommand("msg").setExecutor(new MessageCommand(this));
                /* 216 */
                getCommand("msg").setTabCompleter(new MessageTabCompleter(this));
                /* 217 */
                getCommand("reply").setExecutor(new ReplyCommand(this));
                /* 218 */
                getCommand("socialspy").setExecutor(new SocialSpyCommand(this));
                /* 219 */
                getCommand("msgtoggle").setExecutor(new PmToggleCommand(this));
                /*     */
            }
            /*     */
            /* 222 */
            if (getConfig().getBoolean("check_updates")) {
                /* 223 */
                this.updater = new SpigotUpdater(this);
                /* 224 */
                getUpdater().checkUpdates();
                /* 225 */
                if (SpigotUpdater.updateAvailable()) {
                    /* 226 */
                    System.out.println("----------------------------");
                    /* 227 */
                    System.out.println("     DeluxeChat Updater");
                    /* 228 */
                    System.out.println(" ");
                    /* 229 */
                    System.out.println("An update for DeluxeChat has been found!");
                    /* 230 */
                    System.out.println("DeluxeChat " + SpigotUpdater.getHighest());
                    /* 231 */
                    System.out.println("You are running " + getDescription().getVersion());
                    /* 232 */
                    System.out.println(" ");
                    /* 233 */
                    System.out.println("Download at http://www.spigotmc.org/resources/deluxechat.1277/");
                    /* 234 */
                    System.out.println("----------------------------");
                    /*     */
                } else {
                    /* 236 */
                    System.out.println("----------------------------");
                    /* 237 */
                    System.out.println("     DeluxeChat Updater");
                    /* 238 */
                    System.out.println(" ");
                    /* 239 */
                    System.out.println("You are running " + getDescription().getVersion());
                    /* 240 */
                    System.out.println("The latest version");
                    /* 241 */
                    System.out.println("of DeluxeChat!");
                    /* 242 */
                    System.out.println(" ");
                    /* 243 */
                    System.out.println("----------------------------");
                    /*     */
                }
                /*     */
            }
            /*     */
        } else {
            /*     */
            /* 248 */
            System.out.println("----------------------------");
            /* 249 */
            getLog().warning("DeluxeChat version: " + getDescription().getVersion());
            /* 250 */
            System.out.println("");
            /* 251 */
            getLog().warning("This version of DeluxeChat is not compatible with your server version!");
            /* 252 */
            System.out.println("");
            /* 253 */
            getLog().warning("Find a specific version of DeluxeChat here:");
            /* 254 */
            getLog().warning("https://www.spigotmc.org/resources/deluxechat.1277/history");
            /* 255 */
            System.out.println("----------------------------");
            /* 256 */
            Bukkit.getPluginManager().disablePlugin((Plugin) this);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public void onDisable() {
        /*     */
        /* 267 */
        toSenderPmFormat = null;
        /* 268 */
        toRecipientPmFormat = null;
        /* 269 */
        blacklist = null;
        /* 270 */
        formats.clear();
        /* 271 */
        this.pmToggle.clear();
        /* 272 */
        localPlayers = null;
        /* 273 */
        inPrivateChat = null;
        /* 274 */
        socialSpy = null;
        /* 275 */
        instance = null;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private void loadMessages() {
        /* 280 */
        Lang.setFile(this.messages.getConfig());
        /*     */
        /* 282 */
        for (Lang value : Lang.values()) {
            /* 283 */
            this.messages.getConfig().addDefault(value.getPath(), value.getDefault());
            /*     */
        }
        /*     */
        /* 286 */
        this.messages.getConfig().options().copyDefaults(true);
        /* 287 */
        this.messages.saveConfig();
        /*     */
    }

    /*     */
    /*     */
    public CompatibilityManager getChat() {
        /* 291 */
        return this.chat;
        /*     */
    }

    /*     */
    /*     */
    public SpigotUpdater getUpdater() {
        /* 295 */
        return this.updater;
        /*     */
    }

    /*     */
    /*     */
    public static Map<Integer, DeluxeFormat> getFormats() {
        /* 299 */
        return formats;
        /*     */
    }

    /*     */
    /*     */
    private boolean hookPlaceholderAPI() {
        /* 303 */
        return (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null);
        /*     */
    }

    /*     */
    private boolean setupCompatibility() {
        /*     */
        String version;
        /* 307 */
        CompatAPIImpl compatAPIImpl = new CompatAPIImpl();
        /*     */
        /*     */
        try {
            /* 310 */
            version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            /* 311 */
        } catch (ArrayIndexOutOfBoundsException ex) {
            /* 312 */
            return false;
            /*     */
        }
            /* 315 */
        this.chat = new Spigot_1_19_Chat(compatAPIImpl);
        /* 345 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public DeluxeFormat getTestFormat(String formatName) {
        /* 350 */
        Iterator<DeluxeFormat> f = getFormats().values().iterator();
        /*     */
        /* 352 */
        while (f.hasNext()) {
            /*     */
            /* 354 */
            DeluxeFormat df = f.next();
            /*     */
            /* 356 */
            if (df.getIdentifier().equalsIgnoreCase(formatName)) {
                /* 357 */
                return DeluxeFormat.newInstance(df);
                /*     */
            }
            /*     */
        }
        /*     */
        /* 361 */
        return null;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public DeluxeFormat getPlayerFormat(Player p) {
        /* 366 */
        DeluxeFormat format = null;
        /*     */
        /*     */
        /* 369 */
        if (p.isOp() && opsUseGroupFormat && this.vault != null && this.vault.useVaultPerms()) {
            /*     */
            /* 371 */
            Iterator<DeluxeFormat> f = formats.values().iterator();
            /*     */
            /* 373 */
            while (f.hasNext()) {
                /*     */
                /* 375 */
                DeluxeFormat df = f.next();
                /*     */
                /* 377 */
                if (this.vault.opHasPermission(p, "chatformat." + df.getIdentifier())) {
                    /*     */
                    /* 379 */
                    format = df;
                    /*     */
                    /*     */
                    /*     */
                    /*     */
                    break;
                    /*     */
                }
                /*     */
            }
            /*     */
        } else {
            /* 387 */
            Iterator<DeluxeFormat> f = formats.values().iterator();
            /*     */
            /* 389 */
            while (f.hasNext()) {
                /*     */
                /* 391 */
                DeluxeFormat df = f.next();
                /*     */
                /* 393 */
                if (p.hasPermission("chatformat." + df.getIdentifier())) {
                    /*     */
                    /* 395 */
                    format = df;
                    /*     */
                    /*     */
                    break;
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /* 402 */
        if (format == null) {
            /*     */
            /* 404 */
            format = formats.get(formats.lastKey());
            /*     */
            /* 406 */
            if (format == null) {
                /* 407 */
                format = new DeluxeFormat("default", 2147483647);
                /* 408 */
                format.setChannel("");
                /* 409 */
                format.setPrefix("");
                /* 410 */
                format.setNameColor("");
                /* 411 */
                format.setName("%player_name%");
                /* 412 */
                format.setSuffix(" &8> &r");
                /* 413 */
                format.setChatColor("");
                /* 414 */
                format.setShowChannelTooltip(false);
                /* 415 */
                format.setChannelTooltip(null);
                /* 416 */
                format.setShowPreTooltip(false);
                /* 417 */
                format.setPrefixTooltip(null);
                /* 418 */
                format.setShowNameTooltip(false);
                /* 419 */
                format.setNameTooltip(null);
                /* 420 */
                format.setShowSuffixTooltip(false);
                /* 421 */
                format.setSuffixTooltip(null);
                /* 422 */
                format.setUsePreClick(false);
                /* 423 */
                format.setPreClickCmd(null);
                /* 424 */
                format.setUseNameClick(true);
                /* 425 */
                format.setNameClickCmd("/msg %player_name% ");
                /* 426 */
                format.setUseSuffixClick(false);
                /* 427 */
                format.setSuffixClickCmd(null);
                /*     */
            }
            /*     */
        }
        /*     */
        /* 431 */
        return format;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public boolean isMuted(Player p) {
        /* 436 */
        boolean muted = false;
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 442 */
        if (this.essentials != null) {
            /* 443 */
            muted = this.essentials.isMuted(p);
            /*     */
        }
        /*     */
        /* 446 */
        return muted;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public FancyMessage getBungeePrivateMessageFormat(Player sender, DeluxePrivateMessageFormat pmFormat) {
        /* 452 */
        if (pmFormat == null) {
            /* 453 */
            return null;
            /*     */
        }
        /*     */
        /* 456 */
        String format = pmFormat.getFormat();
        /*     */
        /* 458 */
        format = PlaceholderAPI.setPlaceholders(sender, format);
        /*     */
        /* 460 */
        FancyMessage msg = new FancyMessage(format);
        /*     */
        /* 462 */
        String clr = DeluxeUtil.getLastColor(format);
        /*     */
        /* 464 */
        msg.setLastColor(clr);
        /*     */
        /* 466 */
        msg.setChatColor(ChatColor.translateAlternateColorCodes('&', pmFormat.getChatColor()));
        /*     */
        /* 468 */
        if (pmFormat.getTooltip() != null && !pmFormat.getTooltip().isEmpty())
            /*     */ {
            /* 470 */
            msg.tooltip(pmFormat.getTooltip());
            /*     */
        }
        /*     */
        /* 473 */
        if (pmFormat.getClickAction() != null) {
            /*     */
            /* 475 */
            String chCmd = pmFormat.getClickAction();
            /*     */
            /* 477 */
            if (chCmd.startsWith("[EXECUTE]")) {
                /*     */
                /* 479 */
                chCmd = chCmd.replace("[EXECUTE]", "");
                /* 480 */
                msg.command(chCmd);
                /* 481 */
            } else if (chCmd.startsWith("[URL]")) {
                /*     */
                /* 483 */
                chCmd = chCmd.replace("[URL]", "");
                /*     */
                /* 485 */
                if (!chCmd.startsWith("http://") &&
                        /* 486 */           !chCmd.startsWith("https://")) {
                    /* 487 */
                    chCmd = "http://" + chCmd;
                    /*     */
                }
                /*     */
                /*     */
                /* 491 */
                msg.link(chCmd);
                /*     */
            } else {
                /* 493 */
                msg.suggest(chCmd);
                /*     */
            }
            /*     */
        }
        /* 496 */
        msg.then(ChatColor.translateAlternateColorCodes('&', "&f"));
        /* 497 */
        return msg;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public FancyMessage getPrivateMessageFormat(DeluxePrivateMessageFormat pmFormat) {
        /* 502 */
        if (pmFormat == null) {
            /* 503 */
            return null;
            /*     */
        }
        /*     */
        /* 506 */
        String format = pmFormat.getFormat();
        /*     */
        /* 508 */
        format = ChatColor.translateAlternateColorCodes('&', format);
        /*     */
        /* 510 */
        FancyMessage msg = new FancyMessage(format);
        /*     */
        /* 512 */
        String clr = DeluxeUtil.getLastColor(format);
        /*     */
        /* 514 */
        msg.setLastColor(clr);
        /*     */
        /* 516 */
        msg.setChatColor(ChatColor.translateAlternateColorCodes('&', pmFormat.getChatColor()));
        /*     */
        /* 518 */
        if (pmFormat.getTooltip() != null && !pmFormat.getTooltip().isEmpty())
            /*     */ {
            /* 520 */
            msg.tooltip(pmFormat.getTooltip());
            /*     */
        }
        /*     */
        /* 523 */
        if (pmFormat.getClickAction() != null) {
            /*     */
            /* 525 */
            String chCmd = pmFormat.getClickAction();
            /*     */
            /* 527 */
            if (chCmd.startsWith("[EXECUTE]")) {
                /*     */
                /* 529 */
                chCmd = chCmd.replace("[EXECUTE]", "");
                /* 530 */
                msg.command(chCmd);
                /* 531 */
            } else if (chCmd.startsWith("[URL]")) {
                /*     */
                /* 533 */
                chCmd = chCmd.replace("[URL]", "");
                /*     */
                /* 535 */
                if (!chCmd.startsWith("http://") &&
                        /* 536 */           !chCmd.startsWith("https://")) {
                    /* 537 */
                    chCmd = "http://" + chCmd;
                    /*     */
                }
                /*     */
                /*     */
                /* 541 */
                msg.link(chCmd);
                /*     */
            } else {
                /* 543 */
                msg.suggest(chCmd);
                /*     */
            }
            /*     */
        }
        /* 546 */
        msg.then(ChatColor.translateAlternateColorCodes('&', "&f"));
        /* 547 */
        return msg;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    public FancyMessage getFancyChatFormat(Player p, DeluxeFormat playerFormat) {
        /* 553 */
        if (playerFormat == null) {
            /* 554 */
            return null;
            /*     */
        }
        /*     */
        /* 557 */
        FancyMessage fm = null;
        /*     */
        /* 559 */
        String clr = "";
        /*     */
        /* 561 */
        String channel = playerFormat.getChannel();
        /*     */
        /* 563 */
        if (channel != null && !channel.isEmpty()) {
            /*     */
            /* 565 */
            channel = PlaceholderAPI.setPlaceholders(p, channel);
            /*     */
            /* 567 */
            clr = DeluxeUtil.getLastColor(channel);
            /*     */
            /* 569 */
            fm = new FancyMessage(ChatColor.translateAlternateColorCodes('&', channel));
            /*     */
            /* 571 */
            if (playerFormat.useChannelClick() && playerFormat.getChannelClickCommand() != null) {
                /*     */
                /* 573 */
                String chCmd = PlaceholderAPI.setPlaceholders(p, playerFormat.getChannelClickCommand());
                /*     */
                /* 575 */
                if (chCmd.startsWith("[EXECUTE]")) {
                    /*     */
                    /* 577 */
                    chCmd = chCmd.replace("[EXECUTE]", "");
                    /* 578 */
                    fm.command(ChatColor.translateAlternateColorCodes('&', chCmd));
                    /* 579 */
                } else if (chCmd.startsWith("[URL]")) {
                    /*     */
                    /* 581 */
                    chCmd = chCmd.replace("[URL]", "");
                    /*     */
                    /* 583 */
                    if (!chCmd.startsWith("http://") &&
                            /* 584 */             !chCmd.startsWith("https://")) {
                        /* 585 */
                        chCmd = "http://" + chCmd;
                        /*     */
                    }
                    /*     */
                    /* 588 */
                    fm.link(chCmd);
                    /*     */
                } else {
                    /* 590 */
                    fm.suggest(ChatColor.translateAlternateColorCodes('&', chCmd));
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 594 */
            if (playerFormat.showChannelTooltip() && playerFormat.getChannelTooltip() != null)
                /*     */ {
                /* 596 */
                fm.tooltip(PlaceholderAPI.setPlaceholders(p, playerFormat.getChannelTooltip()));
                /*     */
            }
            /*     */
        }
        /*     */
        /* 600 */
        String prefix = playerFormat.getPrefix();
        /*     */
        /* 602 */
        if (prefix != null && !prefix.isEmpty()) {
            /*     */
            /* 604 */
            prefix = PlaceholderAPI.setPlaceholders(p, prefix);
            /*     */
            /* 606 */
            if (fm == null) {
                /* 607 */
                fm = new FancyMessage(ChatColor.translateAlternateColorCodes('&', prefix));
                /*     */
            } else {
                /* 609 */
                fm.then(ChatColor.translateAlternateColorCodes('&', clr + prefix));
                /*     */
            }
            /*     */
            /* 612 */
            clr = DeluxeUtil.getLastColor(prefix);
            /*     */
            /* 614 */
            if (playerFormat.usePreClick() && playerFormat.getPreClickCmd() != null) {
                /*     */
                /* 616 */
                String preCmd = PlaceholderAPI.setPlaceholders(p, playerFormat.getPreClickCmd());
                /*     */
                /* 618 */
                if (preCmd.startsWith("[EXECUTE]")) {
                    /* 619 */
                    preCmd = preCmd.replace("[EXECUTE]", "");
                    /* 620 */
                    fm.command(ChatColor.translateAlternateColorCodes('&', preCmd));
                    /* 621 */
                } else if (preCmd.startsWith("[URL]")) {
                    /*     */
                    /* 623 */
                    preCmd = preCmd.replace("[URL]", "");
                    /*     */
                    /* 625 */
                    if (!preCmd.startsWith("http://") &&
                            /* 626 */             !preCmd.startsWith("https://")) {
                        /* 627 */
                        preCmd = "http://" + preCmd;
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 631 */
                    fm.link(preCmd);
                    /*     */
                } else {
                    /*     */
                    /* 634 */
                    fm.suggest(ChatColor.translateAlternateColorCodes('&', preCmd));
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 638 */
            if (playerFormat.showPreTooltip() && playerFormat.getPrefixTooltip() != null)
                /*     */ {
                /* 640 */
                fm.tooltip(PlaceholderAPI.setPlaceholders(p, playerFormat.getPrefixTooltip()));
                /*     */
            }
            /*     */
        }
        /*     */
        /* 644 */
        String name = playerFormat.getName();
        /*     */
        /* 646 */
        if (name != null && !name.isEmpty()) {
            /*     */
            /* 648 */
            name = PlaceholderAPI.setPlaceholders(p, playerFormat.getName());
            /*     */
            /* 650 */
            String namecolor = "";
            /*     */
            /* 652 */
            if (playerFormat.getNameColor() != null) {
                /* 653 */
                namecolor = PlaceholderAPI.setPlaceholders(p, playerFormat.getNameColor());
                /*     */
            }
            /*     */
            /* 656 */
            if (fm == null) {
                /* 657 */
                fm = new FancyMessage(ChatColor.translateAlternateColorCodes('&', namecolor + name));
                /*     */
            } else {
                /* 659 */
                fm.then(ChatColor.translateAlternateColorCodes('&', clr + namecolor + name));
                /*     */
            }
            /*     */
            /* 662 */
            clr = DeluxeUtil.getLastColor(namecolor + name);
            /*     */
            /* 664 */
            if (playerFormat.useNameClick() && playerFormat.getNameClickCmd() != null) {
                /*     */
                /* 666 */
                String nameCmd = PlaceholderAPI.setPlaceholders(p, playerFormat.getNameClickCmd());
                /*     */
                /* 668 */
                if (nameCmd.startsWith("[EXECUTE]")) {
                    /*     */
                    /* 670 */
                    nameCmd = nameCmd.replace("[EXECUTE]", "");
                    /* 671 */
                    fm.command(ChatColor.translateAlternateColorCodes('&', nameCmd));
                    /* 672 */
                } else if (nameCmd.startsWith("[URL]")) {
                    /*     */
                    /* 674 */
                    nameCmd = nameCmd.replace("[URL]", "");
                    /*     */
                    /* 676 */
                    if (!nameCmd.startsWith("http://") &&
                            /* 677 */             !nameCmd.startsWith("https://")) {
                        /* 678 */
                        nameCmd = "http://" + nameCmd;
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 682 */
                    fm.link(nameCmd);
                    /*     */
                } else {
                    /*     */
                    /* 685 */
                    fm.suggest(ChatColor.translateAlternateColorCodes('&', nameCmd));
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 689 */
            if (playerFormat.showNameTooltip() && playerFormat.getNameTooltip() != null)
                /*     */ {
                /* 691 */
                fm.tooltip(PlaceholderAPI.setPlaceholders(p, playerFormat.getNameTooltip()));
                /*     */
            }
            /*     */
        }
        /*     */
        /* 695 */
        String suffix = playerFormat.getSuffix();
        /*     */
        /* 697 */
        if (suffix != null && !suffix.isEmpty()) {
            /*     */
            /* 699 */
            suffix = PlaceholderAPI.setPlaceholders(p, playerFormat.getSuffix());
            /*     */
            /* 701 */
            if (fm == null) {
                /* 702 */
                fm = new FancyMessage(ChatColor.translateAlternateColorCodes('&', suffix));
                /*     */
            } else {
                /* 704 */
                fm.then(ChatColor.translateAlternateColorCodes('&', clr + suffix));
                /*     */
            }
            /*     */
            /* 707 */
            clr = DeluxeUtil.getLastColor(clr + suffix);
            /*     */
            /* 709 */
            if (playerFormat.useSuffixClick() && playerFormat.getSuffixClickCmd() != null) {
                /*     */
                /* 711 */
                String suffixCmd = PlaceholderAPI.setPlaceholders(p, playerFormat.getSuffixClickCmd());
                /*     */
                /* 713 */
                if (suffixCmd.startsWith("[EXECUTE]")) {
                    /*     */
                    /* 715 */
                    suffixCmd = suffixCmd.replace("[EXECUTE]", "");
                    /* 716 */
                    fm.command(ChatColor.translateAlternateColorCodes('&', suffixCmd));
                    /* 717 */
                } else if (suffixCmd.startsWith("[URL]")) {
                    /*     */
                    /* 719 */
                    suffixCmd = suffixCmd.replace("[URL]", "");
                    /*     */
                    /* 721 */
                    if (!suffixCmd.startsWith("http://") &&
                            /* 722 */             !suffixCmd.startsWith("https://")) {
                        /* 723 */
                        suffixCmd = "http://" + suffixCmd;
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 727 */
                    fm.link(suffixCmd);
                    /*     */
                } else {
                    /*     */
                    /* 730 */
                    fm.suggest(ChatColor.translateAlternateColorCodes('&', suffixCmd));
                    /*     */
                }
                /*     */
            }
            /* 733 */
            if (playerFormat.showSuffixTooltip() && playerFormat.getSuffixTooltip() != null)
                /*     */ {
                /* 735 */
                fm.tooltip(PlaceholderAPI.setPlaceholders(p, playerFormat.getSuffixTooltip()));
                /*     */
            }
            /*     */
        }
        /*     */
        /* 739 */
        String cc = "";
        /*     */
        /* 741 */
        if (playerFormat.getChatColor() != null && !playerFormat.getChatColor().isEmpty()) {
            /* 742 */
            cc = PlaceholderAPI.setPlaceholders(p, playerFormat.getChatColor());
            /*     */
        }
        /*     */
        /* 745 */
        fm.setLastColor(ChatColor.translateAlternateColorCodes('&', clr));
        /*     */
        /* 747 */
        if (cc != null && !cc.isEmpty())
            /*     */ {
            /* 749 */
            fm.setChatColor(ChatColor.translateAlternateColorCodes('&', clr + cc));
            /*     */
        }
        /*     */
        /* 752 */
        return fm;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static boolean useBungee() {
        /* 760 */
        return (bungee != null);
        /*     */
    }

    /*     */
    /*     */
    public static boolean enableSocialSpy(String uuid) {
        /* 764 */
        if (socialSpy == null) {
            /* 765 */
            socialSpy = new ArrayList<>();
            /* 766 */
            socialSpy.add(uuid);
            /* 767 */
            return true;
            /*     */
        }
        /* 769 */
        if (socialSpy.contains(uuid)) {
            /* 770 */
            return false;
            /*     */
        }
        /* 772 */
        socialSpy.add(uuid);
        /* 773 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    public static boolean enableSocialSpy(Player p) {
        /* 777 */
        return enableSocialSpy(p.getUniqueId().toString());
        /*     */
    }

    /*     */
    /*     */
    public static boolean disableSocialSpy(String uuid) {
        /* 781 */
        if (socialSpy == null) {
            /* 782 */
            socialSpy = new ArrayList<>();
            /* 783 */
            return false;
            /*     */
        }
        /* 785 */
        if (!socialSpy.contains(uuid)) {
            /* 786 */
            return false;
            /*     */
        }
        /* 788 */
        socialSpy.remove(uuid);
        /* 789 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    public static boolean disableSocialSpy(Player p) {
        /* 793 */
        return disableSocialSpy(p.getUniqueId().toString());
        /*     */
    }

    /*     */
    /*     */
    public static boolean inSocialSpy(String uuid) {
        /* 797 */
        if (socialSpy == null) {
            /* 798 */
            return false;
            /*     */
        }
        /* 800 */
        return socialSpy.contains(uuid);
        /*     */
    }

    /*     */
    /*     */
    public static boolean inSocialSpy(Player p) {
        /* 804 */
        return inSocialSpy(p.getUniqueId().toString());
        /*     */
    }

    /*     */
    /*     */
    public boolean setLocal(String uuid) {
        /* 808 */
        if (localPlayers == null) {
            /* 809 */
            localPlayers = new ArrayList<>();
            /* 810 */
            localPlayers.add(uuid);
            /* 811 */
            return true;
            /*     */
        }
        /* 813 */
        if (localPlayers.contains(uuid)) {
            /* 814 */
            return false;
            /*     */
        }
        /* 816 */
        localPlayers.add(uuid);
        /* 817 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    public boolean setGlobal(String uuid) {
        /* 821 */
        if (localPlayers == null) {
            /* 822 */
            localPlayers = new ArrayList<>();
            /* 823 */
            return false;
            /*     */
        }
        /* 825 */
        if (!localPlayers.contains(uuid)) {
            /* 826 */
            return false;
            /*     */
        }
        /* 828 */
        localPlayers.remove(uuid);
        /* 829 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    public static boolean isLocal(String uuid) {
        /* 833 */
        if (localPlayers == null) {
            /* 834 */
            return false;
            /*     */
        }
        /* 836 */
        return localPlayers.contains(uuid);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static void forwardString(Player p, String format, String message, boolean override, String serversToSendTo) {
        /* 841 */
        if (!useBungee()) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /*     */
        /*     */
        try {
            /* 847 */
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            /* 848 */
            out.writeUTF(format);
            /* 849 */
            out.writeUTF(message);
            /* 850 */
            out.writeBoolean(override);
            /* 851 */
            out.writeUTF(serverName);
            /*     */
            /* 853 */
            p.sendPluginMessage((Plugin) instance, PMChan.CHAT.getChannelName(), out.toByteArray());
            /*     */
        }
        /* 855 */ catch (Exception ex) {
            /* 856 */
            ex.printStackTrace();
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static void forwardPm(Player p, PrivateMessageType type, String recipient, String format, String jsonMessage, String rawMessage) {
        /* 862 */
        if (!useBungee()) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /*     */
        /*     */
        try {
            /* 868 */
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            /* 869 */
            out.writeUTF(type.getType());
            /* 870 */
            out.writeUTF(p.getName());
            /* 871 */
            out.writeUTF(recipient);
            /* 872 */
            out.writeUTF(format);
            /* 873 */
            out.writeUTF(jsonMessage);
            /* 874 */
            out.writeUTF(rawMessage);
            /* 875 */
            p.sendPluginMessage((Plugin) instance, PMChan.PM.getChannelName(), out.toByteArray());
            /*     */
        }
        /* 877 */ catch (Exception ex) {
            /* 878 */
            ex.printStackTrace();
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public static boolean globalOnJoin() {
        /* 883 */
        return joinGlobal;
        /*     */
    }

    /*     */
    /*     */
    public static String getServerName() {
        /* 887 */
        return (serverName != null) ? serverName : "";
        /*     */
    }

    /*     */
    /*     */
    public static String getServerPrefix() {
        /* 891 */
        return (serverPrefix != null) ? serverPrefix : "";
        /*     */
    }

    /*     */
    /*     */
    public static boolean useRelationPlaceholders() {
        /* 895 */
        return useRelationPlaceholders;
        /*     */
    }

    /*     */
    /*     */
    public static boolean bungeePMEnabled() {
        /* 899 */
        return bungeePM;
        /*     */
    }

    /*     */
    /*     */
    public static SimpleDateFormat getTimestampFormat() {
        /* 903 */
        return timestampFormat;
        /*     */
    }

    /*     */
    /*     */
    public static String getBooleanTrue() {
        /* 907 */
        return (booleanTrue != null) ? booleanTrue : "true";
        /*     */
    }

    /*     */
    /*     */
    public static String getBooleanFalse() {
        /* 911 */
        return (booleanFalse != null) ? booleanFalse : "false";
        /*     */
    }

    /*     */
    /*     */
    public static String getSocialSpyFormat() {
        /* 915 */
        return socialSpyFormat;
        /*     */
    }

    /*     */
    /*     */
    public static boolean useServerWhitelist() {
        /* 919 */
        return useServerWhitelist;
        /*     */
    }

    /*     */
    /*     */
    public static List<String> getServerWhitelist() {
        /* 923 */
        return (serverWhitelist == null) ? new ArrayList<>() : serverWhitelist;
        /*     */
    }

    /*     */
    /*     */
    public static DeluxePrivateMessageFormat getToSenderPMFormat() {
        /* 927 */
        return DeluxePrivateMessageFormat.newInstance(toSenderPmFormat);
        /*     */
    }

    /*     */
    /*     */
    public static DeluxePrivateMessageFormat getToRecipientPMFormat() {
        /* 931 */
        return DeluxePrivateMessageFormat.newInstance(toRecipientPmFormat);
        /*     */
    }

    /*     */
    /*     */
    public static void setInPm(String player, String recip) {
        /* 935 */
        if (inPrivateChat == null) {
            /* 936 */
            inPrivateChat = new HashMap<>();
            /*     */
        }
        /* 938 */
        inPrivateChat.put(player, recip);
        /*     */
    }

    /*     */
    /*     */
    public static void removeFromPM(String player) {
        /* 942 */
        if (inPrivateChat == null) {
            /*     */
            return;
            /*     */
        }
        /* 945 */
        if (inPrivateChat.containsKey(player)) {
            /* 946 */
            inPrivateChat.remove(player);
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public static String getPmRecipient(String player) {
        /* 951 */
        if (inPrivateChat == null) {
            /* 952 */
            return null;
            /*     */
        }
        /* 954 */
        if (inPrivateChat.containsKey(player)) {
            /* 955 */
            return inPrivateChat.get(player);
            /*     */
        }
        /* 957 */
        return null;
        /*     */
    }

    /*     */
    /*     */
    public boolean hasPmDisabled(Player p) {
        /* 961 */
        return this.pmToggle.contains(p.getName());
        /*     */
    }

    /*     */
    /*     */
    public boolean disablePm(Player p) {
        /* 965 */
        return this.pmToggle.add(p.getName());
        /*     */
    }

    /*     */
    /*     */
    public boolean enablePm(Player p) {
        /* 969 */
        return this.pmToggle.remove(p.getName());
        /*     */
    }

    /*     */
    /*     */
    public Logger getLog() {
        /* 973 */
        return this.log;
        /*     */
    }

    /*     */
    /*     */
    public static DeluxeChat getInstance() {
        /* 977 */
        return instance;
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\DeluxeChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */