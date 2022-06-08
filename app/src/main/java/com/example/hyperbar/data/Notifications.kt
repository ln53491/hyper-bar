//package com.example.hyperbar.data
//
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.content.pm.ApplicationInfo
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.os.Build
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.work.Data
//import androidx.work.Worker
//import androidx.work.WorkerParameters
//import com.example.hyperbar.MainActivity
//import com.example.hyperbar.R
//
//
//class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
//    Worker(appContext, workerParams) {
//    override fun doWork(): Result {
//
////        uploadImages()
//
//        return Result.success()
//    }
//}
//
//
//class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
//    Worker(context, workerParameters) {
//    override fun doWork(): Result {
//        val taskData = inputData
//        val taskDataString = "DONE I GUESS"
//
//        showNotification(
//            "Hyper Bar",
//            taskDataString.toString(),
//            "Order successful!",
//            "Your order has been sent to our personnel. We will notify you when it's ready."
//        )
//
//        val outputData = Data.Builder().putString(WORK_RESULT, "Task Finished").build()
//
//        return Result.success(outputData)
//    }
//
//    private fun showNotification(
//        task: String,
//        desc: String,
//        textTitle: String,
//        textContent: String
//    ) {
//        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
//                as NotificationManager
//        val channelId = "Hyper_Bar"
//        val channelName = "Hyper Bar"
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(
//                channelId, channelName,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            manager.createNotificationChannel(channel)
//        }
//
//
//        val builder = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(R.mipmap.app_icon)
//            .setContentTitle(textTitle)
//            .setContentText(textContent)
//            .setLargeIcon(
//                BitmapFactory.decodeResource(
//                    applicationContext.resources,
//                    R.drawable.ic_icon_bw
//                )
//            )
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText(
//                        textContent
//                    )
//            )
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        manager.notify(1, builder.build())
//    }
//
//
//    companion object {
//        const val WORK_RESULT = "work_result"
//    }
//}
//
//
////fun createNotificationChannel(channelId: String, context: Context) {
////    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////        val name = "Hyper Bar"
////        val desc = "Hyper Bar"
////        val importance = NotificationManager.IMPORTANCE_DEFAULT
////        val channel = NotificationChannel(channelId, name, importance).apply {
////            description = desc
////        }
////        val notificationManager: NotificationManager =
////            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
////        notificationManager.createNotificationChannel(channel)
////
////    }
////}
//
////fun largeTextWithBigIconNotification(
////    context: Context,
////    channelId: String,
////    notificationId: Int,
////    textTitle: String,
////    textContent: String,
////    largeIcon: Bitmap,
////    priority: Int = NotificationCompat.PRIORITY_DEFAULT
////) {
////
////    val builder = NotificationCompat.Builder(context, channelId)
////        .setSmallIcon(R.mipmap.app_icon)
////        .setContentTitle(textTitle)
////        .setContentText(textContent)
////        .setLargeIcon(largeIcon)
////        .setStyle(
////            NotificationCompat.BigTextStyle()
////                .bigText(
////                    textContent
////                )
////        )
////        .setPriority(priority)
////    with(NotificationManagerCompat.from(context)) {
////        notify(notificationId, builder.build())
////    }
////}