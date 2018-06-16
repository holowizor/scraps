package com.marekkwiecien.scraps

import org.dizitart.kno2.filters.eq
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
        } else {
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

    fun findAllContexts(): List<Context> {
        val allContexts = db.getRepository<Context>().find().toList()
        return if (allContexts.size == 0) {
            val newContext = createNewContext("new")
            listOf(newContext)
        } else {
            allContexts
        }
    }

    fun createNewContext(contextName: String): Context {
        val newContext = saveContext(Context(0L, contextName))
        saveScrap(Scrap(0L, newContext.id, "<p>write something here</p>"))
        return newContext
    }

    fun saveScrap(scrap: Scrap): Scrap {
        if (scrap.id == 0L) {
            val newScrap = Scrap(NitriteId.newId().idValue, scrap.contextId, scrap.body)
            db.getRepository<Scrap> {
                insert(newScrap)
            }
            return newScrap
        } else {
            db.getRepository<Scrap> {
                update(scrap)
            }
        }

        return scrap
    }

    fun deleteScrap(scrap: Scrap) {
        db.getRepository<Scrap> {
            remove(scrap)
        }
    }

    fun findScrapsForContext(contextId: Long) = db.getRepository<Scrap>().find(Scrap::contextId eq contextId).toList()
}