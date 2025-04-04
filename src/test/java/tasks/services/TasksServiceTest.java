package tasks.services;

import org.junit.jupiter.api.*;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.Date;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskServiceTest {
    private static TasksService service;

    @BeforeAll
    static void generalSetUp() {
        ArrayTaskList tasks = new ArrayTaskList();
        service = new TasksService(tasks);
    }

    @AfterAll
    static void bigCleaning() {
        service = null;
    }

    @Nested
    @Tag("ECP")
    @DisplayName("ECP Tests")
    class EcpTesting {
        @BeforeEach
        void testSetup() {
            service.clear();
            Task task = new Task("Test data", new Date(), new Date(), 1);
            service.saveTask(task);
        }

        @AfterEach
        void testCleaning() {
            service.clear();
        }


        @Test
        @DisplayName("Save task with a valid title")
        @Tags({ @Tag("ECP"), @Tag("Valid") })
        public void saveTaskWithValidTitle() {
            Task task = new Task("Sample", new Date(), new Date(), 1);

            Assertions.assertDoesNotThrow(() -> service.saveTask(task));
            Assertions.assertEquals(2, service.getTasks().size());
        }

        @Test
        @DisplayName("Save task with a invalid title")
        @Tags({ @Tag("ECP"), @Tag("Invalid") })
        public void saveTaskWithInvalid() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Task task = new Task(
                        "bzhwbkwuzbzgntpfcdkrcnniztzzvjhitfwxrtphqjrgemnxuauyjuanvuieeeadgacfvxjamgypqxtkvaeidiyphmpabbrhhqaehhjmphecaemfmnvnqyryuxtpzzuifpnjmqwepbprcvuyhzfywrctikgyrhtuuawxdzkxjbcbqczcbeyvegkffmrafpxzruxfxjpmxrdftpuxihxrnkthwfjytyrgugxufqhmkezttkdkznzubdiygtbwvdxrghkzeetayzjduayfmqrgihmknknvhvvdmixmhzftjctw",
                        new Date(),
                        new Date(),
                        1
                );
                service.saveTask(task);
            });

            Assertions.assertEquals(1, service.getTasks().size());
        }


        @Test
        @DisplayName("Save task with a valid date")
        @Tags({ @Tag("ECP"), @Tag("Valid") })
        public void saveTaskWithValidDate() {
            Task task = new Task("Title", new Date(1865313000000L), new Date(1865313000000L), 1);

            Assertions.assertDoesNotThrow(() -> {
                service.saveTask(task);
            });

            Assertions.assertEquals(2, service.getTasks().size());
            Assertions.assertEquals(service.getTasks().getTask(1), task);
        }

        @Test
        @DisplayName("Save task with an invalid date")
        @Tags({ @Tag("ECP"), @Tag("Invalid") })
        public void saveTaskWithInvalidDate() {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                Task task = new Task("Title", new Date(2024 - 1900, 04, 21), new Date(-1), 1);
                service.saveTask(task);
            });
            Assertions.assertEquals(1, service.getTasks().size());
        }


        @Nested
        @Tag("BVA")
        @DisplayName("BVA Tests")
        class BvaTesting {
            @BeforeEach
            void testSetup() {
                service.clear();
                Task task = new Task("Test data", new Date(), new Date(), 1);
                service.saveTask(task);
            }

            @AfterEach
            void testCleaning() {
                service.clear();
            }

            @Test
            @DisplayName("Save task with a valid title (lower bound)")
            @Tags({ @Tag("BVA"), @Tag("Valid") })
            public void saveTaskWithValidTitle() {
                Task task = new Task("T", new Date(), new Date(), 1);

                Assertions.assertDoesNotThrow(() -> {
                    service.saveTask(task);
                });

                Assertions.assertEquals(2, service.getTasks().size());
                Assertions.assertEquals(service.getTasks().getTask(1), task);
            }

            @Test
            @DisplayName("Save task with a invalid title (empty) (lower bound)")
            @Tags({ @Tag("BVA"), @Tag("Invalid") })
            public void saveTaskWithInvalidTitleLowerBound() {
                Assertions.assertThrows(Exception.class, () -> {
                    Task task = new Task("", new Date(), new Date(), 1);
                    service.saveTask(task);
                });

                // Verificarea numărului de task-uri poate fi modificată în funcție de comportamentul așteptat.
                Assertions.assertEquals(1, service.getTasks().size());
            }


            @Test
            @DisplayName("Save task with an invalid title (too long) 256 characters (upper bound)")
            @Tags({ @Tag("BVA"), @Tag("Invalid") })
            public void saveTaskWithInvalidTitleUpperBound() {
                Assertions.assertThrows(IllegalArgumentException.class, () -> {
                    Task task = new Task("wLhIUEnLeKNcsrv7oFQqpr2gznG41jQdaWE5MYp1x2Z88PEuaCegF3dSr3ScuuwzzFfT5Fj6Zah8etTUXrRiUau9qfLJZxqwqMoaDv6TT6mG8V2V20BrOlYlV1w2A50sREW5YFRA5N4cY1UMF7NMZN5KTeSCWskqlz5gKbdFbQaMj6P2ZZ3xqHlBH4eDfbrpKD6RJB3i4rASxpJ3RSNNMX0rG4uYJrnuCWlkFiEtjzd3yhVrkIlIwIpw4U9oH6Yt", new Date(), new Date(), 1);
                    service.saveTask(task);
                });

                Assertions.assertEquals(1, service.getTasks().size());
            }


            @Test
            @DisplayName("Save task with a valid title 255 characters (upper bound)")
            @Tags({ @Tag("BVA"), @Tag("Valid") })
            public void saveTaskWithValidTitleUpperBound() {
                Task task = new Task("wLhIUEnLeKNcsrv7oFQqpr2gznG41jQdaWE5MYp1x2Z88PEuaCegF3dSr3ScuuwzzFfT5Fj6Zah8etTUXrRiUau9qfLJZxqwqMoaDv6TT6mG8V2V20BrOlYlV1w2A50sREW5YFRA5N4cY1UMF7NMZN5KTeSCWskqlz5gKbdFbQaMj6P2ZZ3xqHlBH4eDfbrpKD6RJB3i4rASxpJ3RSNNMX0rG4uYJrnuCWlkFiEtjzd3yhVrkIlIwIpw4U9oH6Y", new Date(), new Date(), 1);

                Assertions.assertDoesNotThrow(() -> service.saveTask(task));

                Assertions.assertEquals(2, service.getTasks().size());
            }

            @Test
            @DisplayName("Save task with a valid date (lower bound) 01 ian 2020, 00:00:00")
            @Tags({ @Tag("BVA"), @Tag("Valid") })
            public void saveTaskWithValidDateLowerBound() {
                Task task = new Task("Title", new Date(1577836800000L), new Date(1577836800000L), 1);

                Assertions.assertDoesNotThrow(() -> {
                    service.saveTask(task);
                });

                Assertions.assertEquals(2, service.getTasks().size());
                Assertions.assertEquals(service.getTasks().getTask(1), task);
            }

            @Test
            @DisplayName("Save task with invalid date (lower bound) 31 dec 2019, 23:59:59 ")
            @Tags({ @Tag("BVA"), @Tag("Invalid") })
            public void saveTaskWithInvalidDateLowerBound() {
                Task task = new Task("Title", new Date(1577836799000L), new Date(1577836799000L), 1);

                Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveTask(task));

                Assertions.assertEquals(1, service.getTasks().size());
            }

            @Test
            @DisplayName("Save task with a valid date (upper bound) 31 dec 2050, 23:59:59")
            @Tags({ @Tag("BVA"), @Tag("Valid") })
            public void saveTaskWithValidDateUpperBound() {
                Task task = new Task("Title", new Date(2524607999000L), new Date(2524607999000L), 1);

                Assertions.assertDoesNotThrow(() -> {
                    service.saveTask(task);
                });

                Assertions.assertEquals(2, service.getTasks().size());
                Assertions.assertEquals(service.getTasks().getTask(1), task);
            }

            @Test
            @DisplayName("Save task with invalid date (upper bound) 01 ian 2051, 00:00:00 ")
            @Tags({ @Tag("BVA"), @Tag("Invalid") })
            public void saveTaskWithInvalidDateUpperBound() {
                Task task = new Task("Title", new Date(2524608000000L), new Date(2524608000000L), 1);

                Assertions.assertThrows(IllegalArgumentException.class, () -> service.saveTask(task));

                Assertions.assertEquals(1, service.getTasks().size());
            }
        }
    }
}