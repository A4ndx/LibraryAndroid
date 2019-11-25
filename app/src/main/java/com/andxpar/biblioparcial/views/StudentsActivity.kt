package com.andxpar.biblioparcial.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.andxpar.biblioparcial.R
import com.andxpar.biblioparcial.data.getDB

class StudentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        val logOut = findViewById<Button>(R.id.logOut)
        val btScanQR = findViewById<Button>(R.id.bt_scanQR)

        val db = getDB(this)
        val cursor = db.rawQuery("select * from User", null)
        if (!(cursor.moveToFirst())) {
            switchActivity()
            cursor.close()
            db.close()
        }

        btScanQR.setOnClickListener {
            val intent = Intent(this, QRReaderActivity::class.java)
            intent.putExtra("type",0)
            startActivity(intent)
        }

        logOut.setOnClickListener {
            logOut()
        }
    }

    private fun logOut() {
        val db = getDB(this)
        db.delete("User", "", null)
        val it = Intent(this, LoginActivity::class.java)
        startActivity(it)
        finish()
    }

    private fun switchActivity() {
        val it = Intent(this, LoginActivity::class.java)
        startActivity(it)
    }
}

