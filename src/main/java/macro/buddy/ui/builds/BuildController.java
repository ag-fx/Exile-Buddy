package macro.buddy.ui.builds;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import macro.buddy.builds.BuildUtils;
import macro.buddy.ui.BuildSelector;
import macro.buddy.ui.new_build.NewBuildUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class BuildController extends BuildSelector implements Initializable {
	private static final Logger LOGGER = LogManager.getLogger(BuildController.class);
	@FXML
	private VBox buildGemLinks;

	private Stage stage = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.init();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void updateDisplay() {
		if (buildCombo.getValue() == null) {
			LOGGER.info("Clearing Display");
			buildGemLinks.getChildren().clear();
			return;
		}
		LOGGER.info("Updating Build to: {}", buildCombo.getValue().getName());
		buildGemLinks.getChildren().clear();
		buildGemLinks.setFillWidth(true);
		buildCombo.getValue().getLinks().forEach(link -> {
			HBox linkBox = new HBox();
			linkBox.setSpacing(5.0);
			link.forEach(gem -> {
				GemPane grid = new GemPane(buildCombo.getValue(), BuildUtils.getGem(gem));

				linkBox.getChildren().add(grid);
			});
			buildGemLinks.getChildren().add(linkBox);
		});
	}

	public void updateBuild(ActionEvent event) {
		try {
			if (stage != null)
				new NewBuildUI().start(stage);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			LOGGER.error("Unable to Load New Build UI: {}", ioe.getLocalizedMessage());
		}
	}
}