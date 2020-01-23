package github.macro.build_info;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.Util;
import github.macro.build_info.gems.Gem;
import github.macro.build_info.gems.GemBuild;
import github.macro.build_info.gems.UpdateGem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-23.
 */
public class BuildDeserializer extends StdDeserializer<Build> {
	private static final Logger LOGGER = LogManager.getLogger(BuildDeserializer.class);

	public BuildDeserializer() {
		this(null);
	}

	public BuildDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Build deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var name = node.get("name").asText();
		var classTag = ClassTag.value(node.get("class").asText());
		if (classTag.isEmpty()) {
			LOGGER.warn("Invalid Class: {}", node.get("class").asText());
			return null;
		}
		var ascendency = Ascendency.value(node.get("ascendency").asText());
		if (ascendency.isEmpty()) {
			LOGGER.warn("Invalid Ascendency: {}", node.get("ascendency").asText());
			return null;
		}
		var gemNode = node.get("gems");

		var links = new ArrayList<List<Optional<Gem>>>();
		gemNode.get("links").iterator().forEachRemaining(link -> {
			var linkList = new ArrayList<Optional<Gem>>();
			link.iterator().forEachRemaining(gem -> {
				var gemObj = Util.gemByName(gem.asText());
				linkList.add(gemObj);
			});
			links.add(linkList);
		});
		var updates = new ArrayList<UpdateGem>();
		gemNode.get("updates").iterator().forEachRemaining(update -> {
			var oldGem = Util.gemByName(update.get("oldGem").asText());
			var newGem = Util.gemByName(update.get("newGem").asText());
			updates.add(new UpdateGem(oldGem, newGem, update.get("reason").asText()));
		});
		var gemBuild = new GemBuild(links, updates);

		return new Build(name, classTag.get(), ascendency.get(), gemBuild, new ArrayList<>());
	}
}