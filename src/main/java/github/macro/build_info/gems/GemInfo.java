package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class GemInfo {
	private final String name;
	private final Slot slot;
	private final SortedSet<GemTag> tags;
	private final boolean isVaal;
	private final boolean isAwakened;
	private final Acquisition acquisition;

	@JsonCreator
	public GemInfo(@JsonProperty("name") String name, @JsonProperty("slot") String slot, @JsonProperty("tags") SortedSet<String> tags, @JsonProperty("isVaal") boolean isVaal, @JsonProperty("isAwakened") boolean isAwakened, @JsonProperty("acquisition") Acquisition acquisition) {
		this.name = name;
		this.slot = Slot.value(slot).orElseThrow(() -> new NullPointerException("Invalid Slot provided"));
		this.tags = tags.stream().map(tag -> GemTag.value(tag).orElseThrow(() -> new NullPointerException("Invalid Gem Tag provided"))).collect(Collectors.toCollection(TreeSet::new));
		this.isVaal = isVaal;
		this.isAwakened = isAwakened;
		this.acquisition = acquisition;
	}

	public String getName() {
		return name;
	}

	public Slot getSlot() {
		return slot;
	}

	public SortedSet<GemTag> getTags() {
		return tags;
	}

	public boolean isVaal() {
		return isVaal;
	}

	public boolean isAwakened() {
		return isAwakened;
	}

	public Acquisition getAcquisition() {
		return acquisition;
	}

	public String getFilename() {
		var output = name.replaceAll(" ", "_");
		if (isVaal)
			output += "[Vaal]";
		if (isAwakened)
			output += "[Awakened]";
		return output + ".png";
	}

	public String getFullname() {
		var output = "";
		if (isVaal)
			output += "Vaal ";
		if (isAwakened)
			output += "Awakened ";
		return output + name;
	}

	public String getDisplay() {
		var output = name;
		if (isVaal)
			output += " [Vaal]";
		if (isAwakened)
			output += " [Awakened]";
		return output;
	}

	@Override
	public String toString() {
		return "GemInfo{" +
				"name='" + name + '\'' +
				", slot='" + slot + '\'' +
				", tags=" + tags +
				", isVaal=" + isVaal +
				", isAwakened=" + isAwakened +
				", acquisition=" + acquisition +
				'}';
	}
}