package houtbecke.rs.mpp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import houtbecke.rs.mpp.databinding.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_main)

        // this ensures our LiveData's in the ViewModel will keep updating
        binding.setLifecycleOwner(this)
        val viewModel = androidx.lifecycle.ViewModelProviders.of(this).get(ViewModelA::class.java)

        val adapter = UserStatusAdapter()
        viewModel.updates.observe(this, Observer { adapter.setData(it) })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


        binding.setVariable(BR.viewmodel, viewModel)
        binding.executePendingBindings()

    }
}