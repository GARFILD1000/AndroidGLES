package com.example.phonglighting

import android.opengl.GLES20
import android.opengl.GLES20.*

class Model(val modelData: ObjectData): Object() {
    override var data: ObjectData = modelData

    private val vertexBuffer = BufferHelper.directFloat(*data.vertices)
    private val normalBuffer = BufferHelper.directFloat(*data.normals)
    private val indexBuffer = BufferHelper.directShort(*data.indices)
    private val textureBuffer = BufferHelper.directFloat(*data.texCoords)

    override fun draw() {
        shader?.apply {
            use()
            setTexture("texture", 0, texture)
            setVertexAttribute("position", 3, GL_FLOAT, false, 0, vertexBuffer)
            setVertexAttribute("normal", 3, GL_FLOAT, true, 0, normalBuffer)
            setVertexAttribute("texcoord", 2, GL_FLOAT, false, 0, textureBuffer)
        }
        glDrawElements(GL_TRIANGLES, data.indices.size, GL_UNSIGNED_SHORT, indexBuffer)

    }
}