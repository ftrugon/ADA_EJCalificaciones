package org.example

import java.nio.file.Path

/*
El fichero calificaciones.csv contiene las calificaciones de un curso.
Durante el curso se realizaron dos exámenes parciales de teoría y un examen de
prácticas. Los alumnos que tuvieron menos de 4 en alguno de estos exámenes
pudieron repetirlo en la al final del curso (convocatoria ordinaria).
Escribir un programa que contenga las siguientes funciones:
 */


fun main() {

    val csvPath = Path.of("C:\\Users\\fran\\IdeaProjects\\ADA_EJCalificaciones\\src\\main\\resources\\calificaciones.csv")

    val cR = CalificacionesRepository()

    val mapOfAlumns = cR.csvToMapOfMaps(csvPath)

    cR.addFinalNoteToMapOfMaps(mapOfAlumns)

    val approvesAndFails = cR.listsOfFailsAndApproved(mapOfAlumns)

    println("Approves")
    approvesAndFails.first.forEach{
        println(it)
    }

    println("Fails")
    approvesAndFails.second.forEach {
        println(it)
    }

}