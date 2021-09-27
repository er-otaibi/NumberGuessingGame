package com.example.numbergame

import android.annotation.SuppressLint
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var guessField: EditText
    lateinit var btnGuess: Button
    private lateinit var clRoot: ConstraintLayout
    lateinit var myRV: RecyclerView
     var messages = arrayListOf<String>()
    private var answer = 0
    private var guesses = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        answer = Random.nextInt(11)
        guessField = findViewById(R.id.myText)
        btnGuess = findViewById(R.id.myButton)
        myRV = findViewById(R.id.rvMain)

        myRV.adapter = RecyclerViewAdapter(messages)
        myRV.layoutManager = LinearLayoutManager(this)
        btnGuess.setOnClickListener { addMessage() }

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun addMessage(){
        val msg = guessField.text.toString()
        if(msg.isNotEmpty()){
            if(guesses>0){
                if(msg.toInt() == answer){
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    guesses--
                    messages.add("You guessed $msg")
                    messages.add("You have $guesses guesses left")
                }
                if(guesses==0){
                    disableEntry()
                    messages.add("You lose - The correct answer was $answer")
                    messages.add("Game Over")
                    showAlertDialog("Oops Game over, You Lose!\nThe correct answer was $answer.\n\nPlay again?")
                }
            }
            guessField.text.clear()
            guessField.clearFocus()
            myRV.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clRoot, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun disableEntry(){
        btnGuess.isEnabled = false
        btnGuess.isClickable = false
        guessField.isEnabled = false
        guessField.isClickable = false
    }

    private fun showAlertDialog(title: String) {

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage(title)
            .setCancelable(false)

            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })

            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })


        val alert = dialogBuilder.create()
        alert.setTitle("Game Over")
        alert.show()
    }

}
