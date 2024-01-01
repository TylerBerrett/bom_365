package com.tylerb.myapplication

import androidx.compose.runtime.Stable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tylerb.myapplication.model.ScriptureResponse
import com.tylerb.myapplication.util.DataResult
import com.tylerb.myapplication.util.dataRunCatching
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.immutableMapOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

data class ScriptureContent(
    val mainTitle: String,
    val displayDate: String,
    val scriptures: ImmutableList<String>
)

data class ScriptureUiState(
    val isLoading: Boolean = false,
    val scriptures: ImmutableMap<Int, ScriptureContent> = persistentMapOf()
)

class MainViewModel: ViewModel() {
    private val repo: Repo = Repo()

    private val _scriptureData: MutableStateFlow<ScriptureUiState> =
        MutableStateFlow(ScriptureUiState())
    val scriptureData: StateFlow<ScriptureUiState>
        get() = _scriptureData.asStateFlow()

    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    init {
        val dayOfYearToScripture = mutableMapOf<Int, ScriptureContent>()
        _scriptureData.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            repo.getScriptures().forEach {
                val dayOfYear = getDayOfYear(it.month, it.day)
                dayOfYearToScripture[dayOfYear] = ScriptureContent(
                    mainTitle = it.title,
                    displayDate = displayDate(dayOfYear),
                    scriptures = it.scriptures.toImmutableList()

                )
            }
            _scriptureData.update {
                it.copy(
                    isLoading = false,
                    scriptures = dayOfYearToScripture.toImmutableMap()
                )
            }
        }
    }

    private fun getDayOfYear(monthName: String, day: Int): Int {
        val month = months.indexOf(monthName)
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        return calendar.get(Calendar.DAY_OF_YEAR)
    }

    private fun displayDate(dayOfYear: Int): String {
        val local = Locale.getDefault()
        val format = SimpleDateFormat("MMMM d", local)
        val date = Calendar.getInstance()
        date.set(Calendar.DAY_OF_YEAR, dayOfYear)
        return format.format(date.time)
    }

}