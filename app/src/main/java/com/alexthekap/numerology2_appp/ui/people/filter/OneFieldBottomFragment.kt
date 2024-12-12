package com.alexthekap.numerology2_appp.ui.people.filter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.databinding.FragmentOneFieldBottomBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.ui.people.PeopleViewModel
import com.alexthekap.numerology2_appp.util.TxtChangeListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

const val ARG_FILTER_TYPE = "arg_filter_type_bottom_sheet"
const val ARG_NAME_FILTER = 7071
const val ARG_NOTE_FILTER = 7072

class OneFieldBottomFragment : BottomSheetDialogFragment(R.layout.fragment_one_field_bottom) {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var binding: FragmentOneFieldBottomBinding? = null
    private val b get() = binding!!
    private lateinit var viewModel: PeopleViewModel
    private var filterType: Int? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            filterType = it.getInt(ARG_FILTER_TYPE, -1)
        }
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOneFieldBottomBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        viewModel = ViewModelProvider(requireActivity(), vmFactory).get(PeopleViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        b.singleFieldText.addTextChangedListener( TxtChangeListener{
            b.applyBtnSingleFieldFilter.isEnabled = it.trim().isNotEmpty()
        } )

        when(filterType) {
            ARG_NAME_FILTER -> {
                b.singleFieldTitle.setText(R.string.filter_by_name)
                b.applyBtnSingleFieldFilter.setOnClickListener{
                    viewModel.filterByName(b.singleFieldText.text?.toString())
                    findNavController().navigate(R.id.peopleListFragmentId)
                }
            }
            ARG_NOTE_FILTER -> {
                b.singleFieldTitle.setText(R.string.filter_by_note)
                b.applyBtnSingleFieldFilter.setOnClickListener{
                    viewModel.filterByNote(b.singleFieldText.text?.toString())
                    findNavController().navigate(R.id.peopleListFragmentId)
                }
            }
        }
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}