package ge.ananeishvilitbarkaia.messengerapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ge.ananeishvilitbarkaia.messengerapp.databinding.ActivityLoginBinding
import ge.ananeishvilitbarkaia.messengerapp.model.User
import ge.ananeishvilitbarkaia.messengerapp.session.SharedPref
import ge.ananeishvilitbarkaia.messengerapp.ui.register.RegisterActivity
import ge.ananeishvilitbarkaia.messengerapp.ui.dashboard.DashboardActivity
import ge.ananeishvilitbarkaia.messengerapp.utils.isEmailValid
import ge.ananeishvilitbarkaia.messengerapp.utils.isPasswordValid
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        sharedPref = SharedPref(this)

        viewModel.signInResult.observe(this) {
            if (it.isSuccess) {
                Toast.makeText(this, "Sign In Success", Toast.LENGTH_SHORT).show()
                viewModel.getUserFromFirebase(it.currentUser!!.uid)
            } else {
                binding.progressView.visibility = View.GONE
                Toast.makeText(this, "Sign In Failed: ${it.errorMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewModel.userData.observe(this) {
            if (it.uid.isNotEmpty()) {
                Toast.makeText(this, "User Found, Welcome To The New Messenger", Toast.LENGTH_SHORT)
                    .show()
                goNext(it)
            } else {
                binding.progressView.visibility = View.GONE
                Toast.makeText(this, "User Not Found, Something Went Wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnSignIn.setOnClickListener {
            verifyLogIn()
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if (sharedPref.getSignIn()){
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }

    private fun goNext(it: User) {
        sharedPref.setSignIn(true)
        sharedPref.setUserId(it.uid)
        sharedPref.setUserName(it.name)
        sharedPref.setUserProfession(it.profession)
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun verifyLogIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isEmpty()) {
            binding.etEmail.error = "Email Is Required!"
            return
        }

        if(!email.isEmailValid()){
            binding.etEmail.error = "Email Is Invalid!"
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

        binding.progressView.visibility = View.VISIBLE
        viewModel.signInUser(email, password)
    }
}