package com.coreteam.easytodos.di.module

import com.coreteam.easytodos.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesMainViewModule(): MainViewModel {
        return MainViewModel()
    }

}