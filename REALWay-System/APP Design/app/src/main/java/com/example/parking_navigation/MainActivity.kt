package com.example.parking_navigation

import android.app.Activity
import kotlinx.coroutines.launch
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parking_navigation.ui.theme.Parking_NavigationTheme
import java.util.UUID
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.usb.UsbManager
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.felhr.usbserial.UsbSerialDevice
import com.felhr.usbserial.UsbSerialInterface

import okhttp3.OkHttpClient
//import com.tinder.scarlet.lifecycle.android.lifecycle
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit
import okhttp3.Request
import okhttp3.WebSocketListener
import org.json.JSONException
import org.json.JSONObject

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay

import androidx.compose.material.icons.filled.Pause
import android.os.Vibrator
import android.os.Build
import android.os.VibrationEffect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager


//import androidx.compose.ui.text.font.FontWeight

//fun styleDirections(text: String): AnnotatedString {
//    val keywords = listOf("left", "right")  // Keywords to be bolded
//    return buildAnnotatedString {
//        text.split(" ").forEach { word ->
//            if (word.lowercase() in keywords) {
//                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                    append("$word ")
//                }
//            } else {
//                append("$word ")
//            }
//        }
//    }
//}

//fun updateUIWithDirections(message: String, updateResponseText: (String) -> Unit) {
//    val styledText = styleDirections(message)
//    // Assuming `responseText` is a mutable state managed in your Compose UI
//    updateResponseText(styledText)
//}


//data class JoinRoomMessage(val room: String)
//
//data class Message(val type: String, val message: String)

//interface WebSocketService {
//    @Send
//    fun joinRoom(joinRoomMessage: JoinRoomMessage)
//
//    @Receive
//    fun observeMessages(): Flow<Message>
//}


import android.os.Environment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//fun writeLogToFile(context: Context, uuid: String, text: String, filePrefix: String = "Log") {
//    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
//    val fileName = "${filePrefix}_${uuid}_$timeStamp.json"
//    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
//
//    try {
//        val logFile = File(storageDir, fileName)
//        if (!logFile.exists()) {
//            logFile.createNewFile()
//        }
//
//        val logEntry = JSONObject().apply {
//            put("timestamp", System.currentTimeMillis())
//            put("log", text)
//        }
//
//        FileWriter(logFile, true).apply {
//            append(logEntry.toString() + "\n")
//            flush()
//            close()
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//    }
//}



