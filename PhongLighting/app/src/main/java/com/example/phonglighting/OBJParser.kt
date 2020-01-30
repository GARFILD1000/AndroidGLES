package com.example.phonglighting

import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

object OBJParser {
    private const val VERTEX = "v"
    private const val NORMAL = "vn"
    private const val TEXTURE = "vt"
    private const val FACE = "f"
    private const val SEPARATOR_LINE = " "
    private const val SEPARATOR_FACE = "/"

    fun parse(filename: String): Object.ObjectData? {
        val file = File(filename)
        if (!file.exists()) return null
        return parse(FileInputStream(file))
    }

    fun parse(inputStream: InputStream): Object.ObjectData {
        val reader = Scanner(inputStream)
        val vertexes = LinkedList<Float>()
        val normals = LinkedList<Float>()
        val textures = LinkedList<Float>()
        val indices = LinkedList<Short>()
        val textIndices = LinkedList<Short>()

        val faces = LinkedList<IntArray>()

        while (reader.hasNextLine()) {
            val lineTokens = reader.nextLine().split(SEPARATOR_LINE)
            if (lineTokens.size == 0) continue
            when(lineTokens[0]) {
                VERTEX -> {
                    for (i in 1 until lineTokens.size) {
                        lineTokens[i].toFloatOrNull()?.let {
                            vertexes.addLast(it)
                        }
                    }
                }
                NORMAL -> {
                    for (i in 1 until lineTokens.size) {
                        lineTokens[i].toFloatOrNull()?.let {
                            normals.addLast(it)
                        }
                    }
                }
                TEXTURE -> {
                    for (i in 1 until lineTokens.size) {
                        lineTokens[i].toFloatOrNull()?.let {
                            textures.addLast(it)
                        }
                    }
                }
                FACE -> {
                    for (i in 1 until lineTokens.size) {
                        val faceTokens = lineTokens[i].split(SEPARATOR_FACE)
//                        for (j in faceTokens.indices){
//                            faceTokens[j].toShortOrNull()?.let {idx ->
//                                indices.add(idx)
//                            }
//                        }
                        faceTokens[0].toShortOrNull()?.let {idx ->
                            indices.add(idx)
                        } ?: indices.add(0)
                        faceTokens[1].toShortOrNull()?.let {idx ->
                            textIndices.add(idx)
                        } ?: textIndices.add(0)
                    }
                }
            }
        }

        val textureCoords = FloatArray(textIndices.size)
        for (i in textureCoords.indices) {
            textureCoords[i] = textures.getOrNull(textIndices[i].toInt()) ?: 0f
        }

        val objectData = Object.ObjectData(
            vertexes.toFloatArray(),
            normals.toFloatArray(),
            indices.toShortArray(),
            textureCoords
        )
        return objectData
    }

}
