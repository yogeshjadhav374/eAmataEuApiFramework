package entities.payloads;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Generates dynamic payloads for the Patient clinical sub-modules (Home Care Portal):
 * Allergies, Diagnoses, Medications and Vitals. Every clinical record is scoped to a
 * patient via the {@code patientId} field, so each generator takes the target patient UUID.
 */
public class PatientClinicalPayloadGenerator {

    private static final Faker faker = new Faker();

    private static String pick(String[] values) {
        return values[faker.number().numberBetween(0, values.length)];
    }

    /** Short unique token to avoid backend duplicate-name validation per patient. */
    private static String uniqueToken() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    // --- Allergy (PatientAllergy) ---

    /** Valid allergy create payload. allergyType/reaction/severity map to backend enums. */
    public static Map<String, Object> generateAllergyPayload(String patientId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("patientId", patientId);
        payload.put("allergyType", pick(new String[]{"DRUG", "FOOD", "ENVIRONMENT", "OTHER"}));
        payload.put("name", faker.medical().diseaseName() + " allergy " + uniqueToken());
        payload.put("reaction", pick(new String[]{"PAIN", "SWELLING", "VOMITING", "RASHES", "COUGH", "REDNESS"}));
        payload.put("severity", pick(new String[]{"MILD", "MODERATE", "HIGH"}));
        payload.put("source", "MANUAL");
        payload.put("onSetDate", Instant.now().minus(30, ChronoUnit.DAYS).toString());
        payload.put("recordedDate", Instant.now().toString());
        payload.put("active", true);
        return payload;
    }

    // --- Diagnosis (PatientDiagnosis) ---

    /** Valid diagnosis create payload. type maps to the backend ProblemType enum. */
    public static Map<String, Object> generateDiagnosisPayload(String patientId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("patientId", patientId);
        payload.put("name", faker.medical().diseaseName() + " " + uniqueToken());
        payload.put("medicalCode", "A" + faker.number().numberBetween(10, 99) + "." + faker.number().numberBetween(0, 9));
        payload.put("type", pick(new String[]{"CHRONIC", "ACUTE", "PRIMARY", "SECONDARY", "PROVISIONAL"}));
        payload.put("onSetDate", Instant.now().minus(60, ChronoUnit.DAYS).toString());
        payload.put("recordedDate", Instant.now().toString());
        payload.put("note", faker.lorem().sentence());
        payload.put("active", true);
        return payload;
    }

    // --- Medication (PatientMedication) ---

    /** Valid medication create payload. medicineName is mandatory (@NotBlank). */
    public static Map<String, Object> generateMedicationPayload(String patientId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("patientId", patientId);
        payload.put("medicineName", faker.medical().medicineName());
        payload.put("startDate", LocalDate.now().toString());
        payload.put("endDate", LocalDate.now().plusDays(30).toString());
        payload.put("quantity", String.valueOf(faker.number().numberBetween(1, 3)));
        payload.put("direction", "Take " + faker.number().numberBetween(1, 2) + " tablet(s) twice daily");
        payload.put("duration", "30 days");
        payload.put("medicineType", pick(new String[]{"TABLET", "SYRUP", "INJECTION"}));
        payload.put("source", "MANUAL");
        payload.put("active", true);
        return payload;
    }

    /** Invalid medication payload omitting the mandatory medicineName, expected to fail (400). */
    public static Map<String, Object> generateInvalidMedicationPayload(String patientId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("patientId", patientId);
        payload.put("quantity", "1");
        // medicineName intentionally omitted to trigger @NotBlank validation.
        return payload;
    }

    // --- Vitals (PatientVitalRequest wrapping a list of PatientVital) ---

    /** Valid bulk-vital create payload for POST /patient-vital/list. */
    public static Map<String, Object> generateVitalRequestPayload(String patientId) {
        Map<String, Object> vital = new HashMap<>();
        vital.put("patientId", patientId);
        vital.put("vitalName", "Heart Rate");
        vital.put("value1", (float) faker.number().numberBetween(60, 100));
        vital.put("unit", "bpm");
        vital.put("recordedDate", Instant.now().toString());
        vital.put("source", "MANUAL");

        List<Map<String, Object>> vitals = new ArrayList<>();
        vitals.add(vital);

        Map<String, Object> payload = new HashMap<>();
        payload.put("patientId", patientId);
        payload.put("patientVital", vitals);
        return payload;
    }
}
