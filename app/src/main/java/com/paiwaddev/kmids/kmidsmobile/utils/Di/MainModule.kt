package com.paiwaddev.kmids.kmidsmobile.utils.Di

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.paiwaddev.kmids.kmidsmobile.useCase.LoginUseCase
import com.paiwaddev.kmids.kmidsmobile.useCase.StudentUseCase
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingModel
import com.paiwaddev.kmids.kmidsmobile.view.adapter.SettingsAdapter
import com.paiwaddev.kmids.kmidsmobile.view.custom.ProgressBuilder
import com.paiwaddev.kmids.kmidsmobile.view.ui.home.bus.BusChildAdapter
import com.paiwaddev.kmids.kmidsmobile.viewModel.share.HomeViewModel
import com.paiwaddev.kmids.kmidsmobile.viewModel.single.LoginViewModel
import com.paiwaddev.paiwadpos.data.respository.LoginRepository
import com.paiwaddev.paiwadpos.data.respository.LoginRepositoryImpl
import com.paiwaddev.paiwadpos.data.respository.StudentRepository
import com.paiwaddev.paiwadpos.data.respository.StudentRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    //inject setting adapter
    factory { (data: List<SettingModel>, itemLister: SettingsAdapter.ItemListener, touchSwitchListener: SettingsAdapter.SwitchListener) -> SettingsAdapter(data, itemLister,touchSwitchListener) }

    //inject repo
    factory<LoginRepository> { LoginRepositoryImpl(get()) }
    factory<StudentRepository> { StudentRepositoryImpl(get()) }

    //inject use case
    single { LoginUseCase(get()) }
    single { StudentUseCase(get()) }

    //inject viewModel
    viewModel { LoginViewModel(get(),androidContext()) }
    //inject viewModel share
    single { HomeViewModel(get(),androidContext()) }

    //inject busChild adapter
    factory { (fm: FragmentManager,fragments: List<Fragment>) -> BusChildAdapter(fm, fragments) }

    //inject progress dialog
    single { (activity: Activity) -> ProgressBuilder(activity) }
}