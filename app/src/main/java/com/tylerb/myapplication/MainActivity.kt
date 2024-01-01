package com.tylerb.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.material.datepicker.MaterialDatePicker
import com.tylerb.myapplication.adapter.ViewPagerFragmentState
import com.tylerb.myapplication.databinding.ActivityMainBinding
import com.tylerb.myapplication.ui.theme.ScriptureTheme
import com.tylerb.myapplication.util.DataResult
import com.tylerb.myapplication.util.gospelLibraryUrl
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        title = "Daily Book Of Mormon"

        val pagerAdapter = ViewPagerFragmentState(this)
        binding.viewPager.adapter = pagerAdapter

        val dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        // have to minus one because DAY_OF_YEAR starts dec 31
        binding.viewPager.setCurrentItem(dayOfYear - 1, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.calendar -> {
                val picker = MaterialDatePicker.Builder.datePicker()
                    .setTheme(R.style.Dialog_Theme)
                    .build()
                picker.show(supportFragmentManager, "tag")
                picker.addOnPositiveButtonClickListener {
                    val zone = TimeZone.getTimeZone("UTC")
                    val calendar = Calendar.getInstance(zone)
                    calendar.time = Date(it)
                    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
                    // have to minus one because DAY_OF_YEAR starts dec 31
                    binding.viewPager.setCurrentItem(dayOfYear - 1, false)
                }
                true

            }
            R.id.library -> {
                val mainTitle = findViewById<TextView>(R.id.tv_ref_main).text.toString()
                val url = gospelLibraryUrl(mainTitle)
                startActivity(Intent(Intent.ACTION_VIEW, url))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}

@Composable
fun RowScope.ScriptureActions(
    onCalendarClicked: () -> Unit,
    onOpenInLibrary: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    IconButton(onClick = onCalendarClicked) {
        Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = "Pick a date")
    }
    IconButton(onClick = { showMenu = !showMenu }) {
        Icon(Icons.Default.MoreVert, null)
    }
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = { showMenu = false }
    ) {
        DropdownMenuItem(
            text = { Text(text = stringResource(id = R.string.open_in_gospel_library)) },
            onClick = onOpenInLibrary
        )
    }
}

class MainActivityTwo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ScriptureScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ScriptureScreen(
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    ScriptureTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Daily Book Of Mormon") },
                    actions = {
                        ScriptureActions(
                            onCalendarClicked = {},
                            onOpenInLibrary = {}
                        )
                    },
                    scrollBehavior = scrollBehavior
                )
            },
        ) { scaffoldPadding ->
            val pagerState = rememberPagerState(
                // have to minus one because DAY_OF_YEAR starts dec 31
                initialPage = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1,
                pageCount = { Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR) }
            )
            HorizontalPager(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = scaffoldPadding.calculateTopPadding())
                ,
                state = pagerState,
            ) { dayOfYear ->
                val uiState by viewModel.scriptureData.collectAsStateWithLifecycle()
                val scripture = uiState.scriptures[dayOfYear]

                if (uiState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else if (scripture != null) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = scripture.mainTitle,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = scripture.displayDate,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                            }

                        }
                        items(scripture.scriptures) {
                            Text(text = it)
                        }
                        item {
                            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                        }
                    }
                } else {
                    // show error
                }


            }
        }
    }
}
