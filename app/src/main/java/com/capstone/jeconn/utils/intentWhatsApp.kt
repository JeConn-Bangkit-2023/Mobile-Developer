package com.capstone.jeconn.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat

fun intentWhatsApp(context: Context) {
    try {
        val msg = "Saya Butuh Bantuan"
        val uri: Uri = Uri.parse("smsto:" + 6282260012008)
        val sendIntent = Intent(Intent.ACTION_SENDTO, uri)
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg)
        sendIntent.setPackage("com.whatsapp")
        ContextCompat.startActivity(context, sendIntent, null)
        Toast.makeText(
            context,
            "Anda akan diarahkan ke WhatsApp Customer Service kami",
            Toast.LENGTH_LONG
        ).show()
    } catch (e: PackageManager.NameNotFoundException) {
        Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT).show()
    }
}