//class IMULogger(private val context: Context, private val uuid: String) : SensorEventListener {
//    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
//    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
//    var isLoggingActive = false
//
//    fun startLogging() {
//        if (!isLoggingActive) {
//            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
//            isLoggingActive = true
//        }
//    }
//
//    fun stopLogging() {
//        if (isLoggingActive) {
//            sensorManager.unregisterListener(this)
//            isLoggingActive = false
//        }
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        event?.let {
//            val x = it.values[0]
//            val y = it.values[1]
//            val z = it.values[2]
//            val imuData = "Accel: x=$x, y=$y, z=$z"
//            writeLogToFile(context, uuid, imuData, "IMU_Log")
//        }
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//        // Handle accuracy changes if necessary
//    }
//}
//
//object IMULoggerInstance {
//    private var imuLogger: IMULogger? = null
//
//    fun getLogger(context: Context, uuid: String): IMULogger {
//        if (imuLogger == null) {
//            imuLogger = IMULogger(context, uuid)
//        }
//        return imuLogger!!
//    }
//
//    fun clearLogger() {
//        imuLogger = null
//    }
//}



@Composable
fun Stopwatch() {
    var time by remember { mutableStateOf(0L) } // Time in milliseconds
    var timerRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(timerRunning) {
        while (timerRunning) {
            delay(100L)
            time += 100L
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = formatTime(time),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(), // Fill the width of the parent
            horizontalArrangement = Arrangement.SpaceEvenly // Evenly space the buttons across the available width
        ) {
            Button(
                onClick = { timerRunning = true },
                modifier = Modifier.padding(horizontal = 8.dp) // Add horizontal padding to increase spacing
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Start")  // Using play arrow icon for start
                Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text (if using text)
                //Text("Start", fontSize = 18.sp) // Optional text label
            }
            Button(
                onClick = { timerRunning = false },
                modifier = Modifier.padding(horizontal = 8.dp) // Add horizontal padding to increase spacing
            ) {
                Icon(Icons.Default.Pause, contentDescription = "Pause")  // Using pause icon for pause
                Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text (if using text)
                //Text("Pause", fontSize = 18.sp) // Optional text label
            }
            Button(
                onClick = {
                    timerRunning = false
                    time = 0L
                },
                modifier = Modifier.padding(horizontal = 8.dp) // Add horizontal padding to increase spacing
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Reset")  // Using refresh icon for reset
                Spacer(modifier = Modifier.width(4.dp)) // Space between icon and text (if using text)
                //Text("Reset", fontSize = 18.sp) // Optional text label
            }
        }
    }
}


// Helper function to format the time from milliseconds to a readable format
fun formatTime(millis: Long): String {
    val seconds = millis / 1000 % 60
    val minutes = millis / (1000 * 60) % 60
    val hours = millis / (1000 * 60 * 60) % 24
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}



@Composable
fun WebSocketConnectionButton(sharedPreferences: SharedPreferences, updateResponseText: (String) -> Unit) {
    val uuid = sharedPreferences.getString("uuid", null)
    val context = LocalContext.current

    Button(
        onClick = {
            if (uuid != null) {
                connectWebSocket(context, sharedPreferences, updateResponseText){
                    updateResponseText("Success! Connected and message sent.")
                }
            } else {
                updateResponseText("Please generate a UUID.")
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text("Open WebSocket Connection", fontSize = 18.sp, color = Color.White)
    }
}


fun vibratePhone(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // For newer versions, use VibrationEffect
        val effect = VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    } else {
        // Deprecated method for older devices
        @Suppress("DEPRECATION")
        vibrator.vibrate(500)
    }
}


fun connectWebSocket(context: Context, sharedPreferences: SharedPreferences, updateResponseText: (String) -> Unit, onSuccess: () -> Unit) {
    val uuid = sharedPreferences.getString("uuid", null)
    if (uuid == null) {
        updateResponseText("Please generate a UUID.")
        return
    }

    val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()


    val request = Request.Builder()
        .url("ws://10.0.2.2:8080/ws")   // emulator → laptop FastAPI WS
        .build()

    val wsListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
//            val registrationMessage = "{\"uuid\":\"$uuid\"}"

            val registrationMessage = JSONObject().apply {
                put("uuid", uuid)
                put("connect", "true")  // Assuming you want to send a boolean-like string
            }.toString()

            webSocket.send(registrationMessage)  // Sends a JSON formatted message containing the UUID
            //updateResponseText("Connected to the server")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            vibratePhone(context)
            try {
                val jsonObject = JSONObject(text)
                val message = jsonObject.getString("message")
                updateResponseText(message)  // Updates the UI to show true or false

                // Only send a reply if the message is not already a reply to prevent loops
                if (message != "success") {
                    val timeStamp = System.currentTimeMillis()
                    val reply = JSONObject().apply {
                        put("uuid", uuid)
                        put("timestamp", timeStamp)
                        put("message", "Received: $message")
                    }

                    // Send reply back to server
                    webSocket.send(reply.toString())
                }

                val activity = context as Activity
                if (message == "success") {
                    activity.runOnUiThread {
                        onSuccess()  // Execute the onSuccess method on the UI thread
                    }
                }
                else if (message.contains("Parked at Tag")){
                    val tagNumber = message.split("Parked at Tag ").getOrNull(1)?.trim() ?: "Unknown"
                    connecttoESP32(context, updateResponseText, uuid,"stop") {
                        updateResponseText("Parking Confirmation, You have parked at $tagNumber")
                    }
                    sharedPreferences.edit().apply {
                        putString("tagNumber", tagNumber)
                        apply()
                    }
                }
                else if (message == "Reached final destination"){
                    connecttoESP32(context, updateResponseText, uuid,"stop") {
                        updateResponseText("Reached Destination. Please close the app")
                    }
                    sharedPreferences.edit().clear().apply()
                }
            } catch (e: JSONException) {
                updateResponseText("Failed to parse JSON: ${e.message}")
            }
        }



        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(1000, null)
            updateResponseText("Closing: $code / $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            updateResponseText("Error on WebSocket: ${t.message}")
        }
    }

    client.newWebSocket(request, wsListener)

    // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
    client.dispatcher.executorService.shutdown()
}



