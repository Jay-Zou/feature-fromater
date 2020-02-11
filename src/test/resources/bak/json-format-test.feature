Feature: Test json format

  Scenario: Json before bank line
    And test:
    """

      {"name":"jay", "age": 123}
    """

  Scenario: Normal json
    And test:
    """
      {"name":"jay", "age": 123}
    """

  Scenario: Bad json
    And test:
    """
      {"name":"jay" "age": 123}
    """

  Scenario: Not json
    And test:
    """
      This is not json
    """

  Scenario Outline: Json with params which not include quotes
    And test:
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


  Scenario Outline: Json with params which include quotes
    And test:
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
