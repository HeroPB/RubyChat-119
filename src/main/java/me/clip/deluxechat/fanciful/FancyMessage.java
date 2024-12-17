/*     */ package me.clip.deluxechat.fanciful;
/*     */ 
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.stream.JsonWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.StringWriter;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerialization;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FancyMessage
/*     */   implements JsonRepresentedObject, Cloneable, Iterable<MessagePart>, ConfigurationSerializable
/*     */ {
/*     */   private List<MessagePart> messageParts;
/*     */   private String jsonString;
/*     */   private boolean dirty;
/*     */   private String lastColor;
/*     */   private String chatColor;
/*     */   
/*     */   static {
/*  67 */     ConfigurationSerialization.registerClass(FancyMessage.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage clone() throws CloneNotSupportedException {
/*  78 */     FancyMessage instance = (FancyMessage)super.clone();
/*  79 */     instance.messageParts = new ArrayList<>(this.messageParts.size());
/*  80 */     for (int i = 0; i < this.messageParts.size(); i++) {
/*  81 */       instance.messageParts.add(i, ((MessagePart)this.messageParts.get(i)).clone());
/*     */     }
/*  83 */     instance.dirty = false;
/*  84 */     instance.jsonString = null;
/*  85 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage(String firstPartText) {
/*  93 */     this(TextualComponent.rawText(firstPartText));
/*     */   }
/*     */   
/*     */   public FancyMessage(TextualComponent firstPartText) {
/*  97 */     this.messageParts = new ArrayList<>();
/*  98 */     this.messageParts.add(new MessagePart(firstPartText));
/*  99 */     this.jsonString = null;
/* 100 */     this.dirty = false;
/* 101 */     this.lastColor = "";
/*     */   }
/*     */   
/*     */   public String getLastColor() {
/* 105 */     if (this.lastColor == null) {
/* 106 */       return "";
/*     */     }
/* 108 */     return this.lastColor;
/*     */   }
/*     */   
/*     */   public void setLastColor(String color) {
/* 112 */     if (color != null) {
/* 113 */       this.lastColor = color;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage() {
/* 121 */     this((TextualComponent)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage text(String text) {
/* 131 */     MessagePart latest = latest();
/* 132 */     if (latest.hasText()) {
/* 133 */       throw new IllegalStateException("text for this message part is already set");
/*     */     }
/* 135 */     latest.text = TextualComponent.rawText(text);
/* 136 */     this.dirty = true;
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   public FancyMessage text(TextualComponent text) {
/* 141 */     MessagePart latest = latest();
/* 142 */     if (latest.hasText()) {
/* 143 */       throw new IllegalStateException("text for this message part is already set");
/*     */     }
/* 145 */     latest.text = text;
/* 146 */     this.dirty = true;
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage color(ChatColor color) {
/* 157 */     if (!color.isColor()) {
/* 158 */       throw new IllegalArgumentException(color.name() + " is not a color");
/*     */     }
/* 160 */     (latest()).color = color;
/* 161 */     this.dirty = true;
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage style(ChatColor... styles) {
/* 172 */     for (ChatColor style : styles) {
/* 173 */       if (!style.isFormat()) {
/* 174 */         throw new IllegalArgumentException(style.name() + " is not a style");
/*     */       }
/*     */     } 
/* 177 */     (latest()).styles.addAll(Arrays.asList(styles));
/* 178 */     this.dirty = true;
/* 179 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage file(String path) {
/* 188 */     onClick("open_file", path);
/* 189 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage link(String url) {
/* 198 */     onClick("open_url", url);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage suggest(String command) {
/* 209 */     onClick("suggest_command", command);
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage command(String command) {
/* 220 */     onClick("run_command", command);
/* 221 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage achievementTooltip(String name) {
/* 231 */     onHover("show_achievement", new JsonString("achievement." + name));
/* 232 */     return this;
/*     */   }
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
/*     */   public FancyMessage statisticTooltip(Statistic which) {
/* 265 */     Statistic.Type type = which.getType();
/* 266 */     if (type != Statistic.Type.UNTYPED) {
/* 267 */       throw new IllegalArgumentException("That statistic requires an additional " + type + " parameter!");
/*     */     }
/*     */     try {
/* 270 */       Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getNMSStatistic", new Class[] { Statistic.class }).invoke(null, new Object[] { which });
/* 271 */       return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
/* 272 */     } catch (IllegalAccessException e) {
/* 273 */       Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
/* 274 */       return this;
/* 275 */     } catch (IllegalArgumentException e) {
/* 276 */       Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
/* 277 */       return this;
/* 278 */     } catch (InvocationTargetException e) {
/* 279 */       Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
/* 280 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage statisticTooltip(Statistic which, Material item) {
/* 293 */     Statistic.Type type = which.getType();
/* 294 */     if (type == Statistic.Type.UNTYPED) {
/* 295 */       throw new IllegalArgumentException("That statistic needs no additional parameter!");
/*     */     }
/* 297 */     if ((type == Statistic.Type.BLOCK && item.isBlock()) || type == Statistic.Type.ENTITY) {
/* 298 */       throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + type + "!");
/*     */     }
/*     */     try {
/* 301 */       Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getMaterialStatistic", new Class[] { Statistic.class, Material.class }).invoke(null, new Object[] { which, item });
/* 302 */       return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
/* 303 */     } catch (IllegalAccessException e) {
/* 304 */       Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
/* 305 */       return this;
/* 306 */     } catch (IllegalArgumentException e) {
/* 307 */       Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
/* 308 */       return this;
/* 309 */     } catch (InvocationTargetException e) {
/* 310 */       Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
/* 311 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage statisticTooltip(Statistic which, EntityType entity) {
/* 324 */     Statistic.Type type = which.getType();
/* 325 */     if (type == Statistic.Type.UNTYPED) {
/* 326 */       throw new IllegalArgumentException("That statistic needs no additional parameter!");
/*     */     }
/* 328 */     if (type != Statistic.Type.ENTITY) {
/* 329 */       throw new IllegalArgumentException("Wrong parameter type for that statistic - needs " + type + "!");
/*     */     }
/*     */     try {
/* 332 */       Object statistic = Reflection.getMethod(Reflection.getOBCClass("CraftStatistic"), "getEntityStatistic", new Class[] { Statistic.class, EntityType.class }).invoke(null, new Object[] { which, entity });
/* 333 */       return achievementTooltip((String)Reflection.getField(Reflection.getNMSClass("Statistic"), "name").get(statistic));
/* 334 */     } catch (IllegalAccessException e) {
/* 335 */       Bukkit.getLogger().log(Level.WARNING, "Could not access method.", e);
/* 336 */       return this;
/* 337 */     } catch (IllegalArgumentException e) {
/* 338 */       Bukkit.getLogger().log(Level.WARNING, "Argument could not be passed.", e);
/* 339 */       return this;
/* 340 */     } catch (InvocationTargetException e) {
/* 341 */       Bukkit.getLogger().log(Level.WARNING, "A error has occured durring invoking of method.", e);
/* 342 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage itemTooltip(String itemJSON) {
/* 353 */     onHover("show_item", new JsonString(itemJSON));
/* 354 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage itemTooltip(ItemStack itemStack) {
/*     */     try {
/* 365 */       Object nmsItem = Reflection.getMethod(Reflection.getOBCClass("inventory.CraftItemStack"), "asNMSCopy", new Class[] { ItemStack.class }).invoke(null, new Object[] { itemStack });
/* 366 */       return itemTooltip(Reflection.getMethod(Reflection.getNMSClass("ItemStack"), "save", new Class[] { Reflection.getNMSClass("NBTTagCompound") }).invoke(nmsItem, new Object[] { Reflection.getNMSClass("NBTTagCompound").newInstance() }).toString());
/* 367 */     } catch (Exception e) {
/* 368 */       e.printStackTrace();
/* 369 */       return this;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage tooltip(String text) {
/* 380 */     onHover("show_text", new JsonString(text));
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage tooltip(Iterable<String> lines) {
/* 391 */     tooltip(ArrayWrapper.<String>toArray(lines, String.class));
/* 392 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage tooltip(String... lines) {
/* 402 */     StringBuilder builder = new StringBuilder();
/* 403 */     for (int i = 0; i < lines.length; i++) {
/* 404 */       builder.append(lines[i]);
/* 405 */       if (i != lines.length - 1) {
/* 406 */         builder.append('\n');
/*     */       }
/*     */     } 
/* 409 */     tooltip(builder.toString());
/* 410 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage formattedTooltip(FancyMessage text) {
/* 420 */     for (MessagePart component : text.messageParts) {
/* 421 */       if (component.clickActionData != null && component.clickActionName != null)
/* 422 */         throw new IllegalArgumentException("The tooltip text cannot have click data."); 
/* 423 */       if (component.hoverActionData != null && component.hoverActionName != null) {
/* 424 */         throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
/*     */       }
/*     */     } 
/* 427 */     onHover("show_text", text);
/* 428 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage formattedTooltip(FancyMessage... lines) {
/* 438 */     if (lines.length < 1) {
/* 439 */       onHover(null, null);
/* 440 */       return this;
/*     */     } 
/*     */     
/* 443 */     FancyMessage result = new FancyMessage();
/* 444 */     result.messageParts.clear();
/*     */     
/* 446 */     for (int i = 0; i < lines.length; i++) {
/*     */       try {
/* 448 */         for (MessagePart component : lines[i]) {
/* 449 */           if (component.clickActionData != null && component.clickActionName != null)
/* 450 */             throw new IllegalArgumentException("The tooltip text cannot have click data."); 
/* 451 */           if (component.hoverActionData != null && component.hoverActionName != null) {
/* 452 */             throw new IllegalArgumentException("The tooltip text cannot have a tooltip.");
/*     */           }
/* 454 */           if (component.hasText()) {
/* 455 */             result.messageParts.add(component.clone());
/*     */           }
/*     */         } 
/* 458 */         if (i != lines.length - 1) {
/* 459 */           result.messageParts.add(new MessagePart(TextualComponent.rawText("\n")));
/*     */         }
/* 461 */       } catch (CloneNotSupportedException e) {
/* 462 */         Bukkit.getLogger().log(Level.WARNING, "Failed to clone object", e);
/* 463 */         return this;
/*     */       } 
/*     */     } 
/* 466 */     return formattedTooltip(result.messageParts.isEmpty() ? null : result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage formattedTooltip(Iterable<FancyMessage> lines) {
/* 476 */     return formattedTooltip(ArrayWrapper.<FancyMessage>toArray(lines, FancyMessage.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage then(String text) {
/* 486 */     return then(TextualComponent.rawText(text));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage then(TextualComponent text) {
/* 496 */     if (!latest().hasText()) {
/* 497 */       throw new IllegalStateException("previous message part has no text");
/*     */     }
/* 499 */     this.messageParts.add(new MessagePart(text));
/* 500 */     this.dirty = true;
/* 501 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FancyMessage then() {
/* 510 */     if (!latest().hasText()) {
/* 511 */       throw new IllegalStateException("previous message part has no text");
/*     */     }
/* 513 */     this.messageParts.add(new MessagePart());
/* 514 */     this.dirty = true;
/* 515 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeJson(JsonWriter writer) throws IOException {
/* 520 */     if (this.messageParts.size() == 1) {
/* 521 */       latest().writeJson(writer);
/*     */     } else {
/* 523 */       writer.beginObject().name("text").value("").name("extra").beginArray();
/* 524 */       for (MessagePart part : this) {
/* 525 */         part.writeJson(writer);
/*     */       }
/* 527 */       writer.endArray().endObject();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toJSONString() {
/* 537 */     if (!this.dirty && this.jsonString != null) {
/* 538 */       return this.jsonString;
/*     */     }
/* 540 */     StringWriter string = new StringWriter();
/* 541 */     JsonWriter json = new JsonWriter(string);
/*     */     try {
/* 543 */       writeJson(json);
/* 544 */       json.close();
/* 545 */     } catch (IOException e) {
/* 546 */       throw new RuntimeException("invalid message");
/*     */     } 
/* 548 */     this.jsonString = string.toString();
/* 549 */     this.dirty = false;
/* 550 */     return this.jsonString;
/*     */   }
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
/*     */   public String toOldMessageFormat() {
/* 571 */     StringBuilder result = new StringBuilder();
/* 572 */     for (MessagePart part : this) {
/* 573 */       result.append((part.color == null) ? "" : part.color);
/* 574 */       for (ChatColor formatSpecifier : part.styles) {
/* 575 */         result.append(formatSpecifier);
/*     */       }
/* 577 */       result.append(part.text);
/*     */     } 
/* 579 */     return result.toString();
/*     */   }
/*     */   
/*     */   private MessagePart latest() {
/* 583 */     return this.messageParts.get(this.messageParts.size() - 1);
/*     */   }
/*     */   
/*     */   private void onClick(String name, String data) {
/* 587 */     MessagePart latest = latest();
/* 588 */     latest.clickActionName = name;
/* 589 */     latest.clickActionData = data;
/* 590 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   private void onHover(String name, JsonRepresentedObject data) {
/* 594 */     MessagePart latest = latest();
/* 595 */     latest.hoverActionName = name;
/* 596 */     latest.hoverActionData = data;
/* 597 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> serialize() {
/* 602 */     HashMap<String, Object> map = new HashMap<>();
/* 603 */     map.put("messageParts", this.messageParts);
/*     */     
/* 605 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FancyMessage deserialize(Map<String, Object> serialized) {
/* 616 */     FancyMessage msg = new FancyMessage();
/* 617 */     msg.messageParts = (List<MessagePart>)serialized.get("messageParts");
/* 618 */     msg.jsonString = serialized.containsKey("JSON") ? serialized.get("JSON").toString() : null;
/* 619 */     msg.dirty = !serialized.containsKey("JSON");
/* 620 */     return msg;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<MessagePart> iterator() {
/* 628 */     return this.messageParts.iterator();
/*     */   }
/*     */   
/* 631 */   private static JsonParser _stringParser = new JsonParser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FancyMessage deserialize(String json) {
/* 640 */     JsonObject serialized = _stringParser.parse(json).getAsJsonObject();
/* 641 */     JsonArray extra = serialized.getAsJsonArray("extra");
/* 642 */     FancyMessage returnVal = new FancyMessage();
/* 643 */     returnVal.messageParts.clear();
/* 644 */     for (JsonElement mPrt : extra) {
/* 645 */       MessagePart component = new MessagePart();
/* 646 */       JsonObject messagePart = mPrt.getAsJsonObject();
/* 647 */       for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)messagePart.entrySet()) {
/*     */         
/* 649 */         if (TextualComponent.isTextKey(entry.getKey())) {
/*     */           
/* 651 */           Map<String, Object> serializedMapForm = new HashMap<>();
/* 652 */           serializedMapForm.put("key", entry.getKey());
/* 653 */           if (((JsonElement)entry.getValue()).isJsonPrimitive()) {
/*     */             
/* 655 */             serializedMapForm.put("value", ((JsonElement)entry.getValue()).getAsString());
/*     */           } else {
/*     */             
/* 658 */             for (Map.Entry<String, JsonElement> compositeNestedElement : (Iterable<Map.Entry<String, JsonElement>>)((JsonElement)entry.getValue()).getAsJsonObject().entrySet()) {
/* 659 */               serializedMapForm.put("value." + (String)compositeNestedElement.getKey(), ((JsonElement)compositeNestedElement.getValue()).getAsString());
/*     */             }
/*     */           } 
/* 662 */           component.text = TextualComponent.deserialize(serializedMapForm); continue;
/* 663 */         }  if (MessagePart.stylesToNames.inverse().containsKey(entry.getKey())) {
/* 664 */           if (((JsonElement)entry.getValue()).getAsBoolean())
/* 665 */             component.styles.add((ChatColor)MessagePart.stylesToNames.inverse().get(entry.getKey()));  continue;
/*     */         } 
/* 667 */         if (((String)entry.getKey()).equals("color")) {
/* 668 */           component.color = ChatColor.valueOf(((JsonElement)entry.getValue()).getAsString().toUpperCase()); continue;
/* 669 */         }  if (((String)entry.getKey()).equals("clickEvent")) {
/* 670 */           JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
/* 671 */           component.clickActionName = object.get("action").getAsString();
/* 672 */           component.clickActionData = object.get("value").getAsString(); continue;
/* 673 */         }  if (((String)entry.getKey()).equals("hoverEvent")) {
/* 674 */           JsonObject object = ((JsonElement)entry.getValue()).getAsJsonObject();
/* 675 */           component.hoverActionName = object.get("action").getAsString();
/* 676 */           if (object.get("value").isJsonPrimitive()) {
/*     */             
/* 678 */             component.hoverActionData = new JsonString(object.get("value").getAsString());
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 683 */           component.hoverActionData = deserialize(object.get("value").toString());
/*     */         } 
/*     */       } 
/*     */       
/* 687 */       returnVal.messageParts.add(component);
/*     */     } 
/* 689 */     return returnVal;
/*     */   }
/*     */   
/*     */   public String getChatColor() {
/* 693 */     if (this.chatColor == null) {
/* 694 */       return "";
/*     */     }
/* 696 */     return this.chatColor;
/*     */   }
/*     */   
/*     */   public void setChatColor(String chatColor) {
/* 700 */     this.chatColor = chatColor;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\FancyMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */