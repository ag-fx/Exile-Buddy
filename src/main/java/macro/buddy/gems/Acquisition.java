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

	public String getDisplay(ClassTag buildClass) {
		var recipeDisplay = recipes.stream().map(Recipe::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		var vendorDisplay = vendors.stream().filter(it -> it.getClasses().contains(buildClass)).map(Vendor::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		var questDisplay = quests.stream().filter(it -> it.getClasses().contains(buildClass)).map(Quest::getDisplay).collect(Collectors.joining("\n  ", "\n  ", "")).stripTrailing();
		return String.format("Recipes:%s\nQuests:%s\nVendors:%s", recipeDisplay, questDisplay, vendorDisplay);
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