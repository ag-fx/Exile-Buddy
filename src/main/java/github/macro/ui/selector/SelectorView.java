package github.macro.ui.selector;

import github.macro.build_info.Ascendency;
import github.macro.build_info.BuildInfo;
import github.macro.build_info.ClassTag;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Created by Macro303 on 2020-Jan-22.
 */
public class SelectorView {
	private SelectorController controller;
	private SelectorModel model;

	private BorderPane root;
	private ComboBox<BuildInfo> selectionComboBox;
	private TextField nameTextField;
	private ComboBox<ClassTag> classComboBox;
	private ComboBox<Ascendency> ascendencyComboBox;

	public SelectorView(SelectorController controller, SelectorModel model) {
		this.controller = controller;
		this.model = model;

		createView();
	}

	public Parent asParent() {
		return root;
	}

	private void createView() {
		var titleView = createTitleView();

		root = new BorderPane();
		root.setTop(titleView);
//		selectionComboBox = new ComboBox<>(model.buildsProperty());
//		root.setCenter(new VBox(5.0, new HBox(5.0, selectionComboBox, new Button("Testing"))));
	}

	private HBox createTitleView() {
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
}