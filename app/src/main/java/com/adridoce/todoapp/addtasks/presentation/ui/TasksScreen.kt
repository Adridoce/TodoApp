package com.adridoce.todoapp.addtasks.presentation.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.adridoce.todoapp.addtasks.presentation.TasksUiState
import com.adridoce.todoapp.addtasks.presentation.model.TaskModel
import com.adridoce.todoapp.addtasks.presentation.viewmodels.TasksViewModel

@Composable
fun TasksScreen(modifier: Modifier, tasksViewModel: TasksViewModel) {

    val showDialog: Boolean by tasksViewModel.showDialog.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TasksUiState>(
        initialValue = TasksUiState.Loading, key1 = lifecycle, key2 = tasksViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            tasksViewModel.uiState.collect { value = it }
        }
    }

    when (uiState) {
        is TasksUiState.Error -> {}
        TasksUiState.Loading -> {
            CircularProgressIndicator()
        }

        is TasksUiState.Success -> {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                FabDialog(Modifier.align(Alignment.BottomEnd), tasksViewModel)
                AddTaskDialog(show = showDialog,
                    onDismiss = { tasksViewModel.closeDialog() },
                    onTaskAdded = { taskName, taskDescription ->
                        tasksViewModel.onTaskAdded(taskName, taskDescription)
                    })
                TaskList(tasksViewModel, (uiState as TasksUiState.Success).taskList)
            }
        }
    }
}

@Composable
fun TaskList(tasksViewModel: TasksViewModel, taskList: List<TaskModel>) {
    LazyColumn {
        items(taskList, key = { it.id }) {
            TaskItem(it, tasksViewModel)
        }
    }
}

@Composable
fun TaskItem(taskModel: TaskModel, tasksViewModel: TasksViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    tasksViewModel.onTaskDeleted(taskModel)
                })
            },
        elevation = CardDefaults.cardElevation(12.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(taskModel.taskName)
                Text(taskModel.taskDescription)
            }
            Checkbox(checked = taskModel.selected,
                onCheckedChange = { tasksViewModel.onCheckedChange(taskModel) })
        }
    }
}

@Composable
fun FabDialog(modifier: Modifier, tasksViewModel: TasksViewModel) {
    FloatingActionButton(
        onClick = { tasksViewModel.showDialog() }, modifier = modifier
    ) {
        Icon(Icons.Filled.Add, contentDescription = "")
    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String, String) -> Unit) {
    if (show) {
        var taskName by remember { mutableStateOf("") }
        var taskDescription by remember { mutableStateOf("") }
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nueva tarea", fontWeight = FontWeight.Bold, fontSize = 18.sp
                )
                Spacer(Modifier.size(8.dp))
                TextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    placeholder = { Text("Tarea") },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(Modifier.size(8.dp))
                TextField(
                    value = taskDescription,
                    onValueChange = { taskDescription = it },
                    placeholder = { Text("Descripcion") },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(Modifier.size(8.dp))
                Button(onClick = { onTaskAdded(taskName, taskDescription) }) {
                    Text("Aceptar")
                }
            }
        }
    }
}