package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
@JsonDeserialize(using = IngredientDeserializer.class)
public class Ingredient {
	@NotNull
	private final String name;
	@NotNull
	private final Integer count;
	@Nullable
	private final Integer level;
	@Nullable
	private final Double quality;
	@NotNull
	private final IngredientType ingredientType;

	public Ingredient(@NotNull String name, @NotNull Integer count, @Nullable Integer level, @Nullable Double quality, @NotNull IngredientType ingredientType) {
		this.name = name;
		this.count = count;
		this.level = level;
		this.quality = quality;
		this.ingredientType = ingredientType;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public Integer getCount() {
		return count;
	}

	@Nullable
	public Integer getLevel() {
		return level;
	}

	@Nullable
	public Double getQuality() {
		return quality;
	}

	@NotNull
	public IngredientType getIngredientType() {
		return ingredientType;
	}
}
