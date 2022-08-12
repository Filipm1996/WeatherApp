package com.example.weathertask.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FlowExtensions {
    companion object {
        inline fun <reified T> Flow<T>.observeWithLifecycle(
            lifecycleOwner: LifecycleOwner,
            minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
            noinline action: suspend (T) -> Unit
        ): Job = lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(lifecycleOwner.lifecycle, minActiveState).collect { action(it) }
        }
    }
}