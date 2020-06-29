package com.example.employeedatabase.network

import com.example.employeedatabase.models.EmployeeData
import io.reactivex.Single
import retrofit2.http.GET

interface EmployeeDataAPI {

    @GET("employees.json")
    fun getEmployeeData(): Single<EmployeeData>

    @GET("employees_malformed.json")
    fun getEmployeeMalformedData(): Single<EmployeeData>

    @GET("employees_empty.json")
    fun getEmployeeEmptyData(): Single<EmployeeData>


}