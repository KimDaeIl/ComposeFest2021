package com.codelab.basics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun MyApp() {
    /*
        In Composable functions,
        state that is read or modified by multiple functions should live in a common ancestor—this process is called [state hoisting].
        To hoist means to lift or elevate.

        ---------------

        The [remember] function works only as long as the composable is kept in the Composition.
        When you rotate, the whole activity is restarted so all state is lost.
        Instead of using remember you can use [rememberSaveable].
        This will save each state surviving configuration changes (such as rotations) and process death.
     */
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnBoarding) {
        OnBoardingScreen { shouldShowOnBoarding = false }
    } else {
        Greetings()
    }

}

@Composable
fun Greetings(names: List<String> = List(1000) { it.toString() }) {
    /*
        To display a scrollable column we use a LazyColumn.
        LazyColumn renders only the visible items on screen, allowing performance gains when rendering a big list.

        ** Note: LazyColumn and LazyRow are equivalent to RecyclerView in Android Views.
        ** Note: Make sure you
            import androidx.compose.foundation.lazy.items
            as Android Studio will pick a different items function by default.

        LazyColumn, LazyRow not recycle it's child like RecyclerView
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
//    val expanded = rememberSaveable {
//        mutableStateOf(false)
//    }

    /*
        https://developer.android.com/jetpack/compose/animation?authuser=4
        It returns a State object whose value will continuously be updated by the animation until it finishes.
        It takes a "target value" whose type is Dp

        [animationSpec] parameter that lets you customize the animation.

        ** Note that we are also making sure that padding is never negative, otherwise it could crash the app.
        This introduces a subtle animation bug that we'll fix later in Finishing touches.
     */
//    val extraPadding by animateDpAsState(
//        if (expanded.value) 48.dp else 0.dp,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
    Card(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        CardContent(name)
    }

}

@Composable
fun CardContent(name: String) {

    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello,")
            Text(text = "$name!", style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold))
            if (expanded) {
                Text(
                    "Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ".repeat(4)
                )
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = stringResource(id = if (expanded) R.string.show_less else R.string.show_more)
            )
        }
    }

}

@Composable
fun OnBoardingScreen(onContinueClicked: () -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
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


//@Preview(
//    showBackground = true,
//    widthDp = 320, heightDp = 320,
//    uiMode = UI_MODE_NIGHT_YES,
//    name = "DefaultPreviewDark"
//)
//@Composable
//fun DefaultPreviewDark() {
//    BasicsCodelab2Theme {
//        OnBoardingScreen {}
////        MyApp()
//    }
//}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun DefaultPreview() {
    BasicsCodelab2Theme {
//        OnBoardingScreen {}
        Greetings()
//        MyApp()
    }
}