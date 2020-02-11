### A formatter for the cucumber's feature file's json-like block

Just format the json-like contents between the `triple quotes`->`"""`

```gherkin
  Scenario Outline: some test scenario

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
      | cainiao | www.github.com | google | www.google.com |
```

### Usage:
```shell script
java -jar feature-fromater-xxx-all-deps.jar [file or directory]
```
