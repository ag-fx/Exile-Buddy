package macro.buddy.builds;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Macro303 on 2020-Jan-08.
 */
public class UpdateGem {
	private final String oldGem;
	private final String newGem;
	private final String reason;

	@JsonCreator
	public UpdateGem(@JsonProperty("oldGem") String oldGem, @JsonProperty("newGem") String newGem, @JsonProperty("reason") String reason) {
		this.oldGem = oldGem;
		this.newGem = newGem;
		this.reason = reason;
	}

	public String getOldGem() {
		return oldGem;
	}

	public String getNewGem() {
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