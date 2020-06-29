package com.example.employeedatabase.adapters

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MediatorLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.employeedatabase.R
import com.example.employeedatabase.models.Employee
import com.example.employeedatabase.models.EmployeeType


class EmployeeAdapter(private val context: Context, private val employeeList: ArrayList<Employee>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val imageClickLiveData = MediatorLiveData<String?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EmployeeItemViewHolder(LayoutInflater.from(context).inflate(R.layout.employee_line_item, parent, false))
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    fun setData(employeeListNew: ArrayList<Employee>) {
        employeeList.clear()
        employeeList.addAll(employeeListNew)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmployeeItemViewHolder) {
            val employee = employeeList[position]

            setTextViewAndText(holder.textViewName, employee.fullName, R.string.no_name_found)
            holder.textViewName.paintFlags = holder.textViewName.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            setNameClickListener(holder, employee)

            setTextViewAndText(holder.textViewEmail, employee.emailAddress)

            setTextViewAndText(holder.textViewPhoneNumber, employee.phoneNumber)

            refreshEmployeeDetails(holder, employee)

            setEmployeeType(employee, holder.employmentType)

            setImageData(holder.imageViewProfile, employee.photoURLSmall, employee.photoURLLarge)

        }
    }

    private fun setImageData(imageViewProfile: ImageView, photoURLSmall: String?, photoURLLarge: String?) {
        Glide.with(imageViewProfile)
            .load(photoURLSmall)
            .centerCrop()
            .circleCrop()
            .placeholder(R.drawable.head_profile)
            .error(R.drawable.head_profile)
            .fallback(R.drawable.head_profile)
            .into(imageViewProfile)
        imageViewProfile.setOnClickListener {
            imageClickLiveData.postValue(photoURLLarge)
        }
    }

    private fun setNameClickListener(holder: EmployeeItemViewHolder, employee: Employee) {
        holder.textViewName.setOnClickListener {
            employee.showDetails = !employee.showDetails
            refreshEmployeeDetails(holder, employee)

        }
    }

    private fun refreshEmployeeDetails(holder: EmployeeItemViewHolder, employee: Employee) {
        if (employee.showDetails) {

            setTextViewAndText(holder.textViewTeam, employee.team, titleText = holder.textViewTeamTitle)

            setTextViewAndText(
                holder.textViewBiography,
                employee.biography,
                titleText = holder.textViewBiographyTitle
            )
        } else {
            setTextViewAndText(holder.textViewTeam, null, titleText = holder.textViewTeamTitle)

            setTextViewAndText(
                holder.textViewBiography,
                null,
                titleText = holder.textViewBiographyTitle
            )
        }
    }

    private fun setTextViewAndText(
        textView: TextView,
        text: String?,
        defaultTextId: Int? = null,
        titleText: TextView? = null
    ) {
        if (text.isNullOrEmpty() && defaultTextId != null) {
            textView.text = context.getString(defaultTextId)
            textView.visibility = View.VISIBLE
            titleText?.visibility = View.VISIBLE
        } else if (text.isNullOrEmpty() && defaultTextId == null) {
            textView.visibility = View.GONE
            titleText?.visibility = View.GONE
        } else {
            textView.text = text
            textView.visibility = View.VISIBLE
            titleText?.visibility = View.VISIBLE
        }
    }

    private fun setEmployeeType(employee: Employee, employmentTypeTextView: TextView) {
        employmentTypeTextView.text = when (employee.employeeType) {
            EmployeeType.FULL_TIME.type -> {
                context.getString(R.string.full_time)
            }
            EmployeeType.PART_TIME.type -> {
                context.getString(R.string.part_time)
            }
            EmployeeType.CONTRACTOR.type -> {
                context.getString(R.string.contractor)
            }
            else -> {
                ""
            }
        }
    }


    private class EmployeeItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        internal val textViewEmail: TextView = itemView.findViewById(R.id.textViewEmail)
        internal val textViewPhoneNumber: TextView = itemView.findViewById(R.id.textViewPhoneNumber)
        internal val textViewTeam: TextView = itemView.findViewById(R.id.textViewTeam)
        internal val textViewTeamTitle: TextView = itemView.findViewById(R.id.textViewTeamTitle)
        internal val textViewBiography: TextView = itemView.findViewById(R.id.textViewBiography)
        internal val textViewBiographyTitle: TextView = itemView.findViewById(R.id.textViewBiographyTitle)
        internal val employmentType: TextView = itemView.findViewById(R.id.employmentType)
        internal val imageViewProfile: ImageView = itemView.findViewById(R.id.imageViewProfile)

    }
}