package ui;

import model.Course;
import model.CourseList;
import persistence.JsonReader;
import persistence.JsonWriter;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String saveString = "Save";
    private static final String loadString = "Load";
    private static final String JSON_STORE_GUI = "./data/courseListGUI.json";
    private JButton addButton;
    private JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JTextField courseName;
    private JTextField courseCredit;
    private CourseList courseList;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    public GUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel<>();
        courseList = new CourseList();
        jsonWriter = new JsonWriter(JSON_STORE_GUI);
        jsonReader = new JsonReader(JSON_STORE_GUI);

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);

        AddListener addListener = setAddButton();

        setRemoveButton();

        setSaveButton();

        setLoadButton();

        setJTextField(addListener);

        createPanel(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: set up JTextField
    private void setJTextField(AddListener addListener) {
        courseName = new JTextField(10);
        courseName.addActionListener(addListener);
        courseName.getDocument().addDocumentListener(addListener);

        courseCredit = new JTextField(10);
        courseCredit.addActionListener(addListener);
        courseCredit.getDocument().addDocumentListener(addListener);
    }

    // MODIFIES: this
    // EFFECTS: set up load button
    private void setLoadButton() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
    }

    // MODIFIES: this
    // EFFECTS: set up save button
    private void setSaveButton() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
    }

    // MODIFIES: this
    // EFFECTS: set up remove button
    private void setRemoveButton() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: set up add button
    private AddListener setAddButton() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
        return addListener;
    }

    // MODIFIES: this
    // EFFECTS: create a panel with BoxLayOut
    private void createPanel(JScrollPane listScrollPane) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        setBoundary(buttonPane);
        buttonPane.add(courseName);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(courseCredit);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(addButton);
        setBoundary(buttonPane);
        buttonPane.add(saveButton);
        setBoundary(buttonPane);
        buttonPane.add(loadButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private static void setBoundary(JPanel buttonPane) {
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
    }

    class SaveListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */

        // MODIFIES: this
        // EFFECTS: do save
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(courseList);
                jsonWriter.close();
                Icon icon = new ImageIcon("data/image/icon.png");
                JOptionPane.showMessageDialog(saveButton,
                        "", "Success", JOptionPane.PLAIN_MESSAGE, icon); // get an icon
            } catch (FileNotFoundException exception) {
                JOptionPane.showMessageDialog(saveButton, "Unable to save!", "Warning", 0);
            }

        }
    }

    class LoadListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */

        // MODIFIES: this
        // EFFECTS: do load
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listModel.removeAllElements();
                courseList = jsonReader.read();
                for (Course next : courseList.getCourseList()) {
                    listModel.addElement(next.getName() + " " + next.getCredit() + " Unregistered");
                }
            } catch (IOException exception) {
                JOptionPane.showMessageDialog(saveButton, "Unable to load!", "Warning", 0);
            }
        }
    }


    class RemoveListener implements ActionListener {

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */

        // MODIFIES: this
        // EFFECTS: do remove
        @Override
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);
            List<Course> courses = courseList.getCourseList();
            courses.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable removing.
                removeButton.setEnabled(false);
            }
        }
    }

    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        /**
         * Invoked when an action occurs.
         *
         * @param e the event to be processed
         */

        // MODIFIES: this
        // EFFECTS: do add
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = courseName.getText();
            int credit = Integer.valueOf(courseCredit.getText());
            Course addCourse = new Course(name, credit);
            List<Course> courses = courseList.getCourseList();
            boolean lessThanOrEqualTo0 = credit <= 0;
            boolean result = false;
            for (Course next : courses) {
                if (next.getName().equals(name) && next.getCredit() == credit) {
                    result = true;
                }
            }
            if (lessThanOrEqualTo0) {
                warning();
                JOptionPane.showMessageDialog(addButton,
                        "You should have positive course credit!", "Warning", 0);
            } else if (result) {
                warning();
                JOptionPane.showMessageDialog(addButton,
                        "You can not add same course twice!", "Warning", 0);
            } else {
                courses.add(addCourse);
                listModel.addElement(name + " " + courseCredit.getText() + " Unregistered");
                reset();
            }
        }

        private void warning() {
            Toolkit.getDefaultToolkit().beep();
            courseName.requestFocusInWindow();
            courseName.selectAll();
            courseCredit.requestFocusInWindow();
            courseCredit.selectAll();
        }

        // MODIFIES: this
        // EFFECTS: set JTextField to original state
        private void reset() {
            courseName.requestFocusInWindow();
            courseName.setText("");
            courseCredit.requestFocusInWindow();
            courseCredit.setText("");
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the remove button.
                removeButton.setEnabled(true);
            }
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */

    // MODIFIES: this
    // EFFECTS: create GUI and show it
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("CoursePlanning");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new GUI();
        newContentPane.setOpaque(false); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
