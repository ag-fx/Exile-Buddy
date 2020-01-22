package github.macro.ui.selector;

import github.macro.Util;
import github.macro.build_info.Ascendency;
import github.macro.build_info.BuildInfo;
import github.macro.build_info.ClassTag;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class SelectorModel {
	private static final Logger LOGGER = LogManager.getLogger(SelectorModel.class);
	private final ListProperty<BuildInfo> buildList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObjectProperty<BuildInfo> selectedBuild = new SimpleObjectProperty<>();
	private final ListProperty<ClassTag> classList = new SimpleListProperty<>(FXCollections.observableArrayList(ClassTag.values()));
	private final ObjectProperty<ClassTag> selectedClass = new SimpleObjectProperty<>();
	private final ListProperty<Ascendency> ascendencyList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObjectProperty<Ascendency> selectedAscendency = new SimpleObjectProperty<>();

	public SelectorModel() {
		var files = new File("builds").listFiles();
		if (files == null)
			files = new File[]{};
		var temp = new ArrayList<BuildInfo>();
		Arrays.stream(files).forEach(file -> {
			try {
				temp.add(Util.YAML_MAPPER.readValue(file, BuildInfo.class));
			} catch (IOException ioe) {
				LOGGER.error("Unable to Load Build: {}", file.getName(), ioe);
			}
		});
		buildList.setAll(temp);

		selectedClass.addListener((observable, oldValue, newValue) -> {
			if (newValue != null)
				ascendencyList.setAll(Ascendency.values(newValue));
			else
				ascendencyList.clear();
		});
	}

	public ObservableList<BuildInfo> getBuildList() {
		return buildList.get();
	}

	public ListProperty<BuildInfo> buildListProperty() {
		return buildList;
	}

	public void setBuildList(ObservableList<BuildInfo> buildList) {
		this.buildList.set(buildList);
	}

	public BuildInfo getSelectedBuild() {
		return selectedBuild.get();
	}

	public ObjectProperty<BuildInfo> selectedBuildProperty() {
		return selectedBuild;
	}

	public void setSelectedBuild(BuildInfo selectedBuild) {
		this.selectedBuild.set(selectedBuild);
	}

	public ObservableList<ClassTag> getClassList() {
		return classList.get();
	}

	public ListProperty<ClassTag> classListProperty() {
		return classList;
	}

	public void setClassList(ObservableList<ClassTag> classList) {
		this.classList.set(classList);
	}

	public ClassTag getSelectedClass() {
		return selectedClass.get();
	}

	public ObjectProperty<ClassTag> selectedClassProperty() {
		return selectedClass;
	}

	public void setSelectedClass(ClassTag selectedClass) {
		this.selectedClass.set(selectedClass);
	}

	public ObservableList<Ascendency> getAscendencyList() {
		return ascendencyList.get();
	}

	public ListProperty<Ascendency> ascendencyListProperty() {
		return ascendencyList;
	}

	public void setAscendencyList(ObservableList<Ascendency> ascendencyList) {
		this.ascendencyList.set(ascendencyList);
	}

	public Ascendency getSelectedAscendency() {
		return selectedAscendency.get();
	}

	public ObjectProperty<Ascendency> selectedAscendencyProperty() {
		return selectedAscendency;
	}

	public void setSelectedAscendency(Ascendency selectedAscendency) {
		this.selectedAscendency.set(selectedAscendency);
	}
}