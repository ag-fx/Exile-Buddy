package github.macro.build_info.gems;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Recipe {
	private final int amount;
	private final String ingredient;

	public Recipe(int amount, String ingredient) {
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