package com.pma.weatherapp.base.model.weather

data class Alert(
    val sender_name: String,
    val event: String,
    val start: String,
    val end: String,
    val description: String,
    val tags: List<Tag>
)
