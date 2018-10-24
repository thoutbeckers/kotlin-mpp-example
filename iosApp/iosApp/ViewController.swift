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
        let product = Factory().create(config: ["user": "JetBrains"])
        label.text = product.description
        
        label.text = label.text!+"\n"+"Making network request..."
        
        // Use a Kotlin/Native closure callback
        Network().about(callback: {s in
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

