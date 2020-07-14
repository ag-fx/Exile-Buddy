package github.macro.build_info.gems;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class UpdateGem {
	@Nullable
	private final Gem oldGem;
	@Nullable
	private final Gem newGem;
	@NotNull
	private final String reason;

	public UpdateGem(@Nullable Gem oldGem, @Nullable Gem newGem, @NotNull String reason) {
		this.oldGem = oldGem;
		this.newGem = newGem;
		this.reason = reason;
	}

	@Nullable
	public Gem getOldGem() {
		return oldGem;
	}

	@Nullable
	public Gem getNewGem() {
		return newGem;
	}

	@NotNull
	public String getReason() {
		return reason;
	}
}