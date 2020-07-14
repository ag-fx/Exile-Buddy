package github.macro.build_info;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import github.macro.Util;
import github.macro.build_info.equipment.EquipmentInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02
 */
@JsonDeserialize(using = BuildDeserializer.class)
@JsonSerialize(using = BuildSerializer.class)
public class Build {
	@NotNull
	private static final Logger LOGGER = LogManager.getLogger(Build.class);
	@NotNull
	private final String version;
	@NotNull
	private final String name;
	@NotNull
	private final ClassTag classTag;
	@NotNull
	private final Ascendency ascendency;
	@NotNull
	private final BuildGems buildGems;
	@NotNull
	private final List<EquipmentInfo> equipment;

	public Build(@NotNull String version, @NotNull String name, @NotNull ClassTag classTag, @NotNull Ascendency ascendency) {
		this.version = version;
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.buildGems = new BuildGems(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		this.equipment = new ArrayList<>();
	}

	public Build(@NotNull String version, @NotNull String name, @NotNull ClassTag classTag, @NotNull Ascendency ascendency, @NotNull BuildGems buildGems) {
		this.version = version;
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.buildGems = buildGems;
		this.equipment = new ArrayList<>();
	}

	@NotNull
	public String getVersion() {
		return version;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public ClassTag getClassTag() {
		return classTag;
	}

	@NotNull
	public Ascendency getAscendency() {
		return ascendency;
	}

	@NotNull
	public BuildGems getBuildGems() {
		return buildGems;
	}

	@NotNull
	public List<EquipmentInfo> getEquipment() {
		return equipment;
	}

	public String display() {
		return String.format("{%s} %s [%s/%s]", version, name, classTag, ascendency);
	}

	public void save() {
		var folder = new File("builds");
		if (!folder.exists())
			folder.mkdirs();
		try {
			var filename = String.format("{%s} %s.yaml", version, name).replaceAll(" ", "_");
			File buildFile = new File(folder, filename);
			Util.YAML_MAPPER.writeValue(buildFile, this);
		} catch (IOException ioe) {
			LOGGER.error("Unable to save build: " + ioe);
		}
	}
}