package github.macro.build_info.gems;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public enum Slot {
	GREEN,
	BLUE,
	RED,
	WHITE;

	public static Optional<Slot> value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst();
	}
}