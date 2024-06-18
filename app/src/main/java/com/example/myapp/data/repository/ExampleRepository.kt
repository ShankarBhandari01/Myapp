package com.example.myapp.data.repository

import com.example.myapp.data.dao.ExampleDao
import com.example.myapp.data.remote.AppApi
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ExampleRepository @Inject constructor(private val appApi: AppApi, private val exampleDao: ExampleDao)