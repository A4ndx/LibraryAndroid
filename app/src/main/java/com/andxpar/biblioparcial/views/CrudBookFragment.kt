package com.andxpar.biblioparcial.views

import android.os.Bundle
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
import com.andxpar.biblioparcial.data.Book
import org.json.JSONException

class CrudBookFragment : Fragment() {

    private lateinit var queue: RequestQueue
    private lateinit var bInsert: Button
    private lateinit var bErase: Button
    private lateinit var bMod: Button
    private lateinit var bSelec: Button
    private lateinit var tIdBook: AutoCompleteTextView
    private lateinit var tISBN: AutoCompleteTextView
    private lateinit var tEditorial: AutoCompleteTextView
    private lateinit var tYear: AutoCompleteTextView
    private lateinit var tAuthor: AutoCompleteTextView
    private lateinit var tKnowledgeArea: AutoCompleteTextView
    private lateinit var tQuantity: AutoCompleteTextView
    private lateinit var sCampusAva: Spinner
    private lateinit var tComments: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_crud_book, container, false)

        tISBN = rootView.findViewById(R.id.tISBN)
        tEditorial = rootView.findViewById(R.id.tEditorial)
        tYear = rootView.findViewById(R.id.tAño)
        tAuthor = rootView.findViewById(R.id.tAutor)
        tKnowledgeArea = rootView.findViewById(R.id.tArea)
        tQuantity = rootView.findViewById(R.id.tCant)
        sCampusAva = rootView.findViewById(R.id.sSede)
        tComments = rootView.findViewById(R.id.tNews)

        val adapter = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_item, sedes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sCampusAva.adapter = adapter

        tIdBook = rootView.findViewById(R.id.tIdBook)
        bInsert = rootView.findViewById(R.id.bt_insert)
        bErase = rootView.findViewById(R.id.bt_delete)
        bMod = rootView.findViewById(R.id.bt_mod)
        bSelec = rootView.findViewById(R.id.bt_consult)

        bInsert.setOnClickListener {
            if (tIdBook.text.isNotBlank() && tISBN.text.isNotBlank() && tEditorial.text.isNotBlank() && tYear.text.isNotBlank() && tAuthor.text.isNotBlank() && tKnowledgeArea.text.isNotBlank() && tQuantity.text.isNotBlank() && tComments.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudBook.php?idBook=${tIdBook.text}&ISBN=${tISBN.text}&editorial=${tEditorial.text}&year=${tYear.text}" +
                            "&author=${tAuthor.text}&knowledgeArea=${tKnowledgeArea.text}&quantity=${tQuantity.text}&campusAval=${sCampusAva.selectedItemPosition}" +
                            "&comments=${tComments.text}&type=4"
                // Formulate the request and handle the response.
                val stringRequest = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener<String> {
                        if (it == "false") {
                            Toast.makeText(
                                context,
                                "Error al insertar el libro",
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (it == "true") {
                            Toast.makeText(
                                context,
                                "Libro insertado con exito",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else{
                            Log.d("data",url)
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
            if (tIdBook.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudBook.php?idBook=${tIdBook.text}&type=3"
                // Formulate the request and handle the response.
                val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> {
                        if(it == "false"){
                            Toast.makeText(
                                context,
                                "Codigo de libro no existe",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else if(it == "true"){
                            Toast.makeText(
                                context,
                                "Libro eliminado con exito",
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
                    "Por favor llenar el codigo del libro a eliminar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        bMod.setOnClickListener {
            if (tIdBook.text.isNotBlank()) {
                if (!(tISBN.text.isNotBlank() && tEditorial.text.isNotBlank() && tYear.text.isNotBlank() && tAuthor.text.isNotBlank() && tKnowledgeArea.text.isNotBlank() && tQuantity.text.isNotBlank() && tComments.text.isNotBlank())) {
                    Toast.makeText(
                        context,
                        "Se modifico unicamente el rol ya que es un valor predeterminado",
                        Toast.LENGTH_LONG
                    ).show()
                }
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudBook.php?idBook=${tIdBook.text}&ISBN=${tISBN.text}&editorial=${tEditorial.text}&year=${tYear.text}" +
                            "&author=${tAuthor.text}&knowledgeArea=${tKnowledgeArea.text}&quantity=${tQuantity.text}&campusAval=${sCampusAva.selectedItemPosition}" +
                            "&comments=${tComments.text}&type=2"
                Log.d("LOGMASTER", url)
                // Formulate the request and handle the response.
                val stringRequest = StringRequest(Request.Method.GET, url,
                    Response.Listener<String> {
                        if(it == "false"){
                            Toast.makeText(
                                context,
                                "Codigo de libro no existe, ningun campo modificado",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        else if(it == "true"){
                            Toast.makeText(
                                context,
                                "Libro modificado con exito",
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
                    "Por favor llenar campo de CODIGO para modificar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        bSelec.setOnClickListener {
            if (tIdBook.text.isNotBlank()) {
                queue = Volley.newRequestQueue(context)
                val url =
                    "https://uniajcbiblio.000webhostapp.com/crudBook.php?idBook=${tIdBook.text}&type=1"
                val book = Book(-1, -1, "", -1, "", "", -1, 0,"")
                Log.d("LOGMASTER", "Antes de Synctask")
                val request = JsonObjectRequest(Request.Method.GET, url, null,
                    Response.Listener { response ->
                        try {
                            val jsonArray = response.getJSONArray("book")
                            var cont = 0
                            while (cont < jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(cont)
                                book.idbook = jsonObject.getInt("idBook")
                                book.ISBN = jsonObject.getInt("ISBN")
                                book.editorial = jsonObject.getString("editorial")
                                book.year = jsonObject.getInt("year")
                                book.author = jsonObject.getString("author")
                                book.knowledgeArea = jsonObject.getString("knowledgeArea")
                                book.quantity = jsonObject.getInt("quantity")
                                book.campusAva = jsonObject.getInt("campusAval")
                                book.comments = jsonObject.getString("comments")
                                cont++
                            }
                            if (book.idbook >= 0 && book.idbook != 1) {

                                tIdBook.setText(book.idbook.toString())
                                tISBN.setText(book.ISBN.toString())
                                tEditorial.setText(book.editorial)
                                tYear.setText(book.year.toString())
                                tAuthor.setText(book.author )
                                tKnowledgeArea.setText(book.knowledgeArea)
                                tQuantity.setText(book.quantity.toString())
                                sCampusAva.setSelection(book.campusAva)
                                tComments.setText(book.comments)

                            } else {
                                Toast.makeText(context, "No existe el libro", Toast.LENGTH_SHORT)
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
                    "Por favor llenar campo de codigo de libro para consultar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        return rootView
    }

    companion object {
        private val sedes =
            arrayOf("Sede Sur", "Sede Estación", "Sede Central")
    }

}