import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.fasterxml.jackson.databind.ObjectMapper;

import entidades.Ciudad;
import entidades.Pais;
import entidades.Region;

public class FrmDivisionPolitica extends JFrame {

    private JTree arbol;
    DefaultMutableTreeNode nodoRaiz;
    JLabel lblMapa;

    public FrmDivisionPolitica() {
        setSize(600, 400);
        setTitle("División Política");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tbDivisionPolitica = new JToolBar();

        JButton btnHimno = new JButton();
        btnHimno.setIcon(new ImageIcon(getClass().getResource("/iconos/Himno.png")));
        btnHimno.setToolTipText("Reproducir Himno");
        btnHimno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reproducirHimno();
            }
        });
        tbDivisionPolitica.add(btnHimno);

        // Crear el nodo raíz del árbol
        nodoRaiz = new DefaultMutableTreeNode("Paises");

        // Crear el modelo del árbol
        arbol = new JTree(new DefaultTreeModel(nodoRaiz));
        JScrollPane spArbol = new JScrollPane(arbol);

        // Agregar listener para detectar selección de nodos
        arbol.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                mostrarMapa();
            }
        });

        // Crear
        lblMapa = new JLabel();
        JScrollPane spMapa = new JScrollPane(lblMapa);

        // Divisor entre el árbol y el mapa
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, spArbol, spMapa);
        splitPane.setDividerLocation(250); // Tamaño inicial del árbol

        getContentPane().add(tbDivisionPolitica, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        cargarDatos();
    }

    private List<Pais> paises;

    private void cargarDatos() {
        ObjectMapper objectMapper = new ObjectMapper();
        String nombreArchivo = System.getProperty("user.dir") + "/src/datos/DivisionPolitica.json";

        // obtener los datos desde el archivo yeison
        try {
            paises = objectMapper.readValue(new File(nombreArchivo),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Pais.class));
            if (paises != null) {
                for (Pais pais : paises) {
                    DefaultMutableTreeNode nodoPais = new DefaultMutableTreeNode(pais.getNombre());
                    if (pais.getRegiones() != null) {
                        for (Region region : pais.getRegiones()) {
                            DefaultMutableTreeNode nodoRegion = new DefaultMutableTreeNode(region.getNombre());
                            nodoPais.add(nodoRegion);
                            if (region.getCiudades() != null) {
                                for (Ciudad ciudad : region.getCiudades()) {
                                    DefaultMutableTreeNode nodoCiudad = new DefaultMutableTreeNode(ciudad.getNombre());
                                    nodoRegion.add(nodoCiudad);
                                }

                            }

                        }

                    }
                    nodoRaiz.add(nodoPais);

                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "no se pudieron cargar los datos" + ex);

        }

    }

    private String getNombrePais() {
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();
        while (nodo != null) {
            if (nodo.getParent() == nodoRaiz) {
                return nodo.toString().replace("á", "a")
                        .replace("é", "e")
                        .replace("í", "i")
                        .replace("ó", "o")
                        .replace("ú", "u");
            }
            nodo = (DefaultMutableTreeNode) nodo.getParent();
        }
        return "";

    }

    private void mostrarMapa() {
        String nombrePais = getNombrePais();
        if (!nombrePais.equals("")) {
            String rutaMapa = "src/mapas/" + nombrePais + ".jpg";
            File archivoMapa = new File(rutaMapa);
            if (archivoMapa.exists()) {
                lblMapa.setIcon(new ImageIcon(rutaMapa));
            } else {
                lblMapa.setIcon(null);
                JOptionPane.showMessageDialog(null, "Paila mami, no tenemos mapa de ese hueco");
            }

        }
    }

    private boolean reproduciendo = false;

    private void reproducirHimno() {
        if (reproduciendo) {
            reproduciendo = false;
            ReproductorAudio.detener();

        } else {
            String nombrePais = getNombrePais();
            if (!nombrePais.equals("")) {
                String rutaHimno = "src/himnos/" + nombrePais + ".mp3";
                File archivoHimno = new File(rutaHimno);
                if (archivoHimno.exists()) {
                    ReproductorAudio.reproducir((rutaHimno));
                    reproduciendo = true;
                }else{
                    JOptionPane.showMessageDialog(null, "no tenemos musica peruana");
                }
            }
        }

    }

}
