package com.jingxi.test_xiaorun.ui.weiget

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

/**
 * TextField 前部默认有一个padding,布局上不好处理，所以改用此方案
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(value: String,
             onValueChange: (String) -> Unit,
             modifier: Modifier = Modifier,
             editModifier : Modifier = Modifier,
             enabled: Boolean = true,
             readOnly: Boolean = false,
             textStyle: TextStyle = LocalTextStyle.current,
             label: @Composable (() -> Unit)? = null,
             placeholder: String = "",
             leadingIcon: @Composable (() -> Unit)? = null,
             trailingIcon: @Composable (() -> Unit)? = null,
             supportingText: @Composable (() -> Unit)? = null,
             isError: Boolean = false,
             visualTransformation: VisualTransformation = VisualTransformation.None,
             keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
             keyboardActions: KeyboardActions = KeyboardActions.Default,
             singleLine: Boolean = false,
             maxLines: Int = Int.MAX_VALUE,
             interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
             shape: Shape = RectangleShape,
             color:Color = Color.White,
             cursorColor:Color = Color.White,
             placeholderColor:Color = Color.White){
    Box(modifier = modifier,
        contentAlignment = Alignment.CenterStart){
        var text = value
        if (keyboardOptions.keyboardType == KeyboardType.Password){
            text = value.replace(Regex("\\w"),"*")
        }
        Text(text = text.ifEmpty { placeholder },
            modifier = Modifier
                .wrapContentWidth(Alignment.Start)
                .wrapContentHeight(Alignment.CenterVertically),
            color = if(value.isEmpty()) {placeholderColor} else {color},
            fontSize = textStyle.fontSize,
            maxLines = maxLines,
            textAlign = TextAlign.Start)

        TextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = supportingText,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            interactionSource = interactionSource,
            shape = shape,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                textColor = Color.Transparent,
                placeholderColor = Color.Transparent,
                cursorColor = cursorColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = editModifier
                .wrapContentWidth(Alignment.Start)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    }
}