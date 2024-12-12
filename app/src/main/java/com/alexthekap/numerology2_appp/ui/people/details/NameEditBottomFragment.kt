package com.alexthekap.numerology2_appp.ui.people.details

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.databinding.FragmentNameEditBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.util.TxtChangeListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

const val ARG_PERSON_DB_ID_EDIT = "arg_person_db_id"
const val ARG_PERSON_NAME_EDIT = "arg_person_name_edit"

class NameEditBottomFragment : BottomSheetDialogFragment(R.layout.fragment_name_edit) {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var binding: FragmentNameEditBinding? = null
    private val b get() = binding!!
    private lateinit var detailsViewModel: PersonDetailsViewModel
    private var dbId: Long? = null
    private var name: String? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dbId = it.getLong(ARG_PERSON_DB_ID_EDIT)
            name = it.getString(ARG_PERSON_NAME_EDIT)
        }
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNameEditBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        detailsViewModel = ViewModelProvider(this, vmFactory).get(PersonDetailsViewModel::class.java)

        b.nameEditText.setText(name)

        b.applyBtnEditName.setOnClickListener{
            detailsViewModel.updateNameAndNavigateToDetails(b.nameEditText.text.toString(), dbId, findNavController())
        }

        b.nameEditText.addTextChangedListener( TxtChangeListener{
                b.applyBtnEditName.isEnabled = (!b.nameEditText.text?.toString().isNullOrBlank())
        } )
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}