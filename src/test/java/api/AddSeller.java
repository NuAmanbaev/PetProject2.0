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
import utilities.apiRunner;

import java.util.HashMap;
import java.util.Map;

public class AddSeller {

    Faker faker = new Faker();

    @Test
   public void ArchivedTest(){
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token = CashWiseToken.getToken();

        Map<String, Object> params = new HashMap<>();
        params.put("sellersIdsForArchive", 5531);
        params.put("archive", true);

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .post(url);

        int statusCode = response.statusCode();
        response.prettyPrint();
        System.out.println(statusCode);
        Assert.assertEquals(200, statusCode);


    }


    @Test
    public void ArchiveAll(){
        String url1 = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        String token1 = CashWiseToken.getToken();
    //    RequestBody requestBody = new RequestBody();
        CustomResponse customResponse = new CustomResponse();
        customResponse.getResponses();

        Map<String, Object> params1 = new HashMap<>();

       // params1.put("sellersIdsForArchive", customResponse);
      //  params1.put("archive", true);
        for (int i = 0; i <params1.size() ; i++) {
            Response response = RestAssured

                    .given()
                    .auth()
                    .oauth2(token1)
                    .params(params1)
                    .post(url1);

            int statusCode1 = response.statusCode();
            response.prettyPrint();
            System.out.println(statusCode1);
        //    Assert.assertEquals(200,statusCode1);

            //not working
        }
    }



    @Test
    public void ArchiveAllTwo() throws JsonProcessingException {
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", false );
        params.put("page", 1);
        params.put("size",110);

        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .get(url);
        int statuus = response.statusCode();

        Assert.assertEquals(200,statuus);

        int status = response.statusCode();
        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);
        int size = customResponse.getResponses().size();
        String urlToArchive = Config.getProperty("cashWiseApiUrl") +"/api/myaccount/sellers/archive/unarchive";
        for (int i = 0; i < size; i++) {
            int id = customResponse.getResponses().get(i).getSeller_id();
            Map<String, Object> paramsToArchive = new HashMap<>();
            paramsToArchive.put("sellersIdsForArchive", id);
            paramsToArchive.put("archive", true);

            Response response1 = RestAssured
                    .given()
                    .auth()
                    .oauth2(token)
                    .params(paramsToArchive)
                    .post(urlToArchive);

            int statusCode1 = response1.statusCode();
            Assert.assertEquals(200, statusCode1);
        }
    }


    @Test
    public void GetAllActivate() throws JsonProcessingException {
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();

        params.put("isArchived", true);
        params.put("page", 1 );
        params.put("size", 110 );

        Response response = RestAssured.given().auth().oauth2(token).params(params).get(url);

        int status = response.statusCode();
        Assert.assertEquals(200, status);

        ObjectMapper mapper = new ObjectMapper();

        CustomResponse customResponse = mapper.readValue(response.asString(), CustomResponse.class);

        String urlToArchive = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers/archive/unarchive";
        int size = customResponse.getResponses().size();

        for(int i = 0; i < size; i ++ ){

            if(customResponse.getResponses().get(i).getEmail() != null ){
                if(customResponse.getResponses().get(i).getEmail().endsWith("@hotmail.com") ){
                    int id = customResponse.getResponses().get(i).getSeller_id();

                    Map<String, Object> paramsToArchive = new HashMap<>();

                    paramsToArchive.put("sellersIdsForArchive",id );
                    paramsToArchive.put("archive", false);

                    Response response1 = RestAssured.given().auth().oauth2(token).params(paramsToArchive).post(urlToArchive);

                    int status1 = response1.statusCode();

                    Assert.assertEquals(200, status1);
                }

            }

        }
    }

    @Test
    public void createSeller() throws JsonProcessingException {
        String token = CashWiseToken.getToken();
        String url = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.name().lastName());
        requestBody.setSeller_name(faker.name().fullName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number("3124396556");
        requestBody.setAddress(faker.address().fullAddress());


        Response response = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(url);
        int status = response.statusCode();
        Assert.assertEquals(201, status);
        System.out.println(status);

        ObjectMapper mapper = new ObjectMapper();
        CustomResponse customResponse = mapper.readValue(response.asString(),CustomResponse.class);
        int Expected = customResponse.getSeller_id();
        String uerlToGetSeller = Config.getProperty("cashWiseApiUrl") + "/api/myaccount/sellers";

        Map<String, Object> params = new HashMap<>();
        params.put("isArchived", true);
        params.put("page", 2);
        params.put("size", 5);

        Response response1 = RestAssured
                .given()
                .auth()
                .oauth2(token)
                .params(params)
                .get(uerlToGetSeller);
        int status1 = response1.statusCode();
        System.out.println(status1);
        Assert.assertEquals(200, status1);

        CustomResponse customResponse1 = mapper.readValue(response1.asString(), CustomResponse.class);

        int size = customResponse1.getResponses().size();

        boolean isPresent = false;

        for (int i = 0; i < size ; i++) {
            if (customResponse1.getResponses().get(i).getSeller_id() == Expected) {
                isPresent = true;
                break;
            }
        }
        Assert.assertTrue(isPresent);

        //fix the status code


    }

    @Test
    public void testGet(){
        apiRunner.runGET("/api/myaccount/sellers/2344");
        String email =  apiRunner.getCustomResponse().getEmail();
    }


}




