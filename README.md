# News App

News App is a simple Android application that fetch all news articles within a past week. It uses (https://newsapi.org) as API source.

## Features

- Display articles within a past week
- Search articles
- Display article detail

## Technology
News App use uses these libraries:
- Jetpack Compose
- Lifecycle View Model
- Navigation Compose
- Coroutine & Flow
- Dagger Hilt
- Ktor Http Client

This project also use new version catalog management .toml to manage all dependency libraries versions.

## Architecture
Architecture of project separates business logic from display (UI) logic. This implemented by structuring project with directories like below:
- core
- ---- utils
- ---- errors
- domain
- ---- usecases
- -------- usecase_1
- -------- usecase_n
- ---- entities
- presentations
- ---- screens
- -------- shared_components
- -------- screen_1
- -------- screen_n
- ---- routing
- ---- themes
- ---- viewmodels
- -------- viewmodels_dir_1
- ------------ view_state_1
- ------------ event_1
- ------------ view_models_1
- ---- utils
- repositories
- ---- repositoty_dir_1
- -------- repository_1
- -------- repository_impl_1
- datasources
- ---- remotes
- -------- api_dir_1
- ------------ api_1
- ------------ request
- ------------ response
- ------------ implementations
- ---------------- retrofit/ktor/etc
- -------------------- api_impl_1
- ---- local
- -------- local_data_dir_1
- ------------ local_data_1
- -------- implementations
- ------------ room-sqlite/realmDB/firestore/etc
- ---------------- local_data_impl_1
- di

#### Core Layer
**Core**: Consists class or file that reusable at each directories or layers like utility class as example.
#### Domain Layer
**Domain**: Consists entity of data that can be used by presentation layer and data layer interface. Domain also contains usecases. Usecase used by presentation layer to trigger an action to data layer.
#### Presentation Layer
**Presentations**: All presentations or UI related file are organized in this directories. Ex: Jetpack compose screens or components, viewmodels, routing, theme, and UI specific utility file
#### Data Layer
**Repositories**: Consists file or class that responsible to process data from various data sources and convert them to entity  models that can be consumed by presentations layer.
**Datasources**: Responsible to fetch data from remote sources like web API or local sources (local database, preferences, file, etc).

## State Management
In this project, MVI(Model View Intent) pattern choosed as app state management. MVI implemented by three main class specifically ViewModel, Event class, and View State class. 
Event used to represent all action that can change View State. In Kotlin, thanks to Sealed Class, we can regroup and handle some related context events as one type.
```
sealed class ExploreNewsEvent {
    data object Initialize :
        ExploreNewsEvent()

    data object DisplayNextPaging :
        ExploreNewsEvent()

    data class SearchArticles(val keyword: String = "") :
        ExploreNewsEvent()
}
```

Meanwhile, State class is used to represents what state currently the screen in. Also, one key that must be noticed is state class must be an immutable class that can only be read. Using immutable class like data class, we can avoid direct modification of object outside viewmodels class.
```
data class ExploreNewsState(
    //initial load flag
    val initialLoad: Boolean = true,

    //search keyword
    val keyword: String = "",

    //article pagination related props
    val articleItems: List<PageItem<Article>> = emptyList(),
    val pagingInfo: PagingInfo = PagingInfo(),

    //fetching flag
    val isFetchingData: Boolean = false,

    //error flag
    val fetchError: AppError? = null
)
```

For handling events and change view state as business logic expected, we use ViewModel. ViewModel cannot be affected by Android configuration changes, thus make it perfect place to store the view state. In this project, we use StateFlow to store state info and to emit state changes.
```
@HiltViewModel
class ExploreNewsVM @Inject constructor(
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {
    private val mState =
        MutableStateFlow(
            ExploreNewsState()
        )
    val state = mState.asStateFlow()

    fun notify(event: ExploreNewsEvent) {
        when (event) {
            ExploreNewsEvent.Initialize -> handleInitialLoad()
            ExploreNewsEvent.DisplayNextPaging -> handleDisplayNextPage()
            is ExploreNewsEvent.SearchArticles -> handleSearchArticles(
                event
            )
        }
    }
}    
```

And for consuming state in Compose screen or component, we use collectAsStateWithLifecycle extension method to convert it to Compose component State.
```
@Composable
fun ExploreNewsScreen(
    newsVM: ExploreNewsVM,
    navController: NavController
) {
    val snackBarHostState =
        remember { SnackbarHostState() }

    val exploreNewsState by newsVM.state.collectAsStateWithLifecycle()
    ...
}    
```
