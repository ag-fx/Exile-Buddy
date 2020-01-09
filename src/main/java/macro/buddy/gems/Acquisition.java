package macro.buddy.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import macro.buddy.ClassTag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Acquisition {
	private final List<Recipe> recipes;
	private final List<Vendor> vendors;
	private final List<Quest> quests;

	@JsonCreator
	public Acquisition(@JsonProperty("recipes") List<Recipe> recipes, @JsonProperty("vendors") List<Vendor> vendors, @JsonProperty("quests") List<Quest> quests) {
		this.recipes = recipes;
		this.vendors = vendors;
		this.quests = quests;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public String getDisplay(ClassTag buildClass) {
		var recipeDisplay = recipes.stream().map(Recipe::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		var vendorDisplay = vendors.stream().filter(it -> it.getClasses().contains(buildClass)).map(Vendor::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		var questDisplay = quests.stream().filter(it -> it.getClasses().contains(buildClass)).map(Quest::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		return String.format("Recipes:%s\nVendors:%s\nQuests:%s", recipeDisplay, vendorDisplay, questDisplay);
	}

	@Override
	public String toString() {
		return "Acquisition{" +
				"recipes=" + recipes +
				", vendors=" + vendors +
				", quests=" + quests +
				'}';
	}
}