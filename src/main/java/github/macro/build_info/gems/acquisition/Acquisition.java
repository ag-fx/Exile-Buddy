package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
@JsonDeserialize(using = AcquisitionDeserializer.class)
public class Acquisition {
	@NotNull
	private final List<@NotNull Reward> rewards;
	@NotNull
	private final List<@NotNull List<@NotNull Ingredient>> crafting;

	public Acquisition(@NotNull List<Reward> rewards, @NotNull List<@NotNull List<@NotNull Ingredient>> crafting) {
		this.rewards = rewards;
		this.crafting = crafting;
	}

	@NotNull
	public List<@NotNull Reward> getRewards() {
		return rewards;
	}

	@NotNull
	public List<@NotNull List<@NotNull Ingredient>> getCrafting() {
		return crafting;
	}
}