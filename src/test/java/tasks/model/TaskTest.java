package tasks.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private Task task;
    private Date start, end;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        try {
            task=new Task("new task",Task.getDateFormat().parse("2023-02-12 10:10"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf = Task.getDateFormat();
        try {
            start = sdf.parse("2025-02-27 12:00");
            end = sdf.parse("2025-02-27 10:00");
        } catch (ParseException e){
            fail(e.getMessage());
        }
        task = new Task("seminar", start, end, 1);
    }

    @Test
    public void createTask(){
        Task task1 = new Task("lab", start, end, 1);

        //assertNotEquals(task1, null);
        assert task1 != null;
    }
    @Test
    void testTaskCreation() throws ParseException {
//       assert task.getTitle() == "new task";
        System.out.println(task.getFormattedDateStart());
        System.out.println(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
//       assert task.getFormattedDateStart().equals(task.getDateFormat().format(Task.getDateFormat().parse("2023-02-12 10:10")));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getDateFormat() {
    }

    @Test
    void getTitle() {
        assertEquals("seminar", task.getTitle(), "Task title name should be \'seminar\'");
    }

    @Test
    void setTitle() {
    }

    @Test
    void isActive() {
    }
}