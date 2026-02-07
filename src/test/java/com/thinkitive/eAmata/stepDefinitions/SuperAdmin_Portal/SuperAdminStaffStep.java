package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import com.github.javafaker.Faker;
import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.response.SuperAdminStaffResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.assertNotNull;

public class SuperAdminStaffStep extends ApiRequestBuilder{
      Faker faker = new Faker();
//    private String endpoint;
//    staffDetailsPojo pojo;
//    static String uuid;
//    static String firstName;
//    static String lastName;
//    static String email;
//    static String phoneNumber;
//    static String gender;
//    static String role;
//    static String roleType;
//    Address address = Address.builder()
//            .line1(faker.address().streetAddress())
//            .line2("a1 street")
//            .state("Arizona")
//            .city("Akutan")
//            .country("USA")
//            .zipcode("65895").build();


    @Given("I set up the request structure to add the Admin staff")
    public void setupRequestStructure(Map<String, Object> data) {
        String endpoint = data.get("endpoint").toString();
        String firstName = faker.name().firstName();

        Map<String, String> address = new HashMap<>();
        address.put("line1", faker.address().streetAddress());
        address.put("city", "paris");
        address.put("state", "Île-de-France");
        address.put("country", "France");
        address.put("zipcode", "65478");

        Map<String, Object> staffEntity = new HashMap<>();
        staffEntity.put("firstName", firstName);
        staffEntity.put("lastName", faker.name().lastName());
        staffEntity.put("email", "yogesh.jadhav+superadmin10@eamata.com" );
        staffEntity.put("gender", faker.options().option("MALE"));  //"FEMALE", "OTHER"
        staffEntity.put("phone", "+335225655669");
        staffEntity.put("roleType","ADMIN" );
        staffEntity.put("active", true);
        staffEntity.put("role", faker.options().option("SUPER_ADMIN") );  //"SUPPORT_ADMIN"
        staffEntity.put("address",address );


        // Setup request
        ApiRequestBuilder.PostAPI(superAdminToken,staffEntity, endpoint);
    }

    @Then("I verify that the Admin staff is added successfully with {int} status code")
    public void verifyStaffAddition(int expectedStatusCode) {
        response.prettyPrint();
        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(expectedStatusCode, actualStatusCode);

        // Additional verifications
        assertNotNull("Response should not be null", response);
        assertNotNull("Response body should not be null", response.getBody());

    }

    @Given("I set up the request structure to see the staff list")
    public void iSetUpTheRequestStructureToSeeTheStaffList(Map<String, Object> data) {
        String endpoint = data.get("endpoint").toString();
        String page = data.get("page").toString();
        String size = data.get("size").toString();
        String role = data.get("role").toString();
        String roleType = data.get("roleType").toString();
        String sortBy = data.get("sortBy").toString();
        String sortDirection = data.get("sortDirection").toString();

        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        queryParams.put("size", size);
//        queryParams.put("role", role);
//        queryParams.put("roleType", roleType);
        queryParams.put("sortBy", sortBy);
        queryParams.put("sortDirection", sortDirection);


        ApiRequestBuilder.GetAPI(superAdminToken, queryParams, endpoint);

    }

    @Then("I verify that the Admin staff is see the list of staff successfully with {int} status code")
    public void iVerifyThatTheAdminStaffIsSeeTheListOfStaffSuccessfullyWithStatusCode(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());

        SuperAdminStaffResponse staffResponse = response.as(SuperAdminStaffResponse.class);

//        assertNotNull(staffResponse);    // Now you have the deserialized object and can access the data
//        assertNotNull(staffResponse.code);

//        System.out.println("uuid: " + ;// staffResponse.content.get(0).uuid);
//        System.out.println("Number of staff members: " + staffResponse.data.content.size());

        // Example: Accessing details of the first staff member
//        if (!staffResponse.data.content.isEmpty()) {
//            StaffDetailsResponse.StaffMember firstStaff = staffResponse.data.content.get(0);
//            uuid = firstStaff.uuid;
//            firstName = firstStaff.firstName;
//            lastName = firstStaff.lastName;
//            email = firstStaff.email;
//            gender = firstStaff.gender;
//            phoneNumber = firstStaff.phone;
//            role = firstStaff.role;
//            roleType = firstStaff.roleType;
//
//
//            System.out.println("First Staff Email: " + firstStaff.email);
//            System.out.println("First Staff First Name: " + firstStaff.firstName);
//
//        } else {
//            System.out.println("First Staff details Not available");
//        }

    }
//
//
//    @Given("I set up the request structure to edit the staff details")
//    public void iSetUpTheRequestStructureToEditTheStaffDetails(Map<String, Object> data) {
//        String endpoint = data.get("endpoint").toString();
//        String jsonPath = System.getProperty("user.home") + "/IdeaProjects/eAmataAPITestAutomation/src/test/resources/staffDetails.json";
//
//        pojo = staffDetailsPojo.builder()
//                .uuid(uuid)
//                .firstName(firstName)
//                .lastName(lastName)
//                .email(email)
//                .gender(gender)
//                .phone(phoneNumber)
//                .role(role)
//                .roleType(roleType)
//                .address(address)
//                .build();
//
//        if (Objects.isNull(pojo)) {
//            System.out.println("updating the user that is provided in the json file");
//            ApiRequestBuilder.PutAPI(SuperAdminAccessToken, jsonPath, endpoint);
//        } else {
//            System.out.println("updating the staff details that is provided in pojo class");
//            ApiRequestBuilder.PutAPI(SuperAdminAccessToken, pojo, endpoint);
//        }
//        this.response = ApiRequestBuilder.response;
//
//    }
//
//
//    @Then("I verify that the user can edit the staff details successfully with {int} status code")
//    public void iVerifyThatTheUserCanEditTheStaffDetailsSuccessfullyWithStatusCode(int expectedStatusCode) {
//        response.prettyPrint();
//        int actualStatusCode = response.getStatusCode();
//        Assert.assertEquals(expectedStatusCode, actualStatusCode);
//        assertNotNull(response.jsonPath().get("message"));
//
//    }
//
//    @Given("I set up the request structure to view the staff details")
//    public void iSetUpTheRequestStructureToViewTheStaffDetails(Map<String, Object> data) {
//        String endpoint = data.get("endpoint").toString();
//
//        String UUID = Objects.isNull(uuid) ? data.get("uuid").toString() : uuid;
//
//        ApiRequestBuilder.GetByIdAPI(SuperAdminAccessToken, UUID, endpoint);
//        this.response = ApiRequestBuilder.response;
//
//    }
//
//    @Then("I verify that the user can view the staff details successfully with {int} status code")
//    public void iVerifyThatTheUserCanViewTheStaffDetailsSuccessfullyWithStatusCode(int expectedStatusCode) {
//        response.prettyPrint();
//        int actualStatusCode = response.getStatusCode();
//
//        Assert.assertEquals(expectedStatusCode, actualStatusCode);
//        assertNotNull(response.getBody());
//
//
//    }
}
