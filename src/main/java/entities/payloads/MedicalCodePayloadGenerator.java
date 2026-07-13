package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Medical Codes Management API endpoints.
 */
public class MedicalCodePayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid medical code creation payload.
     */
    public static Map<String, Object> generateCreateMedicalCodePayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("code", faker.letterify("???").toUpperCase() + faker.number().numberBetween(100, 9999));
        payload.put("description", faker.lorem().sentence());
        payload.put("category", faker.options().option("ICD10", "CPT", "SNOMED"));
        payload.put("active", true);
        payload.put("archive", false);
        return payload;
    }

    /**
     * Generates an update medical code payload.
     */
    public static Map<String, Object> generateUpdateMedicalCodePayload(Map<String, Object> existing) {
        existing.put("description", faker.lorem().sentence());
        return existing;
    }
}
