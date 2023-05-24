package garcia.marco.myinterview.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import garcia.marco.myinterview.data.repository.GetListRemoteDataSource
import garcia.marco.myinterview.data.repository.GetListRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    fun provideWeatherRepository(
        remoteDataSource: GetListRemoteDataSource) : GetListRepository =
        GetListRepository(remoteDataSource)

}