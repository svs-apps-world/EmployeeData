package com.example.employeedatabase.views

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.example.employeedatabase.R
import kotlinx.android.synthetic.main.dialog_filter_data.*

class FilterDialog : DialogFragment() {

    private var onFilterButtonClickListener: (() -> Unit)? = null

    var selectedFilterId = R.id.showAll

    companion object {
        const val TAG = "FilterDialog"
        fun newInstance(): FilterDialog {
            val bundle = Bundle()
            val dialog = FilterDialog()
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dialog?.window?.let {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return inflater.inflate(R.layout.dialog_filter_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filterButton.setOnClickListener {
            if (onFilterButtonClickListener != null) {
                selectedFilterId = radioGroupForFilter.checkedRadioButtonId
                onFilterButtonClickListener!!.invoke()
            } else {
                dismiss()
            }
        }
    }

    fun onFilterButtonClickListener(onRightButtonClickListener: () -> Unit) {
        this.onFilterButtonClickListener = onRightButtonClickListener
    }


}