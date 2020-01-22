package github.macro;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import github.macro.build_info.gems.GemInfo;
import github.macro.build_info.gems.Slot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	public static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
	public static final List<GemInfo> gems;
	private static final Logger LOGGER = LogManager.getLogger(Util.class);

	static {
		JSON_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		JSON_MAPPER.findAndRegisterModules();
		JSON_MAPPER.registerModule(new Jdk8Module());
		YAML_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		YAML_MAPPER.findAndRegisterModules();
		YAML_MAPPER.registerModule(new Jdk8Module());

		List<GemInfo> temp;
		try {
			temp = JSON_MAPPER.readValue(new File("gems", "Gems.json"), new TypeReference<>() {
			});
		} catch (IOException ioe) {
			LOGGER.error("Unable to Load Gems: {}", ioe, ioe);
			temp = new ArrayList<>();
		}
		gems = temp;
	}

	public static String slotToColour(Optional<Slot> slot) {
		if (slot.isEmpty())
			return "#999999";
		switch (slot.get()) {
			case RED:
				return "#DD9999";
			case GREEN:
				return "#99DD99";
			case BLUE:
				return "#9999DD";
			case WHITE:
				return "#DDDDDD";
			default:
				return "#999999";
		}
	}

	public static Optional<GemInfo> gemByName(String name) {
		return gems.stream().filter(gem -> gem.getFullname().equalsIgnoreCase(name)).findFirst();
	}
}