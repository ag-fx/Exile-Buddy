package macro.buddy.builds;

import macro.buddy.ClassTag;

import java.util.*;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public enum AscendencyTag {
	NONE,
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

	public static List<AscendencyTag> values(ClassTag classTag) {
		switch (classTag) {
			case DUELIST:
				return Arrays.asList(SLAYER, GLADIATOR, CHAMPION, NONE);
			case SHADOW:
				return Arrays.asList(ASSASSIN, SABOTEUR, TRICKSTER, NONE);
			case MARAUDER:
				return Arrays.asList(JUGGERNAUT, BERSERKER, CHIEFTAIN, NONE);
			case WITCH:
				return Arrays.asList(NECROMANCER, ELEMENTALIST, OCCULTIST, NONE);
			case RANGER:
				return Arrays.asList(DEADEYE, RAIDER, PATHFINDER, NONE);
			case TEMPLAR:
				return Arrays.asList(INQUISTOR, HIEROPHANT, GUARDIAN, NONE);
			case SCION:
				return Arrays.asList(ASCENDANT, NONE);
		}
		return new ArrayList<>();
	}
}