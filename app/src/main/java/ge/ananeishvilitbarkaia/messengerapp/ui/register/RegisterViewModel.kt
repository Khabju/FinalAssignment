package ge.ananeishvilitbarkaia.messengerapp.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ge.ananeishvilitbarkaia.messengerapp.model.RegisterUser
import ge.ananeishvilitbarkaia.messengerapp.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
class RegisterViewModel: ViewModel() {
    val isRegDone: MutableLiveData<RegisterUser> = MutableLiveData()
    val isUserAdded: MutableLiveData<Boolean> = MutableLiveData()
    fun registerUser(email: String, password: String){
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val registerUser = Firebase.auth.currentUser?.let { RegisterUser(true, "", it) }
                    isRegDone.value = registerUser
                } else {
                    val registerUser = task.exception?.localizedMessage?.let { RegisterUser(false, it, null) }
                    isRegDone.value = registerUser
                }
            }
    }

    fun addUserToTheServer(user: User){
        FirebaseDatabase.getInstance().reference.child("User").child(user.uid).setValue(user).addOnCompleteListener {
            isUserAdded.value = it.isSuccessful
        }
    }
}