package uz.texnopos.labworkapp.ui

import android.os.Bundle
import uz.texnopos.labworkapp.core.AppBaseActivity
import uz.texnopos.labworkapp.databinding.ActivityMainBinding

class MainActivity : AppBaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}