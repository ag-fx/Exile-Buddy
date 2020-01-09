package macro.buddy.builds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import macro.buddy.ClassTag;
import macro.buddy.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-02
 */
public class BuildInfo implements Comparable<BuildInfo> {
	@JsonIgnore
	private static final Logger LOGGER = LogManager.getLogger(BuildInfo.class);
	private final String name;
	private final ClassTag classTag;
	private final AscendencyTag ascendency;
	private final List<List<String>> links;
	private final List<UpdateGem> updates;

	@JsonCreator
	public BuildInfo(@JsonProperty("name") String name, @JsonProperty("class") ClassTag classTag, @JsonProperty("ascendency") AscendencyTag ascendency, @JsonProperty("links") List<List<String>> links, @JsonProperty("updates") List<UpdateGem> updates) {
		this.name = name;
		this.classTag = classTag;
		this.ascendency = ascendency;
		this.links = links;
		this.updates = updates;
	}

	public String getName() {
		return name;
	}

	public ClassTag getClassTag() {
		return classTag;
	}

	public AscendencyTag getAscendency() {
		return ascendency;
	}

	public List<List<String>> getLinks() {
		return links;
	}

	public List<UpdateGem> getUpdates() {
		return updates;
	}

	@Override
	public String toString() {
		return "BuildInfo{" +
				"name='" + name + '\'' +
				", classTag=" + classTag +
				", ascendancy=" + ascendency +
				", links=" + links +
				", updates=" + updates +
				'}';
	}

	@Override
	public int compareTo(BuildInfo other) {
		return name.compareToIgnoreCase(other.name);
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