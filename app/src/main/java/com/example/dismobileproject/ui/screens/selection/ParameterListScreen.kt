package com.example.dismobileproject.ui.screens.selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dismobileproject.ui.viewmodels.AscDescUiState
import com.example.dismobileproject.ui.viewmodels.CountUiState
import com.example.dismobileproject.ui.viewmodels.SelectionProductViewModel
import com.example.dismobileproject.ui.widgets.*
import com.example.dismobileproject.R
import com.example.dismobileproject.ui.viewmodels.AvailabilityUiState

@ExperimentalMaterialApi
@Composable
fun ParameterListScreen(
    viewModel: SelectionProductViewModel,
    onIndicationAddClick: () -> Unit = {},
    onContraindicationAddClick: () -> Unit = {}
){
    var selectionViewModel = viewModel
    var indications = viewModel.indicationStates
    var contraindications = viewModel.contraindicationStates

    var priceUiState = selectionViewModel.priceUiState
    var assessmentUiState = selectionViewModel.assessmentUiState
    var reviewsUiState = selectionViewModel.reviewsUiState
    var countUiState = selectionViewModel.countUiState
    var availabilityUiState = selectionViewModel.availabilityUiState

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){
        item(){//Показание (1)
            ExpandableCard(
                title = "Показание"
            ){
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for(item in indications)
                        if (item.isSelected)
                            ClosedTextWithBorder(
                                text = item.Name,
                                onCloseButtonClick = { viewModel.onDeleteIndication(item.Id) },
                                modifier = Modifier.padding(5.dp)
                            )
                }
                AddedTextWithBorder(
                    text = stringResource(id = R.string.add_button),
                    modifier = Modifier.padding(5.dp).width(120.dp),
                    onAddedButtonClick = {onIndicationAddClick()}
                )
            }
        }
        item(){//Противопоказание (до 3)
            ExpandableCard(
                title = "Противопоказание"
            ){
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for(item in contraindications)
                        if (item.isSelected)
                            ClosedTextWithBorder(
                                text = item.Name,
                                onCloseButtonClick = { viewModel.onDeleteContraindication(item.Id) },
                                modifier = Modifier.padding(5.dp))
                }
                AddedTextWithBorder(
                    text = stringResource(id = R.string.add_button),
                    modifier = Modifier.padding(5.dp).width(120.dp),
                    onAddedButtonClick = {onContraindicationAddClick()}
                )
            }
        }
        item(){//Цена (возрастание убывание)
            ExpandableCard(
                title = "Цена"
            ){
                RadioButtonWithText(
                    text = "По возрастанию",
                    checkedButton = priceUiState == AscDescUiState.Ascending,
                    onButtonCheckChange = { selectionViewModel.updatePriceState(AscDescUiState.Ascending) }
                )
                RadioButtonWithText(
                    text = "По убыванию",
                    checkedButton =priceUiState == AscDescUiState.Decreasing,
                    onButtonCheckChange = { selectionViewModel.updatePriceState(AscDescUiState.Decreasing) }
                )
                RadioButtonWithText(
                    text = "Не учитывать",
                    checkedButton =priceUiState == AscDescUiState.Ignore,
                    onButtonCheckChange = { selectionViewModel.updatePriceState(AscDescUiState.Ignore) }
                )
            }
        }
        item(){
            ExpandableCard(//Оценка (возрастание убывание)
                title = "Оценка"
            ){
                RadioButtonWithText(
                    text = "По возрастанию",
                    checkedButton = assessmentUiState == AscDescUiState.Ascending,
                    onButtonCheckChange = { selectionViewModel.updateAssessmentState(AscDescUiState.Ascending) }
                )
                RadioButtonWithText(
                    text = "По убыванию",
                    checkedButton =assessmentUiState == AscDescUiState.Decreasing,
                    onButtonCheckChange = { selectionViewModel.updateAssessmentState(AscDescUiState.Decreasing) }
                )
                RadioButtonWithText(
                    text = "Не учитывать",
                    checkedButton =assessmentUiState == AscDescUiState.Ignore,
                    onButtonCheckChange = { selectionViewModel.updateAssessmentState(AscDescUiState.Ignore) }
                )
            }
        }
        item(){//Количество отзывов (возрастание убывание)
            ExpandableCard(
                title = "Отзывы"
            ){
                RadioButtonWithText(
                    text = "По возрастанию",
                    checkedButton = reviewsUiState == AscDescUiState.Ascending,
                    onButtonCheckChange = { selectionViewModel.updateReviewsState(AscDescUiState.Ascending) }
                )
                RadioButtonWithText(
                    text = "По убыванию",
                    checkedButton =reviewsUiState == AscDescUiState.Decreasing,
                    onButtonCheckChange = { selectionViewModel.updateReviewsState(AscDescUiState.Decreasing) }
                )
                RadioButtonWithText(
                    text = "Не учитывать",
                    checkedButton =reviewsUiState == AscDescUiState.Ignore,
                    onButtonCheckChange = { selectionViewModel.updateReviewsState(AscDescUiState.Ignore) }
                )
            }
        }
        item(){//Колличество результатов (5, 10, Все)
            ExpandableCard(
                title = "Колличество результатов"
            ){
                RadioButtonWithText(
                    text = "5",
                    checkedButton = countUiState == CountUiState.Five,
                    onButtonCheckChange = { selectionViewModel.updateCountState(CountUiState.Five) }
                )
                RadioButtonWithText(
                    text = "10",
                    checkedButton =countUiState == CountUiState.Ten,
                    onButtonCheckChange = { selectionViewModel.updateCountState(CountUiState.Ten) }
                )
                RadioButtonWithText(
                    text = "Все",
                    checkedButton =countUiState == CountUiState.All,
                    onButtonCheckChange = { selectionViewModel.updateCountState(CountUiState.All) }
                )
            }
        }
        item(){//Наличие (Показывать те что закончились или нет)
            ExpandableCard(
                title = "Наличие"
            ){
                RadioButtonWithText(
                    text = "Учитывать",
                    checkedButton = availabilityUiState == AvailabilityUiState.Show,
                    onButtonCheckChange = { selectionViewModel.updateAvailabilityState(AvailabilityUiState.Show) }
                )
                RadioButtonWithText(
                    text = "Не учитывать",
                    checkedButton =availabilityUiState == AvailabilityUiState.NotShow,
                    onButtonCheckChange = { selectionViewModel.updateAvailabilityState(AvailabilityUiState.NotShow) }
                )
            }
        }
    }
}