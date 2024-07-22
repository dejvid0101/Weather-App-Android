package me.projects.firstandroidapp.background

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class Worker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Perform the task here
        println("Background task")
        return Result.success()
    }
}