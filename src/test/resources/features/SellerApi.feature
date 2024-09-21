Feature: test seller api

  @getSellerVerifyEmailNotEmpty @regression
  Scenario: get a single seller and verify seller email is not empty
  Given user hits get single seller api with "/api/myaccount/sellers/5691"
    Then verify seller email is not empty

    @getAllSellers @regression
    Scenario: get all sellers and verify seller id is not 0
      Given user hit get all seller api with "/api/myaccount/sellers"
      Then verify seller ids are not equal to 0

      @updateSeller @regression
      Scenario: get single seller, update the same seller, verify seller was updated
        Given user hits get single seller api with "/api/myaccount/sellers/5700"
        Then verify seller email is not empty
        Then user hits put api with "/api/myaccount/sellers/"
        Then verify user email was updated
        And verify user fisrt name was updated

        @getSingleSeller @regression
        Scenario: get single seller, archive him, verify seller was archived
          Given user hits get single seller api with "/api/myaccount/sellers/"
          Then user archive seller with endpoint "/api/myaccount/sellers/archive/unarchive"
          Then verify seller was archived
          Then user hits get all sellers api with "/api/myaccount/sellers"

          @Delete @regression
          Scenario: create a seller, delete a seller, verify seller was deleted
            Given user hits api with "/api/myaccount/sellers"
            Then verify seller id was generated
            Then verify seller name is not empty
            And verify seller email is not empty
            Then delete the seller "/api/myaccount/sellers"
            Then user hits get all sellers api with "/api/myaccount/sellers"
            Then verify deleted seller is not in the list




