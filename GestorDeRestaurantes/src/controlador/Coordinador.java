package controlador;

import java.awt.Point;

import javax.swing.text.Segment;

import vista.Carrito;
import vista.ListaDeCategorias;
import vista.Login;
import vista.ProductosCategoria;
import vista.Salir;

public class Coordinador {

	private Login login;
	private ListaDeCategorias listaDeCategorias;
	private ProductosCategoria productosCategoria;
	private Carrito carrito;
	private Salir salir;

	// login
	public void mostrarVentanaLogin() {
		login.setVisible(true);
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public Login getLogin() {
		return login;
	}
	// ventanaListadeCategorias
	public void mostrarVentanaListaDeCategorias() {
		listaDeCategorias.setVisible(true);
	}

	public void setVentanaListaDeCategorias(ListaDeCategorias listaDeCategorias) {
		this.listaDeCategorias = listaDeCategorias;
	}

	public ListaDeCategorias getListaCategorias() {
		return listaDeCategorias;
	}

	// ventanaProductosCategorias
	public void mostrarProductosCategoria() {
		productosCategoria.setVisible(true);
	}

	public void setProductosCategoria(ProductosCategoria productosCategoria) {
		this.productosCategoria = productosCategoria;
	}

	public ProductosCategoria getProductosCategoria() {
		return productosCategoria;
	}
	// carrito

	public void mostrarCarrito() {
		carrito.setVisible(true);
	}

	public void setVentanaCarrito(Carrito carrito) {
		this.carrito = carrito;
	}

	// salir
	public void mostrarSalir() {
		salir.setVisible(true);
	}

	public void setVentanaSalir(Salir salir) {
		this.salir = salir;
	}


}
