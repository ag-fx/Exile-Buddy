package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class IngredientDeserializer extends StdDeserializer<Ingredient> {
	private static final Logger LOGGER = LogManager.getLogger(IngredientDeserializer.class);

	public IngredientDeserializer() {
		this(null);
	}

	public IngredientDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Ingredient deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var name = node.get("name").asText();
		var count = node.get("count").asInt();
		var level = node.has("level") ? node.get("level").asInt() : null;
		var quality = node.has("quality") ? node.get("quality").asDouble() : null;
		var ingredientType = IngredientType.value(node.get("type").asText());
		if (ingredientType == null) {
			LOGGER.warn("Invalid Ingredient Type: {}", node.get("type").asText());
			return null;
		}

		return new Ingredient(name, count, level, quality, ingredientType);
	}
}