package github.macro.build_info.gems;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Macro303 on 2019-Dec-27
 */
public enum GemTag {
	WARCRY,
	CHAOS,
	AOE,
	DURATION,
	ACTIVE,
	SUPPORT,
	COLD,
	LIGHTNING,
	FIRE,
	TRAP,
	ATTACK,
	TOTEM,
	MELEE,
	SPELL,
	AURA,
	MINION,
	CHAINING,
	PROJECTILE,
	BRAND,
	CURSE,
	TRIGGER,
	BOW,
	CHANNELLING,
	MOVEMENT,
	TRAVEL,
	BLINK,
	HERALD,
	GUARD,
	MINE,
	GOLEM,
	VAAL,
	PHYSICAL,
	STRIKE,
	NOVA;

	public static Optional<GemTag> value(String name) {
		return Arrays.stream(values()).filter(tag -> tag.name().equalsIgnoreCase(name)).findFirst();
	}
}