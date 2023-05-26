package com.example.dismobileproject.ui.screens.selection.parameter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import kotlinx.coroutines.launch
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.selection.evaluation.EvaluationListScreen
import com.example.dismobileproject.ui.viewmodels.*
import com.google.gson.Gson

@ExperimentalMaterialApi
@Composable
fun SelectionParameterScreen(
    navController: NavController
){
    var viewModel: SelectionParameterViewModel = viewModel(factory = SelectionParameterViewModel.Factory)
    var selectionUiState = viewModel.selectionUiState
    var selectableParameterState = viewModel.selectableParameterState

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
                is SelectableParameterState.SideEffect -> SideEffectListScreen(viewModel)
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp, end = 15.dp),
                            onClick = {
                                val ROUTE_SELECTION_RESULT = "${Screen.SelectionEvaluation.screenName}?$selectionRoute={$selectionRoute}"
                                val selectionParameterModel = viewModel.getSelectionModel()

                                val selectionParameterJson = Gson().toJson(selectionParameterModel)

                                navController.navigate(
                                    ROUTE_SELECTION_RESULT.replace("{$selectionRoute}", selectionParameterJson)
                        )
                            }
                        ) {
                            Text(text = stringResource(id = R.string.next_button ))
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
                    is SelectionUiState.Success -> ParameterListScreen(
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
                        },
                        onSideEffectAddClick = {
                            coroutineScope.launch {
                                viewModel.updateSelectableParameter(SelectableParameterState.SideEffect)
                                bottomState.show()
                            }
                        }
                    )
                    is SelectionUiState.Error -> ErrorScreen(retryAction = { viewModel.getParamaters() })
                }
            }
        }
    }
}