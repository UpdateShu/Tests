package com.geekbrains.tests

import com.geekbrains.tests.presenter.details.DetailsPresenter
import com.geekbrains.tests.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {
    @Mock
    private lateinit var presenter: DetailsPresenter

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    private var countMock: Int = 10

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun setCounter_Test() {
        presenter.setCounter(countMock)
        verify(presenter).setCounter(ArgumentMatchers.eq(10))
    }

    @Test
    fun onIncrement_Test() {
        viewContract.setCount(++countMock)
        verify(viewContract).setCount(ArgumentMatchers.eq(11))

    }

    @Test
    fun onDecrement_Test() {
        viewContract.setCount(--countMock)
        verify(viewContract).setCount(ArgumentMatchers.eq(9))
    }

    @Test
    fun onAttach_Test() {
        presenter.onAttach(viewContract)
        val instance = presenter.javaClass
        instance.declaredFields.forEach {
            it.isAccessible = true
            if (it.name == "view") {
                Assert.assertEquals(viewContract, it.get(presenter))
            }
        }
    }

    @Test
    fun onDetach_Test(){
        presenter.onDetach()
        val instance = presenter.javaClass
        instance.declaredFields.forEach {
            it.isAccessible = true
            if (it.name == "view") {
                Assert.assertNull(it.get(presenter))
            }
        }
    }
}