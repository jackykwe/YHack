package com.yhack.tutoree.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.yhack.tutoree.R
import com.yhack.tutoree.database.ImportDBFromAssets
import com.yhack.tutoree.databinding.ActivityMainBinding
import com.yhack.tutoree.model.GradeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Connection
import java.sql.Driver
import java.sql.DriverManager
import java.sql.SQLException

class Keys private constructor() {
    companion object {
        const val LOGGED_IN = "logged_in"
    }
}

class MainActivity : AppCompatActivity() {

    internal var connection: Connection? = null

    fun modelExampleCode() {
        val gradeModel = GradeModel(assets)
        val arr = FloatArray(18)
        val output = gradeModel.predict(arr)
        for (i in output)
            println(i)
    }

    internal lateinit var sharedPreferences: SharedPreferences
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    private fun setupDBConnection() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                DriverManager.registerDriver(
                    Class.forName("org.sqldroid.SQLDroidDriver").newInstance() as Driver
                )
            } catch (e: Exception) {
                throw RuntimeException("Failed to register SQLDroidDriver")
            }

            ImportDBFromAssets.importIfNeeded(applicationContext)

            val jdbcUrl = "jdbc:sqldroid:${ImportDBFromAssets.buildDBPath(applicationContext)}"
            try {
                connection = DriverManager.getConnection(jdbcUrl)
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        modelExampleCode()

        setupDBConnection()
        super.onCreate(savedInstanceState)

        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)

        setTheme(R.style.AppTheme_NoActionBar)  // To remove splash
        setContentView(mainActivityBinding.root)
        setSupportActionBar(mainActivityBinding.toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            mainActivityBinding.toolbar.visibility = when (destination.id) {
                R.id.startUpFragment, R.id.loginFragment, R.id.signUpFragment,
                R.id.initIAmADeclarationFragment,
                R.id.initTutorInfoFragment, R.id.initTutorVerifyFragment,
                R.id.initTuteeInfoFragment,
                R.id.initMBTIQuestionsFragment, R.id.initMBTIResultFragment,
                R.id.initSubjectCheckboxFragment, R.id.initExitFragment,
                R.id.filterFragment -> View.GONE
                else -> View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        if (connection != null) {
            try {
                connection!!.close()
            } catch (e: SQLException) {
                throw RuntimeException(e)
            }
        }
        super.onDestroy()
    }
}