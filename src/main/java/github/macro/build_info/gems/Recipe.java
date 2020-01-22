package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Recipe {
	private final int amount;
	private final String ingredient;

	@JsonCreator
	public Recipe(@JsonProperty("amount") int amount, @JsonProperty("ingredient") String ingredient) {
		this.amount = amount;
		this.ingredient = ingredient;
	}

	public int getAmount() {
		return amount;
	}

	public String getIngredient() {
		return ingredient;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"amount=" + amount +
				", ingredient='" + ingredient + '\'' +
				'}';
	}
}