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
import github.macro.build_info.gems.GemBuild
import github.macro.build_info.gems.UpdateGem
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.getValue
import tornadofx.setValue
import java.io.File
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = BuildDeserializer::class)
@JsonSerialize(using = BuildSerializer::class)
class Build(
	name: String,
	classTag: ClassTag,
	ascendency: Ascendency,
	gemBuild: GemBuild,
	equipment: List<EquipmentInfo>
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val classProperty = SimpleObjectProperty<ClassTag>()
	var classTag by classProperty

	val ascendencyProperty = SimpleObjectProperty<Ascendency>()
	var ascendency by ascendencyProperty

	val gemBuildProperty = SimpleObjectProperty<GemBuild>()
	var gemBuild by gemBuildProperty

	val equipmentProperty = SimpleListProperty<EquipmentInfo>()
	var equipment by equipmentProperty

	init {
		this.name = name
		this.classTag = classTag
		this.ascendency = ascendency
		this.gemBuild = gemBuild
		this.equipment = FXCollections.observableList(equipment)
	}

	override fun toString(): String {
		return "BuildInfo(name='$name', class=$classTag, ascendency=$ascendency, gemBuild=$gemBuild, equipment=$equipment)"
	}

	fun display(): String = "$name [$classTag/$ascendency]"

	fun save() {
		val folder = File("builds")
		if (!folder.exists())
			folder.mkdirs()
		try {
			val buildFile = File(folder, name.replace(" ", "_") + ".yaml")
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

		val name = node["name"].asText()
		val classTag = ClassTag.value(node["class"].asText()) ?: return null
		val ascendency = Ascendency.value(node["ascendency"].asText()) ?: return null
		val links = node["gems"]["links"].map { link -> link.map { Util.gemByName(it.asText()) } }
		val updates = node["gems"]["updates"].map {
			val oldGem = Util.gemByName(it["oldGem"].asText())
			val newGem = Util.gemByName(it["newGem"].asText())
			val reason = it["reason"].asText()
			UpdateGem(oldGem, newGem, reason)
		}
		val equipment = node["equipment"].mapNotNull { Util.equipmentByName(it.asText()) }

		return Build(
			name, classTag, ascendency,
			GemBuild(links, updates), equipment
		)
	}
}

class BuildSerializer @JvmOverloads constructor(t: Class<Build>? = null) : StdSerializer<Build>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: Build, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeStringField("name", value.name)
		parser.writeStringField("class", value.classTag.name)
		parser.writeStringField("ascendency", value.ascendency.name)
		parser.writeObjectFieldStart("gems")
		value.gemBuild.let {
			parser.writeObjectField("links", it.links.map { link -> link.map { it?.getFullname() } })
			parser.writeArrayFieldStart("updates")
			it.updates.forEach { update ->
				parser.writeStartObject()
				parser.writeStringField("oldGem", update.oldGem?.getFullname())
				parser.writeStringField("newGem", update.newGem?.getFullname())
				parser.writeStringField("reason", update.reason)
				parser.writeEndObject()
			}
			parser.writeEndArray()
		}
		parser.writeEndObject()
		parser.writeObjectField("equipment", value.equipment.map { it.name })
		parser.writeEndObject()
	}
}