package com.elok.githubuserapp.ui.fragments

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.elok.githubuserapp.adapter.SavedUserAdapter
import com.elok.githubuserapp.databinding.FragmentSavedUserBinding
import com.elok.githubuserapp.utilities.InjectorUtils
import com.elok.githubuserapp.viewmodels.SavedUserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_item_saved_user.view.*

class SavedUserFragment : Fragment() {

    private var mBinding: FragmentSavedUserBinding? = null
    private val binding get() = mBinding!!
    private val mViewModel: SavedUserViewModel by viewModels {
        InjectorUtils.provideSavedUserListViewModelFactory(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSavedUserBinding.inflate(inflater, container, false).apply {
            toolbar.setNavigationOnClickListener { view_toolbar ->
                view_toolbar.findNavController().navigateUp()
            }

            val adapter = SavedUserAdapter()

            list.adapter = adapter

            val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.bindingAdapterPosition
                    val user = adapter.differ.currentList[position]
                    mViewModel.deleteSavedUser(user)
                    Snackbar.make(root, "Successfully deleted user", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            mViewModel.addSavedUser(user)
                        }
                        show()
                    }
                }

                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    if (viewHolder != null) {
                        val foreground = viewHolder.itemView.view_foreground
                        ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foreground)
                    }
                }

                override fun onChildDrawOver(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder?,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val foreground = viewHolder!!.itemView.view_foreground
                    ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
                        c,
                        recyclerView,
                        foreground,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }

                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    val foreground = viewHolder.itemView.view_foreground
                    ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foreground)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val foreground = viewHolder.itemView.view_foreground
                    ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foreground,
                    dX, dY, actionState, isCurrentlyActive)
                }
            }

            ItemTouchHelper(itemTouchHelperCallback).apply {
                attachToRecyclerView(list)
            }

            mViewModel.savedUsers.observe(viewLifecycleOwner) { result ->
                adapter.differ.submitList(result)
            }

        }
        return binding.root
    }
}