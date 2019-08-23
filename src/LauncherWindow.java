import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPasswordField;

public class LauncherWindow {

	private JFrame frmLauncherWindow;
	private JTextField txtWPort;
	private JTextField txtHPort;
	private JTextField txtMapNumber;
	private JTextField txtDemoName;
	private JTextField txtClientPath;
	private JTextField txtArguments;
	private JTextField txtExec;
	private JTextField txtName;
	private JTextField txtIP;
	private JTextField txtServerName;
	private JTextField txtRoomID;
	private JTextField txtPointLimit;
	private JTextField txtTimeLimit;
	private JTextField txtMaxPlayers;
	private JTextField txtWorkingDir;
	private JTextField txtConfigFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LauncherWindow window = new LauncherWindow();
					window.frmLauncherWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private String launcherConfFileName = "";
	private int themeIndex = 0;
	private FileInOut SaveLoadSys = new FileInOut();
	private ImageTransformer iT = new ImageTransformer();
	
	private File currentFile;
	
	private int[][] colorRGBs = {
			{0,0,0},
			{247,247,247},
			{215,215,215},
			{151,151,151},
			{23,23,23},
			{128,214,255},
			{0,191,191},
			{119,119,187},
			{0,0,255},
			{255,199,167},
			{223,147,99},
			{231,143,143},
			{183,111,183},
			{223,0,223},
			{225,117,0},
			{126,32,0},
			{159,137,113},
			{119,79,43},
			{255,0,0},
			{111,0,0},
			{0,223,0},
			{90,192,60},
			{173,200,128},
			{143,143,0},
			{255,255,0},
			{215,187,67}
	};
	
	/// filters
	private FileNameExtensionFilter CompatibleFileFilter = new FileNameExtensionFilter("All compatible files", "PK3", "WAD", "SOC", "Lua");
	private FileNameExtensionFilter Pk3Filter = new FileNameExtensionFilter("QUAKE PK3 (*.pk3)", "PK3");
	private FileNameExtensionFilter doomWadFilter = new FileNameExtensionFilter("Doom WAD (*.wad)", "WAD");
	private FileNameExtensionFilter srb2SocFilter = new FileNameExtensionFilter("Sonic Object Configuration (*.soc)", "SOC");
	private FileNameExtensionFilter luaFilter = new FileNameExtensionFilter("Lua script (*.Lua)", "Lua");
	private FileNameExtensionFilter launcherConf = new FileNameExtensionFilter("Config file (*.cfg)", "cfg");
	private FileNameExtensionFilter srb2FileList = new FileNameExtensionFilter("SRB2 file list (*.sfl)", "sfl");
	private FileNameExtensionFilter demoFileFilter = new FileNameExtensionFilter("Demo files (*.lmp)", "lmp");
	
	private String workingDirectory = Paths.get(".").toAbsolutePath().normalize().toString();
	private File configFile = new File(workingDirectory+File.separator+"hcLaunchConfig.cfg");
	
	private int loadConfFromFile;
	private String defaultConf;
	private JPasswordField txtServerPassword;
	
