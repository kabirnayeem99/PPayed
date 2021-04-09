package com.kabirnayeem99.paymentpaid.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kabirnayeem99.paymentpaid.data.db.entities.Work
import com.kabirnayeem99.paymentpaid.data.repositories.FakeRepo
import com.kabirnayeem99.paymentpaid.getOrAwaitValueForTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FirestoreViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    lateinit var viewModel: FirestoreViewModel

    @Before
    fun setUp() {
        val viewModel = FirestoreViewModel(FakeRepo())
    }

    @Test
    fun testSaveWork() {
        val viewModel = FirestoreViewModel(FakeRepo())
        val work = Work("1", "work name", 1, 1, 2021, 200, "student name")
        viewModel.saveWork(work)
        val list = viewModel.workList.getOrAwaitValueForTest()
        assertThat(list).contains(work)
    }


    @Test
    fun testDeleteWork() {
        val viewModel = FirestoreViewModel(FakeRepo())
        val work1 = Work("1", "work name", 1, 1, 2021, 200, "student name")
        val work2 = Work("2", "work name", 1, 1, 2021, 200, "student name")
        viewModel.saveWork(work1)
        viewModel.saveWork(work2)
        viewModel.delete(work1)
        val list = viewModel.workList.getOrAwaitValueForTest()
        assertThat(list).doesNotContain(work1)
    }


}