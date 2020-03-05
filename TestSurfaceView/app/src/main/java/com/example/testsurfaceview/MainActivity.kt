package com.example.testsurfaceview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWidget()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                qrResult.text = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun initWidget() {
        fullScreenScan.setOnClickListener(::startQRScan)

        customScreenScan.setOnClickListener{
            val intent = Intent(this, CustomQr::class.java)
            startActivity(intent)
        }

        customFragmentScan.setOnClickListener {
//            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, CustomBarcodeFragment()).commit()
        }
    }

    private fun startQRScan(view: View) {
        val intentIntegrator = IntentIntegrator(this);
        intentIntegrator.setPrompt("QR 코드를 사각형 안에 비춰주세요.")
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.initiateScan();
    }

    private fun customQRScan(view: View) {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setCaptureActivity(CustomQr::class.java)
        intentIntegrator.initiateScan()
    }
}
