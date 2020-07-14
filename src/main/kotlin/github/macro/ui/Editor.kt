package github.macro.ui

import github.macro.build_info.Ascendency
import github.macro.build_info.Build
import github.macro.build_info.ClassTag
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Editor : View() {
	private val model: UIModel by inject()
	private var selected = model.buildProperty.value!!
	private val ascendencyList = FXCollections.observableArrayList<Ascendency>()

	override val root = borderpane {
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				label(text = selected.display())
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
			}
		}
		center {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				val versionTextfield = textfield {
					promptText = "PoE Version"
					text = selected.version
				}
				val nameTextfield = textfield {
					promptText = "Build Name"
					text = selected.name
					hgrow = Priority.ALWAYS
				}
				val classCombobox = combobox(values = ClassTag.values().asList()) {
					promptText = "Class"
					selectionModel.select(selected.classTag)
				}
				ascendencyList.setAll(Ascendency.values(classCombobox.value))
				val ascendencyCombobox = combobox(values = ascendencyList) {
					promptText = "Ascendency"
					selectionModel.select(selected.ascendency)
					disableWhen {
						classCombobox.valueProperty().isNull
					}
				}
				classCombobox.setOnAction {
					ascendencyCombobox.selectionModel.clearSelection()
					if (classCombobox.value != null)
						ascendencyList.setAll(Ascendency.values(classCombobox.value))
				}
				button(text = "Save") {
					minWidth = 100.0
					action {
						val oldName = selected.filename
						selected.version = versionTextfield.text
						selected.name = nameTextfield.text
						selected.classTag = classCombobox.selectedItem!!
						selected.ascendency = ascendencyCombobox.selectedItem!!
						selected.rename(oldName)
						LOGGER.info("Updating Build: ${selected.display()}")
						val scope = Scope()
						setInScope(model, scope)
						find<Viewer>(scope).openWindow(owner = null, resizable = false)
						close()
					}
					disableWhen {
						versionTextfield.textProperty().isEmpty
							.or(nameTextfield.textProperty().length().lessThanOrEqualTo(3))
							.or(classCombobox.valueProperty().isNull)
							.or(ascendencyCombobox.valueProperty().isNull)
					}
				}
			}
		}
	}

	override fun onDock() {
		currentWindow?.setOnCloseRequest {
			LOGGER.info("Closing Build Editor: ${selected.display()}")
			find<Viewer>().openWindow(owner = null, resizable = false)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Editor::class.java)
	}
}