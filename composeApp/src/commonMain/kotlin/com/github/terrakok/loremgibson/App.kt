package com.github.terrakok.loremgibson

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import loremgibson.composeapp.generated.resources.*
import com.github.terrakok.loremgibson.theme.AppTheme
import com.github.terrakok.loremgibson.theme.LocalThemeIsDark
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.stringResource
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            Text(
                text = "Lorem Gibson",
                style = MaterialTheme.typography.displayLarge
            )
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
                    .verticalScroll(rememberScrollState())
            ) {
                Text(text = txt)
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
