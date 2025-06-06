package com.thinkitive.eAmata;

import io.cucumber.java.Before;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mozilla.javascript.ast.IfStatement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {
    public static RequestSpecification request = given();
    public static Response response;
    private static String pathParam;
    public static String SuperAdminAccessToken;


    public static void setRequestStructure(String access_token) {
        if(access_token == null){
            request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .log().all();
        }else {
            request.baseUri(propertyHandler.getProperty("baseUri")).basePath(propertyHandler.getProperty("basePath"))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer\n" + access_token)
                    .log().all();
        }

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
        request.pathParams("pathParams",p);
    } );
    }



    public static void setRequestBody(String body) {
        Optional.ofNullable(body).ifPresent(obj-> request.body(obj));

    }

    public static <T> void setRequestBody(T classObject){
        Optional.ofNullable(classObject).ifPresent(obj->request.body(obj));
    }

    public static void setRequestBody(Map<String,Object> body){
        Optional.ofNullable(body).ifPresent(obj-> request.body(obj));

    }

    public static JSONObject setRequestBodyWithFile(String filePath){
        JSONObject jsonObject=null;
        if(Objects.nonNull(filePath) &&  !filePath.isEmpty()) {
            JSONParser jsonParser = new JSONParser();
           // byte[] payload;
            try {
                FileReader reader = new FileReader(filePath);
                Object object = jsonParser.parse(reader);
                jsonObject =(JSONObject) object;
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

    public static void loginPostRequest(String access_Token, Map<String, Object> map, String endPoint){
        setRequestStructure(access_Token);
        setRequestBody(map);
        execute(Method.POST, endPoint);
    }

    public static <T> void PostAPI(String access_Token, T Dazz, String endpoint){
        setRequestStructure(access_Token);
        setRequestBody(Dazz);
        execute(Method.POST, endpoint);
    }


    public static void GetAPI(String access_Token, Map<String, Object> queryParams, String endpoint){
        setRequestStructure(access_Token);
        setQueryParams(queryParams);
        execute(Method.GET, endpoint);
    }



}
