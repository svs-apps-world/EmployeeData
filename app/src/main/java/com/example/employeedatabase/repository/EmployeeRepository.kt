package com.example.employeedatabase.repository

import com.example.employeedatabase.models.EmployeeData
import com.example.employeedatabase.network.EmployeeDataAPI
import com.example.employeedatabase.network.RetrofitClientInstance
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EmployeeRepository {

    fun getValidEmployeeData(): Single<EmployeeData>? {
        return getService()?.getEmployeeData()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    fun getMalformedEmployeeData(): Single<EmployeeData>? {
        return getService()?.getEmployeeMalformedData()?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
    }

    fun getEmptyEmployeeData(): Single<EmployeeData>? {
        return getService()?.getEmployeeEmptyData()?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
    }

    fun getService(): EmployeeDataAPI? {
        return RetrofitClientInstance.retrofit.create(EmployeeDataAPI::class.java)

    }
}