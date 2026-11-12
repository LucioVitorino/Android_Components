package ao.co.isptec.aplm.simpleactivity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val powerReceiver = PowerReceiver();
    private val powerFilter = IntentFilter().apply {
        addAction(Intent.ACTION_POWER_CONNECTED)
        addAction(Intent.ACTION_POWER_DISCONNECTED)
    }
    private var myService: MyService? = null;
    private var isBound = false;

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder?){
            val binder = service as MyService.LocalBinder

            myService = binder.getService()
            isBound = true

            Log.i("MainActivity", "Ligação ao Service estabelecida!")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myService = null
            isBound = false

            Log.i("MainActivity", "Ligação ao Service perdida!")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoIniciarService = findViewById<Button>(R.id.botaoIniciarService);
        val botaoAbri = findViewById<Button>(R.id.botaoAbrirSegunda);
        val botaoPararService = findViewById<Button>(R.id.botaoPararService);
        val textoContador = findViewById<TextView>(R.id.textoContador)


        val botaoConsultarContador = findViewById<Button>(R.id.botaoConsultarContador)
        botaoConsultarContador.setOnClickListener {

            val service = myService

            if (isBound && service != null) {
                textoContador.text = "Contador: ${service.contador}"
            } else {
                textoContador.text = "Service ainda não conectado!"
            }
        }
        botaoAbri.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java);
            startActivity(intent);
        }


        botaoIniciarService.setOnClickListener {
            Log.i("MainActivity", "Botão Iniciar Service pressionado")
            val intent = Intent(
                this, MyService::class.java
            )
            startService(intent);
        }

        botaoPararService.setOnClickListener {
            if (isBound) {
                myService?.pararContagem()
            }
        }

        Log.i("MainActivity", "OnCreate");
    }

    fun finishButtonPressed(view: View) {
        finish();
    }

    override fun onStart(){
        super.onStart()

        val intent = Intent(this, MyService::class.java)

        isBound = bindService(
            intent,
            connection,
            Context.BIND_AUTO_CREATE
        )
        ContextCompat.registerReceiver(
            this,
            powerReceiver,
            powerFilter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
        Log.i("MainActivity", "onStart");
    }

    override fun onResume(){
        super.onResume();
        Toast.makeText(
            this,
            "A activity está pronta para ser utilizada!",
            Toast.LENGTH_SHORT
        ).show();
        Log.i("MainActivity", "OnResume");
    }

    override fun onPause(){
        super.onPause();
        Log.i("MainActivity", "OnPause");
    }

    override fun onStop(){
        super.onStop();
        if (isBound) {
            unbindService(connection)
            isBound = false
            myService = null
        }
        Log.i("MainActivity","OnStop");
    }

    override fun onRestart(){
        super.onRestart();
        Log.i("MainActivity", "OnRestart");
    }

    override fun onDestroy(){
        super.onDestroy();
        unregisterReceiver(powerReceiver);
        Log.i("MainActivity", "OnDestroy");
    }
}