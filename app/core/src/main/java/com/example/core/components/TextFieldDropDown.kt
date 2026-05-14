package com.example.core.components

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.core.constans.enums.DropDownItem

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
        OutlinedTextField(
            value = selectedItem.label,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Icon(imageVector = selectedItem.icon, contentDescription = selectedItem.label)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listItem.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item.label) },
                    leadingIcon = {
                        Icon(imageVector = item.icon, contentDescription = item.label)
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