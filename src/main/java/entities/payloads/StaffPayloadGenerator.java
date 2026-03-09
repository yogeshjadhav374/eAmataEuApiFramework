package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Staff Management API endpoints.
 */
public class StaffPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a complete staff user creation payload.
     */
    public static Map<String, Object> generateCreateStaffPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", generateValidName());
        payload.put("lastName", generateValidName());
        payload.put("email", faker.internet().emailAddress());
        payload.put("phone", "+33" + faker.phoneNumber().subscriberNumber(10));
        payload.put("gender", faker.options().option("MALE", "FEMALE", "OTHER"));
        payload.put("roleType", "STAFF");
        payload.put("role", "SUPER_ADMIN");
        payload.put("active", true);
        payload.put("address", generateAddress());
        return payload;
    }

    /**
     * Generates an update staff payload using an existing UUID.
     */
    public static Map<String, Object> generateUpdateStaffPayload(String uuid) {
        Map<String, Object> payload = generateCreateStaffPayload();
        payload.put("uuid", uuid);
        return payload;
    }

    /**
     * Generates a staff payload with invalid data for negative testing.
     */
    public static Map<String, Object> generateInvalidStaffPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", ""); // blank - should fail @NotBlank
        payload.put("lastName", ""); // blank
        payload.put("email", "not-an-email");
        payload.put("phone", ""); // blank
        payload.put("gender", null); // null - should fail @NotNull
        payload.put("role", null); // null - should fail @NotNull
        return payload;
    }

    /**
     * Generates a valid address map.
     */
    public static Map<String, String> generateAddress() {
        Map<String, String> address = new HashMap<>();
        address.put("line1", faker.address().streetAddress());
        address.put("line2", faker.address().secondaryAddress());
        address.put("city", "Paris");
        address.put("state", "IleDeFrance");
        address.put("country", "France");
        address.put("zipcode", String.format("%05d", faker.number().numberBetween(10000, 99999)));
        return address;
    }

    /**
     * Generates query parameters for listing staff users.
     */
    public static Map<String, Object> generateListQueryParams(int page, int size) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("sortBy", "created");
        params.put("sortDirection", "desc");
        params.put("roleType", "STAFF");
        return params;
    }

    /**
     * Generates a valid name matching the required pattern.
     */
    private static String generateValidName() {
        String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
        if (name.length() < 2) name = name + "ab";
        if (name.length() > 32) name = name.substring(0, 32);
        return name;
    }
}
