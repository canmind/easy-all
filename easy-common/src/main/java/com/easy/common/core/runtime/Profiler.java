package com.easy.common.core.runtime;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.o2o.framework.core.runtime.MessageLevel;
import com.o2o.framework.core.runtime.Profiler;
import com.o2o.framework.core.runtime.Profiler.Entry;
import com.o2o.framework.core.runtime.Profiler.Message;

/**
 * @Title: Profiler.java
 * @Copyright: Copyright (c) 2014
 * @Description: 性能诊断工具 使用场景：1. 定义在通用框架中，用于分析整体系统的实时性能 2.需要对特定个业务块做性能分析时</br>
 * @Created on 2014-4-24 下午4:16:03

 */
public final class Profiler {

    private static final ThreadLocal<Entry> entryStack = new ThreadLocal<Entry>();

    private static ThreadLocal<Long> profilerContext = new ThreadLocal<Long>() {
        // 初始化值
        public Long initialValue() {
            return 0L;
        }
    };

    public static void start(String message) {
        if (isStart()) {
            enter(message);
        } else {
            entryStack.set(new Entry(message, null, null));
        }
        profilerContext.set(profilerContext.get() + 1L);
    }

    public static void start(Message message) {
        if (isStart()) {
            enter(message);
        } else {
            entryStack.set(new Entry(message, null, null));
        }
        profilerContext.set(profilerContext.get() + 1L);
    }

    public static void reset() {
        Long c = profilerContext.get();
        if (null != c && c.longValue() > 1) {
            profilerContext.set(profilerContext.get() - 1L);
        } else {
            entryStack.set(null);
            profilerContext.set(0L);
        }
    }

    public static void enter(String message) {
        Entry currentEntry = getCurrentEntry();

        if (currentEntry != null) {
            currentEntry.enterSubEntry(message);
        }
    }

    public static void enter(Message message) {
        Entry currentEntry = getCurrentEntry();

        if (currentEntry != null) {
            currentEntry.enterSubEntry(message);
        }
    }

    public static void release() {
        Entry currentEntry = getCurrentEntry();

        if (currentEntry != null) {
            currentEntry.release();
        }
    }

    public static long getDuration() {
        Entry entry = (Entry) entryStack.get();

        if (entry != null) {
            return entry.getDuration();
        } else {
            return -1;
        }
    }

    public static String dump() {
        return dump("", "");
    }

    /**
     * 判断被父级方法调用过
     * 
     * @return
     */
    public static boolean isSuperStart() {
        return profilerContext.get() > 1L;
    }

    public static String dump(String prefix) {
        return dump(prefix, prefix);
    }

    public static String dump(String prefix1, String prefix2) {
        Entry entry = (Entry) entryStack.get();

        if (entry != null) {
            return entry.toString(prefix1, prefix2);
        } else {
            return "";
        }
    }

    public static Entry getEntry() {
        return (Entry) entryStack.get();
    }

    private static Entry getCurrentEntry() {
        Entry subEntry = (Entry) entryStack.get();
        Entry entry = null;

        if (subEntry != null) {
            do {
                entry = subEntry;
                subEntry = entry.getUnreleasedEntry();
            } while (subEntry != null);
        }

        return entry;
    }

    public static boolean isStart() {
        return null != getEntry() && getEntry().isStart();
    }

    public static final class Entry {
        private final List<Entry> subEntries = new ArrayList<Entry>(4);

        private final Object message;

        private final Entry parentEntry;

        private final Entry firstEntry;

        private final long baseTime;

        private final long startTime;

        private long endTime;

        private boolean isStart = false;

        private Entry(Object message, Entry parentEntry, Entry firstEntry) {
            this.message = message;
            this.startTime = System.currentTimeMillis();
            this.parentEntry = parentEntry;
            this.firstEntry = (Entry) ObjectUtils.defaultIfNull(firstEntry, this);
            this.baseTime = (firstEntry == null) ? 0 : firstEntry.startTime;
            this.isStart = true;
        }

        public String getMessage() {
            String messageString = null;

            if (message instanceof String) {
                messageString = (String) message;
            } else if (message instanceof Message) {
                Message messageObject = (Message) message;
                MessageLevel level = MessageLevel.BRIEF_MESSAGE;

                if (isReleased()) {
                    level = messageObject.getMessageLevel(this);
                }

                if (level == MessageLevel.DETAILED_MESSAGE) {
                    messageString = messageObject.getDetailedMessage();
                } else {
                    messageString = messageObject.getBriefMessage();
                }
            }

            return StringUtils.defaultIfEmpty(messageString, null);
        }

        public long getStartTime() {
            return (baseTime > 0) ? (startTime - baseTime) : 0;
        }

        public long getEndTime() {
            if (endTime < baseTime) {
                return -1;
            } else {
                return endTime - baseTime;
            }
        }

        public long getDuration() {
            if (endTime < startTime) {
                return -1;
            } else {
                return endTime - startTime;
            }
        }

        public long getDurationOfSelf() {
            long duration = getDuration();

            if (duration < 0) {
                return -1;
            } else if (subEntries.isEmpty()) {
                return duration;
            } else {
                for (int i = 0; i < subEntries.size(); i++) {
                    Entry subEntry = (Entry) subEntries.get(i);

                    duration -= subEntry.getDuration();
                }

                if (duration < 0) {
                    return -1;
                } else {
                    return duration;
                }
            }
        }

