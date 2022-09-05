package com.example.weatherapp.arch

interface UseCase<IN : Any, OUT : Any> {

    suspend fun run(input: IN): OUT
}