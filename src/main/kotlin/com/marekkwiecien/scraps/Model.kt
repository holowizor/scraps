package com.marekkwiecien.scraps

import org.dizitart.no2.objects.Id
import java.io.Serializable

data class Context(@Id val id: Long, val name: String) : Serializable
data class Scrap(@Id val id: Long, val contextId: Long, val contextName: String, val name: String) : Serializable
