package garcia.marco.myinterview.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import garcia.marco.myinterview.data.repository.AddUserRemoteDataSource
import garcia.marco.myinterview.data.repository.AddUserRepository
import garcia.marco.myinterview.data.repository.GetListRemoteDataSource
import garcia.marco.myinterview.data.repository.GetListRepository

@Module
@InstallIn(ActivityRetainedComponent::class)
class RepositoryModule {

    @Provides
    fun provideGetListRepository(
        remoteDataSource: GetListRemoteDataSource) : GetListRepository =
        GetListRepository(remoteDataSource)

    @Provides
    fun provideAddUserRepository(
        remoteDataSource: AddUserRemoteDataSource) : AddUserRepository =
        AddUserRepository(remoteDataSource)

}