package com.alexthekap.numerology2_appp.ui.people.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.databinding.FragmentPythagoreanMatrixBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.MainActivity
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.util.Matrix
import com.alexthekap.numerology2_appp.util.Matrix2
import com.alexthekap.numerology2_appp.util.Matrix3
import com.alexthekap.numerology2_appp.util.Utils
import javax.inject.Inject

const val ARG_BIRTHDAY_DATE = "arg_birthday_date"
const val ARG_PERSON_NAME = "arg_person_name"

class PythagoreanMatrixFragment : Fragment(R.layout.fragment_pythagorean_matrix) {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var birthDateMillis: Long? = null
    private var name: String? = null
    private var binding: FragmentPythagoreanMatrixBinding? = null
    private val b get() = binding!!
//    private lateinit var personDetailsViewModel: PersonDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            birthDateMillis = it.getLong(ARG_BIRTHDAY_DATE)
            name = it.getString(ARG_PERSON_NAME)
        }
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPythagoreanMatrixBinding.bind(view)

//        ComponentManager.getViewModelComponent().inject(this)
//        personDetailsViewModel = ViewModelProvider(requireActivity(), vmFactory).get(PersonDetailsViewModel::class.java)

        birthDateMillis?.let{
            fillMatrixFields( Matrix(it), Matrix2(it), Matrix3(it) )
            b.birthDateMatrix.setText( Utils.longToStrDate(it) )
        }

        name?.let{ (requireActivity() as MainActivity).supportActionBar?.title = it }
    }

    private fun fillMatrixFields(matrix: Matrix, matrix2: Matrix2, matrix3: Matrix3) {
        if (matrix._1s.isNotEmpty()) { b.txtOf1s.setText(matrix._1s) }
        if (matrix._2s.isNotEmpty()) { b.txtOf2s.setText(matrix._2s) }
        if (matrix._3s.isNotEmpty()) { b.txtOf3s.setText(matrix._3s) }
        if (matrix._4s.isNotEmpty()) { b.txtOf4s.setText(matrix._4s) }
        if (matrix._5s.isNotEmpty()) { b.txtOf5s.setText(matrix._5s) }
        if (matrix._6s.isNotEmpty()) { b.txtOf6s.setText(matrix._6s) }
        if (matrix._7s.isNotEmpty()) { b.txtOf7s.setText(matrix._7s) }
        if (matrix._8s.isNotEmpty()) { b.txtOf8s.setText(matrix._8s) }
        if (matrix._9s.isNotEmpty()) { b.txtOf9s.setText(matrix._9s) }

        if (matrix2._1s.isNotEmpty()) { b.mtrxTwo1s.setText(matrix2._1s) }
        if (matrix2._2s.isNotEmpty()) { b.mtrxTwo2s.setText(matrix2._2s) }
        if (matrix2._3s.isNotEmpty()) { b.mtrxTwo3s.setText(matrix2._3s) }
        if (matrix2._4s.isNotEmpty()) { b.mtrxTwo4s.setText(matrix2._4s) }
        if (matrix2._5s.isNotEmpty()) { b.mtrxTwo5s.setText(matrix2._5s) }
        if (matrix2._6s.isNotEmpty()) { b.mtrxTwo6s.setText(matrix2._6s) }
        if (matrix2._7s.isNotEmpty()) { b.mtrxTwo7s.setText(matrix2._7s) }
        if (matrix2._8s.isNotEmpty()) { b.mtrxTwo8s.setText(matrix2._8s) }
        if (matrix2._9s.isNotEmpty()) { b.mtrxTwo9s.setText(matrix2._9s) }

        if (matrix3._1s.isNotEmpty()) { b.mtrx31s.setText(matrix3._1s) }
        if (matrix3._2s.isNotEmpty()) { b.mtrx32s.setText(matrix3._2s) }
        if (matrix3._3s.isNotEmpty()) { b.mtrx33s.setText(matrix3._3s) }
        if (matrix3._4s.isNotEmpty()) { b.mtrx34s.setText(matrix3._4s) }
        if (matrix3._5s.isNotEmpty()) { b.mtrx35s.setText(matrix3._5s) }
        if (matrix3._6s.isNotEmpty()) { b.mtrx36s.setText(matrix3._6s) }
        if (matrix3._7s.isNotEmpty()) { b.mtrx37s.setText(matrix3._7s) }
        if (matrix3._8s.isNotEmpty()) { b.mtrx38s.setText(matrix3._8s) }
        if (matrix3._9s.isNotEmpty()) { b.mtrx39s.setText(matrix3._9s) }

        if (matrix.numberList.joinToString() == matrix2.numberList.joinToString()) {
            b.groupMatrix2.visibility = View.GONE
        }
        if ( matrix.numberList.joinToString() == matrix3.numberList.joinToString()
            || matrix2.numberList.joinToString() == matrix3.numberList.joinToString())
        {
            b.groupMatrix3.visibility = View.GONE
        }
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}