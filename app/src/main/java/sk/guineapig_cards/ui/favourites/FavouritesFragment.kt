package sk.guineapig_cards.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.guineapig_cards.MainActivity
import sk.guineapig_cards.R
import sk.guineapig_cards.ui.home.CardAdapter
import sk.guineapig_cards.ui.home.HomeFragmentDirections

class FavouritesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_cards)
        return view
    }

    override fun onResume() {
        super.onResume()

        (activity as? MainActivity)?.getFavouriteCardsFromDatabase { cards ->
            adapter = CardAdapter(cards) { selectedCard ->
                // Use the generated directions to navigate
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToCardDetailsFragment(
                    selectedCard.name,
                    selectedCard.description,
                    selectedCard.favourite,
                    selectedCard.photoPath1 ?: "",
                    selectedCard.photoPath2 ?: ""
                )
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.getFavouriteCardsFromDatabase { cards ->
            adapter = CardAdapter(cards) { selectedCard ->
                // Use the generated directions to navigate
                val action = FavouritesFragmentDirections.actionFavouritesFragmentToCardDetailsFragment(
                    selectedCard.name,
                    selectedCard.description,
                    selectedCard.favourite,
                    selectedCard.photoPath1 ?: "",
                    selectedCard.photoPath2 ?: ""
                )
                findNavController().navigate(action)
            }
            recyclerView.adapter = adapter
        }
    }
}