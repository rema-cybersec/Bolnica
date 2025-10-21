package gui;

import controllers.Queries;
import entities.Mr10223Lekar;
import entities.Mr10223Pregled;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LekarPreglediWindow extends JFrame {
    private final Queries queries = new Queries();
    private JComboBox<Mr10223Lekar> lekarCombo;
    private DefaultTableModel tableModel;

    public LekarPreglediWindow() {
        setTitle("Pregledi po lekaru");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        List<Mr10223Lekar> lekari = queries.getAllLekari();
        lekarCombo = new JComboBox<>(lekari.toArray(new Mr10223Lekar[0])); // do objektnog 2 nisam znao za <> operator
        JButton showButton = new JButton("Prikaži preglede");

        topPanel.add(new JLabel("Izaberi lekara:"));
        topPanel.add(lekarCombo);
        topPanel.add(showButton);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Pacijent ime", "Pacijent prezime", "Datum pregleda", "Trajanje", "Cena"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        showButton.addActionListener(e -> loadPregledi());
    }

    private void loadPregledi() {
        Mr10223Lekar selectedLekar = (Mr10223Lekar) lekarCombo.getSelectedItem();
        if (selectedLekar == null) return;

        List<Mr10223Pregled> pregledi = queries.getPreglediByLekar(selectedLekar);

        tableModel.setRowCount(0);
        for (Mr10223Pregled p : pregledi) {
            tableModel.addRow(new Object[]{
                    p.getId().getPacijentId().getIme(),
                    p.getId().getPacijentId().getPrz(),
                    p.getId().getDat(),
                    p.getVreme(),
                    p.getCena()
            });
        }
    }
}
