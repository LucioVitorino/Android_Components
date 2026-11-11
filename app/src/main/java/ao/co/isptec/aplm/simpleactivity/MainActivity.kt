package ao.co.isptec.aplm.simpleactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val botaoAbri = findViewById<Button>(R.id.botaoAbrirSegunda);
        botaoAbri.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java);
            startActivity(intent);
        }
        Log.i("MainActivity", "OnCreate");
    }

    fun finishButtonPressed(view: View) {
        finish();
    }

    override fun onStart(){
        super.onStart();
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
        Log.i("MainActivity","OnStop");
    }

    override fun onRestart(){
        super.onRestart();
        Log.i("MainActivity", "OnRestart");
    }

    override fun onDestroy(){
        super.onDestroy();
        Log.i("MainActivity", "OnDestroy");
    }
}