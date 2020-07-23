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
class BuildViewer : View() {
	private val model: UIModel by inject()

	override val root = borderpane {
		prefWidth = 650.0
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
									name = model.selectedBuild.name + " Copy",
									classTag = model.selectedBuild.classTag,
									ascendency = model.selectedBuild.ascendency,
									buildGems = model.selectedBuild.buildGems,
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
								model.selectedBuild.delete()
								LOGGER.info("Deleting Build: ${model.selectedBuild.display()}")
								model.selectedBuild = null
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
							label("Armour Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 6).forEach {
									val temp = if (model.selectedBuild.buildGems.armourLinks.size > it)
										model.selectedBuild.buildGems.armourLinks[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Helmet Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.buildGems.helmetLinks.size > it)
										model.selectedBuild.buildGems.helmetLinks[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Glove Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.buildGems.gloveLinks.size > it)
										model.selectedBuild.buildGems.gloveLinks[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Boot Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 4).forEach {
									val temp = if (model.selectedBuild.buildGems.bootLinks.size > it)
										model.selectedBuild.buildGems.bootLinks[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Weaspon 1 Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 3).forEach {
									val temp = if (model.selectedBuild.buildGems.weapon1Links.size > it)
										model.selectedBuild.buildGems.weapon1Links[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
							label("Weapon 2 Gems")
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								paddingAll = 5.0
								(0 until 3).forEach {
									val temp = if (model.selectedBuild.buildGems.weapon2Links.size > it)
										model.selectedBuild.buildGems.weapon2Links[it] ?: Util.missingGem
									else Util.missingGem
									add(GemViewerPane(model.selectedBuild, temp))
								}
							}
						}
					}
				}
				tab(text = "Equipment") {
					isDisable = model.selectedBuild.equipment.isEmpty()
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							paddingAll = 5.0
							model.selectedBuild.equipmentProperty.forEach { equipment ->
								gridpane {
									row {
										label(text = equipment.name) {
											alignment = Pos.CENTER
											gridpaneConstraints {
												columnSpan = 3
											}
										}
									}
									row {
										label(text = equipment.slot.name)
										label(text = equipment.level?.toString() ?: "Missing")
										label(text = equipment.quality?.toString() ?: "Missing")
									}
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