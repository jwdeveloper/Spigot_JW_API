package jw.api.utilites.json;


import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Type;
import java.util.Map;

public class ItemStackAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

    public static class GSON_Skip_Strategy implements ExclusionStrategy {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            // return f.getAnnotation(GSON_Skip.class) != null;
            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {

            return false;
        }
    }

    private Gson gson;
    public ItemStackAdapter() {
        gson = new GsonBuilder().create();
    }

    @Override
    public ItemStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        try
        {
            Map<String, Object> map = gson.fromJson(jsonElement.getAsString(), new TypeToken<Map<String, Object>>(){}.getType());
            return ItemStack.deserialize(map);
        }
        catch (JsonParseException e)
        {
            //log
        }
        return null;
    }

    @Override
    public JsonElement serialize(ItemStack itemStack, Type type, JsonSerializationContext context)
    {

        return new JsonPrimitive(gson.toJson(itemStack.serialize()));
    }
}