	private void initialize() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		int currentThemeIndex=0;
		FileReader fr;
		try {
			fr = new FileReader(configFile);
			BufferedReader br = new BufferedReader(fr);
			
			String curLine;
			while((curLine=br.readLine())!=null) {
				if(curLine.startsWith("ThemeIndex")) {
					UIManager.setLookAndFeel(UIManager.getInstalledLookAndFeels()[Integer.parseInt(curLine.substring(curLine.indexOf("=")+1))].getClassName());
					currentThemeIndex=Integer.parseInt(curLine.substring(curLine.indexOf("=")+1));
				}
				if(curLine.startsWith("DefaultConf")) {
					defaultConf=curLine.substring(curLine.indexOf("=")+1);
				}
				if(curLine.startsWith("LoadConfigFromFile")) {
					if(curLine.substring(curLine.indexOf("=")+1).contains("true"))
						loadConfFromFile=1;
					else if(curLine.substring(curLine.indexOf("=")+1).contains("false"))
						loadConfFromFile=0;
					else
						loadConfFromFile=Integer.parseInt(curLine.substring(curLine.indexOf("=")+1));
				}
			}
			
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}catch(ArrayIndexOutOfBoundsException e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				currentThemeIndex=999;
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (InstantiationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (UnsupportedLookAndFeelException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}
		
		
		
		String OS = System.getProperty("os.name").toLowerCase();
		//saveConf(loadLaunchConfFromFile,launcherConfFileName,themeIndex);
		
		
		frmLauncherWindow = new JFrame();
		frmLauncherWindow.setResizable(false);
		frmLauncherWindow.setVisible(true);
		
		frmLauncherWindow.setTitle("SRB2 Launcher");
		frmLauncherWindow.setBounds(100, 100, 434, 494);
		if(UIManager.getLookAndFeel().getName().contains("GTK")) {
			frmLauncherWindow.setBounds(100, 100, 453, 506);
		}
		frmLauncherWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLauncherWindow.getContentPane().setLayout(null);
		
		JTabbedPane tbpMain = new JTabbedPane(JTabbedPane.TOP);
		tbpMain.setBounds(0, 0, frmLauncherWindow.getWidth()-2, frmLauncherWindow.getHeight()-92);
		frmLauncherWindow.getContentPane().add(tbpMain);
		
		frmLauncherWindow.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				tbpMain.setBounds(0, 0, frmLauncherWindow.getWidth()-2, frmLauncherWindow.getHeight()-92);
				System.out.println(frmLauncherWindow.getWidth());
			}
		});
		
		JPanel tabMain = new JPanel();
		tbpMain.addTab("Main", null, tabMain, null);
		tabMain.setLayout(null);
		
		JTabbedPane tbpMainSettings = new JTabbedPane(JTabbedPane.TOP);
		tbpMainSettings.setBounds(0, 0, tbpMain.getWidth()-1, tbpMain.getHeight()-26);
		if(UIManager.getLookAndFeel().getName().contains("GTK"))
			tbpMainSettings.setBounds(-10, -2, tbpMain.getWidth()-1, tbpMain.getHeight()-26);
		tabMain.add(tbpMainSettings);
		
		JPanel tabEngine = new JPanel();
		tbpMainSettings.addTab("Engine", null, tabEngine, null);
		tabEngine.setLayout(null);
		
		JLabel lblRenderer = new JLabel("Renderer:");
		lblRenderer.setBounds(12, 89, 83, 14);
		tabEngine.add(lblRenderer);
		
		JComboBox<String> cmbRenderer = new JComboBox<>();
		cmbRenderer.setBounds(100, 85, 314, 23);
		tabEngine.add(cmbRenderer);
		cmbRenderer.setModel(new DefaultComboBoxModel<>(new String[] {"Software", "OpenGL"}));
		
		JCheckBox chckbxWindowedMode = new JCheckBox("Windowed mode");
		chckbxWindowedMode.setBounds(8, 42, 148, 23);
		tabEngine.add(chckbxWindowedMode);
		
		JLabel lblSoundMode = new JLabel("Music:");
		lblSoundMode.setBounds(12, 124, 55, 14);
		tabEngine.add(lblSoundMode);
		
		JComboBox<String> cmbMusicMode = new JComboBox<>();
		cmbMusicMode.setBounds(100, 120, 314, 23);
		tabEngine.add(cmbMusicMode);
		cmbMusicMode.setModel(new DefaultComboBoxModel<>(new String[] {"Digital", "Midi", "Use a CD", "Off"}));
		
		JCheckBox chckbxDisableSoundEffects = new JCheckBox("Disable sound effects");
		chckbxDisableSoundEffects.setBounds(8, 155, 172, 23);
		tabEngine.add(chckbxDisableSoundEffects);
		
		txtHPort = new JTextField();
		txtHPort.setBounds(284, 12, 130, 22);
		tabEngine.add(txtHPort);
		txtHPort.setEnabled(false);
		txtHPort.setText("480");
		txtHPort.setColumns(10);
		
		JLabel lblX = new JLabel("x");
		lblX.setBounds(264, 16, 12, 14);
		tabEngine.add(lblX);
		
		txtWPort = new JTextField();
		txtWPort.setBounds(120, 12, 130, 22);
		tabEngine.add(txtWPort);
		txtWPort.setEnabled(false);
		txtWPort.setText("640");
		txtWPort.setColumns(10);
		
		JCheckBox chckbxResolution = new JCheckBox("Resolution:");
		chckbxResolution.setBounds(8, 11, 102, 23);
		tabEngine.add(chckbxResolution);
		
		JLabel lblClientPath = new JLabel("Client path:");
		lblClientPath.setBounds(12, 206, 102, 14);
		tabEngine.add(lblClientPath);
		
		txtClientPath = new JTextField();
		if((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ))
			txtClientPath.setText("lsdl2srb2");
		else if (OS.indexOf("win")>=0)
			txtClientPath.setText("srb2win.exe");
		txtClientPath.setBounds(170, 202, 208, 23);
		tabEngine.add(txtClientPath);
		txtClientPath.setColumns(10);
		
		JButton btnClientBrowse = new JButton("...");
		btnClientBrowse.setBounds(390, 202, 24, 23);
		tabEngine.add(btnClientBrowse);
		
		txtArguments = new JTextField();
		txtArguments.setBounds(170, 237, 244, 23);
		tabEngine.add(txtArguments);
		txtArguments.setColumns(10);
		
		JLabel lblCustomArguments = new JLabel("Custom arguments:");
		lblCustomArguments.setBounds(12, 241, 154, 14);
		tabEngine.add(lblCustomArguments);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(8, 73, 406, 4);
		tabEngine.add(separator_5);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(8, 186, 406, 4);
		tabEngine.add(separator_7);
		
		JPanel tabGeneral = new JPanel();
		tbpMainSettings.addTab("Player", null, tabGeneral, null);
		tabGeneral.setLayout(null);
		
		txtName = new JTextField();
		txtName.setEnabled(false);
		txtName.setBounds(113, 12, 187, 24);
		tabGeneral.add(txtName);
		txtName.setText("Player");
		txtName.setColumns(10);
		
		JCheckBox lblName = new JCheckBox("Name:");
		lblName.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtName.setEnabled(lblName.isSelected());
			}
		});
		lblName.setBounds(8, 11, 89, 24);
		tabGeneral.add(lblName);
		
		JComboBox<String> cmbSkin = new JComboBox<>();
		
		cmbSkin.setBounds(113, 48, 187, 23);
		tabGeneral.add(cmbSkin);
		cmbSkin.setEnabled(false);
		cmbSkin.setEditable(true);
		cmbSkin.setModel(new DefaultComboBoxModel<>(new String[] {"Sonic", "Tails", "Knuckles"}));
		
		JComboBox<String> cmbColor = new JComboBox<>();
		cmbColor.setBounds(113, 83, 187, 23);
		tabGeneral.add(cmbColor);
		cmbColor.setEnabled(false);
		cmbColor.setModel(new DefaultComboBoxModel<>(new String[] {"None", "White", "Silver", "Grey", "Black", "Cyan", "Teal", "Steel blue", "Blue", "Peach", "Tan", "Pink", "Lavender", "Purple", "Orange", "Rosewood", "Beige", "Brown", "Red", "Dark red", "Neon green", "Green", "Zim", "Olive", "Yellow", "Gold"}));
		
		JCheckBox chckbxSkin = new JCheckBox("Set skin:");
		chckbxSkin.setBounds(8, 48, 97, 23);
		tabGeneral.add(chckbxSkin);
		
		JCheckBox chckbxColor = new JCheckBox("Set color:");
		chckbxColor.setBounds(8, 83, 97, 23);
		tabGeneral.add(chckbxColor);
		
		JLabel lblIcon = new JLabel("");
		
		lblIcon.setBounds(318, 12, 96, 96);

		Image tstImg = new ImageIcon(this.getClass().getResource("/LIV"+cmbSkin.getSelectedItem().toString().toUpperCase()+".png")).getImage().getScaledInstance(96, 96, java.awt.Image.SCALE_SMOOTH);
		// Need to "load" the image before it can be converted to a buffered image
		lblIcon.setIcon(new ImageIcon(tstImg));
		BufferedImage iconIcon = iT.colorImage(iT.toBufferedImage(tstImg),colorRGBs[cmbColor.getSelectedIndex()][0],colorRGBs[cmbColor.getSelectedIndex()][1],colorRGBs[cmbColor.getSelectedIndex()][2]);
		lblIcon.setIcon(new ImageIcon(iconIcon));
		
		ActionListener changeSkin = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Image tstImg = new ImageIcon(this.getClass().getResource("/LIV"+cmbSkin.getSelectedItem().toString().toUpperCase()+".png")).getImage().getScaledInstance(lblIcon.getWidth(), lblIcon.getHeight(), java.awt.Image.SCALE_SMOOTH);
				// Need to "load" the image before it can be converted to a buffered image
				lblIcon.setIcon(new ImageIcon(tstImg));
				BufferedImage iconIcon = iT.colorImage(iT.toBufferedImage(tstImg),colorRGBs[cmbColor.getSelectedIndex()][0],colorRGBs[cmbColor.getSelectedIndex()][1],colorRGBs[cmbColor.getSelectedIndex()][2]);
				lblIcon.setIcon(new ImageIcon(iconIcon));

			}
		};
		
		cmbSkin.addActionListener(changeSkin);
		cmbColor.addActionListener(changeSkin);
		tabGeneral.add(lblIcon);
		chckbxColor.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cmbColor.setEnabled(chckbxColor.isSelected());
			}
		});
		chckbxSkin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				cmbSkin.setEnabled(chckbxSkin.isSelected());
			}
		});
		
		JPanel tabMisc = new JPanel();
		tbpMainSettings.addTab("Misc", null, tabMisc, null);
		tabMisc.setLayout(null);
		
		JButton btnDemoBrowse = new JButton("...");
		btnDemoBrowse.setBounds(386, 12, 28, 28);
		tabMisc.add(btnDemoBrowse);
		btnDemoBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(demoFileFilter);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				if(rVal == JFileChooser.APPROVE_OPTION) {
					txtDemoName.setText(fileChooser.getSelectedFile().getName());
				}
			}
		});
		btnDemoBrowse.setEnabled(false);
		
		txtDemoName = new JTextField();
		txtDemoName.setBounds(149, 12, 225, 28);
		tabMisc.add(txtDemoName);
		txtDemoName.setEnabled(false);
		txtDemoName.setText("demoname");
		txtDemoName.setColumns(10);
		
		JComboBox<String> cmbDemo = new JComboBox<>();
		cmbDemo.setBounds(12, 12, 125, 28);
		tabMisc.add(cmbDemo);
		cmbDemo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtDemoName.setEnabled(cmbDemo.getSelectedIndex()!=0);
				btnDemoBrowse.setEnabled(cmbDemo.getSelectedIndex()!=0);
			}
		});
		cmbDemo.setModel(new DefaultComboBoxModel<>(new String[] {"No demo", "Record demo", "Play demo", "Time demo"}));
		
		txtMapNumber = new JTextField();
		txtMapNumber.setBounds(149, 52, 265, 28);
		tabMisc.add(txtMapNumber);
		txtMapNumber.setEnabled(false);
		txtMapNumber.setText("90");
		txtMapNumber.setColumns(10);
		
		JCheckBox chckbxWarpToMap = new JCheckBox("Warp to map:");
		chckbxWarpToMap.setBounds(12, 54, 125, 23);
		tabMisc.add(chckbxWarpToMap);
		
		JCheckBox chckbxDebugMode = new JCheckBox("Debug mode");
		chckbxDebugMode.setBounds(12, 88, 402, 23);
		tabMisc.add(chckbxDebugMode);
		
		JCheckBox chckbxSeparateConsoleWindow = new JCheckBox("Separate console window");
		chckbxSeparateConsoleWindow.setBounds(12, 122, 402, 23);
		tabMisc.add(chckbxSeparateConsoleWindow);
		
		JCheckBox chckbxUltimateMode = new JCheckBox("Ultimate mode");
		chckbxUltimateMode.setBounds(12, 156, 402, 23);
		tabMisc.add(chckbxUltimateMode);
		chckbxWarpToMap.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtMapNumber.setEnabled(chckbxWarpToMap.isSelected());
			}
		});
		btnClientBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				if(rVal == JFileChooser.APPROVE_OPTION) {
					txtClientPath.setText(fileChooser.getSelectedFile().getName());
				}
			}
		});
		chckbxResolution.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtWPort.setEnabled(chckbxResolution.isSelected());
				txtHPort.setEnabled(chckbxResolution.isSelected());
			}
		});
		
		JPanel tabFiles = new JPanel();
		tbpMain.addTab("Files", null, tabFiles, null);
		tabFiles.setLayout(null);
		
		JLabel lblListOfWads = new JLabel("List of PK3s, WADs, SOCs, Lua scripts, and other files to include:");
		lblListOfWads.setBounds(10, 11, 409, 14);
		tabFiles.add(lblListOfWads);
		
		DefaultListModel<String> filesListModel = new DefaultListModel<>();
		JList<String> lstFiles = new JList<>(filesListModel);
		lstFiles.setBorder(new LineBorder(new Color(0, 0, 0)));
		lstFiles.setBounds(0, 36, 431, 232);
		tabFiles.add(lstFiles);
		
		txtExec = new JTextField();
		txtExec.setEnabled(false);
		txtExec.setBounds(158, 340, 261, 26);
		tabFiles.add(txtExec);
		txtExec.setColumns(10);
		
		JCheckBox chckbxExecuteScript = new JCheckBox("Execute script:");
		chckbxExecuteScript.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtExec.setEnabled(chckbxExecuteScript.isSelected());
			}
		});
		chckbxExecuteScript.setBounds(10, 340, 144, 23);
		tabFiles.add(chckbxExecuteScript);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lstFiles.isSelectionEmpty())
					filesListModel.remove(lstFiles.getSelectedIndex());
			}
		});
		btnDelete.setBounds(294, 306, 125, 23);
		tabFiles.add(btnDelete);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(CompatibleFileFilter);
				fileChooser.addChoosableFileFilter(Pk3Filter);
				fileChooser.addChoosableFileFilter(doomWadFilter);
				fileChooser.addChoosableFileFilter(srb2SocFilter);
				fileChooser.addChoosableFileFilter(luaFilter);
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				if(rVal == JFileChooser.APPROVE_OPTION) {
					//filesListModel.addElement(fileChooser.getSelectedFile().getName());
					for(int i = 0; i<fileChooser.getSelectedFiles().length; i++) {
						filesListModel.addElement(fileChooser.getSelectedFiles()[i].getName());
					}
				}
			}
		});
		btnAdd.setBounds(294, 280, 125, 23);
		tabFiles.add(btnAdd);
		
		JButton btnMoveUp = new JButton("");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lstFiles.isSelectionEmpty()) {
					if(lstFiles.getSelectedIndex()!=0) {
						int currentIndex=lstFiles.getSelectedIndex();
						filesListModel.insertElementAt(lstFiles.getSelectedValue(), currentIndex-1);
						filesListModel.remove(currentIndex+1);
						lstFiles.setSelectedIndex(currentIndex-1);
					}
				}
			}
		});
		btnMoveUp.setIcon(new ImageIcon(LauncherWindow.class.getResource("/javax/swing/plaf/metal/icons/sortUp.png")));
		btnMoveUp.setBounds(10, 280, 24, 23);
		tabFiles.add(btnMoveUp);
		
		JButton btnMoveDown = new JButton("");
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lstFiles.isSelectionEmpty()) {
					if(lstFiles.getSelectedIndex()!=lstFiles.getLastVisibleIndex()) {
						int currentIndex=lstFiles.getSelectedIndex();
						filesListModel.insertElementAt(lstFiles.getSelectedValue(), currentIndex+2);
						filesListModel.remove(currentIndex);
						lstFiles.setSelectedIndex(currentIndex+1);
					}
				}
			}
		});
		btnMoveDown.setIcon(new ImageIcon(LauncherWindow.class.getResource("/javax/swing/plaf/metal/icons/sortDown.png")));
		btnMoveDown.setBounds(10, 306, 24, 23);
		tabFiles.add(btnMoveDown);
		
		JButton btnSaveList = new JButton("Save list");
		btnSaveList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileFilter(srb2FileList);
				fc.setCurrentDirectory(new File(workingDirectory));
				int rVal =  fc.showSaveDialog(null);
				if(rVal==JFileChooser.APPROVE_OPTION) {
					try {
						if(!fc.getSelectedFile().toString().endsWith(".sfl"))
							fc.setSelectedFile(new File(fc.getSelectedFile().toString()+".sfl"));
						FileWriter fw = new FileWriter(fc.getSelectedFile());
						
						for(int i=0;i<filesListModel.size();i++) {
							fw.write(filesListModel.getElementAt(i)+"\n");
						}
						
						fw.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnSaveList.setBounds(46, 280, 112, 23);
		tabFiles.add(btnSaveList);
		
		JButton btnLoadList = new JButton("Load list");
		btnLoadList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				filesListModel.clear();
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(srb2FileList);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				
				if(rVal == JFileChooser.APPROVE_OPTION) {
				
					FileReader fr;
					try {
						fr = new FileReader(fileChooser.getSelectedFile());
						BufferedReader br = new BufferedReader(fr);
						
						String curLine;
						while((curLine=br.readLine())!=null) {
							filesListModel.addElement(curLine);
						}
						
						br.close();
						fr.close();
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				
				
				
				
			}
		});
		btnLoadList.setBounds(170, 280, 112, 23);
		tabFiles.add(btnLoadList);
		
		JButton btnClearList = new JButton("Clear list");
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filesListModel.clear();
			}
		});
		btnClearList.setBounds(46, 306, 236, 22);
		tabFiles.add(btnClearList);
		
		JPanel tabNetwork = new JPanel();
		tbpMain.addTab("Network", null, tabNetwork, null);
		tabNetwork.setLayout(null);
		
		JTabbedPane tbpNetwork = new JTabbedPane(JTabbedPane.TOP);
		tbpNetwork.setEnabled(false);
		tbpNetwork.setBounds(0, 34, 431, 341);
		if(UIManager.getLookAndFeel().getName().contains("GTK"))
			tbpNetwork.setBounds(-10, 32, tbpMain.getWidth(), tbpMain.getHeight()-60);
		tabNetwork.add(tbpNetwork);
		
		JCheckBox chckbxMultiplayerGame = new JCheckBox("Multiplayer game");
		chckbxMultiplayerGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tbpNetwork.setEnabled(chckbxMultiplayerGame.isSelected());
			}
		});
		chckbxMultiplayerGame.setBounds(8, 8, 411, 23);
		tabNetwork.add(chckbxMultiplayerGame);
		
		JPanel tabJoin = new JPanel();
		tbpNetwork.addTab("Join", null, tabJoin, null);
		tabJoin.setLayout(null);
		
		JLabel lblHostIp = new JLabel("Host IP:");
		lblHostIp.setBounds(10, 11, 65, 14);
		tabJoin.add(lblHostIp);
		
		txtIP = new JTextField();
		txtIP.setBounds(93, 9, 315, 24);
		tabJoin.add(txtIP);
		txtIP.setColumns(10);
		
		JCheckBox chckbxDontDownloadFiles = new JCheckBox("Don't download files from server");
		chckbxDontDownloadFiles.setBounds(10, 72, 402, 23);
		tabJoin.add(chckbxDontDownloadFiles);
		
		JCheckBox chckbxJoinMyOwn = new JCheckBox("Join my own dedicated server");
		chckbxJoinMyOwn.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				txtIP.setEnabled(!chckbxJoinMyOwn.isSelected());
			}
		});
		chckbxJoinMyOwn.setBounds(10, 41, 266, 24);
		tabJoin.add(chckbxJoinMyOwn);
		
		JPanel tabHost = new JPanel();
		tbpNetwork.addTab("Host", null, tabHost, null);
		tabHost.setLayout(null);
		
		JTabbedPane tbpHost = new JTabbedPane(JTabbedPane.TOP);
		tbpHost.setBounds(0, 0, 426, 314);
		if(UIManager.getLookAndFeel().getName().contains("GTK"))
			tbpHost.setBounds(-10, -2, tbpNetwork.getWidth()-2, tbpNetwork.getHeight()-2);
		tabHost.add(tbpHost);
		
		JPanel tabServer = new JPanel();
		tbpHost.addTab("Server", null, tabServer, null);
		tabServer.setLayout(null);
		
		JLabel lblServerName = new JLabel("Server name:");
		lblServerName.setBounds(10, 12, 113, 23);
		tabServer.add(lblServerName);
		
		txtServerName = new JTextField();
		txtServerName.setBounds(165, 12, 244, 23);
		tabServer.add(txtServerName);
		txtServerName.setColumns(10);
		
		JCheckBox chckbxDedicated = new JCheckBox("Dedicated server");
		chckbxDedicated.setBounds(200, 47, 171, 23);
		tabServer.add(chckbxDedicated);
		
		JLabel lblRoomId = new JLabel("Room ID:");
		lblRoomId.setBounds(10, 78, 90, 23);
		tabServer.add(lblRoomId);
		
		txtRoomID = new JTextField();
		txtRoomID.setBounds(165, 78, 244, 23);
		tabServer.add(txtRoomID);
		txtRoomID.setEnabled(false);
		txtRoomID.setText("33");
		txtRoomID.setColumns(10);
		
		JCheckBox chckbxAdvertiseOnInternet = new JCheckBox("Advertise on internet");
		chckbxAdvertiseOnInternet.setBounds(8, 47, 171, 23);
		tabServer.add(chckbxAdvertiseOnInternet);
		
		JCheckBox chckbxServerPassword = new JCheckBox("Admin password:");
		chckbxServerPassword.setBounds(8, 113, 145, 23);
		tabServer.add(chckbxServerPassword);
		
		JCheckBox chckbxAllowWadDownloading = new JCheckBox("Allow WAD downloading");
		chckbxAllowWadDownloading.setSelected(true);
		chckbxAllowWadDownloading.setBounds(8, 144, 401, 24);
		tabServer.add(chckbxAllowWadDownloading);
		
		txtServerPassword = new JPasswordField();
		txtServerPassword.setEnabled(false);
		txtServerPassword.setBounds(165, 114, 171, 20);
		tabServer.add(txtServerPassword);
		
		JCheckBox chckbxShowPassword = new JCheckBox("Show");
		chckbxShowPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxShowPassword.isSelected()) {
					txtServerPassword.setEchoChar((char)0);
				}else {
					txtServerPassword.setEchoChar('œ');
				}
			}
		});
		chckbxShowPassword.setBounds(342, 112, 67, 24);
		tabServer.add(chckbxShowPassword);
		
		JPanel tabGame = new JPanel();
		tbpHost.addTab("Game", null, tabGame, null);
		tabGame.setLayout(null);
		
		JLabel lblGametype = new JLabel("Gametype:");
		lblGametype.setBounds(12, 12, 93, 14);
		tabGame.add(lblGametype);
		
		JComboBox<String> cmbGameType = new JComboBox<>();
		cmbGameType.setBounds(131, 8, 278, 23);
		tabGame.add(cmbGameType);
		cmbGameType.setModel(new DefaultComboBoxModel<>(new String[] {"Co-op", "Competition", "Race", "Match", "Team Match", "Tag", "Hide and Seek", "Capture the Flag"}));
		
		JLabel lblTeam = new JLabel("Team:");
		lblTeam.setBounds(12, 47, 46, 14);
		tabGame.add(lblTeam);
		
		JComboBox<String> cmbTeam = new JComboBox<>();
		cmbTeam.setBounds(131, 43, 278, 23);
		tabGame.add(cmbTeam);
		cmbTeam.setModel(new DefaultComboBoxModel<>(new String[] {"Spectator", "Red", "Blue"}));
		
		txtTimeLimit = new JTextField();
		txtTimeLimit.setBounds(131, 148, 278, 23);
		tabGame.add(txtTimeLimit);
		txtTimeLimit.setEnabled(false);
		txtTimeLimit.setText("5");
		txtTimeLimit.setColumns(10);
		
		JCheckBox chckbxTimeLimit = new JCheckBox("Time limit:");
		chckbxTimeLimit.setBounds(8, 148, 105, 23);
		tabGame.add(chckbxTimeLimit);
		
		JCheckBox chckbxPointLimit = new JCheckBox("Point limit:");
		chckbxPointLimit.setBounds(8, 113, 105, 23);
		tabGame.add(chckbxPointLimit);
		
		txtPointLimit = new JTextField();
		txtPointLimit.setBounds(131, 113, 278, 23);
		tabGame.add(txtPointLimit);
		txtPointLimit.setEnabled(false);
		txtPointLimit.setText("1000");
		txtPointLimit.setColumns(10);
		
		JCheckBox chckbxMaxPlayers = new JCheckBox("Max players:");
		chckbxMaxPlayers.setBounds(8, 183, 115, 23);
		tabGame.add(chckbxMaxPlayers);
		
		txtMaxPlayers = new JTextField();
		txtMaxPlayers.setBounds(131, 183, 278, 23);
		tabGame.add(txtMaxPlayers);
		txtMaxPlayers.setEnabled(false);
		txtMaxPlayers.setText("4");
		txtMaxPlayers.setColumns(10);
		
		JCheckBox chckbxDisableWeaponRings = new JCheckBox("Disable weapon rings");
		chckbxDisableWeaponRings.setBounds(8, 218, 192, 23);
		tabGame.add(chckbxDisableWeaponRings);
		
		JLabel lblAdvanceMap = new JLabel("Advance map:");
		lblAdvanceMap.setBounds(12, 82, 115, 14);
		tabGame.add(lblAdvanceMap);
		
		JComboBox<String> cmbAdvanceMap = new JComboBox<>();
		cmbAdvanceMap.setBounds(131, 78, 278, 23);
		tabGame.add(cmbAdvanceMap);
		cmbAdvanceMap.setModel(new DefaultComboBoxModel<>(new String[] {"Off", "Next", "Random"}));
		cmbAdvanceMap.setSelectedIndex(1);
		
		JCheckBox chckbxSuddenDeath = new JCheckBox("Sudden death");
		chckbxSuddenDeath.setBounds(204, 218, 205, 23);
		tabGame.add(chckbxSuddenDeath);
		
		JComboBox<String> cmbForceSkin = new JComboBox<>();
		cmbForceSkin.setBounds(131, 252, 278, 23);
		tabGame.add(cmbForceSkin);
		cmbForceSkin.setEnabled(false);
		cmbForceSkin.setModel(new DefaultComboBoxModel<>(new String[] {"Sonic", "Tails", "Knuckles"}));
		
		JCheckBox chckbxForceSkin = new JCheckBox("Force skin:");
		chckbxForceSkin.setBounds(8, 252, 115, 23);
		tabGame.add(chckbxForceSkin);
		chckbxForceSkin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				cmbForceSkin.setEnabled(chckbxForceSkin.isSelected());
			}
		});
		chckbxMaxPlayers.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtMaxPlayers.setEnabled(chckbxMaxPlayers.isSelected());
			}
		});
		chckbxPointLimit.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtPointLimit.setEnabled(chckbxPointLimit.isSelected());
			}
		});
		chckbxTimeLimit.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtTimeLimit.setEnabled(chckbxTimeLimit.isSelected());
			}
		});
		chckbxServerPassword.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtServerPassword.setEnabled(chckbxServerPassword.isSelected());
			}
		});
		chckbxAdvertiseOnInternet.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txtRoomID.setEnabled(chckbxAdvertiseOnInternet.isSelected());
			}
		});
		
		JPanel tabConfig = new JPanel();
		tbpMain.addTab("Launcher settings...", null, tabConfig, null);
		tabConfig.setLayout(null);
		
		txtWorkingDir = new JTextField();
		txtWorkingDir.setText(Paths.get(".").toAbsolutePath().normalize().toString());
		txtWorkingDir.setEnabled(false);
		txtWorkingDir.setBounds(12, 50, 371, 23);
		tabConfig.add(txtWorkingDir);
		txtWorkingDir.setColumns(10);
		
		JLabel lblWorkingDirectory = new JLabel("Working directory:");
		lblWorkingDirectory.setBounds(12, 17, 135, 15);
		tabConfig.add(lblWorkingDirectory);
		
		JButton btnBrowseWorkingDir = new JButton("...");
		btnBrowseWorkingDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dirChoose = new JFileChooser();
				dirChoose.setCurrentDirectory(new File(workingDirectory));
				dirChoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				dirChoose.setAcceptAllFileFilterUsed(false);
				if(dirChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					txtWorkingDir.setText(dirChoose.getSelectedFile().toString());
				}
			}
		});
		btnBrowseWorkingDir.setEnabled(false);
		btnBrowseWorkingDir.setBounds(395, 50, 24, 23);
		tabConfig.add(btnBrowseWorkingDir);
		
		JCheckBox chckbxUseWine = new JCheckBox("Use WINE");
		chckbxUseWine.setBounds(12, 77, 126, 23);
		tabConfig.add(chckbxUseWine);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(12, 108, 407, 5);
		tabConfig.add(separator_6);
		String[] themes = new String[(UIManager.getInstalledLookAndFeels().length)+1];
		for(int i=0;i<themes.length-1;i++) {
			String themename = UIManager.getInstalledLookAndFeels()[i].getName();
			themes[i] = themename;
		}
		themes[themes.length-1]="Default";
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 122, 407, 240);
		tabConfig.add(panel);
		panel.setLayout(null);
		
		JButton btnApplySettings = new JButton("Apply settings");
		btnApplySettings.setBounds(12, 203, 383, 25);
		panel.add(btnApplySettings);
		
		JLabel lblNoteThemeWill = new JLabel("<html>NOTE: Theme will not change until application is restarted.</html>");
		lblNoteThemeWill.setVerticalAlignment(SwingConstants.TOP);
		lblNoteThemeWill.setBounds(12, 161, 383, 30);
		panel.add(lblNoteThemeWill);
		lblNoteThemeWill.setForeground(SystemColor.textInactiveText);
		
		JLabel lblOnOpening = new JLabel("On opening:");
		lblOnOpening.setBounds(12, 67, 95, 15);
		panel.add(lblOnOpening);
		
		txtConfigFile = new JTextField();
		txtConfigFile.setText(defaultConf);
		txtConfigFile.setBounds(12, 94, 347, 23);
		panel.add(txtConfigFile);
		txtConfigFile.setEnabled(false);
		txtConfigFile.setColumns(10);
		
		JButton btnConfBrowse = new JButton("...");
		btnConfBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(launcherConf);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				
				if(rVal == JFileChooser.APPROVE_OPTION) {
					txtConfigFile.setText(fileChooser.getSelectedFile().getName());
				}
			}
		});
		btnConfBrowse.setBounds(371, 94, 24, 23);
		panel.add(btnConfBrowse);
		btnConfBrowse.setEnabled(false);
		
		JComboBox<String> cmbDefaultConf = new JComboBox<>();
		cmbDefaultConf.setBounds(119, 62, 276, 24);
		panel.add(cmbDefaultConf);
		cmbDefaultConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtConfigFile.setEnabled(cmbDefaultConf.getSelectedIndex()==1);
				btnConfBrowse.setEnabled(cmbDefaultConf.getSelectedIndex()==1);
			}
		});
		cmbDefaultConf.setModel(new DefaultComboBoxModel<>(new String[] {"Use previous configuration", "Load settings from a file", "Load default settings"}));
		
		JComboBox<String> cmbTheme = new JComboBox<>();
		cmbTheme.setBounds(119, 129, 276, 24);
		panel.add(cmbTheme);
		cmbTheme.setModel(new DefaultComboBoxModel<>(themes));
		cmbTheme.setSelectedIndex(Math.min(currentThemeIndex,cmbTheme.getItemCount()-1));
		
		JLabel lblUseTheme = new JLabel("Use theme:");
		lblUseTheme.setBounds(12, 134, 95, 15);
		panel.add(lblUseTheme);
		btnApplySettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launcherConfFileName=txtConfigFile.getText();
				loadConfFromFile=(cmbDefaultConf.getSelectedIndex());
				themeIndex=cmbTheme.getSelectedIndex();
				saveConf();
			}
		});
		
		JLabel lblOsA = new JLabel("OS: a");
		lblOsA.setBounds(10, 409, 237, 23);
		frmLauncherWindow.getContentPane().add(lblOsA);
		
		JMenuBar menuBar = new JMenuBar();
		frmLauncherWindow.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new LauncherWindow();
				frmLauncherWindow.dispose();
			}
		});
		mntmNew.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Icon_New.png")).getImage()));
		mnFile.add(mntmNew);
		
		JComboBox<String> cmbWorkingDirSettings = new JComboBox<>();
		cmbWorkingDirSettings.setBounds(131, 12, 288, 24);
		tabConfig.add(cmbWorkingDirSettings);
		cmbWorkingDirSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtWorkingDir.setEnabled(cmbWorkingDirSettings.getSelectedIndex()==1);
				btnBrowseWorkingDir.setEnabled(cmbWorkingDirSettings.getSelectedIndex()==1);
			}
		});
		cmbWorkingDirSettings.setModel(new DefaultComboBoxModel<>(new String[] {"Client directory", "Specify directory..."}));
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(launcherConf);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showOpenDialog(null);
				
				if(rVal == JFileChooser.APPROVE_OPTION) {
					System.out.print("chosen file\n");
					File file = fileChooser.getSelectedFile();
					if(file.exists()) {
						System.out.print("file exists\n");
						LoadConfigHandler lcfH = new LoadConfigHandler();
						lcfH.loadElements(file, chckbxSkin, cmbSkin, chckbxColor, cmbColor, chckbxWarpToMap, txtMapNumber, txtClientPath, cmbRenderer, chckbxWindowedMode, chckbxResolution, txtWPort, txtHPort, chckbxDisableSoundEffects, cmbMusicMode, chckbxSeparateConsoleWindow, chckbxDebugMode, chckbxUltimateMode, cmbDemo, txtDemoName, chckbxExecuteScript, txtExec, txtArguments, filesListModel, chckbxMultiplayerGame, lblName, txtName, tbpNetwork, txtIP, chckbxDontDownloadFiles, chckbxJoinMyOwn, txtServerName, chckbxDedicated, chckbxAdvertiseOnInternet, txtRoomID, chckbxAllowWadDownloading, chckbxServerPassword, txtServerPassword, cmbGameType, chckbxPointLimit, txtPointLimit, chckbxTimeLimit, txtTimeLimit, chckbxMaxPlayers, txtMaxPlayers, chckbxDisableWeaponRings, chckbxSuddenDeath, cmbAdvanceMap, chckbxForceSkin, cmbForceSkin, cmbWorkingDirSettings, txtWorkingDir, chckbxUseWine);
						currentFile=file;
					}
					
				}
				
			}
		});
		mntmOpen.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Icon_OpenFolder_Brown.png")).getImage()));
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] fileList = new String[lstFiles.getModel().getSize()];
				for(int i=0;i<lstFiles.getModel().getSize(); i++) {
					fileList[i]=lstFiles.getModel().getElementAt(i);
				}
				if(currentFile!=null) {
					SaveLoadSys.saveParameters(currentFile, chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
							chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
							chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
							chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
							chckbxSeparateConsoleWindow.isSelected(),
							chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
							Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
							txtClientPath.getText(), txtArguments.getText(), fileList,
							chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
							txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
							txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
							txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
							cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
							chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
							chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
							chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
							chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
							(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected());
				}else {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
					fileChooser.setFileFilter(launcherConf);
					int rVal = fileChooser.showSaveDialog(null);
					if(rVal==JFileChooser.APPROVE_OPTION) {
						currentFile = fileChooser.getSelectedFile();
						SaveLoadSys.saveParameters(fileChooser.getSelectedFile(), chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
								chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
								chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
								chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
								chckbxSeparateConsoleWindow.isSelected(),
								chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
								Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
								txtClientPath.getText(), txtArguments.getText(), fileList,
								chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
								txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
								txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
								txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
								cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
								chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
								chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
								chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
								chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
								(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected());
					}
				}
			}
		});
		mntmSave.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Icon_Save_Modern.png")).getImage()));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(launcherConf);
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showSaveDialog(null);
				if(rVal==JFileChooser.APPROVE_OPTION) {
					String[] fileList = new String[lstFiles.getModel().getSize()];
					for(int i=0;i<lstFiles.getModel().getSize(); i++) {
						fileList[i]=lstFiles.getModel().getElementAt(i);
					}
					SaveLoadSys.saveParameters(fileChooser.getSelectedFile(), chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
							chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
							chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
							chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
							chckbxSeparateConsoleWindow.isSelected(),
							chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
							Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
							txtClientPath.getText(), txtArguments.getText(), fileList,
							chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
							txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
							txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
							txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
							cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
							chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
							chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
							chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
							chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
							(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected());
				}
			}
		});
		mntmSaveAs.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Icon_SaveAs_Modern.png")).getImage()));
		mnFile.add(mntmSaveAs);
		
		frmLauncherWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				String[] fileList = new String[lstFiles.getModel().getSize()];
				for(int i=0;i<lstFiles.getModel().getSize(); i++) {
					fileList[i]=lstFiles.getModel().getElementAt(i);
				}
				File svFile;
				if(workingDirectory.endsWith(File.separator))
					svFile = new File(workingDirectory+"lastConf.cfg");
				else
					svFile=new File(workingDirectory+File.separator+"lastConf.cfg");
				SaveLoadSys.saveParameters(svFile, chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
						chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
						chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
						chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
						chckbxSeparateConsoleWindow.isSelected(),
						chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
						Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
						txtClientPath.getText(), txtArguments.getText(), fileList,
						chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
						txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
						txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
						txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
						cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
						chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
						chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
						chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
						chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
						(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected());
			}
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenu mnExportScript = new JMenu("Export script...");
		mnExportScript.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/Icon_Script_CC.png")).getImage()));
		mnFile.add(mnExportScript);
		
		JMenuItem mntmBatchFile = new JMenuItem("Batch file");
		mntmBatchFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/// TODO generate batch file
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("MS Dos Batch Files (*.bat)", "BAT"));
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showSaveDialog(null);
				if(rVal==JFileChooser.APPROVE_OPTION) {
					
					
					// make sure it's a .bat
					
					if(!fileChooser.getSelectedFile().getName().endsWith(".bat"))
						fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath()+".bat"));
					
					String[] fileList = new String[lstFiles.getModel().getSize()];
					for(int i=0;i<lstFiles.getModel().getSize(); i++) {
						fileList[i]=lstFiles.getModel().getElementAt(i);
					}
					String command = SaveLoadSys.getLaunchCommand(chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
							chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
							chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
							chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
							chckbxSeparateConsoleWindow.isSelected(),
							chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
							Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
							txtClientPath.getText(), txtArguments.getText(), fileList,
							chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
							txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
							txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
							txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
							cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
							chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
							chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
							chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
							chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
							(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected(),OS);
					
					//Write to a file
					try {
						FileWriter write = new FileWriter(fileChooser.getSelectedFile());
						
						write.write(command);
						
						write.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});
		mnExportScript.add(mntmBatchFile);
		
		JMenuItem mntmShellScript = new JMenuItem("Shell script");
		mntmShellScript.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/// TODO Generate shell script
				// #!/bin/bash
				
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("Unix/Linux Shell Script (*.sh)", "SH"));
				fileChooser.setCurrentDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
				int rVal = fileChooser.showSaveDialog(null);
				if(rVal==JFileChooser.APPROVE_OPTION) {
					
					// make sure it's a .sh
					
					if(!fileChooser.getSelectedFile().getName().endsWith(".sh"))
						fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath()+".sh"));
					
					String[] fileList = new String[lstFiles.getModel().getSize()];
					for(int i=0;i<lstFiles.getModel().getSize(); i++) {
						fileList[i]=lstFiles.getModel().getElementAt(i);
					}
					String command = SaveLoadSys.getLaunchCommand(chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
							chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
							chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
							chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
							chckbxSeparateConsoleWindow.isSelected(),
							chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
							Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
							txtClientPath.getText(), txtArguments.getText(), fileList,
							chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
							txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
							txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
							txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
							cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
							chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
							chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
							chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
							chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
							(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected(),OS);
					
					//Write to a file
					try {
						FileWriter write = new FileWriter(fileChooser.getSelectedFile());
						
						write.write("#!/bin/bash");
						write.write(System.getProperty("line.separator"));
						
						write.write(command);
						
						write.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				
			}
		});
		mnExportScript.add(mntmShellScript);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AboutWindow();
			}
		});
		mnHelp.add(mntmAbout);
		if(!((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) || (OS.indexOf("mac") >= 0))) {
			chckbxUseWine.setEnabled(false);
			//System.out.print(System.getProperty("os.name"));
		}
		lblOsA.setText("OS: " + System.getProperty("os.name"));
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String[] fileList = new String[lstFiles.getModel().getSize()];
				for(int i=0;i<lstFiles.getModel().getSize(); i++) {
					fileList[i]=lstFiles.getModel().getElementAt(i);
				}
				String command = SaveLoadSys.getLaunchCommand(chckbxSkin.isSelected(), cmbSkin.getSelectedItem().toString(),
						chckbxColor.isSelected(), cmbColor.getSelectedIndex(), (cmbRenderer.getSelectedIndex()==1),
						chckbxWindowedMode.isSelected(), chckbxResolution.isSelected(), new int[] {Integer.parseInt(txtWPort.getText()),Integer.parseInt(txtHPort.getText())},
						chckbxDisableSoundEffects.isSelected(), cmbMusicMode.getSelectedIndex(),
						chckbxSeparateConsoleWindow.isSelected(),
						chckbxDebugMode.isSelected(), chckbxUltimateMode.isSelected(), chckbxWarpToMap.isSelected(),
						Integer.parseInt(txtMapNumber.getText()), cmbDemo.getSelectedIndex(), txtDemoName.getText(),
						txtClientPath.getText(), txtArguments.getText(), fileList,
						chckbxExecuteScript.isSelected(), txtExec.getText(), chckbxMultiplayerGame.isSelected(), lblName.isSelected(),
						txtName.getText(), (tbpNetwork.getSelectedIndex()==1), txtIP.getText(), chckbxDontDownloadFiles.isSelected(), chckbxJoinMyOwn.isSelected(),
						txtServerName.getText(), chckbxDedicated.isSelected(), chckbxAdvertiseOnInternet.isSelected(),
						txtRoomID.getText(), chckbxServerPassword.isSelected(), txtServerPassword.getPassword().toString(), chckbxAllowWadDownloading.isSelected(),
						cmbGameType.getSelectedIndex(), cmbTeam.getSelectedIndex(), chckbxPointLimit.isSelected(),
						chckbxTimeLimit.isSelected(), Integer.parseInt(txtPointLimit.getText()), Integer.parseInt(txtTimeLimit.getText()),
						chckbxMaxPlayers.isSelected(), Integer.parseInt(txtMaxPlayers.getText()),
						chckbxDisableWeaponRings.isSelected(), chckbxSuddenDeath.isSelected(), cmbAdvanceMap.getSelectedIndex(),
						chckbxForceSkin.isSelected(), cmbForceSkin.getSelectedIndex(),
						(cmbWorkingDirSettings.getSelectedIndex()==0), txtWorkingDir.getText(), chckbxUseWine.isSelected(),OS);
				Thread gameThread = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
					}
					
				});
				gameThread.start();
				try { 
					 
					//command="xfce4-terminal -e \""+command+"\"";
					if((chckbxSeparateConsoleWindow.isSelected() || (tbpNetwork.getSelectedIndex()==1 && chckbxDedicated.isSelected())) && OS.contains("nux")) {
						System.out.println("open terminal");
						String terminalToUse = new DetectLocalTerminal().detectTerminal();
						String[] comList = {terminalToUse,"-e",command};
						new ProcessBuilder(comList).start();
					}else
						Runtime.getRuntime().exec(command);
						//new ProcessBuilder(command.split(" ")).start();
					
					//Runtime.getRuntime().exec("xfce4-terminal -e '/home/hitcoder/Documents/SRB2/2.1/lsdl2srb2 +skin Tails +color 24 -opengl'");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStart.setBounds(274, tbpMain.getHeight()+7, 150, 23);
		frmLauncherWindow.getContentPane().add(btnStart);
		
		cmbDefaultConf.setSelectedIndex(loadConfFromFile);
		
		JLabel lblForAnyOf = new JLabel("<html>For any of the following settins to be applied,\r\nyou must click \"Apply settings\"</html>");
		lblForAnyOf.setVerticalAlignment(SwingConstants.TOP);
		lblForAnyOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblForAnyOf.setFont(new Font("Dialog", Font.BOLD, 14));
		lblForAnyOf.setBounds(12, 12, 339, 38);
		panel.add(lblForAnyOf);
		
		LoadConfigHandler lcfH = new LoadConfigHandler();
		if(loadConfFromFile==0) {
			System.out.println("loadconf last");
			File ldFile;
			if(workingDirectory.endsWith(File.separator))
				ldFile = new File(workingDirectory+"lastConf.cfg");
			else
				ldFile=new File(workingDirectory+File.separator+"lastConf.cfg");
			if(ldFile.exists()) {
				lcfH.loadElements(ldFile, chckbxSkin, cmbSkin, chckbxColor, cmbColor, chckbxWarpToMap, txtMapNumber, txtClientPath, cmbRenderer, chckbxWindowedMode, chckbxResolution, txtWPort, txtHPort, chckbxDisableSoundEffects, cmbMusicMode, chckbxSeparateConsoleWindow, chckbxDebugMode, chckbxUltimateMode, cmbDemo, txtDemoName, chckbxExecuteScript, txtExec, txtArguments, filesListModel, chckbxMultiplayerGame, lblName, txtName, tbpNetwork, txtIP, chckbxDontDownloadFiles, chckbxJoinMyOwn, txtServerName, chckbxDedicated, chckbxAdvertiseOnInternet, txtRoomID, chckbxAllowWadDownloading, chckbxServerPassword, txtServerPassword, cmbGameType, chckbxPointLimit, txtPointLimit, chckbxTimeLimit, txtTimeLimit, chckbxMaxPlayers, txtMaxPlayers, chckbxDisableWeaponRings, chckbxSuddenDeath, cmbAdvanceMap, chckbxForceSkin, cmbForceSkin, cmbWorkingDirSettings, txtWorkingDir, chckbxUseWine);
			}
		}else if(loadConfFromFile==1) {
			System.out.println("load specif");
			File ldFile = new File(txtConfigFile.getText());
			if(ldFile.exists()) {
				lcfH.loadElements(ldFile, chckbxSkin, cmbSkin, chckbxColor, cmbColor, chckbxWarpToMap, txtMapNumber, txtClientPath, cmbRenderer, chckbxWindowedMode, chckbxResolution, txtWPort, txtHPort, chckbxDisableSoundEffects, cmbMusicMode, chckbxSeparateConsoleWindow, chckbxDebugMode, chckbxUltimateMode, cmbDemo, txtDemoName, chckbxExecuteScript, txtExec, txtArguments, filesListModel, chckbxMultiplayerGame, lblName, txtName, tbpNetwork, txtIP, chckbxDontDownloadFiles, chckbxJoinMyOwn, txtServerName, chckbxDedicated, chckbxAdvertiseOnInternet, txtRoomID, chckbxAllowWadDownloading, chckbxServerPassword, txtServerPassword, cmbGameType, chckbxPointLimit, txtPointLimit, chckbxTimeLimit, txtTimeLimit, chckbxMaxPlayers, txtMaxPlayers, chckbxDisableWeaponRings, chckbxSuddenDeath, cmbAdvanceMap, chckbxForceSkin, cmbForceSkin, cmbWorkingDirSettings, txtWorkingDir, chckbxUseWine);
				
			}
		}
	}
	
	public void saveConf() {
		String directory = Paths.get(".").toAbsolutePath().normalize().toString();
		String file = directory+File.separator+"hcLaunchConfig.cfg";
		System.out.print(file);
		try {
			FileWriter write = new FileWriter(file);
			write.write("LoadConfigFromFile="+loadConfFromFile);
			write.write(System.getProperty("line.separator"));
			write.write("DefaultConf="+launcherConfFileName);
			write.write(System.getProperty("line.separator"));
			write.write("ThemeIndex="+themeIndex);
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
