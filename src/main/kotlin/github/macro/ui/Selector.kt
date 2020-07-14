package github.macro.ui

import github.macro.Util
import github.macro.build_info.Ascendency
import github.macro.build_info.Build
import github.macro.build_info.BuildGems
import github.macro.build_info.ClassTag
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.layout.Priority
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Selector : View() {
	private val builds = FXCollections.observableArrayList<Build>()
	private val ascendencyList = FXCollections.observableArrayList<Ascendency>()

	override val root = borderpane {
		paddingAll = 10.0
		top {
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
			}
		}
		center {
			vbox(spacing = 5.0, alignment = Pos.CENTER) {
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					val buildCombobox = combobox<Build>(values = builds) {
						promptText = "Build"
						hgrow = Priority.ALWAYS
						maxWidth = Double.MAX_VALUE
						cellFormat {
							text = it.display()
						}
					}
					button(text = "Select") {
						minWidth = 100.0
						action {
							LOGGER.info("Viewing Build: ${buildCombobox.selectedItem?.display()}")
							val scope = Scope()
							setInScope(UIModel(buildCombobox.selectedItem), scope)
							find<Viewer>(scope).openWindow(owner = null, resizable = false)
							close()
						}
						disableWhen {
							buildCombobox.valueProperty().isNull
						}
					}
				}
				hbox(spacing = 5.0, alignment = Pos.CENTER) {
					val versionTextfield = textfield {
						promptText = "PoE Version"
					}
					val nameTextfield = textfield {
						promptText = "Build Name"
						hgrow = Priority.ALWAYS
					}
					val classCombobox = combobox(values = ClassTag.values().asList()) {
						promptText = "Class"
					}
					val ascendencyCombobox = combobox(values = ascendencyList) {
						promptText = "Ascendency"
						disableWhen {
							classCombobox.valueProperty().isNull
						}
					}
					classCombobox.setOnAction {
						ascendencyCombobox.selectionModel.clearSelection()
						if (classCombobox.value != null)
							ascendencyList.setAll(Ascendency.values(classCombobox.value))
					}
					button(text = "Create") {
						minWidth = 100.0
						action {
							val info = Build(
								version = versionTextfield.text,
								name = nameTextfield.text,
								classTag = classCombobox.selectedItem!!,
								ascendency = ascendencyCombobox.selectedItem!!,
								buildGems = BuildGems(
									armourLinks = emptyList(),
									helmetLinks = emptyList(),
									gloveLinks = emptyList(),
									bootLinks = emptyList(),
									weapon1Links = Util.getClassGems(classTag = classCombobox.selectedItem!!),
									weapon2Links = emptyList(),
									updates = emptyList()
								),
								equipment = emptyList()
							)
							LOGGER.info("Creating Build: ${info.display()}")
							info.save()
							val scope = Scope()
							setInScope(UIModel(info), scope)
							find<Viewer>(scope).openWindow(owner = null, resizable = false)
							close()
						}
						disableWhen {
							versionTextfield.textProperty().isEmpty
								.or(nameTextfield.textProperty().length().lessThanOrEqualTo(3))
								.or(classCombobox.valueProperty().isNull)
								.or(ascendencyCombobox.valueProperty().isNull)
						}
					}
				}
			}
		}
	}

	init {
		val folder = File("builds")
		if (!folder.exists())
			folder.mkdirs()
		folder.walkTopDown().forEach {
			if (it.isDirectory)
				return@forEach
			try {
				builds.add(Util.YAML_MAPPER.readValue(it, Build::class.java))
			} catch (ioe: IOException) {
				LOGGER.error("Unable to Load Build: ${it.nameWithoutExtension} | $ioe")
			}
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Selector::class.java)
	}
}