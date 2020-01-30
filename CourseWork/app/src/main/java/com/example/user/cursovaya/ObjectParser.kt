package com.example.user.cursovaya
import android.content.Context
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class ObjectParser {
    var facesCount = 0

    var normals: FloatArray = FloatArray(0)
    var texcoords: FloatArray = FloatArray(0)
    var positions: FloatArray = FloatArray(0)

    constructor(context: Context, rawId: Int) {
        val verticesList = Vector<Float>()
        val normalsList =  Vector<Float>()
        val texturesList = Vector<Float>()
        val facePointsList = Vector<String>()

        try{
            val stream = context.resources.openRawResource(rawId)
            val inputStream = InputStreamReader(stream)
            val reader = Scanner(inputStream)
            // read file until EOF
            while (reader.hasNextLine()) {
                val line = reader.nextLine()
                val parts = line.split(" ")
                when (parts[0]) {
                    "v" -> {
                        // vertices
                        verticesList.add(parts[1].toFloat())
                        verticesList.add(parts[2].toFloat())
                        verticesList.add(parts[3].toFloat())
                    }
                    "vt" -> {
                        // textures
                        texturesList.add(parts[1].toFloat())
                        texturesList.add(parts[2].toFloat())
                    }
                    "vn" -> {
                        // normals
                        normalsList.add(parts[1].toFloat())
                        normalsList.add(parts[2].toFloat())
                        normalsList.add(parts[3].toFloat())
                    }
                    "f" -> {
                        // faces: vertex/texture/normal
                        if (parts.size == 4) {
                            facePointsList.add(parts[1])
                            facePointsList.add(parts[2])
                            facePointsList.add(parts[3])
                        } else if (parts.size == 5) {
                            facePointsList.add(parts[1])
                            facePointsList.add(parts[2])
                            facePointsList.add(parts[3])
                            facePointsList.add(parts[1])
                            facePointsList.add(parts[3])
                            facePointsList.add(parts[4])
                        }
                    }
                }
            }
        } catch (e: IOException) {
            throw Error(e)
        }

        facesCount = facePointsList.size
        normals = FloatArray(facesCount * 3)
        positions = FloatArray(facesCount * 3)
        texcoords = FloatArray(facesCount * 2)
        var positionIndex = 0
        var normalIndex = 0
        var textureIndex = 0
        for (facePoint in facePointsList) {
            val parts = facePoint.split("/")
            var index = 3 * (parts[0].toInt() - 1)
            positions[positionIndex++] = verticesList[index++]
            positions[positionIndex++] = verticesList[index++]
            positions[positionIndex++] = verticesList[index]

            index = 2 * (parts[1].toInt() - 1)
            texcoords[normalIndex++] = texturesList[index++]
            texcoords[normalIndex++] = 1 - texturesList[index]

            index = 3 * (parts[2].toInt() - 1)
            normals[textureIndex++] = normalsList[index++]
            normals[textureIndex++] = normalsList[index++]
            normals[textureIndex++] = normalsList[index]
        }
    }

    fun free(){
        texcoords = FloatArray(0)
        normals = FloatArray(0)
        positions = FloatArray(0)
        facesCount = 0
    }
}
