Feature: Multiple site support
  Only blog owners can post to a blog, except administrators,
  who can post to all blogs.

  Background:
    Given a global administrator named "Greg"
    And test json:
    """

      {"name":"jay", "age": 123}
    """

  Scenario: Dr. Bill posts to his own blog
    And test json:
    """
      {"name":"jay", "age": 123}
    """

  Scenario: Dr. Bill tries to post to somebody else's blog, and fails
    And test json:
    """
      This is not json
    """


  Scenario Outline: eating
    And test json:
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
      | cainiao | www.runoob.com | google | www.google.com |


  Scenario Outline: eating2
    And test json:
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
      | "cainiao" | "www.runoob.com" | "google" | "www.google.com" |
