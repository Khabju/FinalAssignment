package ge.ananeishvilitbarkaia.messengerapp.session

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {

    private var preference: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private val sharedPrefKey = "hishab_user"

    init {
        preference = context.getSharedPreferences(sharedPrefKey, Context.MODE_PRIVATE)
        editor = preference!!.edit()
    }

    fun setSignIn(isLogIn: Boolean) {
        editor!!.putBoolean("IS_SIGNIN", isLogIn)
        editor!!.apply()
    }

    fun getSignIn(): Boolean {
        return preference!!.getBoolean("IS_SIGNIN", false)
    }

    fun setUserId(userId: String) {
        editor!!.putString("UID", userId)
        editor!!.apply()
    }

    fun setUserName(userId: String) {
        editor!!.putString("U_NAME", userId)
        editor!!.apply()
    }

    fun setUserProfession(userId: String) {
        editor!!.putString("PROFESSION", userId)
        editor!!.apply()
    }
}