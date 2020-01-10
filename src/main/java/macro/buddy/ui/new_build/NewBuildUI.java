package macro.buddy.ui.new_build;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Created by Macro303 on 2019-Dec-03
 */
public class NewBuildUI extends Application {
	private static final Logger LOGGER = LogManager.getLogger(NewBuildUI.class);

	public void init(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("NewBuild.fxml"));
		Parent root = loader.load();
		NewBuildController controller = loader.getController();
		controller.setStage(stage);
		stage.setTitle("Exile Buddy");
		Scene scene = new Scene(root, 800, 600);
		scene.getStylesheets().add(getClass().getResource("../Dark-Theme.css").toExternalForm());
		stage.setScene(scene);
		stage.getIcons().add(new Image("file:logo.png"));
		stage.show();
	}
}