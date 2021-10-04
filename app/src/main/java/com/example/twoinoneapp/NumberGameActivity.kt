package com.example.twoinoneapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.putString
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class NumberGameActivity : AppCompatActivity() {
    private lateinit var myCL1: ConstraintLayout
    private lateinit var input: EditText
    private lateinit var guessBtn: Button
    private lateinit var messages: ArrayList<String>
    private lateinit var textView: TextView
    private lateinit var rv: RecyclerView
    private var guesses = 3
    var someNumber: Int = 0
    var myMessage: String = ""

    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_game)

        myCL1 = findViewById(R.id.myCL1)
        messages = ArrayList()

        rv = findViewById<RecyclerView>(R.id.rv1)
        rv.adapter = Message(this, messages)
        rv.layoutManager = LinearLayoutManager(this)

        textView = findViewById(R.id.textView)
        input = findViewById(R.id.input)
        guessBtn = findViewById(R.id.btn)
        guessBtn.setOnClickListener {
            // check the input only numbers
            if (isNumber(input.text.toString())) {
                checkGuess(input.text.toString())
            } else {
                Snackbar.make(myCL1, "Please enter numbers only!", Snackbar.LENGTH_LONG).show()
            }
        }
        // change title of the page
        title = "Numbers Game"
        //auto scroller after add
//        rvMain.adapter?.notifyDataSetChanged()

        //shared preference
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        myMessage = sharedPreferences.getString("myMessage", "").toString()  // --> retrieves data from Shared Preferences
// We can save data with the following code
        with(sharedPreferences.edit())
        {
            putString("myMessage", myMessage)
            apply()
        }
    }

    // Save data on Rotating the device
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("myNumber", someNumber)
        outState.putString("myMessage", myMessage)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        someNumber = savedInstanceState.getInt("myNumber", 0)
        myMessage = savedInstanceState.getString("myMessage", "No Message")
    }




    private fun checkGuess(inpu : String){
        val random = Random.nextInt(10)

        if(inpu.contentEquals(random.toString()) ){
            textView.setTextColor(Color.GREEN)
            textView.text = "You Got it!!"

        }else{
            textView.setTextColor(Color.RED)
            textView.text = "Wrong try again! "
            guesses --
            messages.add("You guessed $inpu")
            messages.add("You have $guesses guesses left")
            if(guesses == 0){
                messages.add("You lose - The correct answer was $random")
                messages.add("Game Over")
                showAlertDialog("You lose...\nThe correct answer was $random.\n\nPlay again?")
            }
        }
        input.text.clear()
        input.clearFocus()
        rv.adapter?.notifyDataSetChanged()
    }
    private fun isNumber(s: String?): Boolean {
        return if (s.isNullOrEmpty()) false else s.all { Character.isDigit(it) }
    }

    // show Alert with two options
    private fun showAlertDialog(title: String) {
        // 1 build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)
        // 2 set message of alert dialog
        dialogBuilder.setMessage(title)
            //  3 if the dialog is cancelable
            .setCancelable(false)
            // 4 -a positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            // 4 -b negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        // 5 create dialog box
        val alert = dialogBuilder.create()
        //  6 set title for alert dialog box
        alert.setTitle("Game Over")
        alert.show()
    }
    //                       MENU
    // change title on menu
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem = menu!!.getItem(0)
        item.title = "New Game"
        return super.onPrepareOptionsMenu(menu)
    }
    // set menu to inflater
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu , menu)
        return true
    }
    // set action when item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.game1 -> {
                val intent = Intent(this , NumberGameActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.game2 -> {
                val intent = Intent(this , GuessPhraseActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.mainMenu -> {
                val intent = Intent(this , MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

