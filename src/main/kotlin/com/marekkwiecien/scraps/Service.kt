package com.marekkwiecien.scraps

import org.dizitart.kno2.getRepository
import org.dizitart.kno2.nitrite
import org.dizitart.no2.NitriteId
import java.io.File

class ScrapsService {
    private val db = nitrite("sa", "sa") {
        file = File("scraps.nitrite")
        autoCommitBufferSize = 2048
        compress = true
        autoCompact = false
    }

    fun saveContext(context: Context): Context {
        if (context.id == 0L) {
            val newContext = Context(NitriteId.newId().idValue, context.name)
            db.getRepository<Context> {
                insert(newContext)
            }
            return newContext
        }
        else {
            db.getRepository<Context> {
                update(context)
            }
        }

        return context
    }

    fun deleteContext(context: Context) {
        db.getRepository<Context> {
            remove(context)
        }
    }

    fun findAllContexts() = db.getRepository<Context>().find().toList()

}