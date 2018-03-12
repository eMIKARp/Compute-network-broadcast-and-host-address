package compute.network.and.broadcast;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.scene.input.KeyCode.P;
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
    
    public static String getIpAddress()
    {
        return ipInputField.getText();
    }
    
    public static String getNetmask()
    {
        return netmaskInputField.getText();
    }
    
    public static String convertToBinary(String text)
    {
        String[] decTempTable = text.split("[.]");
        String[] binTempTable = new String[decTempTable.length];
        StringBuilder textConvToBinary = new StringBuilder();
        String tempString = "";
        
        for (int i = 0; i < decTempTable.length; i++)
        {
            binTempTable[i] = Integer.toBinaryString(Integer.parseInt(decTempTable[i]));
            if (binTempTable[i].length() < 8)
            {
                int x = 8 - binTempTable[i].length();
                for (int j = 0; j < x; j++ )
                    binTempTable[i] = "0"+binTempTable[i];
            }
            if (i < decTempTable.length-1) textConvToBinary.append(binTempTable[i]+".");
            else textConvToBinary.append(binTempTable[i]);
            System.out.println(binTempTable[i]);
        }
        
        return textConvToBinary.toString();
    }
    
    public static void setIpAddressOutputField(String text)
    {
        ipDecOutputField.setText(text);
        ipBinOutputField.setText(Main.convertToBinary(ipDecOutputField.getText()));
       
    }
    
    public static void setNetmaskOutputField(String text)
    {
        netmaskDecOutputField.setText(text);
        netmaskBinOutputField.setText(Main.convertToBinary(netmaskDecOutputField.getText()));
    }
    
    public static void setBroadcaskOutputField()
    {
        String netmask = netmaskBinOutputField.getText();
            String[] netmaskBinOktets = netmask.split("[.]");
            String[] assistTableNr1; 
            StringBuilder assistString = new StringBuilder(); 
            StringBuilder broadBinOut = new StringBuilder();
            StringBuilder broadDecOut = new StringBuilder();
        
        for (int i=0; i < 4; i++)
        {
            assistTableNr1 = netmaskBinOktets[i].split("");
            assistString.delete(0,8);
            for (int j = 0; j < 8 ; j++)
                {
                    if (assistTableNr1[j].equals("1")) assistString.append("0");
                    else assistString.append("1");
                }
            System.out.println(assistString.toString());
            if (i < 3)
            {
               broadBinOut.append(assistString.toString()+".");
               broadDecOut.append(Integer.parseInt(assistString.toString(),2)+".");
            }
            else
            {
               broadBinOut.append(assistString.toString());
               broadDecOut.append(Integer.parseInt(assistString.toString(),2));
            }
        }
        
        broadcastBinOutputField.setText(broadBinOut.toString());
        broadcastDecOutputField.setText(broadDecOut.toString());
    }
    
    public static void setNetworkOutputField()
    {
        String ip = ipBinOutputField.getText();
        String netmask = netmaskBinOutputField.getText();
        String[] ipOktets = ip.split("[.]");
        String[] netmaskOktets = netmask.split("[.]");
        String[] networkOktets = new String[4];
        StringBuilder netBinOut = new StringBuilder();
        StringBuilder netDecOut = new StringBuilder();
        
        for (int i = 0; i < 4; i++)
        {
            String[] x1 = ipOktets[i].split("");
            String[] x2 = netmaskOktets[i].split("");
            StringBuilder z = new StringBuilder();
            int y = 0;
            
            for (int j = 0; j < 8; j++)
            {
                z.append((Integer.parseInt(x1[j]) * Integer.parseInt(x2[j])+""));
            }   
            
            y = Integer.parseInt(z+"",2);
            
            if (i < 3) 
            {
                netBinOut.append(z.toString()+".");
                netDecOut.append(y+".");
            }
            else 
            {
                netBinOut.append(z.toString());
                netDecOut.append(y);
            }
        }
        
        networkBinOutputField.setText(netBinOut.toString());
        networkDecOutputField.setText(netDecOut.toString());
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
        System.out.println("Reset");
    }
    
    
    public static void bCalculate()
    {
        System.out.println("Calculate");
        
        String IpAddress = Main.getIpAddress();
        String netmask = Main.getNetmask();
        
        String[] decIpTable = IpAddress.split("[.]");
        String[] decNetmaskTable = netmask.split("[.]");
        
        int[] binTempTable = new int[decIpTable.length];
        int[] decTempTable = new int[decIpTable.length];

            for (int i = 0; i <4; i++)
            {
                binTempTable[i] = Integer.parseInt(decIpTable[i]) & Integer.parseInt(decNetmaskTable[i]);
            }
        
        Main.setIpAddressOutputField(Main.getIpAddress());
        Main.setNetmaskOutputField(Main.getNetmask());
        Main.setNetworkOutputField();
        Main.setBroadcaskOutputField();
            
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