package com.alexthekap.numerology2_appp.ui.people.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.databinding.FragmentNoteEditBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.MainActivity
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.util.TxtChangeListener
import javax.inject.Inject

const val ARG_NOTE_TO_EDIT = "arg_note_to_edit"
const val ARG_DB_ID_EDIT_NOTE = "arg_db_id_edit_note"

class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var binding: FragmentNoteEditBinding? = null
    private val b get() = binding!!
    private lateinit var detailsViewModel: PersonDetailsViewModel
    private var noteText: String? = null
    private var id: Long? = null

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            noteText = it.getString(ARG_NOTE_TO_EDIT)
            id = it.getLong(ARG_DB_ID_EDIT_NOTE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNoteEditBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        detailsViewModel = ViewModelProvider(this, vmFactory).get(PersonDetailsViewModel::class.java)

        b.editNoteEt.setText(noteText)

        initListeners()

        (requireActivity() as MainActivity).supportActionBar?.setTitle(R.string.note)
    }

    private fun initListeners() {
        b.saveNoteBtn.setOnClickListener{
            detailsViewModel.updateNote(b.editNoteEt.text?.toString(), id)
            findNavController().popBackStack()
        }

        b.editNoteEt.addTextChangedListener( TxtChangeListener{
            b.saveNoteBtn.isEnabled = (b.editNoteEt.text.toString() != noteText)
        } )
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}