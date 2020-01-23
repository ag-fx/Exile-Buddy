package github.macro;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import github.macro.build_info.ClassTag;
import github.macro.build_info.gems.Gem;
import github.macro.build_info.gems.Slot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	public static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
	public static final List<Gem> gems;
	private static final Logger LOGGER = LogManager.getLogger(Util.class);

	static {
		JSON_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		JSON_MAPPER.findAndRegisterModules();
		JSON_MAPPER.registerModule(new Jdk8Module());
		YAML_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		YAML_MAPPER.findAndRegisterModules();
		YAML_MAPPER.registerModule(new Jdk8Module());

		List<Gem> temp;
		try {
			temp = JSON_MAPPER.readValue(new File("gems", "Gems.json"), new TypeReference<>() {
			});
		} catch (IOException ioe) {
			LOGGER.error("Unable to Load Gems: {}", ioe, ioe);
			temp = new ArrayList<>();
		}
		gems = temp.stream().filter(Objects::nonNull).collect(Collectors.toList());
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

	public static Optional<Gem> gemByName(String name) {
		return gems.stream().filter(gem -> gem.getFullname().equalsIgnoreCase(name)).findFirst();
	}

	public static List<Optional<Gem>> getClassGems(ClassTag classTag) {
		List<String> gemNames;
		switch (classTag) {
			case SCION:
				gemNames = Arrays.asList("Spectral Throw", "Onslaught Support", "Empower Support");
				break;
			case MARAUDER:
				gemNames = Arrays.asList("Heavy Strike", "Ruthless Support", "Empower Support");
				break;
			case RANGER:
				gemNames = Arrays.asList("Burning Arrow", "Pierce Support", "Empower Support");
				break;
			case WITCH:
				gemNames = Arrays.asList("Fireball", "Arcane Surge Support", "Empower Support");
				break;
			case DUELIST:
				gemNames = Arrays.asList("Double Strike", "Chance to Bleed Support", "Empower Support");
				break;
			case TEMPLAR:
				gemNames = Arrays.asList("Glacial Hammer", "Elemental Proliferation Support", "Empower Support");
				break;
			case SHADOW:
				gemNames = Arrays.asList("Viper Strike", "Lesser Poison Support", "Empower Support");
				break;
			default:
				gemNames = Collections.singletonList("Empower Support");
				break;
		}
		return gemNames.stream().map(Util::gemByName).collect(Collectors.toList());
	}
}