import javax.swing.*;
import java.awt.*;

public class GUI extends  JFrame{

    JLabel labId, labFirstname, labName;
    JTextField tfId, tfFirstname, tfName;
    JButton post;

    public GUI(String name) {
        super(name);
        setResizable(false);
    }

    public void addComponentsToPane(final Container pane) {
        //Panel
        GridLayout gl = new GridLayout(4, 2);
        final JPanel postInformations = new JPanel();
        postInformations.setLayout(gl);


        labId = new JLabel(" id : ");
        labFirstname = new JLabel(" firstname : ");
        labName = new JLabel(" name :");

        tfId = new JTextField("insert an id");
        tfFirstname = new JTextField("insert a firstname");
        tfName = new JTextField("insert a name");

        post = new JButton("POST");

        postInformations.add(labId);
        postInformations.add(tfId);
        postInformations.add(labFirstname);
        postInformations.add(tfFirstname);
        postInformations.add(labName);
        postInformations.add(tfName);
        postInformations.add(post);

        post.addActionListener((e) -> {
            System.out.println("POST");
            try {
                HttpClient c = new HttpClient("http://lw2-barbaluc.rhcloud.com/resume");
                c.connect("GET");
                System.out.println("Méthode GET :");
                c.displayResponse();
                c.disconnect();

                System.out.println("Méthode POST :");

                c.connect("POST");
                //c.post("<cv_entry> <id>4</id> <firstname>George</firstname> <name>Leyeti</name> </cv_entry>");
                c.post("<cv_entry><id>" + tfId.getText() + "</id><firstname>" + tfFirstname.getText() + "</firstname><name>" + tfName.getText()
                        + "</name> </cv_entry>");

                c.displayResponse();
                c.disconnect();

            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });


        pane.add(postInformations);

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
