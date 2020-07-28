package github.macro.build_info.gems

/**
 * Created by Macro303 on 2020-Jan-13.
 */
enum class Tag {
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
	NOVA,
	SLAM;

	companion object {
		fun value(name: String): Tag? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}