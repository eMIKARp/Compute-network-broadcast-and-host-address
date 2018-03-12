package compute.network.and.broadcast;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.P;
import javafx.scene.shape.Arc;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import static jdk.internal.org.objectweb.asm.commons.GeneratorAdapter.AND;
import static jdk.nashorn.internal.parser.TokenType.AND;

/**
 * Pro/g/ramming Challenges v 3.0
 * 005 - Given an IPv4 address and a subnet mask, compute the network, broadcast and first/last host addreses
 * @author Emil.Karpowicz
 */
public class Main extends JFrame 
{
    private static int sWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static int sHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    
    private static int fWidth = (int)(sWidth / 2);
    private static int fHeight = (int)(sHeight / 2);

    private CustomButton cButton = new CustomButton("Calculate");
    private CustomButton rButton = new CustomButton("Reset");
    private CustomButton eButton = new CustomButton("Exit");
    
    private CustomLabel lIp = new CustomLabel("Ip address");
    private CustomLabel lNetmask = new CustomLabel("Netmask");
    private CustomLabel lNetwork = new CustomLabel("Network");
    private CustomLabel lBroadcast = new CustomLabel("Broadcast");
    private CustomLabel lHostFirst = new CustomLabel("First host");
    private CustomLabel lHostLast = new CustomLabel("Last host");
   
    private JPanel mPanel = new JPanel();
    private JPanel inPanel = new JPanel();
    private JPanel cPanel = new JPanel();
    private JPanel outPanel = new JPanel();

    private JPanel labelOutPanel = new JPanel();
    private JPanel decOutPanel = new JPanel();
    private JPanel binOutPanel = new JPanel();
    
    private static MaskFormatter decimalMask = Main.createFormatter("###.###.###.###");
    private static MaskFormatter binaryMask = Main.createFormatter("########.########.########.########");    
    private static CustomTextField ipInputField = new CustomTextField(decimalMask);
    private static CustomTextField netmaskInputField = new CustomTextField(decimalMask);

    private static CustomTextField ipDecOutputField = new CustomTextField(decimalMask);
    private static CustomTextField netmaskDecOutputField = new CustomTextField(decimalMask);
    private static CustomTextField networkDecOutputField = new CustomTextField(decimalMask);
    private static CustomTextField broadcastDecOutputField = new CustomTextField(decimalMask);
    private static CustomTextField firsthostDecOutputField = new CustomTextField(decimalMask);
    private static CustomTextField lasthostDecOutputField = new CustomTextField(decimalMask);

    private static CustomTextField ipBinOutputField = new CustomTextField(binaryMask);
    private static CustomTextField netmaskBinOutputField = new CustomTextField(binaryMask);
    private static CustomTextField networkBinOutputField = new CustomTextField(binaryMask);
    private static CustomTextField broadcastBinOutputField = new CustomTextField(binaryMask);
    private static CustomTextField firsthostBinOutputField = new CustomTextField(binaryMask);
    private static CustomTextField lasthostBinOutputField = new CustomTextField(binaryMask);
   
    private static DecimalValueMatrix decimalValueMatrix = new DecimalValueMatrix();
    private static BinaryValueMatrix binaryValueMatrix = new BinaryValueMatrix();
    
