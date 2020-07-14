package github.macro.build_info.gems;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public enum Slot {
	GREEN,
	BLUE,
	RED,
	WHITE;

	@Nullable
	public static Slot value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}