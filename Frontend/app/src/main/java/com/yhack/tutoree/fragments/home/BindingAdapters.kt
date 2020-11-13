package com.yhack.tutoree.fragments.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yhack.tutoree.database.model.Person
import com.yhack.tutoree.database.model.Student
import com.yhack.tutoree.database.model.Tutor

@BindingAdapter("populateDescription1")
fun TextView.populateDescription1(person: Person) {
    // PSLE [OR] Full Time
    when (person) {
        is Tutor -> {
            this.text = if (person.isFulltime) "Full-time tutor" else "Part-time tutor"
        }
        is Student -> {
            this.text = person.academics
        }
    }
}

@BindingAdapter("populateDescription2")
fun TextView.populateDescription2(person: Person) {
    // 4.5 (135 reviews) [OR] Favourited you
    when (person) {
        is Tutor -> {
            this.text = "0 stars (1M reviews)"
        }
        is Student -> {
            this.text = "Favourited you?"
        }
    }
}