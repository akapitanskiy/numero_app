package com.alexthekap.numerology2_appp.ui.people

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import androidx.fragment.app.Fragment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.databinding.FragmentNewPersonBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.util.TxtChangeListener
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.Calendar
import javax.inject.Inject

class NewPersonFragment : Fragment(R.layout.fragment_new_person) {

    private var binding: FragmentNewPersonBinding? = null
    private val b get() = binding!!
    private lateinit var peopleViewModel: PeopleViewModel
    @Inject
    lateinit var vmFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentNewPersonBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        peopleViewModel = ViewModelProvider(requireActivity(), vmFactory).get(PeopleViewModel::class.java)

        initListeners()
    }

    private fun initListeners() {

        b.addPerson.setOnClickListener{
            b.datePicker.clearFocus()
            peopleViewModel.insertPerson(
                b.datePicker.year,
                b.datePicker.month,
                b.datePicker.dayOfMonth,
                b.nameText.text?.toString()?.trim(),
                b.noteEdittext.text?.toString(),
                findNavController()
            )
        }

        b.nameText.addTextChangedListener( TxtChangeListener {
            b.addPerson.isEnabled = it.isNotBlank()
        })

        b.noteEdittext.setOnFocusChangeListener{ v, hasFocus ->
            if (hasFocus) {
                Completable.fromAction{ Thread.sleep(300) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete{ b.scrollView.scrollTo(0, b.noteTitle.top) }
                    .subscribe()
            }
        }

        val dateChangeListener = DatePicker.OnDateChangedListener{ view, year, month, day ->
            b.nameText.clearFocus()
            b.noteEdittext.clearFocus()
            hideKeyboard(view.windowToken ?: requireActivity().window.attributes.token)
        }

        val calendar = Calendar.getInstance()
        b.datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            dateChangeListener
        )
    }

    private fun hideKeyboard(token: IBinder) {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(token, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}