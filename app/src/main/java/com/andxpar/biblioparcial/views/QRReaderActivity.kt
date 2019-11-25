package com.andxpar.biblioparcial.views

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.andxpar.biblioparcial.R
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.util.Log
import android.widget.Toast
import com.andxpar.biblioparcial.data.getDB
import kotlinx.android.synthetic.main.activity_qr_reader.*
import org.json.JSONException
import org.json.JSONObject




class QRReaderActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var miScannerView: ZXingScannerView
    private var bookID = ""
    private var bookName = ""
    private var bookAuthor = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reader)

        bt_scan.setOnClickListener {
            miScannerView = ZXingScannerView(this)
            setContentView(miScannerView)
            miScannerView.setResultHandler(this)
            miScannerView.startCamera()
        }
        bt_quit.setOnClickListener {
            finish()
        }

        bt_save.setOnClickListener {
            if (bookID.isNotBlank() && bookName.isNotBlank() && bookAuthor.isNotBlank()){
                val type = intent.getIntExtra("type", 3)
                if (type == 0){
                    val db = getDB(this)
                    val cursor = db.rawQuery("select COUNT(*) from bookEstudiante", null)
                    if (cursor.moveToFirst()) {
                        tvCant.text = cursor.getInt(0).toString()
                        cursor.close()
                        val values = ContentValues()
                        values.put("codigo", bookID)
                        values.put("nombre", bookName)
                        values.put("autor", bookAuthor)
                        db.insert("bookEstudiante", null, values)
                        db.close()
                    } else {
                        cursor.close()
                        db.close()
                    }
                }
                else if (type == 1){
                    val db = getDB(this)
                    val cursor = db.rawQuery("select COUNT(*) from bookDocente", null)
                    if (cursor.moveToFirst()) {
                        tvCant.text = cursor.getInt(0).toString()
                        cursor.close()
                        val values = ContentValues()
                        values.put("codigo", bookID)
                        values.put("nombre", bookName)
                        values.put("autor", bookAuthor)
                        db.insert("bookDocente", null, values)
                        db.close()
                    } else {
                        cursor.close()
                        db.close()
                    }
                }
                else{
                    Log.d("ERROR", "ERROR NO DEBERIA DE APARECER ESTO..... WHAT THE HELL")
                }
            }
            else{
                Toast.makeText(this,"Por favor escanee un libro para poder prestarselo",Toast.LENGTH_LONG).show()
            }
        }

        bt_delete.setOnClickListener {
            this.tvId.text = ""
            this.tvName.text = ""
            this.tvAuthor.text = ""
        }

    }

    override fun handleResult(result: Result?) {
        Log.v("HandleResult", result?.text.toString())
        miScannerView.resumeCameraPreview(this)
        miScannerView.removeAllViews() //<- here remove all the views, it will make an Activity having no View
        miScannerView.stopCamera() //<- then stop the camera
        setContentView(R.layout.activity_qr_reader)

        try {
            val mainObject = JSONObject(result?.text.toString())
            val bookObject = mainObject.getJSONObject("book")
            bookID = bookObject.getString("id")
            bookName = bookObject.getString("name")
            bookAuthor = bookObject.getString("author")
        }catch (e:JSONException){
            Log.d("TRY CATCH", e.message)
        }

        Log.d("JSON", bookID + bookName + bookAuthor)
        this.tvId.text = bookID
        this.tvName.text = bookName
        this.tvAuthor.text = bookAuthor
    }
}