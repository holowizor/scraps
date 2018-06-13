package com.marekkwiecien.scraps

import org.dizitart.no2.objects.Id
import java.io.Serializable

class Context: Serializable {
    @Id
    var id: Long? = null
    var name: String = "new"

    constructor(name: String) {
        this.name = name
    }
}

class Scrap: Serializable {
    @Id
    var id: Long? = null
    var contextId: Long = 0
    var contents: String = ""
}