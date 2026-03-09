package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Profile Management API endpoints.
 */
public class ProfilePayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a change avatar request payload with a small base64 image.
     */
    public static Map<String, String> generateChangeAvatarPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("newAvatar", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==");
        return payload;
    }
}
