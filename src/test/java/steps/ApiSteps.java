package steps;

import com.github.javafaker.Faker;
import entities.RequestBody;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.apiguardian.api.API;
import org.junit.Assert;
import utilities.apiRunner;

import java.util.HashMap;
import java.util.Map;

public class ApiSteps {
    Faker faker = new Faker();
    String email;
    String sellerName;
    int sellerId;

    @Given("user hits get single seller api with {string}")
    public void user_hits_get_single_seller_api_with(String endpoint) {
        apiRunner.runGET(endpoint);


    }
    @Then("verify seller email is not empty")
    public void verify_seller_email_is_not_empty() {
        String email = apiRunner.getCustomResponse().getEmail();
        Assert.assertFalse(email.isEmpty());


    }


    @Given("user hit get all seller api with {string}")
    public void user_hit_get_all_seller_api_with(String endpoint) {

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 110);
        params.put("isArchived", false);

       apiRunner.runGet(endpoint, params);



    }
    @Then("verify seller ids are not equal to {int}")
    public void verify_seller_ids_are_not_equal_to(Integer int1) {
        int size = apiRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i < size; i++) {
            int sellerId = apiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            Assert.assertNotEquals( 0, sellerId);
        }

    }




    @Then("user hits put api with {string}")
    public void user_hits_put_api_with(String endpoint) {
        RequestBody requestBody = new RequestBody();
        requestBody.setCompany_name(faker.company().name());
        requestBody.setSeller_name(faker.name().firstName());
        requestBody.setEmail(faker.internet().emailAddress());
        requestBody.setPhone_number(faker.phoneNumber().cellPhone());
        requestBody.setAddress(faker.address().fullAddress());

        apiRunner.runPut(endpoint, requestBody, 5685);

        sellerId = apiRunner.getCustomResponse().getSeller_id();

        email = apiRunner.getCustomResponse().getEmail();
        sellerName  = apiRunner.getCustomResponse().getSeller_name();


    }

    @Then("verify user email was updated")
    public void verify_user_email_was_updated() {
        Assert.assertFalse(email.isEmpty());

    }

    @Then("verify user fisrt name was updated")
    public void verify_user_fisrt_name_was_updated() {
        Assert.assertFalse(sellerName.isEmpty());

    }


    @Then("user archive seller with endpoint {string}")
    public void user_archive_seller_with_endpoint(String endpoint) {

        Map<String, Object > params = new HashMap<>();
        params.put("sellersIdsForArchive", 5700);
        params.put("archive", true);
        apiRunner.runPOST(endpoint, params);

    }
    @Then("user hits get all sellers api with {string}")
    public void user_hits_get_all_sellers_api_with(String endpoint) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        params.put("size", 110);
        params.put("isArchived", true);
        apiRunner.runGet(endpoint,params);
        boolean isPresent = false;

        int size = apiRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i <size ; i++) {
            int ids = apiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (sellerId == ids) {
                isPresent = true;
                break;
            }
        }

        Assert.assertTrue(isPresent);
    }

    @Then("verify seller was archived")
    public void verify_seller_was_archived() {

    }




    @Given("user hits api with {string}")
    public void user_hits_api_with(String endpoint) {
       RequestBody requestBody = new RequestBody();
       requestBody.setCompany_name("Whatever");
       requestBody.setSeller_name("whateveer");
       requestBody.setEmail(faker.internet().emailAddress());
       requestBody.setPhone_number("3121232233");
       requestBody.setAddress(faker.address().fullAddress());
       apiRunner.runPOST(endpoint, requestBody);
       sellerId = apiRunner.getCustomResponse().getSeller_id();
       sellerName = apiRunner.getCustomResponse().getSeller_name();

    }

    @Then("verify seller id was generated")
    public void verify_seller_id_was_generated() {
        Assert.assertTrue(sellerId != 0);


    }

    @Then("verify seller name is not empty")
    public void verify_seller_name_is_not_empty() {
        Assert.assertFalse(sellerName.isEmpty());


    }

    @Then("delete the seller {string}")
    public void delete_the_seller(String endpoint) {
        apiRunner.runDelete(endpoint + sellerId);

    }

    @Then("verify deleted seller is not in the list")
    public void verify_deleted_seller_is_not_in_the_list() {

        boolean isEmpty = true;
        int size = apiRunner.getCustomResponse().getResponses().size();
        for (int i = 0; i <size ; i++) {
            int id = apiRunner.getCustomResponse().getResponses().get(i).getSeller_id();
            if (id != sellerId){
                isEmpty = false;
                break;
            }
        }
        Assert.assertFalse(isEmpty);

    }





}
