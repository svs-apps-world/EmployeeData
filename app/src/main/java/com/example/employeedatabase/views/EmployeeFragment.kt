package com.example.employeedatabase.views

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.employeedatabase.R
import com.example.employeedatabase.adapters.EmployeeAdapter
import com.example.employeedatabase.viewmodels.EmployeeViewModel
import kotlinx.android.synthetic.main.fragment_employee_database.*


class EmployeeFragment : Fragment() {

    private lateinit var viewModel: EmployeeViewModel
    private lateinit var adapter: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_employee_database, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        addObserverForEmployeeData()
        addObserverForImageClick()
        if (viewModel.employeeList.isEmpty()) {
            viewModel.getValidEmployeeData()
            fragmentEmployeeLoader.visibility = View.VISIBLE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setupRecyclerView()
        addObserverForEmployeeData()
        addObserverForImageClick()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_employee_extra, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.loadMalformedData -> {
                viewModel.employeeList.clear()
                adapter.notifyDataSetChanged()
                viewModel.getMalformedEmployeeData()
                return true
            }
            R.id.loadEmptyState -> {
                viewModel.employeeList.clear()
                adapter.notifyDataSetChanged()
                viewModel.getEmptyEmployeeData()
                return true
            }
            R.id.loadNormalData -> {
                viewModel.employeeList.clear()
                adapter.notifyDataSetChanged()
                viewModel.getValidEmployeeData()
                return true
            }
            R.id.loadStaticData -> {
                viewModel.loadStaticData()
                adapter.notifyDataSetChanged()
                fragmentEmployeeLoader.visibility = View.GONE
                noDataFoundLayout.visibility = View.GONE
                return true
            }
            R.id.filterData -> {
                viewModel.createUnfilteredList()
                val filterDialog = FilterDialog.newInstance()
                filterDialog.onFilterButtonClickListener {
                    val filteredData = viewModel.getFilteredData(filterDialog.selectedFilterId)
                    if (filteredData.isEmpty()) {
                        loadErrorState()
                    } else {
                        noDataFoundLayout.visibility = View.GONE
                        adapter.setData(filteredData)
                    }

                    filterDialog.dismiss()
                }
                filterDialog.show(requireActivity().supportFragmentManager, FilterDialog.TAG)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun setupRecyclerView() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            employeeDatabaseRecyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        } else {
            employeeDatabaseRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
        if (employeeDatabaseRecyclerView.itemDecorationCount == 0)
            employeeDatabaseRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.vertical_space)))
        adapter = EmployeeAdapter(requireContext(), viewModel.employeeList)
        employeeDatabaseRecyclerView.adapter = adapter
    }

    private fun addObserverForEmployeeData() {
        viewModel.employeeDataLiveDataSuccess.observe(viewLifecycleOwner, Observer { success ->

            if (success && viewModel.employeeList.isNotEmpty()) {
                loadSuccessState()
            } else {
                loadErrorState()
            }
        })
    }

    private fun addObserverForImageClick() {
        adapter.imageClickLiveData.observe(viewLifecycleOwner, Observer { largeImageUrl ->
            largeImageUrl?.let {
                ImageDialog.newInstance(largeImageUrl).show(requireActivity().supportFragmentManager, ImageDialog.TAG)
            }
        })
    }


    private fun loadSuccessState() {
        fragmentEmployeeLoader.visibility = View.GONE
        noDataFoundLayout.visibility = View.GONE
        adapter.notifyDataSetChanged()
    }

    private fun loadErrorState() {
        fragmentEmployeeLoader.visibility = View.GONE
        noDataFoundLayout.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "EmployeeFragment"
        fun newInstance(): EmployeeFragment {
            return EmployeeFragment()
        }
    }
}