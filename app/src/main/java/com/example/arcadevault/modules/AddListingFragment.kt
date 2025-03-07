package com.example.arcadevault.modules

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.arcadevault.R
import com.example.arcadevault.adapter.ImageAdapter
import com.example.arcadevault.viewmodel.ListingViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddListingFragment : BottomSheetDialogFragment(R.layout.fragment_add_listing) {

    private lateinit var titleEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var uploadImageButton: Button
    private lateinit var addButton: Button
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var viewModel: ListingViewModel
    private lateinit var imageAdapter: ImageAdapter // Adapter for displaying images

    private val getImagesResult =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
            // Update the selected images in the ViewModel
            viewModel.setSelectedImages(uris)
        }

    override fun onStart() {
        super.onStart()
        val sheetContainer = requireView().parent as? ViewGroup ?: return
        sheetContainer.layoutParams.height = (resources.displayMetrics.heightPixels * 0.75).toInt()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views using findViewById
        titleEditText = view.findViewById(R.id.titleEditText)
        priceEditText = view.findViewById(R.id.priceEditText)
        uploadImageButton = view.findViewById(R.id.uploadImageButton)
        addButton = view.findViewById(R.id.addButton)
        imagesRecyclerView = view.findViewById(R.id.imagesRecyclerView)

        viewModel = ViewModelProvider(requireActivity())[ListingViewModel::class.java]

        // Setup RecyclerView
        imageAdapter = ImageAdapter()
        imagesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = imageAdapter
        }

        // Observe the uploaded images from the ViewModel
        viewModel.uploadedImages.observe(viewLifecycleOwner) { images ->
            imageAdapter.submitList(images) // Dynamically update the adapter's list
        }

        // Select images
        uploadImageButton.setOnClickListener {
            viewModel.pickImages(getImagesResult)
        }

        // Set up the add listing button
        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val price = priceEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (title.isNotEmpty()) {
                viewModel.addListing(title, price) // Save to DB
                viewModel.setSelectedImages(emptyList())
                dismiss()
            } else {
                titleEditText.error = "Title is required"
            }
        }
    }

}