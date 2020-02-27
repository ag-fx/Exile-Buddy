package github.macro.build_info.gems

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import github.macro.Util
import github.macro.build_info.gems.ingredient.Ingredient
import github.macro.build_info.gems.reward.Reward
import javafx.beans.property.SimpleListProperty
import javafx.collections.FXCollections
import tornadofx.*
import java.io.IOException

/**
 * Created by Macro303 on 2020-Jan-13.
 */
@JsonDeserialize(using = AcquisitionDeserializer::class)
class Acquisition(
	rewards: List<Reward>,
	crafting: List<List<Ingredient>>
) {
	val rewardsProperty = SimpleListProperty<Reward>()
	var rewards by rewardsProperty

	val craftingProperty = SimpleListProperty<List<Ingredient>>()
	var crafting by craftingProperty

	init {
		this.rewards = FXCollections.observableList(rewards)
		this.crafting = FXCollections.observableList(crafting)
	}

	override fun toString(): String {
		return "Acquisition(rewards=$rewards, crafting=$crafting)"
	}
}

class AcquisitionDeserializer @JvmOverloads constructor(vc: Class<*>? = null) : StdDeserializer<Acquisition?>(vc) {

	@Throws(IOException::class, JsonProcessingException::class)
	override fun deserialize(parser: JsonParser, ctx: DeserializationContext): Acquisition? {
		val node: JsonNode = parser.readValueAsTree()

		val rewards = ArrayList<Reward>()
		node["rewards"].mapTo(rewards) { Util.JSON_MAPPER.treeToValue(it, Reward::class.java) }
		val crafting = ArrayList<List<Ingredient>>()
		node["crafting"].mapTo(crafting) { ingredients ->
			val ingredientList = ArrayList<Ingredient>()
			ingredients.mapTo(ingredientList) {
				Util.JSON_MAPPER.treeToValue(it, Ingredient::class.java)
			}
			ingredientList
		}

		return Acquisition(rewards = rewards, crafting = crafting)
	}
}