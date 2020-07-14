package github.macro.build_info;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import github.macro.Util;
import github.macro.build_info.equipment.EquipmentInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

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

		var version = node.get("Version").asText();
		var name = node.get("Name").asText();
		var classTag = ClassTag.value(node.get("Class").asText());
		if (classTag == null) {
			LOGGER.warn("Invalid Class: {}", node.get("Class").asText());
			return null;
		}
		var ascendency = Ascendency.value(node.get("Ascendency").asText());
		if (ascendency == null) {
			LOGGER.warn("Invalid Ascendency: {}", node.get("Ascendency").asText());
			return null;
		}
		var buildGems = Util.YAML_MAPPER.treeToValue(node.get("Gems"), BuildGems.class);
		var equipment = new ArrayList<EquipmentInfo>();

		return new Build(version, name, classTag, ascendency, buildGems);
	}
}