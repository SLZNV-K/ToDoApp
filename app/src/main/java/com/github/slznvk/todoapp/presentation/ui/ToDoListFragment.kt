package com.github.slznvk.todoapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.slznvk.todoapp.R
import com.github.slznvk.todoapp.databinding.FragmentToDoListBinding
import com.github.slznvk.todoapp.domain.dto.TodoItem
import com.github.slznvk.todoapp.presentation.adapter.OnInteractionListener
import com.github.slznvk.todoapp.presentation.adapter.ToDoAdapter
import com.github.slznvk.todoapp.presentation.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToDoListFragment : Fragment() {
    private lateinit var binding: FragmentToDoListBinding
    private val viewModel: ToDoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoListBinding.inflate(layoutInflater, container, false)
        var visibility = true
        val adapter = ToDoAdapter(object : OnInteractionListener {
            override fun onInfoButtonClick(item: TodoItem) {
                viewModel.edit(item)
                findNavController().navigate(
                    R.id.action_toDoListFragment_to_addItemFragment
                )
            }

            override fun onDoneButtonClick(item: TodoItem) {
                viewModel.changeDone(item)
            }
        })

        with(binding) {
            recyclerView.adapter = adapter
            lifecycleScope.launch {
                viewModel.todoItems.collect { list ->
                    adapter.submitList(list)
                }
                viewModel.filteredItems.collect {
                    val size = it.size
                    countDoneItems.text = getString(R.string.done_count_items, size)
                }
            }
            addNewItemButton.setOnClickListener {
                findNavController().navigate(R.id.action_toDoListFragment_to_addItemFragment)
            }

            hiddenButton.setOnClickListener {
                lifecycleScope.launch {
                    if (visibility) {
                        viewModel.filter()
                        viewModel.filteredItems.collect { list ->
                            adapter.submitList(list)
                        }
                    } else viewModel.todoItems.collect { list ->
                        adapter.submitList(list)
                    }
                }
                visibility = !visibility
            }

            return root
        }
    }
}