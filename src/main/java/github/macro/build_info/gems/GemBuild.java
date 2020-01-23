package github.macro.build_info.gems;

import java.util.List;
import java.util.Optional;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class GemBuild {
	private final List<List<Optional<Gem>>> links;
	private final List<UpdateGem> updates;

	public GemBuild(List<List<Optional<Gem>>> links, List<UpdateGem> updates) {
		this.links = links;
		this.updates = updates;
	}

	public List<List<Optional<Gem>>> getLinks() {
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