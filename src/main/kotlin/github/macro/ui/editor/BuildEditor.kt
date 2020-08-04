package github.macro.ui.editor

import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.build_info.Ascendency
import github.macro.build_info.ClassTag
import github.macro.ui.UIController
import github.macro.ui.UIModel
import javafx.geometry.Pos
import javafx.scene.control.ComboBox
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import javafx.scene.control.TextField
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildEditor : View("Exile Buddy") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	private var versionTextField by singleAssign<TextField>()
	private var classComboBox by singleAssign<ComboBox<ClassTag>>()
	private var ascendencyComboBox by singleAssign<ComboBox<Ascendency>>()
	private var nameTextField by singleAssign<TextField>()

	override val root = borderpane {
		prefWidth = 1000.0
		prefHeight = 750.0
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				versionTextField = textfield {
					promptText = "PoE Version"
					text = model.selectedBuild.version
				}
				classComboBox = combobox(values = model.classes) {
					promptText = "Class"
					cellFormat {
						text = it.cleanName()
					}
					selectionModel.select(model.selectedBuild.classTag)
				}
				model.selectedClass(model.selectedBuild.classTag)
				ascendencyComboBox = combobox(values = model.ascendencies) {
					promptText = "Ascendency"
					cellFormat {
						text = it.cleanName()
					}
					disableWhen {
						classComboBox.valueProperty().isNull
					}
					selectionModel.select(model.selectedBuild.ascendency)
				}
				classComboBox.setOnAction {
					ascendencyComboBox.selectionModel.clearSelection()
					controller.updateClass(classComboBox.selectedItem!!)
				}
				nameTextField = textfield {
					promptText = "Build Name"
					hgrow = Priority.ALWAYS
					text = model.selectedBuild.name
				}
				button(text = "Save") {
					addClass(Styles.sizedButton)
					action {
						controller.saveBuild(
							oldView = this@BuildEditor,
							version = versionTextField.text,
							name = nameTextField.text,
							classTag = classComboBox.selectedItem!!,
							ascendency = ascendencyComboBox.selectedItem!!
						)
					}
					disableWhen {
						versionTextField.textProperty().isEmpty
							.or(nameTextField.textProperty().length().lessThanOrEqualTo(3))
							.or(classComboBox.valueProperty().isNull)
							.or(ascendencyComboBox.valueProperty().isNull)
					}
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
			}
		}
		center {
			paddingAll = 5.0
			tabpane {
				tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE
				tab(text = "Gems") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							paddingAll = 5.0
							label("Weapons") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.gems.weapons.size > it)
										model.selectedBuild.gems.weapons[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									if (it == 3)
										add(separator())
									add(GemEditorPane(model, temp))
								}
							}
							label("Armour") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.gems.armour.size > it)
										model.selectedBuild.gems.armour[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model, temp))
								}
							}
							label("Helmet") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.helmet.size > it)
										model.selectedBuild.gems.helmet[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model, temp))
								}
							}
							label("Gloves") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.gloves.size > it)
										model.selectedBuild.gems.gloves[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model, temp))
								}
							}
							label("Boots") {
								addClass(Styles.subtitle)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.boots.size > it)
										model.selectedBuild.gems.boots[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model, temp))
								}
							}
						}
					}
				}
				tab(text = "Equipment") {
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							paddingAll = 5.0
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.weapons.forEach {
									label(it.name)
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.armour.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.helmet.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.gloves.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.boots.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.belt.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								label(model.selectedBuild.equipment.amulet.name)
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.rings.forEach {
									label(it.name)
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								model.selectedBuild.equipment.flasks.forEach {
									label(it.name)
								}
							}
						}
					}
				}
			}
		}
	}

	override fun onDock() {
		currentWindow?.setOnCloseRequest {
			LOGGER.info("Closing Build: ${model.selectedBuild.display}")
			controller.viewBuild(this@BuildEditor)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildEditor::class.java)
	}
}