package com.github.slznvk.todoapp.presentation.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.slznvk.todoapp.databinding.FragmentAddItemBinding
import com.github.slznvk.todoapp.presentation.viewmodel.ToDoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding
    private val viewModel: ToDoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(layoutInflater, container, false)

        with(binding) {

            content.requestFocus()

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.pickedItem.collect {
                    content.setText(it.content)
                    switcherButton.isPressed = it.deadline.isNullOrBlank()
                    deadline.text = it.deadline
                }
                viewModel.dataState.collect {
                    if (it.error) {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            switcherButton.setOnClickListener {
                showDatePickerDialog(deadline)
            }

            closeEditingButton.setOnClickListener {
                viewModel.cancel()
                findNavController().navigateUp()
            }

            saveButton.setOnClickListener {
                if (content.text.isNotBlank()) {
                    viewModel.apply {
                        changeContent(
                            content = content.text.toString(),
                            deadline = deadline.text.toString(),
                            priority = importanceVariants.selectedItem.toString()
                        )
                        saveItem()
                    }
                    findNavController().navigateUp()
                }
            }

            return root
        }
    }

    private fun showDatePickerDialog(view: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate =
                    "$selectedDay ${getMonthName(selectedMonth)} $selectedYear"
                view.text = formattedDate
            }, year, month, dayOfMonth
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun getMonthName(month: Int): String {
        val monthNames = arrayOf(
            "января", "февраля", "марта", "апреля", "мая", "июня",
            "июля", "августа", "сентября", "октября", "ноября", "декбря"
        )
        return monthNames[month]
    }
}