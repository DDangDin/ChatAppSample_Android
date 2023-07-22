package com.myschoolproject.androidchatapp.presentation.components.login

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myschoolproject.androidchatapp.R

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit,
    backgroundColor: Color,
    borderEnabled: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(backgroundColor)
            .clip(RoundedCornerShape(50.dp))
            .border(border = BorderStroke(if (borderEnabled) 1.34.dp else 0.dp, Color.White), shape = RoundedCornerShape(50.dp))
            .clickable { onClick() }
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = text).uppercase(),
//            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun CustomButton2(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit,
    backgroundColor: Color,
    borderColor: Color = Color.White,
    borderEnabled: Boolean,
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(width = if (borderEnabled) 1.34.dp else 0.dp, borderColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        onClick = { onClick() },
    ) {
        Text(
            text = stringResource(id = text).uppercase(),
//            fontFamily = PretendardFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun CustomButtonPreview() {
    CustomButton(
        text = R.string.login_btn_text,
        onClick = {},
        backgroundColor = Color.Transparent,
        borderEnabled = true
    )
}

@Preview
@Composable
fun CustomButtonPreview2() {
    CustomButton2(
        text = R.string.login_btn_text,
        onClick = {},
        backgroundColor = Color.Transparent,
        borderEnabled = true
    )

}