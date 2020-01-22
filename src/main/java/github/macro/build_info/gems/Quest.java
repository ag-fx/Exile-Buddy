package github.macro.build_info.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import github.macro.build_info.ClassTag;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Quest {
	private final int act;
	private final String quest;
	private final SortedSet<ClassTag> classes;

	@JsonCreator
	public Quest(@JsonProperty("act") int act, @JsonProperty("quest") String quest, @JsonProperty("classes") SortedSet<String> classes) {
		this.act = act;
		this.quest = quest;
		this.classes = classes.stream().map(tag -> ClassTag.value(tag).orElseThrow(() -> new NullPointerException("Invalid Class provided"))).collect(Collectors.toCollection(TreeSet::new));
	}

	public int getAct() {
		return act;
	}

	public String getQuest() {
		return quest;
	}

	public SortedSet<ClassTag> getClasses() {
		return classes;
	}

	@Override
	public String toString() {
		return "Vendor{" +
				", act=" + act +
				", quest='" + quest + '\'' +
				", classes=" + classes +
				'}';
	}
}