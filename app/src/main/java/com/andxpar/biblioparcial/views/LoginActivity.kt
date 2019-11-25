package com.andxpar.biblioparcial.views

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.andxpar.biblioparcial.data.User
import org.json.JSONException
import android.util.Log
import android.view.View
import com.andxpar.biblioparcial.R
import com.andxpar.biblioparcial.data.getDB

class LoginActivity : AppCompatActivity() {

    lateinit var user: User
    var idUser = -1
    private lateinit var queue: RequestQueue
    private lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        loading = findViewById(R.id.loading)

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            queue = Volley.newRequestQueue(this)
            login(username.text.toString(), password.text.toString())
        }

    }

    override fun onStart() {
        super.onStart()
        val db = getDB(this)
        val cursor = db.rawQuery("select * from User", null)
        if (cursor.moveToFirst()) {
            switchActivity(cursor.getString(6))
            cursor.close()
            db.close()
        }
    }

    private fun login(username: String, password: String) {

        val db = getDB(this)
        val cursor = db.rawQuery("select * from User", null)

        if (cursor.moveToFirst()) {
            Log.d("LOG", cursor.getString(6))
            switchActivity(cursor.getString(6))
            cursor.close()
            db.close()
        } else {
            cursor.close()
            db.close()
            val url =
                "https://uniajcbiblio.000webhostapp.com/user.php?user=$username&password=$password"
            user = User(-1, "", "", "", "", "", "", "")
            Log.d("LOGMASTER", "Antes de Synctask")
            val request = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val jsonArray = response.getJSONArray("user")
                        var cont = 0
                        while (cont < jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(cont)
                            idUser = jsonObject.getInt("idUser")
                            user.dni = jsonObject.getInt("dni")
                            user.name = jsonObject.getString("name")
                            user.lastName = jsonObject.getString("lastName")
                            user.faculty = jsonObject.getString("faculty")
                            user.program = jsonObject.getString("program")
                            user.role = jsonObject.getString("role")
                            user.user = jsonObject.getString("user")
                            user.password = jsonObject.getString("password")
                            cont++
                        }

                        if (user.dni > 1 && idUser >= 0 && user.name != "1") {
                            val db = getDB(this)
                            val values = ContentValues()
                            values.put("idUser", idUser)
                            values.put("dni", user.dni)
                            values.put("name", user.name)
                            values.put("lastName", user.lastName)
                            values.put("faculty", user.faculty)
                            values.put("program", user.program)
                            values.put("role", user.role)
                            values.put("user", user.user)
                            values.put("password", user.password)
                            db.insert("User", null, values)
                            db.close()
                            Toast.makeText(this, "Bienvenido ${user.name}", Toast.LENGTH_SHORT)
                                .show()

                            switchActivity(user.role)
                        } else {
                            Toast.makeText(this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                    }
                }, Response.ErrorListener {
                    Toast.makeText(this, "ERROR: $it", Toast.LENGTH_LONG).show()
                })
            queue.add(request)

        }
        loading.visibility = View.GONE
    }


    private fun switchActivity(role: String) {
        when (role) {
            "docente" -> {
                val it = Intent(this, TeachersActivity::class.java)
                startActivityForResult(it, 56)
            }
            "estudiante" -> {
                val it = Intent(this, StudentsActivity::class.java)
                startActivityForResult(it, 55)
            }
            "administrador" -> {
                val it = Intent(this, AdminActivity::class.java)
                startActivityForResult(it, 50)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 56) {
            val it = Intent(this, TeachersActivity::class.java)
            startActivityForResult(it, 56)
        } else if (requestCode == 55) {
            val it = Intent(this, StudentsActivity::class.java)
            startActivityForResult(it, 55)
        }else if (requestCode == 50) {
        val it = Intent(this, AdminActivity::class.java)
        startActivityForResult(it, 50)
    }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }
}
