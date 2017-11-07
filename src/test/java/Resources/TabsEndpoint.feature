Feature: Validate Traffic Tab API

  @validUserGetTab
  Scenario Outline: Validate the responsecode of GET API Tab for TTM/BTM
    Given I logged in as "<user>"
    When I retrieve tabs for the user "<user>"
    Then I should see valid response code "200" with "OK"

    Examples:
      | user              |
      | Traffic Manager   |
      | Broadcast Manager |

  @InvalidUserGetTab
  Scenario Outline: Validate the responsecode of GET API Tab for Invalid User/UnAuthorised User
    Given I logged in as "<user>"
    When I retrieve tabs for the user "<user>"
    Then I should see below response code "<responsecode>" with "<statuscode>"

    Examples:
      | user            | responsecode | statuscode                                                            |
      | UnAuth User     |          403 | The supplied authentication is not authorized to access this resource |
      | NullAuthUser    |          400 | Request is missing required HTTP header 'X-User-Id'                   |
      | BTMUnAuth User  |          403 | The supplied authentication is not authorized to access this resource |
      | BTMNullAuthUser |          400 | Request is missing required HTTP header 'X-User-Id'                   |

  @ValidUserCreateTab
  Scenario Outline: Validate the responsecode for Create tab with POST method for valid user
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType |
      | QA API OrderTab | false  | false   | Order   |
    Then I should see valid response code "200" with "OK"

    Examples:
      | user              |
      | Traffic Manager   |
      | Broadcast Manager |

  @InvalidUserCreateTab
  Scenario Outline: Validate the responsecode for Create tab with POST method for invalid user/UnAuthorised User
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType |
      | QA API OrderTab | false  | false   | Order   |
    Then I should see below response code "<responsecode>" with "<statuscode>"

    Examples:
      | user            | responsecode | statuscode                                                            |
      | UnAuth User     |          403 | The supplied authentication is not authorized to access this resource |
      | NullAuthUser    |          400 | Request is missing required HTTP header 'X-User-Id'                   |
      | BTMUnAuth User  |          403 | The supplied authentication is not authorized to access this resource |
      | BTMNullAuthUser |          400 | Request is missing required HTTP header 'X-User-Id'                   |

  @ValidUserDeleteTabGenericTabId
  Scenario Outline: Validate the response code of DELETE API Tab for TTM/BTM hub
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType       |
      | QA API Send Tab | false  | false   | OrderItemSend |
    And I delete tab for the user "<user>"
    Then I should see response code "200" with status "OK"

    Examples:
      | user              |
      | Traffic Manager   |
      | Broadcast Manager |

  @InValidUserDeleteTabGenericTabId
  Scenario Outline: Validate the response code of DELETE API Tab for Invalid User/UnAuthorised User
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType       |
      | QA API Send Tab | false  | false   | OrderItemSend |
    And I delete tab for the user "<unauthuser>"
    Then I should see below response code "<responsecode>" with "<statuscode>"

    Examples:
      | user              | unauthuser      | responsecode | statuscode                                                            |
      | Traffic Manager   | UnAuth User     |          403 | The supplied authentication is not authorized to access this resource |
      | Traffic Manager   | NullAuthUser    |          400 | Request is missing required HTTP header 'X-User-Id'                   |
      | Broadcast Manager | BTMUnAuth User  |          403 | The supplied authentication is not authorized to access this resource |
      | Broadcast Manager | BTMNullAuthUser |          400 | Request is missing required HTTP header 'X-User-Id'                   |

  @ValidUserUpdateTab
  Scenario Outline: Validate the responsecode for Update tab with PUT method for valid user TTM/BTM hub
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType       |
      | QA API Send Tab | false  | false   | OrderItemSend |
    And I update tab for the user "<user>" with the following payload
      | name                   | public | default | tabType        |
      | QA API Send Tab Update | false  | false   | OrderItemClock |
    Then I should see response code "200" with status "true"

    Examples:
      | user              |
      | Broadcast Manager |
      | Traffic Manager   |

  @InValidUserUpdateTab
  Scenario: Validate the responsecode for Update tab with PUT method for invalid/unauth user
    Given I logged in as "Traffic Manager"
    When I create a tab for the user "Traffic Manager" with the following payload
      | name            | public | default | tabType       |
      | QA API Send Tab | false  | false   | OrderItemSend |
    And I update tab for the user "UnAuth User" with the following payload
      | name                   | public | default | tabType        |
      | QA API Send Tab Update | false  | false   | OrderItemClock |
    Then I should see response code "403" with status "The supplied authentication is not authorized to access this resource"


  @InValidUserUpdateTabOrder
  Scenario Outline: Validate the responsecode for Update tab order with PUT method for Invalid user
    Given I logged in as "<user>"
    When I create a tab for the user "<user>" with the following payload
      | name            | public | default | tabType       |
      | QA API Send Tab | false  | false   | OrderItemSend |
    And I update order of tab for the user "unauthuser"
    Then I should see response code "400" with status "Request is missing required HTTP header 'X-User-Id'"

    Examples:
      | user              | unauthuser      |
      | Traffic Manager   | NullAuthUser    |
      | Broadcast Manager | BTMNullAuthUser |




