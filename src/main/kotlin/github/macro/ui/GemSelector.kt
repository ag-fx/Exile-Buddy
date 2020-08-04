package github.macro.ui

import github.macro.Styles
import github.macro.Util
import github.macro.build_info.gems.Gem
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Aug-04.
 */
class GemSelector : View() {
	private val controller by inject<UIController>()
	private val model by inject<UIModel>()

	init {
		model.selectedGem = Util.MISSING_GEM
	}

	override val root = borderpane {
		paddingAll = 10.0
		top {
			paddingAll = 5.0
			vbox(spacing = 5.0, alignment = Pos.TOP_CENTER) {
				paddingAll = 5.0
				label(text = "Exile Buddy") {
					addClass(Styles.title)
				}
			}
		}
		center {
			hbox(spacing = 5.0, alignment = Pos.CENTER) {
				paddingAll = 5.0
				val gemCombobox = combobox<Gem>(values = model.gems.filterNot {
					model.selectedBuild.gems.getUsedGems().contains(it)
				}) {
					promptText = "Select Gem"
					hgrow = Priority.ALWAYS
					cellFormat {
						text = it.getFullname()
					}
				}
				button("Select") {
					isDefaultButton = true
					action {
						model.selectedGem = gemCombobox.selectedItem
						close()
					}
				}
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemSelector::class.java)
	}
}