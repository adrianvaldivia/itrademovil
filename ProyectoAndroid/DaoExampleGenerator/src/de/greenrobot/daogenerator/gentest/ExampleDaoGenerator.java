/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(3, "com.tekton.model");

		addElementoLista(schema);
		addAlumno(schema);
		addPregunta(schema);
		addPreguntaxAlumno(schema);

		new DaoGenerator().generateAll(schema, "../Tekton/src-gen");
	}

	private static void addElementoLista(Schema schema) {
		Entity elemento = schema.addEntity("ElementoLista");
		elemento.addIdProperty();
		elemento.addStringProperty("Principal").notNull();
		elemento.addStringProperty("Secundario");
		elemento.addStringProperty("Terciario");
		elemento.addLongProperty("IdElemento");
	}

	private static void addAlumno(Schema schema) {
		Entity alumno = schema.addEntity("Alumno");
		alumno.addIdProperty();
		alumno.addLongProperty("IdAlumno");
		alumno.addStringProperty("Nombres");
		alumno.addStringProperty("ApePaterno");
		alumno.addStringProperty("ApeMaterno");	
	}
	private static void addPregunta(Schema schema) {
		Entity pregunta = schema.addEntity("Pregunta");
		pregunta.addIdProperty();
		pregunta.addLongProperty("IdPregunta");	
		pregunta.addStringProperty("Formula");				
	}

	private static void addPreguntaxAlumno(Schema schema) {
		Entity preguntaxalumno = schema.addEntity("PreguntaxAlumno");
		preguntaxalumno.addIdProperty();
		preguntaxalumno.addLongProperty("IdAlumno");
		preguntaxalumno.addLongProperty("IdPregunta");
		preguntaxalumno.addIntProperty("Activo");		
	}

}
