/*     */ package me.clip.deluxechat.fanciful;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Reflection
/*     */ {
/*     */   private static String _versionString;
/*     */   
/*     */   public static synchronized String getVersion() {
/*  49 */     if (_versionString == null) {
/*  50 */       if (Bukkit.getServer() == null)
/*     */       {
/*  52 */         return null;
/*     */       }
/*  54 */       String name = Bukkit.getServer().getClass().getPackage().getName();
/*  55 */       _versionString = name.substring(name.lastIndexOf('.') + 1) + ".";
/*     */     } 
/*     */     
/*  58 */     return _versionString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final Map<String, Class<?>> _loadedNMSClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*  68 */   private static final Map<String, Class<?>> _loadedOBCClasses = new HashMap<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Class<?> getNMSClass(String className) {
/*  77 */     if (_loadedNMSClasses.containsKey(className)) {
/*  78 */       return _loadedNMSClasses.get(className);
/*     */     }
/*     */     
/*  81 */     String fullName = "net.minecraft.server." + getVersion() + className;
/*  82 */     Class<?> clazz = null;
/*     */     try {
/*  84 */       clazz = Class.forName(fullName);
/*  85 */     } catch (Exception e) {
/*  86 */       e.printStackTrace();
/*  87 */       _loadedNMSClasses.put(className, null);
/*  88 */       return null;
/*     */     } 
/*  90 */     _loadedNMSClasses.put(className, clazz);
/*  91 */     return clazz;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized Class<?> getOBCClass(String className) {
/* 101 */     if (_loadedOBCClasses.containsKey(className)) {
/* 102 */       return _loadedOBCClasses.get(className);
/*     */     }
/*     */     
/* 105 */     String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
/* 106 */     Class<?> clazz = null;
/*     */     try {
/* 108 */       clazz = Class.forName(fullName);
/* 109 */     } catch (Exception e) {
/* 110 */       e.printStackTrace();
/* 111 */       _loadedOBCClasses.put(className, null);
/* 112 */       return null;
/*     */     } 
/* 114 */     _loadedOBCClasses.put(className, clazz);
/* 115 */     return clazz;
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
/*     */   public static synchronized Object getHandle(Object obj) {
/*     */     try {
/* 128 */       return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
/* 129 */     } catch (Exception e) {
/* 130 */       e.printStackTrace();
/* 131 */       return null;
/*     */     } 
/*     */   }
/*     */   
/* 135 */   private static final Map<Class<?>, Map<String, Field>> _loadedFields = new HashMap<>();
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
/*     */   public static synchronized Field getField(Class<?> clazz, String name) {
/*     */     Map<String, Field> loaded;
/* 156 */     if (!_loadedFields.containsKey(clazz)) {
/* 157 */       loaded = new HashMap<>();
/* 158 */       _loadedFields.put(clazz, loaded);
/*     */     } else {
/* 160 */       loaded = _loadedFields.get(clazz);
/*     */     } 
/* 162 */     if (loaded.containsKey(name))
/*     */     {
/* 164 */       return loaded.get(name);
/*     */     }
/*     */     try {
/* 167 */       Field field = clazz.getDeclaredField(name);
/* 168 */       field.setAccessible(true);
/* 169 */       loaded.put(name, field);
/* 170 */       return field;
/* 171 */     } catch (Exception e) {
/*     */       
/* 173 */       e.printStackTrace();
/*     */       
/* 175 */       loaded.put(name, null);
/* 176 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 184 */   private static final Map<Class<?>, Map<String, Map<ArrayWrapper<Class<?>>, Method>>> _loadedMethods = new HashMap<>();
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
/*     */   public static synchronized Method getMethod(Class<?> clazz, String name, Class<?>... args) {
/* 208 */     if (!_loadedMethods.containsKey(clazz)) {
/* 209 */       _loadedMethods.put(clazz, new HashMap<>());
/*     */     }
/*     */     
/* 212 */     Map<String, Map<ArrayWrapper<Class<?>>, Method>> loadedMethodNames = _loadedMethods.get(clazz);
/* 213 */     if (!loadedMethodNames.containsKey(name)) {
/* 214 */       loadedMethodNames.put(name, new HashMap<>());
/*     */     }
/*     */     
/* 217 */     Map<ArrayWrapper<Class<?>>, Method> loadedSignatures = loadedMethodNames.get(name);
/* 218 */     ArrayWrapper<Class<?>> wrappedArg = new ArrayWrapper<>(args);
/* 219 */     if (loadedSignatures.containsKey(wrappedArg)) {
/* 220 */       return loadedSignatures.get(wrappedArg);
/*     */     }
/*     */     
/* 223 */     for (Method m : clazz.getMethods()) {
/* 224 */       if (m.getName().equals(name) && Arrays.equals((Object[])args, (Object[])m.getParameterTypes())) {
/* 225 */         m.setAccessible(true);
/* 226 */         loadedSignatures.put(wrappedArg, m);
/* 227 */         return m;
/*     */       } 
/* 229 */     }  loadedSignatures.put(wrappedArg, null);
/* 230 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */