package sk.guineapig_cards.model

data class Card(
    val name: String,
    val description: String? = null, // Optional field with a default value of null
    val photoUri: String,
    var photoUri2: String,
    var favourite: Boolean = false
)