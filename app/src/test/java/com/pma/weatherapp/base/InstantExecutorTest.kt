package com.pma.weatherapp.base
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class InstantExecutorTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}