package github.macro.ui

import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildEditor : View() {
	private val model: UIModel by inject()
	var selected = model.buildProperty.value!!

	override val root = borderpane {
		prefWidth = 700.0
		prefHeight = 750.0
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
				vbox(spacing = 5.0, alignment = Pos.CENTER) {
					label(text = selected.display())
					hbox(spacing = 5.0, alignment = Pos.CENTER) {
						button(text = "Save") {
							action {
								LOGGER.info("Closing Build: ${selected.display()}")
								find<Viewer>().openWindow(owner = null, resizable = false)
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
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.armourLinks.forEach {
									add(GemPane(selected, it))
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.helmetLinks.forEach {
									add(GemPane(selected, it))
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.gloveLinks.forEach {
									add(GemPane(selected, it))
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.bootLinks.forEach {
									add(GemPane(selected, it))
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.weapon1Links.forEach {
									add(GemPane(selected, it))
								}
							}
							hbox(spacing = 5.0, alignment = Pos.CENTER_LEFT) {
								selected.buildGems.weapon2Links.forEach {
									add(GemPane(selected, it))
								}
							}
						}
					}
				}
				tab(text = "Equipment") {
					isDisable = selected.equipment.isEmpty()
					scrollpane(fitToWidth = true) {
						hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
						vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
							selected.equipmentProperty.forEach { equipment ->
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
			LOGGER.info("Closing Build: ${selected.display()}")
			find<Viewer>().openWindow(owner = null, resizable = false)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildEditor::class.java)
	}
}