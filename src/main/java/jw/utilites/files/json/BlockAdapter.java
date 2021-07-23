package jw.utilites.files.json;

import com.google.gson.*;
import org.bukkit.block.Block;

import java.lang.reflect.Type;


public class BlockAdapter implements JsonSerializer<Block>, JsonDeserializer<Block> {


    @Override
    public Block deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Block block, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}