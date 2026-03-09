package entities.payloads;

import com.github.javafaker.Faker;

import java.util.*;

/**
 * Generates dynamic payloads for Provider/Nurse Management API endpoints.
 */
public class ProviderPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a complete provider/nurse creation payload with dynamic data.
     */
    public static Map<String, Object> generateCreateProviderPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", generateValidName());
        payload.put("lastName", generateValidName());
        payload.put("email", faker.internet().emailAddress());
        payload.put("phone", "+33" + faker.phoneNumber().subscriberNumber(10));
        payload.put("gender", faker.options().option("MALE", "FEMALE", "OTHER"));
        payload.put("npi", generateNpi());
        payload.put("role", faker.options().option("PROVIDER", "NURSE"));
        payload.put("address", generateAddress());
        payload.put("introduction", faker.lorem().sentence());
        payload.put("chatbotTone", faker.options().option("PROFESSIONAL", "FRIENDLY", "CASUAL"));
        return payload;
    }

    /**
     * Generates an update provider payload using an existing UUID.
     */
    public static Map<String, Object> generateUpdateProviderPayload(String uuid) {
        Map<String, Object> payload = generateCreateProviderPayload();
        payload.put("uuid", uuid);
        return payload;
    }

    /**
     * Generates a provider payload with invalid data for negative testing.
     */
    public static Map<String, Object> generateInvalidProviderPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", ""); // blank - should fail @NotBlank
        payload.put("lastName", "A"); // too short - should fail @Size(min=2)
        payload.put("email", "invalid-email"); // invalid format
        payload.put("phone", ""); // blank
        payload.put("gender", "INVALID_GENDER"); // invalid enum
        payload.put("npi", "123"); // too short - should be 10 chars
        payload.put("role", "NURSE");
        payload.put("address", generateInvalidAddress());
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
     * Generates an invalid address for negative testing.
     */
    public static Map<String, String> generateInvalidAddress() {
        Map<String, String> address = new HashMap<>();
        address.put("line1", " "); // starts with whitespace - should fail pattern
        address.put("city", "");   // blank
        address.put("state", "");  // blank
        address.put("country", "");// blank
        address.put("zipcode", "1234"); // too short - min 5
        return address;
    }

    /**
     * Generates a change avatar request payload.
     */
    public static Map<String, String> generateChangeAvatarPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("newAvatar", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
        return payload;
    }

    /**
     * Generates query parameters for listing providers.
     */
    public static Map<String, Object> generateListQueryParams(int page, int size) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", String.valueOf(page));
        params.put("size", String.valueOf(size));
        params.put("sortBy", "id");
        params.put("sortDirection", "desc");
        return params;
    }

    /**
     * Generates a valid NPI number (10 alphanumeric chars).
     */
    private static String generateNpi() {
        return String.format("%010d", faker.number().randomNumber(10, true)).substring(0, 10);
    }

    /**
     * Generates a valid name matching the pattern ^[a-zA-Z]+[-'.\\s]*[a-zA-Z]+[.]?$
     */
    private static String generateValidName() {
        String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
        if (name.length() < 2) name = name + "ab";
        if (name.length() > 32) name = name.substring(0, 32);
        return name;
    }
}
