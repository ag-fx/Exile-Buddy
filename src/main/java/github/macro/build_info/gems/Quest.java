package github.macro.build_info.gems;

import github.macro.build_info.ClassTag;

import java.util.SortedSet;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Quest {
	private final int act;
	private final String quest;
	private final SortedSet<ClassTag> classes;

	public Quest(int act, String quest, SortedSet<ClassTag> classes) {
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