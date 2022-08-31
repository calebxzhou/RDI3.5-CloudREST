package calebzhou.rdi.microservice.utils;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class IntegerDefaultAdapter implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (json.getAsString().equals("") || json.getAsString().equals("null")) {
            return 0;
        }

        try {
            return json.getAsInt();
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
