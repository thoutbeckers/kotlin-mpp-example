package houtbecke.rs.mpp.binding

import androidx.lifecycle.MutableLiveData

class MutableLiveDataStringPropertyDelegate(live: MutableLiveData<String>): MutableLiveDataPropertyDelegate<String>(live, "")