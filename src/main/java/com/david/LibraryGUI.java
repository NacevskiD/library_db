package com.david;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class LibraryGUI extends JFrame {
    private JPanel mainPanel;

    private JTextField enterBookName;
    private JTextField enterAuthorName;
    private JButton addBooks;
    private JList<Books> allBooksList;
    private JScrollPane allBooksListScrollPane;
    private JLabel bookNameLabel;
    private JLabel authorNameLabel;
    private JLabel libraryBooksLabel;
    private JButton deleteButton;
    private JList<Books> borrowedBooksList;
    private JScrollPane borrowedBooksScrollPane;
    private JButton borrowButton;
    private JLabel borrowedBooksLabel;
    private JButton returnButton;
    private DefaultListModel<Books> allBooksModel;
    private DefaultListModel<Books> borrowedBooksModel;

    private Controller controller;

    LibraryGUI(Controller controller){
        this.controller = controller;

        addComponents();

        setContentPane(mainPanel);
        pack();
        setTitle("Book Manager");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        allBooksModel = new DefaultListModel<Books>();
        allBooksList.setModel(allBooksModel);

        borrowedBooksModel = new DefaultListModel<Books>();
        borrowedBooksList.setModel(borrowedBooksModel);



        addBooks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String book = enterBookName.getText();
                String author = enterAuthorName.getText();

                if (book.isEmpty()){
                    JOptionPane.showMessageDialog(LibraryGUI.this,"Enter the name of the book");
                    return;
                }
                if (author.isEmpty()){
                    JOptionPane.showMessageDialog(LibraryGUI.this,"Enter the author of the book");
                    return;
                }

                Books newBook = new Books(book,author);
                controller.addRecordsToDatabase(newBook);

                enterBookName.setText("");
                enterAuthorName.setText("");

                ArrayList<Books> allData = controller.getAllData();
                setListData(allData);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            Books selectedBook = allBooksList.getSelectedValue();
            if (selectedBook == null){
                JOptionPane.showMessageDialog(LibraryGUI.this,"Please select a book to delete");
            }
            else {
                controller.delete(selectedBook);
                ArrayList<Books> books = controller.getAllData();
                setListData(books);
            }
            }
        });
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Books selectedBook = allBooksList.getSelectedValue();
                if (selectedBook == null){
                    JOptionPane.showMessageDialog(LibraryGUI.this,"Please select a book to borrow");
                }else {
                    controller.borrow(selectedBook);
                    ArrayList<Books> borrowedBooks = controller.getAllBorrowedInfo();
                    setBorrowedListData( borrowedBooks);
                    ArrayList<Books> books = controller.getAllData();
                    setListData(books);
                }
            }
        });
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Books selectedBook = borrowedBooksList.getSelectedValue();
                if (selectedBook == null){
                    JOptionPane.showMessageDialog(LibraryGUI.this,"Please select a book to borrow");
                }else {
                    controller.returnBook(selectedBook);
                    ArrayList<Books> borrowedBooks = controller.getAllBorrowedInfo();
                    setBorrowedListData( borrowedBooks);
                    ArrayList<Books> books = controller.getAllData();
                    setListData(books);
                }
            }
        });
    }
    void setListData(ArrayList<Books> data) {

        //Display data in allDataTextArea
        allBooksModel.clear();

        for (Books book : data) {
            allBooksModel.addElement(book);
        }
    }
    void setBorrowedListData(ArrayList<Books> data){
        borrowedBooksModel.clear();

        for (Books book : data){
            borrowedBooksModel.addElement(book);
        }
    }
    private void addComponents() {

        //Initialize the components
        addBooks = new JButton("Add Book");
        enterBookName = new JTextField();
        enterAuthorName = new JTextField();
        bookNameLabel = new JLabel("Enter the book name");
        authorNameLabel = new JLabel("Enter the author name");
        libraryBooksLabel = new JLabel("Library books");
        deleteButton = new JButton("Delete Book");
        borrowButton = new JButton("Borrow Book");
        borrowedBooksLabel = new JLabel("Borrowed Books");
        returnButton = new JButton("Return Book");

        //and the JList, add it to a JScrollPane
        allBooksList = new JList<Books>();
        allBooksListScrollPane = new JScrollPane(allBooksList);

        borrowedBooksList = new JList<Books>();
        borrowedBooksScrollPane = new JScrollPane(borrowedBooksList);

        //Create a JPanel to hold all of the above
        mainPanel = new JPanel();

        //A LayoutManager is in charge of organizing components within a JPanel.
        //BoxLayout can display items in a vertical or horizontal list, (and also other configurations, see JavaDoc)
        BoxLayout layoutMgr = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(layoutMgr);

        //And add the components to the JPanel mainPanel
        mainPanel.add(bookNameLabel);
        mainPanel.add(enterBookName);
        mainPanel.add(authorNameLabel);
        mainPanel.add(enterAuthorName);
        mainPanel.add(addBooks);
        mainPanel.add(libraryBooksLabel);
        mainPanel.add(allBooksListScrollPane);
        mainPanel.add(borrowButton);
        mainPanel.add(deleteButton);
        mainPanel.add(borrowedBooksLabel);
        mainPanel.add(borrowedBooksScrollPane);
        mainPanel.add(returnButton);


    }

}
