package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import github.macro.Util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class GemBuild {
	private final List<List<Optional<GemInfo>>> links;
	private final List<UpdateGem> updates;

	@JsonCreator
	public GemBuild(@JsonProperty("links") List<List<String>> links, @JsonProperty("updates") List<UpdateGem> updates) {
		this.links = links.stream().map(link -> link.stream().map(gem -> Util.gemByName(gem)).collect(Collectors.toList())).collect(Collectors.toList());
		this.updates = updates;
	}

	public List<List<Optional<GemInfo>>> getLinks() {
		return links;
	}

	public List<UpdateGem> getUpdates() {
		return updates;
	}

	@Override
	public String toString() {
		return "GemBuild{" +
				"links=" + links +
				", updates=" + updates +
				'}';
	}
}