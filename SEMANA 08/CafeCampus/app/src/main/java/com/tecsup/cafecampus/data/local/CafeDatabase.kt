package com.suarez.cafecampus.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.suarez.cafecampus.data.local.dao.PedidoDao
import com.suarez.cafecampus.data.local.dao.ProductoDao
import com.suarez.cafecampus.data.local.dao.UsuarioDao
import com.suarez.cafecampus.data.local.entity.DetallePedido
import com.suarez.cafecampus.data.local.entity.Pedido
import com.suarez.cafecampus.data.local.entity.Producto
import com.suarez.cafecampus.data.local.entity.Usuario

@Database(
    entities = [Producto::class, Usuario::class, Pedido::class, DetallePedido::class],
    version = 1,
    exportSchema = false
)
abstract class CafeDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: CafeDatabase? = null

        fun getDatabase(context: Context): CafeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CafeDatabase::class.java,
                    "cafe_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
