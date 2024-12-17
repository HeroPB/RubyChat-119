/*     */ package me.clip.deluxechat.fanciful;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import org.apache.commons.lang.Validate;
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
/*     */ public final class ArrayWrapper<E>
/*     */ {
/*     */   private E[] _array;
/*     */   
/*     */   public ArrayWrapper(E... elements) {
/*  46 */     setArray(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public E[] getArray() {
/*  56 */     return this._array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setArray(E[] array) {
/*  64 */     Validate.notNull(array, "The array must not be null.");
/*  65 */     this._array = array;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object other) {
/*  76 */     if (!(other instanceof ArrayWrapper))
/*     */     {
/*  78 */       return false;
/*     */     }
/*  80 */     return Arrays.equals((Object[])this._array, (Object[])((ArrayWrapper)other)._array);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     return Arrays.hashCode((Object[])this._array);
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
/*     */   public static <T> T[] toArray(Iterable<? extends T> list, Class<T> c) {
/* 103 */     int size = -1;
/* 104 */     if (list instanceof Collection) {
/*     */       
/* 106 */       Collection coll = (Collection)list;
/* 107 */       size = coll.size();
/*     */     } 
/*     */ 
/*     */     
/* 111 */     if (size < 0) {
/* 112 */       size = 0;
/*     */       
/* 114 */       for (T element : list) {
/* 115 */         size++;
/*     */       }
/*     */     } 
/*     */     
/* 119 */     T[] result = (T[])Array.newInstance(c, size);
/* 120 */     int i = 0;
/* 121 */     for (T element : list) {
/* 122 */       result[i++] = element;
/*     */     }
/* 124 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\kikib\Desktop\SCUOLA\DeluxeChat-1.18.jar!\me\clip\deluxechat\fanciful\ArrayWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */