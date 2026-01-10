package utils;

import com.thinkitive.eAmata.ApiRequestBuilder;
import com.thinkitive.eAmata.propertyHandler;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;

import java.util.HashMap;
import java.util.Map;

public class AuthManager extends ApiRequestBuilder {
    public static String superAdminLogin(String username, String password) {

        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", username);
        loginData.put("password", password);

        request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(loginData).log().all();

        response = request.post("login");
        response.prettyPrint();

        return response.jsonPath().get("data.access_token");
    }

    public static String hcpAdminLogin(String username, String password) {

        Map<String, String> loginData = new HashMap<>();
        loginData.put("username", username);
        loginData.put("password", password);

        request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-tenant-id", propertyHandler.getProperty("TenantId"))
                .body(loginData).log().all();

        response = request.post("login");
        response.prettyPrint();

        return response.jsonPath().get("data.access_token");
    }
}
