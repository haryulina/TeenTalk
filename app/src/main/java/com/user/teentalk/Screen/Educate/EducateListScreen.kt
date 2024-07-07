package com.user.teentalk.Screen.Educate

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.user.teentalk.Components.EducateItem
import com.user.teentalk.Components.EmptyList
import com.user.teentalk.Components.Search
import com.user.teentalk.Data.Model.Educate.Educate
import com.user.teentalk.Data.di.Injection
import com.user.teentalk.R
import com.user.teentalk.ViewModel.EducateListScreenViewModel
import com.user.teentalk.ui.state.UIState

@Composable
fun EducateListScreen(
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit,
    educateListScreenViewModel: EducateListScreenViewModel = viewModel(
        factory = com.user.teentalk.ViewModel.ViewModelFactory(Injection.provideRepository())
    ),
) {
        val query by educateListScreenViewModel.query
        educateListScreenViewModel.uiState.collectAsState(initial = UIState.Loading).value.let { uiState ->
            when (uiState) {
                is UIState.Loading -> {
                    educateListScreenViewModel.search(query)
                }

                is UIState.Success -> {
                    DashboardContent(
                        query = query,
                        onQueryChange = educateListScreenViewModel::search,
                        educateList = uiState.data,
                        navigateToDetail = navigateToDetail,
                        navigateBack = navigateBack
                    )
                }

                is UIState.Error -> {
                    // Handle error state
                }
            }
        }
}

@Composable
fun DashboardContent(
    query: String,
    onQueryChange: (String) -> Unit,
    educateList: List<Educate>,
    navigateToDetail: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(8.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = navigateBack,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
            Search(
                query = query,
                onQueryChange = onQueryChange,
            )
        }
        if (educateList.isNotEmpty()) {
            EducateListItem(
                educateList = educateList,
                navigateToDetail = navigateToDetail
            )
        } else {
            EmptyList(
                Warning = stringResource(R.string.empty_data),
                modifier = Modifier
                    .testTag("emptyList")
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EducateListItem(
    educateList: List<Educate>,
    navigateToDetail: (Int) -> Unit,
    contentPaddingTop: Dp = 0.dp,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = contentPaddingTop
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier
            .testTag("lazy_list")
    ) {
        itemsIndexed(educateList) { _, item ->
            key(item.id) {
                EducateItem(
                    id = item.id,
                    judul = item.judul,
                    imageUrl = item.imageUrl,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .animateItemPlacement(tween(durationMillis = 200))
                        .clickable {
                            navigateToDetail(item.id,)
                        }
                )
            }
        }
    }
}