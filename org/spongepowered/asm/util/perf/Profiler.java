/*     */ package org.spongepowered.asm.util.perf;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.TreeMap;
/*     */ import org.spongepowered.asm.util.PrettyPrinter;
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
/*     */ public final class Profiler
/*     */ {
/*     */   public static final int ROOT = 1;
/*     */   public static final int FINE = 2;
/*     */   private final Map<String, Section> sections;
/*     */   private final List<String> phases;
/*     */   private final Deque<Section> stack;
/*     */   private boolean active;
/*     */   
/*     */   public class Section
/*     */   {
/*     */     static final String SEPARATOR_ROOT = " -> ";
/*     */     static final String SEPARATOR_CHILD = ".";
/*     */     private final String name;
/*     */     private boolean root;
/*     */     private boolean fine;
/*     */     protected boolean invalidated;
/*     */     private String info;
/*     */     
/*     */     Section(String name) {
/*  89 */       this.name = name;
/*  90 */       this.info = name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  97 */     Section getDelegate() { return this; }
/*     */ 
/*     */     
/*     */     Section invalidate() {
/* 101 */       this.invalidated = true;
/* 102 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setRoot(boolean root) {
/* 111 */       this.root = root;
/* 112 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     public boolean isRoot() { return this.root; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Section setFine(boolean fine) {
/* 128 */       this.fine = fine;
/* 129 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     public boolean isFine() { return this.fine; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     public String getName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 151 */     public String getBaseName() { return this.name; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     public void setInfo(String info) { this.info = info; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 167 */     public String getInfo() { return this.info; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 176 */     Section start() { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 185 */     protected Section stop() { return this; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section end() {
/* 194 */       if (!this.invalidated) {
/* 195 */         Profiler.this.end(this);
/*     */       }
/* 197 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Section next(String name) {
/* 207 */       end();
/* 208 */       return Profiler.this.begin(name);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void mark() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     public long getTime() { return 0L; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 231 */     public long getTotalTime() { return 0L; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 238 */     public double getSeconds() { return 0.0D; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     public double getTotalSeconds() { return 0.0D; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     public long[] getTimes() { return new long[1]; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 260 */     public int getCount() { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 267 */     public int getTotalCount() { return 0; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     public double getAverageTime() { return 0.0D; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 283 */     public double getTotalAverageTime() { return 0.0D; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 291 */     public final String toString() { return this.name; }
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
/*     */   class LiveSection
/*     */     extends Section
/*     */   {
/* 306 */     private int cursor = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 311 */     private long[] times = new long[0];
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     private long start = 0L;
/*     */ 
/*     */     
/*     */     private long time;
/*     */     
/*     */     private long markedTime;
/*     */     
/*     */     private int count;
/*     */     
/*     */     private int markedCount;
/*     */ 
/*     */     
/*     */     LiveSection(String name, int cursor) {
/* 329 */       super(Profiler.this, name);
/* 330 */       this.cursor = cursor;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 335 */       this.start = System.currentTimeMillis();
/* 336 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Profiler.Section stop() {
/* 341 */       if (this.start > 0L) {
/* 342 */         this.time += System.currentTimeMillis() - this.start;
/*     */       }
/* 344 */       this.start = 0L;
/* 345 */       this.count++;
/* 346 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 351 */       stop();
/* 352 */       if (!this.invalidated) {
/* 353 */         Profiler.this.end(this);
/*     */       }
/* 355 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     void mark() {
/* 360 */       if (this.cursor >= this.times.length) {
/* 361 */         this.times = Arrays.copyOf(this.times, this.cursor + 4);
/*     */       }
/* 363 */       this.times[this.cursor] = this.time;
/* 364 */       this.markedTime += this.time;
/* 365 */       this.markedCount += this.count;
/* 366 */       this.time = 0L;
/* 367 */       this.count = 0;
/* 368 */       this.cursor++;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 373 */     public long getTime() { return this.time; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 378 */     public long getTotalTime() { return this.time + this.markedTime; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     public double getSeconds() { return this.time * 0.001D; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 388 */     public double getTotalSeconds() { return (this.time + this.markedTime) * 0.001D; }
/*     */ 
/*     */ 
/*     */     
/*     */     public long[] getTimes() {
/* 393 */       long[] times = new long[this.cursor + 1];
/* 394 */       System.arraycopy(this.times, 0, times, 0, Math.min(this.times.length, this.cursor));
/* 395 */       times[this.cursor] = this.time;
/* 396 */       return times;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 401 */     public int getCount() { return this.count; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     public int getTotalCount() { return this.count + this.markedCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 411 */     public double getAverageTime() { return (this.count > 0) ? (this.time / this.count) : 0.0D; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     public double getTotalAverageTime() { return (this.count > 0) ? ((this.time + this.markedTime) / (this.count + this.markedCount)) : 0.0D; }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   class SubSection
/*     */     extends LiveSection
/*     */   {
/*     */     private final String baseName;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Profiler.Section root;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     SubSection(String name, int cursor, String baseName, Profiler.Section root) {
/* 439 */       super(Profiler.this, name, cursor);
/* 440 */       this.baseName = baseName;
/* 441 */       this.root = root;
/*     */     }
/*     */ 
/*     */     
/*     */     Profiler.Section invalidate() {
/* 446 */       this.root.invalidate();
/* 447 */       return super.invalidate();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 452 */     public String getBaseName() { return this.baseName; }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setInfo(String info) {
/* 457 */       this.root.setInfo(info);
/* 458 */       super.setInfo(info);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 463 */     Profiler.Section getDelegate() { return this.root; }
/*     */ 
/*     */ 
/*     */     
/*     */     Profiler.Section start() {
/* 468 */       this.root.start();
/* 469 */       return super.start();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section end() {
/* 474 */       this.root.stop();
/* 475 */       return super.end();
/*     */     }
/*     */ 
/*     */     
/*     */     public Profiler.Section next(String name) {
/* 480 */       stop();
/* 481 */       return this.root.next(name);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Profiler() {
/* 489 */     this.sections = new TreeMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 494 */     this.phases = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 499 */     this.stack = new LinkedList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     this.phases.add("Initial");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActive(boolean active) {
/* 517 */     if ((!this.active && active) || !active) {
/* 518 */       reset();
/*     */     }
/* 520 */     this.active = active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 527 */     for (Section section : this.sections.values()) {
/* 528 */       section.invalidate();
/*     */     }
/*     */     
/* 531 */     this.sections.clear();
/* 532 */     this.phases.clear();
/* 533 */     this.phases.add("Initial");
/* 534 */     this.stack.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section get(String name) {
/* 544 */     Section section = (Section)this.sections.get(name);
/* 545 */     if (section == null) {
/* 546 */       section = this.active ? new LiveSection(name, this.phases.size() - 1) : new Section(name);
/* 547 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 550 */     return section;
/*     */   }
/*     */   
/*     */   private Section getSubSection(String name, String baseName, Section root) {
/* 554 */     Section section = (Section)this.sections.get(name);
/* 555 */     if (section == null) {
/* 556 */       section = new SubSection(name, this.phases.size() - 1, baseName, root);
/* 557 */       this.sections.put(name, section);
/*     */     } 
/*     */     
/* 560 */     return section;
/*     */   }
/*     */ 
/*     */   
/* 564 */   boolean isHead(Section section) { return (this.stack.peek() == section); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 574 */   public Section begin(String... path) { return begin(0, path); }
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
/* 585 */   public Section begin(int flags, String... path) { return begin(flags, Joiner.on('.').join(path)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 595 */   public Section begin(String name) { return begin(0, name); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Section begin(int flags, String name) {
/* 606 */     boolean root = ((flags & true) != 0);
/* 607 */     boolean fine = ((flags & 0x2) != 0);
/*     */     
/* 609 */     String path = name;
/* 610 */     Section head = (Section)this.stack.peek();
/* 611 */     if (head != null) {
/* 612 */       path = head.getName() + (root ? " -> " : ".") + path;
/* 613 */       if (head.isRoot() && !root) {
/* 614 */         int pos = head.getName().lastIndexOf(" -> ");
/* 615 */         name = ((pos > -1) ? head.getName().substring(pos + 4) : head.getName()) + "." + name;
/* 616 */         root = true;
/*     */       } 
/*     */     } 
/*     */     
/* 620 */     Section section = get(root ? name : path);
/* 621 */     if (root && head != null && this.active) {
/* 622 */       section = getSubSection(path, head.getName(), section);
/*     */     }
/*     */     
/* 625 */     section.setFine(fine).setRoot(root);
/* 626 */     this.stack.push(section);
/*     */     
/* 628 */     return section.start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void end(Section section) {
/*     */     try {
/* 639 */       for (Section head = (Section)this.stack.pop(), next = head; next != section; next = (Section)this.stack.pop()) {
/* 640 */         if (next == null && this.active) {
/* 641 */           if (head == null) {
/* 642 */             throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */           }
/* 644 */           throw new IllegalStateException("Attempted to pop " + section + " which was not in the stack, head was " + head);
/*     */         } 
/*     */       } 
/* 647 */     } catch (NoSuchElementException ex) {
/* 648 */       if (this.active) {
/* 649 */         throw new IllegalStateException("Attempted to pop " + section + " but the stack is empty");
/*     */       }
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
/*     */   public void mark(String phase) {
/* 662 */     long currentPhaseTime = 0L;
/* 663 */     for (Section section : this.sections.values()) {
/* 664 */       currentPhaseTime += section.getTime();
/*     */     }
/*     */ 
/*     */     
/* 668 */     if (currentPhaseTime == 0L) {
/* 669 */       int size = this.phases.size();
/* 670 */       this.phases.set(size - 1, phase);
/*     */       
/*     */       return;
/*     */     } 
/* 674 */     this.phases.add(phase);
/* 675 */     for (Section section : this.sections.values()) {
/* 676 */       section.mark();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 684 */   public Collection<Section> getSections() { return Collections.unmodifiableCollection(this.sections.values()); }
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
/*     */   public PrettyPrinter printer(boolean includeFine, boolean group) {
/* 696 */     PrettyPrinter printer = new PrettyPrinter();
/*     */ 
/*     */     
/* 699 */     int colCount = this.phases.size() + 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 704 */     int[] columns = { 0, 1, 2, colCount - 2, colCount - 1 };
/*     */     
/* 706 */     Object[] headers = new Object[colCount * 2]; int col, pos;
/* 707 */     for (col = 0, pos = 0; col < colCount; pos = ++col * 2) {
/* 708 */       headers[pos + 1] = PrettyPrinter.Alignment.RIGHT;
/* 709 */       if (col == columns[0]) {
/* 710 */         headers[pos] = (group ? "" : "  ") + "Section";
/* 711 */         headers[pos + 1] = PrettyPrinter.Alignment.LEFT;
/* 712 */       } else if (col == columns[1]) {
/* 713 */         headers[pos] = "    TOTAL";
/* 714 */       } else if (col == columns[3]) {
/* 715 */         headers[pos] = "    Count";
/* 716 */       } else if (col == columns[4]) {
/* 717 */         headers[pos] = "Avg. ";
/* 718 */       } else if (col - columns[2] < this.phases.size()) {
/* 719 */         headers[pos] = this.phases.get(col - columns[2]);
/*     */       } else {
/* 721 */         headers[pos] = "";
/*     */       } 
/*     */     } 
/*     */     
/* 725 */     printer.table(headers).th().hr().add();
/*     */     
/* 727 */     for (Section section : this.sections.values()) {
/* 728 */       if ((section.isFine() && !includeFine) || (group && section.getDelegate() != section)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */       
/* 733 */       printSectionRow(printer, colCount, columns, section, group);
/*     */ 
/*     */       
/* 736 */       if (group) {
/* 737 */         for (Section subSection : this.sections.values()) {
/* 738 */           Section delegate = subSection.getDelegate();
/* 739 */           if ((subSection.isFine() && !includeFine) || delegate != section || delegate == subSection) {
/*     */             continue;
/*     */           }
/*     */           
/* 743 */           printSectionRow(printer, colCount, columns, subSection, group);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 748 */     return printer.add();
/*     */   }
/*     */   
/*     */   private void printSectionRow(PrettyPrinter printer, int colCount, int[] columns, Section section, boolean group) {
/* 752 */     boolean isDelegate = (section.getDelegate() != section);
/* 753 */     Object[] values = new Object[colCount];
/* 754 */     int col = 1;
/* 755 */     if (group) {
/* 756 */       values[0] = isDelegate ? ("  > " + section.getBaseName()) : section.getName();
/*     */     } else {
/* 758 */       values[0] = (isDelegate ? "+ " : "  ") + section.getName();
/*     */     } 
/*     */     
/* 761 */     long[] times = section.getTimes();
/* 762 */     for (long time : times) {
/* 763 */       if (col == columns[1]) {
/* 764 */         values[col++] = section.getTotalTime() + " ms";
/*     */       }
/* 766 */       if (col >= columns[2] && col < values.length) {
/* 767 */         values[col++] = time + " ms";
/*     */       }
/*     */     } 
/*     */     
/* 771 */     values[columns[3]] = Integer.valueOf(section.getTotalCount());
/* 772 */     values[columns[4]] = (new DecimalFormat("   ###0.000 ms")).format(section.getTotalAverageTime());
/*     */     
/* 774 */     for (int i = 0; i < values.length; i++) {
/* 775 */       if (values[i] == null) {
/* 776 */         values[i] = "-";
/*     */       }
/*     */     } 
/*     */     
/* 780 */     printer.tr(values);
/*     */   }
/*     */ }


/* Location:              D:\工程开发\Cracked\phosphor-1.12.2-0.2.6+build50-universal.jar!\org\spongepowered\as\\util\perf\Profiler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */