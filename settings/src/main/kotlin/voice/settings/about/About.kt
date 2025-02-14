package voice.settings.about

import android.util.TypedValue
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Policy
import androidx.compose.material.icons.rounded.Savings
import androidx.compose.material.icons.rounded.Shop
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.squareup.anvil.annotations.ContributesTo
import voice.common.AppScope
import voice.common.compose.VoiceTheme
import voice.common.compose.rememberScoped
import voice.common.rootComponentAs
import voice.strings.R as StringsR
import voice.common.R as CommonR

@Composable
@Preview
fun AboutPreview() {
  val viewState = AboutViewState(
    appVersion = "1.2.3",
    paddings = "0;0;0;0",
  )
  VoiceTheme(preview = true) {
    About(
      viewState,
      object : AboutListener {
        override fun close() {}
        override fun onPurchaseClick() {}
        override fun onRateClick() {}
        override fun onMoreAppClick() {}
        override fun onPrivacyClick() {}
      },
    )
  }
}

@Composable
private fun About(viewState: AboutViewState, listener: AboutListener) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  val top = viewState.paddings.substringBefore(';').toInt()
  val bottom = viewState.paddings.substringAfter(';').substringBefore(';').toInt()
  val start = viewState.paddings.substringBeforeLast(';').substringAfterLast(';').toInt()
  val end = viewState.paddings.substringAfterLast(';').toInt()
  Scaffold(
    modifier = Modifier
      .nestedScroll(scrollBehavior.nestedScrollConnection)
      .padding(start = start.dp, end = end.dp),
    topBar = {
      TopAppBar(
        title = {
          Text(stringResource(CommonR.string.pref_about_title))
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
          IconButton(
            onClick = {
              listener.close()
            },
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
              contentDescription = stringResource(StringsR.string.close),
            )
          }
        },
        windowInsets = WindowInsets(top = top.dp),
      )
    },
  ) { contentPadding ->
    Box(
      Modifier
        .padding(contentPadding)
        .verticalScroll(rememberScrollState())
    ) {
      Column(Modifier.padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 26.dp)) {
        ListItem(
          modifier = Modifier
//            .clickable {
//              openAbout()
//            }
            .fillMaxWidth(),
          leadingContent = {
            Icon(
              modifier = Modifier.size(72.dp),
              painter = painterResource(id = CommonR.drawable.ic_playbook),
              contentDescription = stringResource(id = CommonR.string.app_name))
          },
          headlineContent = {
            Text(
              modifier = Modifier.padding(start = 12.dp),
              text = stringResource(CommonR.string.app_name),
              fontSize = 18.sp,
            )
          },
          supportingContent = {
            Text(
              modifier = Modifier.padding(start = 12.dp),
              text = stringResource(StringsR.string.pref_app_version) + " " + viewState.appVersion,
              color = LocalContentColor.current.copy(alpha = 0.5F),
            )
          },
        )
        Spacer(modifier = Modifier.size(16.dp))
        HtmlText(stringResource(CommonR.string.pref_about_message))
        Spacer(modifier = Modifier.size(16.dp))
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
          elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
          Row (
            modifier = Modifier
              .fillMaxSize()
              .clickable { listener.onRateClick() },
            verticalAlignment = Alignment.CenterVertically,
            ) {
            Text(modifier = Modifier.padding(start = 16.dp, end  = 8.dp).weight(1f),
              text = stringResource(CommonR.string.pref_rate_title).toUpperCase(LocaleList.current),
              fontSize = 16.sp,
              lineHeight = 18.sp,)
            Box (modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp).width(42.dp)) {
              Icon(modifier = Modifier.alpha(0.2f).size(42.dp), imageVector = Icons.Rounded.Circle, contentDescription = stringResource(id = CommonR.string.pref_rate_title))
              Icon(modifier = Modifier.size(42.dp).padding(8.dp), imageVector = Icons.Rounded.Star, contentDescription = stringResource(id = CommonR.string.pref_rate_title))
            }
          }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
          elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
          Row (
            modifier = Modifier
              .fillMaxSize()
              .clickable { listener.onMoreAppClick() },
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(modifier = Modifier.padding(start = 16.dp, end  = 8.dp).weight(1f),
              text = stringResource(CommonR.string.pref_other_app_title).toUpperCase(LocaleList.current),
              fontSize = 16.sp,
              lineHeight = 18.sp,)
            Box (modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp).width(42.dp)) {
              Icon(modifier = Modifier.alpha(0.2f).size(42.dp), imageVector = Icons.Rounded.Circle, contentDescription = stringResource(id = CommonR.string.pref_other_app_title))
              Icon(modifier = Modifier.size(42.dp).padding(start = 10.dp, end = 10.dp, top = 9.dp, bottom = 11.dp), imageVector = Icons.Rounded.Shop, contentDescription = stringResource(id = CommonR.string.pref_other_app_title))
            }
          }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.inverseOnSurface),
          elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
          Row (
            modifier = Modifier
              .fillMaxSize()
              .clickable { listener.onPrivacyClick() },
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(modifier = Modifier.padding(start = 16.dp, end  = 8.dp).weight(1f),
              text = stringResource(CommonR.string.pref_privacy_title).toUpperCase(LocaleList.current),
              fontSize = 16.sp,
              lineHeight = 18.sp,)
            Box (modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp).width(42.dp)) {
              Icon(modifier = Modifier.alpha(0.2f).size(42.dp), imageVector = Icons.Rounded.Circle, contentDescription = stringResource(id = CommonR.string.pref_privacy_title))
              Icon(modifier = Modifier.size(42.dp).padding(8.dp), imageVector = Icons.Rounded.Policy, contentDescription = stringResource(id = CommonR.string.pref_privacy_title))
            }
          }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
          elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
          Row (
            modifier = Modifier
              .fillMaxSize()
              .clickable { listener.onPurchaseClick() },
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(modifier = Modifier.padding(start = 16.dp, end  = 8.dp).weight(1f),
              text = stringResource(CommonR.string.tipping_jar_title).toUpperCase(LocaleList.current),
              fontSize = 16.sp,
              lineHeight = 18.sp,)
            Box (modifier = Modifier.padding(end = 8.dp, top = 8.dp, bottom = 8.dp).width(42.dp)) {
              Icon(modifier = Modifier.alpha(0.2f).size(42.dp), imageVector = Icons.Rounded.Circle, contentDescription = stringResource(id = CommonR.string.tipping_jar_title))
              Icon(modifier = Modifier.size(42.dp).padding(8.dp), imageVector = Icons.Rounded.Savings, contentDescription = stringResource(id = CommonR.string.tipping_jar_title))
            }
          }
        }
        Spacer(modifier = Modifier.size(bottom.dp))
      }
    }
  }
}

@Composable
fun HtmlText(
  html: String,
  //modifier: Modifier = Modifier,
  color: Color = Color.Unspecified,
  style: TextStyle = LocalTextStyle.current,) {
  val textColor = color
    .takeOrElse { style.color }
    .takeOrElse { LocalContentColor.current.copy(alpha = 1f) }
    .toArgb()

  val density = LocalDensity.current
  val textSize = with(density) {
    style.fontSize
      .takeOrElse { LocalTextStyle.current.fontSize }
      .toPx()
  }
  AndroidView(
    modifier = Modifier.padding(start = 4.dp),
    factory = { context -> TextView(context).apply {
      setTextColor(textColor)
      setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
    }
    },
    update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
  )
}

@ContributesTo(AppScope::class)
interface AboutComponent {
  val aboutViewModel: AboutViewModel
}

@Composable
fun About() {
  val viewModel = rememberScoped { rootComponentAs<AboutComponent>().aboutViewModel }
  val viewState = viewModel.viewState()
  About(viewState, viewModel)
}
