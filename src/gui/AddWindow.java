package gui;

import controllers.Queries;
import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AddWindow extends JFrame {
    private final JComboBox<String> entityTypeCombo;
    private final JPanel dynamicPanel;
    private final Queries queries = new Queries();
    private final Runnable onSaveCallback;

    // klasicni unosi
    private JTextField imeField, prezimeField, specField, datumRodjenjaField;
    private JTextField sifraField, nazivField;
    private JTextArea opisArea;
    private JTextField datumField, vremeField, duzinaField, cenaField;

    // kombo boksovi
    private JComboBox<Mr10223Lekar> lekarCombo;
    private JComboBox<Mr10223Pacijent> pacijentCombo;
    private JComboBox<Mr10223Dijagnoza> dijagnozaCombo;

    public AddWindow(Runnable onSaveCallback) {
        this.onSaveCallback = onSaveCallback;

        setTitle("Dodavanje zapisa");
        setSize(700, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // biranje tipaza dodavanje
        JPanel topPanel = new JPanel();
        entityTypeCombo = new JComboBox<>(new String[]{"Pacijent", "Lekar", "Dijagnoza", "Pregled"});
        topPanel.add(new JLabel("Tip entiteta:"));
        topPanel.add(entityTypeCombo);
        add(topPanel, BorderLayout.NORTH);

        // ovde ubijamo decu
        dynamicPanel = new JPanel(new BorderLayout());
        add(dynamicPanel, BorderLayout.CENTER);

        // dugmici
        JPanel bottomPanel = new JPanel();
        JButton confirmButton = new JButton("Potvrdi");
        JButton cancelButton = new JButton("Otkaži");
        bottomPanel.add(confirmButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // eventovi
        entityTypeCombo.addActionListener(e -> buildForm((String) entityTypeCombo.getSelectedItem()));
        confirmButton.addActionListener(this::onConfirm);
        cancelButton.addActionListener(e -> dispose());

        // telo
        buildForm("Pacijent");
    }

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
                dynamicPanel.add(form, BorderLayout.CENTER);
            }
            case "Lekar" -> {
                imeField = new JTextField(20);
                prezimeField = new JTextField(20);
                specField = new JTextField(20);
                addRow(form, gbc, "Ime:", imeField);
                addRow(form, gbc, "Prezime:", prezimeField);
                addRow(form, gbc, "Specijalizacija:", specField);
                dynamicPanel.add(form, BorderLayout.CENTER);
            }
            case "Dijagnoza" -> {
                sifraField = new JTextField(20);
                nazivField = new JTextField(20);
                opisArea = new JTextArea(4, 20);
                addRow(form, gbc, "Šifra:", sifraField);
                addRow(form, gbc, "Naziv:", nazivField);
                addRow(form, gbc, "Opis:", new JScrollPane(opisArea));
                dynamicPanel.add(form, BorderLayout.CENTER);
            }
            case "Pregled" -> {
                dynamicPanel.add(buildPregledForm(), BorderLayout.CENTER);
                dynamicPanel.revalidate();
                dynamicPanel.repaint();
                return;
            }
        }

        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private JPanel buildPregledForm() {
        JPanel form = new JPanel(new BorderLayout());

        // zasto ovo ne radiiiiiiiiiiiiiiiiiiiiii
        JPanel topGrid = new JPanel(new GridLayout(1, 3, 10, 10));

        // podaci
        List<Mr10223Lekar> lekari = queries.getAllLekari();
        List<Mr10223Pacijent> pacijenti = queries.getAllPacijenti();
        List<Mr10223Dijagnoza> dijagnoze = queries.getAllDijagnoze();

        // Lekar panel
        JPanel lekarPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        lekarPanel.setBorder(BorderFactory.createTitledBorder("Lekar"));
        lekarCombo = new JComboBox<>(lekari.toArray(new Mr10223Lekar[0]));
        lekarCombo.setPreferredSize(new Dimension(150, 25));
        lekarPanel.add(new JLabel("Izaberi lekara:"));
        lekarPanel.add(lekarCombo);

        // Pacijent panel
        JPanel pacijentPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        pacijentPanel.setBorder(BorderFactory.createTitledBorder("Pacijent"));
        pacijentCombo = new JComboBox<>(pacijenti.toArray(new Mr10223Pacijent[0]));
        pacijentCombo.setPreferredSize(new Dimension(150, 25));
        pacijentPanel.add(new JLabel("Izaberi pacijenta:"));
        pacijentPanel.add(pacijentCombo);

        // Dijagnoza panel
        JPanel dijagnozaPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        dijagnozaPanel.setBorder(BorderFactory.createTitledBorder("Dijagnoza"));
        dijagnozaCombo = new JComboBox<>(dijagnoze.toArray(new Mr10223Dijagnoza[0]));
        dijagnozaCombo.setPreferredSize(new Dimension(150, 25));
        dijagnozaPanel.add(new JLabel("Izaberi dijagnozu:"));
        dijagnozaPanel.add(dijagnozaCombo);

        topGrid.add(lekarPanel);
        topGrid.add(pacijentPanel);
        topGrid.add(dijagnozaPanel);

        // ostatak informacija za pregled
        JPanel bottomGrid = new JPanel(new GridLayout(4, 2, 5, 5));
        bottomGrid.setBorder(BorderFactory.createTitledBorder("Pregled"));

        datumField = new JTextField(10);
        vremeField = new JTextField(10);
        duzinaField = new JTextField(10);
        cenaField = new JTextField(10);

        bottomGrid.add(new JLabel("Datum (YYYY-MM-DD):")); bottomGrid.add(datumField);
        bottomGrid.add(new JLabel("Početak (HH:MM):")); bottomGrid.add(vremeField);
        bottomGrid.add(new JLabel("Trajanje (HH:MM):")); bottomGrid.add(duzinaField);
        bottomGrid.add(new JLabel("Cena:")); bottomGrid.add(cenaField);

        form.add(topGrid, BorderLayout.CENTER);
        form.add(bottomGrid, BorderLayout.SOUTH);

        dynamicPanel.add(form, BorderLayout.CENTER);
        return form;
    }



    private void addRow(JPanel form, GridBagConstraints gbc, String label, Component field) {
        gbc.gridx = 0; form.add(new JLabel(label), gbc);
        gbc.gridx = 1; form.add(field, gbc);
        gbc.gridy++;
    }

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
        try {
            Mr10223Lekar selectedLekar = (Mr10223Lekar) lekarCombo.getSelectedItem();
            Mr10223Pacijent selectedPacijent = (Mr10223Pacijent) pacijentCombo.getSelectedItem();
            Mr10223Dijagnoza selectedDijagnoza = (Mr10223Dijagnoza) dijagnozaCombo.getSelectedItem();

            if (selectedLekar == null || selectedPacijent == null || selectedDijagnoza == null) {
                JOptionPane.showMessageDialog(this, "Morate izabrati lekara, pacijenta i dijagnozu!");
                return;
            }

            LocalDate date = LocalDate.parse(datumField.getText().trim());
            LocalTime startTime = LocalTime.parse(vremeField.getText().trim()); // format "hh:mm"
            LocalDateTime pregledDateTime = LocalDateTime.of(date, startTime);

            // parsovanje vremena
            String[] timeParts = duzinaField.getText().trim().split(":");
            if (timeParts.length != 2) {
                JOptionPane.showMessageDialog(this, "Trajanje mora biti u formatu hh:mm!");
                return;
            }
            int hours = Integer.parseInt(timeParts[0]);
            int minutes = Integer.parseInt(timeParts[1]);

            // zasto sam koristio interval day to second zasto nisam mogao biti normalan i koristiti timestamp
            String intervalLiteral = String.format("0 %02d:%02d:00", hours, minutes);

            // parsovanje cene
            Double cena = Double.parseDouble(cenaField.getText().trim());

            // kljucevi i entiteti
            Mr10223PregledKey key = new Mr10223PregledKey();
            key.setLekarId(selectedLekar.getId());
            key.setPacijentId(selectedPacijent.getId());
            key.setDat(pregledDateTime);

            Mr10223Pregled pregled = new Mr10223Pregled();
            pregled.setId(key);
            pregled.setVreme(intervalLiteral);
            pregled.setCena(cena);
            pregled.setDijagnozaId(selectedDijagnoza.getId());

            queries.insert(pregled);

            JOptionPane.showMessageDialog(this, "Pregled uspešno dodat!");
            dispose(); // Close AddWindow
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška pri dodavanju pregleda:\n" + e.getMessage());
        }
    }


}
