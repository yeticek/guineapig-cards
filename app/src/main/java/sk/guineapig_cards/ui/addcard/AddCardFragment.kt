package sk.guineapig_cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import sk.guineapig_cards.databinding.FragmentAddCardFormBinding

class AddCardFragment : Fragment() {

    private var _binding: FragmentAddCardFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val description = binding.descriptionInput.text.toString()
            val photoPath1 = null // Replace with actual photo path logic
            val photoPath2 = null // Replace with actual photo path logic

            if (name.isNotEmpty()) {
                (activity as? MainActivity)?.addCardToDatabase(name, description, photoPath1, photoPath2)
                Toast.makeText(requireContext(), "Card added successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Name is required!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}