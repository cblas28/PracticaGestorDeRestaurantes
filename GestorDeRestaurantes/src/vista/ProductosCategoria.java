package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import controlador.Coordinador;

public class ProductosCategoria {

	// private static final ActionEvent ActionEvent = null;
	private JFrame frame;
	private Coordinador coordinador;
	private JList list;
	private JScrollPane scrollPane;
	private JLabel cantidadProducto, ponerPlatoElegido, anadido;
	private String platoElegido;
	private int cantidad = 0;
	private String cadenaPedido = "";
	public static  ArrayList<String> arrayListPedido = new ArrayList<>();
	public static Boolean valido;
	public static HashMap<String, Integer> pedidos;
	public static Set<String> claves;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductosCategoria window = new ProductosCategoria();
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
	public ProductosCategoria() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 482);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Productos categoria");

		JButton btnNewButton_4 = new JButton("Categorias");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				coordinador.mostrarVentanaListaDeCategorias();
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4.setBounds(139, 9, 103, 21);
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_4_1 = new JButton("Carrito");
		btnNewButton_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				coordinador.mostrarCarrito();
			}
		});
		btnNewButton_4_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4_1.setBounds(252, 9, 85, 21);
		frame.getContentPane().add(btnNewButton_4_1);

		JButton btnNewButton_4_2 = new JButton("Salir");
		btnNewButton_4_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador.mostrarSalir();
			}
		});
		btnNewButton_4_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4_2.setBounds(341, 10, 85, 21);
		frame.getContentPane().add(btnNewButton_4_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 101, 253, 152);
		frame.getContentPane().add(scrollPane);

		cantidadProducto = new JLabel("Opción elegida");
		cantidadProducto.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cantidadProducto.setBounds(194, 317, 30, 37);
		frame.getContentPane().add(cantidadProducto);
		cantidadProducto.setText("" + cantidad);

		JLabel lblNewLabel = new JLabel("Estos son nuestros productos:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(89, 40, 296, 72);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("+");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidad++;
				cantidadProducto.setText("" + cantidad);
				platoElegido = (String) list.getSelectedValue();
				ponerPlatoElegido.setText(platoElegido);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton.setBounds(232, 315, 53, 37);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("-");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cantidad > 0)
					cantidad--;
				cantidadProducto.setText("" + cantidad);
				platoElegido = (String) list.getSelectedValue();
				ponerPlatoElegido.setText(platoElegido);
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 26));
		btnNewButton_1.setBounds(111, 315, 53, 37);
		frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("AÑADIR AL CARRITO");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidadProducto.setText("0");
				platoElegido = ponerPlatoElegido.getText();

				Connection miConexion = null;
				pedidos = new HashMap<String, Integer>();
				String pedidoString = "";
				valido = false;
				int numero = 0;
				try {
					miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes", "root",
							"root");
					Statement miStatement = miConexion.createStatement();
					String parametroString = "productos";
					String intruString = "select * from ";
					ResultSet miResultSet = miStatement.executeQuery(intruString + parametroString);

					while (miResultSet.next()) {
						if (platoElegido.equals(miResultSet.getString("nombre"))) {
							pedidoString = miResultSet.getString("nombre");
							numero = miResultSet.getInt("stock");

							if (numero >= cantidad) {
								pedidos.put(pedidoString, cantidad);
								valido = true;
							}

						}
					}
					if (valido == true) {
						String consultaString = "UPDATE  productos SET  stock= '" + (numero - cantidad) + "'"
								+ "WHERE nombre='" + platoElegido + "'";
						miStatement.executeUpdate(consultaString);
						cadenaPedido = platoElegido + "=" + cantidad;
						arrayListPedido.add(cadenaPedido);
						cadenaPedido = "";
					}

				} catch (SQLException e3) {
					e3.printStackTrace();
					try {
						miConexion.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
						try {
							miConexion.close();
						} catch (SQLException e2) {

							e2.printStackTrace();
						}

					}

				}
				cantidad = 0;
				ponerPlatoElegido.setText("Opción seleccionada");
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.setBounds(111, 376, 174, 59);
		frame.getContentPane().add(btnNewButton_2);

		ponerPlatoElegido = new JLabel("Opción seleccionada");
		ponerPlatoElegido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ponerPlatoElegido.setBounds(84, 270, 274, 37);
		frame.getContentPane().add(ponerPlatoElegido);

		anadido = new JLabel("");
		anadido.setFont(new Font("Tahoma", Font.PLAIN, 30));
		anadido.setBounds(295, 376, 131, 59);
		frame.getContentPane().add(anadido);

	}

	public void crearLista() {

		list = new JList();
		list.setToolTipText("");
		DefaultListModel lista = new DefaultListModel();

		try {
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes",
					"root", "root");
			Statement miStatement = miConexion.createStatement();

			// System.out.println("pepe");
			String consulta = "select nombre from vistaProductos where codCategoria="
					+ coordinador.getListaCategorias().getCodCat() + ";";
			ResultSet miResultset = miStatement.executeQuery(consulta);
			while (miResultset.next()) {
				lista.addElement(miResultset.getString(1));

			}

		} catch (SQLException e1) {
			System.out.println(e1.getMessage());

		} catch (Exception e1) {
			e1.getMessage();
		}

		list.setModel(lista);
		scrollPane.setViewportView(list);
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	public int getCantidad() {
		return cantidad;
	}

	public String getPlatoElegido() {
		return platoElegido;
	}

	public static ArrayList<String> mostrarArrayListPedidos() {
		return arrayListPedido;
	}
	
	public static boolean esValido() {
		return valido;
	}
}
