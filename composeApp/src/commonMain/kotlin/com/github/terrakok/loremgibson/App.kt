package com.github.terrakok.loremgibson

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.github.terrakok.loremgibson.theme.AppTheme
import com.github.terrakok.loremgibson.theme.LocalThemeIsDark
import kotlinx.coroutines.launch
import loremgibson.composeapp.generated.resources.Res
import loremgibson.composeapp.generated.resources.arrow_forward
import loremgibson.composeapp.generated.resources.copy
import loremgibson.composeapp.generated.resources.document
import loremgibson.composeapp.generated.resources.ic_dark_mode
import loremgibson.composeapp.generated.resources.ic_light_mode
import loremgibson.composeapp.generated.resources.refresh
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.random.Random
import kotlin.random.nextInt

private val gibsonWords = listOf(
    "nodality", "nodal point", "jeans", "Kowloon", "Shibuya", "digital",
    "hacker", "post-", "futurity", "papier-mache", "rifle", "otaku", "pistol",
    "modem", "uplink", "ablative", "semiotics", "free-market", "narrative",
    "pre-", "meta-", "marketing", "8-bit", "fetishism", "cyber-", "cardboard",
    "bicycle", "range-rover", "city", "network", "realism", "euro-pop", "j-pop",
    "Chiba", "Tokyo", "San Francisco", "franchise", "sprawl", "urban", "decay",
    "monofilament", "katana", "tanto", "math-", "bomb", "grenade", "tiger-team",
    "3D-printed", "plastic", "carbon", "nano-", "tube", "geodesic", "dome", "construct",
    "A.I.", "media", "drone", "sentient", "dolphin", "-ware", "neural", "-space", "artisanal",
    "beef noodles", "wonton soup", "hotdog", "DIY", "Legba", "voodoo god", "systema",
    "systemic", "military-grade", "denim", "saturation point", "order-flow", "soul-delay",
    "long-chain hydrocarbons", "assault", "sensory", "stimulate", "sub-orbital", "BASE jump",
    "youtube", "footage", "paranoid", "apophenia", "spook", "industrial grade", "silent",
    "engine", "RAF", "wristwatch", "shoes", "faded", "concrete", "singularity", "kanji",
    "refrigerator", "computer", "render-farm", "bridge", "disposable", "assassin", "camera",
    "garage", "warehouse", "knife", "fluidity", "towards", "into", "motion", "claymore mine",
    "face forwards", "rebar", "advert", "dead", "tank-traps", "neon", "convenience store",
    "man", "woman", "boy", "girl", "alcohol", "augmented reality", "savant", "weathered",
    "corrupted", "film", "rain", "vehicle", "cartel", "drugs", "smart-", "corporation", "car",
    "sign", "receding", "lights", "physical", "numinous", "table", "sunglasses", "courier",
    "office", "pen", "boat", "tower", "skyscraper", "shrine", "vinyl", "chrome", "market",
    "shanty town", "tattoo", "human", "gang", "crypto-", "dissident"
)

@Preview
@Composable
internal fun App() = AppTheme {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter,
        ) {
            val minW = with(LocalDensity.current) { 750.dp.roundToPx() }
            val maxW = with(LocalDensity.current) { 1000.dp.roundToPx() }
            var containerWidth by remember { mutableStateOf(minW) }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { containerWidth = it.size.width }
                    .verticalScroll(rememberScrollState())
                    .horizontalScroll(rememberScrollState())
                    .layout { m, c ->
                        val minC = containerWidth.coerceIn(minW, maxW)
                        val placeable = m.measure(
                            c.copy(minWidth = minC, minHeight = 0, maxWidth = minC, maxHeight = Int.MAX_VALUE)
                        )
                        layout(placeable.width, placeable.height) {
                            placeable.place(0, 0)
                        }
                    }
                    .padding(40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Lorem Gibson",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(Modifier.weight(1f))
                    val uriHandler = LocalUriHandler.current
                    IconButton(
                        onClick = { uriHandler.openUri("https://github.com/terrakok/loremgibson") }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.document),
                            contentDescription = "Theme",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }

                    var themeIsDark by LocalThemeIsDark.current
                    IconButton(
                        onClick = { themeIsDark = !themeIsDark }
                    ) {
                        Icon(
                            painter = painterResource(
                                if (themeIsDark) Res.drawable.ic_light_mode else Res.drawable.ic_dark_mode
                            ),
                            contentDescription = "Theme",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
                Text(
                    text = "Lorem Gibson is placeholder text generator.\nIt can be used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )

                Spacer(Modifier.height(16.dp))

                var sentencesCount by remember { mutableStateOf(5) }
                var paragraphsCount by remember { mutableStateOf(3) }
                var refresh by remember { mutableStateOf(false) }

                val txt = remember(sentencesCount, paragraphsCount, refresh) {
                    (1..paragraphsCount).joinToString("\n\n") {
                        (1..sentencesCount).joinToString(" ") {
                            generateSentence(Random.nextInt(5..20))
                        }
                    }
                }

                FlowRow {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Sentences: ",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Counter(
                            value = sentencesCount,
                            onValueChange = { sentencesCount = it },
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Paragraphs: ",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Counter(
                            value = paragraphsCount,
                            onValueChange = { paragraphsCount = it },
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { refresh = !refresh }
                        ) {
                            Image(
                                imageVector = vectorResource(Res.drawable.refresh),
                                contentDescription = "Refresh",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                            )
                        }

                        val clipboard = LocalClipboard.current
                        IconButton(
                            onClick = {
                                scope.launch {
                                    clipboard.setClipEntry(txt.toClipEntry())
                                    snackbarHostState.showSnackbar("Text is copied", duration = SnackbarDuration.Short)
                                }
                            }
                        ) {
                            Image(
                                imageVector = vectorResource(Res.drawable.copy),
                                contentDescription = "Copy",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                SelectionContainer(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                ) {
                    Text(
                        text = txt,
                        minLines  = 10,
                    )
                }
            }
        }
    }
}

@Composable
private fun Counter(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            onClick = { onValueChange(value - 1) },
            enabled = value > 1,
        ) {
            Image(
                modifier = Modifier.rotate(180f),
                imageVector = vectorResource(Res.drawable.arrow_forward),
                contentDescription = "Decrease",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
            )
        }
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.titleLarge,
        )
        IconButton(
            onClick = { onValueChange(value + 1) },
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.arrow_forward),
                contentDescription = "Increase",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
            )
        }
    }
}

private fun generateSentence(wordsCount: Int): String {
    return gibsonWords.shuffled().take(wordsCount)
        .mapIndexed { index, word ->
            if (index == 0) word.replaceFirstChar { it.uppercase() }
            else word
        }
        .joinToString(" ")
        .replace("- ", "-")
        .replace(" -", "-") + "."
}

internal expect fun String.toClipEntry(): ClipEntry
