package entities.Pojo;

import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Map;


public class Address {
    static Faker faker = new Faker();
    static Map<String, Object> address = new HashMap<>();
public static Object getAddress(){
    address.put("line1", faker.address().streetAddress());
    address.put("line2","Lake Michelleton, PW 27974");
    address.put("city","Akutan");
    address.put("state","Newyork");
    address.put("country","USA");
    address.put("zipcode","23454");
 return address;
}



}
