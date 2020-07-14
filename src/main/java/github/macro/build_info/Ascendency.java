package github.macro.build_info;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public enum Ascendency {
	SLAYER,
	GLADIATOR,
	CHAMPION,
	ASSASSIN,
	SABOTEUR,
	TRICKSTER,
	JUGGERNAUT,
	BERSERKER,
	CHIEFTAIN,
	NECROMANCER,
	ELEMENTALIST,
	OCCULTIST,
	DEADEYE,
	RAIDER,
	PATHFINDER,
	INQUISTOR,
	HIEROPHANT,
	GUARDIAN,
	ASCENDANT;

	@NotNull
	public static List<Ascendency> values(ClassTag classTag) {
		switch (classTag) {
			case DUELIST:
				return Arrays.asList(SLAYER, GLADIATOR, CHAMPION);
			case SHADOW:
				return Arrays.asList(ASSASSIN, SABOTEUR, TRICKSTER);
			case MARAUDER:
				return Arrays.asList(JUGGERNAUT, BERSERKER, CHIEFTAIN);
			case WITCH:
				return Arrays.asList(NECROMANCER, ELEMENTALIST, OCCULTIST);
			case RANGER:
				return Arrays.asList(DEADEYE, RAIDER, PATHFINDER);
			case TEMPLAR:
				return Arrays.asList(INQUISTOR, HIEROPHANT, GUARDIAN);
			case SCION:
				return Collections.singletonList(ASCENDANT);
			default:
				return new ArrayList<>();
		}
	}

	@Nullable
	public static Ascendency value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().replaceAll("_", " ").equalsIgnoreCase(name)).findFirst().orElse(null);
	}
}