package com.example.androidbase.presentation.ui.productos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidbase.data.di.CoroutineDispatchers
import com.example.androidbase.data.remote.products.ProductsRepository
import com.example.androidbase.domain.entities.remote.products.Product
import com.example.androidbase.domain.entities.remote.products.ProductResponse
import com.example.androidbase.domain.state.Result
import com.example.androidbase.presentation.base.BaseViewModel
import com.example.androidbase.presentation.helpers.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
    coroutineDispatchers: CoroutineDispatchers,
    networkHelper: NetworkHelper
) : BaseViewModel(coroutineDispatchers, networkHelper) {

    private val _productsResponse = MutableLiveData<Result<ProductResponse>>()
    val productsResponse: LiveData<Result<ProductResponse>>
        get() = _productsResponse

    private val _singleProductResponse = MutableLiveData<Result<Product>>()
    val singleProductResponse: LiveData<Result<Product>>
        get() = _singleProductResponse

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            safeApiCall(_productsResponse, coroutineDispatchers) {
                val response = repository.getProducts()
                withContext(Dispatchers.Main) {
                    _productsResponse.value = Result.Success(response)
                }
            }
        }
    }

    fun getSingleProduct(productId: Int) {
        viewModelScope.launch {
            delay(2.seconds)
            safeApiCall(_singleProductResponse, coroutineDispatchers) {
                val response = repository.getSingleProduct(productId)
                withContext(Dispatchers.Main) {
                    _singleProductResponse.value = Result.Success(response)
                }
            }
        }
    }


}