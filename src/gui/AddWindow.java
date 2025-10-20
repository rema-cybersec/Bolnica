package gui;

import controllers.Queries;
import entities.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddWindow extends JFrame {
    private final JComboBox<String> entityTypeCombo;
    private final JPanel dynamicPanel;
    private final Queries queries = new Queries();
    private final Runnable onSaveCallback;

    // Input components for all entities
    private JTextField imeField, prezimeField, specField, datumRodjenjaField;
    private JTextField sifraField, nazivField;
    private JTextArea opisArea;
    private JTextField vremeField, cenaField;
    private JTextField pacijentImeField, pacijentPrezimeField, pacijentDatumRodjenjaField;
    private JTextField lekarImeField, lekarPrezimeField, lekarSpecField;
    private JTextField dijagnozaSifraField;

    public AddWindow(Runnable onSaveCallback) {
        this.onSaveCallback = onSaveCallback;

        setTitle("Dodavanje zapisa");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Entity type selector ---
        JPanel topPanel = new JPanel();
        entityTypeCombo = new JComboBox<>(new String[]{"Pacijent", "Lekar", "Dijagnoza", "Pregled"});
        topPanel.add(new JLabel("Tip entiteta:"));
        topPanel.add(entityTypeCombo);
        add(topPanel, BorderLayout.NORTH);

        // --- Dynamic form area ---
        dynamicPanel = new JPanel(new BorderLayout());
        add(dynamicPanel, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel bottomPanel = new JPanel();
        JButton confirmButton = new JButton("Potvrdi");
        JButton cancelButton = new JButton("Otkaži");
        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Events ---
        entityTypeCombo.addActionListener(e -> buildForm((String) entityTypeCombo.getSelectedItem()));
        confirmButton.addActionListener(this::onConfirm);
        cancelButton.addActionListener(e -> dispose());

        // Initialize
        buildForm("Pacijent");
    }

    // Dynamically rebuilds form
    private void buildForm(String selected) {
        dynamicPanel.removeAll();
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        switch (selected) {
            case "Pacijent" -> {
                imeField = new JTextField(20);
                prezimeField = new JTextField(20);
                datumRodjenjaField = new JTextField(20);
                addRow(form, gbc, "Ime:", imeField);
                addRow(form, gbc, "Prezime:", prezimeField);
                addRow(form, gbc, "Datum rođenja (YYYY-MM-DD):", datumRodjenjaField);
            }
            case "Lekar" -> {
                imeField = new JTextField(20);
                prezimeField = new JTextField(20);
                specField = new JTextField(20);
                addRow(form, gbc, "Ime:", imeField);
                addRow(form, gbc, "Prezime:", prezimeField);
                addRow(form, gbc, "Specijalizacija:", specField);
            }
            case "Dijagnoza" -> {
                sifraField = new JTextField(20);
                nazivField = new JTextField(20);
                opisArea = new JTextArea(4, 20);
                addRow(form, gbc, "Šifra:", sifraField);
                addRow(form, gbc, "Naziv:", nazivField);
                addRow(form, gbc, "Opis:", new JScrollPane(opisArea));
            }
            case "Pregled" -> {
                form.setLayout(new BorderLayout());

                // Left-right split for lekar/pacijent
                JPanel topGrid = new JPanel(new GridLayout(1, 2, 10, 10));

                JPanel lekarPanel = new JPanel(new GridLayout(3, 2, 5, 5));
                lekarPanel.setBorder(BorderFactory.createTitledBorder("Lekar"));
                lekarImeField = new JTextField(10);
                lekarPrezimeField = new JTextField(10);
                lekarSpecField = new JTextField(10);
                lekarPanel.add(new JLabel("Ime:")); lekarPanel.add(lekarImeField);
                lekarPanel.add(new JLabel("Prezime:")); lekarPanel.add(lekarPrezimeField);
                lekarPanel.add(new JLabel("Specijalizacija:")); lekarPanel.add(lekarSpecField);

                JPanel pacijentPanel = new JPanel(new GridLayout(3, 2, 5, 5));
                pacijentPanel.setBorder(BorderFactory.createTitledBorder("Pacijent"));
                pacijentImeField = new JTextField(10);
                pacijentPrezimeField = new JTextField(10);
                pacijentDatumRodjenjaField = new JTextField(10);
                pacijentPanel.add(new JLabel("Ime:")); pacijentPanel.add(pacijentImeField);
                pacijentPanel.add(new JLabel("Prezime:")); pacijentPanel.add(pacijentPrezimeField);
                pacijentPanel.add(new JLabel("Datum rođenja (YYYY-MM-DD):")); pacijentPanel.add(pacijentDatumRodjenjaField);

                topGrid.add(lekarPanel);
                topGrid.add(pacijentPanel);

                JPanel bottomGrid = new JPanel(new GridLayout(3, 2, 5, 5));
                bottomGrid.setBorder(BorderFactory.createTitledBorder("Pregled"));
                dijagnozaSifraField = new JTextField(10);
                vremeField = new JTextField(10);
                cenaField = new JTextField(10);
                bottomGrid.add(new JLabel("Dijagnoza šifra:")); bottomGrid.add(dijagnozaSifraField);
                bottomGrid.add(new JLabel("Vreme (YYYY-MM-DDTHH:MM):")); bottomGrid.add(vremeField);
                bottomGrid.add(new JLabel("Cena:")); bottomGrid.add(cenaField);

                form.add(topGrid, BorderLayout.CENTER);
                form.add(bottomGrid, BorderLayout.SOUTH);
            }
        }

        dynamicPanel.add(form, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void addRow(JPanel form, GridBagConstraints gbc, String label, Component field) {
        gbc.gridx = 0; form.add(new JLabel(label), gbc);
        gbc.gridx = 1; form.add(field, gbc);
        gbc.gridy++;
    }

    // Handle confirm
    private void onConfirm(ActionEvent e) {
        try {
            String selected = (String) entityTypeCombo.getSelectedItem();
            switch (selected) {
                case "Pacijent" -> insertPacijent();
                case "Lekar" -> insertLekar();
                case "Dijagnoza" -> insertDijagnoza();
                case "Pregled" -> insertPregled();
            }

            if (onSaveCallback != null) onSaveCallback.run();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Entity-specific insert helpers ---

    private void insertPacijent() {
        Mr10223PacijentKey key = new Mr10223PacijentKey();
        key.setIme(imeField.getText());
        key.setPrz(prezimeField.getText());
        key.setDatrodj(LocalDate.parse(datumRodjenjaField.getText()));

        Mr10223Pacijent pacijent = new Mr10223Pacijent();
        pacijent.setId(key);
        queries.insert(pacijent);
    }

    private void insertLekar() {
        Mr10223LekarKey key = new Mr10223LekarKey();
        key.setIme(imeField.getText());
        key.setPrz(prezimeField.getText());
        key.setSpec(specField.getText());

        Mr10223Lekar lekar = new Mr10223Lekar();
        lekar.setId(key);
        queries.insert(lekar);
    }

    private void insertDijagnoza() {
        Mr10223DijagnozaKey key = new Mr10223DijagnozaKey();
        key.setSifra(sifraField.getText());

        Mr10223Dijagnoza dijagnoza = new Mr10223Dijagnoza();
        dijagnoza.setId(key);
        dijagnoza.setNaziv(nazivField.getText());
        dijagnoza.setOpis(opisArea.getText());

        queries.insert(dijagnoza);
    }

    private void insertPregled() {
        // Pacijent
        Mr10223PacijentKey pacijentKey = new Mr10223PacijentKey();
        pacijentKey.setIme(pacijentImeField.getText());
        pacijentKey.setPrz(pacijentPrezimeField.getText());
        pacijentKey.setDatrodj(LocalDate.parse(pacijentDatumRodjenjaField.getText()));

        // Lekar
        Mr10223LekarKey lekarKey = new Mr10223LekarKey();
        lekarKey.setIme(lekarImeField.getText());
        lekarKey.setPrz(lekarPrezimeField.getText());
        lekarKey.setSpec(lekarSpecField.getText());

        // PregledKey
        Mr10223PregledKey pregledKey = new Mr10223PregledKey();
        pregledKey.setLekarId(lekarKey);
        pregledKey.setPacijentId(pacijentKey);
        pregledKey.setDat(LocalDateTime.parse(vremeField.getText()));

        // Pregled
        Mr10223Pregled pregled = new Mr10223Pregled();
        pregled.setId(pregledKey);
        pregled.setCena(Double.valueOf(cenaField.getText()));

        // Dijagnoza reference (optional)
        if (!dijagnozaSifraField.getText().isBlank()) {
            Mr10223DijagnozaKey dijagnozaKey = new Mr10223DijagnozaKey();
            dijagnozaKey.setSifra(dijagnozaSifraField.getText());
            pregled.setDijagnozaId(dijagnozaKey);
        }

        queries.insert(pregled);
    }
}