    public Main() throws ParseException
    {
        try
        {
        
        this.setTitle("Compute network and broadcast and first / last host addreses");
        this.setBounds(sWidth / 4, sHeight / 4, fWidth, fHeight);
        this.setResizable(false);
        this.getContentPane().add(mPanel);
            mPanel.setLayout(new BorderLayout());
            mPanel.add(cPanel, BorderLayout.SOUTH);
                cPanel.setPreferredSize(new Dimension(fWidth, fHeight / 8));
                cPanel.setBorder(BorderFactory.createTitledBorder("Control Panel - choose your action"));
                cPanel.add(cButton);
                cPanel.add(rButton);
                cPanel.add(eButton);
            mPanel.add(inPanel, BorderLayout.NORTH);
                inPanel.setPreferredSize(new Dimension(fWidth, fHeight / 8));
                inPanel.setBorder(BorderFactory.createTitledBorder("Input panel - enter data to calculate network and broadcast addreses"));
                inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.LINE_AXIS));
                ipInputField.setBorder(BorderFactory.createTitledBorder("IP address"));
                ipInputField.setEditable(true);
                inPanel.add(ipInputField);
                netmaskInputField.setBorder(BorderFactory.createTitledBorder("Netmask address"));
                netmaskInputField.setEditable(true);
                inPanel.add(netmaskInputField);
            mPanel.add(outPanel, BorderLayout.CENTER);
                outPanel.setPreferredSize(new Dimension(fWidth, fHeight / 3));
                outPanel.setBorder(BorderFactory.createTitledBorder("Output panel - press \"Calculate\" to see results"));
                outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.LINE_AXIS));
                    labelOutPanel.setBorder(BorderFactory.createTitledBorder("Position"));
                        outPanel.add(labelOutPanel);    
                        labelOutPanel.setLayout(new BoxLayout(labelOutPanel, BoxLayout.PAGE_AXIS));
                        labelOutPanel.add(lIp);
                        labelOutPanel.add(lNetmask);
                        labelOutPanel.add(lNetwork);
                        labelOutPanel.add(lBroadcast);
                        labelOutPanel.add(lHostFirst);
                        labelOutPanel.add(lHostLast);
                    decOutPanel.setBorder(BorderFactory.createTitledBorder("Decimal version"));
                    outPanel.add(decOutPanel);
                        decOutPanel.setLayout(new BoxLayout(decOutPanel, BoxLayout.PAGE_AXIS));
                        decOutPanel.add(ipDecOutputField);
                        decOutPanel.add(netmaskDecOutputField);
                        decOutPanel.add(networkDecOutputField);
                        decOutPanel.add(broadcastDecOutputField);
                        decOutPanel.add(firsthostDecOutputField);
                        decOutPanel.add(lasthostDecOutputField);
                    binOutPanel.setBorder(BorderFactory.createTitledBorder("Binary version"));                    
                        binOutPanel.setLayout(new BoxLayout(binOutPanel, BoxLayout.PAGE_AXIS));
                        binOutPanel.add(ipBinOutputField);
                        binOutPanel.add(netmaskBinOutputField);
                        binOutPanel.add(networkBinOutputField);
                        binOutPanel.add(broadcastBinOutputField);
                        binOutPanel.add(firsthostBinOutputField);
                        binOutPanel.add(lasthostBinOutputField);
                    outPanel.add(binOutPanel);
                    
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
    
    public static void main(String[] args) throws ParseException 
    {
        new Main().setVisible(true);
    }
    
    public static int getFrameWidth()
    {
        return fWidth;
    }
    
    public static int getFrameHeight()
    {
        return fHeight;
    }
    
    public static void setIpAddress(String string)
    {
        ipInputField.setValue(string);
    }
    
    public static void setNetmask(String string)
    {
        netmaskInputField.setValue(string);
    }
    
    public static String getIpAddress()
    {
        return ipInputField.getText();
    }
    
    public static String getNetmask()
    {
        return netmaskInputField.getText();
    }
    
    public static DecimalValueMatrix getDecimalValueMatrix()
    {
        return decimalValueMatrix;
    }
    
    public static BinaryValueMatrix getBinaryValueMatrix()
    {
        return binaryValueMatrix;
    }
    
    public static String removeDots(String string)
    {
        String newString = string.replace(".", "");
        return newString;
    }
    
    public static String addDots(String string, int indextOfDots)
    {
        StringBuilder newString = new StringBuilder(string);
        for (int i = 0; i < 3; i++)
        {
            newString.insert(i * indextOfDots + indextOfDots + i, ".");
        }
                
        return newString.toString();
    }
    
    public static void setIpOutputFieldValue(String decValue, String binValue)
    {
        Main.ipDecOutputField.setValue(decValue);
        Main.ipBinOutputField.setValue(binValue);
    }
    
    public static void setNetmaskOutputFieldValue(String decValue, String binValue)
    {
        Main.netmaskDecOutputField.setValue(decValue);
        Main.netmaskBinOutputField.setValue(binValue);
    }
    
    public static void setNetworkOutputFieldValue(String decValue, String binValue)
    {
        Main.networkDecOutputField.setValue(decValue);
        Main.networkBinOutputField.setValue(binValue);
    }
    
    public static void setBroadcastOutputFieldValue(String decValue, String binValue)
    {
        Main.broadcastDecOutputField.setValue(decValue);
        Main.broadcastBinOutputField.setValue(binValue);
    }
    
    public static void setFirstHostOutputFieldValue(String decValue, String binValue)
    {
        Main.firsthostDecOutputField.setValue(decValue);
        Main.firsthostBinOutputField.setValue(binValue);
    }
    
    public static void setLastHostOutputFieldValue(String decValue, String binValue)
    {
        Main.lasthostDecOutputField.setValue(decValue);
        Main.lasthostBinOutputField.setValue(binValue);
    }
    
    public static MaskFormatter createFormatter (String mask)
    {
        MaskFormatter formatter = null;
        try
        {
            formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('0');
        }
        catch (ParseException e)    
        {
            e.getMessage();
        }
        
        return formatter;
    }
    
}

