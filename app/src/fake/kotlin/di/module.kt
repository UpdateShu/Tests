package di

import com.geekbrains.tests.presenter.RepositoryContract
import org.koin.dsl.module

val module = module {
    single<RepositoryContract> { repository.FakeGitHubRepository() }
}