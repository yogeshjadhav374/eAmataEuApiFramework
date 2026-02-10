package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;

import com.github.javafaker.Faker;
import com.thinkitive.eAmata.ApiRequestBuilder;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PrgramDietStep extends ApiRequestBuilder {

    static String programDietId;
    static String name;
    static String acronym;
    static String description;
    static Boolean active;

    @Given("I set up the request structure to add the program diet")
    public void setupRequestStructureToAddDiet(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Faker faker = new Faker();

        String generatedName = faker.medical().diseaseName() + " Diet"; // Example: "Malaria Diet"
        String generatedAcronym = generatedName.substring(0, Math.min(generatedName.length(), 3)).toUpperCase();
        String generatedDescription = faker.lorem().sentence();

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", generatedName);
        payload.put("acronym", generatedAcronym);
        payload.put("description", generatedDescription);

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the program diet is added successfully with {int} status code")
    public void verifyProgramDietAdded(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull(response.jsonPath().get("message")); // Assuming response returns the created object
    }

    @Given("I set up the request structure to see the program diet list")
    public void setupRequestStructure(Map<String, String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        if (data.containsKey("page")) {
            queryParams.put("page", data.get("page"));
        }
        if (data.containsKey("size")) {
            queryParams.put("size", data.get("size"));
        }
        if (data.containsKey("sortBy")) {
            queryParams.put("sortBy", data.get("sortBy"));
        }
        if (data.containsKey("sortDirection")) {
            queryParams.put("sortDirection", data.get("sortDirection"));
        }

        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);
    }

    @Then("I verify that the program diet list is displayed successfully with {int} status code")
    public void verifyProgramDietList(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull(response.jsonPath().get("data"));

        try {
            programDietId = response.jsonPath().getString("data.content[0].uuid");
            name = response.jsonPath().getString("data.content[0].name");
            acronym = response.jsonPath().getString("data.content[0].acronym");
            description = response.jsonPath().getString("data.content[0].description");
            active = response.jsonPath().getBoolean("data.content[0].active");

            System.out.println("Extracted Diet Details:");
            System.out.println("UUID: " + programDietId);
            System.out.println("Name: " + name);
        } catch (Exception e) {
            System.out.println("Failed to extract diet details: " + e.getMessage());
        }
    }

    @Given("I set up the request structure to update the program diet")
    public void setupRequestStructureToUpdateDiet(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Faker faker = new Faker();

        String name = faker.medical().diseaseName() + " Diet Updated";
        String acronym = name.substring(0, Math.min(name.length(), 3)).toUpperCase() + "U";
        String description = faker.lorem().sentence() + " Updated";

        Map<String, Object> payload = new HashMap<>();
        payload.put("name", name);
        payload.put("acronym", acronym);
        payload.put("description", description);
        payload.put("active", true);

        ApiRequestBuilder.PutAPI(superAdminToken, payload, programDietId, endpoint);
    }

    @Then("I verify that the program diet is updated successfully with {int} status code")
    public void verifyProgramDietUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        String expectedMessage = "Program Diet Updated Successfully.";
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

    @Given("I set up the request structure to inactive the program diet")
    public void setupRequestStructureToInactiveDiet(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        ApiRequestBuilder.PutStatusAPI(superAdminToken, programDietId, "False", endpoint);
    }

    @Then("I verify that the program diet status is updated successfully with {int} status code")
    public void verifyProgramDietStatusUpdated(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
    }

    @Given("I set up the request structure to delete the program diet")
    public void setupRequestStructureToDeleteDiet(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        ApiRequestBuilder.DeleteByIdAPI(superAdminToken, programDietId, endpoint);
    }

    @Then("I verify that the program diet is deleted successfully with {int} status code")
    public void verifyProgramDietDeleted(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        String expectedMessage = "Program Diet deleted successfully.";
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(expectedMessage, actualMessage);
    }

}
