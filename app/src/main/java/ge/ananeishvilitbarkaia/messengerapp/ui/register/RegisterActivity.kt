package ge.ananeishvilitbarkaia.messengerapp.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ge.ananeishvilitbarkaia.messengerapp.databinding.ActivityRegisterBinding
import ge.ananeishvilitbarkaia.messengerapp.model.User
import ge.ananeishvilitbarkaia.messengerapp.session.SharedPref
import ge.ananeishvilitbarkaia.messengerapp.utils.isEmailValid
import ge.ananeishvilitbarkaia.messengerapp.utils.isPasswordValid
import com.google.firebase.auth.FirebaseUser
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var sharedPref: SharedPref
    private var email: String = ""
    private var name: String = ""
    private var profession: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPref = SharedPref(this)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        viewModel.isRegDone.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                setUserData(it.currentUser)
            } else {
                binding.progressView.visibility = View.GONE
                Toast.makeText(this, "Register Failed: ${it.errorMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.isUserAdded.observe(this) {
            if (it) {
                goNext()
            } else {
                binding.progressView.visibility = View.GONE
                Toast.makeText(this, "Data Not Saved", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSignUp.setOnClickListener {
            registerUser()
        }
    }

    private fun goNext() {
        sharedPref.setSignIn(true)
        finish()
    }

    private fun setUserData(currentUser: FirebaseUser?) {
        val user = User(currentUser!!.uid, email, name, profession, "")
        viewModel.addUserToTheServer(user)
        sharedPref.setUserId(currentUser.uid)
        sharedPref.setUserName(name)
        sharedPref.setUserProfession(profession)
    }

    private fun registerUser() {
        name = binding.etNickname.text.toString()
        email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        profession = binding.etWhatIDo.text.toString()

        if (email.isEmpty()) {
            binding.etEmail.error = "Email Is Required!"
            return
        }

        if(!email.isEmailValid()){
            binding.etEmail.error = "Email Is Invalid!"
            return
        }

        if (name.isEmpty()){
            binding.etNickname.error = "Name Is Required!"
            return
        }

        if (password.isEmpty()) {
            binding.etPassword.error = "Password Is Required!"
            return
        }

        if(!password.isPasswordValid()){
            binding.etPassword.error = "Password Is Invalid!"
            return
        }

        if (profession.isEmpty()){
            binding.etWhatIDo.error = "Profession Is Required!"
            return
        }

        binding.progressView.visibility = View.VISIBLE
        viewModel.registerUser(email, password)
    }
}