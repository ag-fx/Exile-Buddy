package github.macro.build_info;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.Util;
import github.macro.build_info.gems.Gem;
import github.macro.build_info.gems.UpdateGem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class BuildGemsDeserializer extends StdDeserializer<BuildGems> {
	private static final Logger LOGGER = LogManager.getLogger(BuildGemsDeserializer.class);

	public BuildGemsDeserializer() {
		this(null);
	}

	public BuildGemsDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public BuildGems deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
		JsonNode node = parser.readValueAsTree();

		var armourLinks = new ArrayList<@Nullable Gem>();
		node.get("Armour").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			armourLinks.add(temp);
		});
		var helmetLinks = new ArrayList<@Nullable Gem>();
		node.get("Helmet").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			helmetLinks.add(temp);
		});
		var glovesLinks = new ArrayList<@Nullable Gem>();
		node.get("Gloves").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			glovesLinks.add(temp);
		});
		var bootsLinks = new ArrayList<@Nullable Gem>();
		node.get("Boots").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			bootsLinks.add(temp);
		});
		var weapon1Links = new ArrayList<@Nullable Gem>();
		node.get("Weapon 1").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			weapon1Links.add(temp);
		});
		var weapon2Links = new ArrayList<@Nullable Gem>();
		node.get("Weapon 2").iterator().forEachRemaining(link -> {
			var temp = Util.gemByName(link.asText());
			weapon2Links.add(temp);
		});
		var updates = new ArrayList<@NotNull UpdateGem>();
		node.get("Updates").iterator().forEachRemaining(subnode -> {
			var oldGem = Util.gemByName(subnode.get("Old Gem").asText());
			var newGem = Util.gemByName(subnode.get("New Gem").asText());
			var reason = subnode.get("Reason").asText();
			updates.add(new UpdateGem(oldGem, newGem, reason));
		});

		return new BuildGems(armourLinks, helmetLinks, glovesLinks, bootsLinks, weapon1Links, weapon2Links, updates);
	}
}