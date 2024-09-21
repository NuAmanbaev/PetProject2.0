package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import entities.CustomResponse;
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

public class PojoTest {
    Faker faker = new Faker();

    @Test
    public void CreateCategory() throws JsonProcessingException {

    String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";
    String token = CashWiseToken.getToken();
    RequestBody requestBody = new RequestBody();
    requestBody.setCategory_title(faker.name().title());
    requestBody.setCategory_description(faker.address().firstName());
    requestBody.setFlag(true);
//
//        Response response = RestAssured
//                .given()
//                .auth()
//                .oauth2(token)
//                .contentType(ContentType.JSON)
//                .body(requestBody)
//                .post(url);

            Response response = RestAssured
                    .given()
                    .auth()
                    .oauth2(token)
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .post(url);
            int status = response.statusCode();
            Assert.assertEquals(201,status);
            ObjectMapper mapper = new ObjectMapper();
            CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
            System.out.println(customResponse.getCategory_id());


    }



    @Test
    public void getSingleIdOfCategory(){

        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/categories";
        String token1 = CashWiseToken.getToken();
        RequestBody requestBody = new RequestBody();
        requestBody.setCategory_title(faker.name().title());
        requestBody.setCategory_description(faker.address().firstName());
        requestBody.setFlag(true);

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token1)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);
                int status1 = response.statusCode();

                String id = response.jsonPath().getString("category_id");
        Assert.assertEquals(201,status1);

        String url2 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/" + id;


        Response response1 = RestAssured
                .given()
                .auth()
                .oauth2(token1)
                .get(url2);
        Assert.assertEquals(200,response1.getStatusCode());
        System.out.println(id);

    }


    @Test
    public void getAllSellers() throws JsonProcessingException {
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        String token = CashWiseToken.getToken();

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", false);
        params.put("page", 1);
        params.put("size",100);
        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .get(url);
        int status = response.statusCode();
        Assert.assertEquals(200,status);
        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int size = customResponse.getResponses().size();

        for (int i = 0; i < size ; i++) {
            String email = customResponse.getResponses().get(i).getEmail();

//            Assert.assertFalse(email.isEmpty());

        }
        response.prettyPrint();


    }

}
