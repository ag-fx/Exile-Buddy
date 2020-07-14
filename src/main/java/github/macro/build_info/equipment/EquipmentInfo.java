package github.macro.build_info.equipment;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
public class EquipmentInfo {
	@NotNull
	private final String name;
	@NotNull
	private final Slot slot;

	public EquipmentInfo(@NotNull String name, @NotNull Slot slot) {
		this.name = name;
		this.slot = slot;
	}

	@NotNull
	public String getName() {
		return name;
	}

	@NotNull
	public Slot getSlot() {
		return slot;
	}
}