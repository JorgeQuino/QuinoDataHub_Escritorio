package INTERFAZ;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CrearTablas {
    private String selectedDatabase;
    private ArrayList<ColumnDetailsPanel> columnPanels;
    private JTextField tableNameField;
    private JTextField columnCountField;
    private JTextField[] txtColumnNames;
    private int columnCount;

    public CrearTablas(String selectedDatabase) {
        this.selectedDatabase = selectedDatabase;
        columnPanels = new ArrayList<>();
    }

    public void crearTablas() {
        JFrame frame = new JFrame("Crear Tabla en la Base de Datos " + selectedDatabase);

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setForeground(Color.WHITE);
        frame.setBackground(new Color(0, 102, 204));
        frame.setSize(new Dimension(400, 300));
        frame.setLocation(574, 73);
        frame.setUndecorated(true);

        // Crear un panel de encabezado
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(55, 71, 79));

        JLabel headerLabel = new JLabel("<html><h1 style='font-family: Arial, sans-serif; color: #FFFFFF;'>Creación de Tablas</h1></html>");
        headerPanel.add(headerLabel, BorderLayout.WEST);

        // Agregar un botón de cerrar al estilo de macOS con HTML y CSS
        JButton closeButton = new JButton("<html><span style='font-size: 20px;'>&#10005;</span></html>");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 18));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(55, 71, 79));
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                      frame.dispose(); // Cierra la ventana actual, no todo el programa
                 



            }
        });
        headerPanel.add(closeButton, BorderLayout.EAST);

        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        tableNameField = new JTextField(30);
        tableNameField.setFont(fieldFont);
        tableNameField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

        columnCountField = new JTextField(5);
        columnCountField.setFont(fieldFont);
        columnCountField.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

        JButton createTableButton = new JButton("Crear Tabla");
        createTableButton.setFont(labelFont);
        createTableButton.setForeground(Color.WHITE);
        createTableButton.setBackground(new Color(0, 102, 204));

        createTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                columnCount = Integer.parseInt(columnCountField.getText());
                openColumnDetailsForm(columnCount);
            }
        });

        mainPanel.add(new JLabel("Nombre de la tabla:"), gbc);
        mainPanel.add(tableNameField, gbc);
        mainPanel.add(new JLabel("Número de columnas:"), gbc);
        mainPanel.add(columnCountField, gbc);
        mainPanel.add(createTableButton, gbc);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    private void openColumnDetailsForm(int columnCount) {
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font fieldFont = new Font("Arial", Font.PLAIN, 14);

        JFrame columnFrame = new JFrame("Definir Columnas");
        JPanel columnPanel = new JPanel(new GridBagLayout());
        
        JButton closeButton = new JButton("Cerrar");
        closeButton.setFont(labelFont);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(204, 0, 0)); // Rojo oscuro

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Agrega aquí el código que deseas ejecutar al hacer clic en el botón de cerrar
                columnFrame.dispose(); // Cierra la ventana
            }
        });

        // Configuración del GridBagConstraints para el botón de cerrar
        GridBagConstraints closeButtonConstraints = new GridBagConstraints();
        closeButtonConstraints.gridx = 14; // Coloca el botón en la primera columna
        closeButtonConstraints.gridy = 0; // Coloca el botón debajo de las filas de columnas
        closeButtonConstraints.gridwidth = 4; // Ancho del botón, ajusta según sea necesario
       // closeButtonConstraints.anchor = GridBagConstraints.CENTER; // Centra el botón

        // Añade el botón de cerrar al panel con las restricciones configuradas
        columnPanel.add(closeButton, closeButtonConstraints);


        columnFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        columnFrame.setSize(new Dimension(800, 1000));
        columnFrame.setLocation(968, 73);
        columnFrame.setUndecorated(true);
       // columnFrame.setLocationRelativeTo(null);

        columnPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        txtColumnNames = new JTextField[columnCount];

        
        for (int i = 0; i < columnCount; i++) {
            ColumnDetailsPanel columnDetails = new ColumnDetailsPanel(i);
            columnPanels.add(columnDetails);
            

            JLabel lblColumnName = new JLabel("<html><div style='font-size: 14px; color: #0076C0;'>&#128196;</div> Nombre Columna " + (i + 1) + "</html>");
            lblColumnName.setFont(labelFont);

            txtColumnNames[i] = new JTextField();
            txtColumnNames[i].setPreferredSize(new Dimension(200, 25));
            txtColumnNames[i].setFont(fieldFont);
            txtColumnNames[i].setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));

            JLabel lblType = new JLabel("<html><div style='font-size: 14px; color: #0076C0;'>&#128196;</div> Tipo de Datos</html>");
            lblType.setFont(labelFont);
            JComboBox<String> columnTypeField = new JComboBox<>(new String[]{"INT", "VARCHAR(255)", "DATE", "DECIMAL"});
            columnTypeField.setPreferredSize(new Dimension(200, 25));

            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.anchor = GridBagConstraints.WEST;
            columnPanel.add(lblColumnName, gbc);

            gbc.gridx = 1;
            columnPanel.add(txtColumnNames[i], gbc);

            gbc.gridx = 2;
            columnPanel.add(lblType, gbc);

            gbc.gridx = 3;
            columnPanel.add(columnTypeField, gbc);
        }

        JButton saveColumnsButton = new JButton("CREAR TABLA");
        saveColumnsButton.setFont(labelFont);
        saveColumnsButton.setForeground(Color.WHITE);
        saveColumnsButton.setBackground(new Color(0, 153, 51));
        

        columnFrame.getContentPane().add(columnPanel, BorderLayout.CENTER);
        columnFrame.getContentPane().add(saveColumnsButton, BorderLayout.SOUTH);
        columnFrame.setVisible(true);

        saveColumnsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String tableName = tableNameField.getText();
                    String url = "jdbc:mysql://localhost:3306/";
                    String user = "root";
                    String password = "";

                    Connection connection = DriverManager.getConnection(url + selectedDatabase, user, password);
                    StringBuilder createTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + tableName +  " (");

                    for (int i = 0; i < columnCount; i++) {
                        String columnName = txtColumnNames[i].getText();
                        String columnType = columnPanels.get(i).getColumnType();
                        createTableSQL.append(columnName).append(" ").append(columnType);
                        if (i < columnCount - 1) {
                            createTableSQL.append(", ");
                        }
                    }

                    createTableSQL.append(");");
                    System.out.println("SQL Query: " + createTableSQL.toString());

                    Statement statement = connection.createStatement();
                    statement.executeUpdate(createTableSQL.toString());

                    JOptionPane.showMessageDialog(null, "Tabla creada exitosamente.");

                    connection.close();
                    columnFrame.dispose(); // Cierra el diálogo de columnas
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al crear la tabla: " + ex.getMessage());
                }
            }
        });
    }
   
}



