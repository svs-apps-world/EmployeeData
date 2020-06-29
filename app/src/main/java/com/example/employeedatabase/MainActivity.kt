package com.example.employeedatabase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.employeedatabase.views.EmployeeFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(EmployeeFragment.newInstance(), EmployeeFragment.TAG)
    }

    private fun addFragment(newInstance: Fragment, tag: String) {
        for (aFrag in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(aFrag).commit()
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, newInstance, tag)
            .commit()
    }
}
