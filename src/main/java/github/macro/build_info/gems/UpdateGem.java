package github.macro.build_info.gems;

import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class UpdateGem {
	private final Optional<Gem> oldGem;
	private final Optional<Gem> newGem;
	private final String reason;

	public UpdateGem(Optional<Gem> oldGem, Optional<Gem> newGem, String reason) {
		this.oldGem = oldGem;
		this.newGem = newGem;
		this.reason = reason;
	}

	public Optional<Gem> getOldGem() {
		return oldGem;
	}

	public Optional<Gem> getNewGem() {
		return newGem;
	}

	public String getReason() {
		return reason;
	}

	@Override
	public String toString() {
		return "UpdateGem{" +
				"oldGem='" + oldGem + '\'' +
				", newGem='" + newGem + '\'' +
				", reason='" + reason + '\'' +
				'}';
	}
}