package com.afapps.notificationSaver.presentation.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.afapps.notificationSaver.core.theme.TemplateYellow
import com.afapps.notificationSaver.core.utilities.DisplayIcon
import com.afapps.notificationSaver.core.utilities.formatTime
import com.afapps.notificationSaver.domain.model.Notification
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.ic_nsaver
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationUI(
    notification: Notification,
    paddingEnd: Dp = 0.dp,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 8.dp , end = 8.dp , top = 8.dp , bottom = paddingEnd)
            .fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        backgroundColor = Color.White.takeIf { notification.isRead } ?: Color(0xFFF5F5F5)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp , horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (notification.icon != null) {
                DisplayIcon(notification.icon)
            } else {
                // Placeholder image or icon
                Icon(
                    painter = painterResource(Res.drawable.ic_nsaver),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = 8.dp).weight(1f)
            ) {
                Text(text = notification.appName,
                    style = MaterialTheme.typography.h6)

                notification.lastTime?.let {
                    Text(
                        text = formatTime(it),
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            // Circle insert count
            Surface(
                shape = CircleShape,
                color = TemplateYellow,
                modifier = Modifier
                    .size(40.dp) // Set a fixed size to maintain the circular shape
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(4.dp)
                ) {
                    val displayCount = try {
                        val count = notification.countValue.toInt()
                        if (count > 100) "100+" else count.toString()
                    } catch (e: NumberFormatException) {
                        notification.countValue // Fallback to original value if not a valid number
                    }

                    AutoScalingText(
                        text = displayCount,
                        color = Color.White
                    )

                }
            }


            Icon(
                modifier = Modifier.padding(start = 4.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }

}

@Composable
fun AutoScalingText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.White,
    maxFontSize: TextUnit = 14.sp, // Adjust max font size
    minFontSize: TextUnit = 10.sp, // Adjust min font size
    fontWeight: FontWeight = FontWeight.Bold
) {
    BoxWithConstraints(modifier = modifier) {
        val maxWidth = constraints.maxWidth

        // Calculate font size based on the text's length and maxWidth
        val fontSize = remember(text, maxWidth) {
            val calculatedSize = maxFontSize * (maxWidth / (text.length * 12).coerceAtLeast(1).toFloat())
            calculatedSize.value.coerceIn(minFontSize.value, maxFontSize.value)
        }

        Text(
            text = text,
            color = color,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            maxLines = 1,
            softWrap = false
        )
    }
}