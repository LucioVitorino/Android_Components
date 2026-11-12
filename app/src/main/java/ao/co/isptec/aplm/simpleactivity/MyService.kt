package ao.co.isptec.aplm.simpleactivity

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyService : Service() {

    private var workerThread: Thread? = null;
    @Volatile
    var contador = 0
        private set
    override fun onCreate()
    {
        super.onCreate();
        Log.i("MyService", "onCreate: Service criado");
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (workerThread?.isAlive == true)
        {
            Log.i("MyService", "Já existe uma tarefa em execução")
            return START_NOT_STICKY
        }
        workerThread = Thread{
            try {
                for (i in 1..100) {
                    contador = i

                    Log.i("WorkerThread", "Contagem: $contador")
                    Thread.sleep(1000);
                }
                Log.i("WorkerThread", "Contagem terminada!");
            }catch (e : InterruptedException){
                Thread.currentThread().interrupt();
                Log.i("WorkerThread", "Contagem interrompida!")
                return@Thread;
            }
        }
        workerThread?.start();
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        workerThread?.interrupt();
        super.onDestroy()
        Log.i("MyService", "onDestroy: Serviço destruído!");
    }

    private val binder = LocalBinder();
    override fun onBind(intent: Intent?): IBinder {
        return (binder);
    }

    inner class LocalBinder : Binder(){
        fun getService(): MyService{
            return this@MyService;
        }
    }

    fun pararContagem() {
        workerThread?.interrupt()
    }
}