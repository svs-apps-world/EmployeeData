package com.example.employeedatabase

import android.app.Application
import com.example.employeedatabase.models.Employee
import com.example.employeedatabase.models.EmployeeType
import com.example.employeedatabase.network.EmployeeDataAPI
import com.example.employeedatabase.repository.EmployeeRepository
import com.example.employeedatabase.viewmodels.EmployeeViewModel
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EmployeeViewModelTest {

    private var employeeViewModel: EmployeeViewModel? = null
    private lateinit var employeeRepository: EmployeeRepository

    @Mock
    lateinit var service: EmployeeDataAPI

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        employeeRepository = EmployeeRepository()
        employeeRepository = Mockito.spy(employeeRepository)
        BDDMockito.willReturn(service).given(employeeRepository).getService()
        employeeViewModel?.employeeList?.addAll(
            listOf(
                Employee("uuid1", employeeType = EmployeeType.PART_TIME.type),
                Employee("uuid2", employeeType = EmployeeType.PART_TIME.type),
                Employee("uuid3", employeeType = EmployeeType.FULL_TIME.type),
                Employee("uuid4", employeeType = EmployeeType.CONTRACTOR.type)
            )
        )
    }

    @Test
    fun testGetValidEmployeeData() {
        employeeRepository.getValidEmployeeData()
        BDDMockito.then(service).should(Mockito.times(1)).getEmployeeData()

        val testObserver = employeeRepository.getEmptyEmployeeData()?.test()
        testObserver?.hasSubscription()
        testObserver?.assertNoErrors()
        testObserver?.assertValueCount(1)
    }

    @Test
    fun testGetFilteredData_fullTime() {
        employeeViewModel?.createUnfilteredList()
        val filterData = employeeViewModel?.getFilteredData(R.id.showFullTimeEmp)
        filterData?.let {
            for (data in filterData) {
                assert(data.employeeType == EmployeeType.FULL_TIME.type)
            }
        }
    }

    @Test
    fun testGetFilteredData_partTime() {
        employeeViewModel?.createUnfilteredList()
        val filterData = employeeViewModel?.getFilteredData(R.id.showPartTimeEmp)
        filterData?.let {
            for (data in filterData) {
                assert(data.employeeType == EmployeeType.PART_TIME.type)
            }
        }
    }

    @Test
    fun testGetFilteredData_contractor() {
        employeeViewModel?.createUnfilteredList()
        val filterData = employeeViewModel?.getFilteredData(R.id.showContractorEmp)
        filterData?.let {
            for (data in filterData) {
                assert(data.employeeType == EmployeeType.CONTRACTOR.type)
            }
        }
    }
}