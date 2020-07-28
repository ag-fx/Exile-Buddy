package github.macro.ui.editor

import github.macro.Util
import github.macro.ui.UIModel
import github.macro.ui.viewer.BuildViewer
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildEditor : View("Exile Buddy") {
	private val model: UIModel by inject()

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
				val versionTextfield = textfield {
					promptText = "PoE Version"
					text = model.selectedBuild.version
				}
				val hardcoreCheckbox = checkbox("Hardcore") {
					isSelected = model.selectedBuild.isHardcore
				}
				val nameTextfield = textfield {
					promptText = "Build Name"
					hgrow = Priority.ALWAYS
					text = model.selectedBuild.name
				}
				val classCombobox = combobox(values = model.classes) {
					promptText = "Class"
					selectionModel.select(model.selectedBuild.classTag)
				}
				model.selectedClass(model.selectedBuild.classTag)
				val ascendencyCombobox = combobox(values = model.ascendencies) {
					promptText = "Ascendency"
					disableWhen {
						classCombobox.valueProperty().isNull
					}
					selectionModel.select(model.selectedBuild.ascendency)
				}
				classCombobox.setOnAction {
					ascendencyCombobox.selectionModel.clearSelection()
					if (classCombobox.selectedItem != null)
						model.selectedClass(classCombobox.selectedItem!!)
				}
				button(text = "Save") {
					minWidth = 100.0
					action {
						model.selectedBuild.delete()
						model.selectedBuild.version = versionTextfield.text
						model.selectedBuild.isHardcore = hardcoreCheckbox.isSelected
						model.selectedBuild.name = nameTextfield.text
						model.selectedBuild.classTag = classCombobox.selectedItem!!
						model.selectedBuild.ascendency = ascendencyCombobox.selectedItem!!
						model.selectedBuild.save()
						LOGGER.info("Updating Build: ${model.selectedBuild.display()}")
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
							label("Weapons")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.gems.weapons.size > it)
										model.selectedBuild.gems.weapons[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									if (it == 3)
										add(separator {})
									add(GemEditorPane(model.selectedBuild, temp))
								}
							}
							label("Armour")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.gems.armour.size > it)
										model.selectedBuild.gems.armour[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model.selectedBuild, temp))
								}
							}
							label("Helmet")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.helmet.size > it)
										model.selectedBuild.gems.helmet[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model.selectedBuild, temp))
								}
							}
							label("Gloves")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.gloves.size > it)
										model.selectedBuild.gems.gloves[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model.selectedBuild, temp))
								}
							}
							label("Boots")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.boots.size > it)
										model.selectedBuild.gems.boots[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemEditorPane(model.selectedBuild, temp))
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
			LOGGER.info("Closing Build: ${model.selectedBuild.display()}")
			find<BuildViewer>().openWindow(owner = null, resizable = false)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildEditor::class.java)
	}
}