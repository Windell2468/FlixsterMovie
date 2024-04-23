import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flixster.R // Import your R class from your project's package


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val supportFragmentManager = supportFragmentManager
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // Replace "id.content" with the correct ID from your layout file
        fragmentTransaction.replace(R.id.content, BestMoviesFragment(), null).commit()
    }
}


