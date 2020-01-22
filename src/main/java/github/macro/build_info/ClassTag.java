package github.macro.build_info;

import java.util.Arrays;
import java.util.Optional;

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

	public static Optional<ClassTag> value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst();
	}
}