package github.macro.build_info;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class BuildGemsSerializer extends StdSerializer<BuildGems> {
	private static final Logger LOGGER = LogManager.getLogger(BuildGemsSerializer.class);

	public BuildGemsSerializer() {
		this(null);
	}

	public BuildGemsSerializer(Class<BuildGems> t) {
		super(t);
	}

	@Override
	public void serialize(BuildGems value, JsonGenerator parser, SerializerProvider provider) throws IOException {
		parser.writeStartObject();
		parser.writeObjectField("Armour", value.getArmourLinks().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeObjectField("Helmet", value.getHelmetLinks().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeObjectField("Gloves", value.getGloveLinks().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeObjectField("Boots", value.getBootLinks().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeObjectField("Weapon 1", value.getWeapon1Links().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeObjectField("Weapon 2", value.getWeapon2Links().stream().map(gem -> gem != null ? gem.getFullname() : null));
		parser.writeArrayFieldStart("Updates");
		value.getUpdates().forEach(update -> {
			try {
				parser.writeStartObject();
				parser.writeStringField("Old Gem", update.getOldGem() != null ? update.getOldGem().getFullname() : null);
				parser.writeStringField("New Gem", update.getNewGem() != null ? update.getNewGem().getFullname() : null);
				parser.writeStringField("Reason", update.getReason());
				parser.writeEndObject();
			} catch (IOException ioe) {
				LOGGER.error("Unable to write Build Gems: {}", ioe, ioe);
			}
		});
		parser.writeEndArray();
		parser.writeEndObject();
	}
}