package github.macro;

import github.macro.ui.selector.SelectorController;
import github.macro.ui.selector.SelectorModel;
import github.macro.ui.selector.SelectorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Launcher extends Application {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class);

	public static void main(String[] args) {
		checkLogLevels();
		LOGGER.info("Initializing Exile Buddy");
		launch(args);
	}

	private static void checkLogLevels() {
		Arrays.stream(Level.values()).sorted().forEach(level -> LOGGER.log(level, "{} is Visible", level.name()));
	}

	@Override
	public void start(Stage primaryStage) {
		SelectorModel model = new SelectorModel();
		SelectorController controller = new SelectorController(model);
		SelectorView view = new SelectorView(controller, model);

		Scene scene = new Scene(view.asParent(), 400, 400);
		scene.getStylesheets().add(getClass().getResource("Dark-Theme.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("Custom.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}