//fun connectToWebSocket(context: Context, uuid: String, updateResponseText: (String) -> Unit) {
//    val application = context.applicationContext as Application
//    val okHttpClient = OkHttpClient.Builder().build()
//    val scarletInstance = Scarlet.Builder()
//        .webSocketFactory(okHttpClient.newWebSocketFactory("ws://192.168.50.112:5000"))
//        .addMessageAdapterFactory(GsonMessageAdapter.Factory())
//        .addStreamAdapterFactory(CoroutinesStreamAdapterFactory()) // Ensure this is added correctly
//        .lifecycle(AndroidLifecycle.ofApplicationForeground(application))
//        .build()
//
//    val webSocketService: WebSocketService = scarletInstance.create(WebSocketService::class.java)
//
//    CoroutineScope(Dispatchers.Main).launch {
//        try {
//            webSocketService.joinRoom(JoinRoomMessage(uuid))
//            webSocketService.observeMessages().collect { message ->
//                updateResponseText(message.message)
//            }
//        } catch (e: Exception) {
//            updateResponseText("Error connecting or receiving: ${e.message}")
//        }
//    }
//}

fun styleDirections(text: String): AnnotatedString {
    val keywords = listOf("left", "right")  // Keywords to be bolded and made uppercase
    return buildAnnotatedString {
        text.split(" ").forEach { word ->
            if (word.lowercase() in keywords) {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("${word.uppercase()} ")  // Convert to uppercase
                }
            } else {
                append("$word ")
            }
        }
    }
}



//@Composable
//fun AnimatedResponseBox(text: String) {
//    var visible by remember { mutableStateOf(true) }  // State to control visibility for blinking
//    var triggerAnimation by remember { mutableStateOf(false) }  // State to control the trigger of animation
//
//    // This effect runs every time text changes or the trigger changes
//    LaunchedEffect(text, triggerAnimation) {
//        visible = false  // Hide the text initially
//        delay(100)  // Short delay to simulate "blink" effect
//        visible = true  // Show the text, completing the "blink" effect
//    }
//
//    // Watching for text change and toggling the trigger
//    var lastText by remember { mutableStateOf("") }
//    if (text != lastText) {
//        lastText = text
//        triggerAnimation = !triggerAnimation  // Toggle the animation trigger
//    }
//
//    AnimatedVisibility(
//        visible = visible,
//        enter = fadeIn(animationSpec = tween(durationMillis = 100)),  // Customize the fadeIn duration
//        exit = fadeOut(animationSpec = tween(durationMillis = 100))  // Customize the fadeOut duration
//    ) {
//        Text(
//            text = styleDirections(text),
//            modifier = Modifier.padding(20.dp),
//            style = TextStyle(fontSize = 24.sp, color = Color.Black)
//        )
//    }
//}


//@Composable
//fun AnimatedResponseBox(text: String) {
//    var textColor by remember { mutableStateOf(Color.Red) } // Start with red color
//
//    LaunchedEffect(key1 = text) {
//        textColor = Color.Red  // Set text color to red when text changes
//        delay(2000)  // Wait for 2 seconds
//        textColor = Color.Black  // Change text color to black
//    }
//
//    Text(
//        text = styleDirections(text),
//        color = textColor,
//        modifier = Modifier.padding(20.dp),
//        style = TextStyle(fontSize = 24.sp)
//    )
//}


class MainActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences
//    private var imuLogger: IMULogger? = null
    companion object {
        const val ACTION_USB_PERMISSION = "com.example.parking_navigation.USB_PERMISSION"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)

        setContent {
            Parking_NavigationTheme {
                Column(Modifier.fillMaxSize()){
                    HealthCheckPanel()
                    MainScreen(sharedPreferences)
                }
            }
        }
    }
}

@Composable
fun MainScreen(sharedPreferences: SharedPreferences) {
    var responseText by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.wifihigh),
                contentDescription = "WiFi Icon",
                modifier = Modifier
                    .size(46.dp) // Adjust size if necessary
                    .graphicsLayer {
                        rotationZ = -50f // Tilt the icon by -60 degrees
                    }
            )
            //Spacer(modifier = Modifier.width(2.dp)) // Adjust spacing between the icon and text
            Text(
                "REALWay Navigation System",
                fontSize = 22.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp)
            )


        }

        Text(
            "Please connect to REALWay WiFi before using the application",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp) // Increased bottom padding
        )
        Button(
            onClick = { navigateToWifiSettings(context) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.wifihigh),
                contentDescription = "WiFi Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.size(8.dp))  // Space between icon and text
            Text("Connect to REALWay WiFi")
        }
        ButtonGroup(sharedPreferences, responseText, updateResponseText = { responseText = it })
        //ResponseBox(responseText)
        ResponseBox(responseText)
