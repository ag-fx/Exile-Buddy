package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import github.macro.Util;

import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class UpdateGem {
	private final Optional<GemInfo> oldGem;
	private final Optional<GemInfo> newGem;
	private final String reason;

	@JsonCreator
	public UpdateGem(@JsonProperty("oldGem") String oldGem, @JsonProperty("newGem") String newGem, @JsonProperty("reason") String reason) {
		this.oldGem = Util.gemByName(oldGem);
		this.newGem = Util.gemByName(newGem);
		this.reason = reason;
	}

	public Optional<GemInfo> getOldGem() {
		return oldGem;
	}

	public Optional<GemInfo> getNewGem() {
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