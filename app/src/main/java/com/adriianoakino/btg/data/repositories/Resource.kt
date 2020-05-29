package com.adriianoakino.btg.data.repositories

class Resource<T> (
    val data: T?,
    val error: String? = null
)