//        AnimatedResponseBox(responseText)

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DeleteEntryButton(sharedPreferences, updateResponseText = { responseText = it })
            Spacer(Modifier.width(16.dp)) // Space between buttons
            ClearResponse(updateResponseText = { responseText = it })
        }
        //Stop_Sending(sharedPreferences, updateResponseText = { responseText = it })

        Stopwatch()
        //ConnectESPButton(sharedPreferences, updateResponseText = { responseText = it })
//        WebSocketConnectionButton(
//            sharedPreferences = sharedPreferences,
//            updateResponseText = { responseText = it },
//            updateDirections = { newDirections -> directions = newDirections }  // Pass this function
//        )
        //Stop_Scanning(sharedPreferences, updateResponseText = { responseText = it })
//        directions.forEach { direction ->
//            NavigationArrow(direction)
//        }

    //}


//        DeleteEntryButton(sharedPreferences, updateResponseText = { responseText = it })
//        ConnectESPButton(sharedPreferences, updateResponseText = { responseText = it })
//        WebSocketConnectionButton(sharedPreferences, updateResponseText = { responseText = it })
//        ClearResponse(updateResponseText = { responseText = it })
        //Stop_Sending(sharedPreferences, updateResponseText = { responseText = it })

        //Text(text = responseText)
    }
}



//@Composable
//fun MainScreen(sharedPreferences: SharedPreferences) {
//    var responseText by remember { mutableStateOf("") }
//    var showOptions by remember { mutableStateOf(false) }  // State to toggle visibility of options
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            "Parking Navigation System",
//            fontSize = 26.sp,
//            color = Color.Black,
//            fontWeight = FontWeight.Bold
//        )
//
//        // Button to toggle the visibility of the main content
//        Button(
//            onClick = { showOptions = !showOptions },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//            modifier = Modifier.padding(8.dp)
//        ) {
//            Text(if (showOptions) "Hide Options" else "Show Options")
//        }
//
//        if (showOptions) {  // Only display the following content if showOptions is true
//            Text(
//                "Please connect to ESP32-AP WiFi before using the application",
//                fontSize = 16.sp,
//                color = Color.Gray,
//                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            )
//            Button(
//                onClick = { navigateToWifiSettings(context) },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
//            ) {
//                Text("Connect to ESP32-AP")
//            }
//            ButtonGroup(sharedPreferences, responseText, updateResponseText = { responseText = it })
//            ResponseBox(responseText)
//            DeleteEntryButton(sharedPreferences, updateResponseText = { responseText = it })
//            ConnectESPButton(sharedPreferences, updateResponseText = { responseText = it })
//            WebSocketConnectionButton(sharedPreferences, updateResponseText = { responseText = it })
//            ClearResponse(updateResponseText = {responseText = it})
//            Stop_Sending(sharedPreferences, updateResponseText = {responseText = it})
//        }
//    }
//}


//@Composable
//fun ButtonGroup(sharedPreferences: SharedPreferences, responseText: String, updateResponseText: (String) -> Unit) {
//    val context = LocalContext.current
//    Row(
//        modifier = Modifier.padding(30.dp),
//        horizontalArrangement = Arrangement.spacedBy(20.dp)
//    ) {
//        Button(
//            onClick = { onPark(context,sharedPreferences, updateResponseText) },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//            modifier = Modifier.width(150.dp)
//
//        ) {
//            Text("Destination\nMarking",
//                fontSize = 18.sp,
//                color = Color.White,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//
//        }
//
//        Button(
//            onClick = { onNavigate(context, sharedPreferences, updateResponseText) },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
//            modifier = Modifier.width(150.dp)
//        ) {
//            Text("Navigate",
//                fontSize = 18.sp,
//                color = Color.White,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )
//        }
//    }
//}


