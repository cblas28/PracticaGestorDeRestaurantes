package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import controlador.Coordinador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JPasswordField;

public class Login {

	private JFrame frame;
	private JTextField ponerCorreo;
	private Coordinador coordinador;
	private Scanner entrada = new Scanner(System.in);
	private int codRes=0;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Login");

		ponerCorreo = new JTextField();
		ponerCorreo.setText("");
		ponerCorreo.setBounds(177, 78, 192, 30);
		frame.getContentPane().add(ponerCorreo);
		ponerCorreo.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(177, 143, 192, 30);
		frame.getContentPane().add(passwordField);

		JLabel lblNewLabel = new JLabel("CORREO");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(63, 75, 96, 30);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblContrasea = new JLabel("CLAVE");
		lblContrasea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContrasea.setBounds(63, 140, 96, 30);
		frame.getContentPane().add(lblContrasea);

		JButton btnNewButton = new JButton("ENTRAR");

		btnNewButton.setBounds(222, 210, 108, 30);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean haEntrado = false;
					Connection miConexion = DriverManager
							.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes", "root", "root");
					Statement miStatement = miConexion.createStatement();
					String correo = ponerCorreo.getText();
					String clave = passwordField.getText();
					String consulta = "select correo, clave, codRes from vistaListaRestaurantes;";
					ResultSet miResultset = miStatement.executeQuery(consulta);

					while (miResultset.next()) {
						if (miResultset.getString("correo").equals(correo)
								&& miResultset.getString("clave").equals(clave)) {
							codRes=miResultset.getInt(3);
							System.out.println(codRes);
							System.out.println("Has entrado");
							haEntrado = true;
							frame.dispose();
							coordinador.mostrarVentanaListaDeCategorias();

						}

					}
					if (correo.equals("root") && clave.equals("root")) {
						haEntrado = true;
						System.out.println("Ahora en consola");
						consolaAdminstrador();
					}
					if (!haEntrado) {
						JOptionPane.showMessageDialog(null, "El usuario y la contraseña no coinciden");
					}

				} catch (SQLException e1) {
					System.out.println(e1.getMessage());

				} catch (Exception e1) {
					e1.getMessage();
				}
			}
		});
		frame.getContentPane().add(btnNewButton);

	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
	
	public int getCodRestaurante() {
		return codRes;
	}

	public void consolaAdminstrador() {

		int opcion = 0;
		do {
			System.out.println("Estas en la aplicaión del administrador");
			System.out.println("¿Qué quieres hacer?");
			System.out.println("1) Insertar nuevas categorías");
			System.out.println("2) Insertar nuevo producto");
			if (entrada.hasNextInt()) {
				opcion = entrada.nextInt();
				entrada.nextLine();
				switch (opcion) {
				case 1:
					insertarNuevaCategoria();
				case 2:
					insertarNuevoProducto();
				}

			} else {
				System.out.println("Tiene que ser un número");
				entrada.nextLine();
			}
		} while (opcion<=1 || opcion>=2);
		
	}

	private void insertarNuevaCategoria() {
		Connection miConexion = null;
		try {
			miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes", "root",
					"root");
			miConexion.setAutoCommit(false);
			Statement miStatement = miConexion.createStatement();
			System.out.println("Introduce el nuevo nombre de la categoría");
			String nuevaCategoria = entrada.nextLine();
			System.out.println("Introduce la descripción de la nueva categoría");
			String descripcionNuevaCategoria = entrada.nextLine();
			String consulta = "insert into categoria (nombre,descripcion) " + "values('" + nuevaCategoria + "', '"
					+ descripcionNuevaCategoria + "');";
			miStatement.executeUpdate(consulta);
			miConexion.commit();
			System.out.println("Ha sido actualizada correctamente");
		} catch (SQLException e1) {
			try {
				miConexion.rollback();
			} catch (SQLException e2) {
				e1.printStackTrace();
			}

		} finally {
			try {
				miConexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void insertarNuevoProducto() {
		Connection miConexion = null;
		int nuevoCodCategoria = 0, nuevoStock = 0;
		double pesoNuevoProducto = 0;
		try {
			miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes", "root",
					"root");
			miConexion.setAutoCommit(false);
			Statement miStatement = miConexion.createStatement();
			do {
				System.out.println("Introduce el código de categoría del nuevo producto:");
				nuevoCodCategoria = entrada.nextInt();
			} while (nuevoCodCategoria <= 0 || nuevoCodCategoria >= 5);
			entrada.nextLine();
			System.out.println("Introduce el nuevo nombre del producto:");
			String nombreNuevoProducto = entrada.nextLine();
			System.out.println("Introduce la descripción de la nueva categoría");
			String descripcionNuevoProducto = entrada.nextLine();
			do {
				System.out.println("Introduce el peso del nuevo producto:");
				pesoNuevoProducto = entrada.nextDouble();
			} while (pesoNuevoProducto <= 0);

			do {
				System.out.println("Introduce el stock del nuevo producto:");
				nuevoStock = entrada.nextInt();
			} while (nuevoStock <= 0);

			String consulta = "insert into productos (codCategoria, nombre ,descripcion, peso, stock) " + "values("
					+ nuevoCodCategoria + ",'" + nombreNuevoProducto + "', '" + descripcionNuevoProducto + "', "
							+ ""+pesoNuevoProducto+", "+nuevoStock+");";
			miStatement.executeUpdate(consulta);
			miConexion.commit();
			System.out.println("Ha sido añadido correctamente");
		} catch (SQLException e1) {
			try {
				miConexion.rollback();
			} catch (SQLException e2) {
				e1.printStackTrace();
			}

		} finally {
			try {
				miConexion.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
