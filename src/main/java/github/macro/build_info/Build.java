package github.macro.build_info;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import github.macro.Util;
import github.macro.build_info.gems.GemBuild;
import github.macro.build_info.gems.UpdateGem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02
 */
@JsonDeserialize(using = BuildDeserializer.class)
@JsonSerialize(using = BuildSerializer.class)
public class Build {
	private static final Logger LOGGER = LogManager.getLogger(Build.class);
	private final String name;
	private final ClassTag classTag;
	private final Ascendency ascendency;
	private final GemBuild gemBuild;
	private final List<String> equipment;

	public Build(String name, ClassTag classTag, Ascendency ascendency, GemBuild gemBuild, List<String> equipment) {
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.gemBuild = gemBuild;
		this.equipment = equipment;
	}

	public Build(String name, ClassTag classTag, Ascendency ascendency) {
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.gemBuild = new GemBuild(Arrays.asList(Util.getClassGems(classTag), Collections.singletonList(Util.gemByName("Portal"))), Collections.singletonList(new UpdateGem(Util.gemByName("Empower Support"), Util.gemByName("Enhance Support"), "Gems are all Max Level")));
		this.equipment = new ArrayList<>();
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
		return String.format("{%s} %s [%s/%s]", name, classTag, ascendency);
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