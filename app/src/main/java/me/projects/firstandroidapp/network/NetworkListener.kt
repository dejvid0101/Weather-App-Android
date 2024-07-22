package me.projects.firstandroidapp.network;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

public class NetworkListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Handle the broadcast
        println("Network")
        Toast.makeText(context, "Broadcast received", Toast.LENGTH_SHORT).show()
    }
}