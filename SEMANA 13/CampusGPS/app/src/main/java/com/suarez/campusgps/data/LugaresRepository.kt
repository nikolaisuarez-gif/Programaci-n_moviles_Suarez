package com.suarez.campusgps.data

import com.suarez.campusgps.model.Lugar

interface LugaresRepository {
    fun obtenerLugares(): List<Lugar>
}
