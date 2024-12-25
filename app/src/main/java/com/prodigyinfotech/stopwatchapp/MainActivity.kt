package com.prodigyinfotech.stopwatchapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prodigyinfotech.stopwatchapp.ui.theme.StopWatchAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

var job:Job? = null

var seconds by mutableIntStateOf(0)
var minutes by mutableIntStateOf(0)
var hours by mutableIntStateOf(0)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StopWatchAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ){
        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Hours")
            SpaceItem()
            Text(text = "Minutes")
            SpaceItem()
            Text(text = "Seconds")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row (
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = hours.toString())
            SpaceItem()
            Text(text = minutes.toString())
            SpaceItem()
            Text(text = seconds.toString())
        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                startStopWatch()
            }
        ) {
            Text(text = "Start Stopwatch")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                stopStopWatch()
            }
        ) {
            Text(text = "Stop Stopwatch")
        }
    }
}

@Composable
fun SpaceItem(){
    Row (
        modifier = Modifier.wrapContentWidth()
    ){
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = ":",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}

fun startStopWatch(){
    job = CoroutineScope(Dispatchers.IO).launch {
        while(isActive){
            if(seconds == 60){
                seconds = 0
                if(minutes == 60){
                    hours++
                    minutes = 0
                }
                else{
                    minutes++
                }
            }
            else{
                seconds++
            }
            delay(1000)
        }
    }
}

fun stopStopWatch(){
    job?.cancel()
    job = null
    seconds = 0
    minutes = 0
    hours = 0
}