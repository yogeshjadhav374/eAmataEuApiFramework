package com.thinkitive.eAmata.stepDefinitions.SuperAdmin_Portal;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Assert;

import com.github.javafaker.Faker;
import com.thinkitive.eAmata.ApiRequestBuilder;
import entities.payloads.CarePlanPayload;
import entities.payloads.CarePlanPayload.ProgramGoal;
import entities.payloads.CarePlanPayload.ProgramGoalTask;
import entities.payloads.CarePlanPayload.Target;
import entities.payloads.CarePlanPayload.VitalRange;
import entities.payloads.CarePlanPayload.VitalReference;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CarePlanStep extends ApiRequestBuilder {

    private static String carePlanId;

    @Given("I set up the request structure to add the care plan")
    public void setupRequestStructureToAddCarePlan(Map<String, String> data) {
        String endpoint = data.get("endpoint");
        Faker faker = new Faker();

        // Create Payload
        CarePlanPayload payload = CarePlanPayload.builder()
                .title("Care Plan " + faker.medical().diseaseName())
                .duration(faker.number().numberBetween(1, 12))
                .durationUnit("MONTH")
                .overview(faker.lorem().paragraph())
                .dietUuids(Collections.singletonList("d6feb490-914a-4f5c-8ff7-a7175017111b")) // Using ID from curl as safe default
                .gender("MALE")
                .ageCriteria("Older than")
                .age("50")
                .diagnosisCodes(Collections.singletonList("A00.0"))
                .deviceName(Collections.singletonList("Smart watch 21 pro max"))
                .vitalReferences(Arrays.asList(
                        VitalReference.builder()
                                .vitalType("Blood Pressure")
                                .vitalRanges(Arrays.asList(
                                        VitalRange.builder().rangeType("LOW_MODERATE_SYSTOLIC").min(91).max(100).build(),
                                        VitalRange.builder().rangeType("HIGH_MODERATE_SYSTOLIC").min(131).max(140).build(),
                                        VitalRange.builder().rangeType("LOW_MODERATE_DIASTOLIC").min(61).max(80).build(),
                                        VitalRange.builder().rangeType("HIGH_MODERATE_DIASTOLIC").min(81).max(90).build(),
                                        VitalRange.builder().rangeType("CRITICAL_SYSTOLIC").min(141).max(180).build(),
                                        VitalRange.builder().rangeType("CRITICAL_DIASTOLIC").min(91).max(120).build(),
                                        VitalRange.builder().rangeType("NORMAL_SYSTOLIC").min(101).max(130).build(),
                                        VitalRange.builder().rangeType("NORMAL_DIASTOLIC").min(61).max(80).build()
                                ))
                                .build(),
                        VitalReference.builder()
                                .vitalType("Heart Rate")
                                .vitalRanges(Arrays.asList(
                                        VitalRange.builder().rangeType("LOW_MODERATE").min(41).max(60).build(),
                                        VitalRange.builder().rangeType("HIGH_MODERATE").min(91).max(110).build(),
                                        VitalRange.builder().rangeType("CRITICAL").min(111).max(180).build(),
                                        VitalRange.builder().rangeType("NORMAL").min(61).max(90).build()
                                ))
                                .build(),
                        VitalReference.builder()
                                .vitalType("BMI")
                                .vitalRanges(Collections.singletonList(
                                        VitalRange.builder().rangeType("NORMAL").min(18.5).max(24.9).build()
                                ))
                                .build()
                ))
                .programGoals(Collections.singletonList(
                        ProgramGoal.builder()
                                .category("BLOOD_PRESSURE")
                                .title("blood pressure")
                                .trackBy("DAY")
                                .targets(Arrays.asList(
                                        Target.builder().targetType("SYSTOLIC").targetValue(120).targetCondition("LESS_THAN").unit("MMHG").build(),
                                        Target.builder().targetType("DIASTOLIC").targetValue(90).targetCondition("LESS_THAN").unit("MMHG").build()
                                ))
                                .objective(faker.lorem().sentence())
                                .programGoalTasks(Collections.singletonList(
                                        ProgramGoalTask.builder().task("Record blood pressure readings twice daily using assigned BP device.").details(faker.lorem().sentence()).build()
                                ))
                                .build()
                ))
                .protocolType("OUT_OF_RANGE_BP")
                .build();

        ApiRequestBuilder.PostAPI(superAdminToken, payload, endpoint);
    }

    @Then("I verify that the care plan is added successfully with {int} status code")
    public void verifyCarePlanAdded(int expectedStatusCode) {
        response.prettyPrint();
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());
        String expectedMessage = "Care plan created successfully.";

        Assert.assertNotNull(response.jsonPath().get("message")); // Assuming response returns uuid
    }
}
