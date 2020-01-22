package github.macro.build_info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import github.macro.Util;
import github.macro.build_info.gems.GemBuild;
import github.macro.build_info.gems.GemInfo;
import github.macro.build_info.gems.UpdateGem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Dec-02
 */
public class BuildInfo {
	@JsonIgnore
	private static final Logger LOGGER = LogManager.getLogger(BuildInfo.class);
	private final String name;
	private final ClassTag classTag;
	private final Ascendency ascendency;
	private final GemBuild gemBuild;
	private final List<String> equipment;

	@JsonCreator
	public BuildInfo(@JsonProperty("name") String name, @JsonProperty("class") String classTag, @JsonProperty("ascendency") String ascendency, @JsonProperty("gems") GemBuild gemBuild, @JsonProperty("equipment") List<String> equipment) {
		this.name = name;
		this.classTag = ClassTag.value(classTag).orElseThrow(() -> new NullPointerException("Invalid Class Provided"));
		this.ascendency = Ascendency.value(ascendency).orElseThrow(() -> new NullPointerException("Invalid Ascendency Provided"));
		this.gemBuild = gemBuild;
		this.equipment = equipment;
	}

	public String getName() {
		return name;
	}

	public ClassTag getClassTag() {
		return classTag;
	}

	public Ascendency getAscendency() {
		return ascendency;
	}

	public GemBuild getGemBuild() {
		return gemBuild;
	}

	public List<String> getEquipment() {
		return equipment;
	}

	@Override
	public String toString() {
		return "BuildInfo{" +
				"name='" + name + '\'' +
				", classTag=" + classTag +
				", ascendency=" + ascendency +
				", gemBuild=" + gemBuild +
				", equipment=" + equipment +
				'}';
	}

	public String display() {
		return String.format("%s [%s/%s]", name, classTag, ascendency);
	}

	public void save() {
		try {
			File buildFile = new File("builds", name.replaceAll(" ", "_") + ".yaml");
			Util.YAML_MAPPER.writeValue(buildFile, this);
		} catch (IOException ioe) {
			LOGGER.error("Unable to save build: " + ioe);
		}
	}
}