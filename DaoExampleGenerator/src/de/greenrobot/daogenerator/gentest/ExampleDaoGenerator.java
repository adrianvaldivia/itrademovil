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
		Schema schema = new Schema(3, "com.itrade.model");

		addCliente(schema);
		addProspecto(schema);
		addUsuario(schema);
		addProducto(schema);
		addPersona(schema);
		addPedido(schema);
		addCategoria(schema);
		addPedidoLinea(schema);
		addElementoLista(schema);
		addEvento(schema);
		addMeta(schema);

		new DaoGenerator().generateAll(schema, "../ITradeMovil/src-gen");
	}

	private static void addElementoLista(Schema schema) {
		Entity elemento = schema.addEntity("ElementoLista");
		elemento.addIdProperty();
		elemento.addStringProperty("Principal").notNull();
		elemento.addStringProperty("Secundario");
		elemento.addStringProperty("Terciario");
		elemento.addLongProperty("IdElemento");
	}

	private static void addEvento(Schema schema) {
		Entity evento = schema.addEntity("Evento");
		evento.addIdProperty();
		evento.addIntProperty("IdEvento");
		evento.addStringProperty("Creador");
		evento.addStringProperty("Asunto");
		evento.addStringProperty("Lugar");
		evento.addStringProperty("Descripcion");
		evento.addStringProperty("Fecha");
		evento.addStringProperty("HoraInicio");
		evento.addStringProperty("HoraFin");
	}
	private static void addMeta(Schema schema) {
		Entity meta = schema.addEntity("Meta");
		meta.addIdProperty();
		meta.addLongProperty("IdMeta");
		meta.addDoubleProperty("Suma");
		meta.addStringProperty("Fechini");
		meta.addStringProperty("Fechafin");
		meta.addDoubleProperty("Meta");
		meta.addStringProperty("Nombre");		
	}

	private static void addCliente(Schema schema) {
		Entity cliente = schema.addEntity("Cliente");
		cliente.addIdProperty();
		cliente.addIntProperty("IdPersona");
		cliente.addIntProperty("IdCliente");
		cliente.addStringProperty("Nombre");
		cliente.addStringProperty("ApePaterno");
		cliente.addStringProperty("ApeMaterno");
		cliente.addStringProperty("Razon_Social").notNull();
		cliente.addStringProperty("RUC");
		cliente.addDoubleProperty("Latitud");
		cliente.addDoubleProperty("Longitud");
		cliente.addStringProperty("Direccion");
		cliente.addIntProperty("IdCobrador");
		cliente.addIntProperty("IdUsuario");
		cliente.addStringProperty("Activo");
	}

	private static void addProspecto(Schema schema) {
		Entity prospecto = schema.addEntity("Prospecto");
		prospecto.addIdProperty();
		prospecto.addIntProperty("IdPersona");
		prospecto.addIntProperty("IdProspecto");
		prospecto.addStringProperty("Nombre");
		prospecto.addStringProperty("ApePaterno");
		prospecto.addStringProperty("ApeMaterno");
		prospecto.addStringProperty("Razon_Social").notNull();
		prospecto.addStringProperty("RUC");
		prospecto.addDoubleProperty("Latitud");
		prospecto.addDoubleProperty("Longitud");
		prospecto.addStringProperty("Direccion");
		prospecto.addIntProperty("IdCobrador");
		prospecto.addIntProperty("IdUsuario");
		prospecto.addStringProperty("Activo");
	}

	private static void addUsuario(Schema schema) {
		Entity usuario = schema.addEntity("Usuario");
		usuario.addIdProperty();
		usuario.addLongProperty("IdUsuario");
		usuario.addStringProperty("Nombre").notNull();
		usuario.addStringProperty("Password");
		usuario.addStringProperty("Username");
		usuario.addStringProperty("NombreReal").notNull();
		usuario.addStringProperty("ApePaterno");
		usuario.addStringProperty("ApeMaterno");
		usuario.addIntProperty("IdPerfil");
		usuario.addIntProperty("IdPersona");
		usuario.addStringProperty("Activo");
		usuario.addIntProperty("IdJerarquia");
		usuario.addIntProperty("IdZona");
		usuario.addIntProperty("IdDistrito");
		usuario.addIntProperty("IdCiudad");
		usuario.addIntProperty("IdPais");
	}

	private static void addProducto(Schema schema) {
		Entity producto = schema.addEntity("Producto");
		producto.addIdProperty();
		producto.addLongProperty("IdProducto");
		producto.addStringProperty("Descripcion").notNull();
		producto.addDoubleProperty("Precio");
		producto.addIntProperty("Stock");
		producto.addStringProperty("Activo");
		producto.addIntProperty("IdCategoria");
		producto.addIntProperty("IdMarca");
	}

	private static void addCategoria(Schema schema) {
		Entity categoria = schema.addEntity("Categoria");
		categoria.addIdProperty();
		categoria.addLongProperty("IdCategoria");
		categoria.addStringProperty("Descripcion").notNull();
	}

	private static void addPersona(Schema schema) {
		Entity persona = schema.addEntity("Persona");
		persona.addIdProperty();
		persona.addLongProperty("IdPersona");
		persona.addStringProperty("Nombre").notNull();
		persona.addStringProperty("ApePaterno");
		persona.addStringProperty("ApeMaterno");
		persona.addStringProperty("DNI");
		persona.addStringProperty("FechNac");
		persona.addStringProperty("Telefono");
		persona.addStringProperty("Email");
		persona.addStringProperty("Activo");
	}

	private static void addPedido(Schema schema) {
		Entity pedido = schema.addEntity("Pedido");
		pedido.addIdProperty();
		pedido.addLongProperty("IdPedido");
		pedido.addIntProperty("IdCliente");
		pedido.addIntProperty("IdEstadoPedido");
		pedido.addIntProperty("CheckIn");
		pedido.addStringProperty("FechaPedido");
		pedido.addStringProperty("FechaCobranza");
		pedido.addDoubleProperty("MontoSinIGV");
		pedido.addDoubleProperty("IGV");
		pedido.addDoubleProperty("MontoTotalPedido");
		pedido.addDoubleProperty("MontoTotalCobrado");
		pedido.addStringProperty("NumVoucher");
		pedido.addDoubleProperty("MontoTotal");
	}

	private static void addPedidoLinea(Schema schema) {
		Entity pedidolinea = schema.addEntity("PedidoLinea");
		pedidolinea.addIdProperty();
		pedidolinea.addLongProperty("IdPedido");
		pedidolinea.addLongProperty("IdPedidoLinea");
		pedidolinea.addIntProperty("IdProducto");
		pedidolinea.addDoubleProperty("MontoLinea");
		pedidolinea.addDoubleProperty("Precio");
		pedidolinea.addIntProperty("Cantidad");
		pedidolinea.addStringProperty("NombreProducto");
		pedidolinea.addStringProperty("Marca");
		pedidolinea.addStringProperty("Categoria");
	}
	// private static void addCustomerOrder(Schema schema) {
	// Entity customer = schema.addEntity("Customer");
	// customer.addIdProperty();
	// customer.addStringProperty("name").notNull();
	//
	// Entity order = schema.addEntity("Order");
	// order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
	// order.addIdProperty();
	// Property orderDate = order.addDateProperty("date").getProperty();
	// Property customerId =
	// order.addLongProperty("customerId").notNull().getProperty();
	// order.addToOne(customer, customerId);
	//
	// ToMany customerToOrders = customer.addToMany(order, customerId);
	// customerToOrders.setName("orders");
	// customerToOrders.orderAsc(orderDate);
	// }

}
