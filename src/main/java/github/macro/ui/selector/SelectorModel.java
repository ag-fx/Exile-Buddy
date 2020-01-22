package github.macro.ui.selector;

import github.macro.Util;
import github.macro.build_info.BuildInfo;
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
	private final ObjectProperty<BuildInfo> selected = new SimpleObjectProperty<>();
	private final ListProperty<BuildInfo> builds;

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
		this.builds = new SimpleListProperty<>(FXCollections.observableArrayList(temp));
	}

	public BuildInfo getSelected() {
		return selected.get();
	}

	public ObjectProperty<BuildInfo> selectedProperty() {
		return selected;
	}

	public void setSelected(BuildInfo selected) {
		this.selected.set(selected);
	}

	public ObservableList<BuildInfo> getBuilds() {
		return builds.get();
	}

	public ListProperty<BuildInfo> buildsProperty() {
		return builds;
	}

	public void setBuilds(ObservableList<BuildInfo> builds) {
		this.builds.set(builds);
	}
}