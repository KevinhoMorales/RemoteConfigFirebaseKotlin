package com.kevinhomorales.remoteconfigfirebasekotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.kevinhomorales.remoteconfigfirebasekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val PRUEBA_KEY = "PruebaKey"
    val remoteConfig = Firebase.remoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRemoteConfig()
    }

    private fun setUpRemoteConfig() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        setUpRemoteConfigMessage()
    }

    private fun setUpRemoteConfigMessage() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    val messateFromFirebase = remoteConfig.getString(PRUEBA_KEY)
                    binding.testId.text = messateFromFirebase
                } else {
                    // Maneja los errores
                }
            }
    }
}