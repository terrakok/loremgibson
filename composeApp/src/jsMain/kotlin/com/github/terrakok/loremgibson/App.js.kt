package com.github.terrakok.loremgibson

import androidx.compose.ui.platform.ClipEntry

internal actual fun String.toClipEntry(): ClipEntry = ClipEntry.withPlainText(this)