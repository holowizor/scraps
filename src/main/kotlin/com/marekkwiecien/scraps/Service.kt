package com.marekkwiecien.scraps

import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.ObjectRepository

class ScrapsService {
    private val db: Nitrite = initDb()
    private val contextRepository: ObjectRepository<Context> = db.getRepository(Context::class.java)
    private val scrapRepository: ObjectRepository<Scrap> = db.getRepository(Scrap::class.java)

    private fun initDb() = Nitrite.builder().filePath("scraps.nitrite").openOrCreate("sa", "sa")

    fun saveContext(context: Context) = if (context.id == null) contextRepository.insert(context) else contextRepository.update(context)
    fun deleteContext(context: Context) = contextRepository.remove(context)
    fun findAllContexts() = contextRepository.find()
}