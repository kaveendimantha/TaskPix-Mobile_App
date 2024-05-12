package com.example.taskmaster.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.taskmaster.R
import com.example.taskmaster.databinding.FragmentHomeBinding
import com.example.taskmaster.MainActivity
import com.example.taskmaster.adapter.TaskAdapter
import com.example.taskmaster.model.Task
import com.example.taskmaster.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener, MenuProvider {

    private var homeBinding: FragmentHomeBinding? = null
    private val binding get() = homeBinding

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        taskViewModel = (activity as MainActivity).taskViewModel
        setupHomeRecyclerView()

        binding?.addNoteFab?.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_addTaskFragment)
        }
    }

    private fun updateUI(task: List<Task>?) {
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)

        val greetingMessage = when (currentHour) {
            in 6..11 -> "Good morning"
            in 12..16 -> "Good afternoon"
            else -> "Good evening"
        }
        binding?.greeting?.text = greetingMessage

        // Format date
        val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(currentTime.time)
        binding?.date?.text = formattedDate

        val imageResId = when {
            currentHour in 4..9 -> R.drawable.morining_sun
            currentHour in 10..15 -> R.drawable.noon_sun
            currentHour in 16..17 -> R.drawable.evenining_sun
            else -> R.drawable.night_moon
        }
        binding?.timeImg?.setImageResource(imageResId)

        if (task != null) {
            if (task.isNotEmpty()) {
                binding?.emptyNotesImage?.visibility = View.GONE
                binding?.homeRecyclerView?.visibility = View.VISIBLE
            } else {
                binding?.emptyNotesImage?.visibility = View.VISIBLE
                binding?.homeRecyclerView?.visibility = View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView() {
        taskAdapter = TaskAdapter()
        binding?.homeRecyclerView?.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = taskAdapter
        }

        activity?.let {
            taskViewModel.getAllTasks().observe(viewLifecycleOwner) { task ->
                taskAdapter.differ.submitList(task)
                updateUI(task)
            }
        }
    }

    private fun searchTask(query: String?) {
        val searchQuery = "%$query"

        taskViewModel.searchTask(searchQuery).observe(this) {list ->
            taskAdapter.differ.submitList(list)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchTask(newText)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }


}