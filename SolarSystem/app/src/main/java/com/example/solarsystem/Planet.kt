package com.example.solarsystem

import javax.microedition.khronos.opengles.GL10

class Planet(gl: GL10,
             planetRadius: Float,
             val orbitRadius: Float,
             val yearDuration: Float,
             val dayDuration: Float,
             textureName: Int,
             renderer: SolarSystemRenderer) : Celestial(gl, planetRadius, textureName, renderer){

}