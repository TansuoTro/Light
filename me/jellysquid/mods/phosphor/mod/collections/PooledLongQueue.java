/*     */ package me.jellysquid.mods.phosphor.mod.collections;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ 
/*     */ public class PooledLongQueue {
/*     */   private static final int CACHED_QUEUE_SEGMENTS_COUNT = 4096;
/*     */   private static final int QUEUE_SEGMENT_SIZE = 1024;
/*     */   private final Pool pool;
/*     */   private Segment cur;
/*     */   private Segment last;
/*     */   private int size;
/*     */   
/*     */   public PooledLongQueue(Pool pool) {
/*  15 */     this.size = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  22 */     this.pool = pool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   public int size() { return this.size; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   public boolean isEmpty() { return this.empty; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(long val) {
/*  47 */     if (this.cur == null) {
/*  48 */       this.empty = false;
/*  49 */       this.cur = this.last = this.pool.acquire();
/*     */     } 
/*     */     
/*  52 */     if (this.last.index == 1024) {
/*  53 */       Segment ret = this.last.next = this.last.pool.acquire();
/*  54 */       ret.longArray[ret.index++] = val;
/*     */       
/*  56 */       this.last = ret;
/*     */     } else {
/*  58 */       this.last.longArray[this.last.index++] = val;
/*     */     } 
/*     */     
/*  61 */     this.size++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   public LongQueueIterator iterator() { return new LongQueueIterator(this.cur, null); }
/*     */ 
/*     */   
/*     */   private void clear() {
/*  73 */     Segment segment = this.cur;
/*     */     
/*  75 */     while (segment != null) {
/*  76 */       Segment next = segment.next;
/*  77 */       segment.release();
/*  78 */       segment = next;
/*     */     } 
/*     */     
/*  81 */     this.size = 0;
/*  82 */     this.cur = null;
/*  83 */     this.last = null;
/*  84 */     this.empty = true;
/*     */   }
/*     */   
/*     */   public class LongQueueIterator {
/*     */     private PooledLongQueue.Segment cur;
/*     */     private long[] curArray;
/*     */     private int index;
/*     */     private int capacity;
/*     */     
/*     */     private LongQueueIterator(PooledLongQueue.Segment cur) {
/*  94 */       this.cur = cur;
/*     */       
/*  96 */       if (this.cur != null) {
/*  97 */         this.curArray = cur.longArray;
/*  98 */         this.capacity = cur.index;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 103 */     public boolean hasNext() { return (this.cur != null); }
/*     */ 
/*     */     
/*     */     public long next() {
/* 107 */       long ret = this.curArray[this.index++];
/*     */       
/* 109 */       if (this.index == this.capacity) {
/* 110 */         this.index = 0;
/*     */         
/* 112 */         this.cur = this.cur.next;
/*     */         
/* 114 */         if (this.cur != null) {
/* 115 */           this.curArray = this.cur.longArray;
/* 116 */           this.capacity = this.cur.index;
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       return ret;
/*     */     }
/*     */ 
/*     */     
/* 124 */     public void finish() { PooledLongQueue.this.clear(); }
/*     */   }
/*     */   
/*     */   public static class Pool
/*     */   {
/* 129 */     private final Deque<PooledLongQueue.Segment> segmentPool = new ArrayDeque();
/*     */     
/*     */     private PooledLongQueue.Segment acquire() {
/* 132 */       if (this.segmentPool.isEmpty()) {
/* 133 */         return new PooledLongQueue.Segment(this, null);
/*     */       }
/*     */       
/* 136 */       return (PooledLongQueue.Segment)this.segmentPool.pop();
/*     */     }
/*     */     
/*     */     private void release(PooledLongQueue.Segment segment) {
/* 140 */       if (this.segmentPool.size() < 4096)
/* 141 */         this.segmentPool.push(segment); 
/*     */     } }
/*     */   private static class Segment { private final long[] longArray;
/*     */     private int index;
/*     */     
/*     */     private Segment(PooledLongQueue.Pool pool) {
/* 147 */       this.longArray = new long[1024];
/* 148 */       this.index = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       this.pool = pool;
/*     */     }
/*     */     private Segment next; private final PooledLongQueue.Pool pool;
/*     */     private void release() {
/* 157 */       this.index = 0;
/* 158 */       this.next = null;
/*     */       
/* 160 */       this.pool.release(this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\me\jellysquid\mods\phosphor\mod\collections\PooledLongQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */