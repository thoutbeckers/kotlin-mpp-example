package houtbecke.rs.mpp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import houtbecke.rs.mpp.binding.MutableLiveDataStringPropertyDelegate
import houtbecke.rs.mpp.firebase.getFirestoreInstance

class ViewModelA(a: Application): AndroidViewModel(a) {

    val user: MutableLiveData<String> = MutableLiveData()
    val mood: MutableLiveData<String> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    val viewModel: ViewModel

    init {
        val s = user.value
        println ("default: $s")

        viewModel = ViewModel(Network(getFirestoreInstance()),
                MutableLiveDataStringPropertyDelegate(user),
                MutableLiveDataStringPropertyDelegate(mood),
                MutableLiveDataStringPropertyDelegate(error)
        )
    }

    fun onClickUpdate() {
        viewModel.updateTapped()
    }

    fun onClickRetrieve() {
        viewModel.retrieveTapped()
    }
}