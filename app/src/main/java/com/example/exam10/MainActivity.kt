package com.example.exam10

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import java.io.File
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG = "MAIN_ACT"

    val passwordsClear = "1\n2\n3\n4\n" +
            "5\n" +
            "666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666\n"

    lateinit var keyVaultCrypter: CryptoHelper

    lateinit var passFileEncrypted: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        keyVaultCrypter = CryptoHelper(context = applicationContext)
        passFileEncrypted = keyVaultCrypter.encryptNew(passwordsClear.toByteArray(), "password_encrypted.txt")

        val btn_passCheck = findViewById<Button>(R.id.btn_passCheck)

        btn_passCheck.setOnClickListener {
            val inputPass: String = findViewById<TextInputEditText>(R.id.password).text.toString()
            val clearPasses: String = keyVaultCrypter.decrypt(passFileEncrypted.readBytes()).decodeToString()
//            val tmpList = listOf<Item>()
//            for (clearPasses) {
//                tmpList
//            }
//            listOf(clearPasses)
////            for (pass in clearPasses) {
////                passesArray.(Item(password = pass))
////            }
            if (inputPass in clearPasses) {
                val intentPassList = Intent(applicationContext, PassListActivity::class.java)
                intentPassList.putExtra("passwords", clearPasses)
                startActivity(intentPassList)
            }
        }
    }
}