@Composable
fun ButtonGroup(sharedPreferences: SharedPreferences, responseText: String, updateResponseText: (String) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.padding(30.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = { onPark(context, sharedPreferences, updateResponseText) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF11314F)),
            modifier = Modifier
                .width(150.dp)
                .height(60.dp) // ✅ Ensure both buttons have equal height
        ) {
            Text(
                "Destination\nMarking",
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2 // ✅ Ensures text doesn't expand the button
            )
        }

        Button(
            onClick = { onNavigate(context, sharedPreferences, updateResponseText) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF215F9A)),
            modifier = Modifier
                .width(150.dp)
                .height(60.dp) // ✅ Ensure both buttons have equal height
        ) {
            Text(
                "Navigate",
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2 // ✅ Ensures it takes the same space as the first button
            )
        }
    }
}


//fun onPark(context: Context, sharedPreferences: SharedPreferences, updateResponse: (String) -> Unit) {
//
//    var uuid = sharedPreferences.getString("uuid", null)
//
//
//    if (uuid == null) {
//        uuid = UUID.randomUUID().toString()
//
//        sharedPreferences.edit().apply {
//            putString("uuid", uuid)
//            apply()
//        }
//
//        // Log initial park action with new UUID
////        writeLogToFile(context, uuid, "New park session started with UUID: $uuid")
//    }
//
//
////    imuLogger = IMULogger(context, uuid)
//
//    val requestData = RequestData(uuid = uuid, action = "park")
//    RetrofitClient.apiService.updateClientDataPark(requestData).enqueue(object : Callback<ResponseData> {
//        override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    vibratePhone(context)
//                    updateResponse("${it.message}")
//
////                    writeLogToFile(context, uuid, "Park successful: ${it.message}")
//
//                    sharedPreferences.edit().apply {
//                        putString("tagNumber", it.tagNumber)
//                        apply()
//                    }
//                } ?: updateResponse("Success, but empty response.")
//            } else {
//                updateResponse("Failed to park: ${response.errorBody()?.string()}")
////                writeLogToFile(context, uuid, "Failed to park: ${response.errorBody()?.string()}")
//            }
//        }
//
//        override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//            updateResponse("Network error: ${t.message}")
////            writeLogToFile(context, uuid, "Network error: ${t.message}")
//        }
//    })
//}


fun onPark(context: Context, sharedPreferences: SharedPreferences, updateResponse: (String) -> Unit) {

    var uuid = sharedPreferences.getString("uuid", null)


    if (uuid == null) {
        uuid = UUID.randomUUID().toString()

        sharedPreferences.edit().apply {
            putString("uuid", uuid)
            apply()
        }

        // Log initial park action with new UUID
//        writeLogToFile(context, uuid, "New park session started with UUID: $uuid")
    }


//    imuLogger = IMULogger(context, uuid)

    val requestData = RequestData(uuid = uuid, action = "park")
    RetrofitClient.apiService.updateClientDataPark(requestData).enqueue(object : Callback<ResponseData> {
        override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    vibratePhone(context)
                    var webSocketConnected = false
                    var serialConnected = false
//                    updateResponse("${it.message}")
                    connectWebSocket(context, sharedPreferences, updateResponse) {
                        webSocketConnected = true
                        if (serialConnected) {
                            vibratePhone(context)// Check if serial was already connected when WebSocket completes
                            updateResponse("${it.message}")
//                                    writeLogToFile(context, uuid, "${it.message} to Tag Number: $tagNumber. Please start from Tag X9@#")
                        }
                    }

//                    writeLogToFile(context, uuid, "Park successful: ${it.message}")
                    connecttoESP32(context, updateResponse, uuid, "start") {
                        serialConnected = true
                        if (webSocketConnected) {
                            vibratePhone(context)// Check if WebSocket was already connected when serial completes
                            updateResponse("${it.message}")
                        }
                    }

//                    sharedPreferences.edit().apply {
//                        putString("tagNumber", it.tagNumber)
//                        apply()
//                    }
                } ?: updateResponse("Success, but empty response.")
            } else {
                updateResponse("Failed to park: ${response.errorBody()?.string()}")
//                writeLogToFile(context, uuid, "Failed to park: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<ResponseData>, t: Throwable) {
            updateResponse("Network error: ${t.message}")
//            writeLogToFile(context, uuid, "Network error: ${t.message}")
        }
    })
}







