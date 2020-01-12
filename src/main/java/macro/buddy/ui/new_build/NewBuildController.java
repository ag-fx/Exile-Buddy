package macro.buddy.ui.new_build;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.ClassTag;
import macro.buddy.builds.AscendencyTag;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;
import macro.buddy.ui.BuildSelector;
import macro.buddy.ui.builds.GemPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class NewBuildController extends BuildSelector implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(NewBuildController.class);
	@FXML
	private TextField buildName;
	@FXML
	private ComboBox<ClassTag> classCombo;
	@FXML
	private ComboBox<AscendencyTag> ascendencyCombo;

	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.init();
		classCombo.setItems(FXCollections.observableArrayList(ClassTag.values()));
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void classSelection(ActionEvent event) {
		if (classCombo.getValue() != null) {
			ascendencyCombo.setDisable(false);
			ascendencyCombo.setItems(FXCollections.observableList(AscendencyTag.values(classCombo.getValue())));
		}
	}

	public void addBuild(ActionEvent event) {
		if (buildName.getText() == null || buildName.getText().trim().isEmpty())
			return;
		BuildInfo newInfo = new BuildInfo(buildName.getText(), classCombo.getValue(), ascendencyCombo.getValue(), new ArrayList<>(), new ArrayList<>());
		newInfo.save();
		buildList.add(newInfo);
		buildCombo.getSelectionModel().select(newInfo);
	}

	@Override
	public void updateDisplay(){
		if (buildCombo.getValue() == null) {
			LOGGER.info("Clearing Display");
			//Clear Display
			return;
		}
		buildName.clear();
		classCombo.getSelectionModel().clearSelection();
		classCombo.setValue(null);
		ascendencyCombo.getSelectionModel().clearSelection();
		ascendencyCombo.setValue(null);
		LOGGER.info("Updating Build to: {}", buildCombo.getValue().getName());
	}
}