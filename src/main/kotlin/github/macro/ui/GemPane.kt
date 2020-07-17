package github.macro.ui

import github.macro.Util
import github.macro.build_info.Build
import github.macro.build_info.gems.Gem
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemPane(val build: Build, var gem: Gem?) : BorderPane() {

	init {
		initialize()
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		style(append = true) {
			borderColor += box(c(Util.slotToColour(gem?.slot)))
			borderStyle += BorderStrokeStyle(
				StrokeType.CENTERED,
				StrokeLineJoin.ROUND,
				StrokeLineCap.ROUND,
				0.0,
				0.0,
				listOf(5.0, 5.0)
			)
			borderWidth += box(2.px)
		}

		top {
			hbox {
				val imageFile = File("resources/Gems", gem?.getFilename() ?: "Missing Gem")
				if (imageFile.exists())
					imageview("file:${imageFile.path}") {
						fitHeight = 80.0
						fitWidth = 80.0
						alignment = Pos.CENTER
					}
				else
					imageview(GemPane::class.java.getResource("placeholder[80x80].png").toExternalForm()) {
						fitHeight = 80.0
						fitWidth = 80.0
						alignment = Pos.CENTER
					}
			}
		}
		center {
			label(text = gem?.getFullname() ?: "Missing Gem") {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
				textFill = Paint.valueOf(Util.slotToColour(gem?.slot))
			}
		}
		bottom {
			hbox(spacing = 5.0) {
				button(text = "<<") {
					isVisible = build.buildGems.updates.any { it.newGem?.equals(gem) ?: false }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action {
						val oldGem = gem
						gem = build.buildGems.updates.firstOrNull { it.newGem?.equals(gem) ?: false }?.oldGem
						LOGGER.info(
							"Previous Selected, Updated {} to {}",
							oldGem?.getFullname() ?: "Missing Gem",
							gem?.getFullname() ?: "Missing Gem"
						)
						initialize()
					}
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				button(text = ">>") {
					isVisible = build.buildGems.updates.any { it.oldGem?.equals(gem) ?: false }
					hgrow = Priority.SOMETIMES
					isFocusTraversable = false
					action {
						val oldGem = gem
						gem = build.buildGems.updates.firstOrNull { it.oldGem?.equals(gem) ?: false }?.newGem
						LOGGER.info(
							"Next Selected, Updated {} to {}",
							oldGem?.getFullname() ?: "Missing Gem",
							gem?.getFullname() ?: "Missing Gem"
						)
						initialize()
					}
					tooltip(build.buildGems.updates.firstOrNull { it.oldGem?.equals(gem) ?: false }?.reason) {
						style {
							fontSize = 10.pt
						}
					}
					Util.hackTooltipStartTiming(tooltip)
				}
			}
		}
	}
}
