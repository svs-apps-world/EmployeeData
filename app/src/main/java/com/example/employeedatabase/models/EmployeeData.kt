package com.example.employeedatabase.models

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class EmployeeData(
    @JsonProperty("employees") var employees: List<Employee>? = null
) : Parcelable

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class Employee(
    @JsonProperty("uuid") var uuid: String? = null,
    @JsonProperty("full_name") var fullName: String? = null,
    @JsonProperty("phone_number") var phoneNumber: String? = null,
    @JsonProperty("email_address") var emailAddress: String? = null,
    @JsonProperty("biography") var biography: String? = null,
    @JsonProperty("photo_url_small") var photoURLSmall: String? = null,
    @JsonProperty("photo_url_large") var photoURLLarge: String? = null,
    @JsonProperty("team") var team: String? = null,
    @JsonProperty("employee_type") var employeeType: String? = null
) : Parcelable {
    @JsonIgnoreProperties
    var showDetails: Boolean = false
}

enum class EmployeeType(val type: String) {
    FULL_TIME("FULL_TIME"),
    PART_TIME("PART_TIME"),
    CONTRACTOR("CONTRACTOR")
}

