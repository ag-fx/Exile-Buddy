package github.macro.ui

import github.macro.Util
import github.macro.build_info.Build
import github.macro.build_info.BuildGems
import github.macro.ui.viewer.BuildViewer
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Selector : View() {
	private val model by inject<UIModel>()

	override val root = borderpane {
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
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
			}
		}
		center {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					val buildCombobox = combobox<Build>(property = model.selectedBuildProperty, values = model.builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						maxWidth = Double.MAX_VALUE
						cellFormat {
							text = it.display()
						}
					}
					button(text = "Select") {
						minWidth = 100.0
						action {
							LOGGER.info("Viewing Build: ${model.selectedBuild.display()}")
							val scope = Scope()
							setInScope(model, scope)
							find<BuildViewer>(scope).openWindow(owner = null, resizable = false)
							close()
						}
						disableWhen {
							buildCombobox.valueProperty().isNull
						}
					}
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					val versionTextfield = textfield {
						promptText = "PoE Version"
					}
					val nameTextfield = textfield {
						promptText = "Build Name"
						hgrow = Priority.ALWAYS
					}
					val classCombobox = combobox(values = model.classes) {
						promptText = "Class"
					}
					val ascendencyCombobox = combobox(values = model.ascendencies) {
						promptText = "Ascendency"
						disableWhen {
							classCombobox.valueProperty().isNull
						}
					}
					classCombobox.setOnAction {
						ascendencyCombobox.selectionModel.clearSelection()
						if (classCombobox.selectedItem != null)
							model.selectedClass(classCombobox.selectedItem!!)
					}
					button(text = "Create") {
						minWidth = 100.0
						action {
							val info = Build(
								version = versionTextfield.text,
								name = nameTextfield.text,
								classTag = classCombobox.selectedItem!!,
								ascendency = ascendencyCombobox.selectedItem!!,
								buildGems = BuildGems(
									armourLinks = emptyList(),
									helmetLinks = emptyList(),
									gloveLinks = emptyList(),
									bootLinks = emptyList(),
									weapon1Links = Util.getClassGems(classTag = classCombobox.selectedItem!!),
									weapon2Links = emptyList(),
									updates = emptyList()
								),
								equipment = emptyList()
							)
							LOGGER.info("Creating Build: ${info.display()}")
							info.save()
							model.selectedBuild = info
							val scope = Scope()
							setInScope(model, scope)
							find<BuildViewer>(scope).openWindow(owner = null, resizable = false)
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
	}

	init {
		model.loadBuilds()
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Selector::class.java)
	}
}