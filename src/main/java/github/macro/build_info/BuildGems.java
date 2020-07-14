package github.macro.build_info;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import github.macro.build_info.gems.Gem;
import github.macro.build_info.gems.UpdateGem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by Macro303 on 2020-Jul-14.
 */
@JsonDeserialize(using = BuildGemsDeserializer.class)
@JsonSerialize(using = BuildGemsSerializer.class)
public class BuildGems {
	@NotNull
	private final List<@Nullable Gem> armourLinks;
	@NotNull
	private final List<@Nullable Gem> helmetLinks;
	@NotNull
	private final List<@Nullable Gem> gloveLinks;
	@NotNull
	private final List<@Nullable Gem> bootLinks;
	@NotNull
	private final List<@Nullable Gem> weapon1Links;
	@NotNull
	private final List<@Nullable Gem> weapon2Links;
	@NotNull
	private final List<@NotNull UpdateGem> updates;

	public BuildGems(@NotNull List<@Nullable Gem> armourLinks, @NotNull List<@Nullable Gem> helmetLinks, @NotNull List<@Nullable Gem> gloveLinks, @NotNull List<@Nullable Gem> bootLinks, @NotNull List<@Nullable Gem> weapon1Links, @NotNull List<@Nullable Gem> weapon2Links, @NotNull List<@NotNull UpdateGem> updates) {
		this.armourLinks = armourLinks;
		this.helmetLinks = helmetLinks;
		this.gloveLinks = gloveLinks;
		this.bootLinks = bootLinks;
		this.weapon1Links = weapon1Links;
		this.weapon2Links = weapon2Links;
		this.updates = updates;
	}

	@NotNull
	public List<@Nullable Gem> getArmourLinks() {
		return armourLinks;
	}

	@NotNull
	public List<@Nullable Gem> getHelmetLinks(){
		return helmetLinks;
	}

	@NotNull
	public List<@Nullable Gem> getGloveLinks() {
		return gloveLinks;
	}

	@NotNull
	public List<@Nullable Gem> getBootLinks() {
		return bootLinks;
	}

	@NotNull
	public List<@Nullable Gem> getWeapon1Links() {
		return weapon1Links;
	}

	@NotNull
	public List<@Nullable Gem> getWeapon2Links() {
		return weapon2Links;
	}

	@NotNull
	public List<@NotNull UpdateGem> getUpdates() {
		return updates;
	}
}