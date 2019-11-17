package com.andxpar.biblioparcial.views

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.andxpar.biblioparcial.R
import com.andxpar.biblioparcial.data.User
import org.json.JSONException
import java.net.URL

class CrudUserFragment : Fragment() {

    private lateinit var queue: RequestQueue
    private lateinit var bInsert: Button
    private lateinit var bErase: Button
    private lateinit var bMod: Button
    private lateinit var bSelec: Button
    private lateinit var sRoles: Spinner
    private lateinit var tCedu: AutoCompleteTextView
    private lateinit var tNomb: AutoCompleteTextView
    private lateinit var tApell: AutoCompleteTextView
    private lateinit var tFacul: AutoCompleteTextView
    private lateinit var tProgram: AutoCompleteTextView
    private lateinit var tUser: AutoCompleteTextView
    private lateinit var tPass: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_crud_user, container, false)

        tCedu = rootView.findViewById(R.id.tCedu)
        tNomb = rootView.findViewById(R.id.tNomb)
        tApell = rootView.findViewById(R.id.tApell)
        tFacul = rootView.findViewById(R.id.tFacul)
        tProgram = rootView.findViewById(R.id.tProgram)
        tUser = rootView.findViewById(R.id.tUser)
        tPass = rootView.findViewById(R.id.tPass)
        sRoles = rootView.findViewById(R.id.sRole)

        val adapter = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sRoles.adapter = adapter

        bInsert = rootView.findViewById(R.id.bt_insert)
        bErase = rootView.findViewById(R.id.bt_delete)
        bMod = rootView.findViewById(R.id.bt_mod)
        bSelec = rootView.findViewById(R.id.bt_consult)


        bInsert.setOnClickListener {
            if (tCedu.text.isNotBlank() && tNomb.text.isNotBlank() && tApell.text.isNotBlank() && tFacul.text.isNotBlank() && tProgram.text.isNotBlank() && tUser.text.isNotBlank() && tPass.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudUser.php?dni=${tCedu.text}&name=${tNomb.text}&lastName=${tApell.text}" +
                            "&faculty=${tFacul.text}&program=${tProgram.text}&role=${if (sRoles.selectedItemPosition == 0) "estudiante" else "docente"}" +
                            "&user=${tUser.text}&password=${tPass.text}&type=4"
                // Formulate the request and handle the response.
                val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> {
                        if(it == "false"){
                            Toast.makeText(
                                context,
                                "Error al insertar el usuario",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else if(it == "true"){
                            Toast.makeText(
                                context,
                                "Usuario insertado con exito",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        // Handle error
                        Toast.makeText(
                            context,
                            "ERROR: %s".format(error.toString()),
                            Toast.LENGTH_LONG
                        ).show()
                    })

                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            } else {
                Toast.makeText(
                    context,
                    "Por favor llenar todos los campos para insertar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        bErase.setOnClickListener {
            if (tCedu.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudUser.php?dni=${tCedu.text}&type=3"
                // Formulate the request and handle the response.
                val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> {
                        if(it == "false"){
                            Toast.makeText(
                                context,
                                "Cedula no existe, el usuario existe",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else if(it == "true"){
                            Toast.makeText(
                                context,
                                "Usuario eliminado con exito",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    Response.ErrorListener { error ->
                        // Handle error
                        Toast.makeText(
                            context,
                            "ERROR: %s".format(error.toString()),
                            Toast.LENGTH_LONG
                        ).show()
                    })

                // Add the request to the RequestQueue.
                queue.add(stringRequest)
            } else {
                Toast.makeText(
                    context,
                    "Por favor llenar la cedula para eliminar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        bMod.setOnClickListener {
            if (tCedu.text.isNotBlank()) {
                if (!(tNomb.text.isNotBlank() && tApell.text.isNotBlank() && tFacul.text.isNotBlank() && tProgram.text.isNotBlank() && tUser.text.isNotBlank() && tPass.text.isNotBlank())) {
                    Toast.makeText(
                        context,
                        "Se modifico unicamente el rol ya que es un valor predeterminado",
                        Toast.LENGTH_LONG
                    ).show()
                }
                    queue = Volley.newRequestQueue(context)
                    val url =
                        "https://uniajcbiblio.000webhostapp.com/crudUser.php?dni=${tCedu.text}&name=${tNomb.text}&lastName=${tApell.text}" +
                                "&faculty=${tFacul.text}&program=${tProgram.text}&role=${if (sRoles.selectedItemPosition == 0) "estudiante" else "docente"}" +
                                "&user=${tUser.text}&password=${tPass.text}&type=2"
                Log.d("LOGMASTER", url)
                    // Formulate the request and handle the response.
                    val stringRequest = StringRequest(Request.Method.GET, url,
                        Response.Listener<String> {
                            if(it == "false"){
                                Toast.makeText(
                                    context,
                                    "Cedula no existe, ningun campo modificado",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            else if(it == "true"){
                                Toast.makeText(
                                    context,
                                    "Usuario modificado con exito",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        },
                        Response.ErrorListener { error ->
                            // Handle error
                            Toast.makeText(
                                context,
                                "ERROR: %s".format(error.toString()),
                                Toast.LENGTH_LONG
                            ).show()
                        })

                    // Add the request to the RequestQueue.
                    queue.add(stringRequest)
            } else {
                Toast.makeText(
                    context,
                    "Por favor llenar campo de cedula para modificar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        bSelec.setOnClickListener {
            if (tCedu.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudUser.php?dni=${tCedu.text}&type=1"
                val user = User(-1, "", "", "", "", "", "", "")
                Log.d("LOGMASTER", "Antes de Synctask")
                val request = JsonObjectRequest(Request.Method.GET, url, null,
                    Response.Listener { response ->
                        try {
                            val jsonArray = response.getJSONArray("user")
                            var cont = 0
                            while (cont < jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(cont)
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
                            if (user.dni >= 0 && user.dni != 1) {

                                tCedu.setText(user.dni.toString())
                                tNomb.setText(user.name)
                                tApell.setText(user.lastName)
                                tFacul.setText(user.faculty)
                                tProgram.setText(user.program )
                                tUser.setText(user.user)
                                tPass.setText(user.password)
                                if (user.role == "estudiante") {
                                    sRoles.setSelection(0)
                                } else if (user.role == "docente") {
                                    sRoles.setSelection(1)
                                }

                            } else {
                                Toast.makeText(context, "No existe el usuario", Toast.LENGTH_SHORT)
                                    .show()
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                        }
                    }, Response.ErrorListener {
                        Toast.makeText(context, "ERROR: $it", Toast.LENGTH_LONG).show()
                    })
                queue.add(request)
            } else {
                Toast.makeText(
                    context,
                    "Por favor llenar campo de cedula para consultar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        return rootView
    }

    companion object {
        private val roles =
            arrayOf("Estudiante", "Docente")
    }
}