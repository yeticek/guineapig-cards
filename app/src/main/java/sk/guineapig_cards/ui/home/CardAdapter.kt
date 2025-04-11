package sk.guineapig_cards.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.core.net.toUri
import sk.guineapig_cards.Card
import sk.guineapig_cards.R

class CardAdapter(
    private val cards: List<Card>,
    private val onCardClick: (Card) -> Unit // Lambda for click handling
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.card_name)
        val descriptionTextView: TextView = itemView.findViewById(R.id.card_description)
        val imageView1: ImageView = itemView.findViewById(R.id.card_image1)
        val imageView2: ImageView = itemView.findViewById(R.id.card_image2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = cards[position]
        holder.nameTextView.text = card.name
        holder.descriptionTextView.text = card.description
        holder.imageView1.setImageURI(card.photoPath1?.toUri())
        holder.imageView2.setImageURI(card.photoPath2?.toUri())

        // Set click listener
        holder.itemView.setOnClickListener {
            onCardClick(card) // Pass the clicked card to the lambda
        }
    }

    override fun getItemCount(): Int = cards.size
}