package com.example.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = Data.Builder().putInt("intKey",1).build()

        val constraints = Constraints.Builder()
           // .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()
/*
        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDataBase>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.SECONDS)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(this).enqueue(myWorkRequest)

 */

        val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<RefreshDataBase>(15,TimeUnit.MINUTES)
            .setInputData(data)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this,
            Observer {

                if (it.state == WorkInfo.State.RUNNING){
                    println("running ")
                } else if (it.state == WorkInfo.State.FAILED){
                    println(it.state == WorkInfo.State.SUCCEEDED)
                    println("succeeded")
                }

                  // WorkManager.getInstance(this).cancelAllWork()  -> tüm işleri iptal etmek için kullanılır

                //Chaining -> Zincirleme için kullanılır periodic requestlerde yapılmıyor.

                /*
                val oneTimeRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDataBase>()
                    .setConstraints(constraints)
                    .setInputData(data)
                    .build()

                WorkManager.getInstance(this).beginWith(oneTimeRequest)
                    .then(oneTimeRequest)
                    .then(oneTimeRequest)
                    .enqueue()


                 */

            })

    }
}