        public double getPecentage() {
            double parentDuration = 0;
            double duration = getDuration();

            if ((parentEntry != null) && parentEntry.isReleased()) {
                parentDuration = parentEntry.getDuration();
            }

            if ((duration > 0) && (parentDuration > 0)) {
                return duration / parentDuration;
            } else {
                return 0;
            }
        }

        public double getPecentageOfAll() {
            double firstDuration = 0;
            double duration = getDuration();

            if ((firstEntry != null) && firstEntry.isReleased()) {
                firstDuration = firstEntry.getDuration();
            }

            if ((duration > 0) && (firstDuration > 0)) {
                return duration / firstDuration;
            } else {
                return 0;
            }
        }

        public List<Entry> getSubEntries() {
            return Collections.unmodifiableList(subEntries);
        }

        private void release() {
            endTime = System.currentTimeMillis();
        }

        /**
         * 判断当前entry是否结束。
         * 
         * @return 如果entry已经结束，则返回<code>true</code>
         */
        private boolean isReleased() {
            return endTime > 0;
        }

        private void enterSubEntry(Object message) {
            Entry subEntry = new Entry(message, this, firstEntry);

            subEntries.add(subEntry);
        }

        private Entry getUnreleasedEntry() {
            Entry subEntry = null;

            if (!subEntries.isEmpty()) {
                subEntry = (Entry) subEntries.get(subEntries.size() - 1);

                if (subEntry.isReleased()) {
                    subEntry = null;
                }
            }

            return subEntry;
        }

        public String toString() {
            return toString("", "");
        }

        private String toString(String prefix1, String prefix2) {
            StringBuffer buffer = new StringBuffer();

            toString(buffer, prefix1, prefix2);

            return buffer.toString();
        }

        private void toString(StringBuffer buffer, String prefix1, String prefix2) {
            buffer.append(prefix1);

            String message = getMessage();
            long startTime = getStartTime();
            long duration = getDuration();
            long durationOfSelf = getDurationOfSelf();
            double percent = getPecentage();
            double percentOfAll = getPecentageOfAll();

            Object[] params = new Object[] { message, // {0} - entry信息
                                            new Long(startTime), // {1} - 起始时间
                                            new Long(duration), // {2} - 持续总时间
                                            new Long(durationOfSelf), // {3} -
                                                                      // 自身消耗的时间
                                            new Double(percent), // {4} -
                                                                 // 在父entry中所占的时间比例
                                            new Double(percentOfAll) // {5} -
                                                                     // 在总时间中所旧的时间比例
            };

            StringBuffer pattern = new StringBuffer("{1,number} ");

            if (isReleased()) {
                pattern.append("[{2,number}ms");

                if ((durationOfSelf > 0) && (durationOfSelf != duration)) {
                    pattern.append(" ({3,number}ms)");
                }

                if (percent > 0) {
                    pattern.append(", {4,number,##%}");
                }

                if (percentOfAll > 0) {
                    pattern.append(", {5,number,##%}");
                }

                pattern.append("]");
            } else {
                pattern.append("[UNRELEASED]");
            }

            if (message != null) {
                pattern.append(" - {0}");
            }

            buffer.append(MessageFormat.format(pattern.toString(), params));

            for (int i = 0; i < subEntries.size(); i++) {
                Entry subEntry = (Entry) subEntries.get(i);

                buffer.append('\n');

                if (i == (subEntries.size() - 1)) {
                    subEntry.toString(buffer, prefix2 + "`---", prefix2 + "    "); // 最后一项
                } else if (i == 0) {
                    subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   "); // 第一项
                } else {
                    subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   "); // 中间项
                }
            }
        }

        public boolean isStart() {
            return isStart;
        }
    }

    public interface Message {
        MessageLevel getMessageLevel(Entry entry);

        String getBriefMessage();

        String getDetailedMessage();
    }

    public static void main(String[] args) {
        ThreadPoolExecutor consumeExecutor = new ThreadPoolExecutor(30, 30 + 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(30 + 10), new ThreadFactory() {
   
            public Thread newThread(Runnable r) {
                Thread myThread = new Thread(r);
                myThread.setName("TT");
                return myThread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());
        while (true) {
            consumeExecutor.execute(new Runnable() {

                public void run() {
                    Profiler.start("Start method: a");
                    try {
                        Thread.sleep(100);

                        Profiler.enter("Start method: a-1");
                        Thread.sleep(100);
                        Profiler.release();

                        Profiler.enter("Start method: a-2");
                        Thread.sleep(100);
                        Profiler.release();

                        Profiler.start("Invoking method: b");
                        try {
                            Thread.sleep(100);

                            Profiler.enter("Start method: b-1");
                            Thread.sleep(100);
                            Profiler.release();

                            Profiler.enter("Start method: b-2");
                            Thread.sleep(100);
                            Profiler.release();

                        } finally {
                            Profiler.release();
                            if (!Profiler.isSuperStart())
                                System.out.println(Profiler.dump());
                            Profiler.reset();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        Profiler.release();
                        System.out.println(Profiler.dump());
                        Profiler.reset();
                    }

                }
            });
        }
    }
}
