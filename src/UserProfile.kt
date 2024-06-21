import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.*



@Serializable
data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    @Contextual
    val registrationDate: Date
)
