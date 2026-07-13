package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Device Management API endpoints.
 */
public class DevicePayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid device creation payload using JavaFaker.
     */
    public static Map<String, Object> generateCreateDevicePayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Device " + faker.letterify("???").toUpperCase() + faker.number().numberBetween(100, 999));
        payload.put("deviceType", faker.options().option("Blood Pressure Monitor", "Glucometer", "Pulse Oximeter", "Thermometer", "Scale"));
        payload.put("description", faker.lorem().sentence());
        payload.put("guideLink", "https://guide.eamata.com/" + faker.letterify("??????????"));
        payload.put("active", true);
        payload.put("archive", false);
        payload.put("category", faker.options().option("MECHANICAL", "ELECTRICAL", "DIGITAL"));
        return payload;
    }

    /**
     * Generates an update device payload using an existing UUID.
     */
    public static Map<String, Object> generateUpdateDevicePayload(String uuid) {
        Map<String, Object> payload = generateCreateDevicePayload();
        payload.put("uuid", uuid);
        return payload;
    }

    /**
     * Generates a device payload with invalid data for negative testing.
     * name is blank which violates @NotBlank and name contains special chars violating @Pattern.
     */
    public static Map<String, Object> generateInvalidDevicePayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", ""); // blank - violates @NotBlank
        payload.put("deviceType", faker.lorem().word());
        payload.put("category", "INVALID_CATEGORY"); // invalid enum
        return payload;
    }
}
