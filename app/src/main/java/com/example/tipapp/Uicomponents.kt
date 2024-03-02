package com.example.tipapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//Correction in Input Text Feild
@Composable
fun TopCard(totalPerPerson: Double = 125.37) {
    val total = "%.2f".format(totalPerPerson)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(7.dp)
            .clip(shape = RoundedCornerShape(8.dp)),
        color = Color(0xffea80fc)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Total Per Person",
                style = TextStyle(
                    color = Color(0xFFf50057),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = "$$total",
                style = TextStyle(
                    color = Color(0xFFf50057),
                    fontSize = 33.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun MainComponent() {
    val totalBillState = remember {
        mutableStateOf("0")
    }
    val splitPeople = remember {
        mutableStateOf("1")
    }
    val finalPercent = remember {
        mutableStateOf(0)
    }
    val finalbillstate = totalBillState.value.toInt()+((totalBillState.value.toInt() * finalPercent.value) / 100)
    val perPerson = (finalbillstate.toDouble() / splitPeople.value.toInt())
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(7.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopCard(perPerson)
            Spacer(modifier = Modifier.height(4.dp))
            BillForm(
                totalBillState = totalBillState,
                totalPeople = splitPeople,
                finalPercent = finalPercent
            )
        }
    }

}

@Composable
private fun BillForm(
//    onValueChange: (String) -> Unit = {},
    totalBillState: MutableState<String>,
    totalPeople: MutableState<String>,
    finalPercent: MutableState<Int>
) {

    //State for checking if the mutable state is changed 4
//    val billState = remember(totalBillState.value) {
//        totalBillState.value.trim().isNotBlank()
//    }
    val tipAmount = ((totalBillState.value.toInt() * finalPercent.value) / 100).toString()
    //keyboard Controller
//    val keyboardcontroller = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(7.dp)),
        border = BorderStroke(
            width = 2.dp,
            color = Color(0xFFf50057)
        )
    ) {
        Column {
            Spacer(modifier = Modifier.height(4.dp))
            InputField(
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,

                onAction = KeyboardActions {
//                    if (!billState) {
//                        return@KeyboardActions
//                    }
//                    onValueChange(totalBillState.value.trim())
//                    //Else hide keyboard for that make a keyboard controller
//                    keyboardcontroller?.hide()
                }

            )
            Splitpart(splitPeople = totalPeople)
            TipForm(tipAmount = tipAmount, finalPercent = finalPercent)
        }
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    imageModifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    fontSize: TextUnit = 18.sp,
    singleLine: Boolean = true,
    imageVector: ImageVector = Icons.Default.AttachMoney,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = labelId, modifier = Modifier.padding(start = 2.dp)) },
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = "Money Icon", modifier = imageModifier
                    .padding(0.dp)
                    .size(35.dp)
            )
        },
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        singleLine = singleLine,
        textStyle = TextStyle(fontSize = fontSize),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction
    )
}

@Composable
fun Splitpart(
    modifier: Modifier = Modifier,
    splitPeople: MutableState<String>
) {

    Surface(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                modifier = Modifier.padding(7.dp),
                text = "Split",
                style = TextStyle(
                    color = Color(0xFFf50057),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.width(55.dp))
            RoundIconButton(
                imageVector = Icons.Default.Add,
                onClick = { splitPeople.value = (splitPeople.value.toInt() + 1).toString() },

                description = "Add Counter"
            )
            Spacer(modifier = Modifier.width(3.dp))
            InputField(
                valueState = splitPeople,
                labelId = "People",
                enabled = true,
                imageVector = Icons.Default.People,
                modifier = Modifier
                    .width(150.dp)
                    .padding(start = 5.dp, end = 5.dp),
                imageModifier = Modifier.size(20.dp), fontSize = 18.sp
            )
            Spacer(modifier = Modifier.width(3.dp))
            RoundIconButton(
                imageVector = Icons.Default.Minimize,
                onClick = {
                    if (splitPeople.value.toInt() > 1) {
                        splitPeople.value = (splitPeople.value.toInt() - 1).toString()
                    }
                },
                description = "Decrease Counter"
            )
        }
    }
}

@Composable
fun RoundIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String = "Not Defined"
) {
    IconButton(
        onClick = onClick, modifier = modifier.run {
            size(35.dp)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = Color.Black
                )
                .clip(CircleShape)
        }
    ) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun TipForm(tipAmount: String = "0", finalPercent: MutableState<Int>) {
    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }
    Surface(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tip Amount",
                    modifier = Modifier
                        .height(45.dp)
                        .padding(start = 27.dp, top = 10.dp),
                    style = TextStyle(
                        color = Color(0xFFf50057),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.width(140.dp))
                Text(
                    text = "$$tipAmount",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            finalPercent.value = (sliderPositionState.floatValue * 100).toInt()
            Text(
                text = "${finalPercent.value}%",
                modifier = Modifier.padding(bottom = 2.dp),
                style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Black)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Slider(
                value = sliderPositionState.floatValue,
                modifier = Modifier.padding(horizontal = 5.dp),
                steps = 5,
                onValueChange = { newVal -> sliderPositionState.floatValue = newVal }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Mainpreview() {
    MainComponent()
}

