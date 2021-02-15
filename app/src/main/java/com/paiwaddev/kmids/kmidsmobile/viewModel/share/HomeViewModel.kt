package com.paiwaddev.kmids.kmidsmobile.viewModel.share

import android.content.Context
import android.widget.ListView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.paiwaddev.kmids.kmidsmobile.data.model.StatusBus
import com.paiwaddev.kmids.kmidsmobile.data.model.Student
import com.paiwaddev.kmids.kmidsmobile.useCase.StudentUseCase
import com.paiwaddev.kmids.kmidsmobile.view.ui.home.bus.ShowStatusBus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(private val repository: StudentUseCase, private val mContext: Context) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val compositeDisposable = CompositeDisposable()
    private var sharepref = this.mContext.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)

    private val _student = MutableLiveData<List<Student>>()
    private val _errorMsg = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>(true)
    private val _isSaved = MutableLiveData<Boolean>(false)
    private val _userID = MutableLiveData<String>()
    private val _locations = MutableLiveData<Pair<String, String>>()
    private val _isArea = MutableLiveData<Triple<Boolean, Int, Int>>()
    private val _notify = MutableLiveData<String>()
    private val _isNormaRole = MutableLiveData<Boolean>()
    private val _isRegister =MutableLiveData<Boolean>()

    private val _statusBusList = MutableLiveData<List<StatusBus>>()

    private val _showStatus = MutableLiveData<ArrayList<ShowStatusBus>>().apply {
        value = ArrayList()
    }

    fun getUserId(){
        sharepref = this.mContext.getSharedPreferences("USER_KMIDS", Context.MODE_PRIVATE)
        _userID.value = sharepref.getString("USER_ID", null)
    }

    fun getMenu(){
        val mySet: Set<String> = sharepref.getStringSet("MENU_ID", null) as Set<String>
        for (myString in mySet) {
            println("menu: "+myString)
        }

        if(mySet.size<=1){
            _isNormaRole.value = true
        }else{
            _isNormaRole.value = false
        }

    }

    var isNormalRole: LiveData<Boolean> = _isNormaRole

    fun userId(): LiveData<String> = _userID

    fun getStudent(search: String){
        val body = JsonObject()
        body.addProperty("Search", search)

        _isLoading.value = true
        _isRegister.postValue(false)

        val disposable = repository.processGetStudentUseCase(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ student ->

                    _student.value = student
                    _isLoading.value = false

                    val find = student.filter { it.StatusRegister==1 }
                    if(find.size > 0){
                        _isRegister.postValue(true)
                    }

                }, { error ->
                    _errorMsg.value = error.message
                    _isLoading.value = false
                })

        compositeDisposable.add(disposable)
    }

    fun insetParent(students: List<Student>, search: String){

        val bodyArray = JsonArray()

        for(it in students){
            val body = JsonObject()
            body.addProperty("PersonID", it.PersonID)
            bodyArray.add(body)
        }

        val disposable = repository.processInsertParent(bodyArray)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                println(resp.result)
                _isLoading.value = false
                _isSaved.value = true
            }, { error ->
                _isLoading.value = false
                _isSaved.value = false
            })

        compositeDisposable.add(disposable)

        getStudent(search)
    }

    fun setLocation(lat: String, lon: String){
        val item = Pair<String, String>(lat, lon)
        _locations.value = item
    }

    fun getDistanceArea(lat: String, lon: String): LiveData<Triple<Boolean, Int, Int>>{

        val body = JsonObject()
        body.addProperty("latitude", lat)
        body.addProperty("Longitude", lon)

        _isLoading.value = true

        val _isArea = MutableLiveData<Triple<Boolean, Int, Int>>()

        val disposable = repository.processCheckDistance(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ location ->

                println(location.Distance.toString() + ", " + location.FixDistance.toString())

                _isLoading.value = false
                if (location.Distance > location.FixDistance) {
                    val triple = Triple(false, location.Distance, location.FixDistance)
                    _isArea.value = triple
                } else {
                    val triple = Triple(true, location.Distance, location.FixDistance)
                    _isArea.value = triple
                }

            }, { error ->
                val triple = Triple(false, 0, 0)
                _isArea.value = triple
                _isLoading.value = false
            })

        compositeDisposable.add(disposable)

        return _isArea
    }

    fun getStatusBus(studens: List<Student>){

        val body = JsonArray()
        studens.forEach {
            val data = JsonObject()
            data.addProperty("PersonID",it.PersonID)
            body.add(data)
        }

        println("body: ${body}")

        if (_isLoading.value == false) {
            _isLoading.value = true
        }

        val disposable = repository.processGetStatusBus(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ status ->

                    _statusBusList.value = status
                    _isLoading.value = false

                }, { error ->
                    _errorMsg.value = error.message
                    _isLoading.value = false
                })

        compositeDisposable.add(disposable)

    }
    fun statusBus(): LiveData<List<StatusBus>> = _statusBusList

    val _statusUpDown = MutableLiveData<String>()
    fun onStatusChange(personID: Int): LiveData<String>{
        val _statusUpDown = MutableLiveData<String>()
        _statusBusList.value?.let {
            it.forEach { status ->
                if(status.PersonID == personID){
                    _statusUpDown.value = status.UpDownName
                }
            }
        }
        return _statusUpDown
    }
    //fun statusChange():LiveData<String> = _statusUpDown

    fun testFucn(items : List<Student>){
        val bodyArray = JsonArray()
        items.forEach {
            val body = JsonObject()
            body.addProperty("PersonID",it.PersonID)
            bodyArray.add(body)
        }


    }

    fun getNotifications(string: String){
        _notify.value = string
    }
    fun notification(): LiveData<String> = _notify

    fun errorMessage(): LiveData<String> = _errorMsg

    fun students(): LiveData<List<Student>> = _student

    fun isLoading(): LiveData<Boolean> = _isLoading

    fun isSaved(): LiveData<Boolean> = _isSaved

    fun isClearSeved(){
        _isSaved.value = false
    }

    fun isLocation(): LiveData<Pair<String, String>> = _locations

    fun isArea(): LiveData<Triple<Boolean, Int, Int>> = _isArea

    fun isRegister(): LiveData<Boolean> = _isRegister


    fun isInitStatusBus(students: List<Student>, status: List<StatusBus>): LiveData<Boolean>{
        _showStatus.value = ArrayList()
        for(idx in 0..students.size-1) {
            _showStatus.value?.add(ShowStatusBus(students[idx], status[idx]))
        }

        val isSuccess = MutableLiveData<Boolean>().apply {
            value = true
        }
        return isSuccess
    }

    fun getStatusBusByPersonID(personID: Int): LiveData<ShowStatusBus>{
        val status = MutableLiveData<ShowStatusBus>()
        status.value = _showStatus.value?.filter { it.student.PersonID == personID}?.first()
        return status
    }

    fun updateStatusBus(showStatusBus: ShowStatusBus,index: Int){
        _showStatus.value?.let {
            it[index] = showStatusBus
        }
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}