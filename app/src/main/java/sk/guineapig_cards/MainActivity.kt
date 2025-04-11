package sk.guineapig_cards

import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import sk.guineapig_cards.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE = 100

    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: CardDatabaseHelper
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Request permissions on app start
        requestPermissionsIfNeeded()

        // Initialize the database helper
        dbHelper = CardDatabaseHelper(this)

        // Set up navigation
        setupNavigation()
    }

    private fun requestPermissionsIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE)
            }
        } else {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
            }
        }
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView

        // Retrieve NavHostFragment and NavController programmatically
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_add_card)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun addCardToDatabase(name: String, description: String, photoPath1: String?, photoPath2: String?) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val card = Card(name = name, description = description, photoPath1 = photoPath1, photoPath2 = photoPath2)
                    dbHelper.insertCard(card)
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error adding card to database", e)
                }
            }
        }
    }

    fun getAllCardsFromDatabase(callback: (List<Card>) -> Unit) {
        lifecycleScope.launch {
            val cards = withContext(Dispatchers.IO) {
                try {
                    dbHelper.getAllCards()
                } catch (e: Exception) {
                    Log.e("MainActivity", "Error fetching cards from database", e)
                    emptyList<Card>()
                }
            }
            callback(cards)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}