Feature: Market API endpoint

  @ValidUserMarket
  Scenario: Verify Markets endpoint get response for BTM hub
    Given I logged in as "Broadcast Manager"
    When I retrieve Markets for the "Broadcast Manager"
    Then I should see the following details in ExportController CSV response payload:
    |sid|name|
    |352|United Arab Emirates|


  @UnAuthUserMarket
  Scenario: Verify Markets endpoint get response for null auth BTM hub
    Given I logged in as "BTMNullAuthUser"
    When I retrieve Markets for the "BTMNullAuthUser"
    Then I should see below response code "400" with "Request is missing required HTTP header 'X-User-Id'"






