package gui;

import controllers.Queries;
import entities.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class DeleteWindow extends JFrame {
    private final JComboBox<String> entityTypeCombo;
    private final JPanel dynamicPanel;
    private final Queries queries = new Queries();
    private JComboBox<?> deleteCombo;
    private final Runnable onDeleteCallback;

    // vecinom kopirano iz AddWindow-a
    public DeleteWindow(Runnable onDeleteCallback) {
        this.onDeleteCallback = onDeleteCallback;

        setTitle("Brisanje zapisa");
        setSize(500, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        entityTypeCombo = new JComboBox<>(new String[]{"Pacijent", "Lekar", "Dijagnoza", "Pregled"});
        topPanel.add(new JLabel("Tip entiteta:"));
        topPanel.add(entityTypeCombo);
        add(topPanel, BorderLayout.NORTH);

        dynamicPanel = new JPanel(new BorderLayout());
        add(dynamicPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Obriši");
        JButton cancelButton = new JButton("Otkaži");
        bottomPanel.add(deleteButton);
        bottomPanel.add(cancelButton);
        add(bottomPanel, BorderLayout.SOUTH);

        entityTypeCombo.addActionListener(e -> buildForm((String) entityTypeCombo.getSelectedItem()));
        deleteButton.addActionListener(this::onDelete);
        cancelButton.addActionListener(e -> dispose());

        buildForm("Pacijent");
    }

    private void buildForm(String selected) {
        dynamicPanel.removeAll();
        JPanel form = new JPanel(new GridLayout(1, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("Izaberi entitet za brisanje"));

        switch (selected) {
            case "Pacijent" -> {
                List<Mr10223Pacijent> pacijenti = queries.getAllPacijenti();
                deleteCombo = new JComboBox<>(pacijenti.toArray(new Mr10223Pacijent[0]));
            }
            case "Lekar" -> {
                List<Mr10223Lekar> lekari = queries.getAllLekari();
                deleteCombo = new JComboBox<>(lekari.toArray(new Mr10223Lekar[0]));
            }
            case "Dijagnoza" -> {
                List<Mr10223Dijagnoza> dijagnoze = queries.getAllDijagnoze();
                deleteCombo = new JComboBox<>(dijagnoze.toArray(new Mr10223Dijagnoza[0]));
            }
            case "Pregled" -> {
                List<Mr10223Pregled> pregledi = queries.getAllPregledi();
                deleteCombo = new JComboBox<>(pregledi.toArray(new Mr10223Pregled[0]));
            }
        }

        if (deleteCombo != null) {
            deleteCombo.setPreferredSize(new Dimension(250, 25));
            form.add(new JLabel("Izaberite:"));
            form.add(deleteCombo);
        }

        dynamicPanel.add(form, BorderLayout.CENTER);
        dynamicPanel.revalidate();
        dynamicPanel.repaint();
    }

    private void onDelete(ActionEvent e) {
        Object selectedObject = deleteCombo.getSelectedItem();
        String selectedType = (String) entityTypeCombo.getSelectedItem();

        if (selectedObject == null) {
            JOptionPane.showMessageDialog(this, "Morate izabrati entitet za brisanje!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Da li ste sigurni da želite obrisati ovaj zapis?",
                "Potvrda brisanja",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            switch (selectedType) {
                case "Pacijent" -> queries.deletePacijent((Mr10223Pacijent) selectedObject);
                case "Lekar" -> queries.deleteLekar((Mr10223Lekar) selectedObject);
                case "Dijagnoza" -> queries.deleteDijagnoza((Mr10223Dijagnoza) selectedObject);
                case "Pregled" -> queries.deletePregled((Mr10223Pregled) selectedObject);
                default -> throw new IllegalArgumentException("Nepoznat tip entiteta: " + selectedType);
            }

            JOptionPane.showMessageDialog(this, "Zapis uspešno obrisan!");
            if (onDeleteCallback != null) onDeleteCallback.run();
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Greška pri brisanju: " + ex.getMessage());
        }
    }


}
