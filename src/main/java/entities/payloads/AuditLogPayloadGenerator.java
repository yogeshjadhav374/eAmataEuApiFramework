package entities.payloads;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;

/**
 * Generates dynamic payloads for the Audit Logs API (Super Admin Portal).
 * Targets the {@code POST /api/admin/audit-logs/save} endpoint backed by
 * {@code SaveAuditLogRequestDTO} (actionType*, description*, module, performOnUuid, createdAt).
 */
public class AuditLogPayloadGenerator {

    private static final Faker faker = new Faker();

    /** Valid action types accepted by the backend AuditActionType enum. */
    private static final String[] ACTION_TYPES = {
            "USER_UPDATE", "USER_CREATE", "PROFILE_UPDATE", "LOGIN", "OTP_VERIFY"
    };

    /** Valid audit modules accepted by the backend AuditModule enum. */
    private static final String[] MODULES = {
            "USER", "PROFILE", "AUTHENTICATION", "ADMIN"
    };

    /**
     * Generates a valid save-audit-log payload with all required fields populated.
     * actionType and description are mandatory (@NotBlank on the DTO).
     */
    public static Map<String, Object> generateSaveAuditLogPayload() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("actionType", ACTION_TYPES[faker.number().numberBetween(0, ACTION_TYPES.length)]);
        payload.put("description", "UI-initiated audit entry: " + faker.lorem().sentence());
        payload.put("module", MODULES[faker.number().numberBetween(0, MODULES.length)]);
        return payload;
    }

    /**
     * Generates an invalid save-audit-log payload that omits the mandatory
     * actionType and description fields, expected to fail @NotBlank validation (400).
     */
    public static Map<String, Object> generateInvalidSaveAuditLogPayload() {
        Map<String, Object> payload = new HashMap<>();
        // Only an optional field is supplied; required actionType/description are missing.
        payload.put("module", MODULES[faker.number().numberBetween(0, MODULES.length)]);
        return payload;
    }
}
