package com.kabirnayeem99.paymentpaid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabirnayeem99.paymentpaid.data.db.entities.User
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import kotlinx.coroutines.launch


/**
 * WorkViewModel is a object that models the GUI application
 * The data and functionality, such as, [insert], [getAllWorks]
 * provided by this object can be used by the views, such as TextEdit, TextView.
 *
 * This Work View Model encapsulate the
 * behavior and the data of everything the application <h1>PPayed</h1> does.
 */
//class WorkViewModel(private val repository: WorkRepository) : ViewModel() {
//
//    private val _userProfile = MutableLiveData<User>()
//    val userProfile: LiveData<User> = _userProfile
//    private val _works = MutableLiveData<List<Work>>()
//    val works: LiveData<List<Work>> = _works
//
//    /**
//     * Inserts a new work in the database
//     */
//    fun insert(work: Work) = viewModelScope.launch {
//        repository.insert(work)
//    }
//
//    /**
//     * Updates an existing work in the database
//     */
//    fun update(work: Work) = viewModelScope.launch {
//        repository.update(work)
//    }
//
//    /**
//     * Deletes a work in the database
//     */
//    fun delete(work: Work) = viewModelScope.launch {
//        repository.delete(work)
//    }
//
//
//    /**
//     * Gets all the works
//     * @return LiveData of Work List
//     */
//    fun getAllWorks() = repository.getAllWorks()
//
//    /**
//     * Gets all the payments sorted in the monthly order
//     * @return LiveData of the list of payment (Integer)
//     */
//    fun getTotalPaymentsByMonth() = repository.getTotalPaymentByMonth()
//
//    /**
//     * Gets total payment of the existing year
//     * @return LiveData of total payment of the year(Integer)
//     */
//    fun getTotalPaymentByYear() = repository.getTotalPaymentByYear()
//
//    fun getAllWorksSync() = repository.getAllWorksSync()
//
//}