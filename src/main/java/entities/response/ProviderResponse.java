package entities.response;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response POJO for Provider/Nurse Management API endpoints.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProviderResponse {

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
        public List<Provider> content;
        public Page page;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Provider {
        public String uuid;
        public String email;
        public String firstName;
        public String lastName;
        public String phone;
        public String gender;
        public String avatar;
        public String npi;
        public String role;
        public String providerType;
        public String introduction;
        public String chatbotTone;
        public String onboardStatus;
        public boolean active;
        public boolean archive;
        public boolean emailVerified;
        public Address address;
        public List<ProviderLicenseDetails> providerLicenseDetails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        public String line1;
        public String line2;
        public String city;
        public String state;
        public String country;
        public String zipcode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProviderLicenseDetails {
        public String uuid;
        public String licenseNumber;
        public Instant expiryDate;
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
