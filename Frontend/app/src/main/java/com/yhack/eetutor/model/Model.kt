package com.yhack.eetutor.model

import android.content.res.AssetManager
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

open class Model(
    assets: AssetManager,
    modelFilename: String,
) {
//    val modelFilename: String = "gradeModel.tflite"
    val interpreter : Interpreter
    open val inputSize = 0
    open val outputSize = 0

    init {
        val model = loadModelFile(assets, modelFilename)
        interpreter = Interpreter(model)

    }

    fun predict (input : FloatArray): FloatArray {
        if(input.size != inputSize) {
            throw Exception("Input size is not correct lmao")
        }
        val inputs : Array<FloatArray> = arrayOf( input.map{ it }.toFloatArray() )
        val outputs : Array<FloatArray> = arrayOf( FloatArray(outputSize))
        interpreter.run(inputs, outputs)
        return outputs[0]
    }

    private fun loadModelFile(
        assets: AssetManager,
        modelFilename: String
    ): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelFilename)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
}