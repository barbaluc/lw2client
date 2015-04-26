import javax.swing.*;
import java.awt.*;

public class GUI extends  JFrame{

    JLabel labId, labFirstname, labName;
    JTextField tfId, tfFirstname, tfName;
    JButton post, get;
    JPanel postInformations, panTerminal;
    FlowLayout fl;
    JTextArea terminal;
    JScrollPane scroll;
    final String urlOS = "http://lw2-barbaluc.rhcloud.com/resume";

    public GUI(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {
        //Panel 1 contenant le formulaire
        GridLayout gl = new GridLayout(4, 2);
        postInformations = new JPanel();
        postInformations.setLayout(gl);

        fl = new FlowLayout();
        panTerminal = new JPanel();
        panTerminal.setLayout(fl);

        labId = new JLabel(" id : ");
        labFirstname = new JLabel(" firstname : ");
        labName = new JLabel(" name :");

        tfId = new JTextField("insert an id");
        tfFirstname = new JTextField("insert a firstname");
        tfName = new JTextField("insert a name");

        post = new JButton("POST");
        get = new JButton("GET");

        postInformations.add(labId);
        postInformations.add(tfId);
        postInformations.add(labFirstname);
        postInformations.add(tfFirstname);
        postInformations.add(labName);
        postInformations.add(tfName);
        postInformations.add(post);
        postInformations.add(get);

        post.addActionListener((e) -> {
            System.out.println("POST");
            try {
                HttpClient c = new HttpClient(urlOS);
                System.out.println("Méthode POST :");
                c.connect("POST");
                c.post("<cv_entry><id>" + tfId.getText() + "</id><firstname>" + tfFirstname.getText() + "</firstname><name>" + tfName.getText()
                        + "</name> </cv_entry>");

                terminal.setText(terminal.getText() + c.displayResponse() + "\n");
                c.disconnect();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        get.addActionListener((e) -> {
            System.out.println("GET");
            try {
                HttpClient c = new HttpClient(urlOS);
                c.connect("GET");
                System.out.println("Méthode GET :");
                terminal.setText(terminal.getText() + c.displayResponse() + "\n");
                c.disconnect();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        panTerminal=new JPanel();

        terminal = new JTextArea(8, 25);
        terminal.setEditable(false);
        terminal.setBackground(Color.BLACK);
        terminal.setForeground(Color.WHITE);

        scroll = new JScrollPane(terminal);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        panTerminal.add(scroll);


        pane.add(postInformations, BorderLayout.NORTH);
        pane.add(panTerminal, BorderLayout.SOUTH);

    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        GUI frame = new GUI("CV Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }
}
