package ao.co.isptec.aplm.simpleactivity

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyService : Service() {

    private var workerThread: Thread? = null;
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
                for (i in 1..10) {
                    Log.i("WorkerThread", "Contagem: $i");
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

    override fun onBind(intent: Intent?): IBinder? {
        return (null);
    }
}