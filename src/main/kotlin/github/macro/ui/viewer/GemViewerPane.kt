package github.macro.ui.viewer

import github.macro.Util
import github.macro.build_info.Build
import github.macro.build_info.gems.Gem
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.layout.BorderPane
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.layout.Priority
import javafx.scene.paint.Paint
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.TextAlignment
import javafx.util.Duration
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File

/**
 * Created by Macro303 on 2020-Jan-14.
 */
class GemViewerPane(val build: Build, gem: Gem) : BorderPane() {
	val gemProperty = SimpleObjectProperty<Gem>()
	var gem by gemProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val colourProperty = SimpleObjectProperty<Paint>()
	var colour by colourProperty

	val backVisibilityProperty = SimpleBooleanProperty()
	var backVisibility by backVisibilityProperty

	val newVisibilityProperty = SimpleBooleanProperty()
	var newVisibility by newVisibilityProperty

	val reasonProperty = SimpleStringProperty()
	var reason by reasonProperty

	init {
		setNewGem(gem)
		initialize()
	}

	fun setNewGem(newGem: Gem) {
		gemProperty.value = newGem

		style {
			borderColor += box(c(Util.slotToColour(gem.slot)))
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

		val imageFile = File("resources/Gems", gem.getFilename())
		imageUrlProperty.value = "file:${imageFile.path}"

		nameProperty.value = gem.getFullname()

		colourProperty.value = Paint.valueOf(Util.slotToColour(gem.slot))

		backVisibilityProperty.value = build.gems.updates.any {
			it.new == gem && it.new != Util.MISSING_GEM
		}

		newVisibilityProperty.value = build.gems.updates.any {
			it.old == gem && it.old != Util.MISSING_GEM
		}

		reasonProperty.value = build.gems.updates.firstOrNull {
			it.old == gem && it.old != Util.MISSING_GEM
		}?.reason
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemViewerPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				imageview(imageUrlProperty, lazyload = true) {
					fitHeight = 80.0
					fitWidth = 80.0
				}
			}
		}
		center {
			label(nameProperty) {
				isWrapText = true
				prefWidth = 90.0
				alignment = Pos.CENTER
				textAlignment = TextAlignment.CENTER
				textFillProperty().bind(colourProperty)
			}
		}
		bottom {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				button(text = "<<") {
					visibleWhen(backVisibilityProperty)
					isFocusTraversable = false
					action {
						setNewGem(
							build.gems.updates.firstOrNull { it.new?.equals(gem) ?: false }?.old
								?: Util.MISSING_GEM
						)
					}
				}
				separator {
					isVisible = false
					hgrow = Priority.ALWAYS
				}
				button(text = ">>") {
					visibleWhen(newVisibilityProperty)
					isFocusTraversable = false
					action {
						setNewGem(
							build.gems.updates.firstOrNull { it.old?.equals(gem) ?: false }?.new
								?: Util.MISSING_GEM
						)
					}
					tooltip(reason) {
						style {
							fontSize = 12.px
						}
						showDelay = Duration(0.0)
						hideDelay = Duration(0.0)
						textProperty().bind(reasonProperty)
					}
				}
			}
		}
	}
}
