package github.macro.build_info.gems;

import github.macro.build_info.ClassTag;

import java.util.SortedSet;

/**
 * Created by Macro303 on 2020-Jan-09.
 */
public class Vendor {
	private final String vendor;
	private final int act;
	private final String quest;
	private final SortedSet<ClassTag> classes;

	public Vendor(String vendor, int act, String quest, SortedSet<ClassTag> classes) {
		this.vendor = vendor;
		this.act = act;
		this.quest = quest;
		this.classes = classes;
	}

	public String getVendor() {
		return vendor;
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
				"vendor='" + vendor + '\'' +
				", act=" + act +
				", quest='" + quest + '\'' +
				", classes=" + classes +
				'}';
	}
}