package com.thinkitive.eAmata.stepDefinitions.Home_Care_Portal;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import entities.payloads.AppointmentPayloadGenerator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Step definitions for Scheduling (Appointments) API endpoints (Home Care Portal).
 */
public class SchedulingStep extends ApiRequestBuilder {

    private static String appointmentUuid;

    // --- Create Appointment ---

    @Given("I set up the HCP request structure to create an appointment")
    public void setupHCPCreateAppointment(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> payload = AppointmentPayloadGenerator.generateCreateAppointmentPayload();
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.POST, endpoint);
    }

    @Then("I verify that the appointment is created successfully with {int} status code")
    public void verifyAppointmentCreated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Get Appointment List ---

    @Given("I set up the HCP request structure to get the appointment list")
    public void setupHCPGetAppointmentList(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.getOrDefault("page", "0"));
        queryParams.put("size", data.getOrDefault("size", "10"));
        queryParams.put("sortBy", data.getOrDefault("sortBy", "id"));
        queryParams.put("sortDirection", data.getOrDefault("sortDirection", "desc"));
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setQueryParams(queryParams);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the appointment list is returned successfully with {int} status code")
    public void verifyAppointmentListReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Data should not be null", response.jsonPath().get("data"));
        try {
            appointmentUuid = response.jsonPath().getString("data.content[0].uuid");
            System.out.println("Captured Appointment UUID: " + appointmentUuid);
        } catch (Exception e) {
            System.out.println("No appointments found: " + e.getMessage());
        }
    }

    // --- Get Appointment By ID ---

    @Given("I set up the HCP request structure to get appointment by ID")
    public void setupHCPGetAppointmentById(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Appointment UUID must be available from list step", appointmentUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(appointmentUuid);
        execute(io.restassured.http.Method.GET, endpoint);
    }

    @Then("I verify that the appointment details are returned successfully with {int} status code")
    public void verifyAppointmentDetailsReturned(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("Appointment data should not be null", response.jsonPath().get("data"));
    }

    // --- Update Appointment ---

    @Given("I set up the HCP request structure to update the appointment")
    public void setupHCPUpdateAppointment(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Assert.assertNotNull("Appointment UUID must be available from list step", appointmentUuid);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setpathParam(appointmentUuid);
        execute(io.restassured.http.Method.GET, endpoint);
        Map<String, Object> existingAppointment = response.jsonPath().getMap("data");
        Map<String, Object> updatedAppointment = AppointmentPayloadGenerator.generateUpdateAppointmentPayload(existingAppointment);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(updatedAppointment);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the appointment is updated successfully with {int} status code")
    public void verifyAppointmentUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    // --- Update Appointment Status ---

    @Given("I set up the HCP request structure to update appointment status")
    public void setupHCPUpdateAppointmentStatus(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String status = data.get("status");
        Assert.assertNotNull("Appointment UUID must be available from list step", appointmentUuid);
        Map<String, Object> payload = AppointmentPayloadGenerator.generateUpdateStatusPayload(appointmentUuid, status);
        resetRequest();
        setRequestStructure(hcpAdminToken, propertyHandler.getProperty("TenantId"));
        setRequestBody(payload);
        execute(io.restassured.http.Method.PUT, endpoint);
    }

    @Then("I verify that the appointment status is updated with {int} status code")
    public void verifyAppointmentStatusUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }
}
