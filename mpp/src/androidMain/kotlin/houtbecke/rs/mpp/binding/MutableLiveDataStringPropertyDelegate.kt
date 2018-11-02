package houtbecke.rs.mpp.binding

import androidx.lifecycle.MutableLiveData

class MutableLiveDataStringPropertyDelegate(val live: MutableLiveData<String>): StringReadWriteProperty() {
    override fun getValue(): String? {
        return live.value
    }

    override fun setValue(value: String) {
        live.value = value
    }
}

