package com.thinkitive.eAmata.stepDefinitions;

import com.github.javafaker.Faker;
import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.Pojo.Address;
import entities.Pojo.staffDetailsPojo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;


public class SuperAdminStaffStepDefinitions extends ApiRequestBuilder {
    private RequestSpecification request;
    private Response response;
    private String endpoint;

    @Given("I set up the request structure to add the Admin staff")
    public void setupRequestStructure(Map<String, Object> data) {
        endpoint = data.get("endpoint").toString();
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        staffDetailsPojo.Address address = new staffDetailsPojo.Address();
        address.setLine1(faker.address().streetAddress());
        address.setLine2("a1 street");
        address.setCity("Akutan");
        address.setState("Arizona");
        address.setCountry("USA");
        address.setZipcode("98709");

        staffDetailsPojo pojo = staffDetailsPojo.builder()
                .firstName(firstName)
                .lastName(faker.name().lastName())
                .email(firstName.toLowerCase() + "@yopmail.com")
                .gender(faker.options().option("MALE", "FEMALE", "OTHER"))
                .phone("+1" + faker.phoneNumber().subscriberNumber(10))
                .roleType("STAFF")
                .role(faker.options().option("SUPER_ADMIN", "FRONTDESK", "BILLER"))
                .address(address).build();


        System.out.println("pojo staff data: " + pojo);
        // Setup request
        ApiRequestBuilder.PostAPI(SuperAdminAccessToken, pojo, endpoint);
        this.response = ApiRequestBuilder.response;
    }

    @Then("I verify that the Admin staff is added successfully with {int} status code")
    public void verifyStaffAddition(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);

        // Additional verifications
        Assert.assertNotNull("Response should not be null", response);
        Assert.assertNotNull("Response body should not be null", response.getBody());

    }

    @Given("I set up the request structure to see the staff list")
    public void iSetUpTheRequestStructureToSeeTheStaffList(Map<String, Object> data) {
        endpoint = data.get("endpoint").toString();
        String page = data.get("page").toString();
        String size = data.get("size").toString();
        String role = data.get("role").toString();
        String roleType = data.get("roleType").toString();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("size", size);
        queryParams.put("role", role);
        queryParams.put("roleType", roleType);


        ApiRequestBuilder.GetAPI(SuperAdminAccessToken, queryParams, endpoint);
        this.response = ApiRequestBuilder.response;


    }

    @Then("I verify that the Admin staff is see the list of staff successfully with {int} status code")
    public void iVerifyThatTheAdminStaffIsSeeTheListOfStaffSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        Assert.assertNotNull("No staff list displayed",response.getBody());

    }
}