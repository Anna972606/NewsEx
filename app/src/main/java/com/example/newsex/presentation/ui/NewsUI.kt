package com.example.newsex.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.newsex.R
import com.example.newsex.domain.entity.TopHeadlinesModel
import com.example.newsex.ui.theme.NewsExTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsUI(topHeadlines: List<TopHeadlinesModel>?, search: ((String) -> Unit)) {
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column {
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                search.invoke(newText)
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    Icons.Filled.Search, null,
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                }
            ),
            placeholder = {
                Text(text = "Search")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            itemsIndexed(
                items = topHeadlines ?: listOf()
            ) { index, item ->
                ItemNews(item)
                if (index < (topHeadlines ?: listOf()).lastIndex) {
                    Divider(
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemNews(item: TopHeadlinesModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(item.urlToImage)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            error = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = "Name : ${item.name}",
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Author: ${item.author}",
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Title: ${item.title}",
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Description: ${item.description}",
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Published At: ${item.publishedAt}",
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NewsExPreview() {
    NewsExTheme {
        NewsUI(
            listOf(
                TopHeadlinesModel(
                    id = "cbs-news",
                    name = "CBS News",
                    author = "Graham Kates",
                    title = "Trump attends arguments in appeal of judgment in E. Jean Carroll case - CBS News",
                    description = "Attorneys for former President Donald Trump argued that a $5 million judgment finding him liable for sexually abusing and defaming E. Jean Carroll should be thrown out.",
                    url = "https://www.cbsnews.com/news/trump-e-jean-carroll-appeal-judgment/",
                    urlToImage = "https://assets3.cbsnewsstatic.com/hub/i/r/2023/07/19/cdd4da91-d4bd-44d5-99a4-e8548d21c7ff/thumbnail/1200x630g4/b16fd3b4f5cae759ff86ea94146153d8/cnn-l19jb21wb25lbnrzl2ltywdll2luc3rhbmnlcy9szwrlltbhmzlkzmywytm2nzziognknjy5nzkyzdy0yzkznwrh-l19wywdlcy9oxzq5zjninzkxota1ztnhmtvjnjazmjdhmgu3otjimzfj.jpg?v=631cf5f2b4e8db7f9bc428589402864d",
                    publishedAt = "2024-09-06T14:24:54Z",
                    content = "Former President Donald Trump attended arguments in a federal appeals court in New York on Friday as he seeks to erase a $5 million judgment finding him liable for sexually abusing and defaming the w… [+3221 chars]"
                )
            )
        ) {}
    }
}
