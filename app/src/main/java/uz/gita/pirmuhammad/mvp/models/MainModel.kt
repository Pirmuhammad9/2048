package uz.gita.pirmuhammad.mvp.models

import uz.gita.pirmuhammad.data.repository.AppRepository
import uz.gita.pirmuhammad.data.repository.Impl.AppRepositoryImpl
import uz.gita.pirmuhammad.mvp.contract.MainContract

class MainModel : MainContract.Model {

    private val repository: AppRepository = AppRepositoryImpl.getAppRepository()

    override fun isAvailableData(): Boolean {
        if (repository.getSharedMatrix() != null) return true
        return false
    }
}