fun onNavigate(context: Context, sharedPreferences: SharedPreferences, updateResponse: (String) -> Unit) {
    val uuid = sharedPreferences.getString("uuid", null)
    val tagNumber = sharedPreferences.getString("tagNumber", "No Tag Number Assigned")

    if (uuid != null) {
//        writeLogToFile(context, uuid, "Navigate action triggered with UUID: $uuid")
//
//        if (uuid.isNotEmpty() && !sharedPreferences.getBoolean("isLoggingActive", false)) {
//            val imuLogger = IMULoggerInstance.getLogger(context, uuid)
//            imuLogger.startLogging()
//            sharedPreferences.edit().putBoolean("isLoggingActive", true).apply()
//            updateResponse("Navigation started with UUID: $uuid and IMU logging started.")
//        } else {
//            updateResponse("Navigation started with UUID: $uuid. IMU logging is already active or UUID is missing.")
//        }
        //
        //IMULogger(context, uuid).startLogging()

        val requestData = RequestData(uuid = uuid, action = "navigate")
        RetrofitClient.apiService.updateClientDataNavigate(requestData)
            .enqueue(object : Callback<ResponseData> {
                override fun onResponse(
                    call: Call<ResponseData>,
                    response: Response<ResponseData>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            var webSocketConnected = false
                            var serialConnected = false

                            connectWebSocket(context, sharedPreferences, updateResponse) {
                                webSocketConnected = true
                                if (serialConnected) {
                                    vibratePhone(context)// Check if serial was already connected when WebSocket completes
                                    updateResponse("${it.message} to Tag Number: $tagNumber. Please start from Tag 1")
//                                    writeLogToFile(context, uuid, "${it.message} to Tag Number: $tagNumber. Please start from Tag X9@#")
                                }
                            }
                            connecttoESP32(context, updateResponse, uuid, "start") {
                                serialConnected = true
                                if (webSocketConnected) {
                                    vibratePhone(context)// Check if WebSocket was already connected when serial completes
                                    updateResponse("${it.message} to Tag Number: $tagNumber. Please start from Tag 1")
                                }
                            }
                        } ?: updateResponse("Success, but empty response.")
                    } else {
                        updateResponse("Failed to navigate: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    updateResponse("Network error: ${t.message}")
                }
            })
    } else {
        updateResponse("Please press Park first to generate a UUID.")
    }
}



fun navigateToWifiSettings(context: Context) {
    context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
}

@Composable
fun ResponseBox(text: String) {
    Text(
        text = styleDirections(text),
        modifier = Modifier.padding(20.dp),
        style = TextStyle(fontSize = 24.sp, color = Color.Black)
    )
}

