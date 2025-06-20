package com.episi.androidapp_s08_sanpedrito

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.episi.androidapp_s08_sanpedrito.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var webViewDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar el FAB para mostrar términos y condiciones
        binding.fab.setOnClickListener {
            showTermsAndConditions()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun showTermsAndConditions() {
        // Crear un diálogo personalizado
        val dialogView = layoutInflater.inflate(R.layout.dialog_webview, null)

        val webView = dialogView.findViewById<WebView>(R.id.webView)
        val closeButton = dialogView.findViewById<MaterialButton>(R.id.closeButton)
        val refreshButton = dialogView.findViewById<MaterialButton>(R.id.refreshButton)

        // Configurar el WebView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://josevasquezramos.github.io/desfile-sanpedrito/terminos-condiciones.html") // Reemplaza con tu URL

        // Crear el diálogo con estilo de Material
        webViewDialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        // Configurar botones
        closeButton.setOnClickListener { webViewDialog?.dismiss() }
        refreshButton.setOnClickListener { webView.reload() }

        // Mostrar el diálogo
        webViewDialog?.show()

        // Ajustar el tamaño del diálogo
        webViewDialog?.window?.setLayout(
            (resources.displayMetrics.widthPixels),
            (resources.displayMetrics.heightPixels)
        )
    }

    override fun onDestroy() {
        webViewDialog?.dismiss()
        super.onDestroy()
    }
}