package com.example.practice.ui.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practice.LoginView
import com.example.practice.ui.signup.SignupActivity
import com.example.practice.data.entities.User
import com.example.practice.data.remote.AuthService
import com.example.practice.data.remote.Result
import com.example.practice.databinding.ActivityLoginBinding
import com.example.practice.ui.main.MainActivity

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.loginPasswordEt.text.toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val email = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val password = binding.loginPasswordEt.text.toString()

        /*val songDB = SongDatabase.getInstance(this)!!

        //잘못된 유저 먼저 보여주
        val user = songDB.userDao().getUser(email, password)


        user?.let {
            Log.d("LOGIN_ACT/GET_USER", "userId: ${user.id}, $user")
            //saveJwt(user.id)

            startMainActivity()
        }
*/
        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(User(email,password,""))
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /*private fun saveJwt(jwt: Int) {
        val spf = getSharedPreferences("auth" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }*/

    private fun saveJwt2(jwt: String) {
        val spf = getSharedPreferences("auth2" , MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt", jwt)
        editor.apply()
    }

    override fun onLoginUpSuccess(code : Int, result : Result) {
        when(code){
            1000->
            {
                saveJwt2(result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginUpFailure() {
        //실패처리
    }


}