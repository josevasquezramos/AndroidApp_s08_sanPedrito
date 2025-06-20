package com.episi.androidapp_s08_sanpedrito.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.episi.androidapp_s08_sanpedrito.R
import com.episi.androidapp_s08_sanpedrito.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up radio group listener
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            binding.codigoLayout.visibility = when (checkedId) {
                R.id.radio_alumno -> View.VISIBLE
                else -> View.GONE
            }
        }

        // Initialize with student selected and code field visible
        binding.radioAlumno.isChecked = true
        binding.codigoLayout.visibility = View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}