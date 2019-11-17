package com.andxpar.biblioparcial.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.andxpar.biblioparcial.R
import com.andxpar.biblioparcial.data.getDB

class AdminActivity : AppCompatActivity() {


    private  var isFragmentOneLoaded = false
    private val manager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        showCrud(0)
        val btCrudUser = findViewById<Button>(R.id.crudUsers)
        val btCrudBook = findViewById<Button>(R.id.crudBooks)
        val logOut = findViewById<Button>(R.id.logOut)

        val db = getDB(this)
        val cursor = db.rawQuery("select * from User", null)
        if (!(cursor.moveToFirst())) {
            switchActivity()
            cursor.close()
            db.close()
        }
        btCrudUser.isEnabled = false

        logOut.setOnClickListener {
            logOut()
        }


        btCrudUser.setOnClickListener {
            btCrudUser.isEnabled = false
            if(!isFragmentOneLoaded){
                showCrud(0)
                btCrudBook.isEnabled = true
            }else{
                btCrudUser.isEnabled = true
            }
        }

        btCrudBook.setOnClickListener {
            btCrudBook.isEnabled = false
            if(isFragmentOneLoaded){
                showCrud(1)
                btCrudUser.isEnabled = true
            }
            else{
                btCrudBook.isEnabled = true
            }
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

    private fun showCrud(i: Int){
        val transaction = manager.beginTransaction()
        val fragment = if(i == 0)CrudUserFragment()else CrudBookFragment()
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
        transaction.replace(R.id.frameLayout, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
        isFragmentOneLoaded = i == 0
    }



}
