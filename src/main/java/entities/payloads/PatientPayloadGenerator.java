package entities.payloads;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Patient Management API endpoints.
 */
public class PatientPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid patient creation payload with dynamic data.
     */
    public static Map<String, Object> generateCreatePatientPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", generateValidName());
        payload.put("lastName", generateValidName());
        payload.put("email", faker.internet().emailAddress());
        payload.put("mobileNumber", "+33" + faker.phoneNumber().subscriberNumber(9));
        payload.put("gender", faker.options().option("MALE", "FEMALE", "OTHER"));
        payload.put("birthDate", Instant.now().minus(faker.number().numberBetween(18 * 365, 80 * 365), ChronoUnit.DAYS).toString());
        payload.put("mrn", "MRN" + faker.number().digits(8));
        payload.put("role", "PATIENT");
        payload.put("address", generateAddress());
        return payload;
    }

    /**
     * Generates an update patient payload using existing patient data map.
     */
    public static Map<String, Object> generateUpdatePatientPayload(Map<String, Object> existingPatient) {
        existingPatient.put("middleName", faker.name().firstName());
        return existingPatient;
    }

    /**
     * Generates an invalid patient payload for negative testing.
     */
    public static Map<String, Object> generateInvalidPatientPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("firstName", ""); // blank - violates @NotBlank
        payload.put("lastName", "A"); // too short - violates @Size(min=2)
        payload.put("email", "invalid-email"); // invalid format
        payload.put("gender", "INVALID_GENDER"); // invalid enum
        payload.put("address", generateInvalidAddress());
        return payload;
    }

    private static String generateValidName() {
        String name = faker.name().firstName().replaceAll("[^a-zA-Z]", "");
        if (name.length() < 2) name = name + "aa";
        if (name.length() > 32) name = name.substring(0, 32);
        return name;
    }

    private static Map<String, String> generateAddress() {
        Map<String, String> address = new HashMap<>();
        address.put("line1", faker.address().streetAddress());
        address.put("line2", faker.address().secondaryAddress());
        address.put("city", "Paris");
        address.put("state", "IleDeFrance");
        address.put("country", "France");
        address.put("zipcode", String.format("%05d", faker.number().numberBetween(10000, 99999)));
        return address;
    }

    private static Map<String, String> generateInvalidAddress() {
        Map<String, String> address = new HashMap<>();
        address.put("line1", " ");
        address.put("city", "");
        address.put("state", "");
        address.put("country", "");
        address.put("zipcode", "123");
        return address;
    }
}
