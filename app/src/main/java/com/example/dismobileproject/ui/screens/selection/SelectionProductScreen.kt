package com.example.dismobileproject.ui.screens.selection

import android.content.ClipData.Item
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.produtclist.product.filter.FilterScreen
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.example.dismobileproject.ui.widgets.SearchBar
import com.example.dismobileproject.ui.widgets.SearchField
import kotlinx.coroutines.launch
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.common.NotResultsScreen
import com.example.dismobileproject.ui.screens.produtclist.product.ProductListScreen
import com.example.dismobileproject.ui.viewmodels.*
import com.example.dismobileproject.ui.widgets.ExpandableCard
import com.google.gson.Gson
import com.squareup.moshi.Moshi

@ExperimentalMaterialApi
@Composable
fun SelectionProductScreen(
    navController: NavController
){
    var viewModel: SelectionProductViewModel = viewModel(factory = SelectionProductViewModel.Factory)
    var selectionUiState = viewModel.selectionUiState
    var selectableParameterState = viewModel.selectableParameterState
    var selectableScreensState = viewModel.selectableScreensState

    var coroutineScope = rememberCoroutineScope()
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val selectionRoute = stringResource(R.string.route_param_selection_model)

    ModalBottomSheetLayout(
        sheetState = bottomState,
        sheetShape = RoundedCornerShape(5),
        sheetContent = {
            when(selectableParameterState){
                is SelectableParameterState.Indication -> IndicationListScreen(viewModel)
                is SelectableParameterState.Contraindication -> ContraindicationListScreen(viewModel)
                is SelectableParameterState.Non -> { Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                OnlyBackBar(
                    onBackClick = { navController.popBackStack() }
                )
            },
            bottomBar = {
                if(selectionUiState == SelectionUiState.Success){
                    when(selectableScreensState){
                        is SelectableScreensState.SelectionParameter -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 15.dp),
                                    onClick = { viewModel.updateSelectableScreensState(SelectableScreensState.EvaluationParameter) }
                                ) {
                                    Text(text = stringResource(id = R.string.next_button ))
                                }
                            }
                        }
                        is SelectableScreensState.EvaluationParameter -> {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                                    onClick = { viewModel.updateSelectableScreensState(SelectableScreensState.SelectionParameter) }
                                ) {
                                    Text(text = stringResource(id = R.string.back_button ))
                                }
                                Button(
                                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 15.dp),
                                    onClick = {

                                        val ROUTE_SELECTION_RESULT = "${Screen.SelectionResults.screenName}?$selectionRoute={$selectionRoute}"
                                        val selectionParameterModel = viewModel.getSelectionModel()

                                        val selectionParameterJson = Gson().toJson(selectionParameterModel)

                                        navController.navigate(
                                            ROUTE_SELECTION_RESULT.replace("{$selectionRoute}", selectionParameterJson)
                                        )
                                    }
                                ) {
                                    Text(text = stringResource(id = R.string.done_button ))
                                }
                            }
                        }
                    }

                }
            }
        ) {
                paddingValues ->
            Surface(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)){
                when(selectionUiState){
                    is SelectionUiState.Loading -> LoadingScreen()
                    is SelectionUiState.Success ->
                        when(selectableScreensState){
                            is SelectableScreensState.SelectionParameter -> ParameterListScreen(
                                viewModel = viewModel,
                                onIndicationAddClick = {
                                    coroutineScope.launch {
                                        viewModel.updateSelectableParameter(SelectableParameterState.Indication)
                                        bottomState.show()
                                    }
                                },
                                onContraindicationAddClick = {
                                    coroutineScope.launch {
                                        viewModel.updateSelectableParameter(SelectableParameterState.Contraindication)
                                        bottomState.show()
                                    }
                                }
                            )
                            is SelectableScreensState.EvaluationParameter -> EvaluationListScreen(viewModel)
                        }
                    is SelectionUiState.Error -> ErrorScreen(retryAction = { viewModel.getParamaters() })
                }
            }
        }
    }
}