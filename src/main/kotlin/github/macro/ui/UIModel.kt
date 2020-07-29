package github.macro.ui

import github.macro.Util
import github.macro.build_info.Ascendency
import github.macro.build_info.Build
import github.macro.build_info.ClassTag
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13
 */
class UIModel : ViewModel() {
	val buildsProperty = SimpleListProperty<Build>()
	var builds by buildsProperty

	val classesProperty = SimpleListProperty<ClassTag>()
	var classes by classesProperty

	val ascendenciesProperty = SimpleListProperty<Ascendency>()
	var ascendencies by ascendenciesProperty

	val selectedBuildProperty = SimpleObjectProperty<Build>()
	var selectedBuild by selectedBuildProperty

	init {
		this.builds = FXCollections.observableArrayList()
		this.classes = FXCollections.observableList(ClassTag.values().toList())
		this.ascendencies = FXCollections.observableArrayList()
		this.selectedBuild = null
		LOGGER.info("Initialize Model")
	}

	fun loadBuilds() {
		builds.clear()
		val folder = File("builds")
		if (!folder.exists())
			folder.mkdirs()
		folder.listFiles()?.filterNot { it.isDirectory }?.mapNotNullTo(builds) {
			try {
				Util.YAML_MAPPER.readValue(it, Build::class.java)
			} catch (ioe: IOException) {
				LOGGER.error("Unable to Load Build: ${it.nameWithoutExtension} | $ioe")
				null
			}
		}
		builds.sortWith(compareBy(Build::version, Build::name))
	}

	fun selectedClass(classTag: ClassTag) {
		ascendencies.clear()
		ascendencies.addAll(Ascendency.values(classTag))
	}

	companion object {
		val LOGGER = LogManager.getLogger(UIModel::class.java)
	}
}