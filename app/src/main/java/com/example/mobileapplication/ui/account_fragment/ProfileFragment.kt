package com.example.mobileapplication.ui.account_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapplication.R
import com.example.mobileapplication.utils.FirebaseUtils

class ProfileFragment: Fragment() {
    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(AccountViewModel::class.java)

        val email = v.findViewById<TextView>(R.id.email)
        val signOutButton = v.findViewById<Button>(R.id.signOutButton)

        val currentUser = FirebaseUtils.getCurrentUser()
        if (currentUser != null) { email.text = currentUser.email }

        signOutButton.setOnClickListener { viewModel.signOut() }
    }
}
