package entities.response;

import java.time.Instant;

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
public class SuperAdminStaffResponse {

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

        public java.util.List<StaffMember> content;
        public Page page;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StaffMember {

        public String uuid;
        public String iamId;
        public String email;
        public String firstName;
        public String lastName;
        public String middleName;
        public String phone;
        public String gender;
        public String avatar;
        public Instant birthDate;
        public String roleType;
        public String role;
        public Address address;
        public Instant lastLogin;
        public boolean active;
        public boolean archive;
        public boolean emailVerified;
        public boolean phoneVerified;
        public String password;
        public String tenantKey;
        public String primaryLanguage;
        public boolean acceptTerms;
        public boolean currentMonthSummary;
        public boolean accountDeletionRequested; // Changed to String as it was null in logs
        public String accountDeletionReason; // Changed to String as it was null in logs
        public String selfCheckUuid;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {

        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;
        private String zipcode;
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
