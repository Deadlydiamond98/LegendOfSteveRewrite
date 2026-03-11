package net.deadlydiamond.legend_of_steve.util.datagen.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Arrays;

public class ZeldaItemModelDatagenUtil {

    public static void registerQuiver(ItemModelGenerator generator, Item quiver) {
        registerVariableFilledItem(generator, "quiver", quiver, "", "_one_arrow", "_two_arrows", "_filled");
    }

    public static void registerBombBag(ItemModelGenerator generator, Item quiver) {
        registerVariableFilledItem(generator, "bomb_bag", quiver, "", "_filled");
    }

    private static void registerVariableFilledItem(ItemModelGenerator generator, String path, Item bag, String... stages) {
        String[] suffixes = Arrays.stream(stages).toArray(String[]::new);
        Identifier itemModelId = ModelIds.getItemModelId(bag);
        Identifier texturePath = getPrefixedId(bag, path);
        Model model = Models.GENERATED;

        model.upload(itemModelId, TextureMap.layer0(texturePath), generator.writer, (id, textures) -> {
            JsonObject jsonObject = model.createJson(id, textures);
            JsonArray jsonArray = new JsonArray();

            for (int i = 0; i < suffixes.length; i++) {
                JsonObject jsonObject2 = new JsonObject();
                JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty("filled", i / (float) suffixes.length);
                jsonObject2.add("predicate", jsonObject3);
                jsonObject2.addProperty("model", id.withSuffixedPath(suffixes[i]).toString());
                jsonArray.add(jsonObject2);
            }

            jsonObject.add("overrides", jsonArray);
            return jsonObject;
        });

        for (int i = 1; i < suffixes.length; i++) {
            String suffix = suffixes[i];
            model.upload(itemModelId.withSuffixedPath(suffix), TextureMap.layer0(texturePath.withSuffixedPath(suffix)), generator.writer);
        }
    }

    public static Identifier getPrefixedId(Item item, String prefix) {
        Identifier identifier = Registries.ITEM.getId(item);
        return identifier.withPrefixedPath("item/" + prefix + "/");
    }
}
