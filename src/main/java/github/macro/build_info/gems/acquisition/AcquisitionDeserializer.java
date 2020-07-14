package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class AcquisitionDeserializer extends StdDeserializer<Acquisition> {
	private static final Logger LOGGER = LogManager.getLogger(AcquisitionDeserializer.class);

	public AcquisitionDeserializer() {
		this(null);
	}

	public AcquisitionDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Acquisition deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var rewards = new ArrayList<Reward>();
		node.get("rewards").iterator().forEachRemaining(temp -> {
			try {
				rewards.add(Util.JSON_MAPPER.treeToValue(temp, Reward.class));
			} catch (JsonProcessingException jpe) {
				LOGGER.error("Unable to Parse Rewards: {}", jpe, jpe);
			}
		});
		var crafting = new ArrayList<List<Ingredient>>();
		node.get("crafting").iterator().forEachRemaining(tempList -> {
			var ingredients = new ArrayList<Ingredient>();
			tempList.iterator().forEachRemaining(temp -> {
				try {
					ingredients.add(Util.JSON_MAPPER.treeToValue(temp, Ingredient.class));
				} catch (JsonProcessingException jpe) {
					LOGGER.error("Unable to Parse Rewards: {}", jpe, jpe);
				}
			});
			crafting.add(ingredients);
		});

		return new Acquisition(rewards, crafting);
	}
}