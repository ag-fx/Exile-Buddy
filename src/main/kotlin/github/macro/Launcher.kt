package github.macro

import github.macro.config.Config
import github.macro.ui.Selector
import javafx.scene.image.Image
import javafx.scene.text.Font
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
//			importStylesheet(Launcher::class.java.getResource("Modena-Dark.css").toExternalForm())
			importStylesheet(Launcher::class.java.getResource("Custom-Dark.css").toExternalForm())
		}
		FX.locale = Locale.ENGLISH
		reloadStylesheetsOnFocus()
		addStageIcon(Image(Launcher::class.java.getResource("logo.png").toExternalForm()))
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
	private val fontBold = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-Bold.otf").openStream(), 12.0)
	private val fontRegular = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-Regular.otf").openStream(), 12.0)
	private val fontSmallCaps = Font.loadFont(Launcher::class.java.getResource("fonts/Fontin-SmallCaps.otf").openStream(), 12.0)

	init {
		root {
			fontFamily = fontRegular.family
			fontSize = 14.px
		}
		button {
			fontFamily = fontSmallCaps.family
			fontSize = 14.px
		}
		title {
			fontFamily = fontBold.family
			fontSize = 36.px
			fontWeight = FontWeight.BOLD
		}
		subtitle {
			fontFamily = fontBold.family
			fontSize = 21.px
			fontWeight = FontWeight.BOLD
		}
		sizedButton {
			prefWidth = 100.px
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Styles::class.java)

		val title by cssclass()
		val subtitle by cssclass()
		val sizedButton by cssclass()
	}
}