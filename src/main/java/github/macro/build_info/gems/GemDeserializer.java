package github.macro.build_info.gems;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.build_info.ClassTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
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
		if (slot.isEmpty()) {
			LOGGER.warn("Invalid Slot: {}", node.get("slot").asText());
			return null;
		}
		var tags = new TreeSet<GemTag>();
		node.get("tags").iterator().forEachRemaining(tag -> {
			var tagObj = GemTag.value(tag.asText());
			tagObj.ifPresentOrElse(tags::add, () -> LOGGER.warn("Invalid Tag: {}", tag.asText()));
		});
		var isVaal = node.has("isVaal") && node.get("isVaal").asBoolean(false);
		var isAwakened = node.has("isAwakened") && node.get("isAwakened").asBoolean(false);
		var acquisitionNode = node.get("acquisition");

		var recipes = new ArrayList<Recipe>();
		acquisitionNode.get("recipes").iterator().forEachRemaining(recipe -> {
			var recipeObj = new Recipe(recipe.get("amount").asInt(), recipe.get("ingredient").asText());
			recipes.add(recipeObj);
		});
		var quests = new ArrayList<Quest>();
		acquisitionNode.get("quests").iterator().forEachRemaining(quest -> {
			var classes = new TreeSet<ClassTag>();
			quest.get("classes").iterator().forEachRemaining(tag -> {
				var classObj = ClassTag.value(tag.asText());
				classObj.ifPresentOrElse(classes::add, () -> LOGGER.warn("Invalid Class: {}", tag.asText()));
			});
			var questObj = new Quest(quest.get("act").asInt(), quest.get("quest").asText(), classes);
			quests.add(questObj);
		});
		var vendors = new ArrayList<Vendor>();
		acquisitionNode.get("vendors").iterator().forEachRemaining(vendor -> {
			var classes = new TreeSet<ClassTag>();
			vendor.get("classes").iterator().forEachRemaining(tag -> {
				var classObj = ClassTag.value(tag.asText());
				classObj.ifPresentOrElse(classes::add, () -> LOGGER.warn("Invalid Class: {}", tag.asText()));
			});
			var vendorObj = new Vendor(vendor.get("vendor").asText(), vendor.get("act").asInt(), vendor.get("quest").asText(), classes);
			vendors.add(vendorObj);
		});

		return new Gem(name, slot.get(), tags, isVaal, isAwakened, new Acquisition(recipes, quests, vendors));
	}
}