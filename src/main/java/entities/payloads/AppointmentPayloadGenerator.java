package entities.payloads;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Scheduling (Appointment) API endpoints.
 */
public class AppointmentPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid appointment creation payload with dynamic data.
     */
    public static Map<String, Object> generateCreateAppointmentPayload() {
        Instant start = Instant.now().plus(faker.number().numberBetween(1, 7), ChronoUnit.DAYS);
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", "Appointment: " + faker.lorem().words(3).toString().replace("[", "").replace("]", "").replace(",", ""));
        payload.put("startTime", start.toString());
        payload.put("endTime", start.plus(30, ChronoUnit.MINUTES).toString());
        payload.put("appointmentMode", faker.options().option("VIRTUAL", "IN_PERSON"));
        payload.put("description", faker.lorem().sentence());
        return payload;
    }

    /**
     * Generates an update appointment payload using existing data.
     */
    public static Map<String, Object> generateUpdateAppointmentPayload(Map<String, Object> existing) {
        existing.put("description", faker.lorem().sentence());
        return existing;
    }

    /**
     * Generates an appointment status update payload.
     */
    public static Map<String, Object> generateUpdateStatusPayload(String appointmentId, String status) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("appointmentId", appointmentId);
        payload.put("status", status);
        return payload;
    }
}
