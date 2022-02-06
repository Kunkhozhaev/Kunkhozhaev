package uz.texnopos.labworkapp.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.texnopos.labworkapp.core.Resource
import uz.texnopos.labworkapp.core.isNetworkAvailable
import uz.texnopos.labworkapp.data.model.Result
import uz.texnopos.labworkapp.data.retrofit.ApiInterface
import java.net.UnknownHostException

class MainViewModel(private val api: ApiInterface) : ViewModel() {

    private var _data: MutableLiveData<Resource<List<Result>>> = MutableLiveData()
    val data get() = _data

    fun getAllData() = viewModelScope.launch {
        _data.value = Resource.loading()
        if (isNetworkAvailable()) {
            try {
                val response = api.getAllData(true)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()!!.result
                        _data.value = Resource.success(body)
                    } else _data.value = Resource.error(response.message())
                }
            } catch (e: Exception) {
                if (e is UnknownHostException) _data.value = Resource.networkError()
                else _data.value = Resource.error(e.localizedMessage)
            }
        } else _data.value = Resource.networkError()
    }
}