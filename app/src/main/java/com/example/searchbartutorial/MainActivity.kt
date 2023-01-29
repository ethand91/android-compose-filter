package com.example.searchbartutorial

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.components.SearchBar
import com.example.data.UserData
import com.example.searchbartutorial.ui.theme.SearchBarTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBarTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun MainScreen() {
    val users = mutableStateListOf<UserData>()
    val user1 = UserData(1, "ethan")
    val user2 = UserData(2, "bob")
    var filteredUsers = remember {
        mutableStateOf(mutableListOf<UserData>(user1, user2))
    }

    users.add(user1)
    users.add(user2)

    Scaffold() {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            
            SearchBar(onSearch = {
                val result = users.filter { user ->
                    user.name.lowercase().contains(it.lowercase())
                }

                if (result.isNotEmpty()) {
                    filteredUsers.value = result.toMutableStateList()
                } else {
                    filteredUsers.value = mutableListOf<UserData>()
                }
            })

            Spacer(modifier = Modifier.height(10.dp))

            if (filteredUsers.value.size == 0) {
                Text(
                    text = stringResource(id = R.string.no_users_text),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 22.sp
                )

                return@Column
            }

            filteredUsers.value.map {
                Text(
                    text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchBarTutorialTheme {
        MainScreen()
    }
}