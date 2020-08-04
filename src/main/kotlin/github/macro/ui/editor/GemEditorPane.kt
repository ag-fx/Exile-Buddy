package github.macro.ui.editor

import github.macro.Util
import github.macro.build_info.Update
import github.macro.build_info.gems.Gem
import github.macro.ui.GemSelector
import github.macro.ui.UIModel
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
class GemEditorPane(val model: UIModel, gem: Gem) : BorderPane() {
	val gemProperty = SimpleObjectProperty<Gem>()
	var gem by gemProperty

	val imageUrlProperty = SimpleStringProperty()
	var imageUrl by imageUrlProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val colourProperty = SimpleObjectProperty<Paint>()
	var colour by colourProperty

	val previousProperty = SimpleBooleanProperty()
	var previous by previousProperty

	val nextProperty = SimpleBooleanProperty()
	var next by nextProperty

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

		previousProperty.value = model.selectedBuild.gems.updates.any {
			(it.new == gem) && it.new != Util.MISSING_GEM
		}

		nextProperty.value = model.selectedBuild.gems.updates.any {
			it.old == gem && it.old != Util.MISSING_GEM
		}

		reasonProperty.value = model.selectedBuild.gems.updates.firstOrNull {
			it.old == gem && it.old != Util.MISSING_GEM
		}?.reason
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemEditorPane::class.java)
	}

	private fun initialize() {
		paddingAll = 5.0
		top {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
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
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					button(text = "<<") {
						visibleWhen(previousProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.new?.equals(gem) ?: false }?.old
									?: Util.MISSING_GEM
							)
						}
					}
					separator {
						isVisible = false
						hgrow = Priority.ALWAYS
					}
					button(text = ">>") {
						visibleWhen(nextProperty)
						isFocusTraversable = false
						action {
							setNewGem(
								model.selectedBuild.gems.updates.firstOrNull { it.old?.equals(gem) ?: false }?.new
									?: Util.MISSING_GEM
							)
						}
						tooltip(reason) {
							style {
								fontSize = 14.px
							}
							showDelay = Duration(0.0)
							hideDelay = Duration(0.0)
							textProperty().bind(reasonProperty)
						}
					}
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					button("Add") {
						visibleWhen(nextProperty.not().and(gemProperty.isEqualTo(Util.MISSING_GEM).not()))
						action {
							val scope = Scope()
							setInScope(model, scope)
							find<GemSelector>(scope).openModal(block = true, resizable = false)
							if (model.selectedGem != Util.MISSING_GEM) {
								model.selectedBuild.gems.updates.add(
									Update(gem, model.selectedGem, "Reason not Implemented")
								)
								setNewGem(model.selectedGem)
							}
						}
					}
					button("Edit") {
						disableWhen {
							true.toProperty()
						}
						action {
							val scope = Scope()
							setInScope(model, scope)
							find<GemSelector>(scope).openModal(block = true, resizable = false)
							if (model.selectedGem != Util.MISSING_GEM) {
								model.selectedBuild.gems.weapons.replaceAll {
									if (it == gem)
										return@replaceAll model.selectedGem
									return@replaceAll it
								}
								model.selectedBuild.gems.armour.replaceAll {
									if (it == gem)
										return@replaceAll model.selectedGem
									return@replaceAll it
								}
								model.selectedBuild.gems.helmet.replaceAll {
									if (it == gem)
										return@replaceAll model.selectedGem
									return@replaceAll it
								}
								model.selectedBuild.gems.gloves.replaceAll {
									if (it == gem)
										return@replaceAll model.selectedGem
									return@replaceAll it
								}
								model.selectedBuild.gems.boots.replaceAll {
									if (it == gem)
										return@replaceAll model.selectedGem
									return@replaceAll it
								}
								model.selectedBuild.gems.updates.filter { it.old == gem }
									.forEach { it.old = model.selectedGem }
								model.selectedBuild.gems.updates.filter { it.new == gem }
									.forEach { it.new = model.selectedGem }
								setNewGem(model.selectedGem)
							}
						}
					}
					button("Del") {
						visibleWhen(previousProperty.or(nextProperty))
						action {
							var update = model.selectedBuild.gems.updates.firstOrNull { it.new == gem }
							if (update != null) {
								LOGGER.info("Found New Update: $update")
								model.selectedBuild.gems.updates.remove(update)
								val refresh = model.selectedBuild.gems.updates.firstOrNull { it.old == gem }
								if (refresh != null)
									refresh.old = update.old
								setNewGem(update.old)
							} else {
								update = model.selectedBuild.gems.updates.firstOrNull { it.old == gem }
								if (update != null) {
									LOGGER.info("Found Old Update: $update")
									model.selectedBuild.gems.updates.remove(update)
									val refresh = model.selectedBuild.gems.updates.firstOrNull { it.new == gem }
									if (refresh != null)
										refresh.new = update.new
									setNewGem(update.new)
								}
							}
						}
					}
				}
			}
		}
	}
}
