package github.macro.ui.selector;

import github.macro.build_info.Ascendency;
import github.macro.build_info.Build;
import github.macro.build_info.ClassTag;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class SelectorView {
	private static final Logger LOGGER = LogManager.getLogger(SelectorView.class);
	private final Stage stage;
	private SelectorController controller;
	private SelectorModel model;

	private BorderPane root;
	private ComboBox<Build> selectionComboBox;
	private TextField nameTextField;
	private ComboBox<ClassTag> classComboBox;
	private ComboBox<Ascendency> ascendencyComboBox;

	public SelectorView(SelectorController controller, SelectorModel model, Stage primaryStage) {
		this.controller = controller;
		this.model = model;
		this.stage = primaryStage;

		createView();
	}

	public Parent asParent() {
		return root;
	}

	private void createView() {
		root = new BorderPane();
		root.setPadding(new Insets(10.0));
		root.setTop(createTopView());
		root.setCenter(createCenterView());
//		selectionComboBox = new ComboBox<>(model.buildsProperty());
//		root.setCenter(new VBox(5.0, new HBox(5.0, selectionComboBox, new Button("Testing"))));
	}

	private HBox createTopView() {
		var titleBox = new HBox(5.0);
		titleBox.setAlignment(Pos.CENTER);

		var preSeperator = new Separator();
		preSeperator.setVisible(false);
		HBox.setHgrow(preSeperator, Priority.ALWAYS);

		var titleLabel = new Label("Exile Buddy");
		titleLabel.setId("title");

		var postSeperator = new Separator();
		postSeperator.setVisible(false);
		HBox.setHgrow(postSeperator, Priority.ALWAYS);

		titleBox.getChildren().addAll(preSeperator, titleLabel, postSeperator);

		return titleBox;
	}

	private VBox createCenterView() {
		var vBox = new VBox(5.0);
		vBox.setAlignment(Pos.TOP_CENTER);

		var selectionBox = new HBox(5.0);
		selectionBox.setAlignment(Pos.CENTER);
		selectionComboBox = new ComboBox<>(model.buildListProperty());
		selectionComboBox.setPromptText("Build");
		selectionComboBox.setMaxWidth(Double.MAX_VALUE);
		model.selectedBuildProperty().bind(selectionComboBox.valueProperty());
		selectionComboBox.setConverter(new StringConverter<>() {
			@Override
			public String toString(Build build) {
				if (build != null)
					return build.display();
				return null;
			}

			@Override
			public Build fromString(String string) {
				return null;
			}
		});
		HBox.setHgrow(selectionComboBox, Priority.ALWAYS);
		var selectButton = new Button("Select");
		selectButton.setMinWidth(100.0);
		selectButton.setOnAction(event -> {
			LOGGER.info("Selected Build: {}", model.getSelectedBuild() != null ? model.getSelectedBuild().display() : "INVALID");
			var newStage = new Stage();
			newStage.setTitle("Test Screen");
			var test = new SelectorView(controller, model, newStage);
			Scene scene = new Scene(test.asParent(), 700, 200);
//			scene.getStylesheets().add(getClass().getResource("../Dark-Theme.css").toExternalForm());
//			scene.getStylesheets().add(getClass().getResource("../Custom.css").toExternalForm());
			newStage.setScene(scene);
			newStage.initModality(Modality.WINDOW_MODAL);
			newStage.initOwner(stage);
			newStage.show();
		});
		selectButton.disableProperty().bind(selectionComboBox.valueProperty().isNull());
		selectionBox.getChildren().addAll(selectionComboBox, selectButton);

		var createBox = new HBox(5.0);
		createBox.setAlignment(Pos.CENTER);
		nameTextField = new TextField();
		nameTextField.setPromptText("Build Name");
		HBox.setHgrow(nameTextField, Priority.ALWAYS);
		classComboBox = new ComboBox<>(model.classListProperty());
		classComboBox.setPromptText("Class");
		classComboBox.setButtonCell(new ListCell<>());
		model.selectedClassProperty().bind(classComboBox.valueProperty());
		ascendencyComboBox = new ComboBox<>(model.ascendencyListProperty());
		ascendencyComboBox.setPromptText("Ascendency");
		model.selectedAscendencyProperty().bind(ascendencyComboBox.valueProperty());
		ascendencyComboBox.disableProperty().bind(classComboBox.valueProperty().isNull());
		var createButton = new Button("Create");
		createButton.setMinWidth(100.0);
		createButton.setOnAction(event -> {
			LOGGER.info("Created Build: {} [{}/{}]", nameTextField.textProperty().get(), model.getSelectedClass(), model.getSelectedAscendency());
			var newBuild = new Build(nameTextField.textProperty().get(), model.getSelectedClass(), model.getSelectedAscendency());
			newBuild.save();
			model.buildListProperty().add(newBuild);
			ascendencyComboBox.getSelectionModel().clearSelection();
			classComboBox.getSelectionModel().clearSelection();
			nameTextField.setText(null);
		});
		createButton.disableProperty().bind(nameTextField.textProperty().isEmpty().or(classComboBox.valueProperty().isNull()).or(ascendencyComboBox.valueProperty().isNull()));
		createBox.getChildren().addAll(nameTextField, classComboBox, ascendencyComboBox, createButton);

		vBox.getChildren().addAll(selectionBox, createBox);
		return vBox;
	}
}