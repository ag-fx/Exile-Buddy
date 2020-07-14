package github.macro.build_info

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import github.macro.Util
import github.macro.build_info.equipment.EquipmentInfo
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = BuildDeserializer::class)
@JsonSerialize(using = BuildSerializer::class)
class Build(
	version: String,
	name: String,
	classTag: ClassTag,
	ascendency: Ascendency,
	buildGems: BuildGems,
	equipment: List<EquipmentInfo>
) {
	val versionProperty = SimpleStringProperty()
	var version by versionProperty

	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val classProperty = SimpleObjectProperty<ClassTag>()
	var classTag by classProperty

	val ascendencyProperty = SimpleObjectProperty<Ascendency>()
	var ascendency by ascendencyProperty

	val buildGemsProperty = SimpleObjectProperty<BuildGems>()
	var buildGems by buildGemsProperty

	val equipmentProperty = SimpleListProperty<EquipmentInfo>()
	var equipment by equipmentProperty

	init {
		this.version = version
		this.name = name
		this.classTag = classTag
		this.ascendency = ascendency
		this.buildGems = buildGems
		this.equipment = FXCollections.observableList(equipment)
	}

	override fun toString(): String {
		return "BuildInfo(version='$version', name='$name', class=$classTag, ascendency=$ascendency, gemBuild=$buildGems, equipment=$equipment)"
	}

	fun display(): String = "{$version} $name [$classTag/$ascendency]"

	fun save() {
		val folder = File("builds")
		if (!folder.exists())
			folder.mkdirs()
		try {
			val filename = "{$version} ${name.replace(" ", "_")}.yaml"
			val buildFile = File(folder, filename)
			Util.YAML_MAPPER.writeValue(buildFile, this)
		} catch (ioe: IOException) {
			LOGGER.error("Unable to save build: $ioe")
		}
	}

	companion object {
		private val LOGGER = LogManager.getLogger(Build::class.java)
	}
}

class BuildDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Build?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): Build? {
		val node: JsonNode = parser.codec.readTree(parser)

		val version = node["Version"].asText()
		val name = node["Name"].asText()
		val classTag = ClassTag.value(node["Class"].asText())
		if(classTag == null){
			LOGGER.info("Invalid Class: ${node["Class"].asText()}")
			return null
		}
		val ascendency = Ascendency.value(node["Ascendency"].asText())
		if(ascendency == null){
			LOGGER.info("Invalid Ascendency: ${node["Ascendency"].asText()}")
			return null
		}
		val buildGems = Util.YAML_MAPPER.treeToValue(node["Gems"], BuildGems::class.java)
		val equipment = node["Equipment"].mapNotNull { Util.equipmentByName(it.asText()) }

		return Build(
			version = version,
			name = name,
			classTag = classTag,
			ascendency = ascendency,
			buildGems = buildGems,
			equipment = equipment
		)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(BuildDeserializer::class.java)
	}
}

class BuildSerializer @JvmOverloads constructor(t: Class<Build>? = null) : StdSerializer<Build>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Build, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("Version", value.version)
		parser.writeStringField("Name", value.name)
		parser.writeStringField("Class", value.classTag.name)
		parser.writeStringField("Ascendency", value.ascendency.name)
		parser.writeObjectField("Gems", value.buildGems)
		parser.writeObjectField("Equipment", value.equipment.map { it.name })
		parser.writeEndObject()
	}
}