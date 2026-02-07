package com.thinkitive.eAmata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static io.restassured.RestAssured.given;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiRequestBuilder {

    public static RequestSpecification request = given();
    public static Response response;
    private static String pathParam;
    public static String superAdminToken;
    public static String hcpAdminToken;

    public static void resetRequest() {
        request = given();
        pathParam = null;
    }

    public static void setRequestStructure(String access_token) {
        request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer\n" + access_token)
                .log().all();

    }

    public static void setRequestStructure(String access_token, String TenantId) {
        request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("x-tenant-id",TenantId)
                .header("Authorization", "Bearer\n" + access_token)
                .log().all();

    }

    public static void execute(Method method, String endPoint) {
        switch (method) {
            case GET:
                response = Objects.isNull(pathParam) ?
                        request.get(endPoint) : request.get(endPoint + "/" + pathParam);
                break;
            case POST:
                response = Objects.isNull(pathParam) ?
                        request.post(endPoint) : request.post(endPoint + "/" + pathParam);
                break;
            case PUT:
                response = Objects.isNull(pathParam) ?
                        request.put(endPoint) : request.put(endPoint + "/" + pathParam);
                break;
            case DELETE:
                response = Objects.isNull(pathParam) ?
                        request.delete(endPoint) : request.delete(endPoint + "/" + pathParam);
                break;
            case PATCH:
                response = Objects.isNull(pathParam) ?
                        request.patch(endPoint) : request.patch(endPoint + "/" + pathParam);
                break;
        }

    }

    public static void setQueryParams(Map<String, Object> queryParams) {
        Optional.ofNullable(queryParams).ifPresent(obj -> request.queryParams(obj));
    }


    public static void setpathParam(String Param){
        Optional.ofNullable(Param).ifPresent(p->{
            pathParam = p;
            // request.pathParams("pathParams",p);
    } );
    }

    public static void setRequestBody(String body) {
        Optional.ofNullable(body).ifPresent(obj -> request.body(obj));

    }

    public static <T> void setRequestBody(T classObject) {
        Optional.ofNullable(classObject).ifPresent(obj -> request.body(obj));
    }

    public static void setRequestBody(Map<String, Object> body) {
        Optional.ofNullable(body).ifPresent(obj -> request.body(obj));

    }

    public static JSONObject setRequestBodyWithFile(String filePath) {
        JSONObject jsonObject = null;
        if (Objects.nonNull(filePath) && !filePath.isEmpty()) {
            JSONParser jsonParser = new JSONParser();
            // byte[] payload;
            try {
                FileReader reader = new FileReader(filePath);
                Object object = jsonParser.parse(reader);
                jsonObject = (JSONObject) object;
                //   payload = Files.readAllBytes(Path.of(filePath));  // access the content from json file and convert into byte array
                request.body(jsonObject);
            } catch (FileNotFoundException e) {
                throw new RuntimeException("file not found ");
            } catch (IOException e) {
                throw new RuntimeException("IO exception");
            } catch (ParseException e) {
                throw new RuntimeException("parsing exception");
            }
        }
        return jsonObject;
    }

    public static void PostAPI(String access_Token, Map<String, Object> payload, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setRequestBody(payload);
        execute(Method.POST, endpoint);
    }

    public static <T> void PostAPI(String access_Token, T Dazz, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setRequestBody(Dazz);
        execute(Method.POST, endpoint);
    }

    public static void GetAPI(String access_Token, Map<String, Object> queryParams, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setQueryParams(queryParams);
        execute(Method.GET, endpoint);
    }

    public static void PutAPI(String access_Token, String jsonUrl, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setRequestBodyWithFile(jsonUrl);
        execute(Method.PUT, endpoint);
    }

    public static void PutAPI(String access_Token, Map<String, Object> payload, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setRequestBody(payload);
        execute(Method.PUT, endpoint);
    }

    public static void PutAPI(String access_Token, Map<String, Object> payload, String uuid, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setRequestBody(payload);
        setpathParam(uuid);
        execute(Method.PUT, endpoint);
    }

    public static void PutStatusAPI(String access_Token, String uuid, String status, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        // Construct path: endpoint/uuid/status/statusValue
        String fullPath = endpoint + "/" + uuid + "/status/" + status;
        response = request.put(fullPath);
        // Note: execute() method handles simple path params, but this is composite. 
        // Direct execution here for simplicity or adapt execute method.
        // For consistency with framework pattern, let's try to stick to patterns if possible, 
        // but this specific path structure is unique.
        // Let's rely on the simple request.put() for this specific case as it doesn't fit the swich(Method) perfectly with single path param
    }

    public static void DeleteByIdAPI(String access_Token, String uuid, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setpathParam(uuid);
        execute(Method.DELETE, endpoint);
    }

    public static void GetByIdAPI(String access_Token, String uuid, String endpoint) {
        resetRequest();
        setRequestStructure(access_Token);
        setpathParam(uuid);
        execute(Method.GET, endpoint);
    }

}
