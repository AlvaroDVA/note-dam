package com.dam.ad.notedam.Storage.Categoria

import com.dam.ad.notedam.Config.ConfigStorageType
import com.dam.ad.notedam.Config.MainContext
import com.dam.ad.notedam.Enums.StorageType
import com.dam.ad.notedam.Models.Categoria
import com.dam.ad.notedam.Storage.IStorage
import com.dam.ad.notedam.Storage.Storage
import java.util.*

class StorageCategoriaMongo : IStorage<Categoria> {
    override fun loadAllItems(uuid: UUID): MutableList<Categoria> {
        //TODO:
        ConfigStorageType.saveStorageType(MainContext.mainActivity!!, StorageType.Local)
        TODO("Sin implementar")
    }

    override fun saveAllItems(path: UUID, listaItems: MutableList<Categoria>) {
        //TODO:
        ConfigStorageType.saveStorageType(MainContext.mainActivity!!, StorageType.Local)
        TODO("Sin implementar")
    }
}