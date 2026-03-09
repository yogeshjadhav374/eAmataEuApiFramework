package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Authentication API endpoints.
 */
public class AuthPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a login request payload with provided credentials.
     */
    public static Map<String, String> generateLoginPayload(String username, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", username);
        payload.put("password", password);
        return payload;
    }

    /**
     * Generates a login request payload with random invalid credentials.
     */
    public static Map<String, String> generateInvalidLoginPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", faker.internet().emailAddress());
        payload.put("password", "InvalidPass@123");
        return payload;
    }

    /**
     * Generates a logout request payload.
     */
    public static Map<String, String> generateLogoutPayload(String refreshToken) {
        Map<String, String> payload = new HashMap<>();
        payload.put("refreshToken", refreshToken);
        return payload;
    }

    /**
     * Generates a set/reset password payload with a valid strong password.
     */
    public static Map<String, String> generateSetPasswordPayload(String email, String otp) {
        Map<String, String> payload = new HashMap<>();
        payload.put("email", email);
        payload.put("otp", otp);
        payload.put("newPassword", generateStrongPassword());
        return payload;
    }

    /**
     * Generates a change password payload.
     */
    public static Map<String, String> generateChangePasswordPayload(String oldPassword) {
        Map<String, String> payload = new HashMap<>();
        payload.put("oldPassword", oldPassword);
        payload.put("newPassword", generateStrongPassword());
        return payload;
    }

    /**
     * Generates a password that meets validation requirements:
     * Min 8 chars, uppercase, lowercase, digit, special char, no spaces.
     */
    public static String generateStrongPassword() {
        return "Test" + faker.number().numberBetween(100, 999) + "@Pwd";
    }

    /**
     * Generates a weak password that should fail validation.
     */
    public static String generateWeakPassword() {
        return "weak";
    }
}
