package shaked.legit.exercise.util.json;

import java.lang.reflect.Type;

public interface JsonParser {

    <T> String convertToJson(T someObject);
    <T> T convertFromJson(String someJson, Class<T> clazz);
    <T> T convertFromJsonGeneric(String someJsonArray, Class<T> clazz, Type type);

}
