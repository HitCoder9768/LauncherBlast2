import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class LoadConfigHandler {
	public void loadElements(File file, JCheckBox chckbxSkin, JComboBox<String> cmbSkin, JCheckBox chckbxColor,
			JComboBox<String> cmbColor, JCheckBox chckbxWarpToMap,
			JTextField txtMapNumber, JTextField txtClientPath, JComboBox<String> cmbRenderer, JCheckBox chckbxWindowedMode, JCheckBox chckbxManualResolution,
			JTextField txtWPort, JTextField txtHPort, JCheckBox chckbxDisableSoundEffects, JComboBox<String> cmbMusicMode,
			JCheckBox chckbxSeparateConsoleWindow,
			JCheckBox chckbxDebugMode, JCheckBox chckbxUltimateMode, JComboBox<String> cmbDemo,
			JTextField txtDemoName, JCheckBox chckbxExecuteScript, JTextField txtExec, JTextField txtArguments,
			DefaultListModel<String> filesListModel, JCheckBox chckbxMultiplayerGame, JCheckBox lblName, JTextField txtName, JTabbedPane tbpNetwork,
			JTextField txtIP, JCheckBox chckbxDontDownloadFiles, JCheckBox chckbxUseMyOwn, JTextField txtServerName,
			JCheckBox chckbxDedicated, JCheckBox chckbxAdvertiseOnInternet, JTextField txtRoomID, JCheckBox chckbxAllowWadDownloading,
			JCheckBox chckbxServerPassword, JTextField txtServerPassword, JComboBox<String> cmbGameType, JCheckBox chckbxPointLimit,
			JTextField txtPointLimit, JCheckBox chckbxTimeLimit, JTextField txtTimeLimit, JCheckBox chckbxMaxPlayers, JTextField txtMaxPlayers,
			JCheckBox chckbxDisableWeaponRings, JCheckBox chckbxSuddenDeath, JComboBox<String> cmbAdvanceMap, JCheckBox chckbxForceSkin, JComboBox<String> cmbForceSkin,
			JComboBox<String> cmbWorkingDirSettings, JTextField txtWorkingDir, JCheckBox chckbxUseWine) {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String curLine;
			String section="";
			while((curLine=br.readLine())!=null) {
				if(curLine.startsWith("[") && curLine.endsWith("]")) {
					section = curLine.substring(1, curLine.length()-1).toUpperCase();
					System.out.print("Section set to: "+section+"\n");
				}else {
					String[] var = curLine.split("=");
					if(var.length>1) {
						System.out.print("Property `"+var[0]+"` to be set to `"+var[1]+"`\n");
						switch(section) {
						case "GAME":
							switch(var[0].toLowerCase()) {
								case "setskin": chckbxSkin.setSelected(var[1].contains("true")); break;
								case "skin":
									if(((DefaultComboBoxModel<String>) cmbSkin.getModel()).getIndexOf(var[1]) != -1) {
										cmbSkin.setSelectedIndex(((DefaultComboBoxModel<String>) cmbSkin.getModel()).getIndexOf(var[1]));
									}
									cmbSkin.getEditor().setItem(var[1]); 
									break;
								case "setcolor": chckbxColor.setSelected(var[1].contains("true")); break;
								case "color": cmbColor.setSelectedIndex(Integer.parseInt(var[1])); break;
								case "warptomap": chckbxWarpToMap.setSelected(var[1].contains("true")); break;
								case "warpmapnumber": txtMapNumber.setText(var[1]); break;
							}
							break;
						case "ENGINE":
							switch(var[0].toLowerCase()) {
								case "executable": txtClientPath.setText(var[1]); break;
								case "useopengl": if(var[1].contains("true")) cmbRenderer.setSelectedIndex(1);
									else cmbRenderer.setSelectedIndex(0); break;
								case "windowedmode": chckbxWindowedMode.setSelected(var[1].contains("true")); break;
								case "manualviewport": chckbxManualResolution.setSelected(var[1].contains("true")); break;
								case "windowport":
									if(var[1].contains("x")) {
										String[] vport=var[1].split("x");
										if(vport.length>1) {
											txtWPort.setText(vport[0]);
											txtHPort.setText(vport[1]);
										}
									}
									break;
								case "disablesoundeffects": chckbxDisableSoundEffects.setSelected(var[1].contains("true")); break;
								case "musicengine": cmbMusicMode.setSelectedIndex(Integer.parseInt(var[1])); break;
							}
							break;
						case "MISC":
							switch(var[0].toLowerCase()) {
								case "separateconsolewindow": chckbxSeparateConsoleWindow.setSelected(var[1].contains("true")); break;
								case "debug": chckbxDebugMode.setSelected(var[1].contains("true")); break;
								case "ultimate": chckbxUltimateMode.setSelected(var[1].contains("true")); break;
								case "recorddemo": cmbDemo.setSelectedIndex(Integer.parseInt(var[1])); break;
								case "demofile": txtDemoName.setText(var[1]); break;
								case "runscript": chckbxExecuteScript.setSelected(var[1].contains("true")); break;
								case "scriptname": txtExec.setText(var[1]); break;
								case "customparameters": txtArguments.setText(var[1]); break;
							}
							break;
						case "FILES":
							filesListModel.addElement(var[1]);
							break;
						case "NETWORK":
							switch(var[0].toLowerCase()) {
								case "isonline": chckbxMultiplayerGame.setSelected(var[1].contains("true")); break;
								case "username": txtName.setText(var[1]); break;
								case "ishost":
									if(var[1].contains("true"))
										tbpNetwork.setSelectedIndex(1);
									else
										tbpNetwork.setSelectedIndex(0);
									break;
							}
							break;
						case "CLIENT":
							switch(var[0].toLowerCase()) {
								case "host": txtIP.setText(var[1]); break;
								case "donotdownload": chckbxDontDownloadFiles.setSelected(var[1].contains("true")); break;
								case "joinownserv": chckbxUseMyOwn.setSelected(var[1].contains("true")); break;
							}
							break;
						case "HOST":
							switch(var[0].toLowerCase()) {
								case "name": txtServerName.setText(var[1]); break;
								case "dedicated": chckbxDedicated.setSelected(var[1].contains("true")); break;
								case "usems": chckbxAdvertiseOnInternet.setSelected(var[1].contains("true")); break;
								case "allowdownloading": chckbxAllowWadDownloading.setSelected(var[1].contains("true")); break;
								case "roomid": txtRoomID.setText(var[1]); break;
								case "useadminpass": chckbxServerPassword.setSelected(var[1].contains("true")); break;
								case "adminpassword": txtServerPassword.setText(var[1]); break;
								case "gametype": cmbGameType.setSelectedIndex(Integer.parseInt(var[1])); break;
								case "enablepointlimit": chckbxPointLimit.setSelected(var[1].contains("true")); break;
								case "pointlimit": txtPointLimit.setText(var[1]); break;
								case "enabletimelimit": chckbxTimeLimit.setSelected(var[1].contains("true")); break;
								case "timelimit": txtTimeLimit.setText(var[1]); break;
								case "capplayers": chckbxMaxPlayers.setSelected(var[1].contains("true")); break;
								case "maxplayers": txtMaxPlayers.setText(var[1]); break;
								case "noweaponrings": chckbxDisableWeaponRings.setSelected(var[1].contains("true")); break;
								case "suddendeath": chckbxSuddenDeath.setSelected(var[1].contains("true")); break;
								case "advancetonextlevel": cmbAdvanceMap.setSelectedIndex(Integer.parseInt(var[1])); break;
								case "forceskin": chckbxForceSkin.setSelected(var[1].contains("true")); break;
								case "skintoforce": cmbForceSkin.setSelectedIndex(Integer.parseInt(var[1])); break;
							}
							break;
						case "LAUNCHER":
							switch(var[0].toLowerCase()) {
								case "usedefaultpath": 
									if(var[1].contains("true"))
										cmbWorkingDirSettings.setSelectedIndex(0);
									else
										cmbWorkingDirSettings.setSelectedIndex(1);
									break;
								case "workingdirectory": txtWorkingDir.setText(var[1]); break;
								case "usewine": chckbxUseWine.setSelected(var[1].contains("true")); break;
							}
							break;
						}
					}
				}
			}
			fr.close();
			br.close();
		}catch(IOException e1) {
			e1.printStackTrace();
		}
	}
}
