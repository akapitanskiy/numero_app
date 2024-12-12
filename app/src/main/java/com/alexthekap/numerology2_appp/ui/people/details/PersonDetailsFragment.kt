package com.alexthekap.numerology2_appp.ui.people.details

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import com.alexthekap.numerology2_appp.databinding.FragmentPersonDetailsBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.MainActivity
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.util.ChineseYear
import com.alexthekap.numerology2_appp.util.Utils
import com.alexthekap.numerology2_appp.util.Zodiac
import java.util.Calendar
import javax.inject.Inject


const val ARG_PERSON_DB_ID = "arg_person_dbId"
const val ARG_PERSON_DB_NAME = "arg_person_name_from_db" // !dynamic arg name in nav_graph DON'T change

class PersonDetailsFragment : Fragment(R.layout.fragment_person_details) {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var binding: FragmentPersonDetailsBinding? = null
    private val b get() = binding!!
    private lateinit var detailsViewModel: PersonDetailsViewModel
    private var dbId: Long? = null
    private var mPerson: PersonModel? = null
    private lateinit var activityResultGallery: ActivityResultLauncher<Intent>

    private val permissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback{ isGranted ->
            if (isGranted)
                activityResultGallery.launch( Intent(Intent.ACTION_PICK).apply{ setType("image/*") } )
        }
    )

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbId = arguments?.getLong(ARG_PERSON_DB_ID, -1L)

        activityResultGallery = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback{
                detailsViewModel.onFileObtained(it.data?.data, requireContext(), dbId)
            }
        )
    }

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPersonDetailsBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        detailsViewModel = ViewModelProvider(this, vmFactory).get(PersonDetailsViewModel::class.java)

        dbId?.let{ id ->
            detailsViewModel.getPersonLd(id).observe(viewLifecycleOwner, this::handlePersonData)
        }

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun handlePersonData(person: PersonModel?) {
        (requireActivity() as MainActivity).supportActionBar?.title = person?.personName
        mPerson = person

        person ?: return

        b.noteText.setText(person.note)
        b.birthdayText.setText(Utils.longToStrDate(person.birthDate))
        b.chineseYearText.setText( ChineseYear.getChineseYear(person.birthDate).first )
        b.isFavoriteImg.setColorFilter(
            if (person.isFavorite)
                ContextCompat.getColor(requireContext(), R.color.gold)
            else ContextCompat.getColor(requireContext(), R.color.gray_77)
        )

        var zodiac = Zodiac.getZodiacMain(person.birthDate).first
        val zodiac2 = Zodiac.getZodiac2(person.birthDate)
        if (zodiac != zodiac2) zodiac += " $zodiac2"
        b.zodiacText.setText( zodiac )

        if (person.img != null) {
            b.pictureImg.visibility = View.VISIBLE
            b.pictureImg.setImageBitmap( BitmapFactory.decodeByteArray(person.img, 0, person.img!!.size) )
            b.addPicImg.visibility = View.GONE
        } else {
            b.addPicImg.visibility = View.VISIBLE
            b.pictureImg.visibility = View.GONE
        }

        initListeners()
    }

    private fun initListeners() {
        b.pythagoreanMatrixText.setOnClickListener{ mPerson?.let{
            findNavController().navigate(
                R.id.pythagoreanMatrixFragmentId,
                Bundle().apply{
                    putLong(ARG_BIRTHDAY_DATE, it.birthDate)
                    putString(ARG_PERSON_NAME, it.personName)
                }
            )
        }}

        b.noteText.setOnClickListener{ mPerson?.let{
            findNavController().navigate(
                R.id.noteEditFragmentId,
                Bundle().apply{
                    putLong(ARG_DB_ID_EDIT_NOTE, it.dbId)
                    putString(ARG_NOTE_TO_EDIT, it.note)
                },
                NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right)
                    .setExitAnim(R.anim.slide_out_left)
                    .setPopEnterAnim(R.anim.slide_in_left)
                    .setPopExitAnim(R.anim.slide_out_right)
                    .build()
            )
        }}

        b.birthdayText.setOnClickListener{
            val cal = Calendar.getInstance().apply{ mPerson?.let{ person -> timeInMillis = person.birthDate } }

            val dateSetListener = DatePickerDialog.OnDateSetListener{ v, year, monthOfYear, dayOfMonth ->
                mPerson?.let{ person ->
                    detailsViewModel.updateBirthdate(year, monthOfYear, dayOfMonth, person.dbId)
                }
            }

            val dpd = DatePickerDialog(
                it.context,
                R.style.DatePicker,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            dpd.datePicker.maxDate = System.currentTimeMillis()
            dpd.show()
        }

        b.isFavoriteImg.setOnClickListener{ detailsViewModel.onFavoriteClick() }

        b.addPicImg.setOnClickListener{
            if ( !checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                activityResultGallery.launch( Intent(Intent.ACTION_PICK).apply{ setType("image/*") } )
            }
        }

        b.pictureImg.setOnClickListener{
            if ( !checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ) {
                permissionRequestLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                activityResultGallery.launch( Intent(Intent.ACTION_PICK).apply{ setType("image/*") } )
            }
        }

        b.pictureImg.setOnLongClickListener{
            val builder = AlertDialog.Builder(requireContext())
            val spanString = SpannableString(getString(R.string.warning_delete_photo))
            spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.warning)), 0, spanString.length, 0 )
            builder.setTitle(spanString)
            builder.setMessage(" ")

            builder.setPositiveButton(R.string.ok) { _, _ ->
                detailsViewModel.removePhoto(dbId)
            }
            builder.setNegativeButton("Cancel        ") { _, _ -> }
            builder.setOnCancelListener{ }
            builder.show()
            return@setOnLongClickListener true
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission( requireContext(), permission ) == PackageManager.PERMISSION_GRANTED
    }

    private val menuProvider = object : MenuProvider {
        override
        fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_edit_name, menu)
        }

        override
        fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.menu_item_edit -> {
                    dbId?.let{
                        findNavController().navigate(R.id.nameEditBottomFragmentId,
                        Bundle().apply{
                            putLong(ARG_PERSON_DB_ID_EDIT, it)
                            mPerson?.let{ putString(ARG_PERSON_NAME_EDIT, it.personName) }
                        } )
                    }
                    return true
                }
                else -> false
            }
        }
    }

    override
    fun onDestroyView() {
        super.onDestroyView()
        detailsViewModel.resetPersonLD()
        binding = null
    }
}