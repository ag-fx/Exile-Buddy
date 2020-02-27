package github.macro.build_info.gems.reward

/**
 * Created by Macro303 on 2020-Feb-27.
 */
enum class RewardType {
	QUEST,
	VENDOR;

	companion object {
		fun value(name: String): RewardType? = values().firstOrNull {
			it.name.replace("_", " ").equals(name, ignoreCase = true)
		}
	}
}