package houtbecke.rs.mpp.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.SnapshotMetadata

fun getFirestoreInstance(): FirestoreMPP {
    val fbs = FirebaseFirestore.getInstance();

    // always use this settings for new projects
    fbs.firestoreSettings =
            FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .build()
    return AndroidFirestore(fbs)
}

class AndroidFirestore(val base: FirebaseFirestore) : FirestoreMPP {
    override fun document(documentPath: String): DocumentReferenceMPP {
        return AndroidDocumentReferenceMPP(base.document(documentPath))
    }

    override fun collection(collectionPath: String): CollectionReferenceMPP {
        return AndroidCollectionReferenceMPP(base.collection(collectionPath))
    }
}

class AndroidCollectionReferenceMPP(val base: CollectionReference): CollectionReferenceMPP {
    override fun document(documentPath: String): DocumentReferenceMPP {
        return AndroidDocumentReferenceMPP(base.document(documentPath))
    }
}

class AndroidListenerRegistrationMPP(val base: ListenerRegistration): ListenerRegistrationMPP {
    // this will remove both the error and document listener.
    override fun remove() {
        base.remove()
    }
}

class AndroidDocumentReferenceMPP(val base: DocumentReference): DocumentReferenceMPP {
    override fun set(data: FirestoreData, write: OnWrite, failure: OnFailure) {
        base.set(data)
            .addOnSuccessListener {
                write()
            }
            .addOnFailureListener {
                failure(it)
            }
    }

    override fun get(snapshot: OnSnaphot, failure: OnFailure) {
        base.get()
            .addOnSuccessListener {
                snapshot(AndroidDocumentSnapshotMPP(it))
            }
            .addOnFailureListener {
                failure(it)
            }
    }

    override fun onSnapshot(snapshot: OnSnaphot, failure: OnFailure): ListenerRegistrationMPP {
        return AndroidListenerRegistrationMPP (
            base.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if (documentSnapshot != null)
                    snapshot.invoke(AndroidDocumentSnapshotMPP(documentSnapshot))
                if (firebaseFirestoreException != null)
                    failure.invoke(Exception(firebaseFirestoreException))
            }
        )
    }
}


class AndroidDocumentSnapshotMPP(val base: DocumentSnapshot): DocumentSnapshotMPP {
    override val reference: DocumentReferenceMPP
        get() = AndroidDocumentReferenceMPP(base.reference)
    override val id: String
        get() = base.id
    override val metadata: SnapshotMetadataMPP
        get() = AndroidSnapshotMetadataMPP(base.metadata)
    override val data: Map<String, Any>?
        get() = base.data

    override fun exists(): Boolean {
        return base.exists()
    }

    override fun contains(field: String): Boolean {
        return base.contains(field)
    }

    override fun get(field: String): Any? {
        return base.get(field)
    }

    override fun getBoolean(field: String): Boolean? {
        return base.getBoolean(field)
    }

    override fun getDouble(field: String): Double? {
        return base.getDouble(field)
    }

    override fun getString(field: String): String? {
        return base.getString(field)
    }

    override fun getLong(field: String): Long? {
        return base.getLong(field)
    }
}

class AndroidSnapshotMetadataMPP(val base: SnapshotMetadata): SnapshotMetadataMPP {

    override fun isFromCache(): Boolean {
        return base.isFromCache
    }

    override fun hasPendingWrites(): Boolean {
        return base.hasPendingWrites()
    }
}