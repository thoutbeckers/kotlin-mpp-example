package houtbecke.rs.mpp.firebase

class IOSFirestore: FirestoreMPP {
    override fun collection(path: String): CollectionReferenceMPP {
        throw Exception("Not implemented")
    }
}