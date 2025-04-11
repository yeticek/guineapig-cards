package sk.guineapig_cards.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import sk.guineapig_cards.databinding.FragmentCardDetailsBinding
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import sk.guineapig_cards.MainActivity
import sk.guineapig_cards.R

class CardDetailsFragment : Fragment() {
    private var _binding: FragmentCardDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = CardDetailsFragmentArgs.fromBundle(requireArguments())
        binding.cardName.text = args.name
        binding.cardDescription.text = args.description
        binding.cardImage1.setImageURI(args.photoPath1.toUri())
        binding.cardImage2.setImageURI(args.photoPath2.toUri())

        binding.cardImage1.setOnClickListener {
            val action = CardDetailsFragmentDirections.actionCardDetailsFragmentToFullscreenImageFragment(args.photoPath1)
            findNavController().navigate(action)
        }

        binding.cardImage2.setOnClickListener {
            val action = CardDetailsFragmentDirections.actionCardDetailsFragmentToFullscreenImageFragment(args.photoPath2)
            findNavController().navigate(action)
        }
        binding.cardFavouriteIcon.setOnClickListener {
            updateStarIcon(binding.cardFavouriteIcon, args)
        }
    }

    private fun updateStarIcon(favouriteIcon: ImageView, card: CardDetailsFragmentArgs) {
        val itemfromdb = (activity as? MainActivity)?.getCardFromDatabase(card.name)
        val favouriteFromDb = itemfromdb?.favourite ?: 0
        if (favouriteFromDb == card.favourite) {
            if (card.favourite == 1) {
                favouriteIcon.setImageResource(R.drawable.ic_star_empty)
                (activity as? MainActivity)?.updateCardInDatabase(card.name, card.description, 0, card.photoPath1, card.photoPath2)
            } else {
                favouriteIcon.setImageResource(R.drawable.ic_star_full)
                (activity as? MainActivity)?.updateCardInDatabase(card.name, card.description, 1, card.photoPath1, card.photoPath2)
            }
        } else {
            if (favouriteFromDb == 0) {
                favouriteIcon.setImageResource(R.drawable.ic_star_full)
                (activity as? MainActivity)?.updateCardInDatabase(card.name, card.description, card.favourite, card.photoPath1, card.photoPath2)
            } else {
                favouriteIcon.setImageResource(R.drawable.ic_star_empty)
                (activity as? MainActivity)?.updateCardInDatabase(card.name, card.description, card.favourite, card.photoPath1, card.photoPath2)
            }
        }
    }
}