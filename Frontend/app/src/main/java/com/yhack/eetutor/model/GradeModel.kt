package com.yhack.eetutor.model

import android.content.res.AssetManager

class GradeModel(assetManager: AssetManager) : Model(assetManager, "gradeModel.tflite") {
    override val inputSize: Int = 18
    override val outputSize: Int = 8

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