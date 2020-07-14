package github.macro.build_info;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import github.macro.build_info.equipment.EquipmentInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

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
	public void serialize(Build value, JsonGenerator parser, SerializerProvider provider) throws IOException {
		parser.writeStartObject();
		parser.writeStringField("Version", value.getVersion());
		parser.writeStringField("Name", value.getName());
		parser.writeStringField("Class", value.getClassTag().name());
		parser.writeStringField("Ascendency", value.getAscendency().name());
		parser.writeObjectField("Gems", value.getBuildGems());
		parser.writeObjectField("Equipment", value.getEquipment().stream().map(EquipmentInfo::getName));
		parser.writeEndObject();
	}
}