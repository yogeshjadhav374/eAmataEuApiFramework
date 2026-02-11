package entities.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarePlanResponse {

    public Instant date;
    public String code;
    public String message;
    public Data data;
    public String path;
    public String requestId;
    public String version;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Data {

        public List<CarePlan> content;
        public Page page;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CarePlan {

        public String uuid;
        public String carePlanReferenceId;
        public String title;
        public Integer duration;
        public String durationUnit;
        public String overview;
        public String gender;
        public String ageCriteria;
        public String age;
        public String deviceName;
        public List<String> deviceModels;
        public String routineCheckup;
        public String programGoals;
        public String vitalReferences;
        public String tenantId;
        public Boolean external;
        public String protocolType;
        public Boolean active;
        public Boolean archive;
        public List<String> trackedVitals;
        public Instant modified;
        public List<String> diagnosisCodes;
        public String protocol;
        public List<String> dietUuids;
        public String programDiet;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Page {

        public int size;
        public int number;
        public int totalElements;
        public int totalPages;
    }
}
