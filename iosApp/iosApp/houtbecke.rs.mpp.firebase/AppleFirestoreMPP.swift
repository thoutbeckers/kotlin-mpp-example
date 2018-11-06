//
//  iOSFirestoreMPP.swift
//  iosApp
//
//  Created by Tijl Houtbeckers on 04/11/2018.
//

import Foundation
import mpp
import Firebase

class FirestoreMPPUtil {
    
    func getAppleFirestore() -> AppleFirestoreMPP {
        let db = Firestore.firestore()
        let settings = db.settings
        settings.areTimestampsInSnapshotsEnabled = true
        db.settings = settings
        return AppleFirestoreMPP(firestore: db)
    }
}

class AppleFirestoreMPP: NSObject, FirestoreMPP {
    
    let base: Firestore
    init(firestore base: Firestore) {
        self.base = base
        super.init()
    }
    
    func collection(collectionPath: String) -> CollectionReferenceMPP  {
        return AppleCollectionReferenceMPP(base: base.collection(collectionPath))
    }
    
    func document(documentPath: String)  -> DocumentReferenceMPP {
        return AppleDocumentReferenceMPP(base: base.document(documentPath))
    }
   
}

class AppleCollectionReferenceMPP: NSObject, CollectionReferenceMPP {
    let base: CollectionReference
    init(base: CollectionReference) {
        self.base = base
        super.init()
    }
    
    func document(documentPath: String) -> DocumentReferenceMPP {
        return AppleDocumentReferenceMPP(base: base.document(documentPath))
    }
}

class AppleDocumentReferenceMPP: NSObject, DocumentReferenceMPP {

    let base: DocumentReference
    init (base: DocumentReference) {
        self.base = base
        super.init()
    }

    func set(data: [String : Any], write: @escaping () -> KotlinUnit, failure: @escaping (KotlinException) -> KotlinUnit) {
       
        base.setData(data) { (error:Error?) in
            if let e = error {
                _ = failure(KotlinException(message: e.localizedDescription))
            } else {
                _ = write()
            }

        }
    }
    
    func get(snapshot: @escaping (DocumentSnapshotMPP) -> KotlinUnit,
             failure: @escaping (KotlinException) -> KotlinUnit) {
        
        base.getDocument { (document:DocumentSnapshot?, error:Error?) in
            if let d = document {
                _ = snapshot(AppleDocumentSnapshotMPP(base: d))
            }
            if let e = error {
                _ = failure(KotlinException(message: e.localizedDescription))
            }
        }
    }
    
    func onSnapshot(snapshot: @escaping (DocumentSnapshotMPP) -> KotlinUnit, failure: @escaping (KotlinException) -> KotlinUnit) -> ListenerRegistrationMPP {
        
        return AppleListenerRegistrationMPP( base:
            base.addSnapshotListener { (document: DocumentSnapshot?, error:Error?) in
                if let d = document {
                    _ = snapshot(AppleDocumentSnapshotMPP(base: d))
                }
                if let e = error {
                    _ = failure(KotlinException(message: e.localizedDescription))
                }
            }
        )
    }
}

class AppleDocumentSnapshotMPP: NSObject, DocumentSnapshotMPP {
    
    let base: DocumentSnapshot
    init (base: DocumentSnapshot) {
        self.base = base
        super.init()
    }
    
    func exists() -> Bool {
        return base.exists
    }
    
    func contains(field: String) -> Bool {
        return base.get(field) != nil
    }
    
    func get(field: String) -> Any? {
        return base.get(field)
    }
    
    func getBoolean(field: String) -> KotlinBoolean? {
        let r = get(field: field)
        if (r is Bool) {
            return (r as! KotlinBoolean)
        }
        if (r == nil) {
            return nil
        }
        fatalError(field+" is not a boolean")
    }
    
    func getDouble(field: String) -> KotlinDouble? {
        let r = get(field: field)
        if (r is Double) {
            return (r as! KotlinDouble)
        }
        if (r == nil) {
            return nil
        }
        fatalError(field+" is not a Double")
    }
    
    func getString(field: String) -> String? {
        let r = get(field: field)
        if (r is String) {
            return (r as! String)
        }
        if (r == nil) {
            return nil
        }
        fatalError(field+" is not a String")

    }
    
    func getLong(field: String) -> KotlinLong? {
        let r = get(field: field)
        if (r is NSNumber) {
            return (r as! KotlinLong)
        }
        if (r == nil) {
            return nil
        }
        fatalError(field+" is not a long")

    }
    
    var id: String {
        get {
            return base.documentID
        }
    }
    
    var metadata: SnapshotMetadataMPP {
        get {
            return AppleSnapshotMetadataMPP(base: base.metadata)
        }
    }
    
    var data: [String : Any]? {
        
            get {
                return base.data()
            }
        }
    
    var reference: DocumentReferenceMPP {
        get {
            return AppleDocumentReferenceMPP(base: base.reference)
        }
    }
    
}

class AppleSnapshotMetadataMPP: NSObject, SnapshotMetadataMPP {
    func isFromCache() -> Bool {
        return base.isFromCache
    }
    
    func hasPendingWrites() -> Bool {
        return base.hasPendingWrites
    }
    
    let base:SnapshotMetadata
    
    init(base:SnapshotMetadata) {
        self.base = base
        super.init()
    }
}

class AppleListenerRegistrationMPP: NSObject, ListenerRegistrationMPP {
    let base: ListenerRegistration
    init(base: ListenerRegistration) {
        self.base = base
        super.init()
    }
    func remove() {
        base.remove()
    }
    
    
}


