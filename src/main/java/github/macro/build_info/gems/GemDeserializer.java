package github.macro.build_info.gems;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.Util;
import github.macro.build_info.gems.acquisition.Acquisition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.TreeSet;

/**
 * Created by Macro303 on 2020-Jan-23.
 */
public class GemDeserializer extends StdDeserializer<Gem> {
	private static final Logger LOGGER = LogManager.getLogger(GemDeserializer.class);

	public GemDeserializer() {
		this(null);
	}

	public GemDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Gem deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var name = node.get("name").asText();
		var slot = Slot.value(node.get("slot").asText());
		if (slot == null) {
			LOGGER.warn("Invalid Slot: {}", node.get("slot").asText());
			return null;
		}
		var tags = new TreeSet<GemTag>();
		node.get("tags").iterator().forEachRemaining(tag -> {
			var temp = GemTag.value(tag.asText());
			if (temp == null) {
				LOGGER.warn("Invalid Tag: {}", tag.asText());
				return;
			}
			tags.add(temp);
		});
		var isVaal = node.has("isVaal") && node.get("isVaal").asBoolean(false);
		var isAwakened = node.has("isAwakened") && node.get("isAwakened").asBoolean(false);
		var acquisition = Util.JSON_MAPPER.treeToValue(node.get("acquisition"), Acquisition.class);

		return new Gem(name, slot, tags, isVaal, isAwakened, acquisition);
	}
}