package com.example.employeedatabase.views

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.employeedatabase.R
import kotlinx.android.synthetic.main.dialog_image_view.*

class ImageDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.dialog_image_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId = arguments?.get(IMAGE_RES)

        dialogImageLoader.visibility = View.VISIBLE

        Glide.with(dialogFullImageView)
            .load(imageId)
            .centerCrop()
            .placeholder(R.drawable.head_profile)
            .error(R.drawable.head_profile)
            .fallback(R.drawable.head_profile)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    dialogImageLoader.visibility = View.GONE
                    return true
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    dialogImageLoader.visibility = View.GONE
                    return false
                }

            })
            .into(dialogFullImageView)


        imageLoaderCancelButton.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        const val TAG = "ImageDialog"
        const val IMAGE_RES = "image_res"
        fun newInstance(imageUrl: String): ImageDialog {
            val fragment = ImageDialog()
            val bundle = Bundle()
            bundle.putString(IMAGE_RES, imageUrl)
            fragment.arguments = bundle
            return fragment
        }
    }
}