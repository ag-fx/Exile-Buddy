package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.build_info.ClassTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class RewardDeserializer extends StdDeserializer<Reward> {
	private static final Logger LOGGER = LogManager.getLogger(RewardDeserializer.class);

	public RewardDeserializer() {
		this(null);
	}

	public RewardDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Reward deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var act = node.get("act").asInt();
		var classes = new ArrayList<ClassTag>();
		node.get("classes").iterator().forEachRemaining(tag -> {
			var temp = ClassTag.value(tag.asText());
			if (temp == null) {
				LOGGER.warn("Invalid Class: {}", tag.asText());
				return;
			}
			classes.add(temp);
		});
		var quest = node.get("quest").asText();
		var rewardType = RewardType.value(node.get("type").asText());
		if (rewardType == null) {
			LOGGER.warn("Invalid Reward Type: {}", node.get("type").asText());
			return null;
		}
		var vendor = node.has("vendor") ? node.get("vendor").asText() : null;

		return new Reward(act, classes, quest, rewardType, vendor);
	}
}