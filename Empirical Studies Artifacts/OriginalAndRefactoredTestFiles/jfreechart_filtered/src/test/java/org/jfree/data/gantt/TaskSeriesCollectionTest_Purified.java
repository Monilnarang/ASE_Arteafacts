package org.jfree.data.gantt;

import java.util.Date;
import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.time.SimpleTimePeriod;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskSeriesCollectionTest_Purified {

    private TaskSeriesCollection<String, String> createCollection1() {
        TaskSeriesCollection<String, String> result = new TaskSeriesCollection<>();
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        s1.add(new Task("Task 1", new Date(1), new Date(2)));
        s1.add(new Task("Task 2", new Date(3), new Date(4)));
        result.add(s1);
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        s2.add(new Task("Task 3", new Date(5), new Date(6)));
        result.add(s2);
        return result;
    }

    private TaskSeriesCollection<String, String> createCollection2() {
        TaskSeriesCollection<String, String> result = new TaskSeriesCollection<>();
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        Task t1 = new Task("Task 1", new Date(10), new Date(20));
        t1.addSubtask(new Task("Task 1A", new Date(10), new Date(15)));
        t1.addSubtask(new Task("Task 1B", new Date(16), new Date(20)));
        t1.setPercentComplete(0.10);
        s1.add(t1);
        Task t2 = new Task("Task 2", new Date(30), new Date(40));
        t2.addSubtask(new Task("Task 2A", new Date(30), new Date(35)));
        t2.addSubtask(new Task("Task 2B", new Date(36), new Date(40)));
        t2.setPercentComplete(0.20);
        s1.add(t2);
        result.add(s1);
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        Task t3 = new Task("Task 3", new Date(50), new Date(60));
        t3.addSubtask(new Task("Task 3A", new Date(50), new Date(55)));
        t3.addSubtask(new Task("Task 3B", new Date(56), new Date(60)));
        t3.setPercentComplete(0.30);
        s2.add(t3);
        result.add(s2);
        return result;
    }

    private TaskSeriesCollection<String, String> createCollection3() {
        Task sub1 = new Task("Sub1", new Date(11), new Date(111));
        Task sub2 = new Task("Sub2", new Date(22), new Date(222));
        Task sub3 = new Task("Sub3", new Date(33), new Date(333));
        Task sub4 = new Task("Sub4", new Date(44), new Date(444));
        Task sub5 = new Task("Sub5", new Date(55), new Date(555));
        Task sub6 = new Task("Sub6", new Date(66), new Date(666));
        sub1.setPercentComplete(0.111);
        sub2.setPercentComplete(0.222);
        sub3.setPercentComplete(0.333);
        sub4.setPercentComplete(0.444);
        sub5.setPercentComplete(0.555);
        sub6.setPercentComplete(0.666);
        TaskSeries<String> seriesA = new TaskSeries<>("Series A");
        Task taskA1 = new Task("Task 1", new SimpleTimePeriod(new Date(100), new Date(200)));
        taskA1.setPercentComplete(0.1);
        taskA1.addSubtask(sub1);
        Task taskA2 = new Task("Task 2", new SimpleTimePeriod(new Date(220), new Date(350)));
        taskA2.setPercentComplete(0.2);
        taskA2.addSubtask(sub2);
        taskA2.addSubtask(sub3);
        seriesA.add(taskA1);
        seriesA.add(taskA2);
        TaskSeries<String> seriesB = new TaskSeries<>("Series B");
        Task taskB2 = new Task("Task 2", new SimpleTimePeriod(new Date(2220), new Date(3350)));
        taskB2.setPercentComplete(0.3);
        taskB2.addSubtask(sub4);
        taskB2.addSubtask(sub5);
        taskB2.addSubtask(sub6);
        seriesB.add(taskB2);
        TaskSeriesCollection<String, String> tsc = new TaskSeriesCollection<>();
        tsc.add(seriesA);
        tsc.add(seriesB);
        return tsc;
    }

    @Test
    public void testGetStartValue_1_testMerged_1() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(1L, c.getStartValue("S1", "Task 1"));
        assertEquals(3L, c.getStartValue("S1", "Task 2"));
        assertEquals(5L, c.getStartValue("S2", "Task 3"));
        assertEquals(1L, c.getStartValue(0, 0));
        assertEquals(3L, c.getStartValue(0, 1));
        assertNull(c.getStartValue(0, 2));
        assertNull(c.getStartValue(1, 0));
        assertNull(c.getStartValue(1, 1));
        assertEquals(5L, c.getStartValue(1, 2));
    }

    @Test
    public void testGetStartValue_10_testMerged_2() {
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(100L, c3.getStartValue(0, 0));
        assertEquals(220L, c3.getStartValue(0, 1));
        assertNull(c3.getStartValue(1, 0));
        assertEquals(2220L, c3.getStartValue(1, 1));
    }

    @Test
    public void testGetStartValue2_1_testMerged_1() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(10L, c.getStartValue("S1", "Task 1", 0));
        assertEquals(16L, c.getStartValue("S1", "Task 1", 1));
        assertEquals(30L, c.getStartValue("S1", "Task 2", 0));
        assertEquals(36L, c.getStartValue("S1", "Task 2", 1));
        assertEquals(50L, c.getStartValue("S2", "Task 3", 0));
        assertEquals(56L, c.getStartValue("S2", "Task 3", 1));
        assertEquals(10L, c.getStartValue(0, 0, 0));
        assertEquals(16L, c.getStartValue(0, 0, 1));
        assertEquals(30L, c.getStartValue(0, 1, 0));
        assertEquals(36L, c.getStartValue(0, 1, 1));
        assertEquals(50L, c.getStartValue(1, 2, 0));
        assertEquals(56L, c.getStartValue(1, 2, 1));
    }

    @Test
    public void testGetStartValue2_13_testMerged_2() {
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(11L, c3.getStartValue(0, 0, 0));
        assertEquals(22L, c3.getStartValue(0, 1, 0));
        assertEquals(33L, c3.getStartValue(0, 1, 1));
        assertNull(c3.getStartValue(1, 0, 0));
        assertEquals(44L, c3.getStartValue(1, 1, 0));
        assertEquals(55L, c3.getStartValue(1, 1, 1));
        assertEquals(66L, c3.getStartValue(1, 1, 2));
    }

    @Test
    public void testGetEndValue_1_testMerged_1() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(2L, c.getEndValue("S1", "Task 1"));
        assertEquals(4L, c.getEndValue("S1", "Task 2"));
        assertEquals(6L, c.getEndValue("S2", "Task 3"));
        assertEquals(2L, c.getEndValue(0, 0));
        assertEquals(4L, c.getEndValue(0, 1));
        assertNull(c.getEndValue(0, 2));
        assertNull(c.getEndValue(1, 0));
        assertNull(c.getEndValue(1, 1));
        assertEquals(6L, c.getEndValue(1, 2));
    }

    @Test
    public void testGetEndValue_10_testMerged_2() {
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(200L, c3.getEndValue(0, 0));
        assertEquals(350L, c3.getEndValue(0, 1));
        assertNull(c3.getEndValue(1, 0));
        assertEquals(3350L, c3.getEndValue(1, 1));
    }

    @Test
    public void testGetEndValue2_1_testMerged_1() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(15L, c.getEndValue("S1", "Task 1", 0));
        assertEquals(20L, c.getEndValue("S1", "Task 1", 1));
        assertEquals(35L, c.getEndValue("S1", "Task 2", 0));
        assertEquals(40L, c.getEndValue("S1", "Task 2", 1));
        assertEquals(55L, c.getEndValue("S2", "Task 3", 0));
        assertEquals(60L, c.getEndValue("S2", "Task 3", 1));
        assertEquals(15L, c.getEndValue(0, 0, 0));
        assertEquals(20L, c.getEndValue(0, 0, 1));
        assertEquals(35L, c.getEndValue(0, 1, 0));
        assertEquals(40L, c.getEndValue(0, 1, 1));
        assertEquals(55L, c.getEndValue(1, 2, 0));
        assertEquals(60L, c.getEndValue(1, 2, 1));
    }

    @Test
    public void testGetEndValue2_13_testMerged_2() {
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(111L, c3.getEndValue(0, 0, 0));
        assertEquals(222L, c3.getEndValue(0, 1, 0));
        assertEquals(333L, c3.getEndValue(0, 1, 1));
        assertNull(c3.getEndValue(1, 0, 0));
        assertEquals(444L, c3.getEndValue(1, 1, 0));
        assertEquals(555L, c3.getEndValue(1, 1, 1));
        assertEquals(666L, c3.getEndValue(1, 1, 2));
    }

    @Test
    public void testGetPercentComplete_1_testMerged_1() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(0.10, c.getPercentComplete("S1", "Task 1"));
        assertEquals(0.20, c.getPercentComplete("S1", "Task 2"));
        assertEquals(0.30, c.getPercentComplete("S2", "Task 3"));
        assertEquals(0.10, c.getPercentComplete(0, 0));
        assertEquals(0.20, c.getPercentComplete(0, 1));
        assertNull(c.getPercentComplete(0, 2));
        assertNull(c.getPercentComplete(1, 0));
        assertNull(c.getPercentComplete(1, 1));
        assertEquals(0.30, c.getPercentComplete(1, 2));
    }

    @Test
    public void testGetPercentComplete_10_testMerged_2() {
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(0.1, c3.getPercentComplete(0, 0));
        assertEquals(0.2, c3.getPercentComplete(0, 1));
        assertNull(c3.getPercentComplete(1, 0));
        assertEquals(0.3, c3.getPercentComplete(1, 1));
        assertEquals(0.111, c3.getPercentComplete(0, 0, 0));
        assertEquals(0.222, c3.getPercentComplete(0, 1, 0));
        assertEquals(0.333, c3.getPercentComplete(0, 1, 1));
        assertEquals(0.444, c3.getPercentComplete(1, 1, 0));
        assertEquals(0.555, c3.getPercentComplete(1, 1, 1));
        assertEquals(0.666, c3.getPercentComplete(1, 1, 2));
    }
}