@Composable
fun DeleteEntryButton(sharedPreferences: SharedPreferences, updateResponseText: (String) -> Unit) {
    Button(
        onClick = {
            sharedPreferences.edit().clear().apply()
            updateResponseText("Entries deleted successfully")
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    ) {
        Text("Delete Entry", fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun ConnectESPButton(sharedPreferences: SharedPreferences, updateResponseText: (String) -> Unit) {
    val context = LocalContext.current
    val uuid = sharedPreferences.getString("uuid", null)
    Button(
        onClick = {
            if (uuid != null) {
                connecttoESP32(context, updateResponseText,uuid,"start") {
                    updateResponseText("Success! Connected and message sent.")
                }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text("Serial Comm with ESP", fontSize = 18.sp, color = Color.White)
    }
}



@Composable
fun Stop_Sending(sharedPreferences: SharedPreferences, updateResponseText: (String) -> Unit) {
    val context = LocalContext.current
    val uuid = sharedPreferences.getString("uuid", null)
    Button(
        onClick = {
            if (uuid != null) {
                connecttoESP32(context, updateResponseText,uuid,"stop") {
                    updateResponseText("Success! Connected and message sent.")
                }
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)

    ) {
        Text("Stop Sending", fontSize = 18.sp, color = Color.White)
    }
}



@Composable
fun ClearResponse(updateResponseText: (String) -> Unit) {
    Button(
        onClick = {updateResponseText("")},
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Text("Clear Response", fontSize =  18.sp, color = Color.White)
    }
}



fun connecttoESP32(context: Context, updateResponse: (String) -> Unit, uuid: String, action: String, onSuccess: () -> Unit) {
    val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    val deviceList = usbManager.deviceList
    val deviceInfo = deviceList.entries.joinToString(separator = "\n") {
        "Device Name: ${it.value.deviceName}, Vendor ID: ${it.value.vendorId}, Product ID: ${it.value.productId}"
    }

//    if (deviceInfo.isEmpty()) {
//        updateResponse("No USB devices connected.")
//    } else {
//        updateResponse(deviceInfo)
//    }
//    var messageToSend = ""
    val device = deviceList.values.find {
        it.vendorId == 4292 && it.productId == 60000 // Replace with your ESP32's actual VID and PID
    }

    device?.let {
        if (!usbManager.hasPermission(it)) {
            // Handle permission request here if not covered by intent filters and broadcast receiver
            updateResponse("Permission not granted for the device: ${it.deviceName}")
            return
        }

        val connection = usbManager.openDevice(it)
        val serial = UsbSerialDevice.createUsbSerialDevice(it, connection)
        if (serial != null && serial.open()) {
            serial.setBaudRate(115200)
            serial.setDataBits(UsbSerialInterface.DATA_BITS_8)
            //serial.setStopBits(UsbSerialInterface.STOPBITS_1)
            serial.setParity(UsbSerialInterface.PARITY_NONE)
            serial.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF)

            val successMessage = "success"
            var accumulatedData = ""

            val mCallback = object : UsbSerialInterface.UsbReadCallback {
                override fun onReceivedData(data: ByteArray) {
                    val receivedData = String(data)
                    accumulatedData += receivedData
                    if (accumulatedData.contains("\n")) {  // Assuming newline denotes end of message
                        val trimmedData = accumulatedData.trim()
                        val activity = context as Activity
                        updateResponse(trimmedData)
//                        updateResponse((trimmedData == successMessage).toString())
//
//                        if (trimmedData == successMessage){
//
//                            Log.d("SerialConnection", "Success message received, triggering onSuccess callback.")
//                            activity.runOnUiThread {
//
//                                onSuccess()
//                            }
//                        }
                        accumulatedData = ""  // Reset for next message
                    } // Pass received data to response box via a callback
                }
            }

            serial.read(mCallback)
            if(action=="start") {
                val messageToSend = "start UUID: $uuid\n"
                serial.write(messageToSend.toByteArray()) // Send "hi\n" to ESP32
            }
            else if(action=="stop"){
                val messageToSend = "stop\n"
                serial.write(messageToSend.toByteArray()) // Send "hi\n" to ESP32
            }


            val activity = context as Activity
            activity.runOnUiThread {
                onSuccess()
            }
        } else {
            updateResponse("Failed to open serial connection")
        }
    } ?: updateResponse("No ESP32 device connected or device not found")
}


@Preview(showBackground = true, name = "Health Check Panel")
@Composable
fun PreviewHealthCheckPanel() {
    Parking_NavigationTheme {
        HealthCheckPanel()
    }
}

@Preview(showBackground = true, name = "Main Screen Preview")
@Composable
fun PreviewMainScreen() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("PreviewPrefs", Context.MODE_PRIVATE)
    Parking_NavigationTheme {
        MainScreen(sharedPreferences)
    }
}

@Composable
fun HealthCheckPanel() {
    val api = remember { RetrofitClient.apiService }
    val scope = rememberCoroutineScope()

    var status by remember { mutableStateOf("Idle") }
    var body by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Backend Health", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        Button(
            enabled = !loading,
            onClick = {
                loading = true
                status = "Checking…"
                body = ""
                scope.launch {
                    try {
                        val resp = api.health()
                        status = if (resp.isSuccessful) "Health OK ✅" else "Health failed ❌"
                        body = resp.body()?.toString() ?: ""
                    } catch (e: Exception) {
                        status = "Health error: ${e.message}"
                        body = ""
                    } finally {
                        loading = false
                    }
                }
            }
        ) {
            Text(if (loading) "Checking…" else "Health Check")
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Status: $status",
            color = when {
                status.contains("error", ignoreCase = true) -> Color.Red
                status.contains("failed", ignoreCase = true) -> Color.Red
                status.contains("OK") -> Color(0xFF2E7D32) // green
                else -> Color.Unspecified
            }
        )

        if (body.isNotBlank()) {
            Spacer(Modifier.height(6.dp))
            Text("Response: $body")
        }
    }
}



