package com.challenge.testcompose

import androidx.compose.runtime.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.testcompose.ui.theme.TestComposeTheme
import com.challenge.testcompose.ui.theme.UiState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            TestComposeTheme {
                MainScreen()

                /*
            Column {
                UseFlowViewModelWithMutableStateFlow()
                UseViewModelWithMutableState()
                UserInputScreenMutableState()
                GreetingPreview()
                }*/
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            UserViewModelWithSealedClass(snackbarHostState)
            Spacer(Modifier.size(16.dp))
            UserViewModelWithSharedFlow(snackbarHostState)
        }
    }
}

@Composable
fun UserViewModelWithSealedClass(snackbarHostState: SnackbarHostState, viewModel: MyViewModelWithSealedClass = MyViewModelWithSealedClass()) {
    var counter = viewModel.counter.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                is UiState.Loading -> {
                    isLoading = true
                }

                is UiState.Error -> {
                    isLoading = false
                    snackbarHostState.showSnackbar(it.message)
                }

                else -> {
                    isLoading = false
                    snackbarHostState.showSnackbar("Counter incremented")
                }
            }
        }
    }
    Column{
        Button(
            onClick = { viewModel.increament() },
            enabled = !isLoading,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        )
        {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            } else
                Text(text = counter.value.toString())
        }
    }
}

@Composable
fun UserViewModelWithSharedFlow(snackbarHostState: SnackbarHostState, viewModel: MyViewModelWithSharedFlow = MyViewModelWithSharedFlow()) {
    val counter by viewModel.counter.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Button(
        onClick = { viewModel.incrementCounter() },
        enabled = !isLoading,
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else {
            Text(
                text = counter.toString(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun UseFlowViewModelWithMutableStateFlow(viewModel: MyViewModelWithMutableStateFlow = MyViewModelWithMutableStateFlow()) {
    val counter by viewModel.counter.collectAsState()

    Button(
        onClick = {
            viewModel.increaseCounter()
        },
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(60.dp)
    ) {
        Text(
            text = counter.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    }
}


//@Preview(showBackground = true)
@Composable
fun UseViewModelWithMutableState(viewModel: ViewModelWithMutableState = ViewModelWithMutableState()) {

    val counter by viewModel.counter

    Button(
        onClick = {
            viewModel.increaseCounter()
        },
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(60.dp)
    ) {
        Text(
            text = counter.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun UserInputScreenMutableState() {
    var counter by remember { mutableIntStateOf(1) }
    Button(
        onClick = {
            counter++
        },
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .height(60.dp)
    ) {
        Text(
            text = counter.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
        )
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val itemsList = List(10) { "Hello ${it + 1}" }
    LazyRow {
        items(itemsList) {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }
    }
}