package sk.guineapig_cards.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import sk.guineapig_cards.MainActivity
import sk.guineapig_cards.R

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_cards)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.getAllCardsFromDatabase { cards ->
            adapter = CardAdapter(cards) { selectedCard ->
                // Use the generated directions to navigate
                val action = HomeFragmentDirections.actionHomeFragmentToCardDetailsFragment(
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