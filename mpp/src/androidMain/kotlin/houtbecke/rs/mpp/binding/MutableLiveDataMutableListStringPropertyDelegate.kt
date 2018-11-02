package houtbecke.rs.mpp.binding

import androidx.lifecycle.MutableLiveData

class MutableLiveDataMutableListPropertyDelegate<T>(val live: MutableLiveData<MutableList<T>>): MutableListReadWriteProperty<T>() {
    override fun getValue(): MutableList<T>? {
        return live.value
    }

    override fun setValue(value: MutableList<T> ) {
        live.value = value
    }
}
