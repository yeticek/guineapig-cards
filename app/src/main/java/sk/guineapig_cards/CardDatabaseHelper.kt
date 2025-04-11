package sk.guineapig_cards

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Card(val id: Long = 0, val name: String, val description: String, val photoPath1: String?, val photoPath2: String?)

class CardDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cards.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CARDS = "cards"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_PHOTO_PATH_1 = "photo_path_1"
        const val COLUMN_PHOTO_PATH_2 = "photo_path_2"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_CARDS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_PHOTO_PATH_1 TEXT,
                $COLUMN_PHOTO_PATH_2 TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARDS")
        onCreate(db)
    }

    fun insertCard(card: Card): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, card.name)
            put(COLUMN_DESCRIPTION, card.description)
            put(COLUMN_PHOTO_PATH_1, card.photoPath1)
            put(COLUMN_PHOTO_PATH_2, card.photoPath2)
        }
        return db.insert(TABLE_CARDS, null, values)
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
                val photoPath1 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_1))
                val photoPath2 = getString(getColumnIndexOrThrow(COLUMN_PHOTO_PATH_2))
                cards.add(Card(id, name, description, photoPath1, photoPath2))
            }
            close()
        }
        return cards
    }
}