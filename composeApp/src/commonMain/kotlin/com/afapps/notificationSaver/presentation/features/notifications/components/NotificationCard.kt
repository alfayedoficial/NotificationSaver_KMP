import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.afapps.notificationSaver.core.theme.TemplateBlue
import com.afapps.notificationSaver.core.theme.TemplateRed
import com.afapps.notificationSaver.core.utilities.DisplayIcon
import com.afapps.notificationSaver.core.utilities.DisplayImage
import com.afapps.notificationSaver.core.utilities.formatTime
import com.afapps.notificationSaver.domain.model.SubNotification
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotificationCard(
    notification: SubNotification,
    onReadClick: (id: Long) -> Unit = {},
    onDeleteClick: (id: Long) -> Unit = {},
    paddingEnd: Dp = 0.dp,
                     ) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .padding(start = 8.dp , end = 8.dp , top = 8.dp , bottom = paddingEnd)
            .fillMaxWidth(),
        onClick = { onReadClick(notification.id) },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ){
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = notification.appName, style = MaterialTheme.typography.subtitle1)
                        Text(text = notification.senderName, style = MaterialTheme.typography.body2)
                        Text(
                            text = formatTime(notification.time),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                IconButton(
                    onClick = { onDeleteClick(notification.id) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = TemplateRed,
                        modifier = Modifier.size(40.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                if (notification.largeIcon != null) {
                    DisplayIcon(notification.largeIcon)
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text = notification.title, style = MaterialTheme.typography.body1)
            }
            Text(text = notification.content, style = MaterialTheme.typography.body1)
            notification.subText?.let { Text(text = it, style = MaterialTheme.typography.body1) }
            notification.bigText?.let { Text(text = it, style = MaterialTheme.typography.body1) }
            notification.summaryText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            if (notification.picture != null) {
                DisplayImage(notification.picture)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Display extra data
            notification.parseExtrasJson()?.let {
                Column {
                    it.forEach { (key, value) ->
                        Row {
                            Text(text = key, style = MaterialTheme.typography.body2)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = value?.toString() ?: "empty",
                                style = MaterialTheme.typography.body2,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            if (notification.isRead){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    Icon(
                        painter = painterResource(Res.drawable.ic_nsaver),
                        contentDescription = null,
                        tint = TemplateBlue,
                        modifier = Modifier.size(25.dp)
                    )

                }
            }

        }
    }
}
