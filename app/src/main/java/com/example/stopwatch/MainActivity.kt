package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private var millisecond = 0
    private var second = 0
    private var minute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val displayTime : TextView = findViewById(R.id.time)
        val playButton : ImageButton = findViewById(R.id.play_button)
        val pauseButton : ImageButton = findViewById(R.id.pause_button)
        val resetButton : ImageButton = findViewById(R.id.reset_button)

        val handler : Handler = Handler(Looper.getMainLooper())
        var runnable : Runnable = Runnable { }

        // prevent the time from getting faster and faster every time the play button is tapped
        var isRunning = false

        // when the user clicks the play button, the time will start/continue
        playButton.setOnClickListener {
            if (!isRunning) {
                runnable = Runnable {
                    millisecond++;
                    displayTime.text = format()
                    handler.postDelayed(runnable, 100)
                }

                handler.postDelayed(runnable, 100)
            }
            isRunning = true

        }

        pauseButton.setOnClickListener {
            handler.removeCallbacks(runnable)
        }

        // set the time to 0
        resetButton.setOnClickListener {
            millisecond = 0
            second = 0
            minute = 0
            displayTime.text = format()
            isRunning = false
        }

    }

    // a helper method that helps with the format of the time
    private fun format() : String {
        var ms = ""
        var sec = ""
        var min = ""

        // if millisecond is more than or equal to 10, increment the second digit and millisecond becomes 0
        if (millisecond >= 10) {
            second++
            millisecond = 0
        }

        if (second >= 60) {
            minute++
            second = 0
        }

        ms += "$millisecond"
        if (second >= 10) {
            sec = "$second"
        } else {
            sec = "0$second"
        }

        if (minute >= 10) {
            min = "$minute"
        } else {
            min = "0$minute"
        }

        // concatenate the min, sec, and ms variables and store it to display
        val display = "$min:$sec.$ms"
        return display
    }
}
