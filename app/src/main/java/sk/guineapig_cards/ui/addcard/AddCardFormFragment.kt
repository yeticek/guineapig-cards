package sk.guineapig_cards.ui.addcard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import sk.guineapig_cards.MainActivity
import sk.guineapig_cards.databinding.FragmentAddCardFormBinding

class AddCardFormFragment : Fragment() {
    private var _binding: FragmentAddCardFormBinding? = null
    private val binding get() = _binding!!

    private val REQUEST_IMAGE_PICK_1 = 101
    private val REQUEST_IMAGE_PICK_2 = 102

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle first photo upload
        binding.uploadPhotoButton1.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK_1)
        }

        // Handle second photo upload
        binding.uploadPhotoButton2.setOnClickListener {
            val pickPhotoIntent = Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            }
            startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK_2)
        }

        // Handle form submission
        binding.submitButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString().takeIf { it.isNotEmpty() }
            val photoUri = binding.photoPreview1.tag?.toString()
            val photoUri2 = binding.photoPreview2.tag?.toString() // This can now be null

            if (name.isNotEmpty() && photoUri != null) {
                // Save the card to the database
                (activity as? MainActivity)?.addCardToDatabase(name, description ?: "", photoUri, photoUri2)
                parentFragmentManager.popBackStack()
            } else {
                val errorMessage = when {
                    name.isEmpty() -> "Name cannot be empty."
                    photoUri == null -> "Please select the first photo."
                    else -> "Unknown error occurred."
                }
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                requireContext().contentResolver.takePersistableUriPermission(
                    selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                when (requestCode) {
                    REQUEST_IMAGE_PICK_1 -> {
                        binding.photoPreview1.setImageURI(selectedImageUri)
                        binding.photoPreview1.tag = selectedImageUri.toString()
                    }
                    REQUEST_IMAGE_PICK_2 -> {
                        binding.photoPreview2.setImageURI(selectedImageUri)
                        binding.photoPreview2.tag = selectedImageUri.toString()
                    }
                }
            }
        }
    }
}