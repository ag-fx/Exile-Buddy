package github.macro.build_info.gems.acquisition;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import github.macro.build_info.ClassTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
@JsonDeserialize(using = RewardDeserializer.class)
public class Reward {
	@NotNull
	private final Integer act;
	@NotNull
	private final List<@NotNull ClassTag> classes;
	@NotNull
	private final String quest;
	@NotNull
	private final RewardType rewardType;
	@Nullable
	private final String vendor;

	public Reward(@NotNull Integer act, @NotNull List<@NotNull ClassTag> classes, @NotNull String quest, @NotNull RewardType rewardType, @Nullable String vendor) {
		this.act = act;
		this.classes = classes;
		this.quest = quest;
		this.rewardType = rewardType;
		this.vendor = vendor;
	}

	@NotNull
	public Integer getAct() {
		return act;
	}

	@NotNull
	public List<@NotNull ClassTag> getClasses() {
		return classes;
	}

	@NotNull
	public String getQuest() {
		return quest;
	}

	@NotNull
	public RewardType getRewardType() {
		return rewardType;
	}

	@Nullable
	public String getVendor() {
		return vendor;
	}
}