package sk.guineapig_cards

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Card(val id: Long = 0, val name: String, val description: String,val favourite: Int, val photoPath1: String?, val photoPath2: String?)

class CardDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cards.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_CARDS = "cards"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_FAVOURITE = "favourite"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PHOTO_PATH_1 = "photo_path_1"
        const val COLUMN_PHOTO_PATH_2 = "photo_path_2"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CARDS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_FAVOURITE INTEGER DEFAULT 0,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_PHOTO_PATH_1 TEXT,
                $COLUMN_PHOTO_PATH_2 TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE $TABLE_CARDS ADD COLUMN $COLUMN_FAVOURITE INTEGER DEFAULT 0")
        }
    }

    fun insertCard(card: Card): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, card.name)
            put(COLUMN_DESCRIPTION, card.description)
            put(COLUMN_FAVOURITE, card.favourite)
            put(COLUMN_PHOTO_PATH_1, card.photoPath1)
            put(COLUMN_PHOTO_PATH_2, card.photoPath2)
        }
        return db.insert(TABLE_CARDS, null, values)
    }

    fun updateCard(card: Card): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, card.name)
            put(COLUMN_DESCRIPTION, card.description)
            put(COLUMN_FAVOURITE, card.favourite)
            put(COLUMN_PHOTO_PATH_1, card.photoPath1)
            put(COLUMN_PHOTO_PATH_2, card.photoPath2)
        }
        return db.update(
            TABLE_CARDS,
            values,
            "name = ?",
            arrayOf(card.name)
        )
    }

    fun getFavouriteCards(): List<Card> {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_CARDS,
            null,
            "$COLUMN_FAVOURITE = ?",
            arrayOf("1"),
            null,
            null,
            null
        )
        val cards = mutableListOf<Card>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val favourite = getInt(getColumnIndexOrThrow(COLUMN_FAVOURITE))
                val photoPath1 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_1))
                val photoPath2 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_2))
                cards.add(Card(id, name, description, favourite, photoPath1, photoPath2))
            }
            close()
        }
        return cards
    }

    fun getAllCards(): List<Card> {
        val db = readableDatabase
        val cursor = db.query(TABLE_CARDS, null, null, null, null, null, null)
        val cards = mutableListOf<Card>()
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val favourite = getInt(getColumnIndexOrThrow(COLUMN_FAVOURITE))
                val photoPath1 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_1))
                val photoPath2 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_2))
                cards.add(Card(id, name, description, favourite,photoPath1, photoPath2))
            }
            close()
        }
        return cards
    }

    fun getCardFromDatabase(name: String): Card? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_CARDS,
            null,
            "$COLUMN_NAME = ?",
            arrayOf(name),
            null,
            null,
            null
        )
        var card: Card? = null
        with(cursor) {
            if (moveToFirst()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val description = getString(getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val favourite = getInt(getColumnIndexOrThrow(COLUMN_FAVOURITE))
                val photoPath1 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_1))
                val photoPath2 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_2))
                card = Card(id, name, description, favourite, photoPath1, photoPath2)
            }
            close()
        }
        return card
    }
}