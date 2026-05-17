package com.example.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import com.example.core.constans.enums.DropDownItem
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> CustomTextFieldDropDown(
    modifier: Modifier = Modifier, 
    defaultItemSelected: DropDownItem<T>, 
    listItem: List<DropDownItem<T>>,
    onItemSelected: (DropDownItem<T>) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember(defaultItemSelected) { mutableStateOf(defaultItemSelected) }
    
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        val activeColor = if (selectedItem.color != androidx.compose.ui.graphics.Color.Unspecified) selectedItem.color else MaterialTheme.colorScheme.primary

        OutlinedTextField(
            value = selectedItem.label,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Icon(imageVector = selectedItem.icon, contentDescription = selectedItem.label, tint = activeColor)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                focusedLabelColor = PrimaryBlue,
                focusedLeadingIconColor = PrimaryBlue,
                errorLeadingIconColor = Danger,
                errorBorderColor = Danger
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            listItem.forEach { item ->
                val itemColor = if (item.color != androidx.compose.ui.graphics.Color.Unspecified) item.color else MaterialTheme.colorScheme.primary
                DropdownMenuItem(
                    text = { 
                        Text(
                            text = item.label,
                            color = itemColor
                        ) 
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = item.icon, 
                            contentDescription = item.label,
                            tint = itemColor
                        )
                    },
                    onClick = {
                        selectedItem = item
                        expanded = false
                        onItemSelected(item)
                    }
                )
            }
        }
    }

}