package houtbecke.rs.mpp.binding

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class MutableListReadWriteProperty<T>: ReadWriteProperty<Any?, MutableList<T>> {
    abstract fun getValue(): MutableList<T>?
    abstract fun setValue(value: MutableList<T>)

    override fun getValue(thisRef: Any?, property: KProperty<*>): MutableList<T> {
        var value = getValue()
        if (value == null) {
            value = mutableListOf()
            setValue(value)
        }
        return value
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: MutableList<T>) {
        setValue(value)
    }
}


abstract class StringReadWriteProperty: ReadWriteProperty<Any?, String> {
    abstract fun getValue(): String?
    abstract fun setValue(value: String)
    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return getValue() ?: ""
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        setValue(value)
    }
}

