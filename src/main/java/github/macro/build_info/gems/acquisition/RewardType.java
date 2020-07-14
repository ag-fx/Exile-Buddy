package github.macro.build_info.gems.acquisition;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public enum RewardType {
	QUEST,
	VENDOR;

	@Nullable
	public static RewardType value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}
