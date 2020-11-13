package com.yhack.tutoree.model

import android.content.res.AssetManager
import com.yhack.tutoree.database.model.Academics
import com.yhack.tutoree.database.model.Person
import java.util.ArrayList

class GradeModel(assetManager: AssetManager) : Model(assetManager, "gradeModel.tflite") {
    override val inputSize: Int = 18
    override val outputSize: Int = 8

    fun predict(person : Person) {
        val academics : Academics = person.academicsObj
        val gradesList : MutableMap<String, ArrayList<Int>>? = academics.grades
        val activities : ArrayList<String> = academics.activities
        val inputs : FloatArray = FloatArray(inputSize)

        if (gradesList != null) {
            var ctr = 0
            for((key, value) in gradesList) {
                /**
                 * can scale grades using key, i.e. scale O-level grades down
                 */
                for(i in 0..7) {
                    inputs[8 * ctr + i] = value[i].toFloat()
                }
            }
        }

    }
/*
predict(input) takes a FloatArray input

Input Format:
Bio Phys Chem Math Econs Hist Geog Lit Bio Phys Chem Math Econs Hist Geog Lit Act_S Act_H

Two sets of subject grades, scores from 0 to 100
Act_S: science activities, score from 0 to 10 (10 - many sciency activities)
Act_H: humanities activities, score from 0 to 10 (10 - many humans activities)

Output Format:
Bio Phys Chem Math Econs Hist Geog Lit

All are values from 0 to 1, indicating how good a person is at each subject
 */
}