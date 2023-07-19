package test.app.fabapplication.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _itemList = MutableStateFlow<List<String>>(emptyList())
    val itemList = _itemList.asStateFlow()

    @SuppressLint("SimpleDateFormat")
    fun onFabClicked() {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:SS")
        val date = format.format(Date())
        _itemList.update {
            it.plus(date)
        }
    }
}