package github.macro

import github.macro.config.Config
import github.macro.ui.Selector
import javafx.scene.text.FontWeight
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.util.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Launcher : App(Selector::class, Styles::class) {
	init {
		checkLogLevels()
		LOGGER.info("Welcome to Exile Buddy")
		if (Config.INSTANCE.useDarkMode) {
			importStylesheet(Launcher::class.java.getResource("Modena-Dark.css").toExternalForm())
//		    importStylesheet(Launcher::class.java.getResource("Custom-Dark.css").toExternalForm())
		}
		FX.locale = Locale.ENGLISH
		reloadStylesheetsOnFocus()
	}

	private fun checkLogLevels() {
		Level.values().sorted().forEach {
			LOGGER.log(it, "{} is Visible", it.name())
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Launcher::class.java)
	}
}

class Styles : Stylesheet() {
	init {
		title {
			fontSize = 35.pt
			fontWeight = FontWeight.BOLD
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Styles::class.java)
		val title by cssid()
	}
}