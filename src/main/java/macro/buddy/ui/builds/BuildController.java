package macro.buddy.ui.builds;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import macro.buddy.builds.AscendencyTag;
import macro.buddy.builds.BuildInfo;
import macro.buddy.builds.BuildUtils;
import macro.buddy.ui.new_build.NewBuildUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class BuildController implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(BuildController.class);
	@FXML
	private ComboBox<BuildInfo> buildCombo;
	@FXML
	private VBox buildGemLinks;

	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		buildCombo.getItems().setAll(BuildUtils.getBuilds());
		buildCombo.setConverter(new StringConverter<BuildInfo>() {
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

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void setBuild(BuildInfo build) {
		buildGemLinks.getChildren().clear();
		buildGemLinks.setFillWidth(true);
		build.getLinks().forEach(link -> {
			HBox linkBox = new HBox();
			linkBox.setSpacing(5.0);
			link.forEach(gem -> {
				GemPane grid = new GemPane(build, BuildUtils.getGem(gem));

				linkBox.getChildren().add(grid);
			});
			buildGemLinks.getChildren().add(linkBox);
		});
	}

	public void buildSelection(ActionEvent event) {
		setBuild(buildCombo.getValue());
	}

	public void addBuild(ActionEvent event) {
		try {
			if (stage != null)
				new NewBuildUI().start(stage);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.error("Unable to Load New Build UI: {}", ioe.getLocalizedMessage());
		}
	}
}