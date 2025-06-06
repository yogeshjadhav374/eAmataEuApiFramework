package entities.Pojo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class staffDetailsPojo {

    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String gender;
    private String phone;
    private String roleType;
    private String role;
    private Address address;

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Address{
        private String line1;
        private String line2;
        private String city;
        private String state;
        private String country;
        private String zipcode;
    }

}
