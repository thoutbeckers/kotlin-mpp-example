//
//  ViewController.swift
//  iosApp
//
//  Created by jetbrains on 12/04/2018.
//  Copyright © 2018 JetBrains. All rights reserved.
//

import UIKit
import mpp
import Firebase

class ViewController: UIViewController {

    func appendLabelText(text: String) {
        self.label.text = self.label.text! + "\n"+text
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // self.label.text = "making network request...\n"
        // Use a Kotlin/Native closure callback
        //        Network(firestore: IOSFirestore()).about(callback: {s in
        //            let text = s.trimmingCharacters(in: NSCharacterSet.whitespacesAndNewlines)
        //            self.label.text = self.label.text! + "\n" + text
        //            return KotlinUnit.init()
        //        })
        
        self.label.text = "Changing mood of "+Emoticon.alien.emoji+" to 😾"
        
        let network = Network(firestore: FirestoreMPPUtil().getAppleFirestore() )
       
        let notKnown = "[not known yet]"
        
        network.followUpdates(user: Emoticon.alien.emoji,
            updateListener: { (userStatus: UserStatusModel) -> KotlinUnit in
                self.appendLabelText(text: "\(userStatus.user) is \(userStatus.mood ?? notKnown)")
                return KotlinUnit()
            },
            errorListener: { (error:KotlinException) -> KotlinUnit in
                self.appendLabelText(text: "Not following status updates: "+error.debugDescription)
                return KotlinUnit()
            }
        )
        
        network.setMood(emoticon: Emoticon.alien, mood: "😾",
            write: { () -> KotlinUnit in
                self.appendLabelText(text: "Set mood to 😾")
                return KotlinUnit()
            },
            failure: { (e: KotlinException) -> KotlinUnit in
                self.appendLabelText(text: "Setting mood didn't work")
                return KotlinUnit()
            }
        )
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBOutlet weak var label: UILabel!
}

