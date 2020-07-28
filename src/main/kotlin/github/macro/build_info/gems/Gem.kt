package github.macro.build_info.gems

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import org.apache.logging.log4j.LogManager
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = GemDeserializer::class)
class Gem(
	name: String,
	slot: Slot,
	tags: List<Tag>,
	isVaal: Boolean,
	isAwakened: Boolean,
	acquisition: Acquisition
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val slotProperty = SimpleObjectProperty<Slot>()
	var slot by slotProperty

	val tagsProperty = SimpleListProperty<Tag>()
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
		val output = getFullname().replace(" ", "_")
		return "$output.png"
	}

	fun getFullname(): String {
		val suffix = if (isVaal) " [Vaal]" else if (isAwakened) " [Awakened]" else ""
		return name + suffix
	}
}

class GemDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Gem?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Gem? {
		val node: JsonNode = parser.readValueAsTree()

		val name = node["name"].asText()
		val slot = Slot.value(node["slot"].asText())
		if (slot == null) {
			LOGGER.info("Invalid Slot: ${node["slot"].asText()}")
			return null
		}
		val tags = node["tags"].mapNotNull {
			val temp = Tag.value(it.asText())
			if (temp == null)
				LOGGER.info("Invalid Gem Tag: ${it.asText()}")
			temp
		}.sorted()
		val isVaal = node["isVaal"]?.asBoolean(false) ?: false
		val isAwakened = node["isAwakened"]?.asBoolean(false) ?: false

		val acquisition = Util.JSON_MAPPER.treeToValue(node["acquisition"], Acquisition::class.java)

		return Gem(name, slot, tags, isVaal, isAwakened, acquisition = acquisition)
	}

	companion object {
		private val LOGGER = LogManager.getLogger(GemDeserializer::class.java)
	}
}
