package com.restready.common.tempchat;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;

public class ConsoleGui {

    private final JFrame _frame;
    private final JTextArea _outputTextArea;
    private Consumer<String> _userTextInputListener;

    public ConsoleGui(String title, String alias) {

        _outputTextArea = new JTextArea();
        _outputTextArea.setEditable(false);
        _outputTextArea.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(_outputTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JTextField inputField = new JTextField();
        inputField.addActionListener(e -> {
            String userInput = inputField.getText();
            if (_userTextInputListener != null) {
                _userTextInputListener.accept(userInput);
            }
            printImpl("[%s] :: %s\n".formatted(alias, userInput));
            inputField.setText(""); // Clear the input
        });

        _frame = new JFrame(title);
        _frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        _frame.setSize(400, 300);
        _frame.setResizable(false);

        Container content = _frame.getContentPane();
        content.setLayout(new BorderLayout());
        content.add(scrollPane, BorderLayout.CENTER);
        content.add(inputField, BorderLayout.SOUTH);

        _frame.setVisible(true);
    }

    private void printImpl(String text) {

        final int maxLines = 128;
        _outputTextArea.append(text);

        // Remove old lines if the line count exceeds the maximum
        int lineCount = _outputTextArea.getLineCount();
        if (lineCount > maxLines) {
            int linesToRemove = lineCount - maxLines;
            int endOffset;
            try {
                endOffset = _outputTextArea.getLineEndOffset(linesToRemove - 1);
            } catch (BadLocationException e) {
                throw new RuntimeException(e);
            }
            _outputTextArea.replaceRange("", 0, endOffset);
        }
    }

    private void shutdownImpl() {
        _frame.dispatchEvent(new WindowEvent(_frame, WindowEvent.WINDOW_CLOSING));
    }

    public void print(String text) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> printImpl(text));
        } else {
            printImpl(text);
        }
    }

    public void shutdown() {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(this::shutdown);
        } else {
            shutdownImpl();
        }
    }

    public void setUserTextInputListener(Consumer<String> listener) {
        _userTextInputListener = listener;
    }
}
