Feature: Users API endpoint

  @ValidUserRetrieveDetails
  Scenario Outline: Verify Users endpoint get response for BTM hub/TTM
    Given I logged in as "<user>"
    When I retrieve User details for the "<user>"
    Then I should see below response code "200" with "OK"

    Examples:
    |user|
    |Broadcast Manager|
    |Traffic Manager  |

  @InvalidUserRetrieveDetails
  Scenario Outline: Verify Users endpoint get response for Invalid BTM/TTM
    Given I logged in as "<unauthuser>"
    When I retrieve User details for the "<unauthuser>"
    Examples:
        | unauthuser           |responsecode | statuscode                                                               |
        | UnAuth User           |403          | The supplied authentication is not authorized to access this resource   |





