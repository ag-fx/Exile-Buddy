package github.macro

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import github.macro.build_info.ClassTag
import github.macro.build_info.ClassTag.*
import github.macro.build_info.equipment.Equipment
import github.macro.build_info.gems.Acquisition
import github.macro.build_info.gems.Gem
import github.macro.config.Config
import org.apache.logging.log4j.LogManager
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
object Util {
	private val LOGGER = LogManager.getLogger(Util::class.java)
	private val HEADERS = mapOf(
		"Accept" to "application/json",
		"Content-Type" to "application/json",
		"User-Agent" to "Exile Buddy"
	)
	internal val JSON_MAPPER: ObjectMapper by lazy {
		val mapper = ObjectMapper()
		mapper.enable(SerializationFeature.INDENT_OUTPUT)
		mapper.findAndRegisterModules()
		mapper.registerModule(Jdk8Module())
		mapper
	}
	internal val YAML_MAPPER: ObjectMapper by lazy {
		val mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
		mapper.enable(SerializationFeature.INDENT_OUTPUT)
		mapper.findAndRegisterModules()
		mapper.registerModule(Jdk8Module())
		mapper
	}
	val GEM_LIST: List<Gem> by lazy {
		try {
			JSON_MAPPER.readValue(File("resources/Gems", "Gems.json"), object : TypeReference<List<Gem>>() {})
		} catch (ioe: IOException) {
			LOGGER.error("Unable to Load Gems: $ioe")
			emptyList<Gem>()
		}
	}
	val MISSING_GEM = Gem(
		name = "Missing",
		slot = github.macro.build_info.gems.Slot.ERROR,
		tags = emptyList(),
		isVaal = false,
		isAwakened = false,
		acquisition = Acquisition(emptyList(), emptyList())
	)
	val EQUIPMENT_LIST: List<Equipment> by lazy {
		try {
			JSON_MAPPER.readValue(
				File("resources/Equipment", "Equipment.json"),
				object : TypeReference<List<Equipment>>() {})
		} catch (ioe: IOException) {
			LOGGER.error("Unable to Load Equipment: $ioe")
			emptyList<Equipment>()
		}
	}
	val MISSING_EQUIPMENT = Equipment(
		name = "Missing",
		slot = github.macro.build_info.equipment.Slot.ERROR,
		level = 0,
		quality = 0.0
	)

	fun slotToColour(slot: github.macro.build_info.gems.Slot?): String = when (slot) {
		github.macro.build_info.gems.Slot.RED -> "#C44C4C"
		github.macro.build_info.gems.Slot.GREEN -> "#4CC44C"
		github.macro.build_info.gems.Slot.BLUE -> "#4C4CC4"
		github.macro.build_info.gems.Slot.WHITE -> if (Config.INSTANCE.useDarkMode) "#C4C4C4" else "#4C4C4C"
		else -> if (Config.INSTANCE.useDarkMode) "#4C4C4C" else "#C4C4C4"
	}

	fun gemByName(name: String?): Gem {
		name ?: return MISSING_GEM
		return GEM_LIST.firstOrNull {
			if (it.isVaal)
				return@firstOrNull "Vaal ${it.name}".equals(name, ignoreCase = true)
			if (it.isAwakened)
				return@firstOrNull "Awakened ${it.name}".equals(name, ignoreCase = true)
			return@firstOrNull it.name.equals(name, ignoreCase = true)
		} ?: MISSING_GEM
	}

	fun equipmentByName(name: String?): Equipment {
		name ?: return MISSING_EQUIPMENT
		return EQUIPMENT_LIST.firstOrNull {
			it.name.equals(name, ignoreCase = true)
		} ?: MISSING_EQUIPMENT
	}

	internal fun getClassGems(classTag: ClassTag): List<Gem> = when (classTag) {
		SCION -> listOf("Spectral Throw", "Onslaught Support")
		MARAUDER -> listOf("Heavy Strike", "Ruthless Support")
		RANGER -> listOf("Burning Arrow", "Pierce Support")
		WITCH -> listOf("Fireball", "Arcane Surge Support")
		DUELIST -> listOf("Double Strike", "Chance to Bleed Support")
		TEMPLAR -> listOf("Glacial Hammer", "Elemental Proliferation Support")
		SHADOW -> listOf("Viper Strike", "Lesser Poison Support")
	}.plus("Empower Support").map { gemByName(it) }

	fun Enum<*>.cleanName(): String = this.name.split("_").joinToString(" ") { it.toLowerCase().capitalize() }
}