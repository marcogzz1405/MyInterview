package garcia.marco.myinterview.di.modules

import android.content.Context
import android.content.res.Resources
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResourcesModule {

    @Provides
    fun provideResources(context: Context) : Resources {
        return context.resources
    }

    @Provides
    fun providesLocationClient(context : Context) : FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

}