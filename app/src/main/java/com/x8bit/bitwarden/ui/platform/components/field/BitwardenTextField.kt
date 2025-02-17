package com.x8bit.bitwarden.ui.platform.components.field

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbar
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.x8bit.bitwarden.ui.platform.base.util.cardBackground
import com.x8bit.bitwarden.ui.platform.base.util.cardPadding
import com.x8bit.bitwarden.ui.platform.base.util.toPx
import com.x8bit.bitwarden.ui.platform.base.util.withLineBreaksAtWidth
import com.x8bit.bitwarden.ui.platform.components.appbar.color.bitwardenMenuItemColors
import com.x8bit.bitwarden.ui.platform.components.divider.BitwardenHorizontalDivider
import com.x8bit.bitwarden.ui.platform.components.field.color.bitwardenTextFieldColors
import com.x8bit.bitwarden.ui.platform.components.field.toolbar.BitwardenCutCopyTextToolbar
import com.x8bit.bitwarden.ui.platform.components.field.toolbar.BitwardenEmptyTextToolbar
import com.x8bit.bitwarden.ui.platform.components.model.CardStyle
import com.x8bit.bitwarden.ui.platform.components.model.IconResource
import com.x8bit.bitwarden.ui.platform.components.model.TextToolbarType
import com.x8bit.bitwarden.ui.platform.theme.BitwardenTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

/**
 * Component that allows the user to input text. This composable will manage the state of
 * the user's input.
 * @param label label for the text field.
 * @param value current next on the text field.
 * @param modifier modifier for the composable.
 * @param onValueChange callback that is triggered when the input of the text field changes.
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the [value] is empty.
 * @param leadingIconResource the optional resource for the leading icon on the text field.
 * @param trailingIconContent the content for the trailing icon in the text field.
 * @param supportingText optional supporting text that will appear below the text input.
 * @param singleLine when `true`, this text field becomes a single line that horizontally scrolls
 * instead of wrapping onto multiple lines.
 * @param readOnly `true` if the input should be read-only and not accept user interactions.
 * @param enabled Whether or not the text field is enabled.
 * @param textStyle An optional style that may be used to override the default used.
 * @param shouldAddCustomLineBreaks If `true`, line breaks will be inserted to allow for filling
 * an entire line before breaking. `false` by default.
 * @param visualTransformation Transforms the visual representation of the input [value].
 * @param keyboardType the preferred type of keyboard input.
 * @param textToolbarType The type of [TextToolbar] to use on the text field.
 * @param textFieldTestTag The optional test tag associated with the inner text field.
 * @param cardStyle Indicates the type of card style to be applied.
 */
