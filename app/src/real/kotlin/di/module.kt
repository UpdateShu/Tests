package di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module {
    single<RepositoryContract> { GitHubRepository(get<Retrofit>().create(GitHubApi::class.java)) }
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() }
}