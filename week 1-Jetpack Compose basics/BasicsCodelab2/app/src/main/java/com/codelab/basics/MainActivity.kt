package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun MyApp(){
    /*
        In Composable functions,
        state that is read or modified by multiple functions should live in a common ancestor—this process is called [state hoisting].
        To hoist means to lift or elevate.
     */
    var shouldShowOnBoarding by remember { mutableStateOf(true) }

    if(shouldShowOnBoarding) {
        OnBoardingScreen { shouldShowOnBoarding = false }
    }
    else {
        Greetings()
    }

}
@Composable
fun Greetings(names: List<String> = List(1000){it.toString()}) {
    /*
        To display a scrollable column we use a LazyColumn.
        LazyColumn renders only the visible items on screen, allowing performance gains when rendering a big list.

        ** Note: LazyColumn and LazyRow are equivalent to RecyclerView in Android Views.
        ** Note: Make sure you
            import androidx.compose.foundation.lazy.items
            as Android Studio will pick a different items function by default.
     */
    LazyColumn(modifier = Modifier.padding(4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }

    }
}

@Composable
fun Greeting(name: String) {
    /*
        Note that if you call the same composable from different parts of the screen you will create different UI elements,
         each with its own version of the state. You can think of internal state as a private variable in a class.

        The composable function will automatically be "subscribed" to the state. If the state changes,
        composables that read these fields will be recomposed to display the updates.
     */
    val expanded = remember {
        mutableStateOf(false)
    }
    val extraPadding = if (expanded.value) 48.dp else 0.dp
    Surface(
        color = MaterialTheme.colors.primary,
//        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        modifier = Modifier.padding(4.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello,")
                Text(text = "$name!")
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show Less" else "Show More")
            }

        }
    }
}

@Composable
fun OnBoardingScreen(onContinueClicked:()-> Unit) {

    Surface(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelabs!")
            Button(modifier = Modifier.padding(24.dp), onClick = onContinueClicked) {
                Text(text = "Continue")
            }
        }

    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
        OnBoardingScreen{}
//        MyApp()
    }
}