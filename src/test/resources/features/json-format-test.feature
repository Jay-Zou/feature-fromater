Feature: Test json format

  Scenario: scenario1
    And test json:
    """
    {
      "name": "jay",
      "age": 123
    }
    """

  Scenario: scenario2
    And test json:
    """
    {
      "name": "jay",
      "age": 123
    }
    """

  Scenario: scenario3
    And test not json:
    """
      This is not json
    """

  Scenario Outline: scenario4
    And test json with params and quotes:
    """
    {
      "sites": [
        {
          "name": "<name1>",
          "url": "<url1>"
        },
        {
          "name": "<name2>",
          "url": "<url2>"
        }
      ]
    }
    """

    Examples:
      | name1   | url1           | name2  | url2           |
      | cainiao | www.github.com | google | www.google.com |


  Scenario Outline: scenario5
    And test json with params and without quotes:
    """
    {
      "sites": [
        {
          "name": <name1>,
          "url": <url1>
        },
        {
          "name": <name2>,
          "url": <url2>
        }
      ]
    }
    """

    Examples:
      | name1     | url1             | name2    | url2             |
      | "cainiao" | "www.github.com" | "google" | "www.google.com" |
