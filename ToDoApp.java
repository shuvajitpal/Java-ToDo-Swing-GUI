package com.ElevateLabs;
import javax.swing.*;
import java.awt.*;

public class ToDoApp {
   public static void main(String[] args) {
      new ToDoApp().createUI();
   }

   private DefaultListModel<String> taskListModel;

   public void createUI() {
      JFrame frame = new JFrame("To-Do List App");
      frame.setSize(400, 400);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());

      JTextArea taskField = new JTextArea(3, 20); // 3 rows, 20 columns
      taskField.setLineWrap(true);
      taskField.setWrapStyleWord(true);
      JScrollPane inputScroll = new JScrollPane(taskField);

      JButton addButton = new JButton("Add Task");
      JButton deleteButton = new JButton("Delete Task");

      JPanel inputPanel = new JPanel();
      inputPanel.setLayout(new BorderLayout());
      inputPanel.add(inputScroll, BorderLayout.CENTER);
      inputPanel.add(addButton, BorderLayout.EAST);

      taskListModel = new DefaultListModel<>();
      JList<String> taskList = new JList<>(taskListModel);

      taskList.setCellRenderer(new DefaultListCellRenderer() {
         @Override
         public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JTextArea area = new JTextArea(value.toString());
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setEditable(false);

            area.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

            if (isSelected) {
               area.setBackground(list.getSelectionBackground());
               area.setForeground(list.getSelectionForeground());
            } else {
               area.setBackground(list.getBackground());
               area.setForeground(list.getForeground());
            }
            return area;
         }
      });

      JScrollPane scrollPanel = new JScrollPane(taskList);

      JLabel emptyLabel = new JLabel("No Tasks Available!", SwingConstants.CENTER);
      emptyLabel.setForeground(Color.GRAY);
      emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));

      JPanel centerPanel = new JPanel(new CardLayout());
      centerPanel.add(emptyLabel, "EMPTY");
      centerPanel.add(scrollPanel, "LIST");

      CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
      cardLayout.show(centerPanel, "EMPTY");

      taskListModel.addListDataListener(new javax.swing.event.ListDataListener() {
         public void intervalAdded(javax.swing.event.ListDataEvent e) {
            if (taskListModel.isEmpty()) cardLayout.show(centerPanel, "EMPTY");
            else cardLayout.show(centerPanel, "LIST");
         }
         public void intervalRemoved(javax.swing.event.ListDataEvent e) {
            if (taskListModel.isEmpty()) cardLayout.show(centerPanel, "EMPTY");
            else cardLayout.show(centerPanel, "LIST");
         }
         public void contentsChanged(javax.swing.event.ListDataEvent e) {
            if (taskListModel.isEmpty()) cardLayout.show(centerPanel, "EMPTY");
            else cardLayout.show(centerPanel, "LIST");
         }
      });
      emptyLabel.setVisible(true);

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(deleteButton);

      frame.add(inputPanel, BorderLayout.NORTH);
      frame.add(centerPanel, BorderLayout.CENTER);
      frame.add(buttonPanel, BorderLayout.SOUTH);

      addButton.addActionListener(e -> {
         String task = taskField.getText().trim();
         if (!task.isEmpty()) {
            taskListModel.addElement(task);
            taskField.setText("");
         }
      });

      deleteButton.addActionListener(e -> {
         int selectedIndex = taskList.getSelectedIndex();
         if (selectedIndex != -1) taskListModel.remove(selectedIndex);
      });

      frame.setVisible(true);
   }
}
