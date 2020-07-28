package github.macro.ui.viewer

import github.macro.Util
import github.macro.build_info.Build
import github.macro.ui.Selector
import github.macro.ui.UIModel
import github.macro.ui.editor.BuildEditor
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildViewer : View("Exile Buddy") {
	private val model: UIModel by inject()

	override val root = borderpane {
		prefWidth = 700.0
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
				label(text = "Exile Buddy") {
					id = "title"
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				vbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					label(text = model.selectedBuild.display())
					hbox(spacing = 5.0, alignment = Pos.CENTER) {
						paddingAll = 5.0
						button(text = "Copy") {
							action {
								val copiedBuild = Build(
									version = model.selectedBuild.version,
									isHardcore = model.selectedBuild.isHardcore,
									name = model.selectedBuild.name + " Copy",
									classTag = model.selectedBuild.classTag,
									ascendency = model.selectedBuild.ascendency,
									gems = model.selectedBuild.gems,
									equipment = model.selectedBuild.equipment
								)
								copiedBuild.save()
								LOGGER.info("Changing Build: ${model.selectedBuild.display()} => ${copiedBuild.display()}")
								val scope = Scope()
								model.selectedBuild = copiedBuild
								setInScope(model, scope)
								find<BuildViewer>(scope).openWindow(owner = null, resizable = false)
								close()
							}
						}
						button(text = "Edit") {
							action {
								LOGGER.info("Editing Build: ${model.selectedBuild.display()}")
								val scope = Scope()
								setInScope(model, scope)
								find<BuildEditor>(scope).openWindow(owner = null, resizable = false)
								close()
							}
						}
						button(text = "Delete") {
							action {
								val temp = model.selectedBuild
								model.selectedBuild = null
								temp.delete()
								LOGGER.info("Deleting Build: ${temp.display()}")
								find<Selector>().openWindow(owner = null, resizable = false)
								close()
							}
						}
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
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Armour")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.gems.armour.size > it)
										model.selectedBuild.gems.armour[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Helmet")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.helmet.size > it)
										model.selectedBuild.gems.helmet[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Gloves")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.gloves.size > it)
										model.selectedBuild.gems.gloves[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Boots")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.gems.boots.size > it)
										model.selectedBuild.gems.boots[it] ?: Util.MISSING_GEM
									else Util.MISSING_GEM
									add(GemViewerPane(model.selectedBuild, temp))
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
			find<Selector>().openWindow(owner = null, resizable = false)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildViewer::class.java)
	}
}