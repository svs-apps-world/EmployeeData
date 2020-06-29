package com.example.employeedatabase

import com.example.employeedatabase.network.EmployeeDataAPI
import com.example.employeedatabase.repository.EmployeeRepository
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EmployeeRepositoryTest {

    private lateinit var employeeRepository: EmployeeRepository

    @Mock
    lateinit var service: EmployeeDataAPI

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        employeeRepository = EmployeeRepository()
        employeeRepository = Mockito.spy(employeeRepository)
        BDDMockito.willReturn(service).given(employeeRepository).getService()
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
    fun testGetMalformedEmployeeData() {
        employeeRepository.getMalformedEmployeeData()
        BDDMockito.then(service).should(Mockito.times(1)).getEmployeeMalformedData()

        val testObserver = employeeRepository.getMalformedEmployeeData()?.test()
        testObserver?.hasSubscription()
        testObserver?.assertNoErrors()
        testObserver?.assertValueCount(1)
    }

    @Test
    fun testGetEmptyEmployeeData() {
        employeeRepository.getEmptyEmployeeData()
        BDDMockito.then(service).should(Mockito.times(1)).getEmployeeEmptyData()

        val testObserver = employeeRepository.getEmptyEmployeeData()?.test()
        testObserver?.hasSubscription()
        testObserver?.assertNoErrors()
        testObserver?.assertValueCount(1)
    }
}