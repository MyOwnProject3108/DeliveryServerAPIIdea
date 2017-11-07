Feature: Export Controller CSV Report

  @validUserRetrieveCSV
  Scenario Outline: Validate the responsecode of GET Export Controller API for TTM
    Given I create a tab for the user "<user>" with the following payload
      | name                | public | default | tabType |
      | QA API CSV OrderTab | false  | false   | Order   |
      When I download CSV report for "<user>"
    Then I should see below response code "200" with "OK"

    Examples:
      | user              |
      | Traffic Manager   |
      | Broadcast Manager |


  @InvalidUserRetrieveCSV
  Scenario Outline: Validate the responsecode of GET API Tab for Invalid User/UnAuthorised User
    Given I create a tab for the user "<user>" with the following payload
      | name                | public | default | tabType |
      | QA API CSV OrderTab | false  | false   | Order   |
    When I download CSV report for "<unauthuser>"
    Then I should see below response code "<responsecode>" with "<statuscode>"

    Examples:
      | user                | unauthuser           |responsecode | statuscode                                                               |
      | Traffic Manager     | UnAuth User           |403          | The supplied authentication is not authorized to access this resource   |
      | Traffic Manager     | NullAuthUser          |400          | Request is missing required HTTP header 'X-User-Id'                     |
      |Broadcast Manager    | BTMUnAuth User        |403          | The supplied authentication is not authorized to access this resource   |
      |Broadcast Manager    | BTMNullAuthUser       |400          | Request is missing required HTTP header 'X-User-Id'                     |


  @validUserPostExportCSV
  Scenario Outline: Validate the responsecode of GET Export Controller API for TTM
    Given I create a tab for the user "<user>" with the following payload
      | name                | public | default | tabType |
      | QA API CSV OrderTab | false  | false   | Order   |

      When I post Export CVS call with the following payload as "<user>"
      |pageNum|pageSize|
      |  0    |     2  |
    Then I should see below response code "200" with "OK"

    Examples:
      | user              |
      | Traffic Manager   |
      | Broadcast Manager |



    @InvalidUserPostExportCSV
    Scenario Outline: Validate the responsecode of GET API Tab for Invalid User/UnAuthorised User
      Given I create a tab for the user "<user>" with the following payload
        | name                | public | default | tabType |
        | QA API CSV OrderTab | false  | false   | Order   |

      When I post Export CVS call with the following payload as "<unauthuser>"
        |pageNum|pageSize|
        |  0    |     2  |
      Then I should see below response code "<responsecode>" with "<statuscode>"

      Examples:
        | user                | unauthuser           |responsecode | statuscode                                                               |
        | Traffic Manager     | UnAuth User           |403          | The supplied authentication is not authorized to access this resource   |
        | Traffic Manager     | NullAuthUser          |400          | Request is missing required HTTP header 'X-User-Id'                     |
        |Broadcast Manager    | BTMUnAuth User        |403          | The supplied authentication is not authorized to access this resource   |
        |Broadcast Manager    | BTMNullAuthUser       |400          | Request is missing required HTTP header 'X-User-Id'                     |









