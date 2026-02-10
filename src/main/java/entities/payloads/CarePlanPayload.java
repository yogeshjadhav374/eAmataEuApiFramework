package entities.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarePlanPayload {

    private String title;
    private int duration;
    private String durationUnit;
    private String overview;
    private List<String> dietUuids;
    private String gender;
    private String ageCriteria;
    private String age;
    private List<String> diagnosisCodes;
    private List<String> deviceName;
    private List<VitalReference> vitalReferences;
    private List<ProgramGoal> programGoals;
    private String protocolType;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VitalReference {

        private String vitalType;
        private List<VitalRange> vitalRanges;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VitalRange {

        private String rangeType;
        private Number min;
        private Number max;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramGoal {

        private String category;
        private String title;
        private String trackBy;
        private List<Target> targets;
        private String objective;
        private List<ProgramGoalTask> programGoalTasks;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Target {

        private String targetType;
        private Number targetValue;
        private String targetCondition;
        private String unit;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProgramGoalTask {

        private String task;
        private String details;
    }
}
