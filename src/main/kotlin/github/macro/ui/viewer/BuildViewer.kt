package github.macro.ui.viewer

import github.macro.Styles
import github.macro.Util
import github.macro.Util.cleanName
import github.macro.ui.UIController
import github.macro.ui.UIModel
import javafx.geometry.Pos
import javafx.scene.control.ScrollPane
import javafx.scene.control.TabPane
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class BuildViewer : View("Exile Buddy") {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	override val root = borderpane {
		prefWidth = 700.0
		prefHeight = 750.0
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				label(text = model.selectedBuild.name) {
					addClass(Styles.subtitle)
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					paddingAll = 5.0
					separator {
						isVisible = false
					}
					label(text = model.selectedBuild.version) {
						addClass(Styles.subtitle)
					}
					separator()
					label(text = "${model.selectedBuild.classTag.cleanName()}/${model.selectedBuild.ascendency.cleanName()}") {
						addClass(Styles.subtitle)
					}
					separator {
						isVisible = false
					}
					button(text = "Copy") {
						addClass(Styles.sizedButton)
						action {
							controller.copyBuild(oldView = this@BuildViewer)
						}
					}
					button(text = "Edit") {
						addClass(Styles.sizedButton)
						action {
							controller.editBuild(oldView = this@BuildViewer)
						}
					}
					button(text = "Delete") {
						addClass(Styles.sizedButton)
						action {
							controller.deleteBuild(oldView = this@BuildViewer)
						}
					}
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
									add(GemViewerPane(model.selectedBuild, temp))
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
									add(GemViewerPane(model.selectedBuild, temp))
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
									add(GemViewerPane(model.selectedBuild, temp))
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
									add(GemViewerPane(model.selectedBuild, temp))
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
			controller.selectBuild(this@BuildViewer)
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildViewer::class.java)
	}
}