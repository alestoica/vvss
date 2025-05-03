package tasks.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

class TaskOperationTest {

    @Test                       // LISTA VIDĂ
    public void Test1_taskIsEmpty() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 17, 12, 30),
                new Date(121, 4, 26, 12, 30));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Empty", result.get(0).getTitle());
    }

    @Test                       // 4 TASK‑URI, 2 ÎN INTERVAL
    public void F02_incoming_tasksIsNotEmptyAndHasDatesInInterval_returnExpectedTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        Task t1 = new Task("TASK1", new Date(121, 4, 15, 12, 30)); // înainte
        Task t2 = new Task("TASK2", new Date(121, 4, 20, 12, 30)); // în
        Task t3 = new Task("TASK3", new Date(121, 4, 25, 12, 30)); // în
        Task t4 = new Task("TASK4", new Date(121, 4, 30, 12, 30)); // după

        tasks.addAll(t1, t2, t3, t4);
        tasks.forEach(task -> task.setActive(true));               // activăm

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 17, 12, 30),
                new Date(121, 4, 26, 12, 30));

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("TASK2")));
        Assertions.assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("TASK3")));
    }

    @Test                       // NU EXISTĂ TASK‑URI ÎN INTERVAL
    public void F02_incoming_tasksIsNotEmptyAndDoesntHaveTasksInInterval_returnOne() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        Task t1 = new Task("TASK1", new Date(121, 4, 15, 12, 30));
        Task t2 = new Task("TASK2", new Date(121, 4, 20, 12, 30));
        Task t3 = new Task("TASK3", new Date(121, 4, 25, 12, 30));
        Task t4 = new Task("TASK4", new Date(121, 4, 30, 12, 30));

        tasks.addAll(t1, t2, t3, t4);
        tasks.forEach(task -> task.setActive(true));

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 21, 12, 30),
                new Date(121, 4, 22, 12, 30));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Empty", result.get(0).getTitle());
    }

    @Test                       // START & END NUL(LE)
    public void F02_incoming_IntervalIsNull_returnOne() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        addFourAndActivate(tasks);

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(null, null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Empty", result.get(0).getTitle());
    }

    @Test                       // START NUL
    public void F02_incoming_StartIsNull_returnOne() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        addFourAndActivate(tasks);

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(null,
                new Date(121, 4, 21, 12, 30));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Empty", result.get(0).getTitle());
    }

    @Test                       // END NUL
    public void F02_incoming_EndIsNull_returnOne() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        addFourAndActivate(tasks);

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 21, 12, 30), null);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Empty", result.get(0).getTitle());
    }

    @Test                       // 1 TASK ÎN INTERVAL
    public void F02_incoming_tasksHasOneElementAndHasDatesInInterval_returnExpectedTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        Task t1 = new Task("TASK1", new Date(121, 4, 20, 12, 30));
        t1.setActive(true);
        tasks.add(t1);

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 17, 12, 30),
                new Date(121, 4, 26, 12, 30));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("TASK1", result.get(0).getTitle());
    }

    @Test                       // 2 TASK‑URI ÎN INTERVAL
    public void F02_incoming_tasksHasTwoElementsAndHasDatesInInterval_returnExpectedTasks() {
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        Task t1 = new Task("TASK1", new Date(121, 4, 20, 12, 30));
        Task t2 = new Task("TASK2", new Date(121, 4, 21, 12, 30));
        t1.setActive(true);
        t2.setActive(true);

        tasks.addAll(t1, t2);

        TasksOperations top = new TasksOperations(tasks);

        ArrayList<Task> result = (ArrayList<Task>) top.incoming(
                new Date(121, 4, 17, 12, 30),
                new Date(121, 4, 26, 12, 30));

        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("TASK1")));
        Assertions.assertTrue(result.stream().anyMatch(t -> t.getTitle().equals("TASK2")));
    }

    /* ===== Helper ===== */
    private static void addFourAndActivate(ObservableList<Task> tasks) {
        Task t1 = new Task("TASK1", new Date(121, 4, 15, 12, 30));
        Task t2 = new Task("TASK2", new Date(121, 4, 20, 12, 30));
        Task t3 = new Task("TASK3", new Date(121, 4, 25, 12, 30));
        Task t4 = new Task("TASK4", new Date(121, 4, 30, 12, 30));

        tasks.addAll(t1, t2, t3, t4);
        tasks.forEach(task -> task.setActive(true));
    }
}
