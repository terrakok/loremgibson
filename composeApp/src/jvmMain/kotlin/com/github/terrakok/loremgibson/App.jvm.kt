package com.github.terrakok.loremgibson

import androidx.compose.ui.platform.ClipEntry
import java.awt.datatransfer.StringSelection

internal actual fun String.toClipEntry(): ClipEntry = ClipEntry(StringSelection(this))
