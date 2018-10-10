package com.plivotest.ui.activities

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.plivotest.R
import com.plivotest.callhandler.CallHandlerService
import com.plivotest.callhandler.CallHandlerService.eventListener
import com.plivotest.callhandler.listeners.CallEventListener
import com.plivotest.ui.fragments.CallProgressFragment
import com.plivotest.ui.fragments.DialerFragment
import com.plivotest.util.Utils


class PhoneCallActivity : AppCompatActivity(), CallEventListener {

    private var screenMode: Utils.ScreenMode = Utils.ScreenMode.DIALER;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.phone_call_activity)

        requestAppPermissions();

        initView()

        eventListener = this

        startSession()
    }

    private fun startSession() {

        val intent = Intent(this, CallHandlerService::class.java)
        intent.action = Utils.LOAD_USER_SESSION
        this!!.startService(intent)
    }

    private fun initView() {

        if (intent.hasExtra(Utils.SCR_MODE_KEY)) {

            screenMode = Utils.ScreenMode.valueOf(intent.getStringExtra(Utils.SCR_MODE_KEY))
        }

        loadFragment("", screenMode)
    }

    fun loadFragment(numberText: String, screenMode: Utils.ScreenMode) {

        var fragment: Fragment;

        when (screenMode) {

            Utils.ScreenMode.DIALER -> fragment = DialerFragment()

            Utils.ScreenMode.CALL_PROGRESS -> fragment = CallProgressFragment.getInstance(numberText)
        }

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, "")
                .commit()
    }

    override fun onCallFailed() {
        loadFragment("", Utils.ScreenMode.DIALER)
    }

    override fun onShowMessage(msg : String) {

        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    override fun onShowDialer() {

        loadFragment("", Utils.ScreenMode.DIALER)
    }

    override fun onAccept() {

    }

    override fun onReject() {

    }

    fun requestAppPermissions() {

        if (Build.VERSION.SDK_INT < 23) {
            // your code

        } else {

            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.RECORD_AUDIO, Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.WRITE_SETTINGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.WAKE_LOCK, Manifest.permission.VIBRATE, Manifest.permission.READ_LOGS, Manifest.permission.USE_SIP, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    10002)
        }

    }
}
