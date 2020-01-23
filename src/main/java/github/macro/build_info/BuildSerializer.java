package github.macro.build_info;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import github.macro.build_info.gems.Gem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2020-Jan-23.
 */
public class BuildSerializer extends StdSerializer<Build> {
	private static final Logger LOGGER = LogManager.getLogger(BuildSerializer.class);

	public BuildSerializer() {
		this(null);
	}

	public BuildSerializer(Class<Build> t) {
		super(t);
	}

	@Override
	public void serialize(Build value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject();
		gen.writeStringField("name", value.getName());
		gen.writeStringField("class", value.getClassTag().name());
		gen.writeStringField("ascendency", value.getAscendency().name());
		gen.writeObjectFieldStart("gems");
		gen.writeObjectField("links", value.getGemBuild().getLinks().stream().map(link -> link.stream().map(gem -> gem.map(Gem::getFullname).orElse(null)).collect(Collectors.toList())).collect(Collectors.toList()));
		gen.writeArrayFieldStart("updates");
		value.getGemBuild().getUpdates().forEach(update -> {
			try {
				gen.writeStartObject();
				gen.writeStringField("oldGem", update.getOldGem().map(Gem::getFullname).orElse(null));
				gen.writeStringField("newGem", update.getNewGem().map(Gem::getFullname).orElse(null));
				gen.writeStringField("reason", update.getReason());
				gen.writeEndObject();
			} catch (IOException ioe) {
				LOGGER.error("Unable to write Gem Update: {}", ioe, ioe);
			}
		});
		gen.writeEndArray();
		gen.writeEndObject();
		gen.writeObjectField("equipment", value.getEquipment());
		gen.writeEndObject();
	}
}