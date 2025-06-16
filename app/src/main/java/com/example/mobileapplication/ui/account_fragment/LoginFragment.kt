package com.example.mobileapplication.ui.account_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.mobileapplication.R

class LoginFragment: Fragment() {
    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)

        val email = v.findViewById<EditText>(R.id.email)
        val password = v.findViewById<EditText>(R.id.password)
        val signInButton = v.findViewById<Button>(R.id.signInButton)
        val signUpLink = v.findViewById<TextView>(R.id.signUpLink)

        signInButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            viewModel.loginUser(email, password)
        }

        signUpLink.setOnClickListener { requireActivity().findViewById<ViewPager2>(R.id.view_pager)?.currentItem = 1 }
    }
}
