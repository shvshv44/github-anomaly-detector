package shaked.legit.exercise.util.json;

import com.google.gson.Gson;
import shaked.legit.exercise.exceptions.JsonParsingException;

import java.lang.reflect.Type;

public class GsonJsonParser implements JsonParser {

    private final Gson gson;

    public GsonJsonParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public <T> String convertToJson(T someObject) {
        try {
            return gson.toJson(someObject);
        } catch (Exception ex) {
            throw new JsonParsingException("Error while converting to json: " + ex.getMessage(), ex);
        }
    }

    @Override
    public <T> T convertFromJson(String someJson, Class<T> clazz) {
        try {
            return gson.fromJson(someJson, clazz);
        } catch (Exception ex) {
            throw new JsonParsingException("Error while converting from json: " + ex.getMessage(), ex);
        }
    }

    @Override
    public <T> T convertFromJsonGeneric(String someJsonArray, Class<T> clazz, Type type) {
        try {
            return (T) gson.fromJson(someJsonArray, type);
        } catch (Exception ex) {
            throw new JsonParsingException("Error while converting from json array: " + ex.getMessage(), ex);
        }
    }
}
