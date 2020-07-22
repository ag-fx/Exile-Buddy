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
import github.macro.build_info.gems.Gem
import github.macro.build_info.gems.UpdateGem
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-23.
 */
@JsonDeserialize(using = BuildGemsDeserializer::class)
@JsonSerialize(using = BuildGemsSerializer::class)
class BuildGems(
	armourLinks: List<Gem?>,
	helmetLinks: List<Gem?>,
	gloveLinks: List<Gem?>,
	bootLinks: List<Gem?>,
	weapon1Links: List<Gem?>,
	weapon2Links: List<Gem?>,
	updates: List<UpdateGem>
) {
	val armourLinksProperty = SimpleListProperty<Gem?>()
	var armourLinks by armourLinksProperty

	val helmetLinksProperty = SimpleListProperty<Gem?>()
	var helmetLinks by helmetLinksProperty

	val gloveLinksProperty = SimpleListProperty<Gem?>()
	var gloveLinks by gloveLinksProperty

	val bootLinksProperty = SimpleListProperty<Gem?>()
	var bootLinks by bootLinksProperty

	val weapon1LinksProperty = SimpleListProperty<Gem?>()
	var weapon1Links by weapon1LinksProperty

	val weapon2LinksProperty = SimpleListProperty<Gem?>()
	var weapon2Links by weapon2LinksProperty

	val updatesProperty = SimpleListProperty<UpdateGem>()
	var updates by updatesProperty

	init {
		this.armourLinks = FXCollections.observableList(armourLinks)
		this.helmetLinks = FXCollections.observableList(helmetLinks)
		this.gloveLinks = FXCollections.observableList(gloveLinks)
		this.bootLinks = FXCollections.observableList(bootLinks)
		this.weapon1Links = FXCollections.observableList(weapon1Links)
		this.weapon2Links = FXCollections.observableList(weapon2Links)
		this.updates = FXCollections.observableList(updates)
	}

	override fun toString(): String {
		return "GemBuild(armourLinks=$armourLinks, helmetLinks=$helmetLinks, gloveLinks=$gloveLinks, bootLinks=$bootLinks, weapon1Links=$weapon1Links, weapon2Links=$weapon2Links, updates=$updates)"
	}
}

class BuildGemsDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BuildGems?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGems? {
		val node: JsonNode = parser.codec.readTree(parser)

		val amourLinks = node["Armour"].map { Util.gemByName(it.asText()) }.chunked(6).firstOrNull() ?: emptyList()
		val helmetLinks = node["Helmet"].map { Util.gemByName(it.asText()) }.chunked(4).firstOrNull() ?: emptyList()
		val gloveLinks = node["Gloves"].map { Util.gemByName(it.asText()) }.chunked(4).firstOrNull() ?: emptyList()
		val bootLinks = node["Boots"].map { Util.gemByName(it.asText()) }.chunked(4).firstOrNull() ?: emptyList()
		val weapon1Links = node["Weapon 1"].map { Util.gemByName(it.asText()) }.chunked(3).firstOrNull() ?: emptyList()
		val weapon2Links = node["Weapon 2"].map { Util.gemByName(it.asText()) }.chunked(3).firstOrNull() ?: emptyList()
		val updates = node["Updates"].map {
			val oldGem = Util.gemByName(it["Old Gem"].asText())
			val newGem = Util.gemByName(it["New Gem"].asText())
			val reason = it["Reason"].asText()
			UpdateGem(oldGem, newGem, reason)
		}

		return BuildGems(
			armourLinks = amourLinks,
			helmetLinks = helmetLinks,
			gloveLinks = gloveLinks,
			bootLinks = bootLinks,
			weapon1Links = weapon1Links,
			weapon2Links = weapon2Links,
			updates = updates
		)
	}
}

class BuildGemsSerializer @JvmOverloads constructor(t: Class<BuildGems>? = null) : StdSerializer<BuildGems>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGems, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Armour", value.armourLinks.map { it?.getFullname() })
		parser.writeObjectField("Helmet", value.helmetLinks.map { it?.getFullname() })
		parser.writeObjectField("Gloves", value.gloveLinks.map { it?.getFullname() })
		parser.writeObjectField("Boots", value.bootLinks.map { it?.getFullname() })
		parser.writeObjectField("Weapon 1", value.weapon1Links.map { it?.getFullname() })
		parser.writeObjectField("Weapon 2", value.weapon2Links.map { it?.getFullname() })
		parser.writeArrayFieldStart("Updates")
		value.updates.forEach { update ->
			parser.writeStartObject()
			parser.writeStringField("Old Gem", update.oldGem?.getFullname())
			parser.writeStringField("New Gem", update.newGem?.getFullname())
			parser.writeStringField("Reason", update.reason)
			parser.writeEndObject()
		}
		parser.writeEndArray()
		parser.writeEndObject()
	}
}