package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;

import controlador.Coordinador;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ListaDeCategorias {

	private JFrame frame;
	private Coordinador coordinador;
	private JList list;
	private String opcion;
	private int codCat=0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaDeCategorias window = new ListaDeCategorias();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ListaDeCategorias() {

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 343);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Lista de categorías");

		JButton btnNewButton_4_1 = new JButton("Carrito");
		btnNewButton_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(84, 101, 201, 124);
		frame.getContentPane().add(scrollPane);

		list = new JList();
		scrollPane.setViewportView(list);
		DefaultListModel lista = new DefaultListModel();
		list.setModel(lista);
	

		JLabel lblNewLabel = new JLabel("Estas son nuestras categorías:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(89, 40, 296, 72);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Siguiente");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
				coordinador.getProductosCategoria().crearLista();
				coordinador.mostrarProductosCategoria();
			}
		});
		btnNewButton.setBounds(128, 235, 112, 42);
		frame.getContentPane().add(btnNewButton);
		try {
			Connection miConexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/GestorDeRestaurantes",
					"root", "root");
			Statement miStatement = miConexion.createStatement();
			String consulta = "select nombre from vistaListaCategorias;";
			ResultSet miResultset = miStatement.executeQuery(consulta);
			while (miResultset.next()) {
				lista.addElement(miResultset.getString(1));
				//System.out.println(miResultset.getString(1));
			}
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());

		} catch (Exception e1) {
			e1.getMessage();
		}
	}
	
	public int getCodCat() {
		
		return list.getSelectedIndex()+1;
	}
	
	public void setCoordinador(Coordinador coordinador) {
		this.coordinador = coordinador;
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
	}
}
