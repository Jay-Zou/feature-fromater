package cn.demojie.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonHelper {
  public static Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
  public static JsonParser jsonParser = new JsonParser();

  public static String format(String data) {
    if (!isJson(data)) {
      return "";
    }
    JsonElement jsonElement;
    try {
      jsonElement = jsonParser.parse(data);
    } catch (Exception e) {
      // If it isn't a json string, or it's invalid
      // TODO Can report the line number
      LogHelper.error("Format json occur exception: " + data);
      return "";
    }
    return gson.toJson(jsonElement);
  }

  public static boolean isJson(String data) {
    String trimData = data.trim();
    if (!trimData.startsWith("{") || !trimData.endsWith("}")) {
      LogHelper.warn("Skip no json data: " + data);
      return false;
    }
    return true;
  }
}
