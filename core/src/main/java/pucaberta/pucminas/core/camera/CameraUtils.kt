package pucaberta.pucminas.core.camera

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

fun AppCompatActivity.setupQrCodeScanner(scanResult: (ScanIntentResult) -> Unit): ActivityResultLauncher<ScanOptions> {

    return registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
        scanResult.invoke(result)
    }
}


