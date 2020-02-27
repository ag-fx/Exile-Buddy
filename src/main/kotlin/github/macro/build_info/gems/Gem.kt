package github.macro.build_info.gems

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.build_info.ClassTag
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import tornadofx.getValue
import tornadofx.setValue
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class Gem(
	name: String,
	slot: Slot,
	tags: List<GemTag>,
	isVaal: Boolean,
	isAwakened: Boolean,
	acquisition: Acquisition
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val slotProperty = SimpleObjectProperty<Slot>()
	var slot by slotProperty

	val tagsProperty = SimpleListProperty<GemTag>()
	var tags by tagsProperty

	val isVaalProperty = SimpleBooleanProperty()
	var isVaal by isVaalProperty

	val isAwakenedProperty = SimpleBooleanProperty()
	var isAwakened by isAwakenedProperty

	val acquisitionProperty = SimpleObjectProperty<Acquisition>()
	var acquisition by acquisitionProperty

	init {
		this.name = name
		this.slot = slot
		this.tags = FXCollections.observableList(tags)
		this.isVaal = isVaal
		this.isAwakened = isAwakened
		this.acquisition = acquisition
	}

	fun getFilename(): String {
		var output = name.replace(" ", "_")
		if (isVaal)
			output += "[Vaal]"
		if (isAwakened)
			output += "[Awakened]"
		return "$output.png"
	}

	fun getFullname(): String {
		var output = ""
		if (isVaal)
			output += "Vaal "
		if (isAwakened)
			output += "Awakened "
		return output + name
	}

	fun getDisplay(): String {
		var output = name
		if (isVaal)
			output += " [Vaal]"
		if (isAwakened)
			output += " [Awakened]"
		return output
	}

	override fun toString(): String {
		return "GemInfo(name='$name', slot=$slot, tags=$tags, isVaal=$isVaal, isAwakened=$isAwakened, acquisition=$acquisition)"
	}
}

class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Gem?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Gem? {
		val node: JsonNode = parser.readValueAsTree()

		val name = node["name"].asText()
		val slot = Slot.value(node["slot"].asText()) ?: return null
		val tags = node["tags"].mapNotNull { GemTag.value(it.asText()) }.sorted()
		val isVaal = if (node.has("isVaal")) node["isVaal"].asBoolean(false) else false
		val isAwakened = if (node.has("isAwakened")) node["isAwakened"].asBoolean(false) else false

		val acquisition = Util.JSON_MAPPER.treeToValue(node["acquisition"], Acquisition::class.java)

		return Gem(name, slot, tags, isVaal, isAwakened, acquisition = acquisition)
	}
}
