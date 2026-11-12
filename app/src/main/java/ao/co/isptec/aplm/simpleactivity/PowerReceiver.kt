package ao.co.isptec.aplm.simpleactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PowerReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent){
        when(intent.action){
            Intent.ACTION_POWER_CONNECTED -> {
                Log.i("PowerReceiver", "Carregador ligado");
            }

            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.i("PowerReceiver", "Carregador desligado!");
            }
        }
    }
}