package macro.buddy.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;
import java.util.SortedSet;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo implements Comparable<GemInfo> {
	private final String name;
	private final String slot;
	private final SortedSet<GemTag> tags;
	private final boolean hasVaal;
	private final boolean hasAwakened;
	private final Acquisition acquisition;

	@JsonCreator
	public GemInfo(@JsonProperty("name") String name, @JsonProperty("resultType") String slot, @JsonProperty("tags") SortedSet<GemTag> tags, @JsonProperty("hasVaal") boolean hasVaal, @JsonProperty("hasAwakened") boolean hasAwakened, @JsonProperty("acquisition") Acquisition acquisition) {
		this.name = name;
		this.slot = slot;
		this.tags = tags;
		this.hasVaal = hasVaal;
		this.hasAwakened = hasAwakened;
		this.acquisition = acquisition;
	}

	public String getName() {
		return name;
	}

	public String getSlot() {
		return slot;
	}

	public SortedSet<GemTag> getTags() {
		return tags;
	}

	public boolean hasVaal() {
		return hasVaal;
	}

	public boolean hasAwakened() {
		return hasAwakened;
	}

	public Acquisition getAcquisition() {
		return acquisition;
	}

	public String getFilename(boolean isVaal, boolean isAwakened) {
		String filename = getName().replaceAll(" ", "_");
		if (isVaal && hasVaal)
			filename += "[Vaal]";
		if (isAwakened && hasAwakened)
			filename += "[Awakened]";
		return filename + ".png";
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", slot='" + slot + '\'' +
				", tags=" + tags +
				", hasVaal=" + hasVaal +
				", hasAwakened=" + hasAwakened +
				", acquisition=" + acquisition +
				'}';
	}

	@Override
	public int compareTo(GemInfo other) {
		return getName().compareToIgnoreCase(other.getName());
	}
}