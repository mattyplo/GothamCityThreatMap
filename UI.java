import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class UI extends Frame {

    private static boolean gChecked = true;
    private static boolean bChecked = true;
    private static boolean pChecked = true;

    private static JFrame genWindow(int w_width, int w_height, String title) {

        // Calculate coordinates to center window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double s_width = screenSize.getWidth();
        double s_height = screenSize.getHeight();
        int x_origin = (int)Math.floor((s_width-w_width)/2);
        int y_origin = (int)Math.floor((s_height-w_height)/2);

        // Window
        JFrame window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(w_width, w_height);
        window.setLocation(x_origin, y_origin);

        return window;

    }

    public static void genMain(BatMap map, ArrayList<Person> people) {

        // Random
        Random rand = new Random(System.currentTimeMillis());

        // Generate main
        JFrame main = genWindow(700, 527, "Batman");
        main.setBackground(Color.white);

        // Content panel
        JPanel content = new JPanel();
        // content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setLayout(null);
        content.setBackground(Color.white);
        main.add(content);

        // Add Title
        Label title = new Label("Gotham City Threat Map");
        title.setBounds(30, 28, 600, 30);
        title.setFont(new Font("Helvetica", Font.PLAIN, 30));
        content.add(title);

        // display text area
        JTextArea output = new JTextArea("Welcome");
        output.setBounds(450, 250, 225, 230);
        output.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        content.add(output);
        
        // Create Table 
        final int SIZE = 20;
        ArrayList<JButton> locations = new ArrayList<JButton>();
        for(int i = 0; i < 20; i++) { // row
            for(int j = 0; j < 20; j++) { // column
                JButton loc = new JButton();
                final int x = j;
                final int y = i;
                loc.setOpaque(true);
                loc.setBounds(30+j*SIZE, 80+i*SIZE, SIZE, SIZE);
                loc.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                loc.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        output.setText("");
                        output.append("Location " + (x+1) + ", " + (y+1) + "\n");
                        // System.out.printf("Button %d, %d\n", x, y);
                        ArrayList<String> names = map.getLocPeople(x, y);
                        // System.out.printf("Threat Level: %d\n", map.getLocThreat(x, y));
                        output.append("Threat Level: " + map.getLocThreat(x, y) + "\n");
                        if(names.size() == 0) {
                            // System.out.println("No people at this location");
                            output.append("No people at this location");
                        } else {
                            // System.out.printf("Names:\n");
                            output.append("People:\n");
                            for(int i = 0; i < names.size(); i++) {
                                String name = names.get(i);
                                // System.out.printf("%s\n", name);
                                output.append(name + "\n");
                            }
                        }
                    }
                });   
                locations.add(loc);
                content.add(loc);
            }
        }

        // Create update button
        JButton updater = new JButton("Update");
        updater.setBounds(450, 85, 100, 40);
        updater.setOpaque(true);
        updater.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        updater.setFont(new Font("Helvetica", Font.PLAIN, 20));
        updater.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // randomize people movement
                for(int i = 0; i < people.size(); i++) {
                    people.get(i).updateLocation(rand.nextInt(3), rand.nextInt(2));
                }
                // reload people on map
                map.reloadPeople(people);
                // re-render
                recolorGrid(locations, map);
                // filter
                filterMap(people, map, locations);
            }
        });
        content.add(updater);

        // color according to threat level
        recolorGrid(locations, map);

        // Create checkboxes
        JCheckBox goodGuys = new JCheckBox("Good Guys", true);
        JCheckBox badGuys = new JCheckBox("Bad Guys", true);
        JCheckBox pedestrians = new JCheckBox("Pedestrians", true);
        goodGuys.setBounds(450, 150, 200, 20);
        badGuys.setBounds(450, 180, 200, 20);
        pedestrians.setBounds(450, 210, 200, 20);
        content.add(goodGuys);
        content.add(badGuys);
        content.add(pedestrians);

        // add event listeners for checkboxes
        // selected = 1, deselected = 2
        goodGuys.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                setGChecked(state == 1);
                filterMap(people, map, locations);
            }
        });
        badGuys.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                setBChecked(state == 1);
                filterMap(people, map, locations);
            }
        });
        pedestrians.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                int state = e.getStateChange();
                setPChecked(state == 1);
                filterMap(people, map, locations);
            }
        });

        // show main
        main.setVisible(true);

    }

    public static void setGChecked(boolean state) {
        gChecked = state;
    }

    public static void setBChecked(boolean state) {
        bChecked = state;
    }

    public static void setPChecked(boolean state) {
        pChecked = state;
    }

    public static void filterMap(ArrayList<Person> people, BatMap map, ArrayList<JButton> locations) {
        ArrayList<Person> peopleChecked = new ArrayList<Person>();
        for(int i = 0; i < people.size(); i++) {
            if(gChecked && people.get(i).whoAmI().equals("good")) {
                peopleChecked.add(people.get(i));
            }
            if(bChecked && people.get(i).whoAmI().equals("bad")) {
                peopleChecked.add(people.get(i));
            }
            if(pChecked && people.get(i).whoAmI().equals("")) {
                peopleChecked.add(people.get(i));
            }
        }
        map.reloadPeople(peopleChecked);
        recolorGrid(locations, map);
    }

    public static void recolorGrid(ArrayList<JButton> locations, BatMap map) {
        final int multiplier = 15;
        for(int i = 0; i < 20; i++) { // rows
            for(int j = 0; j < 20; j++) { // columns
                int id = i*20 + j;
                int threat = map.getLocThreat(j, i);
                if(threat < 0) {
                    int b = 0 - multiplier * threat;
                    if(b > 255) b = 255;
                    locations.get(id).setBackground(new Color(255, 255, b));
                } else if(threat > 0) {
                    int g = 255 - multiplier * threat;
                    if(g < 0) g = 0;
                    locations.get(id).setBackground(new Color(255, g, 0));
                } else {
                    locations.get(id).setBackground(new Color(255, 255, 0));
                }
            }
        }
    }

    public static void updateGrid(ArrayList<JButton> locations, BatMap map, JFrame main) throws InterruptedException {
    // public static void updateGrid(ArrayList<JButton> locations, BatMap map, JFrame main) {
        while(true) {
            for(int i = 0; i < 400; i++) {
                Color c = locations.get(i).getBackground();
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                r = r - 10;
                g = g - 10;
                b = b - 10;
                if(r < 0) r = 0;
                if(g < 0) g = 0;
                if(b < 0) b = 0;
                locations.get(i).setBackground(new Color(r, g, b));
            }
            main.setVisible(true);
            Thread.sleep(1000);
        }
    }

}