class CustomButton extends JButton
{
    public CustomButton(String bName)
    {
        super(bName);
        this.setPreferredSize(new Dimension(Main.getFrameWidth() / 4, Main.getFrameHeight() / 15));
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((JButton)e.getSource()).getText() == "Calculate") bCalculate();
                else if (((JButton)e.getSource()).getText() == "Reset") bReset();
                else if (((JButton)e.getSource()).getText() == "Exit") bExit();
            }
        });
    }
    
    public static void bExit()
    {
        System.exit(0);
    }
    
    public static void bReset()
    {
        Main.setIpAddress("000.000.000.000");
        Main.setNetmask("000.000.000.000");
        Main.setIpOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
        Main.setNetmaskOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
        Main.setNetworkOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
        Main.setBroadcastOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
        Main.setFirstHostOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
        Main.setLastHostOutputFieldValue("000.000.000.000", "00000000.00000000.00000000.00000000");
    }
    
    
    public static void bCalculate()
    {
        
        for (int j = 0; j < 4; j++)
        {
            int ipTemp = Integer.parseInt(Main.removeDots(Main.getIpAddress()).substring(j*3, j*3+3));
            int netmaskTemp = Integer.parseInt(Main.removeDots(Main.getNetmask()).substring(j*3, j*3+3));
            
            if (ipTemp > 255 || ipTemp < 0 || netmaskTemp > 255 || netmaskTemp < 0) 
            {
                JOptionPane.showConfirmDialog(null,"Wrowadzona wartość wykracza poza przedział 0-255. Wprowadź prawidłową wartość.","Nieprawidłowa wartość", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_OPTION);
                bReset();
            }
        }
        
        for (int i = 0; i < 4; i++)
        {
           ((Main.getDecimalValueMatrix())).setOctetsValue("Ip address", i, Main.removeDots(Main.getIpAddress()).substring(i*3, i*3+3));
           ((Main.getBinaryValueMatrix())).setOctetsValue("Ip address", i, Integer.toBinaryString(Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Ip address", i))));
           ((Main.getDecimalValueMatrix())).setOctetsValue("Netmask", i, Main.removeDots(Main.getNetmask()).substring(i*3, i*3+3));
           ((Main.getBinaryValueMatrix())).setOctetsValue("Netmask", i, Integer.toBinaryString(Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Netmask", i))));
        }
        
            Main.setIpOutputFieldValue((Main.getDecimalValueMatrix()).getOctetsValue("Ip address"), (Main.getBinaryValueMatrix()).getOctetsValue("Ip address"));
            Main.setNetmaskOutputFieldValue((Main.getDecimalValueMatrix()).getOctetsValue("Netmask"), (Main.getBinaryValueMatrix()).getOctetsValue("Netmask"));
            
        for (int i = 0; i < 4; i++)
        {
            String[] ipTempVariable = Main.getBinaryValueMatrix().getOctet("Ip address", i).split("");
            String[] netmaskTempVariable = Main.getBinaryValueMatrix().getOctet("Netmask", i).split("");
            StringBuilder tempVariable = new StringBuilder();
                    
            for (int j = 0; j < 8; j++)
            {
                tempVariable.append(Integer.parseInt(ipTempVariable[j]) * Integer.parseInt(netmaskTempVariable[j]));
            }
            
            (Main.getBinaryValueMatrix()).setOctetsValue("Network", i, tempVariable.toString());
            (Main.getDecimalValueMatrix()).setOctetsValue("Network", i, Integer.parseInt(tempVariable.toString(),2)+"");
        }
            Main.setNetworkOutputFieldValue((Main.getDecimalValueMatrix()).getOctetsValue("Network"), (Main.getBinaryValueMatrix()).getOctetsValue("Network"));
            
            String[] broadcastTempVariable = ((((Main.getBinaryValueMatrix()).getOctetsValue("Netmask").replace("1","x")).replace("0","1")).replace("x","0")).split("[.]");
            
            for (int i = 0; i < 4; i++)
            {
                (Main.getDecimalValueMatrix()).setOctetsValue("Broadcast", i, Integer.parseInt(broadcastTempVariable[i],2)+ Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Network",i))+"");
                (Main.getBinaryValueMatrix()).setOctetsValue("Broadcast", i, Integer.toBinaryString(Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Broadcast", i))));
            }
            
            Main.setBroadcastOutputFieldValue(((Main.getDecimalValueMatrix()).getOctetsValue("Broadcast")), ((Main.getBinaryValueMatrix()).getOctetsValue("Broadcast")));
     
        for (int i = 0; i < 4; i++)
        {
            if (i < 3)
            {
            (Main.getDecimalValueMatrix()).setOctetsValue("First host", i, (Main.getDecimalValueMatrix()).getOctet("Network", i));
            (Main.getDecimalValueMatrix()).setOctetsValue("Last host", i, (Main.getDecimalValueMatrix()).getOctet("Broadcast", i));
            }
            else
            {
            (Main.getDecimalValueMatrix()).setOctetsValue("First host", i, (Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Network", i))+1)+"");
            (Main.getDecimalValueMatrix()).setOctetsValue("Last host", i, (Integer.parseInt((Main.getDecimalValueMatrix()).getOctet("Broadcast", i))-1)+"");
            }
            
            (Main.getBinaryValueMatrix()).setOctetsValue("First host", i, Integer.toBinaryString(Integer.parseInt(((Main.getDecimalValueMatrix()).getOctet("First host", i)))));
            (Main.getBinaryValueMatrix()).setOctetsValue("Last host", i, Integer.toBinaryString(Integer.parseInt(((Main.getDecimalValueMatrix()).getOctet("Last host", i)))));
            
        }

        Main.setFirstHostOutputFieldValue((Main.getDecimalValueMatrix()).getOctetsValue("First host"),(Main.getBinaryValueMatrix()).getOctetsValue("First host"));
        Main.setLastHostOutputFieldValue((Main.getDecimalValueMatrix()).getOctetsValue("Last host"),(Main.getBinaryValueMatrix()).getOctetsValue("Last host"));
    }
}

class CustomTextField extends JFormattedTextField
{
    public CustomTextField(MaskFormatter maskFormatter)
    {
        super(maskFormatter);
        this.setPreferredSize(new Dimension(Main.getFrameWidth() / 4, Main.getFrameHeight() / 15));
        this.setEditable(false);
    }
}

class CustomLabel extends JTextField
{
    public CustomLabel(String lText)
    {
        super(lText);
        this.setEditable(false);
        this.setPreferredSize(new Dimension(Main.getFrameWidth() / 4, Main.getFrameHeight() / 15));
    }
}


class DecimalValueMatrix
{
    private static HashMap<String, ArrayList<String>> decValueMatrix = new HashMap<String, ArrayList<String>>();

    public DecimalValueMatrix() 
    {
        decValueMatrix.put("Ip address", new ArrayList<String>());
        decValueMatrix.put("Netmask", new ArrayList<String>());
        decValueMatrix.put("Network", new ArrayList<String>());
        decValueMatrix.put("Broadcast", new ArrayList<String>());
        decValueMatrix.put("First host", new ArrayList<String>());
        decValueMatrix.put("Last host", new ArrayList<String>());
    }
    
    public static String getOctet (String key, int index)
    {
        return ((decValueMatrix.get(key))).get(index);
    }
    
    public static String getOctetsValue (String key)
    {
        StringBuilder tempVariable = new StringBuilder();
        
        for (int i = 0; i < 4; i++)
            tempVariable.append(((decValueMatrix.get(key))).get(i));
        return Main.addDots(tempVariable.toString(), 3);
    }
    
    public static void setOctetsValue(String key, int octetIndex, String octectValue)
    {
        while (octectValue.length() < 3)
            octectValue = "0"+octectValue;
        ((decValueMatrix.get(key))).add(octetIndex, octectValue);
    }
}

class BinaryValueMatrix
{
    private static HashMap<String, ArrayList<String>> binValueMatrix = new HashMap<String, ArrayList<String>>();

    public BinaryValueMatrix() 
    {
        binValueMatrix.put("Ip address", new ArrayList<String>());
        binValueMatrix.put("Netmask", new ArrayList<String>());
        binValueMatrix.put("Network", new ArrayList<String>());
        binValueMatrix.put("Broadcast", new ArrayList<String>());
        binValueMatrix.put("First host", new ArrayList<String>());
        binValueMatrix.put("Last host", new ArrayList<String>());
    }
    
    public static String getOctetsValue (String key)
    {
        StringBuilder tempVariable = new StringBuilder();
        
        for (int i =0; i < 4; i++)
            tempVariable.append((binValueMatrix.get(key)).get(i));
        return Main.addDots(tempVariable.toString(), 8);
    }
    
    public static void setOctetsValue(String key, int octetIndex, String octectValue)
    {
        while (octectValue.length() < 8)
            octectValue = "0"+octectValue;
        ((binValueMatrix.get(key))).add(octetIndex, octectValue);
    }
    
    public static String getOctet (String key, int index)
    {
        return ((binValueMatrix.get(key))).get(index);
    }
}