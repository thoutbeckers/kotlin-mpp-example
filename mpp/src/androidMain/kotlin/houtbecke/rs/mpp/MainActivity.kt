package houtbecke.rs.mpp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import houtbecke.rs.mpp.databinding.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)

        // this ensures our LiveData's in the ViewModel will keep updating
        binding.setLifecycleOwner(this)
        val model = androidx.lifecycle.ViewModelProviders.of(this).get(ViewModelA::class.java)
        binding.setVariable(BR.viewmodel, model)
        binding.executePendingBindings()

    }
}