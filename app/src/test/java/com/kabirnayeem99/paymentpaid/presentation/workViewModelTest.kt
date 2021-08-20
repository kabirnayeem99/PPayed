package com.kabirnayeem99.paymentpaid.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kabirnayeem99.paymentpaid.domain.models.Work
import com.kabirnayeem99.paymentpaid.data.repositories.FakeWorkRepository
import com.kabirnayeem99.paymentpaid.getOrAwaitValueForTest
import com.kabirnayeem99.paymentpaid.other.Utils
import com.kabirnayeem99.paymentpaid.presentation.viewmodels.WorkViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class workViewModelTest {

    /**
     * InstantTaskExecutorRule swaps the background executor
     * used by the Architecture Components with a different
     * one which executes each task synchronously.
     */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    /**
     * Tests save work function
     *
     * if saving work add the work to the list of work or not
     */
    @Test
    fun testSaveWork() {
        val viewModel = WorkViewModel(FakeWorkRepository())

        val work = Work("1", "work name", 1, 1, 2021, 200, "student name")

        viewModel.saveWork(work)

        val list = viewModel.workList.getOrAwaitValueForTest()

        assertThat(list).contains(work)
    }


    /**
     * Tests deleteWork function
     *
     * if deleting work remove the work from the list of work or not
     */
    @Test
    fun testDeleteWork() {

        val viewModel = WorkViewModel(FakeWorkRepository())

        val work1 = Work("1", "work name", 1, 1, 2021, 200, "student name")
        val work2 = Work("2", "work name", 1, 1, 2021, 200, "student name")

        viewModel.saveWork(work1)
        viewModel.saveWork(work2)
        viewModel.deleteWork(work1)

        val list = viewModel.workList.getOrAwaitValueForTest()

        assertThat(list).doesNotContain(work1)
    }

    /**
     * Test work list live data
     *
     * adds works to list and checks if it is returned or not
     *
     */
    @Test
    fun testWorkList() {

        val viewModel = WorkViewModel(FakeWorkRepository())

        val work1 = Work("1", "work name", 1, 1, 2021, 200, "student name")
        val work2 = Work("2", "work name", 1, 1, 2021, 200, "student name")
        val work3 = Work("3", "work name", 1, 1, 2022, 200, "student name")

        viewModel.saveWork(work1)
        viewModel.saveWork(work2)
        viewModel.saveWork(work3)
        viewModel.deleteWork(work1)

        val expectedResult = listOf(work2, work3)
        val originalResult = viewModel.workList.getOrAwaitValueForTest()

        assertThat(originalResult).isEqualTo(expectedResult)

    }

    /**
     * Test payment of current year live data
     *
     * adds different work of different year
     * and checks if only the work of current year is returned or not.
     *
     */
    @Test
    fun testPaymentOfCurrentYear() {

        val viewModel = WorkViewModel(FakeWorkRepository())

        val work1 = Work("1", "work name", 1, 1, Utils.currentYear.toLong(), 100, "student name")
        val work2 = Work("2", "work name", 1, 1, Utils.currentYear.toLong(), 200, "student name")
        // this is not current year
        val work3 = Work("3", "work name", 1, 1, Utils.currentYear.toLong() + 1L, 300, "student name")

        viewModel.saveWork(work1)
        viewModel.saveWork(work2)
        viewModel.saveWork(work3)

        val expectedTotalPaymentOfCurrentYear = work1.payment + work2.payment
        val originalTotalPaymentOfCurrentYear = viewModel.paymentOfCurrentYear.getOrAwaitValueForTest()

        assertThat(originalTotalPaymentOfCurrentYear).isEqualTo(expectedTotalPaymentOfCurrentYear)
    }


}