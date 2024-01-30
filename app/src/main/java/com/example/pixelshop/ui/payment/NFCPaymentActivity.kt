package com.example.pixelshop.ui.payment

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pixelshop.ui.theme.MyShopTheme



class NFCPaymentActivity : ComponentActivity() {
    var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyShopTheme {
                // Initialize the NFC adapter for this context.
                nfcAdapter = NfcAdapter.getDefaultAdapter(this)
                // Check for NFC support and enablement on the device.
                if (nfcAdapter == null) {
                    Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
                } else if (!nfcAdapter!!.isEnabled) {
                    Toast.makeText(this, "NFC is disabled.", Toast.LENGTH_LONG).show()
                }
                // Prepare the NFC Intent filter.
                createNFCIntentFilter()
            }
        }
    }


    // Creates an IntentFilter for NFC to detect NFC tags with NDEF data.
    private fun createNFCIntentFilter(): Array<IntentFilter> {
        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            intentFilter.addDataType("*/*")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("Failed to add MIME type.", e)
        }
        return arrayOf(intentFilter)
    }


    // Handles new intents, typically when an NFC tag is discovered.
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            Toast.makeText(this, "NFC tag detected: ${intent.action.toString()}", Toast.LENGTH_SHORT).show()
        }
        if (intent?.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )
        // Define intent filters for NFC actions.
        val intentFilters = arrayOf<IntentFilter>(
            IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
            IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
        )


        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }


    // Disable foreground dispatch to allow other apps to handle NFC tags.
    override fun onPause() {
        super.onPause()
        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter.disableForegroundDispatch(this)
    }

}



