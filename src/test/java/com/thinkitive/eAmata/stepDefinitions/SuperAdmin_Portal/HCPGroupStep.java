package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.github.javafaker.Faker;
import com.thinkitive.eAmata.ApiRequestBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class HCPGroupStep extends ApiRequestBuilder{
    String responseUUID;
    Faker faker = new Faker();
    Map <String, String> address = new HashMap<>();

    @Given("I set up the request structure to add the HCP Group")
    public void iSetUpTheRequestStructureToAddTheHCPGroup(Map<String, String> data) {
        String endpoint = data.get("endpoint").toString();
        Faker faker = new Faker();

        //set the address of the HCP group
        address.put("line1", faker.address().streetAddress());
        address.put("city", "paris");
        address.put("state", "Île-de-France");
        address.put("country", "France");
        address.put("zipcode", "65478");


        // Generation of the subdomain by the Provider Group Name
        String Subdomain = "";
        String HCPName = faker.medical().hospitalName();
        System.out.println(HCPName);
        String[] splitWords = HCPName.split(" ");

        for (String word : splitWords) {
            if (!word.isEmpty()) {
                System.out.println(word);
                String ch = String.valueOf(word.charAt(0)).toLowerCase();
                Subdomain += ch;
            }
        }

        //set the payload to create HCP group
        Map<String, Object> hcpData = new HashMap<>();
        hcpData.put("name", HCPName);
        hcpData.put("subdomain", Subdomain);
        hcpData.put("email", Subdomain+"@eamata.com");
        hcpData.put("phone", "+33" + faker.phoneNumber().subscriberNumber(10));
        hcpData.put("finess", faker.number().digits(9));
        hcpData.put("address", address);



        ApiRequestBuilder.PostAPI(ApiRequestBuilder.superAdminToken, hcpData, endpoint);

    }


    @Then("I verify that the HCP Group is added successfully with {int} status code")
    public void iVerifyThatTheHCPGroupIsAddedSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        String expectedMessage = "Home care provider is created successfully.";
        String actualMessage = response.jsonPath().getString("message");

        Assert.assertEquals(expectedStatusCode, actualStatusCode);
        Assert.assertEquals(expectedMessage,actualMessage);

    }


    @Given("I set up the request structure to see the HCP Group list")
    public void iSetUpTheRequestStructureToSeeTheHCPGroupList(Map<String,String> data) {
        String endpoint = data.get("endpoint");

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", data.get("page"));
        queryParams.put("size", data.get("size"));
        queryParams.put("sortBy", data.get("sortBy"));
        queryParams.put("sortDirection", data.get("sortDirection"));

        ApiRequestBuilder.GetAPI(ApiRequestBuilder.superAdminToken, queryParams, endpoint);

    }

    @Then("I verify that the HCP Group list is displayed successfully with {int} status code")
    public void iVerifyThatTheHCPGroupListIsDisplayedSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);
        Assert.assertNotNull(response.jsonPath().get("data"));
    }


    @Given("I set up the request structure to edit the HCP Group")
    public void iSetUpTheRequestStructureToEditTheHCPGroup(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        String HCPGroupJsonUrl = "C:/Users/LNV-24/Desktop/eAmataEuApiFramework/src/test/resources/HCPGroup.json";

        ApiRequestBuilder.PutAPI(ApiRequestBuilder.superAdminToken, HCPGroupJsonUrl, endpoint);

    }

    @Then("I verify that the HCP Group is edited successfully with {int} status code")
    public void iVerifyThatTheHCPGroupIsEditedSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        String expectedMessage = "Home care provider updated successfully.";
        String actualMessage = response.jsonPath().get("message");

        Assert.assertEquals(expectedStatusCode, actualStatusCode);
        Assert.assertEquals(expectedMessage,actualMessage);

    }

    @Given("I set up the request structure to view the HCP Group details")
    public void iSetUpTheRequestStructureToViewTheHCPGroupDetails(Map<String,String> data) {
        String endpoint = data.get("endpoint");
        String FeatureFileuuid = data.get("uuid");

        String HCPGroupUUID = (FeatureFileuuid != null && !FeatureFileuuid.isEmpty()) ? FeatureFileuuid : responseUUID;  // for now we have not responseUUID but we can generate from the list API

        if (HCPGroupUUID == null) {
            Assert.fail("UUID for HCP Group is not available for viewing. Ensure a HCP Group was added or HCP a valid UUID.");
        }

        ApiRequestBuilder.GetByIdAPI(ApiRequestBuilder.superAdminToken, HCPGroupUUID, endpoint);

    }

    @Then("I verify that the HCP Group details displayed successfully with {int} status code")
    public void iVerifyThatTheHCPGroupDetailsDisplayedSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode );
        Assert.assertNotNull(response.getBody());
    }
}
