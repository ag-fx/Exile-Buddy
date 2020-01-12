package macro.buddy.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import macro.buddy.builds.AscendencyTag;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;

/**
 * Created by Macro303 on 2020-Jan-13.
 */
public abstract class BuildSelector {
	@FXML
	protected ComboBox<BuildInfo> buildCombo;
	protected ObservableList<BuildInfo> buildList = FXCollections.observableArrayList(BuildUtils.loadBuilds());

	public void init() {
		buildCombo.setItems(buildList);
		buildCombo.setConverter(new StringConverter<>() {
			@Override
			public String toString(BuildInfo object) {
				return object.getName() + " [" + object.getClassTag() + (object.getAscendency() == AscendencyTag.NONE ? "" : "/" + object.getAscendency().name()) + "]";
			}

			@Override
			public BuildInfo fromString(String string) {
				return null;
			}
		});
	}

	public void buildSelection(ActionEvent event) {
		updateDisplay();
	}

	public abstract void updateDisplay();
}