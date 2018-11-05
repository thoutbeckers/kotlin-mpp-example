package houtbecke.rs.mpp.firebase

typealias FirestoreData = Map<String, Any>

typealias OnSnaphot = (DocumentSnapshotMPP) -> Unit
typealias OnWrite = () -> Unit

interface FirestoreMPP {
    fun collection(collectionPath:String): CollectionReferenceMPP
    fun document(documentPath:String): DocumentReferenceMPP
}

interface CollectionReferenceMPP {
    fun document(documentPath:String):DocumentReferenceMPP
}

interface ListenerRegistrationMPP {
    fun remove()
}

interface DocumentReferenceMPP {

    fun get(snapshot: OnSnaphot, failure: OnFailure )
    fun set(data: FirestoreData, write: OnWrite, failure: OnFailure )

    fun onSnapshot(snapshot: OnSnaphot, failure: OnFailure ): ListenerRegistrationMPP

    // TODO do we need a listener for a document that is null? investigate Firestore behaviour
}

interface DocumentSnapshotMPP {
    val id: String
    val metadata: SnapshotMetadataMPP
    val data: FirestoreData?
    val reference: DocumentReferenceMPP
    fun exists(): Boolean

    operator fun contains(field: String): Boolean
    operator fun get(field: String): Any?

    fun getBoolean(field: String): Boolean?
    fun getDouble(field: String): Double?
    fun getString(field: String): String?
    fun getLong(field: String): Long?

// Would be nice
//    fun getDocumentReference(field: String): DocumentReferenceMPP?

// @TODO for sure want this though!!
//    fun getBlob(field: String): BlobMPP?
//    fun getGeoPoint(field: String): GeoPointMPP?
//    fun getDate(field: String): DateMPP?
//    fun getTimestamp(field: String): TimestampMPP?
}

interface SnapshotMetadataMPP {
    fun isFromCache(): Boolean
    fun hasPendingWrites(): Boolean
}

interface QuerySnapshotMPP : Iterable<QueryDocumentSnapshotMPP> {

    val metadata: SnapshotMetadataMPP

    //    Query getQuery();

    // @TODO not yet..
    //    List<DocumentChange> getDocumentChanges();
    //    List<DocumentChange> getDocumentChanges(MetadataChanges metadataChanges);

    val documents: List<DocumentSnapshotMPP>


    val isEmpty: Boolean


    fun size(): Int

    override fun iterator():
            Iterator<QueryDocumentSnapshotMPP>
}

interface QueryDocumentSnapshotMPP : DocumentSnapshotMPP {
    override val data: Map<String, Any>?
}




