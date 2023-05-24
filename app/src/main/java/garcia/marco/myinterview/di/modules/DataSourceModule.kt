package garcia.marco.myinterview.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import garcia.marco.myinterview.data.GetListRemoteDataSourceImpl
import garcia.marco.myinterview.data.repository.GetListRemoteDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindsGetListRemoteDataSource(getListRemoteDataSourceImpl: GetListRemoteDataSourceImpl) : GetListRemoteDataSource

}