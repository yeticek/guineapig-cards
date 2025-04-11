package sk.guineapig_cards.repository

import sk.guineapig_cards.model.Card

object CardRepository {
    private val cards = mutableListOf<Card>()

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun getCards(): List<Card> {
        return cards
    }
}