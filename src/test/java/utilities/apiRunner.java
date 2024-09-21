package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.CustomResponse;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Data
public  class apiRunner {
    @Getter
    private static CustomResponse customResponse;

    //get without params
    public  static void runGET(String path){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + path;

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .get(url);
        System.out.println("Status code " + response.statusCode());

        ObjectMapper mapper = new ObjectMapper();

        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a list response ");
        }
    }

    // get api with params
    public static  void runGet(String path, Map<String, Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + path;
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .get(url);
        System.out.println("Status code " + response.statusCode());
        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a list response ");
        }

    }
    // post with request body
    public static  void runPOST(String path, RequestBody requestBody){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl");
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody).post(url);
        System.out.println("this is a list response ");
        ObjectMapper mapper = new ObjectMapper();
        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a list response ");
        }
    }
    // post without request body
    public static  void runPOST(String path,  Map<String, Object> params){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + path;
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .post(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Status code " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a single response ");
        }


    }

    //delete
    public static void runDelete(String path){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + path;
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .delete(url);
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Status code " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a single response ");
        }

    }

    //update
    public static void runPut(String path, RequestBody requestBody, int id){
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + path + id;
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put(url);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Status code " + response.statusCode());
        try {
            customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        }  catch (JsonProcessingException e) {
            System.out.println("this is a single response ");
        }


    }






}
