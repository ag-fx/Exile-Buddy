package github.macro.build_info.equipment;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public enum Slot {
	WEAPON_1,
	WEAPON_2,
	CHEST,
	BOOTS,
	GLOVES,
	HELMET,
	AMULET,
	BELT,
	RING,
	FLASK;

	@Nullable
	public static Slot value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}