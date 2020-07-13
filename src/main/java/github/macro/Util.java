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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public abstract class Util {
	@NotNull
	public static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	@NotNull
	public static final ObjectMapper YAML_MAPPER = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
	@NotNull
	public static final List<Gem> gems;
	@NotNull
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
			temp = JSON_MAPPER.readValue(new File("resources/Gems", "Gems.json"), new TypeReference<>() {
			});
		} catch (IOException ioe) {
			LOGGER.error("Unable to Load Gems: {}", ioe, ioe);
			temp = new ArrayList<>();
		}
		gems = temp.stream().filter(Objects::nonNull).collect(Collectors.toList());
	}

	@NotNull
	public static String slotToColour(@Nullable Slot slot) {
		if (slot == null)
			return "#999999";
		switch (slot) {
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

	@Nullable
	public static Gem gemByName(@NotNull String name) {
		return gems.stream().filter(gem -> {
			if (gem.isVaal())
				return name.equalsIgnoreCase("Vaal " + gem.getName());
			if (gem.isAwakened())
				return name.equalsIgnoreCase("Awakened " + gem.getName());
			return name.equalsIgnoreCase(gem.getName());
		}).findFirst().orElse(null);
	}

	@NotNull
	public static List<Gem> getClassGems(@NotNull ClassTag classTag) {
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
		return gemNames.stream().map(Util::gemByName).filter(Objects::nonNull).collect(Collectors.toList());
	}
}