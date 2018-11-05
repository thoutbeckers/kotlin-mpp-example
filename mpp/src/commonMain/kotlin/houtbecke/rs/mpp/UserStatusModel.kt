package houtbecke.rs.mpp

data class UserStatusModel(val user:String, val map: Map<String, Any?> = HashMap()) {
    val mood: String? by map
}
