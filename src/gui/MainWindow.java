package gui;

import controllers.Queries;
import entities.Mr10223Lekar;
import views.Mr10223LekarView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainWindow extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private final Queries queries = new Queries();

    public MainWindow() {
        setTitle("Medical Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Top Panel with Entity Buttons ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnPacijent = new JButton("Pacijent");
        JButton btnLekar = new JButton("Lekar");
        JButton btnDijagnoza = new JButton("Dijagnoza");
        JButton btnPregled = new JButton("Pregled");

        topPanel.add(btnPacijent);
        topPanel.add(btnLekar);
        topPanel.add(btnDijagnoza);
        topPanel.add(btnPregled);

        add(topPanel, BorderLayout.NORTH);

        // --- Table Setup ---
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Buttons (Add/Delete) ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        bottomPanel.add(btnAdd);
        bottomPanel.add(btnDelete);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Button Actions ---
        btnPacijent.addActionListener(e -> loadPacijentData());
        btnLekar.addActionListener(e -> loadLekarData());
        btnDijagnoza.addActionListener(e -> loadDijagnozaData());
        btnPregled.addActionListener(e -> loadPregledData());

        btnAdd.addActionListener(e -> new AddWindow(() -> reloadTable()).setVisible(true));

        // Load initial data
        loadPacijentData();
    }

    private void loadPacijentData() {
        String[] columns = {"Ime", "Prezime", "Datum rođenja", "Osiguran"};

        // Fetch the data from DB, converting entities to views
        List<Object> results = queries.getData(entities.Mr10223Pacijent.class, views.Mr10223PacijentView::new);

        // Build table data dynamically
        Object[][] data = new Object[results.size()][columns.length];

        for (int i = 0; i < results.size(); i++) {
            views.Mr10223PacijentView view = (views.Mr10223PacijentView) results.get(i);
            data[i][0] = view.getIme();
            data[i][1] = view.getPrz();
            data[i][2] = view.getDat();
            data[i][3] = view.isOsig();
        }

        updateTable(columns, data);
    }

    private void loadLekarData() {
        String[] columns = {"Ime", "Prezime", "Specijalizacija"};

        // Fetch the data from DB, converting entities to views
        List<Object> results = queries.getData(entities.Mr10223Lekar.class, views.Mr10223LekarView::new);

        // Build table data dynamically
        Object[][] data = new Object[results.size()][columns.length];

        for (int i = 0; i < results.size(); i++) {
            views.Mr10223LekarView view = (views.Mr10223LekarView) results.get(i);
            data[i][0] = view.getIme();
            data[i][1] = view.getPrz();
            data[i][2] = view.getSpec();
        }

        updateTable(columns, data);
    }

    private void loadDijagnozaData() {
        String[] columns = {"Šifra", "Naziv", "Opis"};

        // Fetch the data from DB, converting entities to views
        List<Object> results = queries.getData(entities.Mr10223Dijagnoza.class, views.Mr10223DijagnozaView::new);

        // Build table data dynamically
        Object[][] data = new Object[results.size()][columns.length];

        for (int i = 0; i < results.size(); i++) {
            views.Mr10223DijagnozaView view = (views.Mr10223DijagnozaView) results.get(i);
            data[i][0] = view.getSifra();
            data[i][1] = view.getNaziv();
            data[i][2] = view.getOpis();
        }

        updateTable(columns, data);
    }

    private void loadPregledData() {
        String[] columns = {"Lekar Ime", "Lekar Prezime", "Lekar Specijalizacija", "Pacijent Ime", "Pacijent Prezime", "Pacijent Datum Rodjenja", "Datum pregleda", "Duzina pregleda", "Cena pregleda"};

        // Fetch the data from DB, converting entities to views
        List<Object> results = queries.getData(entities.Mr10223Pregled.class, views.Mr10223PregledView::new);

        // Build table data dynamically
        Object[][] data = new Object[results.size()][columns.length];

        for (int i = 0; i < results.size(); i++) {
            views.Mr10223PregledView view = (views.Mr10223PregledView) results.get(i);
            data[i][0] = view.getLekar_ime();
            data[i][1] = view.getLekar_prz();
            data[i][2] = view.getLekar_spec();
            data[i][3] = view.getPacijent_ime();
            data[i][4] = view.getPacijent_prz();
            data[i][5] = view.getPacijent_datrodj();
            data[i][6] = view.getDat();
            data[i][7] = view.getVreme();
            data[i][8] = view.getCena();
        }

        updateTable(columns, data);
    }

    private void updateTable(String[] columns, Object[][] data) {
        tableModel.setDataVector(data, columns);
    }

    private void reloadTable() {
        // code to reload data from Queries and refresh JTable model
    }

    // Entry point for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
