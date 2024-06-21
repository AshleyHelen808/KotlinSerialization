import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.Document
import kotlinx.serialization.json.Json
import java.util.*

fun main() {
    //connect to your mongo DB
    fun connectToMongoDB(): MongoDatabase {
        val uri = "mongodb://localhost:27017"
        val client = MongoClients.create(uri)
        return client.getDatabase("userManagement")
    }

    //Add a user to MongoDB and serialize
    fun insertUserProfile(database: MongoDatabase, user: UserProfile) {
        val collection = database.getCollection("users")
        val json = Json.encodeToString(UserProfile.serializer(), user)
        val document = Document.parse(json)
        collection.insertOne(document)
    }
    //Retrieve a user and deserialize
    fun getUserProfile(database: MongoDatabase, userId: String): UserProfile? {
        val collection = database.getCollection("users")
        val document = collection.find(Filters.eq("id", userId)).first() ?: return null
        val json = document.toJson()
        return Json.decodeFromString(UserProfile.serializer(), json)
    }

    //create a user profile
    val database = connectToMongoDB()
    val newUser = UserProfile("1", "Alice", "alice@example.com", Date())
    insertUserProfile(database, newUser)
    println("User profile inserted: $newUser")

   //retrieve a user profile
    val userId = "1"
    val userProfile = getUserProfile(database, userId)
    println("Retrieved user profile: $userProfile")

}