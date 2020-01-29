package cn.demojie.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonHelper {
  public static Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
  public static JsonParser jsonParser = new JsonParser();

  public static String format(String data) {
    JsonElement jsonElement;
    try {
      jsonElement = jsonParser.parse(data);
    } catch (Exception e) {
      // If it isn't a json string
      //      e.printStackTrace();
      return "";
    }
    return gson.toJson(jsonElement);
  }
}
