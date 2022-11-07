package controlador;

import java.awt.Point;

import vista.Carrito;
import vista.ListaDeCategorias;
import vista.Login;
import vista.ProductosCategoria;
import vista.Salir;

public class Principal {

	public static void main(String[] args) {
		// se instancian las clases
		Coordinador coordinador = new Coordinador();
		Login login = new Login();
		ListaDeCategorias listaDeCategorias = new ListaDeCategorias();
		ProductosCategoria productosCategoria = new ProductosCategoria();
		Carrito carrito = new Carrito();
		Salir salir = new Salir();

		// se establecen la relacion entre clases
		login.setCoordinador(coordinador);
		listaDeCategorias.setCoordinador(coordinador);
		productosCategoria.setCoordinador(coordinador);
		carrito.setCoordinador(coordinador);
		salir.setCoordinador(coordinador);
		
		// se establecen relaciones con la clase coordinador
		coordinador.setLogin(login);
		coordinador.setVentanaListaDeCategorias(listaDeCategorias);
		coordinador.setProductosCategoria(productosCategoria);
		coordinador.setVentanaCarrito(carrito);
		coordinador.setVentanaSalir(salir);

		// iniciarLaAPP
		coordinador.mostrarVentanaLogin();
	}
}
