Feature: Multiple site support
  Only blog owners can post to a blog, except administrators,
  who can post to all blogs.

  Background:
    Given a global administrator named "Greg"
    And a blog named "Greg's anti-tax rants"
    And a customer named "Dr. Bill"
    And a blog named "Expensive Therapy" owned by "Dr. Bill"
    And test json:
    """

      {"name":"jay", "age": 123}
    """

  Scenario: Dr. Bill posts to his own blog
    Given I am logged in as Dr. Bill
    When I try to post to "Expensive Therapy"
    Then I should see "Your article was published."
    And test json:
    """
      {"name":"jay", "age": 123}
    """

  Scenario: Dr. Bill tries to post to somebody else's blog, and fails
    Given I am logged in as Dr. Bill
    When I try to post to "Greg's anti-tax rants"
    Then I should see "Hey! That's not your blog!"
    And test json:
    """
      This is not json
    """


  Scenario Outline: eating
    Given there are <start> cucumbers
    When I eat <eat> cucumbers
    Then I should have <left> cucumbers
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
