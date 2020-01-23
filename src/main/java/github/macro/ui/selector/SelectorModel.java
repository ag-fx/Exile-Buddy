package github.macro.ui.selector;

import github.macro.Util;
import github.macro.build_info.Ascendency;
import github.macro.build_info.Build;
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
	private final ListProperty<Build> buildList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObjectProperty<Build> selectedBuild = new SimpleObjectProperty<>();
	private final ListProperty<ClassTag> classList = new SimpleListProperty<>(FXCollections.observableArrayList(ClassTag.values()));
	private final ObjectProperty<ClassTag> selectedClass = new SimpleObjectProperty<>();
	private final ListProperty<Ascendency> ascendencyList = new SimpleListProperty<>(FXCollections.observableArrayList());
	private final ObjectProperty<Ascendency> selectedAscendency = new SimpleObjectProperty<>();

	public SelectorModel() {
		var files = new File("builds").listFiles();
		if (files == null)
			files = new File[]{};
		var temp = new ArrayList<Build>();
		Arrays.stream(files).forEach(file -> {
			try {
				temp.add(Util.YAML_MAPPER.readValue(file, Build.class));
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

	public ObservableList<Build> getBuildList() {
		return buildList.get();
	}

	public void setBuildList(ObservableList<Build> buildList) {
		this.buildList.set(buildList);
	}

	public ListProperty<Build> buildListProperty() {
		return buildList;
	}

	public Build getSelectedBuild() {
		return selectedBuild.get();
	}

	public void setSelectedBuild(Build selectedBuild) {
		this.selectedBuild.set(selectedBuild);
	}

	public ObjectProperty<Build> selectedBuildProperty() {
		return selectedBuild;
	}

	public ObservableList<ClassTag> getClassList() {
		return classList.get();
	}

	public void setClassList(ObservableList<ClassTag> classList) {
		this.classList.set(classList);
	}

	public ListProperty<ClassTag> classListProperty() {
		return classList;
	}

	public ClassTag getSelectedClass() {
		return selectedClass.get();
	}

	public void setSelectedClass(ClassTag selectedClass) {
		this.selectedClass.set(selectedClass);
	}

	public ObjectProperty<ClassTag> selectedClassProperty() {
		return selectedClass;
	}

	public ObservableList<Ascendency> getAscendencyList() {
		return ascendencyList.get();
	}

	public void setAscendencyList(ObservableList<Ascendency> ascendencyList) {
		this.ascendencyList.set(ascendencyList);
	}

	public ListProperty<Ascendency> ascendencyListProperty() {
		return ascendencyList;
	}

	public Ascendency getSelectedAscendency() {
		return selectedAscendency.get();
	}

	public void setSelectedAscendency(Ascendency selectedAscendency) {
		this.selectedAscendency.set(selectedAscendency);
	}

	public ObjectProperty<Ascendency> selectedAscendencyProperty() {
		return selectedAscendency;
	}
}