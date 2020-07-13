package github.macro.build_info.gems.ingredient

/**
 * Created by Macro303 on 2020-Feb-27.
 */
enum class IngredientType {
	DIVINATION_CARD,
	GEM,
	CURRENCY,
	EQUIPMENT;

	companion object {
		fun value(name: String): IngredientType? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}