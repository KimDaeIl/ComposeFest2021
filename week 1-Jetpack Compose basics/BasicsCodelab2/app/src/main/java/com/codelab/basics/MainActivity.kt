package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basics.ui.theme.BasicsCodelab2Theme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelab2Theme {
                MyApp()
            }
        }
    }
}

/*
Composable 내부에서 일반 메소드 호출 가능
일반 메소드 내부에서 Composable 호출 불가
=> Composable은 Composable에서 호출 가능
 */

@Composable
fun MyApp(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(4.dp)) {
        for (s in names) {
            Greeting(name = s)
        }
    }
//    Greeting(name = "Android")
}

@Composable
fun Greeting(name: String) {
    Surface(
        color = MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        modifier = Modifier.padding(4.dp)
    ) {
        Row (modifier = Modifier.padding(24.dp)){

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Hello,")
                Text(text = "$name!")
            }
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Show More")
            }
            
        }
    }
}


@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        MyApp()
    }
}