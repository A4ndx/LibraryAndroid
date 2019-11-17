package com.andxpar.biblioparcial.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.andxpar.biblioparcial.R

class CrudBookFragment: Fragment(){

    private lateinit var bInsert: Button
    private lateinit var bErase: Button
    private lateinit var bMod: Button
    private lateinit var bSelec: Button
    private lateinit var sSede: Spinner
    private lateinit var tIdBook: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_crud_book, container, false)

        sSede = rootView.findViewById(R.id.sSede)

        val adapter = ArrayAdapter(container!!.context, android.R.layout.simple_spinner_item, sedes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sSede.adapter = adapter


        bInsert = rootView.findViewById(R.id.bt_insert)
        bErase = rootView.findViewById(R.id.bt_delete)
        bMod = rootView.findViewById(R.id.bt_mod)
        bSelec = rootView.findViewById(R.id.bt_consult)

        bInsert.setOnClickListener {
            Toast.makeText(context, "INSERTAR CON GEST LIBROS", Toast.LENGTH_SHORT).show()
        }

        bErase.setOnClickListener {
            Toast.makeText(context, "BORRAR CON GEST LIBROS", Toast.LENGTH_SHORT).show()
        }

        bMod.setOnClickListener {
            Toast.makeText(context, "MODIFICAR CON GEST LIBROS", Toast.LENGTH_SHORT).show()
        }

        bSelec.setOnClickListener {
            Toast.makeText(context, "MODIFICAR CON GEST LIBROS", Toast.LENGTH_SHORT).show()
        }


        return rootView
    }

    companion object {
        private val sedes =
            arrayOf("Sede Sur", "Sede Estación", "Sede Central")
    }

}