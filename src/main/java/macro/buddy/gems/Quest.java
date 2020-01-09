package macro.buddy.gems;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import macro.buddy.ClassTag;

import java.util.List;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Quest {
	private final int act;
	private final String quest;
	private final List<ClassTag> classes;

	@JsonCreator
	public Quest(@JsonProperty("act") int act, @JsonProperty("quest") String quest, @JsonProperty("classes") List<ClassTag> classes) {
		this.act = act;
		this.quest = quest;
		this.classes = classes;
	}

	public int getAct() {
		return act;
	}

	public String getQuest() {
		return quest;
	}

	public List<ClassTag> getClasses() {
		return classes;
	}

	public String getDisplay() {
		return String.format("- %s, Act %d", quest, act);
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