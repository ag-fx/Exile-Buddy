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
	weapons: List<Gem>,
	armour: List<Gem>,
	helmet: List<Gem>,
	gloves: List<Gem>,
	boots: List<Gem>,
	updates: List<Update>
) {
	val weaponsProperty = SimpleListProperty<Gem>()
	var weapons by weaponsProperty

	val armourProperty = SimpleListProperty<Gem>()
	var armour by armourProperty

	val helmetProperty = SimpleListProperty<Gem>()
	var helmet by helmetProperty

	val glovesProperty = SimpleListProperty<Gem>()
	var gloves by glovesProperty

	val bootsProperty = SimpleListProperty<Gem>()
	var boots by bootsProperty

	val updatesProperty = SimpleListProperty<Update>()
	var updates by updatesProperty

	init {
		this.weapons = FXCollections.observableList(weapons)
		this.armour = FXCollections.observableList(armour)
		this.helmet = FXCollections.observableList(helmet)
		this.gloves = FXCollections.observableList(gloves)
		this.boots = FXCollections.observableList(boots)
		this.updates = FXCollections.observableList(updates)
	}

	fun getUsedGems(): List<Gem> {
		val used = ArrayList<Gem>()
		used.addAll(weapons.filterNot { it == Util.MISSING_GEM })
		used.addAll(armour.filterNot { it == Util.MISSING_GEM })
		used.addAll(helmet.filterNot { it == Util.MISSING_GEM })
		used.addAll(gloves.filterNot { it == Util.MISSING_GEM })
		used.addAll(boots.filterNot { it == Util.MISSING_GEM })
		used.addAll(updates.map { it.old }.filterNot { it == Util.MISSING_GEM })
		used.addAll(updates.map { it.new }.filterNot { it == Util.MISSING_GEM })
		return used
	}

	override fun toString(): String {
		return "BuildGems(weaponsProperty=$weaponsProperty, armourProperty=$armourProperty, helmetProperty=$helmetProperty, glovesProperty=$glovesProperty, bootsProperty=$bootsProperty, updatesProperty=$updatesProperty)"
	}
}

class BuildGemsDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<BuildGems?>(vc) {
	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext?): BuildGems? {
		val node: JsonNode = parser.codec.readTree(parser)

		val weapons = node["Weapons"]?.map { Util.gemByName(it.asText()) }?.chunked(6)?.firstOrNull() ?: emptyList()
		val armour = node["Armour"]?.map { Util.gemByName(it.asText()) }?.chunked(6)?.firstOrNull() ?: emptyList()
		val helmet = node["Helmet"]?.map { Util.gemByName(it.asText()) }?.chunked(4)?.firstOrNull() ?: emptyList()
		val gloves = node["Gloves"]?.map { Util.gemByName(it.asText()) }?.chunked(4)?.firstOrNull() ?: emptyList()
		val boots = node["Boots"]?.map { Util.gemByName(it.asText()) }?.chunked(4)?.firstOrNull() ?: emptyList()
		val updates = node["Updates"]?.map { Util.YAML_MAPPER.treeToValue(it, Update::class.java) } ?: emptyList()

		return BuildGems(
			weapons = weapons,
			armour = armour,
			helmet = helmet,
			gloves = gloves,
			boots = boots,
			updates = updates
		)
	}
}

class BuildGemsSerializer @JvmOverloads constructor(t: Class<BuildGems>? = null) : StdSerializer<BuildGems>(t) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun serialize(value: BuildGems, parser: JsonGenerator, provider: SerializerProvider?) {
		parser.writeStartObject()
		parser.writeObjectField("Weapons", value.weapons.map { it.getFullname() })
		parser.writeObjectField("Armour", value.armour.map { it.getFullname() })
		parser.writeObjectField("Helmet", value.helmet.map { it.getFullname() })
		parser.writeObjectField("Gloves", value.gloves.map { it.getFullname() })
		parser.writeObjectField("Boots", value.boots.map { it.getFullname() })
		parser.writeObjectField("Updates", value.updates)
		parser.writeEndObject()
	}
}
