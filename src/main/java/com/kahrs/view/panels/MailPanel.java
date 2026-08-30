package com.kahrs.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import com.kahrs.app.MailManager;
import com.kahrs.model.Mail;
import com.kahrs.view.GameView;
import com.kahrs.view.visuals.HRSButtons;
import com.kahrs.view.visuals.HRSColors;
import com.kahrs.view.visuals.HRSFonts;
import com.kahrs.view.visuals.HRSLanguages;

/**
 * Classe MailPanel permettant d'afficher l'interface de messagerie.
 * @author Sofyane HARISSE
 * @since 0.1
 * @version 0.1
 */
public class MailPanel extends JPanel {

    // ==================== ATTRIBUTS ====================

    /** Vue principale du jeu. */
    private GameView view;

    /** Liste visuelle des emails. */
    private JList<Mail> mailList;

    /** Modèle de données pour la liste. */
    private DefaultListModel<Mail> listModel;

    /** Zone d'affichage du texte du mail. */
    private JTextArea contentArea;

    /** Label affichant le sujet du mail sélectionné. */
    private JLabel subjectLabel;

    /** Label affichant la date du mail sélectionné. */
    private JLabel dateLabel;

    /** Couleur d'accentuation verte HRS. */
    private final Color ACCENT_GREEN = HRSColors.FM_BORDER_GREEN;
    /** Couleur du texte blanc. */
    private final Color TEXT_WHITE = HRSColors.FM_TEXT_WHITE;
    /** Fond sombre pour le contraste. */
    private final Color BG_DARKER = new Color(25, 25, 25);

    // ================== CONSTRUCTEUR ==================

    /**
     * Constructeur de MailPanel pour initialiser l'interface avec la vue principale.
     * @param view La vue principale du jeu.
     */
    public MailPanel(GameView view) {
        this.view = view;
        this.setBackground(BG_DARKER);
        this.setLayout(new BorderLayout());

        initComponents();
        setupLayout();
    }

    // ==================== METHODES =====================

    /**
     * Méthode pour initialiser et styliser les composants du panneau.
     */
    private void initComponents() {
        listModel = new DefaultListModel<>();
        mailList = new JList<>(listModel);
        mailList.setBackground(new Color(35, 35, 35));
        mailList.setForeground(TEXT_WHITE);
        mailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mailList.setFixedCellHeight(70);

        mailList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, 
                        index, isSelected, cellHasFocus);
                Mail mail = (Mail) value;

                String statusIcon = mail.isRead() ? "" : "<font color='#2ECC71'>● </font>";
                String fontWeight = mail.isRead() ? "normal" : "bold";

                label.setText("<html><body style='width: 180px; padding: 10px;'>"
                            + statusIcon + "<span style='font-weight:" + fontWeight + ";'>" 
                            + mail.getSubject() + "</span><br>"
                            + "<span style='font-size:9px; color:#888888;'>" + mail.getDate() 
                            + "</span>"
                            + "</body></html>");

                label.setFont(HRSFonts.SEGEO_UI);

                if (isSelected) {
                    label.setBackground(new Color(50, 60, 50));
                    label.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, ACCENT_GREEN));
                } else {
                    label.setBackground(index % 2 == 0 ? new Color(35, 35, 35) : new Color(30, 30, 30));
                    label.setBorder(BorderFactory.createMatteBorder(0, 5, 0, 0, new Color(0, 0, 0, 0)));
                }
                return label;
            }
        });

        mailList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && mailList.getSelectedValue() != null) {
                displayMail(mailList.getSelectedValue());
            }
        });

        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBackground(new Color(40, 40, 42));
        contentArea.setForeground(TEXT_WHITE);
        contentArea.setFont(HRSFonts.SEGEO_UI.deriveFont(15f));
        contentArea.setBorder(new EmptyBorder(30, 30, 30, 30));

        subjectLabel = new JLabel(HRSLanguages.Mail.getSelectMailText());
        subjectLabel.setFont(HRSFonts.SEGEO_UI.deriveFont(Font.BOLD, 20f));
        subjectLabel.setForeground(TEXT_WHITE);

        dateLabel = new JLabel("");
        dateLabel.setFont(HRSFonts.SEGEO_UI.deriveFont(13f));
        dateLabel.setForeground(Color.GRAY);

        refreshList();
    }

    /**
     * Méthode pour organiser la disposition des composants.
     */
    private void setupLayout() {
        JPanel topHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 15));
        topHeader.setBackground(BG_DARKER);
        JLabel pageTitle = new JLabel(HRSLanguages.Mail.getTitle());
        pageTitle.setFont(HRSFonts.SEGEO_UI.deriveFont(Font.BOLD, 18f));
        pageTitle.setForeground(ACCENT_GREEN);
        topHeader.add(pageTitle);

        JScrollPane listScroll = new JScrollPane(mailList);
        listScroll.setPreferredSize(new Dimension(300, 0));
        listScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.DARK_GRAY));

        JPanel readPanel = new JPanel(new BorderLayout());
        readPanel.setBackground(new Color(40, 40, 42));

        JPanel mailHeader = new JPanel(new BorderLayout());
        mailHeader.setBackground(new Color(45, 45, 47));
        mailHeader.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY),
            new EmptyBorder(20, 30, 20, 30)
        ));
        mailHeader.add(subjectLabel, BorderLayout.WEST);
        mailHeader.add(dateLabel, BorderLayout.EAST);

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBorder(null);

        readPanel.add(mailHeader, BorderLayout.NORTH);
        readPanel.add(contentScroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 30, 15));
        footer.setBackground(BG_DARKER);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        
        JButton btnBack = new HRSButtons(HRSLanguages.getCloseButtonText(), 150, 40);
        btnBack.addActionListener(e -> this.view.eventCloseButton());
        footer.add(btnBack);

        this.add(topHeader, BorderLayout.NORTH);
        this.add(listScroll, BorderLayout.WEST);
        this.add(readPanel, BorderLayout.CENTER);
        this.add(footer, BorderLayout.SOUTH);
    }

    /**
     * Méthode pour afficher le contenu d'un mail et le marquer comme lu.
     * @param mail Le mail à afficher.
     */
    private void displayMail(Mail mail) {
        subjectLabel.setText(mail.getSubject());
        contentArea.setText(mail.getContent());
        dateLabel.setText(HRSLanguages.Mail.getDateText(mail.getDate()));

        if (!mail.isRead()) {
            mail.setRead(true);
            MailManager.saveMails();
            mailList.repaint();
        }
    }

    /**
     * Méthode pour recharger la liste des emails depuis le MailManager.
     */
    public void refreshList() {
        listModel.clear();
        for (Mail m : MailManager.getMails()) {
            listModel.addElement(m);
        }
        
        if (!listModel.isEmpty()) {
            SwingUtilities.invokeLater(() -> mailList.setSelectedIndex(0));
        } else {
            if (subjectLabel != null) {
                subjectLabel.setText(HRSLanguages.Mail.getNoMessageText());
            }
            if (contentArea != null) {
                contentArea.setText(HRSLanguages.Mail.getEmptyMailsText());
            }
        }
    }
}