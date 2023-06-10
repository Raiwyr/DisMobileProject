package com.example.dismobileproject.ui.screens.selection.evaluation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dismobileproject.R
import com.example.dismobileproject.data.model.SelectionParameterModel
import com.example.dismobileproject.ui.navigation.Screen
import com.example.dismobileproject.ui.screens.common.ErrorScreen
import com.example.dismobileproject.ui.screens.common.LoadingScreen
import com.example.dismobileproject.ui.screens.selection.parameter.ParameterListScreen
import com.example.dismobileproject.ui.viewmodels.InitSelectionEvaluation
import com.example.dismobileproject.ui.viewmodels.SelectableParameterState
import com.example.dismobileproject.ui.viewmodels.SelectionEvaluationViewModel
import com.example.dismobileproject.ui.viewmodels.SelectionUiState
import com.example.dismobileproject.ui.widgets.OnlyBackBar
import com.google.gson.Gson
import kotlinx.coroutines.launch

@Composable
fun SelectionEvaluationScreen(
    selectionModel: SelectionParameterModel?,
    navController: NavController
){
    var viewModel: SelectionEvaluationViewModel = viewModel(factory = SelectionEvaluationViewModel.Factory)

    if (viewModel.initViewModel == InitSelectionEvaluation.NoInit)
        selectionModel?.let { viewModel.initializeViewModel(it) }

    val selectionRoute = stringResource(R.string.route_param_selection_model)

    Scaffold(
        topBar = {
            OnlyBackBar(
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .size(height = 60.dp, width = 250.dp)
                        .padding(top = 5.dp, bottom = 5.dp, end = 15.dp),
                    onClick = {
                        selectionModel?.let {
                            val ROUTE_SELECTION_RESULT = "${Screen.SelectionResults.screenName}?$selectionRoute={$selectionRoute}"
                            val selectionParameterModel = viewModel.getSelectionModel(it)

                            val selectionParameterJson = Gson().toJson(selectionParameterModel)

                            navController.navigate(
                                ROUTE_SELECTION_RESULT.replace("{$selectionRoute}", selectionParameterJson)
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.action_element_color)),
                    shape = CircleShape
                ) {
                    Text(
                        text = stringResource(id = R.string.done_button ),
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }
    ) {
            paddingValues ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)){
            EvaluationListScreen(viewModel = viewModel)
        }
    }
}