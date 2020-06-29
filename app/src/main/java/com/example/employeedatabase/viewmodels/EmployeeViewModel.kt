package com.example.employeedatabase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import com.example.employeedatabase.models.Employee
import com.example.employeedatabase.models.EmployeeData
import com.example.employeedatabase.models.EmployeeType
import com.example.employeedatabase.repository.EmployeeRepository
import com.fasterxml.jackson.databind.ObjectMapper
import io.reactivex.observers.ResourceSingleObserver


class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val employeeRepository = EmployeeRepository()

    val employeeList = ArrayList<Employee>()
    private val employeeListUnFiltered = ArrayList<Employee>()
    val employeeDataLiveDataSuccess = MediatorLiveData<Boolean>()

    fun getValidEmployeeData() {
        employeeRepository.getValidEmployeeData()?.subscribe(object : ResourceSingleObserver<EmployeeData>() {
            override fun onSuccess(employeeData: EmployeeData) {
                if (!employeeData.employees.isNullOrEmpty()) {
                    onSuccessOfGetEmployeeCall(employeeData)
                }
                employeeDataLiveDataSuccess.postValue(true)
            }

            override fun onError(e: Throwable) {
                employeeDataLiveDataSuccess.postValue(false)
            }
        })
    }

    private fun onSuccessOfGetEmployeeCall(employeeData: EmployeeData) {
        employeeList.clear()
        employeeData.employees?.let {
            employeeList.addAll(it)
        }
    }

    fun getMalformedEmployeeData() {
        employeeRepository.getMalformedEmployeeData()?.subscribe(object : ResourceSingleObserver<EmployeeData>() {
            override fun onSuccess(employeeData: EmployeeData) {
                if (!employeeData.employees.isNullOrEmpty()) {
                    onSuccessOfGetEmployeeCall(employeeData)
                }
                employeeDataLiveDataSuccess.postValue(true)
            }

            override fun onError(e: Throwable) {
                employeeDataLiveDataSuccess.postValue(false)
            }
        })
    }

    fun getEmptyEmployeeData() {
        employeeRepository.getEmptyEmployeeData()?.subscribe(object : ResourceSingleObserver<EmployeeData>() {
            override fun onSuccess(employeeData: EmployeeData) {
                if (!employeeData.employees.isNullOrEmpty()) {
                    onSuccessOfGetEmployeeCall(employeeData)
                }
                employeeDataLiveDataSuccess.postValue(true)
            }

            override fun onError(e: Throwable) {
                employeeDataLiveDataSuccess.postValue(false)
            }
        })
    }

    fun createUnfilteredList() {
        if (employeeListUnFiltered.isEmpty())
            employeeListUnFiltered.addAll(employeeList)
    }

    fun getFilteredData(selectedFilterId: String): java.util.ArrayList<Employee> {
        var filteredList = ArrayList<Employee>(employeeListUnFiltered)
        when (selectedFilterId) {
            EmployeeType.FULL_TIME.type -> {
                filteredList = filteredList.filter {
                    it.employeeType == EmployeeType.FULL_TIME.type
                } as ArrayList<Employee>
            }
            EmployeeType.PART_TIME.type -> {
                filteredList =
                    filteredList.filter { it.employeeType == EmployeeType.PART_TIME.type } as ArrayList<Employee>
            }
            EmployeeType.CONTRACTOR.type -> {
                filteredList =
                    filteredList.filter { it.employeeType == EmployeeType.CONTRACTOR.type } as ArrayList<Employee>
            }
            else -> {
                filteredList = employeeListUnFiltered.let {
                    it
                }
            }
        }
        return filteredList
    }


    fun loadStaticData() {
        val json = "{\n" +
                "\t\"employees\" : [\n" +
                "\t\t{\n" +
                "      \"uuid\" : \"0d8fcc12-4d0c-425c-8355-390b312b909c\",\n" +
                "\n" +
                "      \"full_name\" : \"Justine Mason\",\n" +
                "      \"phone_number\" : \"5553280123\",\n" +
                "      \"email_address\" : \"jmason.demo@squareup.com\",\n" +
                "      \"biography\" : \"Engineer on the Point of Sale team.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/16c00560-6dd3-4af4-97a6-d4754e7f2394/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Point of Sale\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"a98f8a2e-c975-4ba3-8b35-01f719e7de2d\",\n" +
                "\n" +
                "      \"full_name\" : \"Camille Rogers\",\n" +
                "      \"phone_number\" : \"5558531970\",\n" +
                "      \"email_address\" : \"crogers.demo@squareup.com\",\n" +
                "      \"biography\" : \"Designer on the web marketing team.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/5095a907-abc9-4734-8d1e-0eeb2506bfa8/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Public Web & Marketing\",\n" +
                "      \"employee_type\" : \"PART_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"b8cf3382-ecf2-4240-b8ab-007688426e8c\",\n" +
                "\n" +
                "      \"full_name\" : \"Richard Stein\",\n" +
                "      \"phone_number\" : \"5557223332\",\n" +
                "      \"email_address\" : \"rstein.demo@squareup.com\",\n" +
                "      \"biography\" : \"Product manager for the Point of sale app!\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/43ed39b3-fbc0-4eb8-8ed3-6a8de479a52a/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Point of Sale\",\n" +
                "      \"employee_type\" : \"PART_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"61b21d34-5499-401a-98b3-16f26e645d54\",\n" +
                "\n" +
                "      \"full_name\" : \"Alaina Daly\",\n" +
                "      \"phone_number\" : \"5555442937\",\n" +
                "      \"email_address\" : \"adaly.demo@squareup.com\",\n" +
                "      \"biography\" : \"Product marketing manager for the Retail Point of Sale app in New York.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/57bc7ed2-5f9e-4814-a7df-dea85c2ed97f/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/57bc7ed2-5f9e-4814-a7df-dea85c2ed97f/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Retail\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"b6dea526-c571-4d43-8b41-375ca5cd9fdb\",\n" +
                "\n" +
                "      \"full_name\" : \"Elisa Rizzo\",\n" +
                "      \"phone_number\" : \"5552234497\",\n" +
                "      \"email_address\" : \"erizzo.demo@squareup.com\",\n" +
                "      \"biography\" : \"iOS Engineer on the Restaurants team.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/8ab10188-74d0-4843-9eb2-1938571f6830/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/8ab10188-74d0-4843-9eb2-1938571f6830/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Restaurants\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"f65a3e65-9e9c-493e-9a13-8810c9eed7e3\",\n" +
                "\n" +
                "      \"full_name\" : \"Nate Anderson\",\n" +
                "      \"phone_number\" : \"5554532433\",\n" +
                "      \"email_address\" : \"nanderson.demo@squareup.com\",\n" +
                "      \"biography\" : \"Server and iOS engineer on the Cash App team.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/92f8e6f4-8d1b-4ea6-801f-a352dfea3751/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/92f8e6f4-8d1b-4ea6-801f-a352dfea3751/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Cash\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"fcde1b31-2bc5-4ce9-bae8-7372d06f6ad6\",\n" +
                "\n" +
                "      \"full_name\" : \"Kaitlyn Spindel\",\n" +
                "      \"phone_number\" : \"5555096266\",\n" +
                "      \"email_address\" : \"kspindel.demo@squareup.com\",\n" +
                "      \"biography\" : \"Designer on the Services team, working on the Appointments iOS and Android apps.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/077c1707-4199-415c-86b5-a29afe4e29e3/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/077c1707-4199-415c-86b5-a29afe4e29e3/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Appointments\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"8623ba77-9d6a-4bcd-bd91-e19ae2c9dba2\",\n" +
                "\n" +
                "      \"full_name\" : \"Ryan Gehani\",\n" +
                "      \"phone_number\" : \"5554047710\",\n" +
                "      \"email_address\" : \"rgehani.demo@squareup.com\",\n" +
                "      \"biography\" : \"Product manager for Invoices!\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/7959987e-0d64-4bf6-8b9e-da78deac3457/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/7959987e-0d64-4bf6-8b9e-da78deac3457/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Invoices\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"ca020d09-76c6-44a5-ada9-8459d281c317\",\n" +
                "\n" +
                "      \"full_name\" : \"Michael Morin\",\n" +
                "      \"phone_number\" : \"5557976363\",\n" +
                "      \"email_address\" : \"mmorin.demo@squareup.com\",\n" +
                "      \"biography\" : \"I work on Core frameworks and infrastructure for our merchant apps.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/b44629e2-47e0-459a-a936-4683f783536b/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/b44629e2-47e0-459a-a936-4683f783536b/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Point of Sale Platform\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"7fb13023-d013-41ac-84f1-e554890ccb32\",\n" +
                "\n" +
                "      \"full_name\" : \"Tim Nakamura\",\n" +
                "      \"phone_number\" : \"5557510409\",\n" +
                "      \"email_address\" : \"tnakamura.demo@squareup.com\",\n" +
                "      \"biography\" : \"Hardware packaging designer on the hardware team, working from LA.\",\n" +
                "      \n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/e2b088e6-0b8d-4295-a66c-d7181cdec3d6/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/e2b088e6-0b8d-4295-a66c-d7181cdec3d6/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Hardware\",\n" +
                "      \"employee_type\" : \"CONTRACTOR\"\n" +
                "    },\n" +
                "\n" +
                "    {\n" +
                "      \"uuid\" : \"d8265dcd-1914-4f93-8068-07f5426f0866\",\n" +
                "\n" +
                "      \"full_name\" : \"Jack Dorsey\",\n" +
                "      \"phone_number\" : \"5554544932\",\n" +
                "      \"email_address\" : \"jdorsey.demo@squareup.com\",\n" +
                "      \"biography\" : \"I work for you.\",\n" +
                "\n" +
                "      \"photo_url_small\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/f8fc3c8e-b8ed-41d8-9005-537cf657c169/small.jpg\",\n" +
                "      \"photo_url_large\" : \"https://s3.amazonaws.com/sq-mobile-interview/photos/f8fc3c8e-b8ed-41d8-9005-537cf657c169/large.jpg\",\n" +
                "\n" +
                "      \"team\" : \"Core\",\n" +
                "      \"employee_type\" : \"FULL_TIME\"\n" +
                "    }    \n" +
                "\t]\n" +
                "}"
        val employeeData: EmployeeData = ObjectMapper().readValue(json, EmployeeData::class.java)
        employeeData.employees?.let {
            employeeList.clear()
            employeeList.addAll(it)
        }
    }
}