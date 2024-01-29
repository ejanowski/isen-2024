package com.emeric.androiderestaurant

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.emeric.androiderestaurant.ui.theme.AndroidERestaurantTheme

enum class DishType {
    STARTER, MAIN, DESSERT
}

interface MenuInterface {
    fun dishPressed(dishType: DishType)
}

class HomeActivity : ComponentActivity(), MenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //getString(R.string.menu_starter)
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupView(this)
                }
            }
        }
    }

    override fun dishPressed(dishType: DishType) {
        Toast.makeText(this, "Voici mon toast", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun SetupView(menu: MenuInterface) {
    Column {
        Image(painterResource(R.drawable.ic_launcher_foreground), null)
        Button(onClick = { menu.dishPressed(DishType.STARTER) }) {
            Text(stringResource(R.string.menu_starter))
        }
        Button(onClick = { menu.dishPressed(DishType.MAIN) }) {
            Text(stringResource(R.string.menu_main))
        }
        Button(onClick = { menu.dishPressed(DishType.DESSERT) }) {
            Text(stringResource(R.string.menu_dessert))
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        SetupView(HomeActivity())
    }
}