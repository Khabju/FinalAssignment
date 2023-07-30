package ge.ananeishvilitbarkaia.messengerapp.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ge.ananeishvilitbarkaia.messengerapp.model.RegisterUser
import ge.ananeishvilitbarkaia.messengerapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
class LoginViewModel: ViewModel() {
    val signInResult: MutableLiveData<RegisterUser> = MutableLiveData()
    val userData: MutableLiveData<User> = MutableLiveData()

    fun signInUser(email: String, password: String){
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val result = Firebase.auth.currentUser?.let { RegisterUser(true, "", it) }
                    signInResult.value = result
                } else {
                    val result = task.exception?.localizedMessage?.let { RegisterUser(false, it, null) }
                    signInResult.value = result
                }
            }
    }

    fun getUserFromFirebase(user: String){
        FirebaseDatabase.getInstance().reference.child("User").child(user).get().addOnSuccessListener {
            userData.value = it.getValue<User>()
        }
    }
}