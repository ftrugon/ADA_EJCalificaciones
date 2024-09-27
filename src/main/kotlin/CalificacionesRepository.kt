package org.example

import java.nio.file.Files
import java.nio.file.Path

class CalificacionesRepository() {

/*
Una función que reciba el fichero de calificaciones y devuelva una lista de diccionarios,
donde cada diccionario contiene la información de los exámenes y la asistencia de un alumno.
La lista tiene que estar ordenada por apellidos.
 */
    fun csvToMapOfMaps(csvPath: Path): MutableMap<String, MutableMap<String, String>>{

        val primeMap = mutableMapOf<String, MutableMap<String, String>>()

        val br = Files.newBufferedReader(csvPath)

        val listOfNames = br.readLine().split(";").toMutableList()
        listOfNames.removeFirst()

        br.forEachLine { line ->
            if (line.isNotBlank()){
                val splitedLine = line.split(";")
                primeMap[splitedLine[0]] = listOfNames.zip(splitedLine.drop(1)).toMap().toMutableMap()



                /*
                 mapOf<String, String>(

                 Pair(listOfNames[0],splitedLine[1]),
                 Pair(listOfNames[1],splitedLine[2]),
                 Pair(listOfNames[2],splitedLine[3]),
                 Pair(listOfNames[3],splitedLine[4]),
                 Pair(listOfNames[4],splitedLine[5]),
                 Pair(listOfNames[5],splitedLine[6]),
                 Pair(listOfNames[6],splitedLine[7]),
                 Pair(listOfNames[7],splitedLine[8]))
                 */


            }
        }

        return primeMap
    }


/*
Una función que reciba una lista de diccionarios como la que devuelve la función anterior
y añada a cada diccionario un nuevo par con la nota final del curso. El peso de cada parcial
de teoría en la nota final es de un 30% mientras que el peso del examen de prácticas es de un 40%.

     */
    fun addFinalNoteToMapOfMaps(mapOfAlumns: MutableMap<String, MutableMap<String, String>>){

        mapOfAlumns.forEach { lastName, mapOfThings ->

            var notaFinal = 0f

            val p1 = mapOfThings["Parcial1"]?.replace(",",".")?.toFloatOrNull() ?: 0f
            val p2 = mapOfThings["Parcial2"]?.replace(",",".")?.toFloatOrNull() ?: 0f
            val o1 = mapOfThings["Ordinario1"]?.replace(",",".")?.toFloatOrNull() ?: 0f
            val o2 = mapOfThings["Ordinario2"]?.replace(",",".")?.toFloatOrNull() ?: 0f
            val pr = mapOfThings["Practicas"]?.replace(",",".")?.toFloatOrNull() ?: 0f
            val opr = mapOfThings["OrdinarioPracticas"]?.replace(",",".")?.toFloatOrNull() ?: 0f

            notaFinal += if (p1 > o1) multiply(p1,0.3f) else multiply(o1,0.3f)

            notaFinal += if (p2 > o2) multiply(p2,0.3f) else multiply(o2,0.3f)

            notaFinal += if (pr > opr) multiply(pr,0.4f) else multiply(opr,0.4f)

            mapOfThings["NotaFinal"] = String.format("%.2f", notaFinal)

        }
    }

    fun multiply(numToMul: Float,noteCount: Float): Float{
        return numToMul * noteCount
    }

/*
Una función que reciba una lista de diccionarios como la que devuelve la función anterior y
devuelva dos listas, una con los alumnos aprobados y otra con los alumnos suspensos. Para aprobar
el curso, la asistencia tiene que ser mayor o igual que el 75%, la nota de los exámenes parciales y
de prácticas mayor o igual que 4 y la nota final mayor o igual que 5.
 */
    fun listsOfFailsAndApproved(mapOfAlumns: MutableMap<String, MutableMap<String, String>>): Pair<List<String>, List<String>>{
        val approves = mutableListOf<String>()
        val fails = mutableListOf<String>()

        mapOfAlumns.forEach{lastName, mapOfThings ->

            val finalGrade = mapOfThings["NotaFinal"]?.replace(",",".")?.toFloat() ?: 0f

            val assistance = mapOfThings["Asistencia"]?.split("%")[0]?.toInt() ?: 0

            if (finalGrade >= 5f && assistance >= 75){
                approves.add("($lastName, ${mapOfThings["Nombre"]})")
            }else{
                fails.add("($lastName, ${mapOfThings["Nombre"]})")
            }
        }

        return Pair(approves,fails)
    }

}