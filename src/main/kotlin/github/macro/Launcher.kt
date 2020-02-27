package github.macro

import github.macro.ui.Selector
import javafx.scene.text.FontWeight
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-13.
 */
class Launcher : App(Selector::class, Styles::class) {
	init {
		checkLogLevels()
		LOGGER.info("Welcome to Exile Buddy")
		Util.gems.forEach {
//			if (it.acquisition.cards.size == 0 || it.acquisition.rewards.size == 0)
//				LOGGER.info("Missing Acquisition for: ${it.getDisplay()}")
			LOGGER.info(it)
		}
		importStylesheet(Launcher::class.java.getResource("Dark-Theme.css").toExternalForm())
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