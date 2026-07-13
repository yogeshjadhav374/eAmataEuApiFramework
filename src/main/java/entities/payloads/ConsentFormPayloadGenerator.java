package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for Consent Form Management API endpoints.
 */
public class ConsentFormPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a valid consent form creation payload.
     */
    public static Map<String, Object> generateCreateConsentFormPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", "Consent Form - " + faker.lorem().words(3).toString().replace("[", "").replace("]", "").replace(",", ""));
        // Minimal valid single-page blank PDF encoded in base64
        payload.put("document", "data:application/pdf;base64,JVBERi0xLjAKMSAwIG9iago8PCAvVHlwZSAvQ2F0YWxvZyAvUGFnZXMgMiAwIFIgPj4KZW5kb2JqCjIgMCBvYmoKPDwgL1R5cGUgL1BhZ2VzIC9LaWRzIFszIDAgUl0gL0NvdW50IDEgPj4KZW5kb2JqCjMgMCBvYmoKPDwgL1R5cGUgL1BhZ2UgL1BhcmVudCAyIDAgUiAvTWVkaWFCb3ggWzAgMCA2MTIgNzkyXSA+PgplbmRvYmoKeHJlZgowIDQKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDAwMDA5IDAwMDAwIG4gCjAwMDAwMDAwNTggMDAwMDAgbiAKMDAwMDAwMDExNSAwMDAwMCBuIAp0cmFpbGVyCjw8IC9TaXplIDQgL1Jvb3QgMSAwIFIgPj4Kc3RhcnR4cmVmCjE5MAolJUVPRgo=");
        payload.put("active", true);
        payload.put("archive", false);
        payload.put("changeConsent", false);
        payload.put("signed", false);
        return payload;
    }

    /**
     * Generates an update consent form payload using an existing UUID.
     */
    public static Map<String, Object> generateUpdateConsentFormPayload(String uuid) {
        Map<String, Object> payload = generateCreateConsentFormPayload();
        payload.put("uuid", uuid);
        return payload;
    }

    /**
     * Generates a consent form payload with invalid data for negative testing.
     */
    public static Map<String, Object> generateInvalidConsentFormPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("name", ""); // blank - violates @NotBlank
        payload.put("document", ""); // blank - violates @NotBlank
        return payload;
    }
}
