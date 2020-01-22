package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Acquisition {
	private final List<Recipe> recipes;
	private final List<Quest> quests;
	private final List<Vendor> vendors;

	@JsonCreator
	public Acquisition(@JsonProperty("recipes") List<Recipe> recipes, @JsonProperty("quests") List<Quest> quests, @JsonProperty("vendors") List<Vendor> vendors) {
		this.recipes = recipes;
		this.quests = quests;
		this.vendors = vendors;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	@Override
	public String toString() {
		return "Acquisition{" +
				"recipes=" + recipes +
				", quests=" + quests +
				", vendors=" + vendors +
				'}';
	}
}