package com.yhack.tutoree.database

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*

class ImportDBFromAssets {
    companion object {
        fun buildDBPath(context: Context) = "${context.filesDir}/databases/phone_internal.db"

        // Source: https://stackoverflow.com/a/53755328/7254995
        /**
         * Gets the .db file from .apk source into the phone's internal app data
         */
        suspend fun importIfNeeded(context: Context) {
            withContext(Dispatchers.IO) {
                var myInput: InputStream? = null
                var myOutput: OutputStream? = null
                try {
                    val outFileName = buildDBPath(context)
                    val f = File(outFileName)
                    if (f.exists()) return@withContext
                    val parentDir = File(f.parent!!)
                    if (!parentDir.exists()) parentDir.mkdirs()

                    myInput = context.assets.open("test.db")
                    myOutput = FileOutputStream(outFileName)

                    //transfer bytes from the inputfile to the outputfile
                    val buffer = ByteArray(1024)
                    var length: Int = myInput.read(buffer)

                    while (length > 0) {
                        myOutput.write(buffer, 0, length)
                        length = myInput.read(buffer)
                    }

                    //Close the streams
                    myOutput.flush()
                    myOutput.close()
                    myInput.close()
                } catch (e: IOException) {
                    Log.d("mememe", "OIII")
                    e.printStackTrace()
                }
            }
        }
    }
}