package github.macro.build_info;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public enum ClassTag {
	SCION,
	MARAUDER,
	RANGER,
	WITCH,
	DUELIST,
	TEMPLAR,
	SHADOW;

	@Nullable
	public static ClassTag value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}