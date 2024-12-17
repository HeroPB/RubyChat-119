/*     */ package me.clip.deluxechat.objects;
/*     */ 
/*     */ import java.util.List;
/*     */ import me.clip.deluxechat.DeluxeChat;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.permissions.Permission;
/*     */ import org.bukkit.permissions.PermissionDefault;
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
/*     */ public class DeluxeFormat
/*     */ {
/*     */   private String identifier;
/*     */   private int index;
/*     */   private String channel;
/*     */   private String prefix;
/*     */   private String nameColor;
/*     */   private String name;
/*     */   private String suffix;
/*     */   private String chatColor;
/*     */   private boolean showChannelTooltip;
/*     */   private boolean showNameTooltip;
/*     */   private boolean showPreTooltip;
/*     */   private boolean showSuffixTooltip;
/*     */   private List<String> channelTooltip;
/*     */   private List<String> prefixTooltip;
/*     */   private List<String> nameTooltip;
/*     */   private List<String> suffixTooltip;
/*     */   private boolean useChannelClick;
/*     */   private boolean usePreClick;
/*     */   private boolean useNameClick;
/*     */   private boolean useSuffixClick;
/*     */   private String channelClickCommand;
/*     */   private String preClickCmd;
/*     */   private String nameClickCmd;
/*     */   private String suffixClickCmd;
/*     */   
/*     */   public DeluxeFormat(String identifier, int index) {
/*  55 */     setIndex(index);
/*  56 */     setIdentifier(identifier);
/*     */     
/*  58 */     setPrefix("");
/*  59 */     setNameColor("&b");
/*  60 */     setName("%displayname%");
/*  61 */     setSuffix("&b: ");
/*  62 */     setChatColor("&f");
/*     */   }
/*     */   
/*     */   public static DeluxeFormat newInstance(DeluxeFormat format) {
/*  66 */     DeluxeFormat copy = new DeluxeFormat(format.getIdentifier(), format.getIndex());
/*  67 */     copy.setChannel(format.getChannel());
/*  68 */     copy.setUseChannelClick(format.useChannelClick());
/*  69 */     copy.setChannelClickCommand(format.getChannelClickCommand());
/*  70 */     copy.setShowChannelTooltip(format.showChannelTooltip());
/*  71 */     copy.setChannelTooltip(format.getChannelTooltip());
/*  72 */     copy.setPrefix(format.getPrefix());
/*  73 */     copy.setUsePreClick(format.usePreClick());
/*  74 */     copy.setPreClickCmd(format.getPreClickCmd());
/*  75 */     copy.setShowPreTooltip(format.showPreTooltip());
/*  76 */     copy.setPrefixTooltip(format.getPrefixTooltip());
/*  77 */     copy.setNameColor(format.getNameColor());
/*  78 */     copy.setName(format.getName());
/*  79 */     copy.setUseNameClick(format.useNameClick());
/*  80 */     copy.setNameClickCmd(format.getNameClickCmd());
/*  81 */     copy.setShowNameTooltip(format.showNameTooltip());
/*  82 */     copy.setNameTooltip(format.getNameTooltip());
/*  83 */     copy.setSuffix(format.getSuffix());
/*  84 */     copy.setUseSuffixClick(format.useSuffixClick());
/*  85 */     copy.setSuffixClickCmd(format.getSuffixClickCmd());
/*  86 */     copy.setShowSuffixTooltip(format.showSuffixTooltip());
/*  87 */     copy.setSuffixTooltip(format.getSuffixTooltip());
/*  88 */     copy.setChatColor(format.getChatColor());
/*  89 */     return copy;
/*     */   }
/*     */   
/*     */   public int getIndex() {
/*  93 */     return this.index;
/*     */   }
/*     */   
/*     */   public void setIndex(int index) {
/*  97 */     this.index = index;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 101 */     return this.prefix;
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix) {
/* 105 */     this.prefix = prefix;
/*     */   }
/*     */   
/*     */   public String getSuffix() {
/* 109 */     return this.suffix;
/*     */   }
/*     */   
/*     */   public void setSuffix(String suffix) {
/* 113 */     this.suffix = suffix;
/*     */   }
/*     */   
/*     */   public List<String> getNameTooltip() {
/* 117 */     return this.nameTooltip;
/*     */   }
/*     */   
/*     */   public void setNameTooltip(List<String> tooltip) {
/* 121 */     this.nameTooltip = tooltip;
/*     */   }
/*     */   
/*     */   public String getIdentifier() {
/* 125 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void setIdentifier(String identifier) {
/* 129 */     this.identifier = identifier;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameColor() {
/* 134 */     return this.nameColor;
/*     */   }
/*     */   
/*     */   public void setNameColor(String nameColor) {
/* 138 */     this.nameColor = nameColor;
/*     */   }
/*     */   
/*     */   public String getChatColor() {
/* 142 */     return this.chatColor;
/*     */   }
/*     */   
/*     */   public void setChatColor(String chatColor) {
/* 146 */     this.chatColor = chatColor;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 150 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 154 */     this.name = name;
/*     */   }
/*     */   
/*     */   public List<String> getPrefixTooltip() {
/* 158 */     return this.prefixTooltip;
/*     */   }
/*     */   
/*     */   public void setPrefixTooltip(List<String> preTooltip) {
/* 162 */     this.prefixTooltip = preTooltip;
/*     */   }
/*     */   
/*     */   public List<String> getSuffixTooltip() {
/* 166 */     return this.suffixTooltip;
/*     */   }
/*     */   
/*     */   public void setSuffixTooltip(List<String> suffixTooltip) {
/* 170 */     this.suffixTooltip = suffixTooltip;
/*     */   }
/*     */   
/*     */   public boolean showNameTooltip() {
/* 174 */     return this.showNameTooltip;
/*     */   }
/*     */   
/*     */   public void setShowNameTooltip(boolean showNameTooltip) {
/* 178 */     this.showNameTooltip = showNameTooltip;
/*     */   }
/*     */   
/*     */   public boolean showPreTooltip() {
/* 182 */     return this.showPreTooltip;
/*     */   }
/*     */   
/*     */   public void setShowPreTooltip(boolean showPreTooltip) {
/* 186 */     this.showPreTooltip = showPreTooltip;
/*     */   }
/*     */   
/*     */   public boolean showSuffixTooltip() {
/* 190 */     return this.showSuffixTooltip;
/*     */   }
/*     */   
/*     */   public void setShowSuffixTooltip(boolean showSuffixTooltip) {
/* 194 */     this.showSuffixTooltip = showSuffixTooltip;
/*     */   }
/*     */   
/*     */   public boolean usePreClick() {
/* 198 */     return this.usePreClick;
/*     */   }
/*     */   
/*     */   public void setUsePreClick(boolean usePreClick) {
/* 202 */     this.usePreClick = usePreClick;
/*     */   }
/*     */   
/*     */   public boolean useNameClick() {
/* 206 */     return this.useNameClick;
/*     */   }
/*     */   
/*     */   public void setUseNameClick(boolean useNameClick) {
/* 210 */     this.useNameClick = useNameClick;
/*     */   }
/*     */   
/*     */   public boolean useSuffixClick() {
/* 214 */     return this.useSuffixClick;
/*     */   }
/*     */   
/*     */   public void setUseSuffixClick(boolean useSuffixClick) {
/* 218 */     this.useSuffixClick = useSuffixClick;
/*     */   }
/*     */   
/*     */   public String getPreClickCmd() {
/* 222 */     return this.preClickCmd;
/*     */   }
/*     */   
/*     */   public void setPreClickCmd(String preClickCmd) {
/* 226 */     this.preClickCmd = preClickCmd;
/*     */   }
/*     */   
/*     */   public String getNameClickCmd() {
/* 230 */     return this.nameClickCmd;
/*     */   }
/*     */   
/*     */   public void setNameClickCmd(String nameClickCmd) {
/* 234 */     this.nameClickCmd = nameClickCmd;
/*     */   }
/*     */   
/*     */   public String getSuffixClickCmd() {
/* 238 */     return this.suffixClickCmd;
/*     */   }
/*     */   
/*     */   public void setSuffixClickCmd(String suffixClickCmd) {
/* 242 */     this.suffixClickCmd = suffixClickCmd;
/*     */   }
/*     */   
/*     */   public boolean showChannelTooltip() {
/* 246 */     return this.showChannelTooltip;
/*     */   }
/*     */   
/*     */   public void setShowChannelTooltip(boolean showChannelTooltip) {
/* 250 */     this.showChannelTooltip = showChannelTooltip;
/*     */   }
/*     */   
/*     */   public List<String> getChannelTooltip() {
/* 254 */     return this.channelTooltip;
/*     */   }
/*     */   
/*     */   public void setChannelTooltip(List<String> channelTooltip) {
/* 258 */     this.channelTooltip = channelTooltip;
/*     */   }
/*     */   
/*     */   public boolean useChannelClick() {
/* 262 */     return this.useChannelClick;
/*     */   }
/*     */   
/*     */   public void setUseChannelClick(boolean useChannelClick) {
/* 266 */     this.useChannelClick = useChannelClick;
/*     */   }
/*     */   
/*     */   public String getChannelClickCommand() {
/* 270 */     return this.channelClickCommand;
/*     */   }
/*     */   
/*     */   public void setChannelClickCommand(String channelClickCommand) {
/* 274 */     this.channelClickCommand = channelClickCommand;
/*     */   }
/*     */   
/*     */   public String getChannel() {
/* 278 */     return this.channel;
/*     */   }
/*     */   
/*     */   public void setChannel(String channel) {
/* 282 */     this.channel = channel;
/*     */   }
/*     */   
/*     */   public void load() {
/* 286 */     if (Bukkit.getPluginManager().getPermission("chatformat." + getIdentifier()) == null) {
/*     */       
/*     */       try {
/* 289 */         Permission perm = new Permission("chatformat." + getIdentifier(), PermissionDefault.FALSE);
/* 290 */         Bukkit.getPluginManager().addPermission(perm);
/* 291 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */     }
/*     */ 
/*     */     
/* 295 */     DeluxeChat.getFormats().put(Integer.valueOf(this.index), this);
/*     */   }
/*     */   
/*     */   public void unload() {
/* 299 */     if (Bukkit.getPluginManager().getPermission("chatformat." + getIdentifier()) != null)
/*     */       
/*     */       try {
/* 302 */         Bukkit.getPluginManager().removePermission("chatformat." + getIdentifier());
/* 303 */       } catch (IllegalArgumentException illegalArgumentException) {} 
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\objects\DeluxeFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */