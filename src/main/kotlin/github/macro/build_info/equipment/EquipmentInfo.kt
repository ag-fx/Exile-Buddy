package github.macro.build_info.equipment

import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*

/**
 * Created by Macro303 on 2020-Jan-16.
 */
class EquipmentInfo(
	name: String,
	slot: Slot,
	level: Int?,
	quality: Double?
) {
	val nameProperty = SimpleStringProperty()
	var name by nameProperty

	val slotProperty = SimpleObjectProperty<Slot>()
	var slot by slotProperty

	val levelProperty = SimpleObjectProperty<Int?>()
	var level by levelProperty

	val qualityProperty = SimpleObjectProperty<Double?>()
	var quality by qualityProperty

	init {
		this.name = name
		this.slot = slot
		this.level = level
		this.quality = quality
	}
}