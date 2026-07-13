package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.PatientClinicalPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.Method;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for the Patient clinical sub-modules (Home Care Portal):
 * Allergies, Diagnoses, Medications and Vitals. All endpoints live under
 * {@code /api/master/} and are tenant-scoped, so requests use the HCP admin token
 * together with the configured tenant id.
 */
public class PatientClinicalStep extends ApiRequestBuilder {

    private static String patientUuid;
    private static String recordUuid;
    private static Map<String, Object> firstRecord;

    /** Builds a fresh tenant-scoped HCP request. */
    private void prep() {
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
    }

    // --- Shared: capture an existing patient to attach clinical records to ---

    @Given("I capture an existing patient UUID from the patient list")
    public void captureExistingPatient() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", "0");
        queryParams.put("size", "10");
        queryParams.put("sortBy", "id");
        queryParams.put("sortDirection", "desc");
        prep();
        setQueryParams(queryParams);
        execute(Method.GET, "patient");
        Assert.assertEquals(200, response.getStatusCode());
        patientUuid = response.jsonPath().getString("data.content[0].uuid");
        Assert.assertNotNull("A patient must exist in the tenant to run clinical tests", patientUuid);
        System.out.println("Captured Patient UUID for clinical tests: " + patientUuid);
    }

    // --- Generic clinical list (captures the first record uuid) ---

    private void listClinicalRecords(String endpoint) {
        recordUuid = null;
        firstRecord = null;
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("patientUuid", patientUuid);
        queryParams.put("page", "0");
        queryParams.put("size", "100");
        prep();
        setQueryParams(queryParams);
        execute(Method.GET, endpoint);
        try {
            firstRecord = response.jsonPath().getMap("data.content[0]");
            recordUuid = (String) firstRecord.get("uuid");
            System.out.println("Captured clinical record UUID: " + recordUuid);
        } catch (Exception e) {
            System.out.println("No clinical records found for patient: " + e.getMessage());
        }
    }

    // ==================== ALLERGIES ====================

    @Given("I set up the request structure to add an allergy for the patient")
    public void createAllergy() {
        prep();
        setRequestBody(PatientClinicalPayloadGenerator.generateAllergyPayload(patientUuid));
        execute(Method.POST, "patient-allergy");
    }

    @Given("I set up the request structure to get the allergy list for the patient")
    public void listAllergies() {
        listClinicalRecords("patient-allergy");
    }

    @Given("I set up the request structure to get the allergy by ID")
    public void getAllergyById() {
        Assert.assertNotNull("Allergy UUID must be captured from the list step", recordUuid);
        prep();
        setpathParam(recordUuid);
        execute(Method.GET, "patient-allergy");
    }

    @Given("I set up the request structure to update the allergy")
    public void updateAllergy() {
        Assert.assertNotNull("Allergy record must be captured from the list step", firstRecord);
        firstRecord.put("name", "Updated allergy " + System.currentTimeMillis());
        prep();
        setRequestBody(firstRecord);
        execute(Method.PUT, "patient-allergy");
    }

    @Given("I set up the request structure to archive the allergy")
    public void archiveAllergy() {
        Assert.assertNotNull("Allergy UUID must be captured from the list step", recordUuid);
        prep();
        response = request.put("patient-allergy/" + recordUuid + "/archive-status/true");
    }

    // ==================== DIAGNOSES ====================

    /**
     * Fetches a real medical code from the system. Diagnosis creation validates the
     * supplied medicalCode against the MedicalCode master (findByCode), so an arbitrary
     * code yields NOT_FOUND (400). Returns null if none are available.
     */
    private String fetchExistingMedicalCode() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("type", "ALL");
        queryParams.put("page", "0");
        queryParams.put("size", "1");
        prep();
        setQueryParams(queryParams);
        execute(Method.GET, "medical-codes");
        try {
            return response.jsonPath().getString("data.content[0].code");
        } catch (Exception e) {
            return null;
        }
    }

    @Given("I set up the request structure to add a diagnosis for the patient")
    public void createDiagnosis() {
        String medicalCode = fetchExistingMedicalCode();
        Map<String, Object> payload = PatientClinicalPayloadGenerator.generateDiagnosisPayload(patientUuid);
        if (medicalCode != null) {
            payload.put("medicalCode", medicalCode);
        }
        prep();
        setRequestBody(payload);
        execute(Method.POST, "patient-diagnosis");
    }

    @Given("I set up the request structure to get the diagnosis list for the patient")
    public void listDiagnoses() {
        listClinicalRecords("patient-diagnosis");
    }

    @Given("I set up the request structure to get the diagnosis by ID")
    public void getDiagnosisById() {
        Assert.assertNotNull("Diagnosis UUID must be captured from the list step", recordUuid);
        prep();
        setpathParam(recordUuid);
        execute(Method.GET, "patient-diagnosis");
    }

    @Given("I set up the request structure to update the diagnosis")
    public void updateDiagnosis() {
        // Use the record captured from the list (get-by-id NPEs on null modifiedBy server-side).
        Assert.assertNotNull("Diagnosis record must be captured from the list step", firstRecord);
        firstRecord.put("note", "Updated diagnosis note " + System.currentTimeMillis());
        prep();
        setRequestBody(firstRecord);
        execute(Method.PUT, "patient-diagnosis");
    }

    @Given("I set up the request structure to archive the diagnosis")
    public void archiveDiagnosis() {
        // The backend rejects archiving an ACTIVE diagnosis, so deactivate it first.
        Assert.assertNotNull("Diagnosis record must be captured from the list step", firstRecord);
        firstRecord.put("active", false);
        prep();
        setRequestBody(firstRecord);
        execute(Method.PUT, "patient-diagnosis");
        // Now archive the (deactivated) diagnosis.
        prep();
        response = request.put("patient-diagnosis/" + recordUuid + "/archive-status/true");
    }

    // ==================== MEDICATIONS ====================

    @Given("I set up the request structure to add a medication for the patient")
    public void createMedication() {
        prep();
        setRequestBody(PatientClinicalPayloadGenerator.generateMedicationPayload(patientUuid));
        execute(Method.POST, "patient-medication");
    }

    @Given("I set up the request structure to add a medication with missing required fields")
    public void createMedicationInvalid() {
        prep();
        setRequestBody(PatientClinicalPayloadGenerator.generateInvalidMedicationPayload(patientUuid));
        execute(Method.POST, "patient-medication");
    }

    @Given("I set up the request structure to get the medication list for the patient")
    public void listMedications() {
        listClinicalRecords("patient-medication");
    }

    @Given("I set up the request structure to get the medication by ID")
    public void getMedicationById() {
        Assert.assertNotNull("Medication UUID must be captured from the list step", recordUuid);
        prep();
        setpathParam(recordUuid);
        execute(Method.GET, "patient-medication");
    }

    @Given("I set up the request structure to update the medication")
    public void updateMedication() {
        Assert.assertNotNull("Medication record must be captured from the list step", firstRecord);
        firstRecord.put("note", "Updated medication note " + System.currentTimeMillis());
        prep();
        setRequestBody(firstRecord);
        execute(Method.PUT, "patient-medication");
    }

    @Given("I set up the request structure to archive the medication")
    public void archiveMedication() {
        Assert.assertNotNull("Medication UUID must be captured from the list step", recordUuid);
        prep();
        request.queryParam("status", "true");
        response = request.put("patient-medication/archive-status/" + recordUuid);
    }

    // ==================== VITALS ====================

    @Given("I set up the request structure to add vitals for the patient")
    public void createVitals() {
        prep();
        setRequestBody(PatientClinicalPayloadGenerator.generateVitalRequestPayload(patientUuid));
        execute(Method.POST, "patient-vital/list");
    }

    @Given("I set up the request structure to get the vitals list for the patient")
    public void listVitals() {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("patientUuid", patientUuid);
        queryParams.put("page", "0");
        queryParams.put("size", "100");
        prep();
        setQueryParams(queryParams);
        execute(Method.GET, "patient-vital");
    }

    @Given("I set up the request structure to get the latest vitals for the patient")
    public void getLatestVitals() {
        prep();
        request.queryParam("patientUuid", patientUuid);
        response = request.get("patient-vital/latest");
    }

    // ==================== SHARED ASSERTIONS ====================

    @Then("I verify that the clinical record is created successfully with {int} status code")
    public void verifyClinicalCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("I verify that the clinical record creation fails with {int} status code")
    public void verifyClinicalCreationFailed(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("I verify that the clinical list is returned successfully with {int} status code")
    public void verifyClinicalListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
    }

    @Then("I verify that the clinical record details are returned with {int} status code")
    public void verifyClinicalDetails(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
    }

    @Then("I verify that the clinical record is updated successfully with {int} status code")
    public void verifyClinicalUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Then("I verify that the clinical record archive status is updated with {int} status code")
    public void verifyClinicalArchived(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
