package entities.payloads;

import com.github.javafaker.Faker;
import entities.payloads.CarePlanPayload.ProgramGoal;
import entities.payloads.CarePlanPayload.ProgramGoalTask;
import entities.payloads.CarePlanPayload.Target;
import entities.payloads.CarePlanPayload.VitalRange;
import entities.payloads.CarePlanPayload.VitalReference;

import java.util.Arrays;
import java.util.Collections;

/**
 * Generates dynamic payloads for Care Plan API endpoints.
 * Extends the existing CarePlanPayload builder with faker-powered generation.
 */
public class CarePlanPayloadGenerator {

    private static final Faker faker = new Faker();

    /**
     * Generates a complete care plan creation payload with dynamic data.
     */
    public static CarePlanPayload generateCreateCarePlanPayload() {
        return CarePlanPayload.builder()
                .title("Care Plan " + faker.medical().diseaseName())
                .duration(faker.number().numberBetween(1, 12))
                .durationUnit("MONTH")
                .overview(faker.lorem().paragraph())
                .gender(faker.options().option("MALE", "FEMALE", "UNISEX"))
                .ageCriteria(faker.options().option("Older than", "Younger than", "Between"))
                .age(String.valueOf(faker.number().numberBetween(18, 90)))
                .diagnosisCodes(Collections.singletonList("A00.0"))
                .deviceName(Collections.singletonList("Smart watch 21 pro max"))
                .vitalReferences(Arrays.asList(
                        generateBloodPressureVitalReference(),
                        generateHeartRateVitalReference(),
                        generateBmiVitalReference()
                ))
                .programGoals(Collections.singletonList(generateBloodPressureGoal()))
                .protocolType("OUT_OF_RANGE_BP")
                .build();
    }

    /**
     * Generates a care plan payload with minimal required fields for negative testing.
     */
    public static CarePlanPayload generateMinimalCarePlanPayload() {
        return CarePlanPayload.builder()
                .title("Minimal Care Plan " + faker.number().digits(4))
                .diagnosisCodes(Collections.singletonList("B00.0"))
                .build();
    }

    /**
     * Generates a care plan payload missing mandatory diagnosisCodes for negative testing.
     */
    public static CarePlanPayload generateInvalidCarePlanPayload() {
        return CarePlanPayload.builder()
                .title("Invalid Care Plan")
                .duration(6)
                .durationUnit("MONTH")
                .diagnosisCodes(null) // mandatory field set to null
                .build();
    }

    /**
     * Generates Blood Pressure vital reference with all range types.
     */
    private static VitalReference generateBloodPressureVitalReference() {
        return VitalReference.builder()
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
                .build();
    }

    /**
     * Generates Heart Rate vital reference.
     */
    private static VitalReference generateHeartRateVitalReference() {
        return VitalReference.builder()
                .vitalType("Heart Rate")
                .vitalRanges(Arrays.asList(
                        VitalRange.builder().rangeType("LOW_MODERATE").min(41).max(60).build(),
                        VitalRange.builder().rangeType("HIGH_MODERATE").min(91).max(110).build(),
                        VitalRange.builder().rangeType("CRITICAL").min(111).max(180).build(),
                        VitalRange.builder().rangeType("NORMAL").min(61).max(90).build()
                ))
                .build();
    }

    /**
     * Generates BMI vital reference.
     */
    private static VitalReference generateBmiVitalReference() {
        return VitalReference.builder()
                .vitalType("BMI")
                .vitalRanges(Collections.singletonList(
                        VitalRange.builder().rangeType("NORMAL").min(18.5).max(24.9).build()
                ))
                .build();
    }

    /**
     * Generates a blood pressure program goal.
     */
    private static ProgramGoal generateBloodPressureGoal() {
        return ProgramGoal.builder()
                .category("BLOOD_PRESSURE")
                .title("blood pressure")
                .trackBy("DAY")
                .targets(Arrays.asList(
                        Target.builder().targetType("SYSTOLIC").targetValue(120).targetCondition("LESS_THAN").unit("MMHG").build(),
                        Target.builder().targetType("DIASTOLIC").targetValue(90).targetCondition("LESS_THAN").unit("MMHG").build()
                ))
                .objective(faker.lorem().sentence())
                .programGoalTasks(Collections.singletonList(
                        ProgramGoalTask.builder()
                                .task("Record blood pressure readings twice daily using assigned BP device.")
                                .details(faker.lorem().sentence())
                                .build()
                ))
                .build();
    }
}
