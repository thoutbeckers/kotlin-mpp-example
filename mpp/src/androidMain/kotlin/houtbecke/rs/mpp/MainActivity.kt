package houtbecke.rs.mpp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.TextView
import org.konan.multiplatform.R
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private var rootLayout: LinearLayout by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootLayout = findViewById(R.id.main_view) as LinearLayout
        rootLayout.removeAllViews()

        val product = Factory.create(mapOf("user" to "JetBrains"))
        val tv = TextView(this)
        tv.text = product.toString()+"\nmaking network request..."

        rootLayout.addView(tv)

        Network().about {
            runOnUiThread {
                tv.text = tv.text.toString() + "\n"+ it;
            }

        }

    }
}