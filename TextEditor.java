import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.*;
//This line was added by me
public class TextEditor extends JFrame implements ActionListener,ItemListener
{
    JButton btn_inc_size,btn_dec_size,change_color;
    JButton color_chooser,bold_btn,italic_btn;
    JButton close_save,close_dsave;
    boolean bolded=false,italic=false;
    JTextArea mtext;
    JScrollPane sp;
    Container cp;
    JLabel label_file_name;
    GraphicsEnvironment ge;
    JMenuBar menuBar;
    JMenu file,edit,format,more,styles;
    JMenuItem help,about,print,exit,bold_m,italic_m,inc_m,dec_m;
    String path;
    String file_name,data_to_write;
    JMenuItem new_file,save_file,save_as_file,open_file,custom,replace;
    JCheckBoxMenuItem dark_theme,word_wrap;
    JComboBox select_size,select_font,select_color;
    long line_cnt=1,col_cnt=1,char_cnt=0,dark_theme_cnt=0;
    JLabel col_label,row_label,char_label,count_title;
    static TextEditor te;
    int screen_height,screen_width;
    Color old_bgcolor,old_txtcolor,old_text_selection_color;
    public TextEditor(int show_cnt,String data_mtext)
    {
        cp=getContentPane();
        setTitle("TextEditor");

        if (show_cnt==1)
        {
            JDialog d=new JDialog();
            d.setLayout(new FlowLayout(FlowLayout.CENTER));
            ImageIcon i1=new ImageIcon("TextEditor_LOGO.jpg");
            JLabel l1=new JLabel(i1);
            JButton b1=new JButton("NEXT"); 
            JButton b2=new JButton("CLOSE"); 

            d.setUndecorated(true);
            d.setSize(525,265);
            d.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/3,Toolkit.getDefaultToolkit().getScreenSize().height/4); 
            d.add(l1);
            d.add(b1);
            d.add(b2);
            d.setVisible(true);
            b1.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    d.dispose();
                    te.setVisible(true);
                }
            });
            b2.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    d.dispose();
                    te.dispose();
                }
            });
        }
        else if (show_cnt==0)
        {
            setVisible(true);
            char_cnt=data_mtext.length();
        }

        screen_height=Toolkit.getDefaultToolkit().getScreenSize().height;
        screen_width=Toolkit.getDefaultToolkit().getScreenSize().width;
        //System.out.println(width+"X"+height);
        setExtendedState(MAXIMIZED_BOTH);
        setSize(screen_width-200,screen_height-200);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev)
            {
                if (file_name==null){
                    JDialog d=new JDialog(te,true);
                    d.setUndecorated(false);
                    d.setLocation(screen_width/3,screen_height/4);
                    d.setLayout(new FlowLayout(FlowLayout.CENTER));
                    JButton cancel=new JButton("Cancel");
                    JButton dont_save=new JButton("Don't Save");
                    JButton save=new JButton("Save");
                    d.add(new JLabel("File Note Saved."));
                    d.add(new JLabel("If you want to save it then click on 'SAVE' button..."));
                    d.add(save);
                    d.add(dont_save);
                    d.add(cancel);

                    dont_save.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            te.dispose();
                        }
                    });
                    cancel.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            String new_str=mtext.getText();
                            te.dispose();
                            new TextEditor(0,new_str);
                        }
                    });
                    save.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            FileDialog dialog=new FileDialog(d,"Select File",FileDialog.SAVE);
                            dialog.setVisible(true);
                            while (dialog.isVisible()==true)
                            {   
                                System.out.println("Visible");
                            }   
                            if (dialog.isVisible()==false)
                            {
                                path=dialog.getDirectory();
                                file_name=dialog.getFile();
                                data_to_write=mtext.getText();
                                try{
                                    if (file_name.contains(".txt")==false)
                                    {
                                        file_name+=".txt";
                                    }
                                    FileWriter fileWriter = new FileWriter(path+"/"+file_name);
                                    fileWriter.write(data_to_write);
                                    fileWriter.close();
                                    JDialog d_new=new JDialog(te,true);
                                    d_new.setTitle("Status Dialog");
                                    d_new.setLayout(new FlowLayout(FlowLayout.CENTER));
                                    d_new.setSize(250,120);
                                    d_new.setLocation(screen_width/3,screen_height/3);
                                    d_new.setResizable(false);
                                    d_new.add(new Label("File Saved Successfully..."));
                                    d_new.addWindowListener(new WindowAdapter(){
                                        public void windowClosing(WindowEvent ee)
                                        {
                                            d_new.dispose();
                                            te.dispose();
                                        }
                                    });
                                    d_new.setVisible(true);
                                    label_file_name.setText("File Name : "+file_name);
                                }
                                catch(Exception ex)
                                {
                                    System.out.println(ex.getMessage());
                                }                            
                            }
                        }
                    });
                    d.setResizable(false);
                    d.setSize(320,130);
                    d.setVisible(true);
                }
                else
                {
                    JDialog d=new JDialog(te,true);
                    d.setLocation(screen_width/3,screen_height/4);
                    d.setLayout(new FlowLayout(FlowLayout.LEFT));
                    d.add(new JLabel("Good Bye...Thank You For Using TextEditor"));
                    d.setSize(300,100);
                    d.setVisible(true);
                    while (d.isVisible()==true)
                    {

                    }
                    te.dispose();
                }
            }
        });

        label_file_name=new JLabel("File Name : NONE");
        menuBar=new JMenuBar();
        file=new JMenu("File");
        edit=new JMenu("Edit");
        format=new JMenu("Format");
        more=new JMenu("More");
        print=new JMenuItem("Print");
        exit=new JMenuItem("Exit");
        new_file=new JMenuItem("New");
        open_file=new JMenuItem("Open");
        save_file=new JMenuItem("Save");
        styles=new JMenu("Styles");
        bold_m=new JMenuItem("Bold");
        italic_m=new JMenuItem("Italic");
        inc_m=new JMenuItem("Increase Text Size");
        dec_m=new JMenuItem("Decrease Text Size");
        save_as_file=new JMenuItem("Save As");
        dark_theme=new JCheckBoxMenuItem("Dark Mode", false);
        custom=new JMenuItem("Custom");
        replace=new JMenuItem("Replace");
        word_wrap=new JCheckBoxMenuItem("Word Wrap",false);

        new_file.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        open_file.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        save_as_file.setAccelerator(KeyStroke.getKeyStroke('L',Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        exit.setAccelerator(KeyStroke.getKeyStroke('Q',Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        save_file.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        custom.setAccelerator(KeyStroke.getKeyStroke('M', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        word_wrap.setAccelerator(KeyStroke.getKeyStroke('W', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        replace.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        dark_theme.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        bold_m.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        italic_m.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        inc_m.setAccelerator(KeyStroke.getKeyStroke('.', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        dec_m.setAccelerator(KeyStroke.getKeyStroke(',', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        

        help=new JMenuItem("Help");
        help.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                String str="Menu : \n"+
                "    1. File Menu - \n"+
                "         a. New        -  To create new document.\n"+
                "         b. Open      -  To open existing file.\n"+
                "         c. Save       -  To save file.\n"+
                "         d. Save As  -  To again save file with different name.\n"+
                "         d. Exit          -  To directly close TextEditor.\n\n"+
                "    2. Edit Menu - \n"+
                "         a. Word Wrap - Wrap the contents on same screen.\n"+
                "         b. Replace    - Replace specified word with new one.\n\n"+
                "    3. Format Menu - \n"+
                "         a. Dark Mode - To set editor's dark mode.\n"+
                "         b. Custom      - To change TextEditor colors.\n"+
                "         c. Styles      - To give Styles to Text.\n"+
                "              i. Bold Text                   - To change TextEditor colors.\n"+
                "             ii. Italic Text                    - To change TextEditor colors.\n"+
                "             iii. Increase Text Size      - To Increase Text Size.\n"+
                "             iv. Decrease Text Size    - To Decrease Text Size.\n"+
                "    4. More - \n"+
                "         a. Help  - Show Guidelines of TextEditor options\n"+
                "         b. About - Provide application and developer information.\n\n"+
                "Tools : \n"+
                "    1. Size List             -   To select text size directly.\n"+
                "    2. Size Button (A)   -   To increase text size.\n"+                
                "    3. Size Button (a)   -   To decrease text size.\n"+
                "    4. Font List             -   To select font of text.\n"+
                "    5. Bold Button (B)   -   To set/remove bold effect of text.\n"+
                "    6. Italic Button (I)    -   To set/remove italic effect of text.";
                JTextArea area=new JTextArea();
                area.setText(str);
                area.setEditable(false);
                Font f=new Font("Arial",Font.PLAIN,14);
                area.setFont(f);

                JScrollPane sp=new JScrollPane(area);
                sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

                JDialog d_help=new JDialog(te);
                d_help.setTitle("Help Dialog");
                d_help.setModal(true);
                d_help.setLocation(screen_width/3,screen_height/4);
                d_help.setSize(440,500);
                d_help.setResizable(false);
                d_help.setLayout(new BorderLayout());
                d_help.add(sp,BorderLayout.CENTER);
                d_help.setVisible(true);
            }
        });
        
        about=new JMenuItem("About");
        about.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                JDialog d_about=new JDialog(te);
                d_about.setTitle("Dialog");
                d_about.setLocation(screen_width/3,screen_height/4);
                d_about.setModal(true);
                d_about.setSize(410,400);
                d_about.setResizable(false);
                JTabbedPane tb=new JTabbedPane();
                tb.addTab("About", new aboutSoftware());
                d_about.add(tb);
                d_about.setVisible(true);
            }
        });

        help.setAccelerator(KeyStroke.getKeyStroke('K', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
        about.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

        file.add(new_file);
        file.add(open_file);
        file.add(save_file);
        file.add(save_as_file);
        file.add(exit);
        edit.add(word_wrap);
        edit.add(replace);

        format.add(dark_theme);
        format.add(custom);
        format.add(styles);

        styles.add(bold_m);
        styles.add(italic_m);
        styles.add(inc_m);
        styles.add(dec_m);

        more.add(help);
        more.add(about);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(format);
        menuBar.add(more);
        
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                dispose();
            }
        });

        count_title=new JLabel("Counts - ");
        char_label=new JLabel("Total Chars : "+char_cnt);
        row_label=new JLabel("Total Lines Used : "+line_cnt);
        color_chooser=new JButton("COLOR");
        bold_btn=new JButton("B");
        italic_btn=new JButton("I");
        Font f1=new Font("Courier New",Font.BOLD,15);
        italic_btn.setFont(f1);
        btn_inc_size=new JButton("A");
        btn_dec_size=new JButton("a");
        change_color=new JButton("COLOR");
        mtext=new JTextArea(36,113);
        mtext.setText(data_mtext);

        select_font=new JComboBox();
        
        JPanel dp=new JPanel();
        dp.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dp.add(char_label);
        dp.add(row_label);

        
        ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
        Font font_list[]=ge.getAllFonts();

        select_font.addItem("---------------------------");
        for (int i=0;i<font_list.length;i++)
        {
            select_font.addItem(font_list[i].getFontName());
        }


        select_size=new JComboBox();

        select_size.addItem("----");
        for (int i=4;i<=100;i+=2)
        {
            select_size.addItem(i);            
        }


        select_color=new JComboBox();
        select_color.addItem("BLACK");
        select_color.addItem("WHITE");
        select_color.addItem("RED");
        select_color.addItem("YELLOW");
        select_color.addItem("GREEN");
        select_color.addItem("BLUE");
        select_color.addItem("MAGENTA");
        select_color.addItem("CYAN");
        select_color.addItem("PINK");

        select_color.setSelectedItem("BLACK");

        sp = new JScrollPane(mtext);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        Font f=mtext.getFont();
        String default_font=f.getFontName();
        int s=f.getSize();

        select_size.setSelectedItem(s);
        select_font.setSelectedItem(default_font);

        setJMenuBar(menuBar);
        setLayout(new BorderLayout());
        JPanel subPanel=new JPanel();
        subPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        subPanel.add(label_file_name);
        subPanel.add(select_size);
        subPanel.add(btn_inc_size);
        subPanel.add(btn_dec_size);
        subPanel.add(select_font);
        subPanel.add(bold_btn);
        subPanel.add(italic_btn);
        //cp.add(select_color);
        cp.add(subPanel,BorderLayout.NORTH);
        cp.add(sp,BorderLayout.CENTER);
        cp.add(dp,BorderLayout.SOUTH);
        //cp.add(count_title);
        //cp.add(char_label);
        //cp.add(row_label);
        //cp.add(col_label);

        mtext.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e)
            {
                
            }
            public void keyReleased(KeyEvent e)
            {
                String str=mtext.getText();
                // System.out.println(str);
                long line=1;
                long char_c=0;
                for (int i=0;i<str.length();i++)
                {
                    if (str.charAt(i)!=10)
                    {
                        char_c++;
                    }
                }
                for (int i=0;i<str.length();i++)
                {
                    if (str.charAt(i)==10)
                    {
                        line++;
                    }
                }

                char_cnt=char_c;
                line_cnt=line;
                //System.out.println("Char Cnt : "+char_cnt);
                //System.out.println("Line Used : "+line_cnt);

                char_label.setText("Total Chars : "+char_cnt);
                row_label.setText("Total Lines Used : "+line_cnt);
            }
        });
        
        btn_inc_size.addActionListener(this);
        btn_dec_size.addActionListener(this);
        select_size.addActionListener(this);
        select_font.addActionListener(this);
        bold_btn.addActionListener(this);
        italic_btn.addActionListener(this);
        select_color.addActionListener(this);
        color_chooser.addActionListener(this);
        open_file.addActionListener(this);
        save_file.addActionListener(this);
        save_as_file.addActionListener(this);
        new_file.addActionListener(this);
        help.addActionListener(this);
        word_wrap.addActionListener(this);
        replace.addActionListener(this);

        bold_m.addActionListener(this);
        italic_m.addActionListener(this);
        inc_m.addActionListener(this);
        dec_m.addActionListener(this);

        dark_theme.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if (dark_theme_cnt==0)
                {
                    old_bgcolor=mtext.getBackground();
                    old_txtcolor=mtext.getForeground();
                    old_text_selection_color=mtext.getSelectionColor();
                }
                if (dark_theme.isSelected()==true)
                {
                    Color c=new Color(0,14,45);
                    Color c1=new Color(148,209,219);
                    setBackground(c);
                    mtext.setBackground(c);
                    mtext.setForeground(Color.WHITE);
                    mtext.setSelectionColor(c1);
                    menuBar.setBackground(Color.LIGHT_GRAY);
                    dark_theme_cnt++;
                }
                else 
                {
                    mtext.setBackground(old_bgcolor);
                    mtext.setForeground(old_txtcolor);
                    mtext.setSelectionColor(old_text_selection_color);
                    Color c=new Color(235,228,233);
                    menuBar.setBackground(c);
                    dark_theme_cnt=0;
                }
            }
        });

        custom.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                JDialog d=new JDialog();
                d.setTitle("Custom Color Setting");
                d.add(new JLabel("TextArea Background Color    : "));
                d.setModal(true);
                d.setLocation(screen_width/3,screen_height/3);
                JButton b=new JButton("Color");
                JButton b1=new JButton("Color");
                d.add(b);
                d.add(new JLabel("TextArea Text Selection Color : "));
                d.add(b1);
                d.add(new JLabel("   TextArea Text Color                    : "));
                d.add(color_chooser);
                d.setLayout(new FlowLayout());
                b.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent en)
                    {
                        Color initialcolor=mtext.getBackground();    
                        Color color=JColorChooser.showDialog(d,"Select a color",initialcolor);    
                        mtext.setBackground(color);
                    }
                });
                b1.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e)
                    {
                        Color initialcolor=mtext.getSelectionColor();    
                        Color color=JColorChooser.showDialog(d,"Select a color",initialcolor);    
                        mtext.setSelectionColor(color);
                    }
                });
                d.setResizable(false);
                d.setSize(350,150);
                d.setVisible(true);
            }
        });
    }
    public static void main(String args[])
    {
        te=new TextEditor(1,"");
    }

    public void itemStateChanged(ItemEvent e)
    {

    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==word_wrap)
        {
            if (word_wrap.isSelected()==true)
            {   
                mtext.setLineWrap(true);
                mtext.setWrapStyleWord(true);
            }
            else
            {
                mtext.setLineWrap(false);
                mtext.setWrapStyleWord(false);
            }
        }
        else if (e.getSource()==replace)
        {
            JDialog d=new JDialog();
                d.setTitle("Custom Color Setting");
                d.add(new JLabel("Find What       : "));
                d.setModal(true);
                d.setLocation(screen_width/3,screen_height/3);
                JTextField tf_find=new JTextField(15);
                JTextField tf_replace=new JTextField(15);
                d.add(tf_find);
                d.add(new JLabel("Replace with : "));
                d.add(tf_replace);
                JButton b=new JButton("Replace");
                JLabel l1=new JLabel("");
                d.add(b);
                d.add(l1);
                d.setLayout(new FlowLayout());
                b.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent en)
                    {
                        String str=mtext.getText();
                        String find_str=tf_find.getText();
                        String replace_str=tf_replace.getText();

                        if (find_str.equals("")==false && replace_str.equals("")==false)
                        {
                            String old_str=mtext.getText();
                            str=str.replace(find_str,replace_str);
                            mtext.setText(str);
                            String new_str=mtext.getText();
                            if (old_str.equals(new_str)==true)
                            {
                                l1.setText(find_str+" Not Found...");
                            }
                            else
                            {
                                l1.setText(find_str+" Replaced...");
                            }
                        }
                        else
                        {
                            l1.setText("Fill all the fields...");
                        }
                    }
                });
                d.setResizable(false);
                d.setSize(280,150);
                d.setVisible(true);
        }
        if (e.getSource()==btn_inc_size || e.getSource()==inc_m)
        {
            Font f=mtext.getFont();
            int s=f.getSize();
            String fname=f.getFontName();            
            s+=2;
            if (s>100)
                s=100;
            Font fnew=new Font(fname,Font.PLAIN,s);
            select_size.setSelectedItem(s);
            mtext.setFont(fnew);
            
        }
        else if (e.getSource()==btn_dec_size || e.getSource()==dec_m)
        {
            Font f=mtext.getFont();
            int s=f.getSize();
            String fname=f.getFontName();            
            s-=2;
            if (s<4)
                s=4;
            Font fnew=new Font(fname,Font.PLAIN,s);
            select_size.setSelectedItem(s);
            mtext.setFont(fnew);
        }
        else if (e.getSource()==select_size)
        {
            String s=select_size.getSelectedItem().toString();
            if (s.equals("----")==false)
            {
                int size=Integer.parseInt(s);
                Font f=mtext.getFont();
                String fname=f.getFontName();            
                Font fnew=new Font(fname,Font.PLAIN,size);
                select_size.setSelectedItem(s);
                mtext.setFont(fnew);
            }
        }
        else if (e.getSource()==select_font)
        {
            String font_s=select_font.getSelectedItem().toString();
            Font f=mtext.getFont();
            int f_size=f.getSize();
            int style=f.getStyle();

            Font f_new=new Font(font_s,style,f_size);
            mtext.setFont(f_new);
        }
        else if (e.getSource()==select_color)
        {
            String str=select_color.getSelectedItem().toString();
            switch (str)
            {
                case "BLACK" : mtext.setForeground(Color.BLACK);break;
                case "WHITE" : mtext.setForeground(Color.WHITE);break;
                case "RED" : mtext.setForeground(Color.RED);break;
                case "YELLOW" : mtext.setForeground(Color.YELLOW);break;
                case "GREEN" : mtext.setForeground(Color.GREEN);break;
                case "BLUE" : mtext.setForeground(Color.BLUE);break;
                case "MAGENTA" : mtext.setForeground(Color.MAGENTA);break;
                case "CYAN" : mtext.setForeground(Color.CYAN);break;
                case "PINK" : mtext.setForeground(Color.PINK);break;

            }
        }
        else if (e.getSource()==save_file)
        {
            if (file_name==null)
            {
                FileDialog dialog=new FileDialog(te,"Select File",FileDialog.SAVE);
                dialog.setVisible(true);
                while (dialog.isVisible()==true)
                {   
                    System.out.println("Visible");
                }   
                if (dialog.isVisible()==false)
                {
                    path=dialog.getDirectory();
                    file_name=dialog.getFile();
                    data_to_write=mtext.getText();
                    try{
                        if (file_name.contains(".txt")==false)
                        {
                            file_name+=".txt";
                        }
                        FileWriter fileWriter = new FileWriter(path+"/"+file_name);
                        fileWriter.write(data_to_write);
                        fileWriter.close();
                        JDialog d_new=new JDialog(te,true);
                        d_new.setTitle("Status Dialog");
                        d_new.setLayout(new FlowLayout(FlowLayout.CENTER));
                        d_new.setSize(250,120);
                        d_new.setLocation(screen_width/3,screen_height/3);
                        d_new.setResizable(false);
                        d_new.add(new Label("File Saved Successfully..."));
                        d_new.addWindowListener(new WindowAdapter(){
                            public void windowClosing(WindowEvent ee)
                            {
                                d_new.dispose();
                            }
                        });
                        d_new.setVisible(true);
                        label_file_name.setText("File Name : "+file_name);
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex.getMessage());
                    }
                }
            }
            else
            {
                try{
                    data_to_write=mtext.getText();
                    FileWriter fileWriter = new FileWriter(path+"/"+file_name);
                    fileWriter.write(data_to_write);
                    fileWriter.close();

                    JDialog d_new=new JDialog(te,true);
                        d_new.setTitle("Status Dialog");
                        d_new.setLayout(new FlowLayout(FlowLayout.CENTER));
                        d_new.setSize(250,120);
                        d_new.setLocation(screen_width/3,screen_height/3);
                        d_new.setResizable(false);
                        d_new.add(new Label("File Saved.."));
                        d_new.addWindowListener(new WindowAdapter(){
                            public void windowClosing(WindowEvent ee)
                            {
                                d_new.dispose();
                            }
                        });
                        d_new.setVisible(true);
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else if (e.getSource()==save_as_file)
        {
            FileDialog dialog=new FileDialog(te,"Select File",FileDialog.SAVE);
            dialog.setVisible(true);
            while (dialog.isVisible()==true)
            {   
                System.out.println("Visible");
            }   
            if (dialog.isVisible()==false)
            {
                String path=dialog.getDirectory();
                file_name=dialog.getFile();
                data_to_write=mtext.getText();
                try{
                    if (file_name.contains(".txt")==false)
                    {
                        file_name+=".txt";
                    }
                    FileWriter fileWriter = new FileWriter(path+"/"+file_name);
                    fileWriter.write(data_to_write);
                    fileWriter.close();
                    JDialog d_new=new JDialog(te,true);
                    d_new.setTitle("Status Dialog");
                    d_new.setLayout(new FlowLayout(FlowLayout.CENTER));
                    d_new.setSize(250,120);
                    d_new.setLocation(screen_width/3,screen_height/3);
                    d_new.setResizable(false);
                    d_new.add(new Label("File Saved Successfully..."));
                    d_new.addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent ee)
                        {
                            d_new.dispose();
                        }
                    });
                    d_new.setVisible(true);
                    label_file_name.setText("File Name : "+file_name);
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else if (e.getSource()==open_file)
        {
            FileDialog dialog=new FileDialog(te,"Select File",FileDialog.LOAD);
            dialog.setVisible(true);
            while (dialog.isVisible()==true)
            {   
                System.out.println("Visible");
            }   
            if (dialog.isVisible()==false)
            {
                String path=dialog.getDirectory();
                file_name=dialog.getFile();
                data_to_write="";
                try{
                    FileReader fReader=new FileReader(path+"/"+file_name);
                    int i;
                    while((i=fReader.read())!=-1)    
                    {
                        data_to_write+=(char)i;
                        // System.out.print((char)i);    
                    }
                    fReader.close();    
                    mtext.setText(data_to_write);
                    label_file_name.setText("File Name : "+file_name);
                }
                catch(Exception ex)
                {
                    System.out.println(ex.getMessage());
                }
            }
        }
        else if (e.getSource()==color_chooser){
            Color initialcolor=mtext.getForeground();    
            Color color=JColorChooser.showDialog(this,"Select a color",initialcolor);    
            mtext.setForeground(color);
        }
        else if (e.getSource()==new_file)
        {
            dispose();
            new TextEditor(0,"");
        }
        else if (e.getSource()==bold_btn || e.getSource()==bold_m)
        {
            Font f=mtext.getFont();
            int style=f.getStyle();

            if (bolded==false)
            {
                if (italic==true)
                    mtext.setFont(f.deriveFont(Font.BOLD|Font.ITALIC));
                else
                    mtext.setFont(f.deriveFont(Font.BOLD));

                bolded=true;
            }
            else if (bolded==true)
            {
                if (italic==true)
                    mtext.setFont(f.deriveFont(Font.ITALIC));
                else
                    mtext.setFont(f.deriveFont(Font.PLAIN));

                bolded=false;
            }
        }
        else if (e.getSource()==italic_btn || e.getSource()==italic_m)
        {
            Font f=mtext.getFont();
            int style=f.getStyle();
            if (italic==false)
            {
                if (bolded==true)
                    mtext.setFont(f.deriveFont(Font.ITALIC|Font.BOLD));
                else
                    mtext.setFont(f.deriveFont(Font.ITALIC));

                italic=true;
            }
            else if (italic==true)
            {
                if (bolded==true)
                    mtext.setFont(f.deriveFont(Font.BOLD));
                else
                    mtext.setFont(f.deriveFont(Font.PLAIN));

                italic=false;
            }
        }
    }
}

class aboutSoftware extends JPanel
{
    public aboutSoftware()
    {
        String str="TextEditor \n It is JAVA based application with interactive and user friendly\n interface. This editor has different functionalities"
        +" means it has \n all the functionalities provided by nornal text editor and also\n has its own"+
        " advanced and unique functionalities. Some of the \n functionalities are : \n"+
        "    1. You can use it in normal and dark mode"+
        "\n    2. You can save your documentation whereever you want to\n        save.\n"+
        "    3. You can open saved file in TextEditor and also in any\n        other editor.\n"+
        "    4. You can change the foreground and background color\n        of TextEditor.\n"+
        "    5. You can format your text by using different formating\n        tools like bold, italic, fonts, and many more.\n"+
        "    6. You can choose any color by using color picker provided\n        by TextEditor application.\n"+
        "Because all of above functionalities any one can use our\nTextEditor easily.";
        JTextArea area=new JTextArea();
        Font f=new Font("Arial",Font.PLAIN,14);
        area.setFont(f);
        setLayout(new BorderLayout());
        area.setEditable(false);
        area.setText(str);
        area.setAutoscrolls(false);
        add(area,BorderLayout.CENTER);
    }
}
