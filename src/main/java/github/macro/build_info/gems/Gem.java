package github.macro.build_info.gems;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.SortedSet;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
@JsonDeserialize(using = GemDeserializer.class)
public class Gem {
	private final String name;
	private final Slot slot;
	private final SortedSet<GemTag> tags;
	private final boolean isVaal;
	private final boolean isAwakened;
	private final Acquisition acquisition;

	public Gem(String name, Slot slot, SortedSet<GemTag> tags, boolean isVaal, boolean isAwakened, Acquisition acquisition) {
		this.name = name;
		this.slot = slot;
		this.tags = tags;
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