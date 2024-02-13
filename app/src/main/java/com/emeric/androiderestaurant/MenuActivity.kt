package com.emeric.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.emeric.androiderestaurant.network.Category
import com.emeric.androiderestaurant.network.Dish
import com.emeric.androiderestaurant.network.MenuResult
import com.emeric.androiderestaurant.network.NetworkConstants
import com.google.gson.GsonBuilder
import org.json.JSONObject

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = (intent.getSerializableExtra(CATEGROY_EXTRA_KEY) as? DishType) ?: DishType.STARTER

        setContent {
            MenuView(type)
        }
        Log.d("lifeCycle", "Menu Activity - OnCreate")
    }

    override fun onPause() {
        Log.d("lifeCycle", "Menu Activity - OnPause")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "Menu Activity - OnResume")
    }

    override fun onDestroy() {
        Log.d("lifeCycle", "Menu Activity - onDestroy")
        super.onDestroy()
    }

    companion object {
        val CATEGROY_EXTRA_KEY = "CATEGROY_EXTRA_KEY"
    }
}

@Composable
fun MenuView(type: DishType) {
    val category = remember {
        mutableStateOf<Category?>(null)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(type.title())
        LazyColumn {
            category.value?.let {
                items(it.items) {
                    dishRow(it)
                }
            }
        }
    }
    postData(type, category)
}

@Composable fun dishRow(dish: Dish) {
    Card {
        Text(dish.name)
    }
}

@Composable
fun postData(type: DishType, category: MutableState<Category?>) {
    val currentCategory = type.title()
    val context = LocalContext.current
    val queue = Volley.newRequestQueue(context)

    val params = JSONObject()
    params.put(NetworkConstants.ID_SHOP, "1")

    val request = JsonObjectRequest(
        Request.Method.POST,
        NetworkConstants.URL,
        params,
        { response ->
            Log.d("request", response.toString(2))
            val result = GsonBuilder().create().fromJson(response.toString(), MenuResult::class.java)
            val filteredResult = result.data.first { categroy -> categroy.name == currentCategory }
            category.value = filteredResult
        },
        {
            Log.e("request", it.toString())
        }
    )

    queue.add(request)

}