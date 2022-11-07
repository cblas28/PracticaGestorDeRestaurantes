package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;

import controlador.Coordinador;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Carrito {

	private JFrame frame;
	private Coordinador coordinador;
	private Connection miConexion;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Carrito window = new Carrito();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Carrito() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 548);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Carrito");

		JButton btnNewButton_4 = new JButton("Categorias");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				coordinador.mostrarVentanaListaDeCategorias();
			}
		});
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4.setBounds(205, 10, 103, 21);
		frame.getContentPane().add(btnNewButton_4);

		JButton btnNewButton_4_2 = new JButton("Salir");
		btnNewButton_4_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador.mostrarSalir();
			}
		});
		btnNewButton_4_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton_4_2.setBounds(323, 10, 103, 21);
		frame.getContentPane().add(btnNewButton_4_2, BorderLayout.WEST);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(76, 74, 269, 161);
		frame.getContentPane().add(scrollPane);

		JList list = new JList();
		scrollPane.setViewportView(list);

		JButton btnNewButton = new JButton("Ver Carrito");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultListModel lista = new DefaultListModel();
				list.setModel(lista);
				for (String nombre : coordinador.getProductosCategoria().mostrarArrayListPedidos()) {
					lista.addElement(nombre);
				}

			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(144, 264, 121, 37);
		frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("REALIZAR PEDIDO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Connection miConexion = null;
				try {
					miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes", "root",
							"root");
					miConexion.setAutoCommit(false);
					Statement miStatement = miConexion.createStatement();
					Calendar c1 = Calendar.getInstance();
					Calendar c2 = new GregorianCalendar();
					String dia = Integer.toString(c1.get(Calendar.DATE));
					String mes = Integer.toString(c1.get(Calendar.MONTH));
					String annio = Integer.toString(c1.get(Calendar.YEAR));
					String fechaHoy=annio+"-"+mes+"-"+dia;
					String consulta = "insert into pedidos(codRestaurante, fecha, enviado) value"
							+ " ("+coordinador.getLogin().getCodRestaurante()+", '"+fechaHoy+"', false); ";
					miStatement.executeUpdate(consulta);
					miConexion.commit();
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Pedido realizado correctamente, muchas gracias");
				} catch (SQLException e1) {
					try {
						miConexion.rollback();
					} catch (SQLException e2) {
						e2.printStackTrace();
					}

				} finally {
					try {
						miConexion.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnNewButton_1.setBounds(20, 424, 406, 77);
		frame.getContentPane().add(btnNewButton_1);

	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}

	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}
}
