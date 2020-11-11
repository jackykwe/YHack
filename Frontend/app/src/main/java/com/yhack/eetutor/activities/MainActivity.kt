package com.yhack.eetutor.activities

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.yhack.eetutor.R
import com.yhack.eetutor.databinding.ActivityMainBinding
import com.yhack.eetutor.model.CompatibilityModel
import com.yhack.eetutor.model.GradeModel
import org.tensorflow.lite.Interpreter
import java.io.File
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class Keys private constructor() {
    companion object {
        const val LOGGED_IN = "logged_in"
        const val INIT_SURVEY_DONE = "init_survey_done"
        const val TUTOR_INIT_VERIFY_DONE = "tutor_init_verify_done"
    }
}

class MainActivity : AppCompatActivity() {


    fun modelExampleCode() {
        val gradeModel = GradeModel(assets)
        val arr = FloatArray(18)
        val output = gradeModel.predict(arr)
        for (i in output)
            println(i)
    }


    internal lateinit var sharedPreferences: SharedPreferences
    internal lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        modelExampleCode()

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
}