@Composable
fun BitwardenTextField(
    label: String?,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIconResource: IconResource? = null,
    trailingIconContent: (@Composable () -> Unit)? = null,
    supportingText: String? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = BitwardenTheme.typography.bodyLarge,
    shouldAddCustomLineBreaks: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    autoFocus: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textToolbarType: TextToolbarType = TextToolbarType.DEFAULT,
    autoCompleteOptions: ImmutableList<String> = persistentListOf(),
    textFieldTestTag: String? = null,
    cardStyle: CardStyle? = null,
) {
    BitwardenTextField(
        modifier = modifier,
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIconResource = leadingIconResource,
        trailingIconContent = trailingIconContent,
        supportingTextContent = supportingText?.let {
            {
                Text(
                    text = it,
                    style = BitwardenTheme.typography.bodySmall,
                    color = BitwardenTheme.colorScheme.text.secondary,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
        singleLine = singleLine,
        readOnly = readOnly,
        enabled = enabled,
        textStyle = textStyle,
        shouldAddCustomLineBreaks = shouldAddCustomLineBreaks,
        keyboardType = keyboardType,
        isError = isError,
        autoFocus = autoFocus,
        visualTransformation = visualTransformation,
        textToolbarType = textToolbarType,
        autoCompleteOptions = autoCompleteOptions,
        textFieldTestTag = textFieldTestTag,
        cardStyle = cardStyle,
    )
}

/**
 * Component that allows the user to input text. This composable will manage the state of
 * the user's input.
 * @param label label for the text field.
 * @param value current next on the text field.
 * @param modifier modifier for the composable.
 * @param onValueChange callback that is triggered when the input of the text field changes.
 * @param supportingTextContent An optional supporting text composable that will appear below the
 * text input.
 * @param placeholder the optional placeholder to be displayed when the text field is in focus and
 * the [value] is empty.
 * @param leadingIconResource the optional resource for the leading icon on the text field.
 * @param trailingIconContent the content for the trailing icon in the text field.
 * @param singleLine when `true`, this text field becomes a single line that horizontally scrolls
 * instead of wrapping onto multiple lines.
 * @param readOnly `true` if the input should be read-only and not accept user interactions.
 * @param enabled Whether or not the text field is enabled.
 * @param textStyle An optional style that may be used to override the default used.
 * @param shouldAddCustomLineBreaks If `true`, line breaks will be inserted to allow for filling
 * an entire line before breaking. `false` by default.
 * @param visualTransformation Transforms the visual representation of the input [value].
 * @param keyboardType the preferred type of keyboard input.
 * @param textToolbarType The type of [TextToolbar] to use on the text field.
 * @param textFieldTestTag The optional test tag associated with the inner text field.
 * @param cardStyle Indicates the type of card style to be applied.
 */
@Suppress("LongMethod", "CyclomaticComplexMethod")
@Composable
fun BitwardenTextField(
    label: String?,
    value: String,
    onValueChange: (String) -> Unit,
    supportingTextContent: (@Composable ColumnScope.() -> Unit)?,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIconResource: IconResource? = null,
    trailingIconContent: (@Composable () -> Unit)? = null,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    textStyle: TextStyle = BitwardenTheme.typography.bodyLarge,
    shouldAddCustomLineBreaks: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    autoFocus: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textToolbarType: TextToolbarType = TextToolbarType.DEFAULT,
    autoCompleteOptions: ImmutableList<String> = persistentListOf(),
    textFieldTestTag: String? = null,
    cardStyle: CardStyle? = null,
) {
    var widthPx by remember { mutableIntStateOf(0) }
    val focusRequester = remember { FocusRequester() }
    val formattedText = if (shouldAddCustomLineBreaks) {
        value.withLineBreaksAtWidth(
            // Adjust for built in padding
            widthPx = widthPx - 32.dp.toPx(),
            monospacedTextStyle = textStyle,
        )
    } else {
        value
    }
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = formattedText)) }
    val textFieldValue = textFieldValueState.copy(text = value)
    SideEffect {
        if (textFieldValue.selection != textFieldValueState.selection ||
            textFieldValue.composition != textFieldValueState.composition
        ) {
            textFieldValueState = textFieldValue
        }
    }
    val textToolbar = when (textToolbarType) {
        TextToolbarType.DEFAULT -> BitwardenCutCopyTextToolbar(
            value = textFieldValue,
            onValueChange = onValueChange,
            defaultTextToolbar = LocalTextToolbar.current,
            clipboardManager = LocalClipboardManager.current.nativeClipboard,
        )

        TextToolbarType.NONE -> BitwardenEmptyTextToolbar
    }
    var lastTextValue by remember(value) { mutableStateOf(value = value) }
    CompositionLocalProvider(value = LocalTextToolbar provides textToolbar) {
        var hasFocused by remember { mutableStateOf(value = false) }
        Box(modifier = modifier.defaultMinSize(minHeight = 60.dp)) {
            Column(
                modifier = Modifier
                    .onGloballyPositioned { widthPx = it.size.width }
                    .onFocusEvent { focusState -> hasFocused = focusState.hasFocus }
                    .focusRequester(focusRequester)
                    .cardBackground(cardStyle = cardStyle)
                    .cardPadding(cardStyle = cardStyle, vertical = 6.dp)
                    .fillMaxWidth(),
            ) {
                TextField(
                    colors = bitwardenTextFieldColors(),
                    enabled = enabled,
                    label = label?.let { { Text(text = it) } },
                    value = textFieldValue,
                    leadingIcon = leadingIconResource?.let { iconResource ->
                        {
                            Icon(
                                painter = iconResource.iconPainter,
                                contentDescription = iconResource.contentDescription,
                            )
                        }
                    },
                    trailingIcon = trailingIconContent,
                    placeholder = placeholder?.let {
                        {
                            Text(
                                text = it,
                                style = textStyle,
                            )
                        }
                    },
                    onValueChange = {
                        hasFocused = true
                        textFieldValueState = it
                        val stringChangedSinceLastInvocation = lastTextValue != it.text
                        lastTextValue = it.text
                        if (stringChangedSinceLastInvocation) {
                            onValueChange(it.text)
                        }
                    },
                    singleLine = singleLine,
                    readOnly = readOnly,
                    textStyle = textStyle,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                    isError = isError,
                    visualTransformation = visualTransformation,
                    modifier = Modifier
                        .run { textFieldTestTag?.let { testTag(tag = it) } ?: this }
                        .fillMaxWidth(),
                )
                supportingTextContent?.let {
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    BitwardenHorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                    )
                    Spacer(modifier = Modifier.height(height = 12.dp))
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        content = it,
                    )
                    Spacer(modifier = Modifier.height(height = 6.dp))
                }
            }
            val filteredAutoCompleteList = autoCompleteOptions
                .filter { option ->
                    option.startsWith(textFieldValue.text) && option != textFieldValue.text
                }
                .toImmutableList()
            DropdownMenu(
                expanded = filteredAutoCompleteList.isNotEmpty() && hasFocused,
                shape = BitwardenTheme.shapes.menu,
                containerColor = BitwardenTheme.colorScheme.background.primary,
                properties = PopupProperties(),
                onDismissRequest = { hasFocused = false },
            ) {
                filteredAutoCompleteList.forEach {
                    DropdownMenuItem(
                        colors = bitwardenMenuItemColors(),
                        text = { Text(text = it, style = textStyle) },
                        onClick = { onValueChange(it) },
                    )
                }
            }
        }
    }
    if (autoFocus) {
        LaunchedEffect(Unit) { focusRequester.requestFocus() }
    }
}

@Preview
@Composable
private fun BitwardenTextField_preview() {
    BitwardenTheme {
        Column {
            BitwardenTextField(
                label = "Label",
                value = "Input",
                onValueChange = {},
                supportingText = "Hint",
                cardStyle = CardStyle.Top(),
            )
            BitwardenTextField(
                label = "Label",
                value = "",
                onValueChange = {},
                supportingText = "Hint",
                cardStyle = CardStyle.Bottom,
            )
        }
    }
}
