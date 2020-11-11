package com.yhack.eetutor.model

import android.content.res.AssetManager

class CompatibilityModel(assetManager: AssetManager) : Model(assetManager, "compatModel.tflite") {
    override val inputSize: Int = 16
    override val outputSize: Int = 8

    /*
predict(input) takes 2 FloatArray inputs, one from the tutor, one from the tutee (order matters!)

Input Format:
Bio Phys Chem Math Econs Hist Geog Lit MBTI MBTI MBTI MBTI Int_Music Int_Sport Int_Sci Int_Humans

Subjects: 0 - 1 float values obtained from gradeModel
MBTI: 0 or 1 depending on personality type
Int_Music..Int_Humans: 0 - 10 depending on how interested the person is in music, sports science or humanities activities

Output Format:
Bio Phys Chem Math Econs Hist Geog Lit

All are values from 0 to 1, indicating how compatible 2 people are at a subject
 */


    fun predict (
        tutor : FloatArray,
        tutee : FloatArray,
    ) : FloatArray {
        val input = FloatArray(inputSize)

//        Gets difference between tutor and tutee's grades
        for(i in 0..7) {
            input[i] = tutor[i] - tutee[i]
        }

//        feature cross between tutor and tutee's MBTI
        for(i in 8..11) {
            input[i] = (1 - (tutor[i].toInt()) xor (tutee[i].toInt())).toFloat()
        }

//        feature cross between tutor and tutee's activities
        for(i in 12..15) {
            input[i] = tutor[i] * tutee[i]
        }
        return predict(input)
    }



}