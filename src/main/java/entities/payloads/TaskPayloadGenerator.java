package entities.payloads;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Task Management API endpoints.
 */
public class TaskPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid task creation payload with dynamic data.
     */
    public static Map<String, Object> generateCreateTaskPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Task: " + faker.lorem().words(4).toString().replace("[", "").replace("]", "").replace(",", ""));
        payload.put("dueDate", Instant.now().plus(faker.number().numberBetween(1, 30), ChronoUnit.DAYS).toString());
        payload.put("status", faker.options().option("OPEN", "IN_PROGRESS"));
        payload.put("priority", faker.options().option("LOW", "MEDIUM", "HIGH"));
        payload.put("description", faker.lorem().paragraph());
        payload.put("archive", false);
        return payload;
    }

    /**
     * Generates an update task payload using existing task data.
     */
    public static Map<String, Object> generateUpdateTaskPayload(Map<String, Object> existingTask) {
        existingTask.put("description", faker.lorem().paragraph());
        existingTask.put("priority", faker.options().option("LOW", "MEDIUM", "HIGH"));
        return existingTask;
    }

    /**
     * Generates an invalid task payload for negative testing.
     */
    public static Map<String, Object> generateInvalidTaskPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", ""); // blank - violates @NotBlank
        payload.put("dueDate", null); // null - violates @NotNull
        payload.put("status", null); // null - violates @NotNull
        payload.put("priority", null); // null - violates @NotNull
        return payload;
    }
}
