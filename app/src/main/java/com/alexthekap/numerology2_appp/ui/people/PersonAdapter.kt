package com.alexthekap.numerology2_appp.ui.people

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.util.Utils
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import com.alexthekap.numerology2_appp.databinding.ItemPersonBinding
import com.alexthekap.numerology2_appp.ui.people.details.ARG_PERSON_DB_ID
import com.alexthekap.numerology2_appp.ui.people.details.ARG_PERSON_DB_NAME
import com.alexthekap.numerology2_appp.util.ChineseYear
import com.alexthekap.numerology2_appp.util.Zodiac
import com.alexthekap.numerology2_appp.util.ZodiacEnum
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PersonAdapter : ListAdapter<PersonModel, PersonAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonModel>() {

            override fun areItemsTheSame(old: PersonModel, new: PersonModel): Boolean {
                return old.dbId == new.dbId
            }
            override fun areContentsTheSame(old: PersonModel, new: PersonModel): Boolean {
                return old.personName == new.personName
                        && old.birthDate == new.birthDate
                        && old.note == new.note
//                        && oldSong.title?.label    .equals(newSong.title?.label)
            }
        }
    }

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override
    fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ItemViewHolder(private val b: ItemPersonBinding) : RecyclerView.ViewHolder(b.root) {
        private lateinit var mPerson: PersonModel

        init {
            itemView.setOnClickListener{ view ->
                Navigation.findNavController(view).navigate(
                    R.id.personDetailsFragmentId,
                    Bundle().apply{
                        putLong(ARG_PERSON_DB_ID, mPerson.dbId)
                        putString(ARG_PERSON_DB_NAME, mPerson.personName)
                    }
                )
        }}

        @SuppressLint("CheckResult")
        fun bind(person: PersonModel) {
            mPerson = person

            val yearInfo = ChineseYear.getChineseYear(person.birthDate)
            val zodiac2 = Zodiac.getZodiac2(person.birthDate)
            val zodiac1 = Zodiac.getZodiacMain(person.birthDate)

            b.nameItem.setText(person.personName)
            b.birthDateItem.setText( Utils.longToStrDate(person.birthDate) )
            b.descriptionItem.setText(person.note)
            b.chineseYearItem.setText( yearInfo.first )

            if (person.img != null) {
                Single.fromCallable{ BitmapFactory.decodeByteArray(person.img, 0, person.img!!.size) }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { b.personPhoto.setImageBitmap(it) },
                        {}
                    ) // т.к @SuppressLint("CheckResult") нет disposable
            } else {
                b.personPhoto.setImageResource(R.drawable.ic_no_photo)
            }

            if (person.isFavorite)
                b.isFavoriteItemImg.visibility = View.VISIBLE
            else
                b.isFavoriteItemImg.visibility = View.GONE

            b.zodiac1Item.setText( zodiac1.first )
            if (zodiac1.first != zodiac2) {
                b.zodiac2Item.setText( zodiac2 )
                b.zodiac2Item.visibility = View.VISIBLE
            } else {
                b.zodiac2Item.visibility = View.GONE
            }

            if (zodiac1.second in 0..2) {
                b.chineseYearDate.setText( yearInfo.second?.substring(0, 6) )
                b.chineseYearDate.visibility = View.VISIBLE
            } else {
                b.chineseYearDate.visibility = View.GONE
            }

            if (Zodiac.isTransitional(person.birthDate)) {
                b.transitionalImg.visibility = View.VISIBLE
            } else {
                b.transitionalImg.visibility = View.GONE
            }
        }
    }

}