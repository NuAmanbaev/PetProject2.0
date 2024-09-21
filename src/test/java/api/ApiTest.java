package api;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utilities.CashWiseToken;
import utilities.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ApiTest {
    Faker faker = new Faker();

    @Test
    public void testToken(){
        String endPoint = "https://backend.cashwise.us/api/myaccount/auth/login";
        RequestBody requestBody = new RequestBody();

        requestBody.setEmail("amanbaev62@gmail.com");
        requestBody.setPassword("Amanbaev1997");

        Response response =  RestAssured.given().contentType(ContentType.JSON)
                .body(requestBody).post(endPoint);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
       response.prettyPrint();

       String token = response.jsonPath().getString("jwt_token");
        System.out.println(token);

    }

    @Test
    public void getSingleSeller(){
        String url = Config.getProperty("casWiseApi") +  "/api/myaccount/sellers/" + 4631;
        String token = CashWiseToken.getToken();

        Response response = RestAssured.given().auth().oauth2(token).get(url);
        String expecteedEmail =  response.jsonPath().getString("email");
        Assert.assertFalse(expecteedEmail.isEmpty());
        response.prettyPrint();

       Assert.assertTrue(expecteedEmail.endsWith("123"));



    }
    @Test
    public void getAllSellers(){
        String url = Config.getProperty("casWiseApi") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size", 2);


        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);
        int statusCode = response.statusCode();
        Assert.assertEquals(200, statusCode);
        String email = response.jsonPath().getString("responses[0].email");
        Assert.assertFalse(email.isEmpty());
        response.prettyPrint();
        System.out.println(statusCode);


    }

    @Test
    public void CreateSeller(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        String token = CashWiseToken.getToken();
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name("Suka");
        requestBody.setSeller_name("Suka");
        requestBody.setEmail("Suka@gmail.com");
        requestBody.setPhone_number("1231232233");
        requestBody.setAddress("Suka");

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);

        int status = response.statusCode();
        Assert.assertEquals(201,status);

    }

    @Test
    public void CreateAnotherSeller(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";


        for (int i = 0; i <15 ; i++) {
            String token1 = CashWiseToken.getToken();
            RequestBody requestBody = new RequestBody();
            requestBody.setCompany_name(faker.name().firstName());
            requestBody.setSeller_name(faker.name().fullName());
            requestBody.setEmail(faker.internet().emailAddress());
            requestBody.setPhone_number(faker.phoneNumber().phoneNumber());
            requestBody.setAddress(faker.address().fullAddress());
            Response response = RestAssured
                    .given()
                    .auth()
                    .oauth2(token1)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post(url);
            int statusOf = response.statusCode();
            response.prettyPrint();
            System.out.println(statusOf);

        }










//        String id = response.jsonPath().getString("seller_id");
//        String url2 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + id;
//
//            Response response1 = RestAssured
//                    .given()
//                    .auth()
//                    .oauth2(token1)
//                    .get(url2);
//            Assert.assertEquals(200,response1.getStatusCode());
//
//
//        response.prettyPrint();
//        System.out.println(response.prettyPrint());

    }










}
