//
//  ViewController.swift
//  iosApp
//
//  Created by jetbrains on 12/04/2018.
//  Copyright © 2018 JetBrains. All rights reserved.
//

import UIKit
import mpp

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.label.text = "making network request...\n"
        
        // Use a Kotlin/Native closure callback
        Network(firestore: IOSFirestore()).about(callback: {s in
            let text = s.trimmingCharacters(in: NSCharacterSet.whitespacesAndNewlines)
            self.label.text = self.label.text! + "\n" + text
            return KotlinUnit.init()
        })
        
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBOutlet weak var label: UILabel!
}

