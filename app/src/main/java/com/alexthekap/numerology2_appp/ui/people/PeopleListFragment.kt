package com.alexthekap.numerology2_appp.ui.people

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.alexthekap.numerology2_appp.R
import com.alexthekap.numerology2_appp.data.db.model.PersonModel
import com.alexthekap.numerology2_appp.databinding.FragmentPeopleListBinding
import com.alexthekap.numerology2_appp.di.ComponentManager
import com.alexthekap.numerology2_appp.ui.ViewModelFactory
import com.alexthekap.numerology2_appp.ui.people.filter.ARG_FILTER_TYPE
import com.alexthekap.numerology2_appp.ui.people.filter.ARG_NAME_FILTER
import com.alexthekap.numerology2_appp.ui.people.filter.ARG_NOTE_FILTER
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PeopleListFragment : Fragment(R.layout.fragment_people_list) {

    private var binding: FragmentPeopleListBinding? = null
    private val b get() = binding!!
    private lateinit var peopleViewModel: PeopleViewModel
    private lateinit var adapter: PersonAdapter
    @Inject
    lateinit var vmFactory: ViewModelFactory
    private var menuItemSorted: MenuItem? = null

    override
    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPeopleListBinding.bind(view)

        ComponentManager.getViewModelComponent().inject(this)
        peopleViewModel = ViewModelProvider(requireActivity(), vmFactory).get(PeopleViewModel::class.java)

        initRecyclerView()
        initView()

        peopleViewModel.getPeopleLd().observe(viewLifecycleOwner, this::handlePeopleData)
        peopleViewModel.getSortedFavoriteLD().observe(viewLifecycleOwner, this::handleSortedFavorite)

        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initView() {
        b.fabAddPerson.setOnClickListener{ view ->
            Navigation.findNavController(view).navigate(R.id.addPersonFragmentId)
        }
    }

    private fun handlePeopleData(people: List<PersonModel>?) {
        adapter.submitList(people)
    }

    private fun handleSortedFavorite(sorted: Boolean?) {
        menuItemSorted?.isChecked = (sorted == true)
    }

    private fun initRecyclerView() {
        b.peopleRv.layoutManager = LinearLayoutManager(requireContext())
        b.peopleRv.addItemDecoration( DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply{
            ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let{ setDrawable(it) }
        })
        adapter = PersonAdapter()
        b.peopleRv.adapter = adapter

        swipeHandler.attachToRecyclerView(b.peopleRv)
    }

    private val swipeHandler = ItemTouchHelper( object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override
        fun onMove(rv: RecyclerView, vh: ViewHolder, target: ViewHolder): Boolean {
            return false
        }
        override
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val builder = AlertDialog.Builder(requireContext())
            val spanString = SpannableString(getString(R.string.warning_delete_person))
            spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.warning)), 0, spanString.length, 0 )
            builder.setTitle(spanString)
            builder.setMessage(" ")
            builder.setPositiveButtonIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_outline))

            builder.setPositiveButton(R.string.ok) { _, _ ->
                peopleViewModel.deletePersonById( adapter.currentList.get(viewHolder.adapterPosition).dbId )
            }
            builder.setNegativeButton("Cancel        ") { _, _ ->
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
            builder.setOnCancelListener{
                adapter.notifyItemChanged(viewHolder.adapterPosition)
            }
            builder.show()
        }
    } )

    private val menuProvider = object : MenuProvider {
        override
        fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_people_list, menu)

            val itemFilter = menu.findItem(R.id.menu_item_filters)
            menuInflater.inflate(R.menu.sub_menu_people, itemFilter.subMenu)
        }
        override
        fun onPrepareMenu(menu: Menu) {
            menuItemSorted = menu.findItem(R.id.item_favorite_first)
            menuItemSorted?.isChecked = (peopleViewModel.getSortedFavoriteLD().value == true)
        }

        override
        fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.item_name_filter -> {
                    findNavController().navigate(
                        R.id.oneFieldBottomFragmentId,
                        Bundle().apply{ putInt(ARG_FILTER_TYPE, ARG_NAME_FILTER) }
                    )
                    return true
                }
                R.id.item_note_filter -> {
                    findNavController().navigate(
                        R.id.oneFieldBottomFragmentId,
                        Bundle().apply{ putInt(ARG_FILTER_TYPE, ARG_NOTE_FILTER) }
                    )
                    return true
                }
                R.id.item_favorite_first -> {
                    peopleViewModel.orderFavorite()
                    Completable.fromAction{ Thread.sleep(500) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete{ b.peopleRv.smoothScrollToPosition(0) }
                        .subscribe()
                    return true
                }
                R.id.menu_item_reload -> {
                    peopleViewModel.reload()
                    Completable.fromAction{ Thread.sleep(500) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete{ b.peopleRv.smoothScrollToPosition(0) }
                        .subscribe()
                    return true
                }
                R.id.item_delete_all -> {
                    val builder = AlertDialog.Builder(requireContext())
                    val spanString = SpannableString(getString(R.string.warning_delete_all))
                    spanString.setSpan( ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.warning)), 0, spanString.length, 0 )
                    builder.setTitle(spanString)
                    builder.setMessage(" ")
                    builder.setPositiveButtonIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete_outline))

                    builder.setPositiveButton(R.string.ok) { _, _ ->
                        peopleViewModel.deleteAllFromDb()
                    }
                    builder.setNegativeButton("Cancel        ") { _, _ -> }
                    builder.setOnCancelListener{ }
                    builder.show()
                    return true
                }
                else -> false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}