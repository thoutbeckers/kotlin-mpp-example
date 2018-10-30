package houtbecke.rs.mpp.binding

import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class MutableLiveDataPropertyDelegate<T>(val live: MutableLiveData<T>, private val nonNullDefault: T): ReadWriteProperty<Any?, T>
{

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return live.value ?: nonNullDefault

    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        live.value = value
    }

}